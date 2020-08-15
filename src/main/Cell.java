package main;

import java.util.ArrayList;

public class Cell {
	public float pole;
	public float initPole;
	public char type;
	public float level;
	public float zrel;
	public int zrel_id;
	public float width_o; //Half width, outer
	public float width_i; //Half width, inner
	public util.GObject geometry;
	public int numpoles;
	public int enter;
	public boolean collapsing=false;
	public int genc=-1;
	
	public float rz=0;
	
	public int platform_id;
	public int platform_length;
	
	public boolean rotationDefined=false;
	
	//Type specific
	private float rotationSpeed;
	private float rotation;
	
	private float ztSpeed=0;
	
	//Generation variables
	public float gpfgen1=0;
	public float gpfgen2=0;
	public boolean gpbgen1=false;
	public boolean gpbgen2=false;
	
	public boolean doneGenerating=true;
	

	public Cell(int pole, int level, char type) {
		this.pole=pole;
		this.initPole=pole;
		this.level=level;
		this.type=type;
	}
	public void rotate(float p) {
		pole+=p;
		while(pole>=numpoles) {pole-=numpoles;}
		while(pole<0) {pole+=numpoles;}
	}
	public void setRotation(float p) {
		pole=p+initPole;
		while(pole>=numpoles) {pole-=numpoles;}
		while(pole<0) {pole+=numpoles;}
	}
	public void calculateConsts() {
		width_o=(float) Math.tan(Math.toRadians(180.0f/numpoles))*(BosonX.depth(level)+BosonX.cell_thickness);
		if(this.type=='B') {
			width_o=(float) Math.tan(Math.toRadians(180.0f/numpoles))*(BosonX.depth(level));
		}
		width_i=(float) Math.tan(Math.toRadians(180.0f/numpoles))*(BosonX.depth(level));
		if(this.type=='B') {
			width_i=(float) Math.tan(Math.toRadians(180.0f/numpoles))*(BosonX.depth(BosonX.B_height));
		}
		zrel=BosonX.cell_length*zrel_id;
		rotationSpeed=((float)BosonX.m.randint(1400)-700)/1000f;
		geometry=new util.GObject(Integer.toString(enter)+":"+Integer.toString(zrel_id-enter),1);
	}
	public void initGeo() {
		geometry.quads=new ArrayList<util.Quad>();
		geometry.tris=new ArrayList<util.Tri>();
		switch (type) {
		case 'P':

			geometry.quads.add(new util.Quad(
					width_i,-BosonX.depth(level),zrel,
					-width_i,-BosonX.depth(level),zrel,
					-width_i,-BosonX.depth(level),zrel+BosonX.cell_length,
					width_i,-BosonX.depth(level),zrel+BosonX.cell_length)
					.setNormal(0, 1, 0, true)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length)
					.setNormal(0, -1, 0, true)
					);

			geometry.quads.add(new util.Quad(
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					-width_i,-BosonX.depth(level),zrel+BosonX.cell_length,
					-width_i,-BosonX.depth(level),zrel)
					.autoNormal(-1, 0, 0)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					width_i,-BosonX.depth(level),zrel+BosonX.cell_length,
					width_i,-BosonX.depth(level),zrel)
					.autoNormal(1, 0, 0)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_i,-BosonX.depth(level),zrel,
					width_i,-BosonX.depth(level),zrel)
					.autoNormal(0, 0,-1)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					-width_i,-BosonX.depth(level),zrel+BosonX.cell_length,
					width_i,-BosonX.depth(level),zrel+BosonX.cell_length)
					.autoNormal(0, 0, 1)
					);
			geometry.setColor(BosonX.m.P_color.x,BosonX.m.P_color.y,BosonX.m.P_color.z);
			geometry.lighting=BosonX.m.P_color.w;

			geometry.setRotation(0,0,(360.0f/numpoles)*pole);
			break;
		case 'E':
			geometry.quads.add(new util.Quad(
					width_i,-BosonX.depth(level),zrel,
					-width_i,-BosonX.depth(level),zrel,
					-width_i,-BosonX.depth(level),zrel+BosonX.cell_length,
					width_i,-BosonX.depth(level),zrel+BosonX.cell_length)
					.setNormal(0, 1, 0, true)
					);

			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length)
					.setNormal(0,-1,0,true)
					);

			geometry.quads.add(new util.Quad(
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					-width_i,-BosonX.depth(level),zrel+BosonX.cell_length,
					-width_i,-BosonX.depth(level),zrel)
					.autoNormal(-1,0,0)
					);

			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					width_i,-BosonX.depth(level),zrel+BosonX.cell_length,
					width_i,-BosonX.depth(level),zrel)
					.autoNormal(1, 0, 0)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_i,-BosonX.depth(level),zrel,
					width_i,-BosonX.depth(level),zrel)
					.autoNormal(0,0,-1)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					-width_i,-BosonX.depth(level),zrel+BosonX.cell_length,
					width_i,-BosonX.depth(level),zrel+BosonX.cell_length)
					.autoNormal(0, 0, 1)
					);
			doColors();

			geometry.setRotation(0,0,(360.0f/numpoles)*pole);
			break;
		case 'C':
			geometry.quads.add(new util.Quad(
					width_i,-BosonX.depth(level),zrel,
					-width_i,-BosonX.depth(level),zrel,
					-width_i,-BosonX.depth(level),zrel+BosonX.cell_length,
					width_i,-BosonX.depth(level),zrel+BosonX.cell_length)
					.setNormal(0, 1, 0, true)
					);

			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length)
					.setNormal(0,-1,0,true)
					);

			geometry.quads.add(new util.Quad(
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					-width_i,-BosonX.depth(level),zrel+BosonX.cell_length,
					-width_i,-BosonX.depth(level),zrel)
					.autoNormal(-1,0,0)
					);

			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					width_i,-BosonX.depth(level),zrel+BosonX.cell_length,
					width_i,-BosonX.depth(level),zrel)
					.autoNormal(1, 0, 0)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_i,-BosonX.depth(level),zrel,
					width_i,-BosonX.depth(level),zrel)
					.autoNormal(0,0,-1)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					-width_i,-BosonX.depth(level),zrel+BosonX.cell_length,
					width_i,-BosonX.depth(level),zrel+BosonX.cell_length)
					.autoNormal(0, 0, 1)
					);
			doColors();

			geometry.setRotation(0,0,(360.0f/numpoles)*pole);
			break;
		case 'N':
			break;
		case 'B':
			geometry.quads.add(new util.Quad(
					width_i,-BosonX.depth(BosonX.B_height),zrel,
					-width_i,-BosonX.depth(BosonX.B_height),zrel,
					-width_i,-BosonX.depth(BosonX.B_height),zrel+BosonX.cell_length,
					width_i,-BosonX.depth(BosonX.B_height),zrel+BosonX.cell_length)
					.setNormal(0, 1, 0, true)
					);

			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level),zrel,
					-width_o,-BosonX.depth(level),zrel,
					-width_o,-BosonX.depth(level),zrel+BosonX.cell_length,
					width_o,-BosonX.depth(level),zrel+BosonX.cell_length)
					.setNormal(0,-1,0,true)
					);

			geometry.quads.add(new util.Quad(
					-width_o,-BosonX.depth(level),zrel,
					-width_o,-BosonX.depth(level),zrel+BosonX.cell_length,
					-width_i,-BosonX.depth(BosonX.B_height),zrel+BosonX.cell_length,
					-width_i,-BosonX.depth(BosonX.B_height),zrel)
					.autoNormal(-1,0,0)
					);

			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level),zrel,
					width_o,-BosonX.depth(level),zrel+BosonX.cell_length,
					width_i,-BosonX.depth(BosonX.B_height),zrel+BosonX.cell_length,
					width_i,-BosonX.depth(BosonX.B_height),zrel)
					.autoNormal(1, 0, 0)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level),zrel,
					-width_o,-BosonX.depth(level),zrel,
					-width_i,-BosonX.depth(BosonX.B_height),zrel,
					width_i,-BosonX.depth(BosonX.B_height),zrel)
					.autoNormal(0,0,-1)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level),zrel+BosonX.cell_length,
					-width_o,-BosonX.depth(level),zrel+BosonX.cell_length,
					-width_i,-BosonX.depth(BosonX.B_height),zrel+BosonX.cell_length,
					width_i,-BosonX.depth(BosonX.B_height),zrel+BosonX.cell_length)
					.autoNormal(0, 0, 1)
					);
			geometry.setColor(BosonX.m.P_color.x,BosonX.m.P_color.y,BosonX.m.P_color.z);
			geometry.lighting=BosonX.m.P_color.w;

			geometry.setRotation(0,0,(360.0f/numpoles)*pole);
			break;
		case 'F':
			geometry.quads.add(new util.Quad(
					width_i,-BosonX.depth(level),zrel,
					-width_i,-BosonX.depth(level),zrel,
					-width_i,-BosonX.depth(level),zrel+BosonX.cell_length,
					width_i,-BosonX.depth(level),zrel+BosonX.cell_length)
					.setNormal(0, 1, 0, true)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length)
					.setNormal(0, -1, 0, true)
					);

			geometry.quads.add(new util.Quad(
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					-width_i,-BosonX.depth(level),zrel+BosonX.cell_length,
					-width_i,-BosonX.depth(level),zrel)
					.autoNormal(-1, 0, 0)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					width_i,-BosonX.depth(level),zrel+BosonX.cell_length,
					width_i,-BosonX.depth(level),zrel)
					.autoNormal(1, 0, 0)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_i,-BosonX.depth(level),zrel,
					width_i,-BosonX.depth(level),zrel)
					.autoNormal(0, 0,-1)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+BosonX.cell_length,
					-width_i,-BosonX.depth(level),zrel+BosonX.cell_length,
					width_i,-BosonX.depth(level),zrel+BosonX.cell_length)
					.autoNormal(0, 0, 1)
					);
			geometry.setColor(BosonX.m.P_color.x,BosonX.m.P_color.y,BosonX.m.P_color.z);
			geometry.lighting=BosonX.m.P_color.w;

			geometry.setRotation(0,0,(360.0f/numpoles)*pole);
			break;
		case '#':
			rotationDefined=true;
			geometry.loadOBJ("resources/cannon_outer.obj");
			geometry.scale(15,15,3);
			geometry.translate(0, 0, zrel);
			geometry.setColor(BosonX.m.P_color.x,BosonX.m.P_color.y,BosonX.m.P_color.z);
			geometry.lighting=BosonX.m.P_color.w;
			util.GObject inner=new util.GObject(1);
			inner.loadOBJ("resources/cannon_inner.obj");
			inner.setColor(1, 1, 1);
			geometry.append(inner);
			geometry.quads.add(new util.Quad(-1,0.02f,0, 1,0.02f,0, 1,-0.02f,0, -1,-0.02f,0).setColor(5, 5, 5).setNormal(0,1,0,true));
			break;
		default:
			System.out.print("Type ");
			System.out.print(type);
			System.out.println(" not recognized.");
			//System.exit(1);
		}
		handleRZ();
	}
	public void onRenderAdd() {
		switch(type) {
		case 'F':
			if(BosonX.m.p0_scalc) {
				ztSpeed=BosonX.m.F_speed;
				float timeToDest=(((zrel_id-platform_id)*BosonX.cell_length)-BosonX.m.r.cam_translation[2])/BosonX.m.speed;
				float zt_init=timeToDest*ztSpeed;
				zrel+=zt_init;
			} else {
				ztSpeed=BosonX.m.F_speed;
				float timeToDest=(((zrel_id+(platform_length-platform_id))*BosonX.cell_length)-BosonX.m.r.cam_translation[2])/BosonX.m.speed;
				float zt_init=timeToDest*ztSpeed;
				zrel+=zt_init;
			}
			break;
		}
	}
	public void resetColors() {
		switch(type) {
		case 'P':
			geometry.setColor(BosonX.m.P_color.x,BosonX.m.P_color.y,BosonX.m.P_color.z);
			geometry.lighting=BosonX.m.P_color.w;
			break;
		case 'E':
			doColors();
			break;
		case 'C':
			doColors();
			break;
		case 'B':
			geometry.setColor(BosonX.m.P_color.x,BosonX.m.P_color.y,BosonX.m.P_color.z);
			geometry.lighting=BosonX.m.P_color.w;
		default:
			System.out.print("Type ");
			System.out.print(type);
			System.out.println(" not recognized.");
		}
	}
	public char handleEffects(float cam_z, boolean a) {
		switch(type) {
		case 'C':
			doColors();
			if(this.collapsing) {
				level-=0.1f;
				initGeo();
			}
			if((BosonX.m.r.cam_translation[2]>this.zrel-0.2 && BosonX.m.r.cam_translation[2]<this.zrel+0.4+BosonX.cell_length && (pole<0.5 || pole>numpoles-0.5) && BosonX.m.r.on)||a) {
				this.collapsing=true;
				for(Cell c : BosonX.m.r.celllist) {
					if(c.level==this.level && c.pole==this.pole && c.zrel_id==this.zrel_id+1 && c.type=='C') {
						c.handleEffects(cam_z, true);
					}
				}
			}
			break;
		case 'E':
			doColors();
			if(BosonX.m.r.cam_translation[2]>this.zrel-0.2 && BosonX.m.r.cam_translation[2]<this.zrel+0.4+BosonX.cell_length && (this.pole<0.5 || this.pole>numpoles-0.5) && BosonX.m.r.on) {
				BosonX.m.energy+=BosonX.m.energy_gain;
			}
			break;
		case '#':
			rotation+=rotationSpeed;
			geometry.setRotation(0, 0, (360.0f/numpoles)*pole+rotation);
			break;
		case 'F':
			zrel-=ztSpeed;
			initGeo();
			break;
		}
		generate(false);
		
		
		if(BosonX.m.r.cam_translation[2]>this.zrel-0.2 && BosonX.m.r.cam_translation[2]<this.zrel+0.4+BosonX.cell_length && Math.floor(this.pole)==0 && BosonX.m.r.on) {
			return this.type;
		} else {
			return '!';
		}
		
	}
	
	public void doColors() {
		switch(type) {
		case 'E':
			float fe=(float) ((1.0f/(1+Math.exp(-BosonX.m.E_l*Math.cos(2*(1.0f/BosonX.m.E_c)*Math.PI*((int) (BosonX.m.r.millis()-BosonX.m.start_time)))))  -  1.0f/(1+Math.exp(BosonX.m.E_l)))
			* (1.0f/(1.0f/(1.0f+Math.exp(-BosonX.m.E_l)) - 1.0f/(1.0f+Math.exp(BosonX.m.E_l)))));
			
			float re=(BosonX.m.E_color1.x-BosonX.m.E_color0.x)*fe+BosonX.m.E_color0.x;
			float ge=(BosonX.m.E_color1.y-BosonX.m.E_color0.y)*fe+BosonX.m.E_color0.y;
			float be=(BosonX.m.E_color1.z-BosonX.m.E_color0.z)*fe+BosonX.m.E_color0.z;
			
			geometry.setColor(re, ge, be);
			geometry.lighting=BosonX.m.E_color0.w;
			break;
		case 'C':
			float fc=(float) ((1.0f/(1+Math.exp(-BosonX.m.C_l*Math.cos(2*(1.0f/BosonX.m.C_c)*Math.PI*((int) (BosonX.m.r.millis()-BosonX.m.start_time)))))  -  1.0f/(1+Math.exp(BosonX.m.C_l)))
			* (1.0f/(1.0f/(1.0f+Math.exp(-BosonX.m.C_l)) - 1.0f/(1.0f+Math.exp(BosonX.m.C_l)))));
			
			float rc=(BosonX.m.C_color1.x-BosonX.m.C_color0.x)*fc+BosonX.m.C_color0.x;
			float gc=(BosonX.m.C_color1.y-BosonX.m.C_color0.y)*fc+BosonX.m.C_color0.y;
			float bc=(BosonX.m.C_color1.z-BosonX.m.C_color0.z)*fc+BosonX.m.C_color0.z;
			geometry.setColor(rc, gc, bc);
			geometry.lighting=BosonX.m.C_color0.w;
			break;
		}
	}
	public void initGenerate() {
		doneGenerating=false;
		switch(BosonX.m.cp.level) {
		case 1:
			geometry.translation[2]=200;
			break;
		case 2:
			gpfgen1=level; //Save our initial level
			level-=20;
			break;
		case 3:
			if(type!='#') {
				rotate(-5);
				if(!rotationDefined) {initGeo();}
				gpfgen2=-5; //This will store our offset
			}
			break;
		case 4:
			if(type=='B') {
				rotate(-3);
				if(!rotationDefined) {initGeo();}
				gpfgen2=-3;
			} else {
				gpfgen1=level;
				level-=20;
			}
			break;
		case 5:
			gpfgen1=level;
			level=-20;
			break;
		case 6:
			gpfgen1=level;
			rz=360;
			level=-20;
			break;
		}
	}
	public ArrayList<Cell> getAhead() {
		ArrayList<Cell> ret=new ArrayList<Cell>();
		boolean go=true;
		int search=1;
		while(go) {
			go=false;
			for(Cell c : BosonX.m.holding) {
				if(c.pole==this.pole && c.level==this.level && c.zrel_id==this.zrel_id+search && c.type==this.type) {
					go=true;
					ret.add(c);
					break;
				}
			}
			search++;
		}
		return ret;
	}
	public boolean isAhead() {
		for(Cell c : BosonX.m.holding) {
			if(c.pole==this.pole && c.level==this.level && c.zrel_id==this.zrel_id+1 && c.type==this.type) {
				return true;
			}
		}
		return false;
	}
	public void generate(boolean a) {
		if(genc!=-1 && !doneGenerating) {genc++;}
		switch(BosonX.m.cp.level) { 
		case 1:
			if(geometry.translation[2]>0) {
				geometry.translate(0, 0, -10f);
			} else if(geometry.translation[2]<0) {
				geometry.translation[2]=0;
			} else if(!doneGenerating) {
				doneGenerating=true;
			}
			//if((genc==-1 && this.zrel<BosonX.m.r.cam_translation[2]+((BosonX.m.gen_distance+1)*BosonX.cell_length) && this.zrel>BosonX.m.r.cam_translation[2]+((BosonX.m.gen_distance-BosonX.m.gen_width)*BosonX.cell_length) && type!='#') || a) {}
			break;
		case 2:
			if(level<gpfgen1 && !collapsing && type!='B') {
				level+=(gpfgen1-level)/55.0f+0.05f;
				initGeo();
			} else if(level>gpfgen1 && !collapsing && type!='B') {
				level=gpfgen1;
				initGeo();
			} else if(type=='B' && !doneGenerating) {
				level=gpfgen1;
				doneGenerating=true;
			} else if(collapsing) {
				doneGenerating=true;
			} else if(level==gpfgen1) {
				doneGenerating=true;
			}
			break;
		case 3:
			if(type!='#') {
				if(gpfgen2<0) {
					rotate((-gpfgen2)/55.0f+0.01f);
					gpfgen2+=(-gpfgen2)/55.0f+0.01f;
					if(!rotationDefined) {initGeo();}
				} else if(gpfgen2>0) {
					rotate(-gpfgen2);
					gpfgen2=0;
					if(!rotationDefined) {initGeo();}
				} else if(gpfgen2==0) {
					pole=(float) Math.round(pole*BosonX.m.r.factor)/BosonX.m.r.factor;
					doneGenerating=true;
				}
			}
			break;
		case 4:
			if(type=='B') {
				if(gpfgen2<0) {
					rotate((-gpfgen2)/55.0f+0.02f);
					gpfgen2+=(-gpfgen2)/55.0f+0.02f;
					if(!rotationDefined) {initGeo();}
				} else if(gpfgen2>0) {
					rotate(-gpfgen2);
					gpfgen2=0;
					if(!rotationDefined) {initGeo();}
				} else if(gpfgen2==0) {
					pole=(float) Math.round(pole*BosonX.m.r.factor)/BosonX.m.r.factor;
					doneGenerating=true;
				}
			} else {
				if(level<gpfgen1 && !collapsing && type!='B') {
					level+=0.6f;
					initGeo();
				} else if(level>gpfgen1 && !collapsing && type!='B') {
					level=gpfgen1;
					initGeo();
				} else if(type=='B' && !doneGenerating) {
					level=gpfgen1;
					doneGenerating=true;
				} else if(collapsing) {
					doneGenerating=true;
				} else if(level==gpfgen1) {
					doneGenerating=true;
				}
			}
			break;
		case 5:
			if(level<gpfgen1 && !collapsing && type!='B') {
				level+=(gpfgen1-level)/10.0f+1.5f;
				initGeo();
			} else if(level>gpfgen1 && !collapsing && type!='B') {
				level=gpfgen1;
				initGeo();
			} else if(type=='B' && !doneGenerating) {
				level=gpfgen1;
				doneGenerating=true;
			} else if(collapsing) {
				doneGenerating=true;
			} else if(level==gpfgen1) {
				doneGenerating=true;
			}
			break;
		case 6:
			if(level<gpfgen1 && !collapsing && type!='B') {
				 level+=(gpfgen1-level)/25.0f+0.5f;
				initGeo();
			} else if(level>gpfgen1 && !collapsing && type!='B') {
				level=gpfgen1;
				initGeo();
			} else if(type=='B' && !doneGenerating) {
				level=gpfgen1;
				doneGenerating=true;
			} else if(collapsing) {
				doneGenerating=true;
			} else if(level==gpfgen1) {
				doneGenerating=true;
			}
			if(rz>0) {
				rz-=10;
			} else if(rz<0) {
				rz=0;
			} else {
				doneGenerating=true;
			}
			handleRZ();
			break;
		}
	}
	public void handleRZ() {
		if(!rotationDefined) {
		double t1=(360.0f/numpoles)*pole;
		double t2=((360.0f/numpoles)*pole)+rz;
		double r=BosonX.depth(level);
		geometry.setRotation(0,0,(float)t2);
		double x1=Math.cos(Math.toRadians(t1+90))*r; //Where is the target //0
		double y1=Math.sin(Math.toRadians(t1+90))*r;//r
		
		double x2=Math.cos(Math.toRadians(t2+90))*r; //Where are we after rotation //r
		double y2=Math.sin(Math.toRadians(t2+90))*r; //0
		
		double x=x1-x2;
		double y=y1-y2;
		
		geometry.setTranslation((float)-x,(float)-y,0);//(float)-y2,0);
		}
	}
}