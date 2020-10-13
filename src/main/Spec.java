package main;

public class Spec {
	//Level-specific constant definitions
	public static void loadSpec(int level) {
		BosonX.active=new LevelSettingsObject();
		BosonX.active.background=new background.Bg();
		switch(level) {
		case 1:
			BosonX.active.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.8f,0.8f,0.8f};

			BosonX.active.jump_relative=0.2f;
			BosonX.active.jump_duration=135;
			
			
			
			BosonX.active.speed0=0.4f;
			BosonX.active.speed1=0.75f;
			BosonX.active.gravity_i=0.2f;
			BosonX.active.gravity_o=0.5f;
			BosonX.active.background.bgcolor=new util.Vector4f(0.305f,0.4648f,0.6094f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(1.2f, 1.2f, 1.2f, 1f);
			BosonX.active.background.l=8;
			BosonX.active.background.w=0.2f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.intensity(0.7f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=true;
			
			BosonX.active.background.loadTextureType(1);
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.82f,0.79f,0.66f,1);
			
			BosonX.active.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.active.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.active.gen_distance=25;
			//BosonX.active.gen_width=15;
			BosonX.active.energy_gain=0.2f;
			BosonX.active.strictGeneration=true;
			
			break;
		case 2:
			BosonX.active.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.7f,0.7f,0.5f};
			BosonX.m.r.lightDiffuse=new float[] {1.6f,1.6f,1.2f};

			BosonX.active.jump_relative=0.2f;
			BosonX.active.jump_duration=135;
			
			BosonX.active.speed0=0.7f;
			BosonX.active.speed1=0.9f;
			BosonX.active.gravity_i=0.23f;
			BosonX.active.gravity_o=0.5f;
			
			BosonX.active.background.bgcolor=new util.Vector4f(0.945f,0.898f,0.246f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(0,0,0, 1f);
			BosonX.active.background.intensity(0.8f);
			BosonX.active.background.l=8;
			BosonX.active.background.w=0.2f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=true;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.262f,0.32f,0.34f,1);
			
			BosonX.active.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.active.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.active.C_color0=new util.Vector4f(0.262f, 0.32f, 0.34f,1);
			BosonX.active.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.active.C_l=1000;
			BosonX.active.C_c=400;
			
			BosonX.active.gen_distance=25;
			
			BosonX.active.energy_gain=0.3f;
			BosonX.active.strictGeneration=false;
			break;
		case 3:
			BosonX.active.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.7f,0.7f,0.5f};
			BosonX.m.r.lightDiffuse=new float[] {1.6f,1.6f,1.2f};

			BosonX.active.jump_relative=0.2f;
			BosonX.active.jump_duration=135;
			
			BosonX.active.speed0=0.7f;
			BosonX.active.speed1=1.0f;
			BosonX.active.gravity_i=0.25f;
			BosonX.active.gravity_o=0.5f;
			
			BosonX.active.background.bgcolor=new util.Vector4f(0.945f,0.379f,0.551f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(0,0,0, 1f);
			BosonX.active.background.intensity(0.8f);
			BosonX.active.background.l=10;
			BosonX.active.background.w=0.3f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=true;
			
			BosonX.active.background.loadTextureType(1);
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.367f,0.418f,0.477f,1);
			
			BosonX.active.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.active.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.active.C_color0=BosonX.active.P_color;
			BosonX.active.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.active.C_l=1000;
			BosonX.active.C_c=400;
			
			BosonX.active.gen_distance=25;
			
