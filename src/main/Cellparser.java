package main;

import java.io.*;
import java.util.*;


public class Cellparser {
	String f_contents="";
	public int level;
	public void point(int stage) {
		level=stage;
		f_contents="";
		try {
			File f=new File("resources/patterns_"+Integer.toString(stage)+".txt");
			Scanner s=new Scanner(f);
			while(s.hasNextLine()) {
				String ln=s.nextLine();
				f_contents=f_contents+ln+"\n";
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error grabbing data from pattern files!");
			System.exit(1);
		}
	}
	public ArrayList<Pattern> parsePatterns() {
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
	public Pattern parsePattern(int p) {
		assert !f_contents.equals("");
		String[] pstrs=f_contents.split("\n;\n");
		return pstr(pstrs[p]);
	}
	public Cell[] frow(int p) {
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
	public Cell[] lrow(int p) {
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
	public int numPatterns() {
		assert !f_contents.equals("");
		return f_contents.split("\n;\n").length;
	}
	public Pattern pstr(String pstr) {
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
