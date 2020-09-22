package main;
import java.util.*;

public class Pattern {
	int numPoles;
	ArrayList<Cell[]> rows;
	ArrayList<Cell> specials;
	ArrayList<Integer> specialIDs;
	int enter;
	public Pattern(int np) {
		numPoles=np;
		rows=new ArrayList<Cell[]>();
		specials=new ArrayList<Cell>();
		specialIDs=new ArrayList<Integer>();
	}
	public void initCells() { //Do our init routine for specials and normals alike
		for(int i=0;i<rows.size();i++) {
			for(Cell c1 : rows.get(i)) {
				c1.numpoles=numPoles;
				c1.enter=enter;
				c1.zrel_id=i+enter;
				c1.calculateConsts();
			}
		}
		assert specials.size()==specialIDs.size();
		for(int i=0;i<specials.size();i++) {
			specials.get(i).enter=enter;
			specials.get(i).numpoles=numPoles;
			specials.get(i).zrel_id=specialIDs.get(i)+enter;
			specials.get(i).calculateConsts();
		}
	}
	public void rotate(float p) { //These are methods that just do the same thing on every cell
		for(Cell[] c : rows) {
			for(Cell c1 : c) {
				c1.rotate(p);
			}
		}
	}
	public void setRotation(float p) {
		for(Cell[] c : rows) {
			for(Cell c1 : c) {
				c1.setRotation(p);
			}
		}
	}
	public void initGeo() {
		for(int i=0;i<rows.size();i++) {
			for(Cell c1 : rows.get(i)) {
				c1.initGeo();
			}
		}
		for(Cell c1 : specials) {
			c1.initGeo();
		}
	}
	public void addHoldingRoutine() { //Add to our pergatory in main.BosonX
		for(int ci=0;ci<rows.size();ci++) {
			for(Cell c1 : rows.get(ci)) {
				if(c1.geometry.quads.size()!=0) {BosonX.m.holding.add(c1);}
				//System.out.println(c1.enter);
			}
		}
		for(Cell c1 : specials) {
			BosonX.m.holding.add(c1);
		}
	}
	
}
