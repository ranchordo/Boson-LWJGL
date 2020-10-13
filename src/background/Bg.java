package background;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.BosonX;
import util.Util;

public class Bg { //This is the background of particles that go by us as we run the level.
	public ArrayList<Particle> particles=new ArrayList<Particle>();
	public util.Vector4f bgcolor=new util.Vector4f(1,0,1,1);
	public util.Vector4f partcolor=new util.Vector4f(0,1,0,1);
	
	public boolean lightParticles=true;
	
	public float v_variance=0;
	public float y_variance=0;
	
	public float t_min=0;
	public float t_max=0;
	
	public float w=10;
	public float l=10;
	
	public float density=0;
	
	public boolean HSBrandom=false;
	public float HSBcycle=0;
	public float Hmin=0;
	public float Hmax=0;
	
	public boolean randomXR=false;
	
	public int numPoles=0;
	public float xt=0;
	
	public BufferedImage pbi;
	
	public boolean pt=false;
	public boolean separateE=false;
	
	public util.Vector3f maskColor=new util.Vector3f(1,1,0);
	public float maskHSBcycle=0;
	
	public Bg() {
		loadTextureType(0);
	}
	public void loadTexture(String fname) { //Fetch a texture
		try {
			pbi=ImageIO.read(BosonX.class.getResourceAsStream(fname));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	public void loadTextureType(int type) {
		switch(type) {
		case 0:
			loadTexture("/particle_fade.png");
			break;
		case 1:
			pbi=new BufferedImage(10,10,BufferedImage.TYPE_4BYTE_ABGR); //A square.
			Graphics g=pbi.getGraphics();
			g.setColor(new Color(255,255,255));
			g.fillRect(0,0,pbi.getWidth(),pbi.getHeight());
			g.dispose();
			break;
		}
	}
	public void intensity(float v) {
		this.bgcolor.x*=v;
		this.bgcolor.y*=v;
		this.bgcolor.z*=v;
	}
	public void printSize() {
		System.out.println(particles.size());
	}
	public void populate() { //Add all the particles we need
		for(int i=0;i<density*BosonX.background_gen;i++) {
			Particle p=new Particle();
			particles.add(p);
			p.tex.initBI(this.pbi);
			p.tex.applyBI();
			p.lighting=this.lightParticles;
		}
	}
	public util.Vector4f partcolorspace() { //For some levels, we need to cycle HSB values around. These are color space conversion methods.
		float h=partcolor.x;//*256.0f;
		float s=partcolor.y;//*256.0f;
		float br=partcolor.z;//*256.0f;
		float w=partcolor.w;
		int rgb=Color.HSBtoRGB(h,s,br);
		float r=((rgb>>16)&0xFF)/256.0f;
		float g=((rgb>>8)&0xFF)/256.0f;
		float b=(rgb&0xFF)/256.0f;
		return new util.Vector4f(r,g,b,w);
	}
	public util.Vector4f bgcolorspace() {
		float h=bgcolor.x;//*256.0f;
		float s=bgcolor.y;//*256.0f;
		float br=bgcolor.z;//*256.0f;
		float w=bgcolor.w;
		int rgb=Color.HSBtoRGB(h,s,br);
		float r=((rgb>>16)&0xFF)/256.0f;
		float g=((rgb>>8)&0xFF)/256.0f;
		float b=(rgb&0xFF)/256.0f;
		return new util.Vector4f(r,g,b,w);
	}
	public util.Vector4f maskcolorspace() {
		float h=maskColor.x;//*256.0f;
		float s=maskColor.y;//*256.0f;
		float br=maskColor.z;//*256.0f;
		float w=1;
		int rgb=Color.HSBtoRGB(h,s,br);
		float r=((rgb>>16)&0xFF)/256.0f;
		float g=((rgb>>8)&0xFF)/256.0f;
		float b=(rgb&0xFF)/256.0f;
		return new util.Vector4f(r,g,b,w);
	}
	public void generate() { //Get all the particles that have passed us. Reinit them.
		ArrayList<Particle> past=new ArrayList<Particle>();
		for(Particle p : particles) {
			if(p.zt<main.BosonX.m.r.cam_translation[2]) {
				past.add(p);
			}
		}
		for(Particle n : past) {
			n.r=partcolor.x;
			n.g=partcolor.y;
			n.b=partcolor.z;
			n.a=partcolor.w;
			if(randomXR) {n.xr=Util.randint(180);}
			else {n.xr=0;}
			if(HSBrandom) {
				float f=0;
				if(Hmin!=Hmax) {
					f=(Util.randint((int) ((Hmax-Hmin)*1000f))/1000.0f)+Hmin;
				} else {
					f=Hmin;
				}
				this.partcolor.x=f;
				util.Vector4f rgb=partcolorspace();
				n.r=rgb.x;
				n.g=rgb.y;
				n.b=rgb.z;
				n.a=rgb.w;
			}
			n.zr=Util.randint(360);
			if(numPoles!=0 && xt>1) {
				n.zr=Util.randint(numPoles)*(360.0f/numPoles);
				n.xt=Util.randint((int) (200*xt))/100.0f - xt;
			}
			n.zt=main.BosonX.m.r.cam_translation[2]+Util.randint((int) main.BosonX.background_gen*1000)/1000.0f;
			
			n.w=w;
			n.l=l;
			n.initGeo();
			
			n.vz=-Util.randint((int) (v_variance*1000f))/1000f;
			
			if(t_min!=t_max) {
				n.vt=Util.randint((int) ((t_max-t_min)*1000f))/1000f+t_min;
			} else {
				n.vt=t_max;
			}
			
			n.yd=main.BosonX.particle_depth+Util.randint((int) (y_variance*1000f))/1000f;
		}
	}
	public void flushParticles() { //Reinit all particles
		for(Particle n : particles) {
			n.r=partcolor.x;
			n.g=partcolor.y;
			n.b=partcolor.z;
			n.a=partcolor.w;
			if(randomXR) {n.xr=Util.randint(180);}
			else {n.xr=0;}
			if(HSBrandom) {
				float f=0;
				if(Hmin!=Hmax) {
					f=(Util.randint((int) ((Hmax-Hmin)*1000f))/1000.0f)+Hmin;
				} else {
					f=Hmin;
				}
				this.partcolor.x=f;
				util.Vector4f rgb=partcolorspace();
				n.r=rgb.x;
				n.g=rgb.y;
				n.b=rgb.z;
				n.a=rgb.w;
			}
			n.zr=Util.randint(360);
			if(numPoles!=0 && xt>1) {
				n.zr=Util.randint(numPoles)*(360.0f/numPoles);
				n.xt=Util.randint((int) (200*xt))/100.0f - xt;
			}
			n.zt=main.BosonX.m.r.cam_translation[2]+Util.randint((int) main.BosonX.background_gen*1000)/1000.0f;
			
			n.w=w;
			n.l=l;
			n.initGeo();
			
			n.vz=-Util.randint((int) (v_variance*1000f))/1000f;
			
			if(t_min!=t_max) {
				n.vt=Util.randint((int) ((t_max-t_min)*1000f))/1000f+t_min;
			} else {
				n.vt=t_max;
			}
			
			n.yd=main.BosonX.particle_depth+Util.randint((int) (y_variance*1000f))/1000f;
		}
	}
	public void render() { //Render our background
		bgcolor.x+=HSBcycle/BosonX.m.frc;
		while(bgcolor.x>1) {
			bgcolor.x-=1;
		}
		while(bgcolor.x<0) {
			bgcolor.x+=1;
		}
		
		maskColor.x+=maskHSBcycle/BosonX.m.frc;
		while(maskColor.x>1) {
			maskColor.x-=1;
		}
		while(maskColor.x<0) {
			maskColor.x+=1;
		}
		if(density!=0) {
			particles.get(0).tex.applyBI();
			for(Particle p : particles) {
				p.render();
			}
		}
	}
}
