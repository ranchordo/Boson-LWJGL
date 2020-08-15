package main;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Graphics;
import java.nio.FloatBuffer;
import java.util.*;

import org.lwjgl.opengl.GL;

public class Renderer {
	public ArrayList<Cell> celllist=new ArrayList<Cell>();
	public float[] cam_translation= {0.0f,0.0f,0.0f};
	util.Rotation cam_rotation=new util.Rotation(180,0,0);
	public int winW=1920;
	public int winH=1080;
	
	private util.Texture hudtex;
	
	public float fov=70;
	
	float lightAmbient[] = { 0.2f, 0.2f, 0.2f, 1.0f };// Ambient Light Values
    float lightDiffuse[] = { 0.8f, 0.8f, 0.8f, 1.0f };// Diffuse Light Values
    float lightPosition[] = { 0.0f, 1.0f, 0.0f, 0.0f };// Light Position
	public void fov_encap(float f) {
		glPerspective(f,((float)winW)/((float)winH),0.1f,500f);
	}
	public void rotate_encap(float a, float x, float y, float z) {
		glRotatef(a,x,y,z);
	}
	public void scale_encap(float x, float y, float z) {
		glScalef(x,y,z);
	}
	public void translate_encap(float x, float y, float z) {
		glTranslatef(x,y,z);
	}
	public void material_encap(int face, int pname, FloatBuffer param) {
		glMaterialfv(face,pname,param);
	}
	private static void glPerspective(float fov, float aspect, float zNear, float zFar) {
	    float fH = (float) Math.tan(fov / 360 * Math.PI) * zNear;
	    float fW = fH * aspect;
	    glFrustum( -fW, fW, -fH, fH, zNear, zFar );
	}
	public void translateCamera(float x, float y, float z) {
		float dx=(float) (x*Math.cos(Math.toRadians(cam_rotation.x))) + (float) (-z*Math.sin(Math.toRadians(cam_rotation.x)));
		float dz=(float) (x*Math.sin(Math.toRadians(cam_rotation.x))) + (float) (z*Math.cos(Math.toRadians(cam_rotation.x)));
		cam_translation[0]+=dx;
		cam_translation[1]+=y;
		cam_translation[2]+=dz;
	}
	public void rotateCamera(float x, float y, float z) {
		cam_rotation.rotate(x, y, z);
	}
	public float abs(float a) {
		if(-a>a) {
			return -a;
		} else {
			return a;
		}
	}
	public long initGL() {
		if(!glfwInit()) {
			System.err.println("GLFWInit Error.");
			System.exit(1);
		}
		long win=glfwCreateWindow(winW,winH,"Boson-Awful",0,0);
		glfwShowWindow(win);
		glfwMakeContextCurrent(win);
		GL.createCapabilities();
		//glClearColor(1.0f,0.0f,0.0f,0.0f);
		
		hudtex=new util.Texture();
		
        glShadeModel(GL_SMOOTH);
        glEnable(GL_LIGHTING);
        
        glEnable(GL_LIGHT1);
        //glEnable(GL_LIGHT2);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        //glLightModelfv(GL_LIGHT_MODEL_AMBIENT,asFloatBuffer(new float[] {0.1f,0.1f,0.1f,1f}));
        glLightfv(GL_LIGHT1, GL_AMBIENT, BosonX.asFloatBuffer(lightAmbient));
        glLightfv(GL_LIGHT1, GL_DIFFUSE, BosonX.asFloatBuffer(lightDiffuse));
        
        glEnable(GL_COLOR_MATERIAL);
        glColorMaterial(GL_FRONT_AND_BACK, GL_POSITION);
        glEnable(GL_COLOR_MATERIAL);
        
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 
        glClearDepth(1.0); 
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL); 

