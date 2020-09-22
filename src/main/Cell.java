package main;

import java.util.ArrayList;

public class Cell {
	public float pole;
	public float initPole;
	public char type;
	public float level;
	public float zrel; //Z value
	public int zrel_id; //Z value in units of cells
	public float width_o; //Half width, outer
	public float width_i; //Half width, inner
	public util.GObject geometry;
	public int numpoles;
	public int enter;
	public int genc=-1;
	
	public float length=BosonX.cell_length;
	
	public float rz=0;
	
	public int platform_id;
	public int platform_length;
	
	public boolean rotationDefined=false;
	
	//Type specific
	private float rotationSpeed;
	private float rotation;
	private float B_yd=0; //Y depth for barriers
	private float ztSpeed=0;
	public boolean collapsing=false;
	
	//Generation variables
	public float gpfgen1=0; //General Purpose Float for GENeration1
	public float gpfgen2=0;
	public float polegen_offset=0;
	public boolean gpbgen1=false;
	public boolean gpbgen2=false;
	
	public boolean doneGenerating=true;
	

	public Cell(int pole, int level, char type) {
		this.pole=pole;
		this.initPole=pole;
		this.level=level;
		this.type=type;
	}
	public void rotate(float p) { //Rotate p poles over and modulo by numpoles
		pole+=p;
		while(pole>=numpoles) {pole-=numpoles;}
		while(pole<0) {pole+=numpoles;}
	}
	public void setRotation(float p) {
		pole=p+initPole;
		while(pole>=numpoles) {pole-=numpoles;}
		while(pole<0) {pole+=numpoles;}
	}
	public void calculateConsts() { //Calculate some stuff about widths on the top and bottom of the cell
		width_o=(float) Math.tan(Math.toRadians(180.0f/numpoles))*(BosonX.depth(level)+BosonX.cell_thickness);
		if(this.type=='B') {
			width_o=(float) Math.tan(Math.toRadians(180.0f/numpoles))*(BosonX.depth(BosonX.B_depth));
		} else if(this.type=='I') {
			width_o=(float) Math.tan(Math.toRadians(180.0f/numpoles))*(BosonX.depth(level)+BosonX.cell_thickness-(BosonX.incline*platform_id));
		} else if(this.type=='D') {
			width_o=(float) Math.tan(Math.toRadians(180.0f/numpoles))*(BosonX.depth(level)+BosonX.cell_thickness+(BosonX.incline*platform_id));
		}
		//Calculate inner width
		width_i=(float) Math.tan(Math.toRadians(180.0f/numpoles))*(BosonX.depth(level));
		if(this.type=='B') {
			width_i=(float) Math.tan(Math.toRadians(180.0f/numpoles))*(BosonX.depth(BosonX.B_height));
		} else if(this.type=='I') {
			width_i=(float) Math.tan(Math.toRadians(180.0f/numpoles))*(BosonX.depth(level)-(BosonX.incline*platform_id));
		} else if(this.type=='D') {
			width_i=(float) Math.tan(Math.toRadians(180.0f/numpoles))*(BosonX.depth(level)+(BosonX.incline*platform_id));
		}
		
		if(numpoles<=2) {
			width_o=4;
			width_i=4;
		}
		
		zrel=BosonX.cell_length*zrel_id;
		rotationSpeed=((float)BosonX.m.randint(1400)-700)/1000f;
		geometry=new util.GObject(Integer.toString(enter)+":"+Integer.toString(zrel_id-enter),1);
	}
	public float height() { //Get the height of the top of the cell
		if(type=='I') {
			float passed=BosonX.m.r.cam_translation[2]-zrel;
			if(BosonX.m.r.cam_translation[2]<zrel || BosonX.m.r.cam_translation[2]>zrel+BosonX.cell_length) {
				passed=0;
			}
			passed/=BosonX.cell_length;
			return BosonX.depth(level)-((platform_id+passed)*BosonX.incline);
		} else if(type=='D') {
			float passed=BosonX.m.r.cam_translation[2]-zrel;
			if(BosonX.m.r.cam_translation[2]<zrel || BosonX.m.r.cam_translation[2]>zrel+BosonX.cell_length) {
				passed=0;
			}
			passed/=BosonX.cell_length;
			return BosonX.depth(level)+((platform_id+passed)*BosonX.incline);
		}
		return BosonX.depth(level);
	}
	public void doRotations() {
		switch (type) {
		case 'D':
		case 'P':
		case 'I':
		case 'E':
		case 'C':
		case 'B':
		case 'G':
		case 'F':
		case 'L':
			geometry.setRotation(0,0,(360.0f/numpoles)*pole);
			break;
		}
		handleRZ();
	}
	public void initGeo() { //Initialize geometry.
		//This wipes out our original geometry and adds all the quads to it
		geometry.quads=new ArrayList<util.Quad>();
		geometry.tris=new ArrayList<util.Tri>();
		geometry.post=new float[] {0,0,0};
		geometry.rotationalLighting=BosonX.m.cellRotationalLighting;
		float i;
		float iw_off;
		float ow_off;
		switch (type) {
		case 'I':
			i=BosonX.incline;
			iw_off=(float) Math.tan(Math.toRadians(180.0f/numpoles))*(BosonX.depth(level)-(i*platform_id+i))-width_i;
			ow_off=(float) Math.tan(Math.toRadians(180.0f/numpoles))*(BosonX.depth(level)+BosonX.cell_thickness-(i*platform_id+i))-width_o;
			geometry.quads.add(new util.Quad( //Upwards face
					width_i,(i*platform_id)+-BosonX.depth(level),zrel,
					-width_i,(i*platform_id)+-BosonX.depth(level),zrel,
					-(width_i+iw_off),(i*platform_id)+i-BosonX.depth(level),zrel+this.length,
					(width_i+iw_off),(i*platform_id)+i-BosonX.depth(level),zrel+this.length)
					.autoNormal(0, 1, 0)
					);
			geometry.quads.add(new util.Quad(
					width_o,(i*platform_id)+-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,(i*platform_id)+-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-(width_o+ow_off),(i*platform_id)+i-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					(width_o+ow_off),(i*platform_id)+i-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length)
					.autoNormal(0, -1, 0)
					);

			geometry.quads.add(new util.Quad(
					-width_o,(i*platform_id)+-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-(width_o+ow_off),(i*platform_id)+i-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					-(width_i+iw_off),(i*platform_id)+i-BosonX.depth(level),zrel+this.length,
					-width_i,(i*platform_id)+-BosonX.depth(level),zrel)
					.autoNormal(-1, 0, 0)
					);
			geometry.quads.add(new util.Quad(
					width_o,(i*platform_id)+-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					(width_o+ow_off),(i*platform_id)+i-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					(width_i+iw_off),(i*platform_id)+i-BosonX.depth(level),zrel+this.length,
					width_i,(i*platform_id)+-BosonX.depth(level),zrel)
					.autoNormal(1, 0, 0)
					);
			geometry.quads.add(new util.Quad(
					width_o,(i*platform_id)+-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,(i*platform_id)+-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_i,(i*platform_id)+-BosonX.depth(level),zrel,
					width_i,(i*platform_id)+-BosonX.depth(level),zrel)
					.autoNormal(0, 0,-1)
					);
			geometry.quads.add(new util.Quad(
					(width_o+ow_off),(i*platform_id)+i-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					-(width_o+ow_off),(i*platform_id)+i-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					-(width_i+iw_off),(i*platform_id)+i-BosonX.depth(level),zrel+this.length,
					(width_i+iw_off),(i*platform_id)+i-BosonX.depth(level),zrel+this.length)
					.autoNormal(0, 0, 1)
					);
			geometry.setColor(BosonX.m.P_color.x,BosonX.m.P_color.y,BosonX.m.P_color.z);
			geometry.lighting=BosonX.m.P_color.w;

			geometry.setRotation(0,0,(360.0f/numpoles)*pole);
			break;
		case 'D':
			i=-BosonX.incline;
			iw_off=(float) Math.tan(Math.toRadians(180.0f/numpoles))*(BosonX.depth(level)-(i*platform_id+i))-width_i;
			ow_off=(float) Math.tan(Math.toRadians(180.0f/numpoles))*(BosonX.depth(level)+BosonX.cell_thickness-(i*platform_id+i))-width_o;
			geometry.quads.add(new util.Quad( //Upwards face
					width_i,(i*platform_id)+-BosonX.depth(level),zrel,
					-width_i,(i*platform_id)+-BosonX.depth(level),zrel,
					-(width_i+iw_off),(i*platform_id)+i-BosonX.depth(level),zrel+this.length,
					(width_i+iw_off),(i*platform_id)+i-BosonX.depth(level),zrel+this.length)
					.autoNormal(0, 1, 0)
					);
			geometry.quads.add(new util.Quad(
					width_o,(i*platform_id)+-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,(i*platform_id)+-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-(width_o+ow_off),(i*platform_id)+i-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					(width_o+ow_off),(i*platform_id)+i-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length)
					.autoNormal(0, -1, 0)
					);

			geometry.quads.add(new util.Quad(
					-width_o,(i*platform_id)+-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-(width_o+ow_off),(i*platform_id)+i-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					-(width_i+iw_off),(i*platform_id)+i-BosonX.depth(level),zrel+this.length,
					-width_i,(i*platform_id)+-BosonX.depth(level),zrel)
					.autoNormal(-1, 0, 0)
					);
			geometry.quads.add(new util.Quad(
					width_o,(i*platform_id)+-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					(width_o+ow_off),(i*platform_id)+i-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					(width_i+iw_off),(i*platform_id)+i-BosonX.depth(level),zrel+this.length,
					width_i,(i*platform_id)+-BosonX.depth(level),zrel)
					.autoNormal(1, 0, 0)
					);
			geometry.quads.add(new util.Quad(
					width_o,(i*platform_id)+-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,(i*platform_id)+-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_i,(i*platform_id)+-BosonX.depth(level),zrel,
					width_i,(i*platform_id)+-BosonX.depth(level),zrel)
					.autoNormal(0, 0,-1)
					);
			geometry.quads.add(new util.Quad(
					(width_o+ow_off),(i*platform_id)+i-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					-(width_o+ow_off),(i*platform_id)+i-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					-(width_i+iw_off),(i*platform_id)+i-BosonX.depth(level),zrel+this.length,
					(width_i+iw_off),(i*platform_id)+i-BosonX.depth(level),zrel+this.length)
					.autoNormal(0, 0, 1)
					);
			geometry.setColor(BosonX.m.P_color.x,BosonX.m.P_color.y,BosonX.m.P_color.z);
			geometry.lighting=BosonX.m.P_color.w;

			geometry.setRotation(0,0,(360.0f/numpoles)*pole);
			break;
		case 'P':
			geometry.quads.add(new util.Quad(
					width_i,-BosonX.depth(level),zrel,
					-width_i,-BosonX.depth(level),zrel,
					-width_i,-BosonX.depth(level),zrel+this.length,
					width_i,-BosonX.depth(level),zrel+this.length)
					.setNormal(0, 1, 0, true)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length)
					.setNormal(0, -1, 0, true)
					);

			geometry.quads.add(new util.Quad(
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					-width_i,-BosonX.depth(level),zrel+this.length,
					-width_i,-BosonX.depth(level),zrel)
					.autoNormal(-1, 0, 0)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					width_i,-BosonX.depth(level),zrel+this.length,
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
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					-width_i,-BosonX.depth(level),zrel+this.length,
					width_i,-BosonX.depth(level),zrel+this.length)
					.autoNormal(0, 0, 1)
					);
			geometry.setColor(BosonX.m.P_color.x,BosonX.m.P_color.y,BosonX.m.P_color.z);
			geometry.lighting=BosonX.m.P_color.w;

