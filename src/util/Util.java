package util;

import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.BufferUtils;

public class Util {
	public static FloatBuffer asFloatBuffer(float[] values) {
		FloatBuffer buffer=BufferUtils.createFloatBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}
	private static Random rand=new Random();
	public static int randint(int max) {
		return rand.nextInt(max);
	}
}
