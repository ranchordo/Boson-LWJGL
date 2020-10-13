package main;

import java.util.ArrayList;
import util.Util;

public class GeneratorThread extends Thread {
	static boolean contains(ArrayList<Integer> a,int v,int start) { //Self explanatory
		for(int i=start;i<a.size();i++) {
			if(a.get(i)==v) {
				return true;
			}
		}
		return false;
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
		if(BosonX.active.strictGeneration) {
			banlimit=6;
			while(!good) {
				good=true; //Assume we're good
				ridx=Util.randint(BosonX.m.cp.numPatterns()-1)+1; //Get a new random pattern index
				while(GeneratorThread.contains(banned,ridx,banstart) || tried.contains(ridx)) { //Get candidate pattern indices until we've tried everything or it's not tried or banned
					ridx=Util.randint(BosonX.m.cp.numPatterns()-1)+1; //Generate new ridx
					boolean containsAll=true; //We'll say that tried contains all pattern indices
					for(int i=1;i<BosonX.m.cp.numPatterns();i++) {
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
				p=BosonX.m.cp.parsePattern(ridx); //Get a new pattern
				p.enter=BosonX.m.enter; //initialize it
				p.initCells();
				numc=0;
				for(int r=0;r<p.numPoles+1;r++) { //Try out all its rotations. Which one works best?
					p.setRotation(r);
					int[] resp=new int[0];
					try {
						resp=BosonX.m.tergen.checkContinuity(BosonX.m.tergen.lastPattern,p);
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
			if(BosonX.m.cp.numPatterns()<6+2) { //In case we have few patterns
				banlimit=0;
			} else {
				banlimit=6;
			}
			ridx=Util.randint(BosonX.m.cp.numPatterns()-1)+1;
			while(banned.contains(ridx)) { //Generate a random number not in banned
				ridx=Util.randint(BosonX.m.cp.numPatterns()-1)+1;
			}
			p=BosonX.m.cp.parsePattern(ridx);
			p.enter=BosonX.m.enter;
			p.initCells();
			for(int r=0;r<p.numPoles+1;r++) { //Hit me with your best rot(ation)
				p.setRotation(r);
				int[] resp=BosonX.m.tergen.checkContinuity(BosonX.m.tergen.lastPattern,p);
				t=resp[0];
				pos=resp[1];
				begs=resp[2];
				if(t>numc) {
					numc=t;
					bestRot=r;
				} else if(t==numc) {
					if(Util.randint(2)==0) {
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
		BosonX.m.enter+=p.rows.size(); //Increment our cell pointer
		p.setRotation(bestRot); //Give it our best rot
		p.initGeo();
		//Initialize stuff
		BosonX.m.tergen.lastPattern=p;
		p.addHoldingRoutine(); //Add it to our pergatory
		this.mt=null;
	}
	
	private Thread mt;
	
	public void run() {
		this.addPattern();
	}
	public void start() {
		if(mt==null) {
			mt=new Thread(this);
			mt.start();
		}
	}
	
}