			geometry.setRotation(0,0,(360.0f/numpoles)*pole);
			break;
		case 'L':
			geometry.quads.add(new util.Quad( //Upper face
					width_i,-BosonX.depth(level),zrel,
					-width_i,-BosonX.depth(level),zrel,
					-width_i,-BosonX.depth(level),zrel+this.length,
					width_i,-BosonX.depth(level),zrel+this.length)
					.setNormal(0, 1, 0, true)
					);
			geometry.quads.add(new util.Quad(
					-width_i,-BosonX.depth(level)-BosonX.l_thickness,zrel,
					-width_i,-BosonX.depth(level)-BosonX.l_thickness,zrel+this.length,
					-width_i,-BosonX.depth(level),zrel+this.length,
					-width_i,-BosonX.depth(level),zrel)
					.autoNormal(-1, 0, 0)
					);
			geometry.quads.add(new util.Quad(
					width_i,-BosonX.depth(level)-BosonX.l_thickness,zrel,
					width_i,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					width_i,-BosonX.depth(level),zrel+this.length,
					width_i,-BosonX.depth(level),zrel)
					.autoNormal(1, 0, 0)
					);
			geometry.quads.add(new util.Quad(
					width_i,-BosonX.depth(level)-BosonX.l_thickness,zrel,
					-width_i,-BosonX.depth(level)-BosonX.l_thickness,zrel,
					-width_i,-BosonX.depth(level),zrel,
					width_i,-BosonX.depth(level),zrel)
					.autoNormal(0, 0,-1)
					);
			geometry.quads.add(new util.Quad(
					width_i,-BosonX.depth(level)-BosonX.l_thickness,zrel+this.length,
					-width_i,-BosonX.depth(level)-BosonX.l_thickness,zrel+this.length,
					-width_i,-BosonX.depth(level),zrel+this.length,
					width_i,-BosonX.depth(level),zrel+this.length)
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
					-width_i,-BosonX.depth(level),zrel+this.length,
					width_i,-BosonX.depth(level),zrel+this.length)
					.setNormal(0, 1, 0, true)
					);

			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length)
					.setNormal(0,-1,0,true)
					);

			geometry.quads.add(new util.Quad(
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					-width_i,-BosonX.depth(level),zrel+this.length,
					-width_i,-BosonX.depth(level),zrel)
					.autoNormal(-1,0,0)
					);

			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					width_i,-BosonX.depth(level),zrel+this.length,
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
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					-width_i,-BosonX.depth(level),zrel+this.length,
					width_i,-BosonX.depth(level),zrel+this.length)
					.autoNormal(0, 0, 1)
					);
			doColors();

			geometry.setRotation(0,0,(360.0f/numpoles)*pole);
			break;
		case 'C':
			geometry.quads.add(new util.Quad(
					width_i,-BosonX.depth(level),zrel,
					-width_i,-BosonX.depth(level),zrel,
					-width_i,-BosonX.depth(level),zrel+this.length,
					width_i,-BosonX.depth(level),zrel+this.length)
					.setNormal(0, 1, 0, true)
					);

			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length)
					.setNormal(0,-1,0,true)
					);

			geometry.quads.add(new util.Quad(
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					-width_i,-BosonX.depth(level),zrel+this.length,
					-width_i,-BosonX.depth(level),zrel)
					.autoNormal(-1,0,0)
					);

			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					width_i,-BosonX.depth(level),zrel+this.length,
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
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					-width_i,-BosonX.depth(level),zrel+this.length,
					width_i,-BosonX.depth(level),zrel+this.length)
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
					-width_i,-BosonX.depth(BosonX.B_height),zrel+this.length,
					width_i,-BosonX.depth(BosonX.B_height),zrel+this.length)
					.setNormal(0, 1, 0, true)
					);

			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(BosonX.B_depth),zrel,
					-width_o,-BosonX.depth(BosonX.B_depth),zrel,
					-width_o,-BosonX.depth(BosonX.B_depth),zrel+this.length,
					width_o,-BosonX.depth(BosonX.B_depth),zrel+this.length)
					.setNormal(0,-1,0,true)
					);

			geometry.quads.add(new util.Quad(
					-width_o,-BosonX.depth(BosonX.B_depth),zrel,
					-width_o,-BosonX.depth(BosonX.B_depth),zrel+this.length,
					-width_i,-BosonX.depth(BosonX.B_height),zrel+this.length,
					-width_i,-BosonX.depth(BosonX.B_height),zrel)
					.autoNormal(-1,0,0)
					);

			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(BosonX.B_depth),zrel,
					width_o,-BosonX.depth(BosonX.B_depth),zrel+this.length,
					width_i,-BosonX.depth(BosonX.B_height),zrel+this.length,
					width_i,-BosonX.depth(BosonX.B_height),zrel)
					.autoNormal(1, 0, 0)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(BosonX.B_depth),zrel,
					-width_o,-BosonX.depth(BosonX.B_depth),zrel,
					-width_i,-BosonX.depth(BosonX.B_height),zrel,
					width_i,-BosonX.depth(BosonX.B_height),zrel)
					.autoNormal(0,0,-1)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(BosonX.B_depth),zrel+this.length,
					-width_o,-BosonX.depth(BosonX.B_depth),zrel+this.length,
					-width_i,-BosonX.depth(BosonX.B_height),zrel+this.length,
					width_i,-BosonX.depth(BosonX.B_height),zrel+this.length)
					.autoNormal(0, 0, 1)
					);
			geometry.setColor(BosonX.m.P_color.x,BosonX.m.P_color.y,BosonX.m.P_color.z);
			geometry.lighting=BosonX.m.P_color.w;
			
			geometry.post=new float[] {0,-B_yd,0};

			geometry.setRotation(0,0,(360.0f/numpoles)*pole);
			break;
		case 'G':
		case 'F':
			geometry.quads.add(new util.Quad(
					width_i,-BosonX.depth(level),zrel,
					-width_i,-BosonX.depth(level),zrel,
					-width_i,-BosonX.depth(level),zrel+this.length,
					width_i,-BosonX.depth(level),zrel+this.length)
					.setNormal(0, 1, 0, true)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length)
					.setNormal(0, -1, 0, true)
					);

			geometry.quads.add(new util.Quad(
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					-width_i,-BosonX.depth(level),zrel+this.length,
					-width_i,-BosonX.depth(level),zrel)
					.autoNormal(-1, 0, 0)
					);
			geometry.quads.add(new util.Quad(
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel,
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					width_i,-BosonX.depth(level),zrel+this.length,
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
					width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					-width_o,-BosonX.depth(level)-BosonX.cell_thickness,zrel+this.length,
					-width_i,-BosonX.depth(level),zrel+this.length,
					width_i,-BosonX.depth(level),zrel+this.length)
					.autoNormal(0, 0, 1)
					);
			geometry.setColor(BosonX.m.P_color.x,BosonX.m.P_color.y,BosonX.m.P_color.z);
			geometry.lighting=BosonX.m.P_color.w;

			geometry.setRotation(0,0,(360.0f/numpoles)*pole);
			break;
		case '#':
			rotationDefined=true;
			geometry.loadOBJ("/cannon_outer.obj");
			geometry.scale(15,15,3);
			geometry.translate(0, 0, zrel);
			geometry.setColor(BosonX.m.P_color.x,BosonX.m.P_color.y,BosonX.m.P_color.z);
			geometry.lighting=BosonX.m.P_color.w;
			util.GObject inner=new util.GObject(1);
			inner.loadOBJ("/cannon_inner.obj");
			inner.setColor(1, 1, 1);
			inner.translate(0,0,zrel);
			inner.scale(15,15,3);
			inner.lighting=0;
			geometry.attached.add(inner);
			util.GObject beam=new util.GObject(1);
			beam.quads.add(new util.Quad(-1,0.02f,0, 1,0.02f,0, 1,-0.02f,0, -1,-0.02f,0).setColor(5, 5, 5).setNormal(0,0,-1,true));
			beam.translate(0,0,zrel);
			beam.scale(15,15,3);
			beam.lighting=0;
			geometry.attached.add(beam);
			geometry.rotationalLighting=true;
			break;
		default:
			System.out.print("Type ");
			System.out.print(type);
			System.out.println(" not recognized.");
			//System.exit(1);
		}
		handleRZ();
	}
	public float onRenderAdd(float a) { //When we are released from pergatory
		switch(type) {
		case 'G': //Pass some values down to coordinate platforms
			ztSpeed=(BosonX.m.randint(1000)/2000.0f)+1.0f;
			if(a==-1) {
				a=ztSpeed;
			} else {
				ztSpeed=a;
			}
			//Intentionally no break; here
		case 'F': //Calculate our speedy stuff
			if(type=='F') {ztSpeed=BosonX.m.F_speed;}
			float timeToDest=(zrel-BosonX.m.r.cam_translation[2])/BosonX.m.speed;
			length=(BosonX.cell_length/BosonX.m.speed)*ztSpeed+BosonX.cell_length;
			float zt_init=timeToDest*ztSpeed;
			zrel+=zt_init;
			break;
		}
		return a;
	}
	public void resetColors() { //Exactly what it sounds like.
		switch(type) {
		case 'F':
		case 'G':
		case 'L':
		case 'I':
		case 'D':
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
			break;
		case '#':
			geometry.setColor(BosonX.m.P_color.x,BosonX.m.P_color.y,BosonX.m.P_color.z);
			geometry.lighting=BosonX.m.P_color.w;
			break;
		default:
			System.out.print("Type ");
			System.out.print(type);
			System.out.println(" not recognized.");
		}
	}
	public char handleEffects(float cam_z, boolean a) { //Handle stuff such as energy increase, platform motion, collapse, etc.
		switch(type) {
		case 'C':
			doColors();
			if(this.collapsing) {
				level-=(0.1f)/BosonX.m.frc;
				initGeo();
			}
			if((BosonX.m.r.cam_translation[2]>this.zrel-0.2 && BosonX.m.r.cam_translation[2]<this.zrel+0.4+BosonX.cell_length && (pole<0.5 || pole>numpoles-0.5) && BosonX.m.r.on)||a) { //Recurse over a to trigger platforms
				if(!collapsing && !a) {
					BosonX.m.r.rumble.play(BosonX.m.r.rumble_b);
				}
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
				BosonX.m.energy+=BosonX.m.energy_gain/BosonX.m.frc;
				BosonX.m.onE=true;
			}
			break;
		case '#':
			rotation+=(rotationSpeed)/BosonX.m.frc;
			geometry.setRotation(0, 0, (360.0f/numpoles)*pole+rotation);
			break;
		case 'G':
		case 'F':
			zrel-=ztSpeed/BosonX.m.frc;
			initGeo();
			break;
		}
		if(this.zrel>BosonX.m.gen_cutoff*BosonX.cell_length) {
			generate(false);
		}
		
		
		if(BosonX.m.r.cam_translation[2]>this.zrel-0.2 && BosonX.m.r.cam_translation[2]<this.zrel+0.4+BosonX.cell_length && Math.floor(this.pole)==0 && BosonX.m.r.on) {
			return this.type;
		} else {
			return '!';
		}
		
	}
	
	public void doColors() { //For oscillating colors
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
	public float[] initGenerate(float[] a) { //These contain our subroutines for initializing generation
		doneGenerating=false;
		switch(BosonX.m.cp.level) {
		case 1:
			geometry.translation[2]=200;
			break;
		case 2:
			gpfgen1=level; //Save our initial level
			if(type!='B') {level-=20;}
			B_yd+=BosonX.depth(-20)-BosonX.depth(0);
			break;
		case 3:
			if(!rotationDefined) {
				rotate(-5);
				doRotations();
				polegen_offset=-5; //This will store our offset
			}
			break;
		case 4:
			if(type=='B') {
				rotate(-3);
				if(!rotationDefined) {doRotations();}
				polegen_offset=-3;
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
			float rze=0;
			if(platform_id==0) {rze=BosonX.m.randint(180)+180;}
			else {rze=a[0];}
			rz=rze;
			if(type!='B') {level-=20;}
			B_yd+=BosonX.depth(-20)-BosonX.depth(0);
			float r=-1;
			if(!rotationDefined) {
				if(platform_id==0) {r=-1-BosonX.m.randint(1000)/1000.0f;}
				else {r=a[1];}
				rotate(r);
				initGeo();
				polegen_offset=r;
			}
			if(platform_id==0) {
				return new float[] {rze,r};
			}
			break;
		case 7:
			gpfgen1=level;
			rze=0;
			if(platform_id==0) {rze=BosonX.m.randint(180)+180;}
			else {rze=a[0];}
			rz=rze;
			if(type!='B') {level-=20;}
			B_yd+=BosonX.depth(-20)-BosonX.depth(0);
			r=-1;
			if(!rotationDefined) {
				if(platform_id==0) {r=-1-BosonX.m.randint(1000)/1000.0f;}
				else {r=a[1];}
				rotate(r);
				initGeo();
				polegen_offset=r;
			}
			if(platform_id==0) {
				return new float[] {rze,r};
			}
			break;
		case 8:
			gpbgen1=true;
			gpfgen1=level; //Save our initial level
			if(type!='B') {level-=20;}
			B_yd+=BosonX.depth(-20)-BosonX.depth(0);
			break;
		case 9:
			if(!rotationDefined) {
				polegen_offset=-pole;
				rotate(-pole);
				doRotations();
			}
			break;
		case 10:
			if(!rotationDefined && type!='B') {rz=360;}
			break;
		case 11:
			if(!rotationDefined) {
				rotate(-2);
				doRotations();
				polegen_offset=-2;
			}
			break;
		case 12:
			geometry.asMesh=true;
			break;
		case 13:
			if(type!='L') {geometry.translation[2]=200;}
			else {
				gpfgen1=level;
				level-=BosonX.l_thickness;
			}
			break;
		case 14:
			if(type=='L') {
				gpfgen1=level;
				level-=BosonX.l_thickness;
			}
			break;
		case 15:
			if(!rotationDefined) {
				if(type!='L') {geometry.translation[2]=200;}
				else {
					gpfgen1=level;
					level-=BosonX.l_thickness;
				}
			}
			break;
		case 16:
			if(type=='L') {
				gpfgen1=level;
				level-=BosonX.l_thickness;
			}
			break;
		case 17:
			if(type=='L') {
				gpfgen1=level;
				level-=BosonX.l_thickness;
			}
			break;
		}
		return a;
	}
	public ArrayList<Cell> getAhead() { //Search and get all the cells ahead of us in our platform.
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
	public float pole() { //Get our pole. I don't think this is used anywhere.
		float t=this.pole-this.polegen_offset;
		t=(float) Math.round(t);
		while(t>=numpoles) {t-=numpoles;}
		while(t<0) {t+=numpoles;}
		return t;
	}
	public boolean isAhead() { //Is there a cell of the same type directly ahead of us
		for(Cell c : BosonX.m.holding) {
			if(c.pole==this.pole && c.level==this.level && c.zrel_id==this.zrel_id+1 && c.type==this.type) {
				return true;
			}
		}
		return false;
	}
	public void generate(boolean a) { //This stores our generation subroutines for each level. No point in documenting them.
		if(genc!=-1) {genc++;}
		switch(BosonX.m.cp.level) { 
		case 1:
			if(geometry.translation[2]>0) {
				geometry.translate(0, 0, -10f/BosonX.m.frc);
			} else if(geometry.translation[2]<0) {
				geometry.translation[2]=0;
			} else if(!doneGenerating) {
				doneGenerating=true;
			}
			//if((genc==-1 && this.zrel<BosonX.m.r.cam_translation[2]+((BosonX.m.gen_distance+1)*BosonX.cell_length) && this.zrel>BosonX.m.r.cam_translation[2]+((BosonX.m.gen_distance-BosonX.m.gen_width)*BosonX.cell_length) && type!='#') || a) {}
			break;
		case 2:
			if(type!='B') {
				if(level<gpfgen1 && !collapsing) {
					level+=((gpfgen1-level)/55.0f+0.05f)/BosonX.m.frc;
					initGeo();
				} else if(level>gpfgen1 && !collapsing) {
					level=gpfgen1;
					initGeo();
				} else if(level==gpfgen1) {
					
				} else if(collapsing) {
					doneGenerating=true;
				} else if(level==gpfgen1) {
					doneGenerating=true;
				}
			} else {
				if(B_yd>0) {
					B_yd-=((B_yd)/55.0f+0.05f)/BosonX.m.frc;
					initGeo();
				} else if(B_yd<0) {
					B_yd=0;
					initGeo();
				} else {
					doneGenerating=true;
				}
			}
			break;
		case 3:
			if(!rotationDefined) {
				if(polegen_offset<0) {
					rotate(((-polegen_offset)/55.0f+0.01f)/BosonX.m.frc);
					polegen_offset+=((-polegen_offset)/55.0f+0.01f)/BosonX.m.frc;
					if(!rotationDefined) {doRotations();}
				} else if(polegen_offset>0) {
					rotate(-polegen_offset);
					polegen_offset=0;
					if(!rotationDefined) {doRotations();}
				} else if(polegen_offset==0) {
					pole=(float) Math.round(pole*BosonX.m.r.factor)/BosonX.m.r.factor;
					doneGenerating=true;
				}
			}
			break;
		case 4:
			if(type=='B') {
				if(polegen_offset<0) {
					rotate(((-polegen_offset)/55.0f+0.02f)/BosonX.m.frc);
					polegen_offset+=((-polegen_offset)/55.0f+0.02f)/BosonX.m.frc;
					if(!rotationDefined) {doRotations();}
				} else if(polegen_offset>0) {
					rotate(-polegen_offset);
					polegen_offset=0;
					if(!rotationDefined) {doRotations();}
				} else if(polegen_offset==0) {
					pole=(float) Math.round(pole*BosonX.m.r.factor)/BosonX.m.r.factor;
					doneGenerating=true;
				}
			} else {
				if(level<gpfgen1 && !collapsing && type!='B') {
					level+=(0.6f)/BosonX.m.frc;
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
				level+=((gpfgen1-level)/10.0f+1.5f)/BosonX.m.frc;
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
			if(!rotationDefined) {
				doneGenerating=true;
				if(type!='B') {
					if(level<gpfgen1 && !collapsing) {
						level+=((gpfgen1-level)/17.0f+0.7f)/BosonX.m.frc;
						doneGenerating=false;
						initGeo();
					} else if(level>gpfgen1 && !collapsing) {
						level=gpfgen1;
						doneGenerating=false;
						initGeo();
					}
				} else {
					if(B_yd>0) {
						B_yd-=((B_yd)/17.0f+0.7f)/BosonX.m.frc;
						initGeo();
						doneGenerating=false;
					} else if(B_yd<0) {
						B_yd=0;
						initGeo();
						doneGenerating=false;
					}
				}
				if(rz>0) {
					rz-=(15)/BosonX.m.frc;
					doneGenerating=false;
				} else if(rz<0) {
					rz=0;
					doneGenerating=false;
				}
				handleRZ();
				if(polegen_offset<0) {
					rotate(((-polegen_offset)/35.0f+0.05f)/BosonX.m.frc);
					polegen_offset+=((-polegen_offset)/35.0f+0.05f)/BosonX.m.frc;
					doRotations();
					doneGenerating=false;
				} else if(polegen_offset>0) {
					rotate(-polegen_offset);
					polegen_offset=0;
					doRotations();
					doneGenerating=false;
				} else if(polegen_offset==0) {
					pole=(float) Math.round(pole*BosonX.m.r.factor)/BosonX.m.r.factor;
					doRotations();
				}
			} else {
				doneGenerating=true;
			}
			break;
		case 7:
			if(!rotationDefined) {
				doneGenerating=true;
				if(type!='B') {
					if(level<gpfgen1 && !collapsing) {
						level+=((gpfgen1-level)/17.0f+0.7f)/BosonX.m.frc;
						doneGenerating=false;
						initGeo();
					} else if(level>gpfgen1 && !collapsing) {
						level=gpfgen1;
						doneGenerating=false;
						initGeo();
					}
				} else {
					if(B_yd>0) {
						B_yd-=((B_yd)/17.0f+0.7f)/BosonX.m.frc;
						initGeo();
						doneGenerating=false;
					} else if(B_yd<0) {
						B_yd=0;
						initGeo();
						doneGenerating=false;
					}
				}
				if(rz>0) {
					rz-=(15)/BosonX.m.frc;
					doneGenerating=false;
				} else if(rz<0) {
					rz=0;
					doneGenerating=false;
				}
				handleRZ();
				if(polegen_offset<0) {
					rotate(((-polegen_offset)/35.0f+0.05f)/BosonX.m.frc);
					polegen_offset+=((-polegen_offset)/35.0f+0.05f)/BosonX.m.frc;
					doRotations();
					doneGenerating=false;
				} else if(polegen_offset>0) {
					rotate(-polegen_offset);
					polegen_offset=0;
					doRotations();
					doneGenerating=false;
				} else if(polegen_offset==0) {
					pole=(float) Math.round(pole*BosonX.m.r.factor)/BosonX.m.r.factor;
					doRotations();
				}
			} else {
				doneGenerating=true;
			}
			break;
		case 8:
			if(!rotationDefined) {
				float speed=0;
				if(genc>50) {
					speed=0.3f;
				}
				if(!gpbgen1) {
					speed=0.1f;
				}
				if(gpbgen1) {
					if(type!='B') {
						if(level<gpfgen1+2 && !collapsing) {
							level+=(speed)/BosonX.m.frc;
							initGeo();
						} else if(level>gpfgen1+2 && !collapsing) {
							gpbgen1=false;
							level=gpfgen1+2;
							initGeo();
						} else if(collapsing) {
							doneGenerating=true;
						}
					} else {
						if(B_yd>BosonX.depth(2)-BosonX.depth(0)) {
							B_yd+=(BosonX.depth(speed)-BosonX.depth(0))/BosonX.m.frc;
							initGeo();
						} else if(B_yd<BosonX.depth(2)-BosonX.depth(0)) {
							B_yd=BosonX.depth(2)-BosonX.depth(0);
							gpbgen1=false;
							initGeo();
						}
					}
				} else {
					if(type!='B') {
						if(level>gpfgen1 && !collapsing) {
							level-=(speed)/BosonX.m.frc;
							initGeo();
						} else if(level<gpfgen1 && !collapsing) {
							level=gpfgen1;
							initGeo();
						} else if(collapsing) {
							doneGenerating=true;
						} else if(level==gpfgen1) {
							doneGenerating=true;
						}
					} else {
						if(B_yd<0) {
							B_yd-=(BosonX.depth(speed)-BosonX.depth(0))/BosonX.m.frc;
							initGeo();
						} else if(B_yd>0) {
							B_yd=0;
							initGeo();
						} else {
							doneGenerating=true;
						}
					}
				}
			} else {
				doneGenerating=true;
			}
			break;
		case 9:
			if(!rotationDefined) {
				float f=(polegen_offset+numpoles)/numpoles;
				if(polegen_offset<0) {
					rotate(((-polegen_offset)/55.0f+0.01f)/BosonX.m.frc);
					polegen_offset+=((-polegen_offset)/55.0f+0.01f)/BosonX.m.frc;
					if(!rotationDefined) {doRotations();}
				} else if(polegen_offset>0) {
					rotate(-polegen_offset);
					polegen_offset=0;
					if(!rotationDefined) {doRotations();}
				} else if(polegen_offset==0) {
					pole=(float) Math.round(pole*BosonX.m.r.factor)/BosonX.m.r.factor;
					doneGenerating=true;
				}
				geometry.setScale(f,f,1);
				geometry.translation[2]=zrel*(1-f);
			}
			break;
		case 10:
			if(!rotationDefined && type!='B') {
				if(rz>0) {
					rz-=(rz/45.0f+5.0f)/BosonX.m.frc;
					handleRZ();
				} else if(rz<0) {
					doneGenerating=true;
					rz=0;
					handleRZ();
				}
			}
			break;
		case 11:
			if(!rotationDefined) {
				if(polegen_offset<0) {
					rotate((0.02f)/BosonX.m.frc);
					polegen_offset+=(0.02f)/BosonX.m.frc;
					doRotations();
				} else if(polegen_offset>0) {
					rotate(-polegen_offset);
					polegen_offset=0;
					doRotations();
				} else if(polegen_offset==0) {
					pole=(float) Math.round(pole*BosonX.m.r.factor)/BosonX.m.r.factor;
					doneGenerating=true;
				}
			}
			break;
		case 12:
			if(genc>100*BosonX.m.frc) {
				geometry.asMesh=false;
			}
			break;
		case 13:
			if(type!='L') {
				if(geometry.translation[2]>0) {
					geometry.translate(0, 0, (-10f)/BosonX.m.frc);
				} else if(geometry.translation[2]<0) {
					geometry.translation[2]=0;
				} else if(!doneGenerating) {
					doneGenerating=true;
				}
			} else {
				if(level<gpfgen1 && !collapsing) {
					level+=((gpfgen1-level)/35.0f+1f)/BosonX.m.frc;
					initGeo();
				} else if(level>gpfgen1 && !collapsing) {
					level=gpfgen1;
					initGeo();
				} else {
					doneGenerating=true;
				}
			}
			break;
		case 14:
			if(type=='L') {
				if(level<gpfgen1 && !collapsing) {
					level+=((gpfgen1-level)/35.0f+1f)/BosonX.m.frc;
					initGeo();
				} else if(level>gpfgen1 && !collapsing) {
					level=gpfgen1;
					initGeo();
				} else {
					doneGenerating=true;
				}
			}
			break;
		case 15:
			if(!rotationDefined) {
				if(type!='L') {
					if(geometry.translation[2]>0) {
						geometry.translate(0, 0, (-10f)/BosonX.m.frc);
					} else if(geometry.translation[2]<0) {
						geometry.translation[2]=0;
					} else if(!doneGenerating) {
						doneGenerating=true;
					}
				} else {
					if(level<gpfgen1 && !collapsing) {
						level+=((gpfgen1-level)/35.0f+1f)/BosonX.m.frc;
						initGeo();
					} else if(level>gpfgen1 && !collapsing) {
						level=gpfgen1;
						initGeo();
					} else {
						doneGenerating=true;
					}
				}
			}
			break;
		case 16:
			if(type=='L') {
				if(level<gpfgen1 && !collapsing) {
					level+=((gpfgen1-level)/35.0f+1f)/BosonX.m.frc;
					initGeo();
				} else if(level>gpfgen1 && !collapsing) {
					level=gpfgen1;
					initGeo();
				} else {
					doneGenerating=true;
				}
			}
			break;
		case 17:
			if(type=='L') {
				if(level<gpfgen1 && !collapsing) {
					level+=((gpfgen1-level)/35.0f+1f)/BosonX.m.frc;
					initGeo();
				} else if(level>gpfgen1 && !collapsing) {
					level=gpfgen1;
					initGeo();
				} else {
					doneGenerating=true;
				}
			}
			break;
		}
	}
	public void handleRZ() { //Do the trig for rotation around the z axis
		if(!rotationDefined && (BosonX.m.cp.level==6 || BosonX.m.cp.level==7 || BosonX.m.cp.level==10)) {
			double t1=(360.0f/numpoles)*pole;
			double t2=((360.0f/numpoles)*pole)+rz;
			double r=BosonX.depth(level);
			geometry.setRotation(0,0,(float)t2);
			double x1=Math.cos(Math.toRadians(t1+90))*r; //Where is the target
			double y1=Math.sin(Math.toRadians(t1+90))*r;

			double x2=Math.cos(Math.toRadians(t2+90))*r; //Where are we after rotation 
			double y2=Math.sin(Math.toRadians(t2+90))*r;

			double x=x1-x2;
			double y=y1-y2;

			geometry.setTranslation((float)-x,(float)-y,0);
		}
	}
}