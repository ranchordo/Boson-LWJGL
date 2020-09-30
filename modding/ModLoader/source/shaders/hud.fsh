#version 120

varying vec2 texCoords;
varying vec4 col;
uniform sampler2D tex;
void main() {
	gl_FragColor=vec4(texture2D(tex,texCoords).x,texture2D(tex,texCoords).y,texture2D(tex,texCoords).z,col.w*texture2D(tex,texCoords).w);
}