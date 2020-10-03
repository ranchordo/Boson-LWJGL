package main;

public class Spec {
	//Level-specific constant definitions
	public static void loadSpec(int level) {
		BosonX.m.background=new background.Bg();
		switch(level) {
		case 1:
			BosonX.m.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.8f,0.8f,0.8f};

			BosonX.m.jump_relative=0.2f;
			BosonX.m.jump_duration=135;
			
			
			
			BosonX.m.speed0=0.4f;
			BosonX.m.speed1=0.75f;
			BosonX.m.gravity_i=0.2f;
			BosonX.m.gravity_o=0.5f;
			BosonX.m.background.bgcolor=new util.Vector4f(0.305f,0.4648f,0.6094f,1f);
			BosonX.m.background.partcolor=new util.Vector4f(1.2f, 1.2f, 1.2f, 1f);
			BosonX.m.background.l=8;
			BosonX.m.background.w=0.2f;
			BosonX.m.background.density=3.5f;
			BosonX.m.background.intensity(0.7f);
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=true;
			
			BosonX.m.background.loadTextureType(1);
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(0.82f,0.79f,0.66f,1);
			
			BosonX.m.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.m.gen_distance=25;
			//BosonX.m.gen_width=15;
			BosonX.m.energy_gain=0.2f;
			BosonX.m.strictGeneration=true;
			
			break;
		case 2:
			BosonX.m.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.7f,0.7f,0.5f};
			BosonX.m.r.lightDiffuse=new float[] {1.6f,1.6f,1.2f};

			BosonX.m.jump_relative=0.2f;
			BosonX.m.jump_duration=135;
			
