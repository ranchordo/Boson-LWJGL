package util;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glRotatef;

public class Patch implements Comparable<Patch> {
	//A textured quad. That's all it is.
	public float x;
	public float y;
	public float w;
	public float h;
	public float r=0;
	
	public float alpha=1;
	public Texture tex;
	public float level;
	
	public boolean center=false;
	public boolean right=false;
	public boolean bottom=false;
	@Override
	public int compareTo(Patch a) {
		return ((Float) this.level).compareTo((Float) a.level);
	}
	public void render() {
		tex.applyBI();
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		if(!center) {
			if(!right && !bottom) {glTranslatef(x+(w/2.0f),y+(h/2.0f),0);}
			if(!right && bottom)  {glTranslatef(x+(w/2.0f),y+(h/2.0f)-h,0);}
			if(right && !bottom)  {glTranslatef(x+(w/2.0f)-w,y+(h/2.0f),0);}
			if(right && bottom)   {glTranslatef(x+(w/2.0f)-w,y+(h/2.0f)-h,0);}
			//glTranslatef(x+(w/2.0f),y+(h/2.0f),0);
		} else {
			glTranslatef(x,y,0);
		}
		glRotatef(r,0,0,1);
		glBegin(GL_QUADS);
			glColor4f(1,1,1,alpha);
			glNormal3f(0,0,-1);
			glTexCoord2f(1,1);
			glVertex3f(w/2.0f,h/2.0f,-2);
			glTexCoord2f(1,0);
			glVertex3f(w/2.0f,-h/2.0f,-2);
			glTexCoord2f(0,0);
			glVertex3f(-w/2.0f,-h/2.0f,-2);
			glTexCoord2f(0,1);
			glVertex3f(-w/2.0f,h/2.0f,-2);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}
}
