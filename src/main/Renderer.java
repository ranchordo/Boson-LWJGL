package main;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import audio.Audio;
import audio.Source;
import background.Particle;
import util.Atlas;
import util.MultiQuad;
import util.PrefetchedImages;
import util.Shader;
import util.Texture;
import static util.MultiQuad.*;
import util.Util;

public class Renderer {
	public ArrayList<Cell> celllist=new ArrayList<Cell>(); //Render routine
	public float[] cam_translation= {0.0f,0.0f,0.0f};
	util.Rotation cam_rotation=new util.Rotation(180,0,0);
	public int windowedW=864;
	public int windowedH=486;
	public int windowedX=200;
	public int windowedY=200;
	public int fullW=0;
	public int fullH=0;
	public int winW=1920;
	public int winH=1080;
	
	public int targ_fr=60;
	
	protected util.Texture hudtex; //Texture for the HUD
	
	public float fov=70;
	
	float lightAmbient[] = { 0.2f, 0.2f, 0.2f, 1.0f };// Ambient Light Values
    float lightDiffuse[] = { 0.8f, 0.8f, 0.8f, 1.0f };// Diffuse Light Values
    float lightPosition[] = { 0.0f, 1.0f, 0.4f, 0.0f };// Light Position
    
    //I defined these encap functions because they made things a little easier. I'm not really sure why.
	public void fov_encap(float f) {
		glMatrixMode(GL_PROJECTION_MATRIX); 
	    glLoadIdentity();
	    glPerspective(f,((float)winW)/((float)winH),0.1f,500f);
	    glMatrixMode(GL_MODELVIEW_MATRIX);
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
	public float abs(float a) { //I didn't realize Math had this all along. Hey, at least you don't need to cast from float to double everywhere with this one.
		if(-a>a) {
			return -a;
		} else {
			return a;
		}
	}
	public void getFullscDimensions() {
		GLFWVidMode d=glfwGetVideoMode(glfwGetPrimaryMonitor());
		this.fullW=d.width();
		this.fullH=d.height();
		this.targ_fr=d.refreshRate();
	}
	public long doWindow(boolean fullscreen, long prevwin) {
		if(!glfwInit()) {
			System.err.println("GLFWInit Error.");
			System.exit(1);
		}
		System.out.println("InitGL: Getting specifications of your monitor:");
		getFullscDimensions();
		winW=fullW;
		winH=fullH;
		BosonX.m.aspectRatio=(float)winW/(float)winH;
		BosonX.m.hrc=winH/1080.0f;
		BosonX.m.wrc=winW/1920.0f;
		BosonX.m.mrc=Math.min(BosonX.m.hrc,BosonX.m.wrc);
		System.out.println("Resolution: "+winW+"x"+winH);
		System.out.println("Refresh rate / target frame rate: "+targ_fr+"Hz");
		long win;
		if(fullscreen) {
			win=glfwCreateWindow(winW,winH,"Boson-LWJGL",glfwGetPrimaryMonitor(),prevwin);
		} else {
			win=glfwCreateWindow(windowedW,windowedH,"Boson-LWJGL",0,prevwin);
			winW=windowedW;
			winH=windowedH;
		}
		glfwShowWindow(win);
		glfwMakeContextCurrent(win);
		GL.createCapabilities();
		return win;
	}
	GLFWWindowSizeCallback onResize=new GLFWWindowSizeCallback() {
    	public void invoke(long win, int w,  int h) {
    		if(w==fullW && h==fullH) {
    			glfwSetWindowMonitor(win,glfwGetPrimaryMonitor(),0,0,w,h,targ_fr);
    			fullscreen=true;
    		} else {
    			glfwSetWindowMonitor(win,0,windowedX,windowedY,w,h,targ_fr);
    			windowedW=w;
    			windowedH=h;
    			fullscreen=false;
    		}
			winW=w;
			winH=h;
			BosonX.m.aspectRatio=(float)winW/(float)winH;
			BosonX.m.hrc=winH/1080.0f;
			BosonX.m.wrc=winW/1920.0f;
			BosonX.m.mrc=Math.min(BosonX.m.hrc,BosonX.m.wrc);
			glViewport(0,0,winW,winH);
			fov_encap(fov);
			gui.calcConsts();
			Texture.clearCache();
    	}
    };
    GLFWWindowPosCallback onMove=new GLFWWindowPosCallback() {
    	public void invoke(long win, int x, int y) {
    		windowedX=x;
			windowedY=y;
    		if(fullscreen) {
    			glfwSetWindowMonitor(win,glfwGetPrimaryMonitor(),x,y,winW,winH,targ_fr);
    		} else {
    			glfwSetWindowMonitor(win,0,x,y,winW,winH,targ_fr);
    		}
    	}
    };
	public long initGL(boolean fullscreen) { //Initialize our OpenGL bindings (Camera...)
		long win=doWindow(fullscreen,0);
		//glClearColor(1.0f,0.0f,0.0f,0.0f);
		
		hudtex=new util.Texture();
		
        glShadeModel(GL_SMOOTH);
        
        glEnable(GL_LIGHT1);
        //glEnable(GL_LIGHT2);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        //glLightModelfv(GL_LIGHT_MODEL_AMBIENT,asFloatBuffer(new float[] {0.1f,0.1f,0.1f,1f}));
        glLightfv(GL_LIGHT1, GL_AMBIENT, Util.asFloatBuffer(lightAmbient));
        glLightfv(GL_LIGHT1, GL_DIFFUSE, Util.asFloatBuffer(lightDiffuse));
        
        glEnable(GL_COLOR_MATERIAL);
        glColorMaterial(GL_FRONT_AND_BACK, GL_POSITION);
        glEnable(GL_COLOR_MATERIAL);

        
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 
        glClearDepth(1.0); 
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL); 
        
        glEnable(GL_STENCIL_TEST);
        glDisable(GL_CULL_FACE);

        fov_encap(fov);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        
        glfwSetInputMode(win, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        glfwSetWindowSizeCallback(win,onResize);
        System.out.println("InitGL: Completed successfully. Window "+win+" created.");
        return win;
	}
	public Source initAL() { //Init audio
		Audio.init();
		Audio.initListener();
		return new Source();
	}
	private void renderLights() { //Lights...
		glLightfv(GL_LIGHT1, GL_POSITION, Util.asFloatBuffer(lightPosition));
	}
	private void renderAll(int a) {//Action...
		for(Cell cell : celllist) {
			this.renderGObject(a,cell.geometry);
		}
	}
	private void renderAll(int a,char t) {//Render all of a type (Action...)
		for(Cell cell : celllist) {
			if(cell.type==t) {this.renderGObject(a,cell.geometry);}
		}
	}
	private String handleAllEffects() { //For each cell in our render routine, handle the effects.
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
	private void handleRemoving() { //Remove the cell if they pass our view. Why waste resources when you can not?
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
	private float jc=-1;
	private long animc=0;
	public boolean on=true;
	private boolean r_rise=false;
	private boolean l_rise=false;
	private boolean r_anim=false; //Are we animating right
	private boolean l_anim=false; //Are we animating left
	public float defaultfactor=6;
	public float factor=(int) (defaultfactor); //How many steps in our animation
	
	private void speedup() { //Process our speedup
		float frac=BosonX.m.energy/100.0f;
		BosonX.m.speed=frac*(BosonX.active.speed1-BosonX.active.speed0)+BosonX.active.speed0;
	}
	
	private void movement(long win) { //Physics / movement engine!
		cam_translation[2]+=(BosonX.m.speed/BosonX.m.frc); //Do the moving forward thing
		int numpoles=BosonX.m.cp.numpoles;
		if(glfwGetKey(win,GLFW_KEY_UP)==GL_TRUE || glfwGetKey(win,GLFW_KEY_RIGHT)==GL_TRUE || glfwGetKey(win,GLFW_KEY_LEFT)==GL_TRUE) {
			if(jc==-1 && on) {jc=0;} //Start our jump counter
		} else {
			jc=-1; //Stop the jump counter
		}
		if((glfwGetKey(win,GLFW_KEY_RIGHT)==GL_TRUE && !r_rise && on && !l_anim) || (glfwGetKey(win,GLFW_KEY_RIGHT)==GL_TRUE && glfwGetKey(win,GLFW_KEY_UP)==GL_TRUE && !r_rise && !l_anim && !r_anim && BosonX.active.allowMultiJumps)) {
			//Start our animation
			r_rise=true; //Only rising edges are accepted
			r_anim=true;
			animc=0;
			while(BosonX.m.pole>celllist.get(0).numpoles) {
				BosonX.m.pole-=celllist.get(0).numpoles;
			}
		}
		if(glfwGetKey(win,GLFW_KEY_RIGHT)==GL_FALSE) {r_rise=false;}
		if((glfwGetKey(win,GLFW_KEY_LEFT)==GL_TRUE && !l_rise && on && !r_anim) || (glfwGetKey(win,GLFW_KEY_LEFT)==GL_TRUE && glfwGetKey(win,GLFW_KEY_UP)==GL_TRUE && !l_rise && !l_anim && !r_anim && BosonX.active.allowMultiJumps)) {
			//Start our animation
			l_rise=true; //Only rising edge values are accepted
			l_anim=true;
			animc=0;
			while(BosonX.m.pole<0) {
				BosonX.m.pole+=celllist.get(0).numpoles;
			}
		}
		if(glfwGetKey(win,GLFW_KEY_LEFT)==GL_FALSE) {l_rise=false;}
		if(r_anim) {
			//Animate our pole
			BosonX.m.pole+=1f/factor;
			BosonX.m.pole=Math.round(BosonX.m.pole*factor)/factor;
			while(BosonX.m.pole>=numpoles) {BosonX.m.pole-=numpoles;}
			while(BosonX.m.pole<0) {BosonX.m.pole+=numpoles;}
			for(Cell c : celllist) {
				c.rotate(1f/factor);
				if(c.doneGenerating) {c.pole=Math.round(c.pole*factor)/factor;}
				if(!c.rotationDefined) {
					c.doRotations();
				}
			}
			animc++;
			if(animc>=factor) {
				r_anim=false;
			}
		}
		if(l_anim) {
			//Animate our pole
			BosonX.m.pole-=1f/factor;
			BosonX.m.pole=Math.round(BosonX.m.pole*factor)/factor;
			while(BosonX.m.pole>=numpoles) {BosonX.m.pole-=numpoles;}
			while(BosonX.m.pole<0) {BosonX.m.pole+=numpoles;}
			for(Cell c : celllist) {
				c.rotate(-1f/factor);
				if(c.doneGenerating) {c.pole=Math.round(c.pole*factor)/factor;}
				if(!c.rotationDefined) {
					c.doRotations();
				}
			}
			animc++;
			if(animc>=factor) {
				l_anim=false;
			}
		}
		
		if(!r_anim && !l_anim) {factor=(int) (defaultfactor*BosonX.m.frc);} //Make the animation take the right amt of frames
		
		if(jc>=0) {jc+=1.0f/BosonX.m.frc;} //Jump counter
		
		
		float vy=-BosonX.active.gravity_i; //Initial vertical velocity
		float t0=BosonX.active.jump_relative+BosonX.active.gravity_i; //Vy as the jump starts
		float vyr=0;
		if(jc>=0) {
			vyr=((-t0)/BosonX.active.jump_duration)*jc+t0;
			if(vyr<0) {vyr=0;}
			//Jump vertical velocity offset
		}
		vy+=vyr; //Apply it
		if(cam_translation[1]<-BosonX.depth(-1)) {
			vy=-BosonX.active.gravity_o; //Use outer gravity if we're outside our level
		}
		on=false;
		for(Cell c : celllist) {
			if(cam_translation[2]>c.zrel-0.3 && cam_translation[2]<c.zrel+0.4+c.length && (c.pole<0.5f || c.pole>c.numpoles-0.5f)) { //If we're on top of this cell
				if(c.type!='B') { //Not a barrier
					if(cam_translation[1]>-c.height()+2 && cam_translation[1]+vy<=-c.height()+2) { //If our next frame will take us through the top
						vy=0f; //Cancel our vy
						jc=-1;
						on=true;
						if(c.type=='I') {
							float cpassed=BosonX.m.speed/BosonX.cell_length;
							vy=cpassed*BosonX.incline;
							//Process some incline/decline stuff
						}
						if(c.type=='D') {
							float cpassed=BosonX.m.speed/BosonX.cell_length;
							vy=-cpassed*BosonX.incline;
						}
					}
					if(cam_translation[1]>-c.height()+1 && cam_translation[1]+vy<=-c.height()+1) { //If we hit slightly below the top, push us back up there.
						vy=1f*BosonX.m.frc;
						jc=-1;
						on=true;
					}
				} else { // A barrier!
					if(cam_translation[1]>-BosonX.depth(BosonX.B_height)+2 && cam_translation[1]+vy<=-BosonX.depth(BosonX.B_height)+2) { //The top is BosonX.B_height
						vy=0f;
						jc=-1;
						on=true;
					}
					if(cam_translation[1]>-BosonX.depth(BosonX.B_height)+1 && cam_translation[1]+vy<=-BosonX.depth(BosonX.B_height)+1) {
						vy=1f*BosonX.m.frc;
						jc=-1;
						on=true;
					}
				}
			}
		}
		cam_translation[1]+=vy/BosonX.m.frc;
	}
	public long millis() { //Wow. So advanced.
		return System.nanoTime()/1000000;
	}
	public int celllistLength() {
		//What's the highest zrel_id in celllist?
		int max_zrel_id=0;
		for(Cell c : celllist) {
			if(c.zrel_id>max_zrel_id) {
				max_zrel_id=c.zrel_id;
			}
		}
		int cam_cell=Math.round(cam_translation[2]/BosonX.cell_length);
		return max_zrel_id-cam_cell;
	}
	private static boolean lighting=true;
	private static boolean rotationalLighting=false;
	public static void setRL(boolean l) {
		rotationalLighting=l;
		if(!l) {
			BosonX.m.r.cellsh.setUniform("rot",0);
		}
	}
	public void resetShaders() {
		cellsh_l=new Shader("cell");
		menush=new Shader("menu");
		cellsh_nl=new Shader("cellnl");
	}
	public static void setLighting(boolean l) {
		if(l) {
			BosonX.m.r.cellsh=BosonX.m.r.cellsh_l;
			lighting=true;
		} else {
			BosonX.m.r.cellsh=BosonX.m.r.cellsh_nl;
			lighting=false;
		}
		BosonX.m.r.cellsh.bind();
	}
	public void pc() { //Pattern counter!? When did I implement this!?
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
	
	//All the unrelated stuff for our main loop
	protected boolean menu=true;
	protected int music;
	protected int rumble_b;
	protected int charge_b;
	protected int explode_b;
	protected int critical_b;
	
	protected int uiback;
	protected int uibleep;
	protected int uiselect;
	protected int uiintro;
	protected int uino;
	
	protected String dbg;
	
	Source rumble;
	Source charge;
	Source explode;
	Source critical;
	Source ui;
	Source s;
	
	Atlas font=new Atlas("/font.png",5);
	Atlas font2=new Atlas("/font_2x.png",5);
	Atlas digits=new Atlas("/digits.png",5,"1234567890.%");
	Atlas font_large=new Atlas("/font_large.png",5,"ABCDEFGHIJKLMNOPQRSTUVWXYZ:");
	
	
	
	
	private boolean musicHandled=false;
	private float cc=-1;
	
	Shader cellsh=null;
	Shader cellsh_l=null;
	Shader cellsh_nl=null;
	Shader menush=null;
	
	boolean fullscreen=true;
	
	public boolean back=false;
	private PrefetchedImages p=new PrefetchedImages();
	public boolean Main() { //I have no idea why I made this return a boolean
		//Add assets to our PrefetchedImages object so we can get 'em later
		p.add("title_bg","/title_bg.png");
		p.add("light1","/light1.png");
		p.add("light2","/light2.png");
		p.add("light3","/light3.png");
		p.add("beam","/title_beam.png");
		p.add("ss_button","/stage_select_button.png");
		p.add("facility1","/facility1_2x.png");
		p.add("facility2","/facility2_2x.png");
		p.add("facility3","/facility3_2x.png");
		p.add("selector_off","/button_off.png");
		p.add("disc","/discovered_text_2x.png");
		p.add("undisc","/undiscovered_text_2x.png");
		
		p.add("bg1",Texture.rehue(p.get("title_bg"),-1,1,1, false));
		p.add("bg2",Texture.rehue(p.get("title_bg"),0.5f,1,0.3f, false));
		p.add("bg3",Texture.rehue(p.get("title_bg"),-1,2f,1, false));
		
		p.add("beam1",Texture.rehue(p.get("beam"),-1,1,1, false));
		p.add("beam2",Texture.rehue(p.get("beam"),0.5f,1,0.3f, false));
		p.add("beam3",Texture.rehue(p.get("beam"),-1,2f,1, false));
		
		p.add("l1_bg1",Texture.recolor(p.get("light1"),new Color(255,255,255), false));
		p.add("l1_bg2",Texture.recolor(p.get("light1"),new Color(0,0,0), false));
		p.add("l1_bg3",Texture.recolor(p.get("light1"),new Color(255,255,0), false));
		
		p.add("l2_bg1",Texture.recolor(p.get("light2"),new Color(255,255,255), false));
		p.add("l2_bg2",Texture.recolor(p.get("light2"),new Color(0,0,0), false));
		p.add("l2_bg3",Texture.recolor(p.get("light2"),new Color(38,179,255), false));
		
		p.add("l3_bg1",Texture.recolor(p.get("light3"),new Color(255,255,255), false));
		p.add("l3_bg2",Texture.recolor(p.get("light3"),new Color(0,0,0), false));
		p.add("l3_bg3",Texture.recolor(p.get("light3"),new Color(255,255,255), false));
		
		p.add("stage1","/stage1_label_2x.png");
		p.add("stage2","/stage2_label_2x.png");
		p.add("stage3","/stage3_label_2x.png");
		p.add("stage4","/stage4_label_2x.png");
		p.add("stage5","/stage5_label_2x.png");
		p.add("stage6","/stage6_label_2x.png");
		p.add("stage7","/stage7_label_2x.png");
		p.add("stage8","/stage8_label_2x.png");
		p.add("stage9","/stage9_label_2x.png");
		p.add("stage10","/stage10_label_2x.png");
		p.add("stage11","/stage11_label_2x.png");
		p.add("stage12","/stage12_label_2x.png");
		p.add("stage13","/stage13_label_2x.png");
		p.add("stage14","/stage14_label_2x.png");
		p.add("stage15","/stage15_label_2x.png");
		p.add("stage16","/stage16_label_2x.png");
		p.add("stage17","/stage17_label_2x.png");
		p.add("stage18","/stage18_label_2x.png");
		
		//Load up our fonts
		font.load_glyphs();
		font2.load_glyphs();
		font_large.load_glyphs();
		font.mono=false;
		font2.mono=false;
		font_large.mono=false;
		digits.load_glyphs();
		digits.mono=false;
		s=initAL(); //Music
		//Generate the buffer IDs:
		rumble_b=Audio.getOGG("/rumble.ogg");
		charge_b=Audio.getOGG("/charge.ogg");
		explode_b=Audio.getOGG("/explosion.ogg");
		critical_b=Audio.getOGG("/reach_critical.ogg");
		
		uiback=Audio.getOGG("/uiback.ogg");
		uibleep=Audio.getOGG("/uibleep.ogg");
		uiselect=Audio.getOGG("/uiselect.ogg");
		uiintro=Audio.getOGG("/intro.ogg");
		uino=Audio.getOGG("/not_allowed.ogg");
		
		rumble=new Source();
		charge=new Source();
		explode=new Source();
		critical=new Source();
		ui=new Source();
		
		rumble.setGain(0.4f*BosonX.m.volume);
		
		explode.setGain(BosonX.m.volume);
		critical.setGain(BosonX.m.volume);
		ui.setGain(BosonX.m.volume);
		s.setGain(BosonX.m.volume);
		
		long win=initGL(true);
		
		//Bind our HUD texture
		hudtex.bind();
		
		float pc=-1; //How many 60fps frames since we passed the level?
		float lc=0; //How many 60fps frames since we started the level?
		
		float pfov=fov; //Previous FOV
		
		boolean f11rise=true;
		boolean f10rise=true;
		
		initmenu(); //Do the menu thing
		cellsh_l=new Shader("cell");
		menush=new Shader("menu");
		Shader hudsh=new Shader("hud");
		cellsh_nl=new Shader("cellnl");
		Renderer.setLighting(true);
		System.out.println();
		System.out.println("Initialization complete, ready to go!");
		
		while (!glfwWindowShouldClose(win)) { //LOOOOP!
			//--------MAINLOOP---------//
			//I'm sorry, the main loop is kind of an undocumented mess.
			if(glfwGetKey(win,GLFW_KEY_P)==GL_TRUE) {
				glfwSetWindowShouldClose(win,true); //PANIC
			}
			if(glfwGetKey(win,GLFW_KEY_F10)==GL_TRUE) {
				if(f10rise) {
					f10rise=false;
					System.out.println("Refreshing audio...");
					Audio.cleanUp();
					Audio.init();
					Audio.initListener();
				}
			} else {f10rise=true;}
			if(glfwGetKey(win,GLFW_KEY_F11)==GL_TRUE) {
				if(f11rise) {
					fullscreen=!fullscreen;
					if(fullscreen) {
				        glfwSetWindowPosCallback(win,null);
					}
					glfwSetWindowMonitor(win,fullscreen ? glfwGetPrimaryMonitor() : 0,windowedX,windowedY,fullscreen?fullW:windowedW,fullscreen?fullH:windowedH,targ_fr);
					winW=fullscreen?fullW:windowedW;
					winH=fullscreen?fullH:windowedH;
					BosonX.m.aspectRatio=(float)winW/(float)winH;
					BosonX.m.hrc=winH/1080.0f;
					BosonX.m.wrc=winW/1920.0f;
					BosonX.m.mrc=Math.min(BosonX.m.hrc,BosonX.m.wrc);
					glViewport(0,0,winW,winH);
					fov_encap(fov);
					initmenu();
					Texture.clearCache();
					f11rise=false;
					if(!fullscreen) {
				        glfwSetWindowPosCallback(win,onMove);
					}
				}
				
			} else {
				f11rise=true;
			}
			glfwPollEvents(); //Poll the events (I'm sure you could not figure that out from the name of this function)
			if(menu) { //Mess with our background colors
				if(menustage==0 || menustage==1) {
					switch(fac) {
					case 0:
						glClearColor(0,0,0,1);
						break;
					case 1:
						glClearColor(1,1,1,1);
						break;
					case 2:
						glClearColor(0.3f,0.2f,0.5f,1);
					}
				} else if(menustage==2) {
					glClearColor(1,1,1,1);
				} else if(menustage==-1) {
					if(BosonX.active.background.HSBcycle==0) {
						glClearColor(BosonX.active.background.bgcolor.x,BosonX.active.background.bgcolor.y,BosonX.active.background.bgcolor.z,BosonX.active.background.bgcolor.w);
					} else {
						util.Vector4f f=BosonX.active.background.bgcolorspace();
						glClearColor(f.x,f.y,f.z,f.w);
					}
				}
			} else {
				if(BosonX.active.background.HSBcycle==0) {
					glClearColor(BosonX.active.background.bgcolor.x,BosonX.active.background.bgcolor.y,BosonX.active.background.bgcolor.z,BosonX.active.background.bgcolor.w);
				} else {
					util.Vector4f f=BosonX.active.background.bgcolorspace();
					glClearColor(f.x,f.y,f.z,f.w);
				}
			}
			glStencilMask(0xFF); //Stencil stuff? This is all for level 18 where we use the stencil buffer.
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
			long t1=millis(); //Frame start time
			if(!back && !menu) { //It's level time!
				BosonX.m.tergen.tick();
				//GL STUFF
				if(pfov!=fov) {fov_encap(fov);} //If our FOV changed, change it
				pfov=fov;
				speedup(); //Handle our speedup
				boolean p_onE=BosonX.m.onE; //Previous onE
				BosonX.m.onE=false; //No, onE, no!
				BosonX.m.tergen.handleRenderAdd();
				movement(win); //PHYSICS TIME
				handleAllEffects(); //Do the effects stuff (this also sets onE)
				float chargeDecay=15; //How many frames to decay our charging sound
				if(BosonX.m.onE!=p_onE) { //If our current onE is not our prev one
					if(BosonX.m.onE) { //If we are onE now, but not onE last frame
						charge.play(charge_b); //Start the charging!
						cc=-1; //Stop the clock!
					} else {
						cc=0; //We stopped being onE. Start the decay clock.
					}
				}
				if(cc>=chargeDecay) {
					cc=-1;//All right, we've decayed enough. Stop the clock, stop the charging.
					charge.stop();
				} else if(cc!=-1) {
					charge.setGain(((1.0f-(cc/chargeDecay))*0.7f)*BosonX.m.volume); //Set our gain to slowly decay
				} else {
					charge.setGain(0.7f*BosonX.m.volume); //Set our gain to normal
				}
				if(BosonX.m.onE && !charge.isPlaying()) {
					charge.play(charge_b); //Keep playing if we're on an E
				}
				if(cc!=-1) {cc+=1.0f/BosonX.m.frc;} //Handle our clock increase and handle framerate correction
				float f=(float) ((Math.sqrt(BosonX.m.energy/14.3f))/(10.0f/1.4f))+1;
				charge.setPitch(f); //Make the pitch increase with energy
				handleRemoving(); //Remove all the cells that we passed. I'm not really sure what's supposed to come after heaven, but all cells go there.
				if(handleDeath(win)) {back=true;} //If we die, go back to the menu
				glPushMatrix(); 
				cam_rotation.renderRotation(); //Render cam transforms
				glTranslatef(-cam_translation[0],-cam_translation[1],-cam_translation[2]);
				BosonX.active.background.generate(); //Generate the background
				cellsh.bind();
				Renderer.setRL(false);
				BosonX.active.background.render(); //Render our background
				renderLights(); //Lights....
				int a=1;
				if(BosonX.active.background.pt) { //I think pt stands for "platforms transparent"?
					//Do some stencil stuff to change the stencil buffer where our platforms are
					a=0;
					glStencilFunc(GL_ALWAYS,1,0xFF);
					glStencilOp(GL_KEEP,GL_KEEP,GL_REPLACE);
					glStencilMask(0xFF);
				}
				renderAll(a); //Render all with an alpha multiplier of 1 (unless pt, in that case 0, i.e. platforms are transparent)
				Renderer.setRL(false);
				if(BosonX.active.background.separateE) { //Why is separateE part of BosonX.active.background? I have _no_ idea.
					renderAll(1,'E'); //Render all of our E's again.
				}
				
				glPopMatrix(); //Pop our matrix to before the cam transforms
				if(BosonX.active.background.pt) { //Render our masking quad and enable the stencil test
					glStencilMask(0x00);
					glStencilFunc(GL_GREATER,1,0xFF);
					glDisable(GL_DEPTH_TEST);
					Renderer.setLighting(false);
					glBegin(GL_QUADS);
						if(BosonX.active.background.maskHSBcycle==0) {
							glColor4f(BosonX.active.background.maskColor.x,BosonX.active.background.maskColor.y,BosonX.active.background.maskColor.z,1);
						} else {
							util.Vector4f mask=BosonX.active.background.maskcolorspace();
							glColor4f(mask.x,mask.y,mask.z,1);
						}
						glNormal3f(0,0,1);
						glVertex3f(16f,16f,-2);
						glVertex3f(16f,-16f,-2);
						glVertex3f(-16f,-16f,-2);
						glVertex3f(-16f,16f,-2);
					glEnd();
					glEnable(GL_DEPTH_TEST);
					Renderer.setLighting(true);
					glStencilOp(GL_KEEP,GL_KEEP,GL_KEEP);
				}
				glPushMatrix();
				//Do HUD things
				float w=winW/1920.0f;
				float h=winH/1080.0f;
				float hudW=528.0f*w;
				float hudH=121.5f*h;
				lc+=1.0f/BosonX.m.frc;
				Graphics g=hudtex.initBI((int)hudW,(int)hudH);
				font2.drawString("ENERGY: ",(int) (20*w),(int) (10*h),(int) (25*h),g);
				font2.drawString(dbg,(int) (10.0f*w),(int)(87.0f*h),(int)(13.0f*h),g);
				font2.drawString(Integer.toString(Math.round(BosonX.m.fr))+" FPS",(int) (300.0f*w),(int)(87.0f*h),(int)(13.0f*h),g);
				//0.56,1.3,1.4
				float l=1;
				float c=50;
				float fe=(float) ((1.0f/(1+Math.exp(-l*Math.cos(2*(1.0f/c)*Math.PI*((int) (lc)))))  -  1.0f/(1+Math.exp(l)))
				* (1.0f/(1.0f/(1.0f+Math.exp(-l)) - 1.0f/(1.0f+Math.exp(l)))));
				
				float r=(fe*(255-100))+100;
				float ge=(fe*(255-230))+230;
				g.setColor(new Color(143,255,255));
				if(BosonX.m.energy>BosonX.m.scores.peaks.get(BosonX.m.cp.level)) {
					font2.drawString("NEW PEAK",(int)(250*w),(int) (10*h),(int)(25*h),new Color((int)r,(int) ge,255),g);
				}
				if(BosonX.m.energy<100.0f) {
					g.fillRect(0,(int) (35*h),(int) ((BosonX.m.energy/100.0f)*hudW),(int) (5*h));
				} else {
					g.setColor(new Color(255,40,40));
					g.fillRect(0,(int) (100.5f*h),(int) hudW,(int) (21*h));
					font2.drawString("READY FOR COLLISION",(int)(20*w),(int) (100.5f*h),(int) (25*h),new Color((int)r,(int) ge,255),g);
					g.setColor(new Color((int)r,(int) ge,255));
					g.fillRect(0,(int) (35*h),(int) (hudW),(int) (5*h));
				}
				digits.drawString(Float.toString((float) Math.round(BosonX.m.energy*100)/100f)+"%", (int) (20*w), (int) (40*h), (int) (55*h), g);
				//digits.drawString("HELLO", 20, 60, 45, g);
				hudtex.applyBI();
				g.dispose();
				glEnable(GL_TEXTURE_2D);
				glDisable(GL_DEPTH_TEST);
				float xmax=(float) (2*(BosonX.m.aspectRatio)*Math.tan(Math.toRadians(fov/2.0f)));
				float xmin=(float) (0.9*(BosonX.m.aspectRatio)*Math.tan(Math.toRadians(fov/2.0f)));
				float ymax=(float) (2*Math.tan(Math.toRadians(fov/2.0f)));
				float ymin=(float) (1.55*Math.tan(Math.toRadians(fov/2.0f)));
				glStencilFunc(GL_ALWAYS,1,0xFF);
				hudsh.bind();
				glBegin(GL_QUADS);
					glColor4f(5,5,5,0.5f);
					glNormal3f(0,0,1);
					glTexCoord2f(1,0);
					glVertex3f(xmax,ymax,-2);
					glTexCoord2f(1,1);
					glVertex3f(xmax,ymin,-2);
					glTexCoord2f(0,1);
					glVertex3f(xmin,ymin,-2);
					glTexCoord2f(0,0);
					glVertex3f(xmin,ymax,-2);
				glEnd();
				glDisable(GL_TEXTURE_2D);
				glEnable(GL_DEPTH_TEST);
				
				//AL STUFF
				
				if(!s.isPlaying()) {
					s.play(music); //Play music!
				}
				if(!musicHandled && BosonX.m.energy>=100.0f) {
					fov=71;
					Spec_alt.loadSpec_alt(BosonX.m.cp.level); //Load our alternate level-specific constants
					musicHandled=true;
					s.stop();
					critical.play(critical_b);
					music=Audio.getOGG("/stage"+Integer.toString((BosonX.m.cp.level-1)%6+1)+"b.ogg");
					pc=0; //Start our passed count
				}
				if(fov!=70 && fov!=90) { //Draw the weird white quad that provides that flash on passing a level
					fov+=0.3f;
					//This method includes a cellsh.bind() call:
					Renderer.setLighting(false);
					glDisable(GL_DEPTH_TEST);
					glStencilFunc(GL_ALWAYS,1,0xFF);
					glBegin(GL_QUADS);
						glColor4f(1,1,1,(90.0f-fov)/20.0f);
						glNormal3f(0,0,1);
						glVertex3f(16f,16f,-2);
						glVertex3f(16f,-16f,-2);
						glVertex3f(-16f,-16f,-2);
						glVertex3f(-16f,16f,-2);
					glEnd();
					glEnable(GL_DEPTH_TEST);
					Renderer.setLighting(true);
				}
				if(fov>90) {
					fov=90;
				}
				if(pc!=-1) {pc+=1.0f/BosonX.m.frc;} //Handle our passed count
			}
			glPopMatrix();
			if(back) { //Clean up after ourselves
				back=false;
				menu=true;
				BosonX.active.beenInitialized=false;
				BosonX.m.tergen.holding=new ArrayList<Cell>();
				cam_translation=new float[] {0,0,0};
				cam_rotation=new util.Rotation(180,0,0);
				celllist=new ArrayList<Cell>();
				BosonX.m.enter=0;
				BosonX.active.background.particles=new ArrayList<Particle>();
				BosonX.active.background.populate();
				//Cleanup movement engine
				jc=-1;
				lc=0;
				animc=0;
				Texture.clearCache();
				on=true;
				r_rise=false;
				l_rise=false;
				r_anim=false;
				l_anim=false;
				//Stop audio
				s.stop();
				charge.stop();
				rumble.stop();
				explode.play(explode_b); //EXPLODE!
				musicHandled=false;
				fov=70;
				fov_encap(fov);
				pc=-1;
				initmenu();
				menustage=-1;
				BosonX.active.background.flushParticles();
				BosonX.m.scores.scores.get(BosonX.m.cp.level).add(BosonX.m.energy);
				BosonX.m.scores.applyLimit();
				BosonX.m.scores.doPeak();
				
				System.out.println();
				BosonX.m.scores.output();
			}
			if(menu) {
				menush.bind();
				menu(win); //Wow.
			}
			glfwSwapBuffers(win);//No idea what this does, but it's magical.
			int targ_delta=1000/targ_fr; //Target frame time (ms)
			
			while(millis()-t1 < targ_delta) { //Accurate sleep routine (to regulate framerate)
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {}
			}
			//Bunch o' stuff about memory
			dbg=""; //Debug text!
			dbg+=String.format("%3.2f",((float)(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/(float)Runtime.getRuntime().totalMemory())*100.0f);
			dbg+="% of ";
			dbg+=Runtime.getRuntime().totalMemory()/1048576.0f;
			dbg+="M";
			//Bunch o' stuff about framerate
			BosonX.m.fp=(millis()-t1);
			BosonX.m.fr=1000.0f/BosonX.m.fp; //Calculate framerate and framerate compensation
			BosonX.m.frc=BosonX.m.fr/60.0f;
			if(BosonX.m.frc<0.33f) {
				BosonX.m.frc=0.33f;
			}
		}
		glfwTerminate();
		System.out.println();
		System.out.println("GLFW Termination");
		Audio.cleanUp(); //Clean up our stuff
		System.out.println("OpenAL Termination");
		s.close();
		ui.close();
		charge.close();
		rumble.close();
		critical.close();
		explode.close();
		System.out.println("Sources are closed");
		System.out.println("Renderer main termination");
		return back;
	}
	
	public static final float z_distance=-2;
	
	
	protected int primec=-1;
	protected long primes=0;
	protected boolean tempb=false;
	private long mstart=0;
	
	private float apval=0.9f;
	private float apval_light1=0.9f;
	private float apval_light2=0.9f;
	private float apval_light3=0.9f;
	
	public int fac=0;
	
	public int lvl=0;
	
	public int menustage=0;
	
	public boolean arr_rise=true;
	public boolean prime=true;
	MultiQuad gui=new MultiQuad(); //This is our menu GUI. Look at the MultiQuad class to figure out what it even is.
	public void initmenu() {
		gui.calcConsts();
		mstart=millis();
		//Reset our rising edge booleans
		enter_rise=false;
		esc_rise=false;
		arr_rise=false;
	}
	public void menu(long win) {
		int mtime=(int) (millis()-mstart);
		if(primec==-1) {
			if(menustage==-1) {menu_death(win,mtime);}
			if(menustage==0) {menu_stage0(win,mtime);}
			else if(menustage==1) {menu_stage1(win,mtime);}
		} else {
			menustage=2;
			menu_stage2(win,primec);
		}
		
		
		if(!menu) { //If we got the signal to start
			primec=0; //Start the "priming detector" clock
			primes=millis();
			menu=true;
		}
		if(primec!=-1 && !BosonX.active.beenInitialized) {
			Spec.loadSpec(BosonX.m.cp.level);
		}
		if(primec>=2000 || (!prime&& primec!=-1)) { //Start up our level
			ui.play(uiintro);
			menu=false;
			primes=0;
			primec=-1;
			BosonX.m.pole=0;
			BosonX.m.energy=0;
			BosonX.m.start_time=this.millis();
			BosonX.m.tergen.addAdapter(16);
			prime=true;
			//BosonX.m.addPattern(16,0);
			music=Audio.getOGG("/stage"+Integer.toString((BosonX.m.cp.level-1)%6+1)+"a.ogg");
		}
		if(primec!=-1) {primec=(int) (millis()-primes);} //PrimeC is actually in milliseconds!
	}
	private boolean handleDeath(long win) { //Are we dead yet?
		if(glfwGetKey(win,GLFW_KEY_ESCAPE)==GL_TRUE) {
			if(esc_rise) {
				esc_rise=false;
				System.out.println("Death by user input");
				return true;
			}
		} else {
			esc_rise=true;
		}
		if(cam_translation[1]<-25) {
			System.out.println("Death by falling");
			return true;
		}
		for(Cell c : celllist) {
			switch(c.type) {
			case 'E':
			case 'G':
			case 'C':
			case 'F':
			case 'P':
				if(cam_translation[2]>c.zrel && cam_translation[2]<c.zrel+BosonX.cell_length && (c.pole<0.5f || c.pole>c.numpoles-0.5f)) {
					if(cam_translation[1]<-c.height() && cam_translation[1]>-c.height()-BosonX.cell_thickness) {
						System.out.println("Death by collision with "+c.type);
						return true;
					}
				}
				break;
			case 'L':
				if(cam_translation[2]>c.zrel && cam_translation[2]<c.zrel+BosonX.cell_length && (c.pole<0.5f || c.pole>c.numpoles-0.5f)) {
					if(cam_translation[1]<-BosonX.depth(c.level)) {
						System.out.println("Death by collision with "+c.type);
						return true;
					}
				}
				break;
			case 'B':
				if(cam_translation[2]>c.zrel && cam_translation[2]<c.zrel+BosonX.cell_length && (c.pole<0.5f || c.pole>c.numpoles-0.5f)) {
					if(cam_translation[1]<-BosonX.depth(BosonX.B_height) && cam_translation[1]>-BosonX.depth(BosonX.B_depth)) {
						System.out.println("Death by collision with "+c.type);
						return true;
					}
				}
				break;
			case '#':
				if(cam_translation[2]>c.zrel && cam_translation[2]<c.zrel+BosonX.cell_length) {
					float ang=((360.0f/c.numpoles)*BosonX.m.pole+90.0f)%360;
					if(Math.abs(ang-c.geometry.rotation.z)<20.0 || Math.abs(((ang+180.0)%360)-c.geometry.rotation.z)<20.0) {
					}
				}
			}
		}
		//Return false, everything's fine.
		//Return true, 'tis but a flesh wound, albeit a fatal one.
		return false;
	}
	public void renderQuad(util.Quad q) { //Render a quadrangle!
		glBegin(GL_QUADS);
			if(!BosonX.active.background.pt) {
				glColor4f(q.color[0],q.color[1],q.color[2],q.color[3]);
			} else {
				glColor4f(q.color[0],q.color[1],q.color[2],0);
			}
			glNormal3f(q.normal[0],q.normal[1],q.normal[2]);
			for(float[] vert : q.vertices) {
				glVertex3f(vert[0],vert[1],vert[2]);
			}
		glEnd();
	}
	public void renderTri(util.Tri t) { //Render a triangle!
		glBegin(GL_TRIANGLES);
			glColor4f(t.color[0],t.color[1],t.color[2],t.color[3]);
			glNormal3f(t.normal[0],t.normal[1],t.normal[2]);
			for(float[] vert : t.vertices) {
				glVertex3f(vert[0],vert[1],vert[2]);
			}
		glEnd();
	}
	public void renderQuad(int a,util.Quad q) { //Render a quadrangle with an alpha multiplier!
		glBegin(GL_QUADS);
			glColor4f(q.color[0],q.color[1],q.color[2],a*q.color[3]);
			glNormal3f(q.normal[0],q.normal[1],q.normal[2]);
			for(float[] vert : q.vertices) {
				glVertex3f(vert[0],vert[1],vert[2]);
			}
		glEnd();
	}
	public void renderTri(int a,util.Tri t) { //Render a triangle with an alpha multiplier!
		glBegin(GL_TRIANGLES);
			glColor4f(t.color[0],t.color[1],t.color[2],a*t.color[3]);
			glNormal3f(t.normal[0],t.normal[1],t.normal[2]);
			for(float[] vert : t.vertices) {
				glVertex3f(vert[0],vert[1],vert[2]);
			}
		glEnd();
	}
	public void renderGObject(util.GObject obj) { //Render a GObject.
		for(util.GObject o : obj.attached) {
			renderGObject(o);
		}
		glPushMatrix();
		if(obj.asMesh) {glPolygonMode(GL_FRONT_AND_BACK,GL_LINE);}
		if(obj.lighting==0) {Renderer.setLighting(false);}
		if(obj.rotationalLighting) {Renderer.setRL(true);}
		if(Renderer.lighting && Renderer.rotationalLighting) {
			float rot=(float) Math.toRadians(obj.rotation.z);//+cam_rotation.z);
			cellsh.setUniform("rot",rot);
		}
		obj.renderTransforms();
		for(util.Quad q : obj.quads) {
			this.renderQuad(q);
		}
		for(util.Tri t : obj.tris) {
			this.renderTri(t);
		}
		if(obj.lighting==0) {Renderer.setLighting(true);}
		if(obj.rotationalLighting) {Renderer.setRL(false);}
		if(obj.asMesh) {glPolygonMode(GL_FRONT_AND_BACK,GL_FILL);}
		if(Renderer.lighting && Renderer.rotationalLighting) {
			Renderer.setRL(false);
		}
		glPopMatrix();
	}
	public void renderGObject(int a,util.GObject obj) { //Render a GObject with an alpha multiplier!
		for(util.GObject o : obj.attached) {
			renderGObject(a,o);
		}
		glPushMatrix();
		if(obj.asMesh) {glPolygonMode(GL_FRONT_AND_BACK,GL_LINE);}
		if(obj.lighting==0) {Renderer.setLighting(false);}
		if(obj.rotationalLighting) {Renderer.setRL(true);}
		if(Renderer.lighting && Renderer.rotationalLighting) {
			float rot=(float) Math.toRadians(obj.rotation.z);//+cam_rotation.z);
			cellsh.setUniform("rot",rot);
		}
		obj.renderTransforms();
		for(util.Quad q : obj.quads) {
			this.renderQuad(a,q);
		}
		for(util.Tri t : obj.tris) {
			this.renderTri(a,t);
		}
		if(obj.lighting==0) {Renderer.setLighting(true);}
		if(obj.rotationalLighting) {Renderer.setRL(true);}
		if(obj.asMesh) {glPolygonMode(GL_FRONT_AND_BACK,GL_FILL);}
		if(Renderer.lighting && Renderer.rotationalLighting) {
			Renderer.setRL(false);
		}
		glPopMatrix();
	}


	public void menu_stage0(long win, int mtime) { //Our facility select (SMAC, DERC, ASPA) menu. Pretty self explanatory.
		boolean pressed=false;
		
		//Generate the facilities that are active
		boolean[] act=new boolean[3];
		for(int i=0;i<3;i++) {
			act[i]=true;
			int lvls=i*6; //How many levels need to be passed to activate this facility?
			//Scan levels:
			for(int j=1;j<=lvls;j++) {
				if(BosonX.m.scores.peaks.get(j)<100.0f) {
					act[i]=false;
				}
			}
		}
		
		if(glfwGetKey(win,GLFW_KEY_RIGHT)==GL_TRUE) {
			if(arr_rise) {
				fac+=1;
				while(fac>2) {fac-=3;}
				if(act[fac]) {
					ui.play(uibleep);
				} else {
					fac-=1;
					while(fac<0) {fac+=3;}
					ui.play(uino);
				}
			}
			pressed=true;
		}
		if(glfwGetKey(win,GLFW_KEY_LEFT)==GL_TRUE) {
			if(arr_rise) {
				fac-=1;
				while(fac<0) {fac+=3;}
				if(act[fac]) {
					ui.play(uibleep);
				} else {
					fac+=1;
					while(fac>2) {fac-=3;}
					ui.play(uino);
				}
			}
			pressed=true;
		}
		if(glfwGetKey(win,GLFW_KEY_ENTER)==GL_TRUE) {
			if(enter_rise) {
				ui.play(uiselect);
				menustage++;
				Texture.clearCache();
				enter_rise=false;
			}
		} else {
			enter_rise=true;
		}
		if(glfwGetKey(win,GLFW_KEY_ESCAPE)==GL_TRUE) {
			if(esc_rise) {
				glfwSetWindowShouldClose(win,true);
				esc_rise=false;
			}
		} else {
			esc_rise=true;
		}
		if(!pressed) {
			arr_rise=true;
		} else {
			arr_rise=false;
		}
		//Create some flickery alphas
		float dap=((Util.randint(1000)/500.0f)-0.5f)*0.01f;
		while(apval+dap<0.9f || apval+dap>1.0f) {
			dap=((Util.randint(1000)/500.0f)-0.5f)*0.2f;
		}
		apval+=dap;
		
		float dap_l1=((Util.randint(1000)/500.0f)-0.5f)*0.01f;
		while(apval_light1+dap_l1<0.5f || apval_light1+dap_l1>1.0f) {
			dap_l1=((Util.randint(1000)/500.0f)-0.5f)*0.5f;
		}
		apval_light1+=dap_l1;
		
		float dap_l2=((Util.randint(1000)/500.0f)-0.5f)*0.01f;
		while(apval_light2+dap_l2<0.5f || apval_light2+dap_l2>1.0f) {
			dap_l2=((Util.randint(1000)/500.0f)-0.5f)*0.5f;
		}
		apval_light2+=dap_l2;
		
		float dap_l3=((Util.randint(1000)/500.0f)-0.5f)*0.01f;
		while(apval_light3+dap_l3<0.5f || apval_light3+dap_l3>1.0f) {
			dap_l3=((Util.randint(1000)/500.0f)-0.5f)*0.5f;
		}
		apval_light3+=dap_l3;
		
		gui.onFrame();
		
		FloatBuffer fb=BufferUtils.createFloatBuffer(4);
		glGetFloatv(GL_COLOR_CLEAR_VALUE,fb);
		
		String bgset="";
		
		float rect_location=0;
		switch(fac) {
		case 0:
			rect_location=0.2f;
			bgset="1";
			break;
		case 1:
			rect_location=0.5f;
			bgset="2";
			break;
		case 2:
			rect_location=0.8f;
			bgset="3";
			break;
		}
		
		//Do the multiquad drawing stuff
		BufferedImage beam=Texture.realpha(p.get("beam"+bgset),apval, false);
		gui.drawImage(beam,0.5f,0.2f,1,(float) beam.getHeight()/(float)winH,"BEAM",0,true);
		gui.drawImage(Texture.recolor(new BufferedImage(p.get("title_bg").getWidth(),p.get("title_bg").getHeight(),BufferedImage.TYPE_3BYTE_BGR),new Color(fb.get(0),fb.get(1),fb.get(2)), true),0.5f,0.2f,1.2f,"BG_blank",0,true,MINIMUM);
		gui.drawImage(Texture.realpha(p.get("bg"+bgset),apval, false),0.5f,0.2f,1.2f,"BG",0, true,MINIMUM);
		gui.drawImage(Texture.realpha(p.get("l1_bg"+bgset),apval_light1, false),0.5f,0.2f,1.2f,"light1",((mtime/50000.0f)*360.0f)%360.0f, true,MINIMUM);
		gui.drawImage(Texture.realpha(p.get("l2_bg"+bgset),apval_light3, false),0.5f,0.2f,0.6f,"light2",-(((mtime/50000.0f)*360.0f)%360.0f), true,MINIMUM);
		gui.drawImage(Texture.realpha(p.get("l3_bg"+bgset),apval_light2, false),0.5f,0.2f,0.8f,"light3",((mtime/55000.0f)*360.0f)%360.0f, true,MINIMUM);
		
		
		gui.drawImage(Texture.recolor(p.get("ss_button"),act[0] ? new Color(141,208,217) : new Color(100,100,100), true),0.2f,0.6f,1.7f,"ss_button1",0,true,MINIMUM);
		gui.drawImage(p.get("facility1"),0.2f,0.6f,0.8f,"ss_logo1",0,true,MINIMUM);
		gui.drawImage(Texture.recolor(p.get("ss_button"),act[1] ? new Color(217,141,141) : new Color(100,100,100), true),0.5f,0.6f,1.7f,"ss_button2",0,true,MINIMUM);
		gui.drawImage(p.get("facility2"),0.5f,0.6f,0.8f,"ss_logo2",0,true,MINIMUM);
		gui.drawImage(Texture.recolor(p.get("ss_button"),act[2] ? new Color(237,235,121) : new Color(100,100,100), true),0.8f,0.6f,1.7f,"ss_button3",0,true,MINIMUM);
		gui.drawImage(p.get("facility3"),0.8f,0.6f,0.8f,"ss_logo3",0,true,MINIMUM);
		
		
		BufferedImage selector=new BufferedImage(p.get("ss_button").getWidth(),p.get("ss_button").getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
		Graphics sg=selector.getGraphics();
		sg.setColor(new Color(255,141,141));
		((Graphics2D) sg).setStroke(new BasicStroke(4));
		sg.drawRect(0,0,selector.getWidth(),selector.getHeight());
		gui.drawImage(selector,rect_location,0.6f,1.8f,"selector",0,true,MINIMUM);
		sg.dispose();
		
		gui.render();
	}
	
	
	
	
	private boolean enter_rise=false;
	private boolean esc_rise=false;
	public void menu_stage1(long win,int mtime) { //Level select menu. Very similar to menu_stage0.
		
		boolean[] act=new boolean[6];
		for(int i=0;i<6;i++) {
			act[i]=true;
			int lvls=i+(6*fac); //How many levels need to be passed to activate this facility?
			//Scan levels:
			for(int j=1;j<=lvls;j++) {
				if(BosonX.m.scores.peaks.get(j)<100.0f) {
					act[i]=false;
				}
			}
		}
		
		boolean pressed=false;
		if(glfwGetKey(win,GLFW_KEY_RIGHT)==GL_TRUE) {
			if(arr_rise) {
				ui.play(uibleep);
				lvl+=1;
				while(lvl>5) {lvl-=6;}
			}
			pressed=true;
		}
		if(glfwGetKey(win,GLFW_KEY_LEFT)==GL_TRUE) {
			if(arr_rise) {
				ui.play(uibleep);
				lvl-=1;
				while(lvl<0) {lvl+=6;}
			}
			pressed=true;
		}
		
		if(glfwGetKey(win,GLFW_KEY_DOWN)==GL_TRUE) {
			if(arr_rise) {
				ui.play(uibleep);
				lvl+=3;
				while(lvl>5) {lvl-=6;}
			}
			pressed=true;
		}
		if(glfwGetKey(win,GLFW_KEY_UP)==GL_TRUE) {
			if(arr_rise) {
				ui.play(uibleep);
				lvl-=3;
				while(lvl<0) {lvl+=6;}
			}
			pressed=true;
		}
		if(glfwGetKey(win,GLFW_KEY_ESCAPE)==GL_TRUE) {
			if(esc_rise) {
				ui.play(uiback);
				menustage--;
				lvl=0;
				esc_rise=false;
			}
		} else {
			esc_rise=true;
		}
		if(glfwGetKey(win,GLFW_KEY_ENTER)==GL_TRUE) {
			if(enter_rise) {
				if(act[lvl]) {
					BosonX.m.cp.point(6*fac+lvl+1);
					menu=false;
				} else {
					ui.play(uino);
				}
				enter_rise=false;
			}
		} else {
			enter_rise=true;
		}
		if(!pressed) {
			arr_rise=true;
		} else {
			arr_rise=false;
		}
		float dap=((Util.randint(1000)/500.0f)-0.5f)*0.01f;
		while(apval+dap<0.9f || apval+dap>1.0f) {
			dap=((Util.randint(1000)/500.0f)-0.5f)*0.2f;
		}
		apval+=dap;
		
		float dap_l1=((Util.randint(1000)/500.0f)-0.5f)*0.01f;
		while(apval_light1+dap_l1<0.5f || apval_light1+dap_l1>1.0f) {
			dap_l1=((Util.randint(1000)/500.0f)-0.5f)*0.5f;
		}
		apval_light1+=dap_l1;
		
		float dap_l2=((Util.randint(1000)/500.0f)-0.5f)*0.01f;
		while(apval_light2+dap_l2<0.5f || apval_light2+dap_l2>1.0f) {
			dap_l2=((Util.randint(1000)/500.0f)-0.5f)*0.5f;
		}
		apval_light2+=dap_l2;
		
		float dap_l3=((Util.randint(1000)/500.0f)-0.5f)*0.01f;
		while(apval_light3+dap_l3<0.5f || apval_light3+dap_l3>1.0f) {
			dap_l3=((Util.randint(1000)/500.0f)-0.5f)*0.5f;
		}
		apval_light3+=dap_l3;
		
		gui.onFrame();
		
		FloatBuffer fb=BufferUtils.createFloatBuffer(4);
		glGetFloatv(GL_COLOR_CLEAR_VALUE,fb);
		
		String bgset="";
		
		float rect_location=0;
		float recty=0.5f;
		switch(lvl) {
		case 0:
			rect_location=0.2f;
			recty=0.5f;
			break;
		case 1:
			rect_location=0.5f;
			recty=0.5f;
			break;
		case 2:
			rect_location=0.8f;
			recty=0.5f;
			break;
		case 3:
			rect_location=0.2f;
			recty=0.8f;
			break;
		case 4:
			rect_location=0.5f;
			recty=0.8f;
			break;
		case 5:
			rect_location=0.8f;
			recty=0.8f;
			break;
		}
		bgset=Integer.toString(fac+1);
		
		BufferedImage beam=Texture.realpha(p.get("beam"+bgset),apval, false);
		gui.drawImage(beam,0.5f,0.2f,1,(float) beam.getHeight()/(float)winH,"BEAM",0,true);
		gui.drawImage(Texture.recolor(new BufferedImage(p.get("title_bg").getWidth(),p.get("title_bg").getHeight(),BufferedImage.TYPE_3BYTE_BGR),new Color(fb.get(0),fb.get(1),fb.get(2)), true),0.5f,0.2f,1.2f,"BG_blank",0,true,HORIZONTAL);
		gui.drawImage(Texture.realpha(p.get("bg"+bgset),apval, false),0.5f,0.2f,1.2f,"BG",0, true,HORIZONTAL);
		gui.drawImage(Texture.realpha(p.get("l1_bg"+bgset),apval_light1, false),0.5f,0.2f,1.2f,"light1",((mtime/50000.0f)*360.0f)%360.0f, true,HORIZONTAL);
		gui.drawImage(Texture.realpha(p.get("l2_bg"+bgset),apval_light3, false),0.5f,0.2f,0.6f,"light2",-(((mtime/50000.0f)*360.0f)%360.0f), true,HORIZONTAL);
		gui.drawImage(Texture.realpha(p.get("l3_bg"+bgset),apval_light2, false),0.5f,0.2f,0.8f,"light3",((mtime/55000.0f)*360.0f)%360.0f, true,HORIZONTAL);
		
		int a=6*fac;
		
		//System.out.println(BosonX.m.fr);
		
		Color[] colors=new Color[6];
		String[] discs=new String[6];
		String[] peaks=new String[6];
		for(int j=1;j<=6;j++) {
			peaks[j-1]=Float.toString(Math.round(BosonX.m.scores.peaks.get(a+j)*100.0f)/100.0f);
			if(BosonX.m.scores.peaks.get(a+j)>100.0f) {
				colors[j-1]=new Color(141,208,217);
				discs[j-1]="disc";
			} else {
				colors[j-1]=new Color(210,202,169);
				discs[j-1]="undisc";
			}
			if(!act[j-1]) {
				colors[j-1]=new Color(100,100,100);
			}
		}
		
		gui.drawImage(Texture.recolor(p.get("ss_button"),colors[0], true),0.2f,0.5f,1.7f,"ss_button1",0,true,MINIMUM);
		gui.drawImage(p.get("stage"+Integer.toString(1+a)),0.2f,0.5f,0.8f,"ss_logo1",0,true,MINIMUM);
		if(act[0]) {gui.drawImage(p.get(discs[0]),0.2f,0.5f,0.8f,"ss_disc1",0,true,MINIMUM);}
		gui.drawImage(Texture.recolor(p.get("ss_button"),colors[1], true),0.5f,0.5f,1.7f,"ss_button2",0,true,MINIMUM);
		gui.drawImage(p.get("stage"+Integer.toString(2+a)),0.5f,0.5f,0.8f,"ss_logo2",0,true,MINIMUM);
		if(act[1]) {gui.drawImage(p.get(discs[1]),0.5f,0.5f,0.8f,"ss_disc2",0,true,MINIMUM);}
		gui.drawImage(Texture.recolor(p.get("ss_button"),colors[2], true),0.8f,0.5f,1.7f,"ss_button3",0,true,MINIMUM);
		gui.drawImage(p.get("stage"+Integer.toString(3+a)),0.8f,0.5f,0.8f,"ss_logo3",0,true,MINIMUM);
		if(act[2]) {gui.drawImage(p.get(discs[2]),0.8f,0.5f,0.8f,"ss_disc3",0,true,MINIMUM);}
		gui.drawImage(Texture.recolor(p.get("ss_button"),colors[3], true),0.2f,0.8f,1.7f,"ss_button4",0,true,MINIMUM);
		gui.drawImage(p.get("stage"+Integer.toString(4+a)),0.2f,0.8f,0.8f,"ss_logo4",0,true,MINIMUM);
		if(act[3]) {gui.drawImage(p.get(discs[3]),0.2f,0.8f,0.8f,"ss_disc4",0,true,MINIMUM);}
		gui.drawImage(Texture.recolor(p.get("ss_button"),colors[4], true),0.5f,0.8f,1.7f,"ss_button5",0,true,MINIMUM);
		gui.drawImage(p.get("stage"+Integer.toString(5+a)),0.5f,0.8f,0.8f,"ss_logo5",0,true,MINIMUM);
		if(act[4]) {gui.drawImage(p.get(discs[4]),0.5f,0.8f,0.8f,"ss_disc5",0,true,MINIMUM);}
		gui.drawImage(Texture.recolor(p.get("ss_button"),colors[5], true),0.8f,0.8f,1.7f,"ss_button6",0,true,MINIMUM);
		gui.drawImage(p.get("stage"+Integer.toString(6+a)),0.8f,0.8f,0.8f,"ss_logo6",0,true,MINIMUM);
		if(act[5]) {gui.drawImage(p.get(discs[5]),0.8f,0.8f,0.8f,"ss_disc6",0,true,MINIMUM);}
		
		if(act[0]) {gui.drawImage(digits.getString(peaks[0]+"%",15,new Color(0,0,0)),0.12f,0.56f,1,"peak1",0,false,MINIMUM);}
		if(act[1]) {gui.drawImage(digits.getString(peaks[1]+"%",15,new Color(0,0,0)),0.42f,0.56f,1,"peak2",0,false,MINIMUM);}
		if(act[2]) {gui.drawImage(digits.getString(peaks[2]+"%",15,new Color(0,0,0)),0.72f,0.56f,1,"peak3",0,false,MINIMUM);}
		if(act[3]) {gui.drawImage(digits.getString(peaks[3]+"%",15,new Color(0,0,0)),0.12f,0.86f,1,"peak4",0,false,MINIMUM);}
		if(act[4]) {gui.drawImage(digits.getString(peaks[4]+"%",15,new Color(0,0,0)),0.42f,0.86f,1,"peak5",0,false,MINIMUM);}
		if(act[5]) {gui.drawImage(digits.getString(peaks[5]+"%",15,new Color(0,0,0)),0.72f,0.86f,1,"peak6",0,false,MINIMUM);}
		
		BufferedImage selector=new BufferedImage(p.get("ss_button").getWidth(),p.get("ss_button").getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
		Graphics sg=selector.getGraphics();
		sg.setColor(new Color(255,141,141));
		((Graphics2D) sg).setStroke(new BasicStroke(4));
		sg.drawRect(0,0,selector.getWidth(),selector.getHeight());
		gui.drawImage(selector,rect_location,recty,1.8f,"selector",0,true,MINIMUM);
		sg.dispose();
		
		gui.render();
	}
	
	
	public void menu_stage2(long win,int primec) { //Ready player one
		gui.onFrame();
		gui.drawImage(font2.getString("PRIMING DETECTOR "+Integer.toString(BosonX.m.cp.level),72,new Color(100,100,100)),0.5f,0.5f,1f,"prime",0,true,VERTICAL);
		gui.drawImage(font2.getString("GET READY",72,new Color(100,100,100)),0.5f,0.55f,1f,"ready",0,true,VERTICAL);
		gui.render();
	}
	public void menu_death(long win, int primec) {
		if(!s.isPlaying()) {
			s.setPitch(0.5f);
			s.setGain(0.4f*BosonX.m.volume);
			s.play(music);
		}
		if(glfwGetKey(win,GLFW_KEY_ESCAPE)==GL_TRUE) {
			if(esc_rise) {
				s.setPitch(1);
				s.setGain(BosonX.m.volume);
				s.stop();
				ui.play(uiback);
				menustage=1;
				lvl=0;
				esc_rise=false;
				
			}
		} else {
			esc_rise=true;
		}
		if(glfwGetKey(win,GLFW_KEY_ENTER)==GL_TRUE) {
			if(enter_rise) {
				s.setPitch(1);
				s.setGain(BosonX.m.volume);
				s.stop();
				menu=false;
				enter_rise=false;
				prime=false;
			}
		} else {
			enter_rise=true;
		}
		
		gui.onFrame();
		glPushMatrix();
		cam_rotation.renderRotation(); //Render cam transforms
		glTranslatef(-cam_translation[0],-cam_translation[1],-cam_translation[2]);
		BosonX.active.background.generate(); //Generate the background
		cellsh.bind();
		BosonX.active.background.render(); //Render our background
		menush.bind();
		renderLights(); //Lights....
		glPopMatrix();
		gui.drawImage(new Color(255,255,255),0,0.9f,1,0.1f,"bottom_bar",false);
		gui.drawImage(font_large.getString("ESC: BACK",70,new Color(0,0,0)),0.03f,0.9f,1,"esc",0,false,VERTICAL);
		gui.drawImage(font_large.getString("ENTER: AGAIN",70,new Color(0,0,0)),0.97f,0.9f,1,"ent",0,false,VERTICAL,   true, false);
		gui.drawImage(new Color(0,0,0),0,0,1,0.9f,"brightness",false);
		gui.patches.get("brightness").alpha=0.5f;
		gui.drawImage(font_large.getString("ENERGY:",70,new Color(255,255,255)),0.5f,0.45f,1,"energy",0,true,VERTICAL);
		gui.drawImage(digits.getString(Float.toString((float) Math.round(BosonX.m.energy*100)/100f)+"%",70,new Color(255,255,255)),0.5f,0.55f,1,"score",0,true,VERTICAL);
		
		int graphX=(int) (1920.0f*0.8f*BosonX.m.wrc);
		int graphY=(int) (324.0f*BosonX.m.hrc);
		BufferedImage graph=new BufferedImage(graphX,graphY,BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g=(Graphics2D)graph.getGraphics();
		float m=BosonX.m.scores.peaks.get(BosonX.m.cp.level);
		ArrayList<Float> data=BosonX.m.scores.scores.get(BosonX.m.cp.level);
		for(int i=0;i<data.size();i++) {if(data.get(i)>m) {m=data.get(i);}} //Get a max of data
		ArrayList<Float> pxy=new ArrayList<Float>();
		for(float o : data) {
			pxy.add(-((o/m)*(float)(graphY))+graphY);
		}
		int inc=100;
		int dx=(int) ((float)(graphX-200)/(float) (data.size()-1.0f));
		g.setColor(new Color(255,255,255));
		for(int i=0;i<pxy.size();i++) {
			g.setStroke(new BasicStroke(5.0f*Math.min(BosonX.m.hrc,BosonX.m.wrc)));
			if(i!=0) {
				g.drawLine(inc,(int)(pxy.get(i)*0.9f),inc-dx,(int)(pxy.get(i-1)*0.9f));
			}
			inc+=dx;
		}
		digits.drawString(Float.toString((float) Math.round(m*10)/10f)+"%",0,0,(int)(20*BosonX.m.hrc),g);
		digits.drawString("0.0%",0,(int) (0.9f*graphY),(int)(20*BosonX.m.hrc),g);
		g.dispose();
		
		gui.drawImage(graph,0.1f,0.1f,0.8f,0.3f,"graph",0,false);
		gui.patches.get("graph").alpha=0.5f;
		
		gui.render();
	}
}