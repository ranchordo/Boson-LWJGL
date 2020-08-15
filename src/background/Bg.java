package background;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;

import javax.imageio.ImageIO;

import main.BosonX;

public class Bg {
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
	public float Hmin=0;
	public float Hmax=0;
	
	public BufferedImage fade;
	
	public Bg() {
		try {
			fade=ImageIO.read(new File("resources/particle_fade.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
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
	public void populate() {
		for(int i=0;i<density*BosonX.background_gen;i++) {
			Particle p=new Particle();
			particles.add(p);
			p.tex.initBI(this.fade);
			p.tex.applyBI();
			p.lighting=this.lightParticles;
		}
	}
	public util.Vector4f partcolorspace() {
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
	public void generate() {
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
			if(HSBrandom) {
				float f=0;
				if(Hmin!=Hmax) {
					f=(BosonX.m.randint((int) ((Hmax-Hmin)*1000f))/1000.0f)+Hmin;
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
			n.zr=main.BosonX.m.randint(360);
			n.zt=main.BosonX.m.r.cam_translation[2]+main.BosonX.m.randint((int) main.BosonX.background_gen*1000)/1000.0f;
			
			n.w=w;
			n.l=l;
			n.initGeo();
			
			n.vz=-main.BosonX.m.randint((int) (v_variance*1000f))/1000f;
			
			if(t_min!=t_max) {
				n.vt=main.BosonX.m.randint((int) ((t_max-t_min)*1000f))/1000f+t_min;
			} else {
				n.vt=t_max;
			}
			
			n.yd=main.BosonX.particle_depth+main.BosonX.m.randint((int) (y_variance*1000f))/1000f;
		}
	}
	public void initTransforms() {
		for(Particle p : particles) {
			p.zr=main.BosonX.m.randint(360);
			p.zt=main.BosonX.m.r.cam_translation[2]+main.BosonX.m.randint((int) main.BosonX.background_gen*1000)/1000.0f;
		}
	}
	public void render() {
		for(Particle p : particles) {
			p.render();
		}
	}
}
