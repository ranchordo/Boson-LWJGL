package main;

public class LevelSettingsObject {
	//Level-specific constants
		public background.Bg background;
		public float gravity_i=0.27f; //Inner gravity
		public float gravity_o=0.5f; //Outer gravity
		public float jump_relative=0.2f; //Max jump velocity relative to grav
		public int jump_duration=135; //Jmp duration in 1/60 sec
		
		public float speed0=0.4f; //Speed, energy 0
		public float speed1=0.75f; //Speed, energy 100%
		
		public util.Vector4f P_color=new util.Vector4f(1,0,1,1); //P platform color
		
		public util.Vector4f C_color1=new util.Vector4f(1,0,1,1); //Collapsing colors
		public util.Vector4f C_color0=new util.Vector4f(1,0,1,1);
		public float C_c=100;
		public float C_l=1;
		
		public util.Vector4f E_color1=new util.Vector4f(1,0,1,1); //Energy colors
		public util.Vector4f E_color0=new util.Vector4f(1,0,1,1);
		public float E_c=100;
		public float E_l=1;
		
		public float gen_distance=0;
		
		public float energy_gain=0; //Gain per 1/60 sec
		
		public float F_speed=0;
		public float G_speed0=0;
		public float G_speed1=0;
		
		public boolean strictGeneration=false; //Whether we use a stricter gen algorithm
		
		public boolean cellRotationalLighting=true;
		
		public boolean allowMultiJumps=false; //Always false
		public boolean beenInitialized=false; //Have we been inited
}
