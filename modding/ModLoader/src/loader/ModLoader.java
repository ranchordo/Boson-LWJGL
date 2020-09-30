package loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class ModLoader { //Refer to the example mod for how to use this.
	public static FileFilter dirs=new FileFilter() { //File filter for only directories
		public boolean accept(File in) {
			return in.isDirectory();
		}
	};
	public static FileFilter files=new FileFilter() { //File filter for only files
		public boolean accept(File in) {
			return !in.isDirectory();
		}
	};
	
	public static void printInfo(String iname) {
		InputStream i=null;
		try {
			i=new FileInputStream(iname);
		} catch (FileNotFoundException e1) {
			return;
		}
		BufferedReader br=new BufferedReader(new InputStreamReader(i));
		
		try {
			String line;
			System.out.println("[ModLoader]: Printing mod info: ");
			while((line=br.readLine())!=null) {
				System.out.println("[ModLoader]: "+line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void main(String[] args) {
		File jarpath=new File(ModLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath()); //Get path outside jar
		String externalPath=jarpath.getParentFile().getAbsolutePath().replace("\\","/");
		
		printInfo(externalPath+"/mod/mod-info.txt");
		//This builds a list of the changed files.
		ArrayList<String> n=new ArrayList<String>();
		for(String s : fileList(externalPath+"/mod",".class",".vsh",".fsh")) {
			n.add(new File(new File(s).getParent()).getName()+"/"+new File(s).getName());
		}
		for(File f : new File(externalPath+"/mod").listFiles(files)) {
			n.add(f.getName());
		}
		
		
		System.out.println("Copying original files");
		
		//Clear our ./modded output folder
		File ff=new File(externalPath+"/modded");
		if(!ff.exists()) {
			ff.mkdir();
		}
		boolean deleted=false;
		while(!deleted) { //Yeah, whatever. I know it's not the most efficient, but it works, okay?
			deleted=true;
			for(String s : everythingList(externalPath+"/modded","")) {
				new File(s).delete();
				deleted=false;
			}
		}
		
		//Create code directories and copy all the original code
		ArrayList<String> packages=new ArrayList<String>(); //A list of the original folders for code files (main, shaders, background, audio, etc)
		for(String s : fileList(externalPath+"/source",".class",".vsh",".fsh")) { //We're counting shaders as code
			System.out.println(s); //List *all* original classes
			String packname=new File(new File(s).getParent()).getName();
			if(!packages.contains(packname)) { //If we haven't made this code directory yet
				packages.add(packname);
				new File(externalPath+"/modded/"+packname).mkdir(); //Make the directory
			}
			String sname=new File(new File(s).getParent()).getName()+"/"+new File(s).getName();
			if(!n.contains(sname)) {copy(s, externalPath+"/modded/"+sname);} //If (the mod doesn't contain an updated version of the file) {Copy the original file over}
			
		}
		
		//Copy the original assets
		for(File f : new File(externalPath+"/source").listFiles(files)) {
			System.out.println(f.getAbsolutePath());
			if(!n.contains(f.getName())) { //If (the mod doesn't contain an updated version of the file) {Copy the original file over}
				copy(f.getAbsolutePath(),externalPath+"/modded/"+f.getName());
			}
		}
		
		//Copy new files
		//We don't need to check for existence. We just need to copy files over.
		System.out.println("\nCopying new files");
		for(String s : fileList(externalPath+"/mod",".class",".vsh",".fsh")) {
			System.out.println(s); //List *all* original assets
			String sname=new File(new File(s).getParent()).getName()+"/"+new File(s).getName(); //Get a name like "main/BosonX.class"
			copy(s, externalPath+"/modded/"+sname); //Copy the code files
		}
		for(File f : new File(externalPath+"/mod").listFiles(files)) {
			copy(f.getAbsolutePath(),externalPath+"/modded/"+f.getName()); //Copy the new assets
		}
		System.out.println("\n\n");
		
		System.out.println("Exit code "+exec("java -cp "
				+ "./source/lwjgl-3.2.3/lwjgl/*;"
				+ "./source/lwjgl-3.2.3/lwjgl-opengl/*;"
				+ "./source/lwjgl-3.2.3/lwjgl-openal/*;"
				+ "./source/lwjgl-3.2.3/lwjgl-glfw/*;"
				+ "./source/lwjgl-3.2.3/lwjgl-stb/*;"
				+ "./modded "
				+ "main/BosonX")); //EXECUTE!
	}
	private static void printLines(String cmd,InputStream ins,boolean err) throws Exception { //Print an inputstream realtime
        String line=null;
        BufferedReader in=new BufferedReader(new InputStreamReader(ins));
        while((line=in.readLine())!=null) {
            if(!err) {System.out.println(cmd+" "+line);}
            else {System.err.println(cmd+" "+line);}
        }
      }
	public static int exec(String cmd) { //Exec command (simple)
		try {
			Process proc=Runtime.getRuntime().exec(cmd);
			try {
				printLines("[Boson-LWJGL Modded]: ",proc.getInputStream(),false);
				printLines("[Boson-LWJGL Modded]: ",proc.getErrorStream(),true);
			} catch (Exception e1) {
				e1.printStackTrace();
				System.exit(1);
			}
			while(true) {
				try {
					proc.waitFor();
				} catch (InterruptedException e) {
					continue;
				}
				break;
			}
			return proc.exitValue();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return -1;
	}
	public static ArrayList<String> fileList(String path, String... exts) { //Recursively list files that end with one of exts
	
		File target=new File(path);
		ArrayList<String> ret=new ArrayList<String>();
		for(File f : target.listFiles()) {
			for(String ext : exts) {
				if(f.getAbsolutePath().endsWith(ext)) {ret.add(f.getAbsolutePath());}
			}
		}
		for(File f : target.listFiles(dirs)) {
			ret.addAll(fileList(f.getAbsolutePath(),exts));
		}
		return ret;
	}
	public static void copy(String source, String dest) { //Copy from source to dest
		File f1=new File(source);
		if(!f1.exists()) {
			System.err.println(source+" does not exist!");
			System.exit(1);
		}
		File f2=new File(dest);
		if(!f2.exists()) {
			try {
				f2.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		try {
			FileInputStream s_stream=new FileInputStream(source);
			FileOutputStream d_stream=new FileOutputStream(dest);
			FileChannel s=s_stream.getChannel();
			FileChannel d=d_stream.getChannel();
			
			d.transferFrom(s,0,s.size());
			
			s_stream.close();
			d_stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	public static ArrayList<String> everythingList(String path, String ext) { //Recursively list *everything*. This is useful for deleting things.
		File target=new File(path);
		ArrayList<String> ret=new ArrayList<String>();
		for(File f : target.listFiles()) {
			if(f.getAbsolutePath().endsWith(ext)) {ret.add(f.getAbsolutePath());}
		}
		for(File f : target.listFiles(dirs)) {
			ret.add(f.getAbsolutePath());
			ret.addAll(fileList(f.getAbsolutePath(),ext));
		}
		return ret;
	}
}
