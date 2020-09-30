#version 120
#define pi 3.1415926535897932384

uniform float rot=0;

varying vec4 diffuse;
varying vec4 col;
void main() {
	gl_Position=ftransform();
	
	vec3 n=gl_Normal;
	
	float theta=rot;
	
	float proj_mag=sqrt(pow(n.x,2)+pow(n.y,2));
	
	vec3 normal=vec3(proj_mag*cos(atan(n.y,n.x)+theta),proj_mag*sin(atan(n.y,n.x)+theta),n.z);
	vec3 lightVector=normalize(gl_LightSource[1].position.xyz);
	float nxDir=max(0.0, dot(normal,lightVector));
	diffuse=gl_LightSource[1].diffuse * nxDir;
	col=gl_Color;
}