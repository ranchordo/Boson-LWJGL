package main;

import java.util.ArrayList;
import java.util.Arrays;

public class TerrainGenerator {
	public ArrayList<Cell> holding=new ArrayList<Cell>(); //The pergatory realm for newly generated cells
	Pattern lastPattern;
	private GeneratorThread genThread=new GeneratorThread();
	
	public boolean isRunning=false;
	
	public void tick() {
		this.addPendingPattern();
		this.handle_generation();
	}
	
	
	public Pattern genAdapter(int np, int alen) { //Generate a long platform at pole 0
		Pattern adapter=new Pattern(np);
		for(int i=0;i<alen;i++) {
			Cell[] newrow=new Cell[np];
			for(int j=0;j<np;j++) {
				newrow[j]=new Cell(j,0,'N');
			}
			newrow[0]=new Cell(0,0,'P');
			adapter.rows.add(newrow);
		}
		return adapter;
	}
	
	public void handle_generation() { //Keep our pergatory stocked		
		ArrayList<Integer> uns=new ArrayList<Integer>();
		@SuppressWarnings("unchecked")
		ArrayList<Cell> holding_temp=(ArrayList<Cell>) holding.clone();
		for(Cell o : holding_temp) {
			int e=o.zrel_id;
			boolean unique=true;
			for(int o1 : uns) {
				if(o1==e) {
					unique=false;
				}
			}
			if(unique) {
				uns.add(e);
			}
		}
		if(uns.size()<40) {
			genThread.start();
		}
		
	}
	public boolean isWalkable(char a) { //Can I walk on it?
		for(char c : BosonX.m.walkable) {
			if(c==a) {
				return true;
			}
		}
		return false;
	}
	public int[] checkContinuity(Pattern a, Pattern b) throws IndexOutOfBoundsException { //Check paths between two patterns.
		assert a.numPoles==b.numPoles;
		int n=1;
		int ends=0;
		Cell[] la=new Cell[] {};
		boolean ar=false;
		//Try to find the last row with walkable platforms
		while(!ar) {
			la=a.rows.get(a.rows.size()-n);
			for(Cell c : la) {
				if(isWalkable(c.type)) {
					//We found the last row with platforms!
					ar=true;
					break;
				}
			}
			if(!ar) {n++;}
		}
		//Enumerate the ends
		for(Cell c : la) {
			if(isWalkable(c.type)) {
				ends++;
			}
		}
		Cell[] fb=new Cell[] {};
		int nb=0;
		boolean br=false;
		//Find the first row of b with walkable platforms
		while(!br) {
			fb=b.rows.get(nb);
			for(Cell c : fb) {
				if(isWalkable(c.type)) {
					//We found the first row with platforms!
					br=true;
					break;
				}
			}
			if(!br) {nb++;}
		}
		//Convert the fb row to integers with the nearest platform as the value
		//This is a really confusing routine
		int[] fb_b=new int[a.numPoles];
		Arrays.fill(fb_b, -1);
		for(Cell fbc : fb) {
			if(isWalkable(fbc.type)) {
				assert (float) Math.round(fbc.pole())==fbc.pole();
				fb_b[Math.round(fbc.pole())]=nb;
			}
		}
		//Convert the la row to booleans
		boolean[] la_b=new boolean[a.numPoles];
		for(Cell lac : la) {
			if(isWalkable(lac.type)) {
				assert (float) Math.round(lac.pole())==lac.pole();
				try {
					la_b[Math.round(lac.pole())]=true;
				} catch(IndexOutOfBoundsException e) {
					e.printStackTrace();
					System.out.println(lac.pole());
					System.exit(1);
				}
			} else {
				la_b[Math.round(lac.pole())]=false;
			}
		}
		//Go up the cell check distance and do some stuff as we go:
		for(int i=1;i<BosonX.check_cells+1;i++) {
			Cell[] fbt=new Cell[] {}; //We won't use this value
			try {
				fbt=b.rows.get(nb+i); //Step forward along b
			} catch(IndexOutOfBoundsException e) {
				continue;
			}
			for(Cell fbc : fbt) {
				if(isWalkable(fbc.type)) {
					assert (float) Math.round(fbc.pole())==fbc.pole(); //Assert fbc.pole() is actually an int
					if(fb_b[Math.round(fbc.pole())]==-1) {
						fb_b[Math.round(fbc.pole())]=nb+i;
					}
				}
			}
			for(Cell fbc : fbt) {
				if(fbc.type=='B') { //We have a barrier in the way
					assert (float) Math.round(fbc.pole())==fbc.pole(); //Assert fbc.pole() is actually an int
					if(nb+i<fb_b[Math.round(fbc.pole())]) {
						fb_b[Math.round(fbc.pole())]=-1;
					}
				}
			}
		}
		//Enumerate beginnings
		int beginnings=0;
		for(int i : fb_b) {
			if(i!=-1) {
				beginnings++;
			}
		}
		int paths=0;
		//Now we just need to enumerate a crap ton of paths:
		for(int i=0;i<la_b.length;i++) {
			//i is our pole
			if(la_b[i]) {
				for(int j=0;j<fb_b.length;j++) {
					if(fb_b[j]!=-1) {
						if(i==j || i==(j+1)%a.numPoles || j==(i+1)%a.numPoles) {
							paths++;
							break;
						}
					}
				}
			}
		}
		return new int[] {paths,ends,beginnings};
	}
	public void handleRenderAdd() { //Add the contents of our pergatory to the render routine one by one
		boolean done=false;
		while(!done) {
			done=true;
			for(int i=0;i<holding.size();i++) {
				if(holding.get(i).inRender) {
					continue;
				}
				if(BosonX.m.r.cam_translation[2]+((BosonX.active.gen_distance*(BosonX.m.speed/BosonX.active.speed0))*BosonX.cell_length) >= holding.get(i).zrel) {
					ArrayList<Cell> toremove=new ArrayList<Cell>();
					toremove.add(holding.get(i));
					for(Cell c : holding.get(i).getAhead()) {
						toremove.add(c); //Copy over the contents of the entire platform to toremove
					}
					//This way we generate in one entire platform at a time
					float[] a=new float[0]; //These are for passing constants as things initialize
					float a2=-1;
					//End
					int idx=0;
					for(int j=0;j<toremove.size();j++) { //Make sure we handle our generation boundary right
						if(toremove.get(j).zrel>BosonX.m.gen_cutoff*BosonX.cell_length) {
							idx=j;
							break;
						}
					}
					for(int j=0;j<toremove.size();j++) {
						Cell c=toremove.get(j); //Grab the cell in the platform
						if(c.inRender) {
							continue;
						}
						c.rotate(BosonX.m.pole); //Make sure we rotate it correctly
						c.platform_id=j-idx; //Give it its platform id while handling the generation boundary
						c.platform_length=toremove.size();
						if(!c.rotationDefined) {c.calculateConsts(); c.initGeo();} //Initialize some stuff
						if(c.zrel>BosonX.m.gen_cutoff*BosonX.cell_length) {
							a=c.initGenerate(a); //Start generating
							c.genc=0; //Start the generation clock
						}
						a2=c.onRenderAdd(a2); //Call the onRenderAdd method and pass floats down the platform as we go
						while(holding.remove(c)) {}
						BosonX.m.r.celllist.add(c); //Add to render routine
						done=false;
					}
					break;
				}
			}
		}
	}
	
	
	
	
	
	public Pattern pendingPattern=null;
	public void addAdapter(int len) {
		Pattern p=genAdapter(BosonX.m.cp.numpoles,len);
		p.enter=BosonX.m.enter;
		BosonX.m.enter+=p.rows.size();
		//System.out.println(enter);
		p.initCells();
		p.initGeo();
		pendingPattern=p;
	}
	public void addPattern_direct(int idx,int rot) { //Manual adding
		Pattern p=BosonX.m.cp.parsePattern(idx);
		p.enter=BosonX.m.enter;
		BosonX.m.enter+=p.rows.size();
		//System.out.println(enter);
		p.initCells();
		p.rotate(rot+6);
		p.initGeo();
		pendingPattern=p;
	}
	private void addPendingPattern() {
		if(pendingPattern!=null) {
			lastPattern=pendingPattern;
			pendingPattern.addHoldingRoutine();
			pendingPattern=null;
		}
	}
}
