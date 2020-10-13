package main;

public class Spec_alt {
	//Alternate level-specific constant definitions
	public static void loadSpec_alt(int level) {
		switch(level) {
		case 1:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.8f,0.8f,0.8f};
			BosonX.active.background.bgcolor=new util.Vector4f(0.617f,0.352f,0.1172f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(1.2f, 1.2f, 1.2f, 1f);
			BosonX.active.background.l=8;
			BosonX.active.background.w=0.2f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.intensity(0.8f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.262f,0.32f,0.34f,1);
			
			break;
		case 2:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.8f,0.8f,0.8f};
			BosonX.active.background.bgcolor=new util.Vector4f(0.246f,0.58f,0.1172f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(2f, 2f, 2f, 1f);
			BosonX.active.background.l=8;
			BosonX.active.background.w=0.2f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.intensity(0.8f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.262f,0.32f,0.34f,1);
			
			break;
		case 3:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.8f,0.8f,0.8f};
			BosonX.active.background.bgcolor=new util.Vector4f(0.313f,0.172f,0.43f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(1.2f,1.2f,1.2f, 1f);
			BosonX.active.background.l=8;
			BosonX.active.background.w=0.2f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.intensity(0.6f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=true;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.5f,0.5f,0.34f,1);
			
			break;
		case 4:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.3f,0.3f,0.3f};
			BosonX.active.background.bgcolor=new util.Vector4f(0,0,0,1f);
			BosonX.active.background.partcolor=new util.Vector4f(1.2f,1.2f,1.2f, 1f);
			BosonX.active.background.l=8;
			BosonX.active.background.w=0.2f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.intensity(0.6f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=true;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.35f,0.4f,0.6f,1);
			
			break;
		case 5:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.6f,0.6f,0.3f};
			BosonX.active.background.bgcolor=new util.Vector4f(0.6f,0.25f,0.2f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(1.2f,1.2f,1.2f, 1f);
			BosonX.active.background.l=8;
			BosonX.active.background.w=0.2f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.intensity(0.8f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=true;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.6f,0.4f,0.2f,1);
			
			break;
		case 6:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.6f,0.6f,0.3f};
			BosonX.active.background.bgcolor=new util.Vector4f(0.6f,1,1,1f);
			BosonX.active.background.partcolor=new util.Vector4f(1.2f,1.2f,1.2f, 0.15f);
			BosonX.active.background.l=25;
			BosonX.active.background.w=0.8f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.intensity(0.8f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=true;
			
			BosonX.active.background.HSBcycle=0.005f;
			BosonX.active.background.HSBrandom=false;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(1,1,1,1);
			
			break;
		case 7:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.6f,0.6f,0.3f};
			BosonX.active.background.bgcolor=new util.Vector4f(0.7f,0.23f,0.16f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(1.2f,1.2f,1.2f, 1f);
			BosonX.active.background.l=10;
			BosonX.active.background.w=0.15f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.intensity(0.8f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=true;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.082f,0.746f,0.57f,1);
			
			break;
		case 8:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.6f,0.6f,0.3f};
			BosonX.active.background.bgcolor=new util.Vector4f(0.8f,0.4f,0.2f,1);
			BosonX.active.background.partcolor=new util.Vector4f(1.2f,1.2f,1f, 1f);
			BosonX.active.background.l=10;
			BosonX.active.background.w=0.15f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.intensity(0.8f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=true;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.2f,0.52f,0.86f,1);
			
