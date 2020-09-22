package util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.mortennobel.imagescaling.ResampleOp;

public class Atlas {//This is a weird one. Prepare for nonsense.
	/*
	 * This is for drawing non-monospaced font atlases to a BufferedImage. It has adaptive glyph bounds recognition and is excessively fancy.
	 */
	public BufferedImage atlas;
	public float spacing;
	public int height;
	public String atlas_layout="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890.,-&?!%@/:()'=[]<>_+";
	public boolean[] columns;
	public int[] starts;
	public int[] widths;
	public boolean mono=true;
	public float space_width=6;
	public String filename="";
	public boolean[] columns() { //Is there a non-0 alpha value in this column?
		boolean[] columns=new boolean[atlas.getWidth()+1];
		for(int x=0;x<atlas.getWidth();x++) {
			columns[x]=false;
			for(int y=0;y<atlas.getHeight();y++) {
				int pixel=atlas.getRGB(x,y);
				float alpha=((pixel>>24)&0xff);
				if(alpha>0) {
					columns[x]=true;
					break;
				}
			}
		}
		columns[atlas.getWidth()]=false;
		return columns;
	}
	public int[] char_starts() { //Recognize the character starting places
		ArrayList<Integer> starts=new ArrayList<Integer>();
		boolean palpha=false;
		for(int i=0;i<columns.length;i++) {
			if(!palpha && columns[i]) {
				starts.add(i);
			}
			palpha=columns[i];
		}
		int[] ret=new int[starts.size()];
		for(int i=0;i<starts.size();i++) {
			ret[i]=starts.get(i);
		}
		return ret;
	}
	public int[] char_stops() { //Recognize the character ending places
		ArrayList<Integer> stops=new ArrayList<Integer>();
		boolean palpha=false;
		for(int i=0;i<columns.length;i++) {
			if(palpha && !columns[i]) {
				stops.add(i);
			}
			palpha=columns[i];
		}
		int[] ret=new int[stops.size()];
		for(int i=0;i<stops.size();i++) {
			ret[i]=stops.get(i);
		}
		return ret;
	}
	public int[] char_widths() { //Cross-reference starts and ends, get the widths of the glyphs.
		int[] ret=new int[starts.length];
		int[] stops=char_stops();
		assert stops.length==starts.length;
		for(int i=0;i<starts.length;i++) {
			ret[i]=stops[i]-starts[i];
		}
		return ret;
	}
	public void load_glyphs() { //Calculate all the glyph info
		System.out.println("Loading font glyphs: "+filename);
		this.columns=columns();
		this.starts=char_starts();
		this.widths=char_widths();
	}
	public Atlas(String fname,float w) { //Go get our bufferedimage from a file. Spacing is w. This can either be constant (monospace) or adaptive.
		spacing=w;
		filename=fname;
		try {
			atlas=ImageIO.read(main.BosonX.class.getResourceAsStream(fname));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		height=atlas.getHeight();
		System.out.println("Add font atlas: "+fname);
	}
	public Atlas(String fname,float w,String layout) { //Layout is the character layout. Ex. "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
		atlas_layout=layout;
		spacing=w;
		filename=fname;
		try {
			atlas=ImageIO.read(main.BosonX.class.getResourceAsStream(fname));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		height=atlas.getHeight();
		System.out.println("Add font atlas: "+fname);
	}
	public BufferedImage charId(int id) { //Get a single char image
		return atlas.getSubimage(starts[id],0,widths[id],height);
	}
	public BufferedImage charId(int id, Color col) { //Get a single char image, clone it, and recolor it.
		BufferedImage atlas_sub=atlas.getSubimage(starts[id],0,widths[id],height);
		ColorModel cm=atlas_sub.getColorModel();
		boolean isAlphaPremultiplied=atlas_sub.isAlphaPremultiplied();
		WritableRaster sub_raster=atlas_sub.copyData(atlas_sub.getRaster().createCompatibleWritableRaster());
		BufferedImage c=new BufferedImage(cm,sub_raster,isAlphaPremultiplied,null);
		WritableRaster raster=c.getRaster();
		for(int x=0;x<c.getWidth();x++) {
			for(int y=0;y<c.getHeight();y++) {
				int[] pix=raster.getPixel(x,y,(int[])null);
				pix[0]=col.getRed();
				pix[1]=col.getGreen();
				pix[2]=col.getBlue();
				raster.setPixel(x,y,pix);
			}
		}
		return c;
	}
	public BufferedImage stringId(int[] ids) { //Get a string of chars by their ids and draw them into one bufferedimage
		float w;
		if(mono) {
			w=spacing*(float)ids.length;
		} else {
			w=0;
			for(int id : ids) {
				if(id!=-1) {
					w+=widths[id]+spacing;
				} else {
					w+=space_width*spacing;
				}
			}
		}
		BufferedImage ret=new BufferedImage((int)(w),height,BufferedImage.TYPE_4BYTE_ABGR);
		float cursor=0;
		for(int id : ids) {
			if(id!=-1) {
				Graphics g=ret.getGraphics();
				BufferedImage Char=charId(id);
				g.drawImage(Char,(int)cursor,0,null);
				cursor+=spacing;
				if(!mono) {
					cursor+=widths[id];
				}
			} else {
				if(mono) {
					cursor+=spacing;
				} else {
					cursor+=space_width*spacing;
				}
			}
		}
		return ret;
	}
	public BufferedImage stringId(int[] ids, Color col) { //Recolored string by ids
		float w;
		if(mono) {
			w=spacing*(float)ids.length;
		} else {
			w=0;
			for(int id : ids) {
				if(id!=-1) {
					w+=widths[id]+spacing;
				} else {
					w+=space_width*spacing;
				}
			}
		}
		BufferedImage ret=new BufferedImage((int)(w),height,BufferedImage.TYPE_4BYTE_ABGR);
		float cursor=0;
		for(int id : ids) {
			if(id!=-1) {
				Graphics g=ret.getGraphics();
				BufferedImage Char=charId(id,col);
				g.drawImage(Char,(int)cursor,0,null);
				cursor+=spacing;
				if(!mono) {
					cursor+=widths[id];
				}
			} else {
				if(mono) {
					cursor+=spacing;
				} else {
					cursor+=space_width*spacing;
				}
			}
		}
		return ret;
	}
	
	//Get strings and draw strings:
	public void drawString(String s, int x, int y, int h, Color col, Graphics g) {
		float scale=(float)h/(float)this.height;
		g.drawImage(sstring(s,scale,col),x,y,null);
	}
	public void drawString(String s, int x, int y, int h, Graphics g) {
		float scale=(float)h/(float)this.height;
		g.drawImage(sstring(s,scale),x,y,null);
	}
	public BufferedImage getString(String s, int h, Color col) {
		float scale=(float)h/(float)this.height;
		return sstring(s,scale,col);
	}
	public BufferedImage getString(String s, int h) {
		float scale=(float)h/(float)this.height;
		return sstring(s,scale);
	}
	public BufferedImage string(String s) {
		char[] al=atlas_layout.toCharArray();
		char[] sc=s.toCharArray();
		int[] ids=new int[sc.length];
		for(int i=0;i<sc.length;i++) {
			ids[i]=-1;
			for(int j=0;j<al.length;j++) {
				if(sc[i]==al[j]) {
					ids[i]=j;
					break;
				}
			}
		}
		return stringId(ids);
	}
	public BufferedImage size(BufferedImage a,float s) { //Resize an image
		BufferedImage ret=new BufferedImage((int)(a.getWidth()*s),(int)(a.getHeight()*s),BufferedImage.TYPE_4BYTE_ABGR);
		Graphics retg=ret.getGraphics();
		retg.drawImage(a,0,0,(int)(a.getWidth()*s),(int)(a.getHeight()*s),null);
		return ret;
	}
	public BufferedImage sstring(String s, float scale) { //Get the character ids from the layout and fetch a bufferedimage with them
		char[] al=atlas_layout.toCharArray();
		char[] sc=s.toCharArray();
		int[] ids=new int[sc.length];
		for(int i=0;i<sc.length;i++) {
			ids[i]=-1;
			for(int j=0;j<al.length;j++) {
				if(sc[i]==al[j]) {
					ids[i]=j;
					break;
				}
			}
		}
		BufferedImage string=stringId(ids);
		return size(string,scale);
	}
	public BufferedImage sstring(String s, float scale, Color col) { //Same as before, but recolored!
		char[] al=atlas_layout.toCharArray();
		char[] sc=s.toCharArray();
		int[] ids=new int[sc.length];
		for(int i=0;i<sc.length;i++) {
			ids[i]=-1;
			for(int j=0;j<al.length;j++) {
				if(sc[i]==al[j]) {
					ids[i]=j;
					break;
				}
			}
		}
		//System.out.println();
		BufferedImage string=stringId(ids,col);
		return size(string,scale);
	}
	public BufferedImage sstring_anti(String s, float scale) { //Scaled string, but antialiased! (Performance is _terrible_)
		char[] al=atlas_layout.toCharArray();
		char[] sc=s.toCharArray();
		int[] ids=new int[sc.length];
		for(int i=0;i<sc.length;i++) {
			ids[i]=-1;
			for(int j=0;j<al.length;j++) {
				if(sc[i]==al[j]) {
					ids[i]=j;
					break;
				}
			}
		}
		BufferedImage string=stringId(ids);
		ResampleOp resamp=new ResampleOp((int) (string.getWidth()*scale),(int) (string.getHeight()*scale));
		return resamp.filter(string,null);
	}
}