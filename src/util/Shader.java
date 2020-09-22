package util;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shader {
	private int program;
	private int vs;
	private int fs;
	public Shader(String fname) {
		program=glCreateProgram();
		vs=glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, readFile(fname+".vsh"));
		glCompileShader(vs);
		if(glGetShaderi(vs, GL_COMPILE_STATUS) != 1) {
			System.err.println(glGetShaderInfoLog(vs));
			System.exit(1);
		}
		
		fs=glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, readFile(fname+".fsh"));
		glCompileShader(fs);
		if(glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
			System.err.println(glGetShaderInfoLog(fs));
			System.exit(1);
		}
		
		glAttachShader(program,vs);
		glAttachShader(program,fs);
		
		glBindAttribLocation(program,0,"vertices");
		
		glLinkProgram(program);
		if(glGetProgrami(program,GL_LINK_STATUS)!=1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
		glValidateProgram(program);
		if(glGetProgrami(program,GL_VALIDATE_STATUS)!=1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
	}
	
	public void setUniform(String name, float value) {
		int location=glGetUniformLocation(program,name);
		if(location!=-1) {
			glUniform1f(location,value);
		}
	}
	
	public void bind() {
		glUseProgram(program);
	}
	
	private String readFile(String fname) {
		StringBuilder string=new StringBuilder();
		BufferedReader b;
		try {
			b=new BufferedReader(new InputStreamReader(main.BosonX.class.getResourceAsStream("/shaders/"+fname)));
			String line;
			while((line=b.readLine())!=null) {
				string.append(line);
				string.append("\n");
			}
			b.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return string.toString();
	}
}