			BosonX.active.energy_gain=0.3f;
			BosonX.active.strictGeneration=true;
			break;
		case 4:
			BosonX.active.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.7f,0.7f,0.5f};
			BosonX.m.r.lightDiffuse=new float[] {1.6f,1.6f,1.2f};

			BosonX.active.jump_relative=0.2f;
			BosonX.active.jump_duration=135;
			
			BosonX.active.speed0=0.7f;
			BosonX.active.speed1=1.0f;
			BosonX.active.gravity_i=0.27f;
			BosonX.active.gravity_o=0.5f;
			
			BosonX.active.background.bgcolor=new util.Vector4f(0.7f,0.7f,0.7f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(1.4f,1.4f,1.4f, 1f);
			BosonX.active.background.intensity(0.8f);
			BosonX.active.background.l=10;
			BosonX.active.background.w=0.1f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.background.loadTextureType(1);
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.367f,0.418f,0.477f,1);
			
			BosonX.active.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.active.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.active.C_color0=BosonX.active.P_color;
			BosonX.active.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.active.C_l=1000;
			BosonX.active.C_c=400;
			
			BosonX.active.F_speed=1.3f;
			
			BosonX.active.gen_distance=25;
			
			BosonX.active.energy_gain=0.3f;
			BosonX.active.strictGeneration=true;
			
			
			break;
		case 5:
			BosonX.active.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.active.jump_relative=0.2f;
			BosonX.active.jump_duration=135;
			
			BosonX.active.speed0=0.7f;
			BosonX.active.speed1=1.0f;
			BosonX.active.gravity_i=0.27f;
			BosonX.active.gravity_o=0.5f;
			
			BosonX.active.background.bgcolor=new util.Vector4f(0.129f,0.148f,0.277f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(1.4f,1.4f,1.4f, 1f);
			BosonX.active.background.intensity(0.5f);
			BosonX.active.background.l=10;
			BosonX.active.background.w=0.1f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.background.loadTextureType(1);
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(1f,1f,1f,1);
			
			BosonX.active.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.active.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.active.C_color0=new util.Vector4f(0.065f,0.074f,0.114f,0);
			BosonX.active.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,0);
			
			BosonX.active.C_l=1000;
			BosonX.active.C_c=400;
			
			BosonX.active.F_speed=0.8f;
			
			BosonX.active.gen_distance=25;
			
			BosonX.active.energy_gain=0.3f;
			BosonX.active.strictGeneration=true;
			
			
			break;
		case 6:
			BosonX.active.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.active.jump_relative=0.2f;
			BosonX.active.jump_duration=135;
			
			BosonX.active.speed0=1.1f;
			BosonX.active.speed1=1.3f;
			BosonX.active.gravity_i=0.27f;
			BosonX.active.gravity_o=0.5f;
			
			BosonX.active.background.bgcolor=new util.Vector4f(0.129f,0.148f,0.277f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(1.4f,1.4f,1.4f, 0.1f);
			BosonX.active.background.intensity(0.5f);
			BosonX.active.background.l=25;
			BosonX.active.background.w=0.8f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=2.5f;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.background.HSBrandom=true;
			BosonX.active.background.Hmin=0.43f;
			BosonX.active.background.Hmax=0.53f;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.5f,0.5f,0.6f,1);
			
			BosonX.active.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.active.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.active.C_color0=new util.Vector4f(0.4f,0.4f,0.5f,1);
			BosonX.active.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.active.C_l=1000;
			BosonX.active.C_c=400;
			
			BosonX.active.F_speed=0.8f;
			
			BosonX.active.gen_distance=25;
			
			BosonX.active.energy_gain=0.15f;
			BosonX.active.strictGeneration=true;
			
			
			break;
		case 7:
			BosonX.active.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.active.jump_relative=0.2f;
			BosonX.active.jump_duration=135;
			
			BosonX.active.speed0=0.7f;
			BosonX.active.speed1=1.0f;
			BosonX.active.gravity_i=0.27f;
			BosonX.active.gravity_o=0.5f;
			
			BosonX.active.background.bgcolor=new util.Vector4f(0.211f,0.14f,0.093f,1);
			BosonX.active.background.partcolor=new util.Vector4f(1.4f,1.4f,1.4f, 1f);
			BosonX.active.background.intensity(0.5f);
			BosonX.active.background.l=10;
			BosonX.active.background.w=0.1f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.background.loadTextureType(1);
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.785f,0.573f,0.2f,1);
			
			BosonX.active.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.active.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.active.C_color0=new util.Vector4f(0.4f,0.4f,0.5f,1);
			BosonX.active.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.active.C_l=1000;
			BosonX.active.C_c=400;
			
			BosonX.active.F_speed=0.8f;
			
			BosonX.active.gen_distance=25;
			
			BosonX.active.energy_gain=0.3f;
			BosonX.active.strictGeneration=true;
			
			
			break;
		case 8:
			BosonX.active.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.active.jump_relative=0.2f;
			BosonX.active.jump_duration=135;
			
			BosonX.active.speed0=0.7f;
			BosonX.active.speed1=1.0f;
			BosonX.active.gravity_i=0.27f;
			BosonX.active.gravity_o=0.5f;
			
			BosonX.active.background.bgcolor=new util.Vector4f(0.17f,0.18f,0.32f,1);
			BosonX.active.background.partcolor=new util.Vector4f(1.4f,1.4f,1.4f, 1f);
			BosonX.active.background.intensity(0.5f);
			BosonX.active.background.l=10;
			BosonX.active.background.w=0.1f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.background.loadTextureType(1);
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.4f,0.7f,0.3f,1);
			
			BosonX.active.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.active.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.active.C_color0=new util.Vector4f(0.2f,0.35f,0.15f,1);
			BosonX.active.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.active.C_l=1000;
			BosonX.active.C_c=400;
			
			BosonX.active.F_speed=0.8f;
			
			BosonX.active.gen_distance=35;
			
			BosonX.active.energy_gain=0.6f;
			BosonX.active.strictGeneration=true;
			
			
			break;
		case 9:
			BosonX.active.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.active.jump_relative=0.2f;
			BosonX.active.jump_duration=135;
			
			BosonX.active.speed0=0.7f;
			BosonX.active.speed1=1.0f;
			BosonX.active.gravity_i=0.27f;
			BosonX.active.gravity_o=0.5f;
			
			BosonX.active.background.bgcolor=new util.Vector4f(0.3f,0.4f,0.5f,1);
			BosonX.active.background.partcolor=new util.Vector4f(0,0,0, 1f);
			BosonX.active.background.intensity(0.5f);
			BosonX.active.background.l=0.6f;
			BosonX.active.background.w=0.6f;
			BosonX.active.background.density=6f;
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.background.loadTextureType(1);
			BosonX.active.background.randomXR=true;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.31f,0.32f,0.4f,1);
			
			BosonX.active.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.active.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.active.C_color0=new util.Vector4f(0.31f,0.32f,0.4f,1);
			BosonX.active.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.active.C_l=1000;
			BosonX.active.C_c=400;
			
			BosonX.active.F_speed=0.8f;
			
			BosonX.active.gen_distance=33;
			
			BosonX.active.energy_gain=0.45f;
			BosonX.active.strictGeneration=true;
			
			
			break;
		case 10:
			BosonX.active.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.active.jump_relative=0.2f;
			BosonX.active.jump_duration=135;
			
			BosonX.active.speed0=0.9f;
			BosonX.active.speed1=1.1f;
			BosonX.active.gravity_i=0.3f;
			BosonX.active.gravity_o=0.5f;
			
			BosonX.active.background.bgcolor=new util.Vector4f(0.34f,0.36f,0.34f,1);
			BosonX.active.background.partcolor=new util.Vector4f(0,0,0, 1f);
			BosonX.active.background.intensity(0.5f);
			BosonX.active.background.l=10;
			BosonX.active.background.w=0.4f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.background.loadTextureType(1);
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.63f,0.64f,0.55f,1);
			
			BosonX.active.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.active.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.active.C_color0=new util.Vector4f(0.31f,0.32f,0.4f,1);
			BosonX.active.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.active.C_l=1000;
			BosonX.active.C_c=400;
			
			BosonX.active.F_speed=0.8f;
			
			BosonX.active.gen_distance=33;
			
			BosonX.active.energy_gain=0.25f;
			BosonX.active.strictGeneration=false;
			
			
			break;
		case 11:
			BosonX.active.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.active.jump_relative=0.2f;
			BosonX.active.jump_duration=135;
			
			BosonX.active.speed0=0.9f;
			BosonX.active.speed1=1.1f;
			BosonX.active.gravity_i=0.3f;
			BosonX.active.gravity_o=0.5f;
			
			BosonX.active.background.bgcolor=new util.Vector4f(0,0,0,1);
			BosonX.active.background.partcolor=new util.Vector4f(0.2f,0.3f,0.6f, 1f);
			BosonX.active.background.intensity(0.7f);
			BosonX.active.background.l=16;
			BosonX.active.background.w=0.4f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.background.loadTextureType(1);
			
			BosonX.active.background.t_min=5;
			BosonX.active.background.t_max=12;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.87f,0.46f,0.25f,1);
			
			BosonX.active.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,0);
			BosonX.active.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,0);
			
			BosonX.active.C_color0=new util.Vector4f(0.31f,0.32f,0.4f,1);
			BosonX.active.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.active.C_l=1000;
			BosonX.active.C_c=400;
			
			BosonX.active.F_speed=0.8f;
			
			BosonX.active.gen_distance=33;
			
			BosonX.active.energy_gain=0.25f;
			BosonX.active.strictGeneration=false;
			
			
			break;
		case 12:
			BosonX.active.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.active.jump_relative=0.2f;
			BosonX.active.jump_duration=135;
			
			BosonX.active.speed0=0.9f;
			BosonX.active.speed1=1.1f;
			BosonX.active.gravity_i=0.3f;
			BosonX.active.gravity_o=0.5f;
			
			BosonX.active.background.bgcolor=new util.Vector4f(0,0,0,1);
			BosonX.active.background.partcolor=new util.Vector4f(0.2f,0.3f,0.6f, 1f);
			BosonX.active.background.intensity(0.7f);
			BosonX.active.background.l=16;
			BosonX.active.background.w=0.4f;
			BosonX.active.background.density=0;
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(1,1,1,0);
			
			BosonX.active.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,0);
			BosonX.active.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,0);
			
			BosonX.active.C_color0=new util.Vector4f(0,0,0,0);
			BosonX.active.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,0);
			
			BosonX.active.C_l=1000;
			BosonX.active.C_c=400;
			
			BosonX.active.F_speed=0.8f;
			
			BosonX.active.gen_distance=33;
			
			BosonX.active.energy_gain=0.15f;
			BosonX.active.strictGeneration=false;
			
			
			break;
		case 13:
			BosonX.active.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.active.jump_relative=0.2f;
			BosonX.active.jump_duration=135;
			
			BosonX.active.speed0=0.9f;
			BosonX.active.speed1=1.1f;
			BosonX.active.gravity_i=0.3f;
			BosonX.active.gravity_o=0.5f;
			
			BosonX.active.background.bgcolor=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.active.background.partcolor=new util.Vector4f(0.27f, 0.447f, 0.6f, 1f);
			BosonX.active.background.intensity(0.7f);
			BosonX.active.background.l=16;
			BosonX.active.background.w=0.4f;
			BosonX.active.background.density=8f;
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.87f,0.46f,0.25f,1);
			
			BosonX.active.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.active.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.active.C_color0=new util.Vector4f(0.31f,0.32f,0.4f,1);
			BosonX.active.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.active.C_l=1000;
			BosonX.active.C_c=400;
			
			BosonX.active.F_speed=0.8f;
			
			BosonX.active.gen_distance=33;
			
			BosonX.active.energy_gain=0.25f;
			BosonX.active.strictGeneration=false;
			
			
			break;
		case 14:
			BosonX.active.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.active.jump_relative=0.2f;
			BosonX.active.jump_duration=135;
			
			BosonX.active.speed0=0.9f;
			BosonX.active.speed1=1.1f;
			BosonX.active.gravity_i=0.3f;
			BosonX.active.gravity_o=0.5f;
			
			BosonX.active.background.bgcolor=new util.Vector4f(0.37f, 0.68f, 0.5f,1);
			BosonX.active.background.partcolor=new util.Vector4f(0.2f,0.2f,0.2f, 1f);
			BosonX.active.background.intensity(0.7f);
			BosonX.active.background.l=2.5f;
			BosonX.active.background.w=2.5f;
			BosonX.active.background.density=3f;
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.background.loadTextureType(1);
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.34f,0.5f,0.54f,1);
			
			BosonX.active.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.active.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.active.C_color0=new util.Vector4f(0.31f,0.32f,0.4f,1);
			BosonX.active.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.active.C_l=1000;
			BosonX.active.C_c=400;
			
			BosonX.active.F_speed=0.8f;
			
			BosonX.active.gen_distance=40;
			
			BosonX.active.energy_gain=0.25f;
			BosonX.active.strictGeneration=false;
			
			
			break;
		case 15:
			BosonX.active.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.active.jump_relative=0.15f;
			BosonX.active.jump_duration=185;
			
			BosonX.active.speed0=0.9f;
			BosonX.active.speed1=1.1f;
			BosonX.active.gravity_i=0.3f;
			BosonX.active.gravity_o=0.5f;
			
			BosonX.active.background.bgcolor=new util.Vector4f(0.945f,0.898f,0.246f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(1,0.1f,0.1f, 1f);
			BosonX.active.background.intensity(0.7f);
			BosonX.active.background.l=16;
			BosonX.active.background.w=0.3f;
			BosonX.active.background.density=3f;
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.background.loadTextureType(1);
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.6f,0.4f,0.8f,1);
			
			BosonX.active.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.active.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.active.C_color0=new util.Vector4f(0.31f,0.32f,0.4f,1);
			BosonX.active.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.active.C_l=1000;
			BosonX.active.C_c=400;
			
			BosonX.active.F_speed=0.8f;
			
			BosonX.active.gen_distance=40;
			
			BosonX.active.energy_gain=0.25f;
			BosonX.active.strictGeneration=false;
			
			
			break;
		case 16:
			BosonX.active.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};
			
			BosonX.active.jump_relative=0.2f;
			BosonX.active.jump_duration=165;
			
			BosonX.active.speed0=1.2f;
			BosonX.active.speed1=1.4f;
			BosonX.active.gravity_i=0.3f;
			BosonX.active.gravity_o=0.5f;
			
			BosonX.active.background.bgcolor=new util.Vector4f(0.6f,0.8f,0.85f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(0.8f,0.9f,0.94f, 1f);
			BosonX.active.background.intensity(0.9f);
			BosonX.active.background.l=5;
			BosonX.active.background.w=5;
			BosonX.active.background.density=3f;
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.background.loadTextureType(1);
			BosonX.active.background.numPoles=2;
			BosonX.active.background.xt=200;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.85f,0.23f,0.16f,1f);
			
			BosonX.active.E_color0=new util.Vector4f(0.85f,0.23f,0.16f,1f);
			BosonX.active.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.active.C_color0=new util.Vector4f(0.31f,0.32f,0.4f,1);
			BosonX.active.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.active.C_l=1000;
			BosonX.active.C_c=400;
			
			BosonX.active.F_speed=0.8f;
			
			BosonX.active.gen_distance=80;
			
			BosonX.active.energy_gain=0.25f;
			BosonX.active.strictGeneration=false;
			
			
			break;
		case 17:
			BosonX.active.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.7f,0.5f,0.7f};

			BosonX.active.jump_relative=0.2f;
			BosonX.active.jump_duration=135;
			
			BosonX.active.speed0=0.9f;
			BosonX.active.speed1=1.1f;
			BosonX.active.gravity_i=0.3f;
			BosonX.active.gravity_o=0.5f;
			
			BosonX.active.background.bgcolor=new util.Vector4f(0.18f,0.14f,0.27f,1);
			BosonX.active.background.partcolor=new util.Vector4f(0,0,0, 1f);
			BosonX.active.background.intensity(1.1f);
			BosonX.active.background.l=16;
			BosonX.active.background.w=0.3f;
			BosonX.active.background.density=3f;
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.background.loadTextureType(1);
			
			BosonX.active.background.t_max=10;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.367f,0.418f,0.477f,1);
			
			BosonX.active.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.active.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.active.C_color0=new util.Vector4f(0.31f,0.32f,0.4f,1);
			BosonX.active.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.active.C_l=1000;
			BosonX.active.C_c=400;
			
			BosonX.active.F_speed=0.8f;
			
			BosonX.active.gen_distance=80;
			
			BosonX.active.energy_gain=0.3f;
			BosonX.active.strictGeneration=false;
			
			
			break;
		case 18:
			BosonX.active.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.7f,0.5f,0.7f};

			BosonX.active.jump_relative=0.2f;
			BosonX.active.jump_duration=165;
			
			BosonX.active.speed0=2.2f;
			BosonX.active.speed1=2.4f;
			BosonX.active.gravity_i=0.3f;
			BosonX.active.gravity_o=0.5f;
			
			BosonX.active.background.bgcolor=new util.Vector4f(1,1,1,1);
			BosonX.active.background.partcolor=new util.Vector4f(1,1,1, 1f);
			BosonX.active.background.intensity(0.4f);
			BosonX.active.background.l=11;
			BosonX.active.background.w=0.3f;
			BosonX.active.background.density=3f;
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=3;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.background.loadTextureType(1);
			
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.367f,0.418f,0.477f,1);
			
			BosonX.active.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.active.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.active.C_color0=new util.Vector4f(0.31f,0.32f,0.4f,1);
			BosonX.active.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.active.C_l=1000;
			BosonX.active.C_c=400;
			
			BosonX.active.F_speed=0.8f;
			
			BosonX.active.gen_distance=80;
			
			BosonX.active.energy_gain=0.1f;
			BosonX.active.strictGeneration=false;
			
			
			
			BosonX.active.background.pt=true;
			BosonX.active.background.separateE=true;
			
			BosonX.active.background.maskColor=new util.Vector3f(0,0,0);
			break;
		}
		BosonX.active.background.populate();
		BosonX.active.beenInitialized=true;
		
		BosonX.m.gen_cutoff=BosonX.active.gen_distance;
	}
}
