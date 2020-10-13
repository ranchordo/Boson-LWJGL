package util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

import main.BosonX;

public class MultiQuad {
	//It's like a bufferedimage, but it stores image pieces as individual Patches instead of one big image. It helps improve memory usage.
	public static final int HORIZONTAL=0;
	public static final int VERTICAL=1;
	public static final int MINIMUM=2;
	
	public HashMap<String,Patch> patches=new HashMap<String,Patch>();
	float xmax;
	float xmin;
	float ymax;
	float ymin;
	
	private float currentEnter=0;
	public Patch drawImage_raw(BufferedImage img, float x, float y, float s, String name, float r, boolean c) {
		Patch p=new Patch();
		p.tex=new Texture();
		p.tex.initBI(img);
		p.w=coordw((float)img.getWidth()/(float)BosonX.m.r.winW,img)*s;
		p.h=coordh((float)img.getHeight()/(float)BosonX.m.r.winH,img)*s;
		p.x=x;
		p.y=y;
		p.r=r;
		p.center=c;
		p.level=currentEnter;
		patches.put(name,p);
		currentEnter+=1;
		return p;
	}
	public Patch drawImage_raw(BufferedImage img, float x, float y, float w, float h, String name, float r, boolean c) {
		Patch p=new Patch();
		p.tex=new Texture();
		p.tex.initBI(img);
		p.w=w;
		p.h=h;
		p.x=x;
		p.y=y;
		p.r=r;
		p.center=c;
		p.level=currentEnter;
		patches.put(name,p);
		currentEnter+=1;
		return p;
	}
	
	public void drawImage(BufferedImage img, float x, float y, String name, float r, boolean c, boolean... layouts) {
		Patch p=drawImage_raw(img,coordx(x),coordy(y),1,name,r,c);
		if(layouts.length==2) {
			p.right=layouts[0];
			p.bottom=layouts[1];
		}
	}
	public void drawImage(BufferedImage img, float x, float y, float s, String name, float r, boolean c, int scaleMode, boolean... layouts) {
		Patch p=drawImage_raw(img,coordx(x),coordy(y),s*(scaleMode==0?BosonX.m.wrc:(scaleMode==1?BosonX.m.hrc:BosonX.m.mrc)),name,r,c);
		if(layouts.length==2) {
			p.right=layouts[0];
			p.bottom=layouts[1];
		}
	}
	public void drawImage(BufferedImage img, float x, float y, float w, float h, String name, float r, boolean c, boolean... layouts) {
		Patch p=drawImage_raw(img,coordx(x),coordy(y),coordw(w,img),coordh(h,img),name,r,c);
		if(layouts.length==2) {
			p.right=layouts[0];
			p.bottom=layouts[1];
		}
	}
	
	public void drawImage(Color col, float x, float y, float w, float h, String name, boolean c, boolean... layouts) {
		BufferedImage img=new BufferedImage(10,10,BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g=img.getGraphics();
		g.setColor(col);
		g.fillRect(0,0,img.getWidth(),img.getHeight());
		Patch p=drawImage_raw(img,coordx(x),coordy(y),coordw(w,img),coordh(h,img),name,0,c);
		if(layouts.length==2) {
			p.right=layouts[0];
			p.bottom=layouts[1];
		}
		g.dispose();
	}
	public void onFrame() {
		currentEnter=0;
		patches.clear();
	}
	public void removeImage(String name) {
		patches.remove(name);
	}
	public float coordx(float x) {
		float fx=(2.0f*x)-1.0f;
		float retx=xmax*fx;
		return retx;
	}
	public float coordy(float y) {
		float fy=(2.0f*y)-1.0f;
		float rety=-ymax*fy;
		return rety;
	}
	public float coordw(float w, BufferedImage img) {
		if(w==-1) {
			w=img.getWidth()/BosonX.m.r.winW;
		}
		return xmax*w*2.0f;
	}
	public float coordh(float h, BufferedImage img) {
		if(h==-1) {
			h=img.getHeight()/BosonX.m.r.winH;
		}
		return -ymax*h*2.0f;
	}
	public void calcConsts() {
		xmax=(float) (2*(BosonX.m.aspectRatio)*Math.tan(Math.toRadians(BosonX.m.r.fov/2.0f)));
		xmin=(float) (-2*(BosonX.m.aspectRatio)*Math.tan(Math.toRadians(BosonX.m.r.fov/2.0f)));
		ymax=(float) (2*Math.tan(Math.toRadians(BosonX.m.r.fov/2.0f)));
		ymin=(float) (-2*Math.tan(Math.toRadians(BosonX.m.r.fov/2.0f)));
	}
	public void render() {
		glDisable(GL_DEPTH_TEST);
		LinkedHashMap<String,Patch> sorted=new LinkedHashMap<String,Patch>();
		patches.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(x->sorted.put(x.getKey(),x.getValue()));
		for(Map.Entry<String, Patch> entry : sorted.entrySet()) {
			entry.getValue().render();
		}
		glEnable(GL_DEPTH_TEST);
	}
}
