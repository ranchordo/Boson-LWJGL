package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class BosonX {
	
	//Instantaneous variables
	public float pole=0; //Our current "pole", i.e. rotation
	public float energy=0; //Energy
	public float speed=0;
	public float fr=60; //Frame rate
	public float frc=1; //Frame rate correction
	public long fp=1; //Milliseconds between frames
	public float aspectRatio=16.0f/9.0f;
	public float hrc=1;
	public float wrc=1;
	public float mrc=1;
	public float gen_cutoff;
	
	public float volume=1.0f;
	
	public boolean onE=false; //On an E platform
	
	//Universal, non-final constants
	public long start_time=0;
	
	//Levelsettingsobject
	public static LevelSettingsObject active=new LevelSettingsObject();
	
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
	public int enter=0;
	public TerrainGenerator tergen=new TerrainGenerator();
	
	ArrayList<Character> walkable=new ArrayList<Character>();
	
	
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