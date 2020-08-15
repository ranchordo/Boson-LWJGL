package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.*;

import org.lwjgl.BufferUtils;

public class BosonX {
	
	//Instantaneous variables
	public float pole=0;
	public float energy=0;
	public float speed=0;
	
	//Level-specific constants
	public background.Bg background;
	public float gravity_i=0.27f;
	public float gravity_o=0.5f;
	public float jump_relative=0.2f;
	public int jump_duration=2000;
	
	public float speed0=0.4f;
	public float speed1=0.75f;
	
	public background.Bg bg;
	
	public util.Vector4f P_color;
	
	public util.Vector4f C_color1;
	public util.Vector4f C_color0;
	public float C_c;
	public float C_l;
	
	public util.Vector4f E_color1;
	public util.Vector4f E_color0;
	public float E_c;
	public float E_l;
	
	public float gen_distance=0;
	public float gen_width=0;
	
	public float energy_gain=0;
	
	public float F_speed=0;
	public float G_speed0=0;
	public float G_speed1=0;
	
	public boolean strictGeneration=false;
	
	public boolean p0_scalc=false;
	//Universal, non-final constants
	public long start_time=0;
	
	//Constants
	public static final float cell_length=5f;
	public static final float cell_thickness=1f;
	public static final float background_gen=500f;
	public static final float particle_depth=30;
	public static final float B_height=12;
	public static final boolean allowMultiJumps=false;
	public static final int check_cells=4;
	
	public Font neuropol;
	
	public static final float depth(float level) {return 12.0f-0.8f*level;}
	
	public Renderer r=new Renderer();
	public Cellparser cp=new Cellparser();
	public static BosonX m;
	private Random rand=new Random();
	public int enter=0;
	private Pattern lastPattern;
	
	public ArrayList<Cell> holding=new ArrayList<Cell>();
	
	public void loadSpec(int level) {
		Spec.loadSpec(level);
	}
	