        glMatrixMode(GL_PROJECTION_MATRIX); 
        glLoadIdentity();
        glPerspective(fov,((float)winW)/((float)winH),0.1f,500f);
        glMatrixMode(GL_MODELVIEW_MATRIX);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        return win;
	}
	private void renderLights() {
		glLightfv(GL_LIGHT1, GL_POSITION, BosonX.asFloatBuffer(lightPosition));
	}
	private void renderAll() {
		for(Cell cell : celllist) {
			this.renderGObject(cell.geometry);
		}
	}
	private String handleAllEffects() {
		String ret="";
		float cam_z=cam_translation[2];
		for(Cell cell : celllist) {
			char r=cell.handleEffects(cam_z,false);
			if(r!='!') {
				ret+=r;
			}
		}
		return ret;
	}
	private void handleRemoving() {
		float cam_z=cam_translation[2];
		boolean none=false;
		while(!none) {
			none=true;
			for(int j=0;j<celllist.size();j++) {
				if(cam_z>(celllist.get(j).zrel+BosonX.cell_length+10)) {
					celllist.remove(j);
					none=false;
					break;
				}
			}
		}
	}
	private int jc=-1;
	private long js=0;
	private long animc=0;
	public boolean on=true;
	private boolean r_rise=false;
	private boolean l_rise=false;
	private boolean r_anim=false;
	private boolean l_anim=false;
	public float factor=6f;
	
	private void speedup() {
		float frac=BosonX.m.energy/100.0f;
		BosonX.m.speed=frac*(BosonX.m.speed1-BosonX.m.speed0)+BosonX.m.speed0;
	}
	
	private void movement(long win) {
		cam_translation[2]+=BosonX.m.speed;
		int numpoles=celllist.get(0).numpoles;
		if(glfwGetKey(win,GLFW_KEY_UP)==GL_TRUE || glfwGetKey(win,GLFW_KEY_RIGHT)==GL_TRUE || glfwGetKey(win,GLFW_KEY_LEFT)==GL_TRUE) {
			if(jc==-1 && on) {jc=0; js=millis();}
		} else {
			jc=-1;
		}
		if((glfwGetKey(win,GLFW_KEY_RIGHT)==GL_TRUE && !r_rise && on) || (glfwGetKey(win,GLFW_KEY_RIGHT)==GL_TRUE && glfwGetKey(win,GLFW_KEY_UP)==GL_TRUE && !r_rise && !l_anim && !r_anim && BosonX.allowMultiJumps)) {
			r_rise=true;
			r_anim=true;
			animc=0;
			while(BosonX.m.pole>celllist.get(0).numpoles) {
				BosonX.m.pole-=celllist.get(0).numpoles;
			}
		}
		if(glfwGetKey(win,GLFW_KEY_RIGHT)==GL_FALSE) {r_rise=false;}
		if((glfwGetKey(win,GLFW_KEY_LEFT)==GL_TRUE && !l_rise && on) || (glfwGetKey(win,GLFW_KEY_LEFT)==GL_TRUE && glfwGetKey(win,GLFW_KEY_UP)==GL_TRUE && !l_rise && !l_anim && !r_anim && BosonX.allowMultiJumps)) {
			l_rise=true;
			l_anim=true;
			animc=0;
			while(BosonX.m.pole<0) {
				BosonX.m.pole+=celllist.get(0).numpoles;
			}
		}
		if(glfwGetKey(win,GLFW_KEY_LEFT)==GL_FALSE) {l_rise=false;}
		if(r_anim) {
			BosonX.m.pole+=1f/factor;
			BosonX.m.pole=Math.round(BosonX.m.pole*factor)/factor;
			while(BosonX.m.pole>=numpoles) {BosonX.m.pole-=numpoles;}
			while(BosonX.m.pole<0) {BosonX.m.pole+=numpoles;}
			for(Cell c : celllist) {
				c.rotate(1f/factor);
				if(c.doneGenerating) {c.pole=Math.round(c.pole*factor)/factor;}
				if(!c.rotationDefined) {
					c.initGeo();
				}
			}
			animc++;
			if(animc>=factor) {
				r_anim=false;
			}
		}
		if(l_anim) {
			BosonX.m.pole-=1f/factor;
			BosonX.m.pole=Math.round(BosonX.m.pole*factor)/factor;
			while(BosonX.m.pole>=numpoles) {BosonX.m.pole-=numpoles;}
			while(BosonX.m.pole<0) {BosonX.m.pole+=numpoles;}
			for(Cell c : celllist) {
				c.rotate(-1f/factor);
				if(c.doneGenerating) {c.pole=Math.round(c.pole*factor)/factor;}
				if(!c.rotationDefined) {
					c.initGeo();
				}
			}
			animc++;
			if(animc>=factor) {
				l_anim=false;
			}
		}
		
		if(jc>=0) {jc=(int) (millis()-js);}
		
		
		float vy=-BosonX.m.gravity_i;
		float t0=BosonX.m.gravity_i+BosonX.m.jump_relative;
		float vyr=0;
		if(jc>=0) {
			vyr=((-t0)/BosonX.m.jump_duration)*jc+t0;
			if(vyr<0) {vyr=0;}
		}
		vy+=vyr;
		if(cam_translation[1]<-BosonX.depth(-1)) {
			vy=-BosonX.m.gravity_o;
		}
		on=false;
		for(Cell c : celllist) {
			if(cam_translation[2]>c.zrel-0.3 && cam_translation[2]<c.zrel+0.4+BosonX.cell_length && (c.pole<0.5f || c.pole>c.numpoles-0.5f)) {
				if(cam_translation[1]>-BosonX.depth(c.level)+2 && cam_translation[1]+vy<=-BosonX.depth(c.level)+2) {
					vy=0f;
					jc=-1;
					on=true;
				}
				if(cam_translation[1]>-BosonX.depth(c.level)+1 && cam_translation[1]+vy<=-BosonX.depth(c.level)+1) {
					vy=1f;
					jc=-1;
					on=true;
				}
			}
		}
		cam_translation[1]+=vy;
	}
	public long millis() {
		return System.nanoTime()/1000000;
	}
	public void pc() {
		ArrayList<Integer> pc=new ArrayList<Integer>();
		for(Cell c : celllist) {
			boolean u=true;
			for(int pci : pc) {
				if(pci==c.enter) {
					u=false;
				}
			}
			if(u) {
				pc.add(c.enter);
			}
		}
		System.out.println(pc.size());
	}
	private boolean menu=true;
	public boolean Main() {
		//initObj();
		boolean back=false;
		long win=initGL();
		while (!glfwWindowShouldClose(win)) {
			//--------MAINLOOP---------//
			if(glfwGetKey(win,GLFW_KEY_ESCAPE)==GL_TRUE) {
				glfwSetWindowShouldClose(win,true);
			}
			if(glfwGetKey(win,GLFW_KEY_A)==GL_TRUE) {
				back=true;
				menu=true;
			}
			glfwPollEvents();
			if(menu) {
				glClearColor(0,0,0,1);
			} else {
				glClearColor(BosonX.m.background.bgcolor.x,BosonX.m.background.bgcolor.y,BosonX.m.background.bgcolor.z,BosonX.m.background.bgcolor.w);
			}
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			long t1=millis();
			if(!back && !menu) {
				speedup();
				BosonX.m.handleRenderAdd();
				handleAllEffects();
				handleRemoving();
				BosonX.m.handle_generation();
				if(handleDeath(win)) {back=true;}
				movement(win);
				glPushMatrix();
				cam_rotation.renderRotation();
				glTranslatef(-cam_translation[0],-cam_translation[1],-cam_translation[2]);
				//lightPosition[2]=-cam_translation[2]-30;
				BosonX.m.background.generate();
				BosonX.m.background.render();
				renderLights();
				renderAll();
				glPopMatrix();
				glPushMatrix();
				Graphics g=hudtex.initBI();
				g.drawString(Float.toString((float) Math.round(BosonX.m.energy*10)/10f)+"%", 20, 60);
				hudtex.applyBI();
				hudtex.bind();
				glEnable(GL_TEXTURE_2D);
				glDisable(GL_LIGHTING);
				glDisable(GL_DEPTH_TEST);
				glBegin(GL_QUADS);
					glColor4f(5,5,5,0.5f);
					glNormal3f(0,0,1);
					glTexCoord2f(1,0);
					glVertex3f(2.4f,1.3f,-2);
					glTexCoord2f(1,1);
					glVertex3f(2.4f,1f,-2);
					glTexCoord2f(0,1);
					glVertex3f(1f,1f,-2);
					glTexCoord2f(0,0);
					glVertex3f(1f,1.3f,-2);
				glEnd();
				glDisable(GL_TEXTURE_2D);
				glEnable(GL_LIGHTING);
				glEnable(GL_DEPTH_TEST);
			}
			glPopMatrix();
			if(back) {
				back=false;
				menu=true;
				BosonX.m.doFont(30);
				BosonX.m.energy=0;
				//Cleanup
				BosonX.m.holding=new ArrayList<Cell>();
				BosonX.m.background.particles=new ArrayList<background.Particle>();
				BosonX.m.background.populate();
				cam_translation=new float[] {0,0,0};
				cam_rotation=new util.Rotation(180,0,0);
				celllist=new ArrayList<Cell>();
				BosonX.m.pole=0;
				BosonX.m.enter=0;
			}
			if(menu) {
				menu(win);
			}
			glfwSwapBuffers(win);
			long t2=millis();
			int delta=(int) (t2-t1);
			int targ_delta=1000/60;
			//float fr=1000/delta;
			/*
			System.out.print(((float)(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/(float)Runtime.getRuntime().totalMemory())*100.0f);
			System.out.print("% of ");
			System.out.print(Runtime.getRuntime().totalMemory()/1048576.0f);
			System.out.print("M, FR=");
			System.out.print(fr);
			System.out.println("fps");
			*/
			if(targ_delta>delta) {
				try {
					Thread.sleep(targ_delta-delta);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		glfwTerminate();
		return back;
	}
	private int primec=-1;
	private long primes=0;
	public void menu(long win) {
		Graphics g=hudtex.initBI(600,600);
		g.drawString("Choose your level... 1 - 6", 10, 60);
		if(glfwGetKey(win,GLFW_KEY_1)==GL_TRUE && primec==-1) {
			BosonX.m.cp.point(1);
			menu=false;
		}
		if(glfwGetKey(win,GLFW_KEY_2)==GL_TRUE && primec==-1) {
			BosonX.m.cp.point(2);
			menu=false;
		}
		if(glfwGetKey(win,GLFW_KEY_3)==GL_TRUE && primec==-1) {
			BosonX.m.cp.point(3);
			menu=false;
		}
		if(glfwGetKey(win,GLFW_KEY_4)==GL_TRUE && primec==-1) {
			BosonX.m.cp.point(4);
			menu=false;
		}
		if(glfwGetKey(win,GLFW_KEY_5)==GL_TRUE && primec==-1) {
			BosonX.m.cp.point(5);
			menu=false;
		}
		if(glfwGetKey(win,GLFW_KEY_6)==GL_TRUE && primec==-1) {
			BosonX.m.cp.point(6);
			menu=false;
		}
		if(!menu) {
			primec=0;
			primes=millis();
			menu=true;
		}
		if(primec!=-1) {
			g=hudtex.initBI(600,600);
			g.drawString("Priming detector "+Integer.toString(BosonX.m.cp.level)+"...", 10, 60);
			g.drawString("Get ready...",10,120);
			primec=(int) (millis()-primes);
		}
		hudtex.applyBI();
		hudtex.bind();
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
			glColor4f(5,5,5,0.5f);
			glNormal3f(0,0,1);
			glTexCoord2f(1,0);
			glVertex3f(1f,1f,-2);
			glTexCoord2f(1,1);
			glVertex3f(1f,-1f,-2);
			glTexCoord2f(0,1);
			glVertex3f(-1f,-1f,-2);
			glTexCoord2f(0,0);
			glVertex3f(-1f,1f,-2);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
		if(primec>=1000) {
			menu=false;
			primes=0;
			primec=-1;
			Spec.loadSpec(BosonX.m.cp.level);
			BosonX.m.doFont(42);
			BosonX.m.start_time=this.millis();
			BosonX.m.addPattern(0,0);
		}
	}
	private boolean handleDeath(long win) {
		if(glfwGetKey(win,GLFW_KEY_ENTER)==GL_TRUE) {
			cam_translation[1]=-1000;
		}
		if(cam_translation[1]<-25) {
			return true;
		}
		for(Cell c : celllist) {
			switch(c.type) {
			case 'E':
			case 'G':
			case 'C':
			case 'F':
			case 'P':
				if(cam_translation[2]>c.zrel-0.3 && cam_translation[2]<c.zrel+0.4+BosonX.cell_length && (c.pole<0.5f || c.pole>c.numpoles-0.5f)) {
					if(cam_translation[1]<-BosonX.depth(c.level) && cam_translation[1]>-BosonX.depth(c.level)-BosonX.cell_thickness) {
						return true;
					}
				}
				break;
			case 'B':
				if(cam_translation[2]>c.zrel-0.3 && cam_translation[2]<c.zrel+0.4+BosonX.cell_length && (c.pole<0.5f || c.pole>c.numpoles-0.5f)) {
					if(cam_translation[1]<-BosonX.depth(BosonX.B_height) && cam_translation[1]>-BosonX.depth(c.level)) {
						return true;
					}
				}
				break;
			}
		}
		return false;
	}
	public void renderQuad(util.Quad q) {
		glBegin(GL_QUADS);
			glColor4f(q.color[0],q.color[1],q.color[2],q.color[3]);
			glNormal3f(q.normal[0],q.normal[1],q.normal[2]);
			for(float[] vert : q.vertices) {
				glVertex3f(vert[0],vert[1],vert[2]);
			}
		glEnd();
	}
	public void renderTri(util.Tri t) {
		glBegin(GL_TRIANGLES);
			glColor4f(t.color[0],t.color[1],t.color[2],t.color[3]);
			glNormal3f(t.normal[0],t.normal[1],t.normal[2]);
			for(float[] vert : t.vertices) {
				glVertex3f(vert[0],vert[1],vert[2]);
			}
		glEnd();
	}
	public void renderGObject(util.GObject obj) {
		glPushMatrix();
		if(obj.lighting==0) {glDisable(GL_LIGHTING);}
		obj.renderTransforms();
		for(util.Quad q : obj.quads) {
			this.renderQuad(q);
		}
		for(util.Tri t : obj.tris) {
			this.renderTri(t);
		}
		if(obj.lighting==0) {glEnable(GL_LIGHTING);}
		glPopMatrix();
	}
	
}