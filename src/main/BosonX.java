package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.lwjgl.BufferUtils;

public class BosonX {
	
	//Instantaneous variables
	public float pole=0; //Our current "pole", i.e. rotation
	public float energy=0; //Energy
	public float speed=0;
	public float fr=60; //Frame rate
	public float frc=1; //Frame rate correction
	
	public float rc=1; //Resolution correction
	
	public float volume=1.0f;
	
	public boolean onE=false; //On an E platform
	
	//Level-specific constants
	public background.Bg background;
	public float gravity_i=0.27f; //Inner gravity
	public float gravity_o=0.5f; //Outer gravity
	public float jump_relative=0.2f; //Max jump velocity relative to grav
	public int jump_duration=135; //Jmp duration in 1/60 sec
	
	public float speed0=0.4f; //Speed, energy 0
	public float speed1=0.75f; //Speed, energy 100%
	
	public util.Vector4f P_color; //P platform color
	
	public util.Vector4f C_color1; //Collapsing colors
	public util.Vector4f C_color0;
	public float C_c;
	public float C_l;
	
	public util.Vector4f E_color1; //Energy colors
	public util.Vector4f E_color0;
	public float E_c;
	public float E_l;
	
	public float gen_distance=0;
	public float gen_width=0; //Not used
	
	public float energy_gain=0; //Gain per 1/60 sec
	
	public float F_speed=0;
	public float G_speed0=0;
	public float G_speed1=0;
	
	public boolean strictGeneration=false; //Whether we use a stricter gen algorithm
	
	public boolean p0_scalc=false; //Not used
	
	public boolean cellRotationalLighting=true;
	
	public boolean allowMultiJumps=false; //Always false
	public boolean beenInitialized=false; //Have we been inited
	//Universal, non-final constants
	public long start_time=0;
	
	//Constants
	public static final float cell_length=5f;
	public static final float cell_thickness=1f;
	public static final float background_gen=500f; //Particle gen distance
	public static final float particle_depth=30;
	public static final float B_height=12;
	public static final float B_depth=0;
	public static final int check_cells=4; //Generation pathfinding cell check distance
	public static final float incline=BosonX.depth(0)-BosonX.depth(0.5f); //Incline of I cell
	public static final float l_thickness=1000;
	
	public static final float depth(float level) {return 12.0f-0.8f*level;} //The depth in units of a given level
	
	public Renderer r=new Renderer();
	public Cellparser cp=new Cellparser();
	public static BosonX m;
	private Random rand=new Random();
	public int enter=0;
	private Pattern lastPattern;
	
	ArrayList<Character> walkable=new ArrayList<Character>();
	
	public ArrayList<Cell> holding=new ArrayList<Cell>(); //The pergatory realm for newly generated cells
	
