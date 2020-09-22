package main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreRecord implements java.io.Serializable {
	private static final long serialVersionUID = 4308428021568288363L;
	public HashMap<Integer,ArrayList<Float>> scores=new HashMap<Integer,ArrayList<Float>>();
	
	public int limit=20;
	public HashMap<Integer,Float> peaks=new HashMap<Integer,Float>();
	//Settings list:
	/*
	 * Volume
	 * Resolution
	 * Tutorial
	 * Frame rate limit
	 * 
	 */
	public static ScoreRecord template() {
		ScoreRecord ret=new ScoreRecord();
		for(int i=1;i<=18;i++) {
			ArrayList<Float> fs=new ArrayList<Float>();
			for(int k=0;k<20;k++) {
				fs.add(0.0f);
			}
			ret.scores.put(i,fs);
			ret.peaks.put(i,0.0f);
		}
		return ret;
	}
	public void applyLimit() {
		for(Map.Entry<Integer,ArrayList<Float>> e : scores.entrySet()) {
			while(e.getValue().size()>this.limit) {
				e.getValue().remove(0);
			}
		}
	}
	public void doPeak() {
		for(Map.Entry<Integer,ArrayList<Float>> e : scores.entrySet()) {
			float m=0;
			for(float o : e.getValue()) {
				if(o>m) {
					m=o;
				}
			}
			if(m>peaks.get(e.getKey())) {
				peaks.put(e.getKey(),m);
			}
		}
	}
	public void output() {
		try {
			FileOutputStream scr_output=new FileOutputStream(BosonX.m.externalPath.replace("\\","/")+"/itstate.scrr");
			ObjectOutputStream scr_out=new ObjectOutputStream(scr_output);
			scr_out.writeObject(this);
			scr_out.close();
			scr_output.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	public void put(int level, float energy) {
		scores.get(level).add(energy);
	}
	
}
