package util;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {
	private int id;
	private int width;
	private int height;
	public BufferedImage bi;
	public Graphics initBI() {
		bi=new BufferedImage(240,75,BufferedImage.TYPE_BYTE_BINARY);
		Graphics g=bi.getGraphics();
		g.setFont(main.BosonX.m.neuropol);
		width=bi.getWidth();
		height=bi.getHeight();
		return g;
	}
	public Graphics initBI(BufferedImage input) {
		bi=input;
		Graphics g=bi.getGraphics();
		g.setFont(main.BosonX.m.neuropol);
		width=bi.getWidth();
		height=bi.getHeight();
		return g;
	}
	public Graphics initBI(File file) {
		try {
			bi=ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		Graphics g=bi.getGraphics();
		g.setFont(main.BosonX.m.neuropol);
		width=bi.getWidth();
		height=bi.getHeight();
		return g;
	}
	public Graphics initBI(int w, int h) {
		bi=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_BINARY);
		Graphics g=bi.getGraphics();
		g.setFont(main.BosonX.m.neuropol);
		width=bi.getWidth();
		height=bi.getHeight();
		return g;
	}
	public Texture() {
		initBI();
		
		width=bi.getWidth();
		height=bi.getHeight();
		
		applyBI();
	}
	public void applyBI() {
		int[] pixels_raw=new int[width*height*4];
		pixels_raw=bi.getRGB(0, 0, width, height, null, 0, width);
		ByteBuffer pixels=BufferUtils.createByteBuffer(width*height*4);
		
		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				int pixel=pixels_raw[i*height+j];
				pixels.put((byte)((pixel >> 16)&0xFF));
				pixels.put((byte)((pixel >> 8)&0xFF));
				pixels.put((byte)((pixel)&0xFF));
				pixels.put((byte)((pixel >> 24)&0xFF));
			}
		}
		pixels.flip();
		
		id=glGenTextures();
		
		glBindTexture(GL_TEXTURE_2D,id);
		
		glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
		
		glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,width,height,0,GL_RGBA,GL_UNSIGNED_BYTE,pixels);
	}
	public void bind() {
		glBindTexture(GL_TEXTURE_2D,id);
	}
}
