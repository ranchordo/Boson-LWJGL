package main;

import java.io.*;
import java.util.*;


public class Cellparser {
	String f_contents="";
	public int level;
	public int numpoles=0;
	public void point(int stage) { //Fetch the next pattern file
		level=stage;
		f_contents="";
		InputStream f=BosonX.class.getResourceAsStream("/patterns_"+Integer.toString(stage)+".txt");
		//InputStream f=BosonX.class.getResourceAsStream("/patterns_jon.txt");
		Scanner s=new Scanner(f);
		while(s.hasNextLine()) {
			String ln=s.nextLine();
			f_contents=f_contents+ln+"\n";
		}
		s.close();
		this.numpoles=this.parsePattern(0).numPoles;
	}
	public ArrayList<Pattern> parsePatterns() { //Get a list of patterns in our file (This is boooring parsing work)
		assert !f_contents.equals("");
		
		String[] patternstrs=f_contents.split("\n;\n");
		ArrayList<Pattern> patterns=new ArrayList<Pattern>();
		
		for(String pstr : patternstrs) {
			
			int numpoles=-1;
			for(char i : pstr.split("\n",0)[0].toCharArray()) {if(i=='|') {numpoles++;}}
			assert numpoles>1;
			Pattern toapp=new Pattern(numpoles);
			String[] rstrs=pstr.split("\n");
			for(int r=0;r<rstrs.length;r++) {
				
				String[] cells_bad=rstrs[r].split("\\|");
				String[] cellstrs=Arrays.copyOfRange(cells_bad,1,cells_bad.length);
				Cell[] cells=new Cell[numpoles];
				
				for(int ci=0;ci<cellstrs.length;ci++) {
					
					Cell t;
					if(cellstrs[ci].equals("  ")) {
						t=new Cell(ci,0,'N');
						cells[ci]=t;
					} else if(cellstrs[ci].length()==2) {
						char type=cellstrs[ci].toCharArray()[0];
						int levelstr;
						try {
							levelstr=Integer.parseInt(Character.toString(cellstrs[ci].toCharArray()[1]));
						} catch(NumberFormatException e) {
							levelstr=0;
						}
						t=new Cell(ci,levelstr,type);
						cells[ci]=t;
					} else if(cellstrs[ci].length()==1) {
						if(cellstrs[ci].toCharArray()[0]=='#') {
							toapp.specials.add(new Cell(ci,0,'#'));
							toapp.specialIDs.add(r);
						}
					}
				}
				toapp.rows.add(cells);
			}
			Collections.reverse(toapp.rows);
			patterns.add(toapp);
		}
		return patterns;
	}
	public Pattern parsePattern(int p) { //Parse a specific pattern from an index
		assert !f_contents.equals("");
		String[] pstrs=f_contents.split("\n;\n");
		return pstr(pstrs[p]);
	}
	public Cell[] frow(int p) { //First row of a pattern
		assert !f_contents.equals("");
		String[] pstrs=f_contents.split("\n;\n");
		int numpoles=-1;
		for(char i : pstrs[0].split("\n",0)[0].toCharArray()) {if(i=='|') {numpoles++;}}
		assert numpoles>1;
		String[] rstrs=pstrs[p].split("\n");
		
		String[] cells_bad=rstrs[0].split("\\|");
		String[] cellstrs=Arrays.copyOfRange(cells_bad,1,cells_bad.length);
		Cell[] cells=new Cell[numpoles];
		
		for(int ci=0;ci<cellstrs.length;ci++) {
			
			Cell t;
			if(cellstrs[ci].equals("  ")) {
				t=new Cell(ci,0,'N');
				cells[ci]=t;
			} else if(cellstrs[ci].length()==2) {
				char type=cellstrs[ci].toCharArray()[0];
				int levelstr;
				try {
					levelstr=Integer.parseInt(Character.toString(cellstrs[ci].toCharArray()[1]));
				} catch(NumberFormatException e) {
					levelstr=0;
				}
				t=new Cell(ci,levelstr,type);
				cells[ci]=t;
			}
		}
		return cells;
	}
	public Cell[] lrow(int p) { //Last row of a pattern
		assert !f_contents.equals("");
		String[] pstrs=f_contents.split("\n;\n");
		int numpoles=-1;
		for(char i : pstrs[0].split("\n",0)[0].toCharArray()) {if(i=='|') {numpoles++;}}
		assert numpoles>1;
		String[] rstrs=pstrs[p].split("\n");
		
		String[] cells_bad=rstrs[rstrs.length-1].split("\\|");
		String[] cellstrs=Arrays.copyOfRange(cells_bad,1,cells_bad.length);
		Cell[] cells=new Cell[numpoles];
		
		for(int ci=0;ci<cellstrs.length;ci++) {
			
			Cell t;
			if(cellstrs[ci].equals("  ")) {
				t=new Cell(ci,0,'N');
				cells[ci]=t;
			} else if(cellstrs[ci].length()==2) {
				char type=cellstrs[ci].toCharArray()[0];
				int levelstr;
				try {
					levelstr=Integer.parseInt(Character.toString(cellstrs[ci].toCharArray()[1]));
				} catch(NumberFormatException e) {
					levelstr=0;
				}
				t=new Cell(ci,levelstr,type);
				cells[ci]=t;
			}
		}
		return cells;
	}
	public int numPatterns() { //Number of patterns
		assert !f_contents.equals("");
		return f_contents.split("\n;\n").length;
	}
	public Pattern pstr(String pstr) { //Parse a single pattern string (boooooring parsing)
		int numpoles=-1;
		for(char i : pstr.split("\n",0)[0].toCharArray()) {if(i=='|') {numpoles++;}}
		assert numpoles>1;
		Pattern toapp=new Pattern(numpoles);
		String[] rstrs=pstr.split("\n");
		for(int r=0;r<rstrs.length;r++) {
			
			String[] cells_bad=rstrs[r].split("\\|");
			String[] cellstrs=Arrays.copyOfRange(cells_bad,1,cells_bad.length);
			Cell[] cells=new Cell[numpoles];
			
			for(int ci=0;ci<cellstrs.length;ci++) {
				
				Cell t;
				if(cellstrs[ci].equals("  ")) {
					t=new Cell(ci,0,'N');
					cells[ci]=t;
				} else if(cellstrs[ci].length()==2) {
					char type=cellstrs[ci].toCharArray()[0];
					int levelstr;
					try {
						levelstr=Integer.parseInt(Character.toString(cellstrs[ci].toCharArray()[1]));
					} catch(NumberFormatException e) {
						levelstr=0;
					}
					t=new Cell(ci,levelstr,type);
					cells[ci]=t;
				} else if(cellstrs[ci].length()==1) {
					if(cellstrs[ci].toCharArray()[0]=='#') {
						toapp.specials.add(new Cell(ci,0,'#'));
						toapp.specialIDs.add(r);
					}
				}
			}
			toapp.rows.add(cells);
		}
		Collections.reverse(toapp.rows);
		return toapp;
	}
}
