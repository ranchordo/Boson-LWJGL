package util;

public class Tri {
	public float[] color= {0.0f,0.0f,0.0f,1.0f};
	public float[] normal;
	public float[][] vertices={
			{0.0f,0.0f,0.0f},
			{0.0f,0.0f,0.0f},
			{0.0f,0.0f,0.0f}};
	public Tri(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3) {
		vertices[0]=new float[] {x1,y1,z1};
		vertices[1]=new float[] {x2,y2,z2};
		vertices[2]=new float[] {x3,y3,z3};
	}
	public Tri setColor(float r, float g, float b) {
		color[0]=r;
		color[1]=g;
		color[2]=b;
		color[3]=1;
		return this;
	}
	public Tri setAlpha(float a) {
		color[3]=a;
		return this;
	}
	public Tri normalizeNormal() {
		float mag=(float) Math.sqrt(Math.pow(normal[0], 2)+Math.pow(normal[1], 2)+Math.pow(normal[2], 2));
		normal[0]/=mag;
		normal[1]/=mag;
		normal[2]/=mag;
		return this;
	}
	public Tri setNormal(float x, float y, float z, boolean n) {
		this.normal=new float[] {x, y, z};
		if(n) {
			normalizeNormal();
		}
		return this;
	}
	public Tri autoNormal(float x, float y, float z) {
		float[] v1=new float[] {vertices[0][0]-vertices[1][0],vertices[0][1]-vertices[1][1],vertices[0][2]-vertices[1][2]};
		float[] v2=new float[] {vertices[0][0]-vertices[2][0],vertices[0][1]-vertices[2][1],vertices[0][2]-vertices[2][2]};
		float[] cd1=new float[3];
		cd1[0]=v1[1]*v2[2]-v1[2]*v2[1];
		cd1[1]=v1[2]*v2[0]-v1[0]*v2[2];
		cd1[2]=v1[0]*v2[1]-v1[1]*v2[0];
		float[] cd2=new float[] {-cd1[0],-cd1[1],-cd1[2]};
		float ca1=(float) Math.acos((x*cd1[0] + y*cd1[1] + z*cd1[2])/(Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2)+Math.pow(x, 2))  *  Math.sqrt(Math.pow(cd1[0], 2)+Math.pow(cd1[1], 2)+Math.pow(cd1[2], 2))));
		float ca2=(float) Math.acos((x*cd2[0] + y*cd2[1] + z*cd2[2])/(Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2)+Math.pow(x, 2))  *  Math.sqrt(Math.pow(cd2[0], 2)+Math.pow(cd2[1], 2)+Math.pow(cd2[2], 2))));
		if(ca1<ca2) {
			this.normal=cd1;
		} else {
			this.normal=cd2;
		}
		return this;
	}
}