			break;
		case 9:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.6f,0.6f,0.3f};
			BosonX.active.background.bgcolor=new util.Vector4f(0.082f,0.746f,0.57f,1);
			BosonX.active.background.partcolor=new util.Vector4f(0,0,0, 1f);
			BosonX.active.background.l=0.6f;
			BosonX.active.background.w=0.6f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.intensity(0.8f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=true;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.85f,0.23f,0.16f,1f);
			
			break;
		case 10:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.6f,0.6f,0.3f};
			BosonX.active.background.bgcolor=new util.Vector4f(0.18f,0.14f,0.27f,1);
			BosonX.active.background.partcolor=new util.Vector4f(1,1,1, 1f);
			BosonX.active.background.l=10;
			BosonX.active.background.w=0.4f;
			BosonX.active.background.density=6f;
			BosonX.active.background.intensity(0.8f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.background.t_min=5;
			BosonX.active.background.t_max=7;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.6f,1.0f,0.09f,1f);
			
			break;
		case 11:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.6f,0.6f,0.3f};
			BosonX.active.background.bgcolor=new util.Vector4f(0.1f,0.2f,0.5f,1);
			BosonX.active.background.partcolor=new util.Vector4f(0,0,0, 1f);
			BosonX.active.background.l=10;
			BosonX.active.background.w=0.4f;
			BosonX.active.background.density=3.5f;
			BosonX.active.background.intensity(0.8f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.6f,0.2f,0.6f,1f);
			
			break;
		case 12:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.6f,0.6f,0.3f};
			BosonX.active.background.bgcolor=new util.Vector4f(0.6f,1,1,1);
			BosonX.active.background.partcolor=new util.Vector4f(0,0,0, 1f);
			BosonX.active.background.l=10;
			BosonX.active.background.w=0.4f;
			BosonX.active.background.density=0;
			BosonX.active.background.intensity(0.8f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.background.HSBcycle=0.005f;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(1,1,1,1f);
			
			break;
		case 13:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.6f,0.6f,0.3f};
			BosonX.active.background.bgcolor=new util.Vector4f(0.945f,0.898f,0.246f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(0,0,0, 1f);
			BosonX.active.background.l=16;
			BosonX.active.background.w=0.4f;
			BosonX.active.background.density=8;
			BosonX.active.background.intensity(0.8f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.6f,0.2f,0.6f,1f);
			
			break;
		case 14:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.6f,0.6f,0.3f};
			BosonX.active.background.bgcolor=new util.Vector4f(0.082f,0.746f,0.57f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(1,1,1, 1f);
			BosonX.active.background.l=2.5f;
			BosonX.active.background.w=2.5f;
			BosonX.active.background.density=8;
			BosonX.active.background.intensity(0.8f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.85f,0.23f,0.16f,1f);
			
			break;
		case 15:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.6f,0.6f,0.3f};
			BosonX.active.background.bgcolor=new util.Vector4f(0.1f,0.1f,0.1f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(0.6f,0.2f,0.6f, 1f);
			BosonX.active.background.l=16;
			BosonX.active.background.w=0.3f;
			BosonX.active.background.density=3;
			BosonX.active.background.intensity(0.8f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.4f,0.8f,0.4f,1f);
			
			break;
		case 16:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.6f,0.6f,0.3f};
			BosonX.active.background.bgcolor=new util.Vector4f(0.1f,0.1f,0.1f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(0.4f,0.8f,0.4f,1f);
			BosonX.active.background.l=5;
			BosonX.active.background.w=5;
			BosonX.active.background.density=3;
			BosonX.active.background.intensity(0.8f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.4f,0.8f,0.4f,1f);
			
			break;
		case 17:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.6f,0.6f,0.3f};
			BosonX.active.background.bgcolor=new util.Vector4f(0.8f,0.2f,0.6f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(0.8f,0.2f,0.6f,1f);
			BosonX.active.background.l=13;
			BosonX.active.background.w=0.3f;
			BosonX.active.background.density=3;
			BosonX.active.background.intensity(0.6f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(1.0f,0.8f,0.1f,1f);
			
			break;
		case 18:
			BosonX.m.r.lightAmbient=new float[] {0.2f,0.2f,0.2f};
			BosonX.m.r.lightDiffuse=new float[] {0.6f,0.6f,0.3f};
			BosonX.active.background.bgcolor=new util.Vector4f(0.5f,1,0.6f,1f);
			BosonX.active.background.partcolor=new util.Vector4f(1,1,1,1f);
			BosonX.active.background.l=13;
			BosonX.active.background.w=0.3f;
			BosonX.active.background.density=3;
			BosonX.active.background.intensity(0.6f);
			BosonX.active.background.y_variance=10;
			BosonX.active.background.v_variance=1;
			BosonX.active.background.lightParticles=false;
			
			BosonX.active.background.HSBrandom=true;
			BosonX.active.background.Hmax=1;
			BosonX.active.background.Hmin=0;
			
			BosonX.active.background.HSBcycle=0.005f;
			BosonX.active.background.maskHSBcycle=0.005f;
			
			BosonX.active.E_l=1;
			BosonX.active.E_c=2000;
			
			BosonX.active.P_color=new util.Vector4f(0.4f,0.8f,0.4f,1f);
			
			BosonX.active.background.maskColor=new util.Vector3f(0,1,1);
			break;
		}
		BosonX.active.background.flushParticles();
		for(Cell c : BosonX.m.r.celllist) {
			c.resetColors();
		}
	}
	
}