	public void handle_generation() {
		
		
		ArrayList<Integer> uns=new ArrayList<Integer>();
		for(Cell o : holding) {
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
		for(Cell o : r.celllist) {
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
		if(uns.size()<(this.gen_distance*(speed/speed0))+40) {
			addPattern();
		}
		
	}
	public int[] checkContinuity(Pattern a, Pattern b) {
		assert a.numPoles==b.numPoles;
		int n=1;
		int ends=0;
		Cell[] la=new Cell[] {};
		boolean ar=false;
		while(!ar) {
			la=a.rows.get(a.rows.size()-n);
			for(Cell c : la) {
				if(c.type=='P' || c.type=='E' || c.type=='C') {
					ar=true;
					break;
				}
			}
			if(!ar) {n++;}
		}
		for(Cell c : la) {
			if(c.type=='P' || c.type=='E' || c.type=='C') {
				ends++;
			}
		}
		Cell[] fb=new Cell[] {};
		int nb=0;
		boolean br=false;
		while(!br) {
			try {
				fb=b.rows.get(nb);
				for(Cell c : fb) {
					if(c.type=='P' || c.type=='E' || c.type=='C' || c.type=='F') {
						//We found the first row with platforms!
						br=true;
						break;
					}
				}
				if(!br) {nb++;}
			} catch(IndexOutOfBoundsException e) {
				e.printStackTrace();
				br=true; //Whatever. Let's not use this pattern.
				return new int[] {0,a.numPoles,b.numPoles}; //This is a bad pattern now
			}
		}
		//Convert the fb row to integers with the nearest platform as the value
		int[] fb_b=new int[a.numPoles];
		Arrays.fill(fb_b, -1);
		for(Cell fbc : fb) {
			if(fbc.type=='P' || fbc.type=='E' || fbc.type=='C' || fbc.type=='F') {
				assert (float) Math.round(fbc.pole)==fbc.pole;
				fb_b[Math.round(fbc.pole)]=nb;
			}
		}
		//Convert the la row to booleans
		boolean[] la_b=new boolean[a.numPoles];
		for(Cell lac : la) {
			if(lac.type=='P' || lac.type=='E' || lac.type=='C' || lac.type=='F') {
				assert (float) Math.round(lac.pole)==lac.pole;
				la_b[Math.round(lac.pole)]=true;
			} else {
				la_b[Math.round(lac.pole)]=false;
			}
		}
		//Sequence the idx and update
		for(int i=1;i<BosonX.check_cells+1;i++) {
			Cell[] fbt=new Cell[] {}; //We won't use this value
			try {
				fbt=b.rows.get(nb+i);
			} catch(IndexOutOfBoundsException e) {
				continue;
			}
			for(Cell fbc : fbt) {
				if(fbc.type=='P' || fbc.type=='E' || fbc.type=='C' || fbc.type=='F') {
					assert (float) Math.round(fbc.pole)==fbc.pole; //Assert fbc.pole is actually an int
					if(fb_b[Math.round(fbc.pole)]==-1) {
						fb_b[Math.round(fbc.pole)]=nb+i;
					}
				}
			}
			for(Cell fbc : fbt) {
				if(fbc.type=='B') { //We have a barrier in the way
					assert (float) Math.round(fbc.pole)==fbc.pole; //Assert fbc.pole is actually an int
					if(nb+i<fb_b[Math.round(fbc.pole)]) {
						fb_b[Math.round(fbc.pole)]=-1;
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
	public void handleRenderAdd() {
		boolean done=false;
		while(!done) {
			done=true;
			for(int i=0;i<holding.size();i++) {
				if(r.cam_translation[2]+((this.gen_distance*(speed/speed0))*BosonX.cell_length) >= holding.get(i).zrel) {
					ArrayList<Cell> toremove=new ArrayList<Cell>();
					toremove.add(holding.get(i));
					for(Cell c : holding.get(i).getAhead()) {
						toremove.add(c);
					}
					for(int j=0;j<toremove.size();j++) {
						Cell c=toremove.get(j);
						c.rotate(this.pole);
						c.platform_id=j;
						c.platform_length=toremove.size();
						if(!c.rotationDefined) {c.initGeo();}
						if(c.zrel>(gen_distance*(speed/speed0))*BosonX.cell_length) {
							c.initGenerate();
							c.genc=0;
						}
						c.onRenderAdd();
						while(holding.remove(c)) {};
						r.celllist.add(c);
					}
					
					done=false;
					break;
				}
			}
		}
	}
	private int banlimit=6;
	private int banstart=0;
	private int allowedBreaks=0;
	private ArrayList<Integer> banned=new ArrayList<Integer>();
	public void addPattern() {
		int ridx=-1;//this.randint(cp.numPatterns()-1)+1;
		Pattern p=new Pattern(0);
		int bestRot=0;
		int numc=0;
		int t=0;
		int pos=0;
		int begs=0;
		boolean good=false;
		ArrayList<Integer> tried=new ArrayList<Integer>();
		if(strictGeneration) {
			while(!good) {
				good=true;
				ridx=this.randint(cp.numPatterns()-1)+1;
				while(contains(banned,ridx,banstart) || tried.contains(ridx)) {
					ridx=this.randint(cp.numPatterns()-1)+1;
					boolean containsAll=true;
					for(int i=1;i<cp.numPatterns();i++) {
						if(!tried.contains(i) && !banned.contains(i)) {
							containsAll=false;
						}
					}
					if(containsAll) {
						//Couldn't get an ideal pattern. Start removing banned indices.
						if(banstart>=3) {
							//Still no ideal patterns. (?!)
							allowedBreaks++;
							System.out.println("Allowing a discontinuity");
						} else {
							banstart++;
						}
						tried=new ArrayList<Integer>();
					}
				}
				tried.add(ridx);
				p=cp.parsePattern(ridx);
				p.enter=enter;
				p.initCells();
				numc=0;
				for(int r=0;r<p.numPoles+1;r++) {
					p.setRotation(r);
					int[] resp=checkContinuity(lastPattern,p);
					t=resp[0];
					pos=resp[1];
					begs=resp[2];
					if(t>numc) {
						numc=t;
						bestRot=r;
					}
				}
				if(numc<pos-allowedBreaks || begs>pos+3) {
					good=false;
				} else {
					p.setRotation(bestRot);
					allowedBreaks=0;
					banstart=0;
				}
			}
		} else {
			ridx=this.randint(cp.numPatterns()-1)+1;
			p=cp.parsePattern(ridx);
			p.enter=enter;
			p.initCells();
			for(int r=0;r<p.numPoles+1;r++) {
				p.setRotation(r);
				int[] resp=checkContinuity(lastPattern,p);
				t=resp[0];
				pos=resp[1];
				begs=resp[2];
				if(t>numc) {
					numc=t;
					bestRot=r;
				}
			}
		}
		banned.add(ridx);
		while(banned.size()>banlimit) {
			banned.remove(0);
		}
		enter+=p.rows.size();
		p.setRotation(bestRot);
		p.initGeo();
		lastPattern=p;
		p.addHoldingRoutine();
	}
	private boolean contains(ArrayList<Integer> a,int v,int start) {
		for(int i=start;i<a.size();i++) {
			if(a.get(i)==v) {
				return true;
			}
		}
		return false;
	}
	public void addPattern(int idx,int rot) {
		Pattern p=cp.parsePattern(idx);
		p.enter=enter;
		enter+=p.rows.size();
		//System.out.println(enter);
		p.initCells();
		p.rotate(rot+6);
		p.initGeo();
		lastPattern=p;
		p.addHoldingRoutine();
	}
	public int randint(int max) {
		return rand.nextInt(max);
	}
	public static FloatBuffer asFloatBuffer(float[] values) {
		FloatBuffer buffer=BufferUtils.createFloatBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}
	public void doFont(float size) {
		try {
			neuropol=Font.createFont(Font.TRUETYPE_FONT, new File("resources/neuropol.ttf")).deriveFont(size);
		    GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(neuropol);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
	}
	public void Main() {
		start_time=r.millis();
		
		doFont(30);
		r.Main();
	}
	public static void main(String[] args) {
		m=new BosonX();
		m.Main();
	}
}