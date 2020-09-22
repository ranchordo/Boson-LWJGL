package util;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class PrefetchedImages {
	//This is basically just a convenient String,BufferedImage HashMap wrapper
	HashMap<String, BufferedImage> images=new HashMap<String,BufferedImage>();
	public PrefetchedImages() {}
	public void add(String key, BufferedImage img) {
		images.put(key,img);
		System.out.println("PrefetchedImages BuIm add routine: "+key);
	}
	public void add(String key, String fname) {
		images.put(key,Texture.getImage(fname));
		System.out.println("PrefetchedImages File add routine: "+key+", "+fname);
	}
	public void replace(String key, BufferedImage img) {
		images.remove(key);
		this.add(key,img);
	}
	public BufferedImage get(String key) {
		return images.get(key);
	}
}
