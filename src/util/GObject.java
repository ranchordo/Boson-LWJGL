package util;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GObject {
	public ArrayList<Quad> quads=new ArrayList<Quad>();
	public ArrayList<Tri> tris=new ArrayList<Tri>();
	public float[] translation= {0.0f,0.0f,0.0f};
	public float[] post= {0,0,0};
	public ArrayList<GObject> attached=new ArrayList<GObject>();
	float[] scale= {1,1,1};
	public Rotation rotation;
	String name;
	public float lighting;
	public boolean rotationalLighting=false;
	public boolean asMesh=false;
	public GObject scale(float x, float y, float z) {
		scale[0]*=x;
		scale[1]*=y;
		scale[2]*=z;
		return this;
	}
	public GObject setScale(float x, float y, float z) {
		scale=new float[] {x,y,z};
		return this;
	}
	public GObject(int rot_type) { //Rotation type controls whether it's a vector or orthogonal rotation. I never implemented vector rotation.
		if(rot_type==0) {
			rotation=new Rotation(0,0,0,0);
		} else if(rot_type==1) {
			rotation=new Rotation(0,0,0);
		}
		name="";
	}
	public GObject(String nm, int rot_type) {
		if(rot_type==0) {
			rotation=new Rotation(0,0,0,0);
		} else if(rot_type==1) {
			rotation=new Rotation(0,0,0);
		}
		name=nm;
	}
	public void renderTransforms() {
		main.BosonX.m.r.translate_encap(translation[0],translation[1],translation[2]); //Why am I using renderer encaps here?
		main.BosonX.m.r.scale_encap(scale[0],scale[1],scale[2]);
		rotation.renderRotation();
		main.BosonX.m.r.translate_encap(post[0],post[1],post[2]);
	}
	public void translate(float x, float y, float z) { //Recursive transformation methods
		translation[0]+=x;
		translation[1]+=y;
		translation[2]+=z;
		for(GObject o : attached) {
			o.translate(x,y,z);
		}
	}
	public void rotate(float x, float y, float z) {
		rotation.rotate(x, y, z);
		for(GObject o : attached) {
			o.rotate(x,y,z);
		}
	}
	public void setTranslation(float x, float y, float z) {
		translation[0]=x;
		translation[1]=y;
		translation[2]=z;
		for(GObject o : attached) {
			o.setTranslation(x,y,z);
		}
	}
	public void setRotation(float x, float y, float z) {
		rotation.setRotation(x,y,z);
		for(GObject o : attached) {
			o.setRotation(x,y,z);
		}
	}
	public void setColor(float r, float g, float b) {
		for(Quad q : quads) {
			q.setColor(r, g, b);
		}
		for(Tri t : tris) {
			t.setColor(r, g, b);
		}
	}
	public GObject loadOBJ(String filename) { //Wavefront file parser "frontend"
		quads=new ArrayList<Quad>();
		try {
			tris=loadOBJ_raw(main.BosonX.class.getResourceAsStream(filename));
		} catch(FileNotFoundException e) {
			System.out.println(filename+" does not appear to exist.");
			System.exit(1);
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return this;
	}
	public GObject append(GObject a) { //Add a GObject to another
		for(Quad q : a.quads) {
			quads.add(q);
		}
		for(Tri t : a.tris) {
			tris.add(t);
		}
		return this;
	}
	public ArrayList<Tri> loadOBJ_raw(InputStream f) throws FileNotFoundException, IOException { //Wavefront file parser!
		BufferedReader reader=new BufferedReader(new InputStreamReader(f));
		String line;
		ArrayList<Vector3f> vertices=new ArrayList<Vector3f>();
		ArrayList<Vector3f> normals=new ArrayList<Vector3f>();
		ArrayList<Vector3f> vertIndices=new ArrayList<Vector3f>();
		ArrayList<Vector3f> normIndices=new ArrayList<Vector3f>();
		while((line=reader.readLine())!=null) {
			if(line.startsWith("v ")) {
				float x=Float.valueOf(line.split(" ")[1]);
				float y=Float.valueOf(line.split(" ")[2]);
				float z=Float.valueOf(line.split(" ")[3]);
				vertices.add(new Vector3f(x,y,z));
			} else if(line.startsWith("vn ")) {
				float x=Float.valueOf(line.split(" ")[1]);
				float y=Float.valueOf(line.split(" ")[2]);
				float z=Float.valueOf(line.split(" ")[3]);
				normals.add(new Vector3f(x,y,z));
			} else if(line.startsWith("f ")) {
				float x=Float.valueOf(line.split(" ")[1].split("/")[0]);
				float y=Float.valueOf(line.split(" ")[2].split("/")[0]);
				float z=Float.valueOf(line.split(" ")[3].split("/")[0]);
				vertIndices.add(new Vector3f(x,y,z));
				float xn=Float.valueOf(line.split(" ")[1].split("/")[2]);
				float yn=Float.valueOf(line.split(" ")[2].split("/")[2]);
				float zn=Float.valueOf(line.split(" ")[3].split("/")[2]);
				normIndices.add(new Vector3f(xn,yn,zn));
			}
		}
		ArrayList<Tri> obj=new ArrayList<Tri>();
		for(int i=0;i<vertIndices.size();i++) {
			Vector3f vertex1=vertices.get((int) vertIndices.get(i).x-1);
			Vector3f vertex2=vertices.get((int) vertIndices.get(i).y-1);
			Vector3f vertex3=vertices.get((int) vertIndices.get(i).z-1);
			
			Vector3f normal1=normals.get((int) normIndices.get(i).x-1);
			
			assert normIndices.get(i).x==normIndices.get(i).y;
			assert normIndices.get(i).x==normIndices.get(i).z;
			
			obj.add(new Tri(vertex1.x,vertex1.y,vertex1.z,vertex2.x,vertex2.y,vertex2.z,vertex3.x,vertex3.y,vertex3.z).setNormal(normal1.x, normal1.y, normal1.z, true));
		}
		reader.close();
		return obj;
	}
}
