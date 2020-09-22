package util;
import java.awt.Color;
import java.awt.image.BufferedImage;


public class ModOp implements Comparable<ModOp> {// A modification operation. We can use these as keys for our modCache.
	public float aw; //W/H for our BI
	public float ah;
	public int type;//0 - rehue, 1 - recolor, 2 - realpha
	public float h;
	public float sm;
	public float vm;
	public String nm;
	public int id;
	public Color col;
	public float mul;
	public ModOp(BufferedImage a, float h, float sm, float vm, int id) {
		type=0;
		this.aw=a.getWidth();
		this.ah=a.getHeight();
		this.h=h;
		this.sm=sm;
		this.vm=vm;
		this.id=id;
	}
	public ModOp(BufferedImage a, Color col, int id) {
		type=1;
		this.aw=a.getWidth();
		this.ah=a.getHeight();
		this.col=col;
		this.id=id;
	}
	public ModOp(BufferedImage a, float mul, int id) {
		type=2;
		this.aw=a.getWidth();
		this.ah=a.getHeight();
		this.mul=mul;
		this.id=id;
	}
	public ModOp(int id) {
		this.id=id;
	}
	@Override
	public int compareTo(ModOp m) {
		return ((Integer)this.id).compareTo((Integer)m.id);
	}
	@Override
	public boolean equals(Object o) { //Equals based on content, not pointer.
		if(this==o) return true;
		ModOp m=(ModOp) o;
		if(m.type!=this.type) return false;
		if(m.h!=this.h) return false;
		if(m.sm!=this.sm) return false;
		if(m.vm!=this.vm) return false;
		if(m.mul!=this.mul) return false;
		if(m.aw!=this.aw) return false;
		if(m.ah!=this.ah) return false;
		if(this.nm!=null && m.nm!=null) {if(!nm.equals(m.nm)) return false;}
		if(this.col!=null && m.col!=null) {if(!col.equals(m.col)) return false;}
		return true;
	}
	@Override
	public int hashCode() {
		int result=0;
		result=31*result + (((Integer)type!=null) ? ((Integer)type).hashCode() : 0);
		result=31*result + (((Float)h!=null) ? ((Float)h).hashCode() : 0);
		result=31*result + (((Float)sm!=null) ? ((Float)sm).hashCode() : 0);
		result=31*result + (((Float)vm!=null) ? ((Float)vm).hashCode() : 0);
		result=31*result + (((Float)mul!=null) ? ((Float)mul).hashCode() : 0);

		result=31*result + ((col!=null) ? col.hashCode() : 0);
		result=31*result + ((nm!=null) ? nm.hashCode() : 0);

		return result;
	}
}