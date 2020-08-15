package main;

public class Spec {
	//Level-specific constant definitions
	public static void loadSpec(int level) {
		BosonX.m.background=new background.Bg();
		switch(level) {
		case 1:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.8f,0.8f,0.8f};
			BosonX.m.speed0=0.4f;
			BosonX.m.speed1=0.75f;
			BosonX.m.background.bgcolor=new util.Vector4f(0.305f,0.4648f,0.6094f,1f);
			BosonX.m.background.partcolor=new util.Vector4f(1.2f, 1.2f, 1.2f, 1f);
			BosonX.m.background.l=8;
			BosonX.m.background.w=0.2f;
			BosonX.m.background.density=3.5f;
			BosonX.m.background.intensity(0.45f);
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=true;
			
			BosonX.m.E_l=1;
			BosonX.m.E_c=2000;
			
			BosonX.m.P_color=new util.Vector4f(0.82f,0.79f,0.66f,1);
			
			BosonX.m.E_color0=new util.Vector4f(0.27f, 0.447f, 0.6f,1);
			BosonX.m.E_color1=new util.Vector4f(0.56f, 1.3f, 1.4f,1);
			
			BosonX.m.gen_distance=25;
			//BosonX.m.gen_width=15;
			BosonX.m.energy_gain=0.2f;
			BosonX.m.strictGeneration=false;
			break;
		case 2:
			BosonX.m.r.lightAmbient=new float[] {0.7f,0.7f,0.5f};
			BosonX.m.r.lightDiffuse=new float[] {1.6f,1.6f,1.2f};
			
			BosonX.m.speed0=0.7f;
			BosonX.m.speed1=0.9f;
			
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
			
			BosonX.m.energy_gain=0.25f;
			BosonX.m.strictGeneration=false;
			break;
		case 3:
			BosonX.m.r.lightAmbient=new float[] {0.7f,0.7f,0.5f};
			BosonX.m.r.lightDiffuse=new float[] {1.6f,1.6f,1.2f};
			
			BosonX.m.speed0=0.7f;
			BosonX.m.speed1=1.0f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0.945f,0.379f,0.551f,1f);
			BosonX.m.background.partcolor=new util.Vector4f(0,0,0, 1f);
			BosonX.m.background.intensity(0.8f);
			BosonX.m.background.l=10;
			BosonX.m.background.w=0.3f;
			BosonX.m.background.density=3.5f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=true;
			
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
			BosonX.m.strictGeneration=false;
			break;
		case 4:
			BosonX.m.r.lightAmbient=new float[] {0.7f,0.7f,0.5f};
			BosonX.m.r.lightDiffuse=new float[] {1.6f,1.6f,1.2f};
			
			BosonX.m.speed0=0.7f;
			BosonX.m.speed1=1.0f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0.7f,0.7f,0.7f,1f);
			BosonX.m.background.partcolor=new util.Vector4f(1.4f,1.4f,1.4f, 1f);
			BosonX.m.background.intensity(0.8f);
			BosonX.m.background.l=10;
			BosonX.m.background.w=0.1f;
			BosonX.m.background.density=3.5f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=false;
			
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
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};
			
			BosonX.m.speed0=0.7f;
			BosonX.m.speed1=1.0f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0.129f,0.148f,0.277f,1f);
			BosonX.m.background.partcolor=new util.Vector4f(1.4f,1.4f,1.4f, 1f);
			BosonX.m.background.intensity(0.5f);
			BosonX.m.background.l=10;
			BosonX.m.background.w=0.1f;
			BosonX.m.background.density=3.5f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
			BosonX.m.background.lightParticles=false;
			
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
			BosonX.m.strictGeneration=false;
			
			BosonX.m.p0_scalc=true;
			break;
		case 6:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.5f,0.5f,0.5f};
			
			BosonX.m.speed0=1.1f;
			BosonX.m.speed1=1.3f;
			
			BosonX.m.background.bgcolor=new util.Vector4f(0.129f,0.148f,0.277f,1f);
			BosonX.m.background.partcolor=new util.Vector4f(1.4f,1.4f,1.4f, 0.3f);
			BosonX.m.background.intensity(0.5f);
			BosonX.m.background.l=25;
			BosonX.m.background.w=0.8f;
			BosonX.m.background.density=3.5f;
			BosonX.m.background.y_variance=10;
			BosonX.m.background.v_variance=1;
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
		}
		BosonX.m.background.populate();
	}
}