			BosonX.m.speed0=0.7f;
			BosonX.m.speed1=0.9f;
			BosonX.m.gravity_i=0.23f;
			BosonX.m.gravity_o=0.5f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0.945f,0.898f,0.246f,1f);
			BosonX.m.background.partcolor=new util.Vector4f(0,0,0, 1f);
			BosonX.m.background.intensity(0.8f);
			BosonX.m.background.l=8;
			BosonX.m.background.w=0.2f;
			BosonX.m.background.density=3.5f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=true;
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(0.262f,0.32f,0.34f,1);
			
			BosonX.m.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.m.C_color0=new util.Vector4f(0.262f, 0.32f, 0.34f,1);
			BosonX.m.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.m.C_l=1000;
			BosonX.m.C_c=400;
			
			BosonX.m.gen_distance=25;
			
			BosonX.m.energy_gain=0.3f;
			BosonX.m.strictGeneration=false;
			break;
		case 3:
			BosonX.m.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.7f,0.7f,0.5f};
			BosonX.m.r.lightDiffuse=new float[] {1.6f,1.6f,1.2f};

			BosonX.m.jump_relative=0.2f;
			BosonX.m.jump_duration=135;
			
			BosonX.m.speed0=0.7f;
			BosonX.m.speed1=1.0f;
			BosonX.m.gravity_i=0.25f;
			BosonX.m.gravity_o=0.5f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0.945f,0.379f,0.551f,1f);
			BosonX.m.background.partcolor=new util.Vector4f(0,0,0, 1f);
			BosonX.m.background.intensity(0.8f);
			BosonX.m.background.l=10;
			BosonX.m.background.w=0.3f;
			BosonX.m.background.density=3.5f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=true;
			
			BosonX.m.background.loadTextureType(1);
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(0.367f,0.418f,0.477f,1);
			
			BosonX.m.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.m.C_color0=BosonX.m.P_color;
			BosonX.m.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.m.C_l=1000;
			BosonX.m.C_c=400;
			
			BosonX.m.gen_distance=25;
			
			BosonX.m.energy_gain=0.3f;
			BosonX.m.strictGeneration=true;
			break;
		case 4:
			BosonX.m.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.7f,0.7f,0.5f};
			BosonX.m.r.lightDiffuse=new float[] {1.6f,1.6f,1.2f};

			BosonX.m.jump_relative=0.2f;
			BosonX.m.jump_duration=135;
			
			BosonX.m.speed0=0.7f;
			BosonX.m.speed1=1.0f;
			BosonX.m.gravity_i=0.27f;
			BosonX.m.gravity_o=0.5f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0.7f,0.7f,0.7f,1f);
			BosonX.m.background.partcolor=new util.Vector4f(1.4f,1.4f,1.4f, 1f);
			BosonX.m.background.intensity(0.8f);
			BosonX.m.background.l=10;
			BosonX.m.background.w=0.1f;
			BosonX.m.background.density=3.5f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=false;
			
			BosonX.m.background.loadTextureType(1);
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(0.367f,0.418f,0.477f,1);
			
			BosonX.m.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.m.C_color0=BosonX.m.P_color;
			BosonX.m.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.m.C_l=1000;
			BosonX.m.C_c=400;
			
			BosonX.m.F_speed=1.3f;
			
			BosonX.m.gen_distance=25;
			
			BosonX.m.energy_gain=0.3f;
			BosonX.m.strictGeneration=true;
			
			BosonX.m.p0_scalc=false;
			break;
		case 5:
			BosonX.m.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.m.jump_relative=0.2f;
			BosonX.m.jump_duration=135;
			
			BosonX.m.speed0=0.7f;
			BosonX.m.speed1=1.0f;
			BosonX.m.gravity_i=0.27f;
			BosonX.m.gravity_o=0.5f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0.129f,0.148f,0.277f,1f);
			BosonX.m.background.partcolor=new util.Vector4f(1.4f,1.4f,1.4f, 1f);
			BosonX.m.background.intensity(0.5f);
			BosonX.m.background.l=10;
			BosonX.m.background.w=0.1f;
			BosonX.m.background.density=3.5f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=false;
			
			BosonX.m.background.loadTextureType(1);
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(1f,1f,1f,1);
			
			BosonX.m.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.m.C_color0=new util.Vector4f(0.065f,0.074f,0.114f,0);
			BosonX.m.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,0);
			
			BosonX.m.C_l=1000;
			BosonX.m.C_c=400;
			
			BosonX.m.F_speed=0.8f;
			
			BosonX.m.gen_distance=25;
			
			BosonX.m.energy_gain=0.3f;
			BosonX.m.strictGeneration=true;
			
			BosonX.m.p0_scalc=true;
			break;
		case 6:
			BosonX.m.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.m.jump_relative=0.2f;
			BosonX.m.jump_duration=135;
			
			BosonX.m.speed0=1.1f;
			BosonX.m.speed1=1.3f;
			BosonX.m.gravity_i=0.27f;
			BosonX.m.gravity_o=0.5f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0.129f,0.148f,0.277f,1f);
			BosonX.m.background.partcolor=new util.Vector4f(1.4f,1.4f,1.4f, 0.1f);
			BosonX.m.background.intensity(0.5f);
			BosonX.m.background.l=25;
			BosonX.m.background.w=0.8f;
			BosonX.m.background.density=3.5f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=2.5f;
			BosonX.m.background.lightParticles=false;
			
			BosonX.m.background.HSBrandom=true;
			BosonX.m.background.Hmin=0.43f;
			BosonX.m.background.Hmax=0.53f;
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(0.5f,0.5f,0.6f,1);
			
			BosonX.m.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.m.C_color0=new util.Vector4f(0.4f,0.4f,0.5f,1);
			BosonX.m.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.m.C_l=1000;
			BosonX.m.C_c=400;
			
			BosonX.m.F_speed=0.8f;
			
			BosonX.m.gen_distance=25;
			
			BosonX.m.energy_gain=0.15f;
			BosonX.m.strictGeneration=true;
			
			BosonX.m.p0_scalc=true;
			break;
		case 7:
			BosonX.m.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.m.jump_relative=0.2f;
			BosonX.m.jump_duration=135;
			
			BosonX.m.speed0=0.7f;
			BosonX.m.speed1=1.0f;
			BosonX.m.gravity_i=0.27f;
			BosonX.m.gravity_o=0.5f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0.211f,0.14f,0.093f,1);
			BosonX.m.background.partcolor=new util.Vector4f(1.4f,1.4f,1.4f, 1f);
			BosonX.m.background.intensity(0.5f);
			BosonX.m.background.l=10;
			BosonX.m.background.w=0.1f;
			BosonX.m.background.density=3.5f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=false;
			
			BosonX.m.background.loadTextureType(1);
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(0.785f,0.573f,0.2f,1);
			
			BosonX.m.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.m.C_color0=new util.Vector4f(0.4f,0.4f,0.5f,1);
			BosonX.m.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.m.C_l=1000;
			BosonX.m.C_c=400;
			
			BosonX.m.F_speed=0.8f;
			
			BosonX.m.gen_distance=25;
			
			BosonX.m.energy_gain=0.3f;
			BosonX.m.strictGeneration=true;
			
			BosonX.m.p0_scalc=false;
			break;
		case 8:
			BosonX.m.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.m.jump_relative=0.2f;
			BosonX.m.jump_duration=135;
			
			BosonX.m.speed0=0.7f;
			BosonX.m.speed1=1.0f;
			BosonX.m.gravity_i=0.27f;
			BosonX.m.gravity_o=0.5f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0.17f,0.18f,0.32f,1);
			BosonX.m.background.partcolor=new util.Vector4f(1.4f,1.4f,1.4f, 1f);
			BosonX.m.background.intensity(0.5f);
			BosonX.m.background.l=10;
			BosonX.m.background.w=0.1f;
			BosonX.m.background.density=3.5f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=false;
			
			BosonX.m.background.loadTextureType(1);
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(0.4f,0.7f,0.3f,1);
			
			BosonX.m.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.m.C_color0=new util.Vector4f(0.2f,0.35f,0.15f,1);
			BosonX.m.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.m.C_l=1000;
			BosonX.m.C_c=400;
			
			BosonX.m.F_speed=0.8f;
			
			BosonX.m.gen_distance=35;
			
			BosonX.m.energy_gain=0.6f;
			BosonX.m.strictGeneration=true;
			
			BosonX.m.p0_scalc=false;
			break;
		case 9:
			BosonX.m.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.m.jump_relative=0.2f;
			BosonX.m.jump_duration=135;
			
			BosonX.m.speed0=0.7f;
			BosonX.m.speed1=1.0f;
			BosonX.m.gravity_i=0.27f;
			BosonX.m.gravity_o=0.5f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0.3f,0.4f,0.5f,1);
			BosonX.m.background.partcolor=new util.Vector4f(0,0,0, 1f);
			BosonX.m.background.intensity(0.5f);
			BosonX.m.background.l=0.6f;
			BosonX.m.background.w=0.6f;
			BosonX.m.background.density=6f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=false;
			
			BosonX.m.background.loadTextureType(1);
			BosonX.m.background.randomXR=true;
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(0.31f,0.32f,0.4f,1);
			
			BosonX.m.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.m.C_color0=new util.Vector4f(0.31f,0.32f,0.4f,1);
			BosonX.m.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.m.C_l=1000;
			BosonX.m.C_c=400;
			
			BosonX.m.F_speed=0.8f;
			
			BosonX.m.gen_distance=33;
			
			BosonX.m.energy_gain=0.45f;
			BosonX.m.strictGeneration=true;
			
			BosonX.m.p0_scalc=false;
			break;
		case 10:
			BosonX.m.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.m.jump_relative=0.2f;
			BosonX.m.jump_duration=135;
			
			BosonX.m.speed0=0.9f;
			BosonX.m.speed1=1.1f;
			BosonX.m.gravity_i=0.3f;
			BosonX.m.gravity_o=0.5f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0.34f,0.36f,0.34f,1);
			BosonX.m.background.partcolor=new util.Vector4f(0,0,0, 1f);
			BosonX.m.background.intensity(0.5f);
			BosonX.m.background.l=10;
			BosonX.m.background.w=0.4f;
			BosonX.m.background.density=3.5f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=false;
			
			BosonX.m.background.loadTextureType(1);
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(0.63f,0.64f,0.55f,1);
			
			BosonX.m.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.m.C_color0=new util.Vector4f(0.31f,0.32f,0.4f,1);
			BosonX.m.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.m.C_l=1000;
			BosonX.m.C_c=400;
			
			BosonX.m.F_speed=0.8f;
			
			BosonX.m.gen_distance=33;
			
			BosonX.m.energy_gain=0.25f;
			BosonX.m.strictGeneration=false;
			
			BosonX.m.p0_scalc=true;
			break;
		case 11:
			BosonX.m.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.m.jump_relative=0.2f;
			BosonX.m.jump_duration=135;
			
			BosonX.m.speed0=0.9f;
			BosonX.m.speed1=1.1f;
			BosonX.m.gravity_i=0.3f;
			BosonX.m.gravity_o=0.5f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0,0,0,1);
			BosonX.m.background.partcolor=new util.Vector4f(0.2f,0.3f,0.6f, 1f);
			BosonX.m.background.intensity(0.7f);
			BosonX.m.background.l=16;
			BosonX.m.background.w=0.4f;
			BosonX.m.background.density=3.5f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=false;
			
			BosonX.m.background.loadTextureType(1);
			
			BosonX.m.background.t_min=5;
			BosonX.m.background.t_max=12;
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(0.87f,0.46f,0.25f,1);
			
			BosonX.m.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,0);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,0);
			
			BosonX.m.C_color0=new util.Vector4f(0.31f,0.32f,0.4f,1);
			BosonX.m.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.m.C_l=1000;
			BosonX.m.C_c=400;
			
			BosonX.m.F_speed=0.8f;
			
			BosonX.m.gen_distance=33;
			
			BosonX.m.energy_gain=0.25f;
			BosonX.m.strictGeneration=false;
			
			BosonX.m.p0_scalc=true;
			break;
		case 12:
			BosonX.m.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.m.jump_relative=0.2f;
			BosonX.m.jump_duration=135;
			
			BosonX.m.speed0=0.9f;
			BosonX.m.speed1=1.1f;
			BosonX.m.gravity_i=0.3f;
			BosonX.m.gravity_o=0.5f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0,0,0,1);
			BosonX.m.background.partcolor=new util.Vector4f(0.2f,0.3f,0.6f, 1f);
			BosonX.m.background.intensity(0.7f);
			BosonX.m.background.l=16;
			BosonX.m.background.w=0.4f;
			BosonX.m.background.density=0;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=false;
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(1,1,1,0);
			
			BosonX.m.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,0);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,0);
			
			BosonX.m.C_color0=new util.Vector4f(0,0,0,0);
			BosonX.m.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,0);
			
			BosonX.m.C_l=1000;
			BosonX.m.C_c=400;
			
			BosonX.m.F_speed=0.8f;
			
			BosonX.m.gen_distance=33;
			
			BosonX.m.energy_gain=0.15f;
			BosonX.m.strictGeneration=false;
			
			BosonX.m.p0_scalc=true;
			break;
		case 13:
			BosonX.m.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.m.jump_relative=0.2f;
			BosonX.m.jump_duration=135;
			
			BosonX.m.speed0=0.9f;
			BosonX.m.speed1=1.1f;
			BosonX.m.gravity_i=0.3f;
			BosonX.m.gravity_o=0.5f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.m.background.partcolor=new util.Vector4f(0.27f, 0.447f, 0.6f, 1f);
			BosonX.m.background.intensity(0.7f);
			BosonX.m.background.l=16;
			BosonX.m.background.w=0.4f;
			BosonX.m.background.density=8f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=false;
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(0.87f,0.46f,0.25f,1);
			
			BosonX.m.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.m.C_color0=new util.Vector4f(0.31f,0.32f,0.4f,1);
			BosonX.m.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.m.C_l=1000;
			BosonX.m.C_c=400;
			
			BosonX.m.F_speed=0.8f;
			
			BosonX.m.gen_distance=33;
			
			BosonX.m.energy_gain=0.25f;
			BosonX.m.strictGeneration=false;
			
			BosonX.m.p0_scalc=true;
			break;
		case 14:
			BosonX.m.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.m.jump_relative=0.2f;
			BosonX.m.jump_duration=135;
			
			BosonX.m.speed0=0.9f;
			BosonX.m.speed1=1.1f;
			BosonX.m.gravity_i=0.3f;
			BosonX.m.gravity_o=0.5f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0.37f, 0.68f, 0.5f,1);
			BosonX.m.background.partcolor=new util.Vector4f(0.2f,0.2f,0.2f, 1f);
			BosonX.m.background.intensity(0.7f);
			BosonX.m.background.l=2.5f;
			BosonX.m.background.w=2.5f;
			BosonX.m.background.density=3f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=false;
			
			BosonX.m.background.loadTextureType(1);
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(0.34f,0.5f,0.54f,1);
			
			BosonX.m.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.m.C_color0=new util.Vector4f(0.31f,0.32f,0.4f,1);
			BosonX.m.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.m.C_l=1000;
			BosonX.m.C_c=400;
			
			BosonX.m.F_speed=0.8f;
			
			BosonX.m.gen_distance=40;
			
			BosonX.m.energy_gain=0.25f;
			BosonX.m.strictGeneration=false;
			
			BosonX.m.p0_scalc=true;
			break;
		case 15:
			BosonX.m.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};

			BosonX.m.jump_relative=0.15f;
			BosonX.m.jump_duration=185;
			
			BosonX.m.speed0=0.9f;
			BosonX.m.speed1=1.1f;
			BosonX.m.gravity_i=0.3f;
			BosonX.m.gravity_o=0.5f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0.945f,0.898f,0.246f,1f);
			BosonX.m.background.partcolor=new util.Vector4f(1,0.1f,0.1f, 1f);
			BosonX.m.background.intensity(0.7f);
			BosonX.m.background.l=16;
			BosonX.m.background.w=0.3f;
			BosonX.m.background.density=3f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=false;
			
			BosonX.m.background.loadTextureType(1);
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(0.6f,0.4f,0.8f,1);
			
			BosonX.m.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.m.C_color0=new util.Vector4f(0.31f,0.32f,0.4f,1);
			BosonX.m.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.m.C_l=1000;
			BosonX.m.C_c=400;
			
			BosonX.m.F_speed=0.8f;
			
			BosonX.m.gen_distance=40;
			
			BosonX.m.energy_gain=0.25f;
			BosonX.m.strictGeneration=false;
			
			BosonX.m.p0_scalc=true;
			break;
		case 16:
			BosonX.m.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};
			
			BosonX.m.jump_relative=0.2f;
			BosonX.m.jump_duration=165;
			
			BosonX.m.speed0=1.2f;
			BosonX.m.speed1=1.4f;
			BosonX.m.gravity_i=0.3f;
			BosonX.m.gravity_o=0.5f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0.6f,0.8f,0.85f,1f);
			BosonX.m.background.partcolor=new util.Vector4f(0.8f,0.9f,0.94f, 1f);
			BosonX.m.background.intensity(0.9f);
			BosonX.m.background.l=5;
			BosonX.m.background.w=5;
			BosonX.m.background.density=3f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=false;
			
			BosonX.m.background.loadTextureType(1);
			BosonX.m.background.numPoles=2;
			BosonX.m.background.xt=200;
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(0.85f,0.23f,0.16f,1f);
			
			BosonX.m.E_color0=new util.Vector4f(0.85f,0.23f,0.16f,1f);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.m.C_color0=new util.Vector4f(0.31f,0.32f,0.4f,1);
			BosonX.m.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.m.C_l=1000;
			BosonX.m.C_c=400;
			
			BosonX.m.F_speed=0.8f;
			
			BosonX.m.gen_distance=80;
			
			BosonX.m.energy_gain=0.25f;
			BosonX.m.strictGeneration=false;
			
			BosonX.m.p0_scalc=true;
			break;
		case 17:
			BosonX.m.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.7f,0.5f,0.7f};

			BosonX.m.jump_relative=0.2f;
			BosonX.m.jump_duration=135;
			
			BosonX.m.speed0=0.9f;
			BosonX.m.speed1=1.1f;
			BosonX.m.gravity_i=0.3f;
			BosonX.m.gravity_o=0.5f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0.18f,0.14f,0.27f,1);
			BosonX.m.background.partcolor=new util.Vector4f(0,0,0, 1f);
			BosonX.m.background.intensity(1.1f);
			BosonX.m.background.l=16;
			BosonX.m.background.w=0.3f;
			BosonX.m.background.density=3f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=false;
			
			BosonX.m.background.loadTextureType(1);
			
			BosonX.m.background.t_max=10;
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(0.367f,0.418f,0.477f,1);
			
			BosonX.m.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.m.C_color0=new util.Vector4f(0.31f,0.32f,0.4f,1);
			BosonX.m.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.m.C_l=1000;
			BosonX.m.C_c=400;
			
			BosonX.m.F_speed=0.8f;
			
			BosonX.m.gen_distance=80;
			
			BosonX.m.energy_gain=0.3f;
			BosonX.m.strictGeneration=false;
			
			BosonX.m.p0_scalc=true;
			break;
		case 18:
			BosonX.m.allowMultiJumps=false;
			
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.7f,0.5f,0.7f};

			BosonX.m.jump_relative=0.2f;
			BosonX.m.jump_duration=165;
			
			BosonX.m.speed0=2.2f;
			BosonX.m.speed1=2.4f;
			BosonX.m.gravity_i=0.3f;
			BosonX.m.gravity_o=0.5f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(1,1,1,1);
			BosonX.m.background.partcolor=new util.Vector4f(1,1,1, 1f);
			BosonX.m.background.intensity(0.4f);
			BosonX.m.background.l=11;
			BosonX.m.background.w=0.3f;
			BosonX.m.background.density=3f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=3;
			BosonX.m.background.lightParticles=false;
			
			BosonX.m.background.loadTextureType(1);
			
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(0.367f,0.418f,0.477f,1);
			
			BosonX.m.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.m.C_color0=new util.Vector4f(0.31f,0.32f,0.4f,1);
			BosonX.m.C_color1=new util.Vector4f(1f, 0.1f, 0.1f,1);
			
			BosonX.m.C_l=1000;
			BosonX.m.C_c=400;
			
			BosonX.m.F_speed=0.8f;
			
			BosonX.m.gen_distance=80;
			
			BosonX.m.energy_gain=0.1f;
			BosonX.m.strictGeneration=false;
			
			BosonX.m.p0_scalc=true;
			
			BosonX.m.background.pt=true;
			BosonX.m.background.separateE=true;
			
			BosonX.m.background.maskColor=new util.Vector3f(0,0,0);
			break;
		}
		BosonX.m.background.populate();
		BosonX.m.beenInitialized=true;
	}
}
