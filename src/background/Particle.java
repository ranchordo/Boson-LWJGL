package background;
import static org.lwjgl.opengl.GL11.*;


public class Particle { //This is really self-explanatory. I'll let you figure this out.
	public util.Texture tex=new util.Texture();
	public util.Quad p=new util.Quad(0,0,0, 0.5f,0,0, 0.5f,0,1, 0,0,1);
	public float vz=0;
	public float vt=0;
	
	public float l=1;
	public float w=0.5f;
	
	public float r=1;
	public float g=1;
	public float b=1;
	public float a=1;
	public float zr=0;
	public float zt=0;
	public float yd=0;
	public float xr=0;
	
	public float xt=0;
	
	public boolean lighting;
	public Particle() {}
	
	public void initGeo() {
		p=new util.Quad(0,0,0, w,0,0, w,0,l, 0,0,l);
	}
	
	public void render() {
		zt+=vz/main.BosonX.m.frc;
		zr+=(-1.0f/360.0f)*vt;
		glEnable(GL_TEXTURE_2D);
		if(!lighting) {glDisable(GL_LIGHTING);}
		glPushMatrix();
		glTranslatef(xt,0,0);
		glRotatef(zr,0,0,1);
		glTranslatef(0,-yd,zt);
		glRotatef(xr,0,0,1);
		glRotatef(vt,0,1,0);
		glColor4f(r,g,b,a);
		
		glBegin(GL_QUADS);
			glNormal3f(0,1,0);
			glTexCoord2f(0,0);
			glVertex3f(p.vertices[0][0],p.vertices[0][1],p.vertices[0][2]);
			glTexCoord2f(1,0);
			glVertex3f(p.vertices[1][0],p.vertices[1][1],p.vertices[1][2]);
			glTexCoord2f(1,1);
			glVertex3f(p.vertices[2][0],p.vertices[2][1],p.vertices[2][2]);
			glTexCoord2f(0,1);
			glVertex3f(p.vertices[3][0],p.vertices[3][1],p.vertices[3][2]);
		glEnd();
		
		glPopMatrix();
		
		glDisable(GL_TEXTURE_2D);
		if(!lighting) {glEnable(GL_LIGHTING);}
	}
}