	public void loadSpec(int level) {
		Spec.loadSpec(level);
		gen_cutoff=gen_distance;
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
	public boolean isWalkable(char a) { //Can I walk on it?
		for(char c : walkable) {
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
	public float gen_cutoff=gen_distance;
	public void handleRenderAdd() { //Add the contents of our pergatory to the render routine one by one
		boolean done=false;
		while(!done) {
			done=true;
			for(int i=0;i<holding.size();i++) { //We're going to break this for loop every time we find something.
				//The while loop will keep this going until we get an iteration where we find nothing.
				//Yes, it's a stupid way to avoid a ConcurrentModificationException, but it works, okay?
				if(r.cam_translation[2]+((this.gen_distance*(speed/speed0))*BosonX.cell_length) >= holding.get(i).zrel) {
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
						if(toremove.get(j).zrel>gen_cutoff*BosonX.cell_length) {
							idx=j;
							break;
						}
					}
					for(int j=0;j<toremove.size();j++) {
						Cell c=toremove.get(j); //Grab the cell in the platform
						c.rotate(this.pole); //Make sure we rotate it correctly
						c.platform_id=j-idx; //Give it its platform id while handling the generation boundary
						c.platform_length=toremove.size();
						if(!c.rotationDefined) {c.calculateConsts(); c.initGeo();} //Initialize some stuff
						if(c.zrel>gen_cutoff*BosonX.cell_length) {
							a=c.initGenerate(a); //Start generating
							c.genc=0; //Start the generation clock
						}
						a2=c.onRenderAdd(a2); //Call the onRenderAdd method and pass floats down the platform as we go
						while(holding.remove(c)) {}; //Make sure to remove all duplicates
						r.celllist.add(c); //Add to render routine
					}
					
					done=false;
					break;
				}
			}
		}
	}
	private int banlimit;
	private int banstart=0;
	private int allowedBreaks=0;
	private ArrayList<Integer> banned=new ArrayList<Integer>();
	public void addPattern() {
		int ridx=-1;
		Pattern p=new Pattern(0);
		int bestRot=0;
		int numc=0;
		int t=0;
		int pos=0;
		int begs=0;
		boolean good=false;
		ArrayList<Integer> tried=new ArrayList<Integer>(); //All of the patterns we have tried for strict generation
		if(strictGeneration) {
			banlimit=6;
			while(!good) {
				good=true; //Assume we're good
				ridx=this.randint(cp.numPatterns()-1)+1; //Get a new random pattern index
				while(this.contains(banned,ridx,banstart) || tried.contains(ridx)) { //Get candidate pattern indices until we've tried everything or it's not tried or banned
					ridx=this.randint(cp.numPatterns()-1)+1; //Generate new ridx
					boolean containsAll=true; //We'll say that tried contains all pattern indices
					for(int i=1;i<cp.numPatterns();i++) {
						if(!tried.contains(i) && !banned.contains(i)) {
							containsAll=false; //If something isn't contained, than containsAll is false.
							break;
						}
					}
					if(containsAll) {
						//We couldn't get an ideal pattern. Start shifting our banned indices
						if(banstart>=3) {
							//Still no ideal patterns. (?!)
							allowedBreaks++;
							System.out.println("Allowing a discontinuity"); //Start allowing breaks
						} else {
							banstart++;
						}
						tried=new ArrayList<Integer>(); //Clear our tried list
					}
				}
				tried.add(ridx);
				p=cp.parsePattern(ridx); //Get a new pattern
				p.enter=enter; //initialize it
				p.initCells();
				numc=0;
				for(int r=0;r<p.numPoles+1;r++) { //Try out all its rotations. Which one works best?
					p.setRotation(r);
					int[] resp=new int[0];
					try {
						resp=checkContinuity(lastPattern,p);
					} catch(IndexOutOfBoundsException e) {
						e.printStackTrace();
						System.out.println("On pattern "+ridx+":");
						audio.Audio.cleanUp();
						System.exit(1);
					}
					t=resp[0];
					pos=resp[1];
					begs=resp[2];
					if(t>numc) {
						numc=t;
						bestRot=r;
					}
				}
				if(numc<pos-allowedBreaks || begs>pos+3) {
					good=false; //Not a good enough pattern
				} else {
					p.setRotation(bestRot); //Yay! We found it!
					allowedBreaks=0;
					banstart=0;
				}
			}
		} else { //No strict generation:
			if(cp.numPatterns()<6+2) { //In case we have few patterns
				banlimit=0;
			} else {
				banlimit=6;
			}
			ridx=this.randint(cp.numPatterns()-1)+1;
			while(banned.contains(ridx)) { //Generate a random number not in banned
				ridx=this.randint(cp.numPatterns()-1)+1;
			}
			p=cp.parsePattern(ridx);
			p.enter=enter;
			p.initCells();
			for(int r=0;r<p.numPoles+1;r++) { //Hit me with your best rot(ation)
				p.setRotation(r);
				int[] resp=checkContinuity(lastPattern,p);
				t=resp[0];
				pos=resp[1];
				begs=resp[2];
				if(t>numc) {
					numc=t;
					bestRot=r;
				} else if(t==numc) {
					if(randint(2)==0) {
						bestRot=r;
					}
				}
			}
		}
		banned.add(ridx);
		while(banned.size()>banlimit) { //Maintain banned size
			banned.remove(0);
		}
		//We accept this pattern
		enter+=p.rows.size(); //Increment our cell pointer
		p.setRotation(bestRot); //Give it our best rot
		p.initGeo();
		//Initialize stuff
		lastPattern=p;
		p.addHoldingRoutine(); //Add it to our pergatory
	}
	private boolean contains(ArrayList<Integer> a,int v,int start) { //Self explanatory
		for(int i=start;i<a.size();i++) {
			if(a.get(i)==v) {
				return true;
			}
		}
		return false;
	}
	public void addPattern(int idx,int rot) { //Manual adding
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
	public void addAdapter(int len) { //Add a long platform at pole 0
		Pattern p=genAdapter(cp.parsePattern(0).numPoles,len);
		p.enter=enter;
		enter+=p.rows.size();
		//System.out.println(enter);
		p.initCells();
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
	public String externalPath;
	public ScoreRecord scores;
	public void Main() { //Non-static main
		walkable.add('P');
		walkable.add('E');
		walkable.add('C');
		walkable.add('F');
		walkable.add('G');
		walkable.add('I');
		walkable.add('D');
		walkable.add('L');
		start_time=r.millis();
		File jarpath=new File(BosonX.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		externalPath=jarpath.getParentFile().getAbsolutePath();
		FileInputStream scores_file=null;
		try {
			scores_file=new FileInputStream(externalPath.replace("\\","/")+"/itstate.scrr");
		} catch (FileNotFoundException e) {
			System.out.println("Score file not found. Creating a new one.");
			scores=ScoreRecord.template();
			File scr_f=new File(externalPath.replace("\\","/")+"/itstate.scrr");
			try {
				scr_f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
				System.exit(1);
			}
			FileOutputStream scr_output=null;
			try {
				scr_output=new FileOutputStream(externalPath.replace("\\","/")+"/itstate.scrr");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				System.exit(1);
			}
			try {
				ObjectOutputStream scr_out=new ObjectOutputStream(scr_output);
				scr_out.writeObject(scores);
				scr_out.close();
				scr_output.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				System.exit(1);
			}
			
			try {
				scores_file=new FileInputStream(externalPath.replace("\\","/")+"/itstate.scrr");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				System.exit(1);
			}
		}
		try {
			ObjectInputStream scr_in=new ObjectInputStream(scores_file);
			scores=(ScoreRecord) scr_in.readObject();
			System.out.println("Score file found, ready to go.");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		r.Main();
		System.out.println("Bye");
	}
	public static void main(String[] args) { //----------------------------main--------------------------------//
		m=new BosonX();
		m.Main();
	}
}