package util;

public class Rotation {
	//This just stores a rotation value and can render it.
	public float x;
	public float y;
	public float z;
	public float a;
	public int type; //0 for Vector, 1 for Ortho
	public Rotation(float a, float x, float y, float z) {
		this.a=a;
		this.x=x;
		this.y=y;
		this.z=z;
		this.type=0;
	}
	public Rotation(float x, float y, float z) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.type=1;
	}
	public void renderRotation() {
		if(this.type==0) {
			main.BosonX.m.r.rotate_encap(a, x, y, z);
		} else if(this.type==1) {
			if(this.x>=0) {main.BosonX.m.r.rotate_encap(x,0,1,0);}
			else {main.BosonX.m.r.rotate_encap(-x,0,-1,0);}

			if(this.y>=0) {main.BosonX.m.r.rotate_encap(y,1,0,0);}
			else {main.BosonX.m.r.rotate_encap(-y,-1,0,0);}
			
			if(this.z>=0) {main.BosonX.m.r.rotate_encap(z,0,0,1);}
			else {main.BosonX.m.r.rotate_encap(-z,0,0,-1);}
		}
	}
	public void rotate(float a, float x, float y, float z) {
		assert type==0 : "Incorrect vector/ortho type for Rotation.rotate(axyz)";
		System.out.println("Rotation.rotate(axyz) is not implemented yet.");
		System.exit(1);
	}
	public void rotate(float x, float y, float z) {
		assert type==1 : "Incorrect vector/ortho type for Rotation.rotate(xyz)";
		this.x+=x;
		this.y+=y;
		this.z+=z;
		this.x%=360;
		this.y%=360;
		this.z%=360;
	}
	public void setRotation(float a, float x, float y, float z) {
		assert type==0 : "Incorrect vector/ortho type for Rotation.setRotation(axyz)";
		this.a=a;
		this.x=x;
		this.y=y;
		this.z=z;
	}
	public void setRotation(float x, float y, float z) {
		assert type==1 : "Incorrect vector/ortho type for Rotation.setRotation(xyz)";
		this.x=x;
		this.y=y;
		this.z=z;
	}
}
