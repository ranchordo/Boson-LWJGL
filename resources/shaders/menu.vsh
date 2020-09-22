#version 120

varying vec2 texCoords;
varying vec4 col;
void main() {
	gl_Position=ftransform();
	texCoords=gl_MultiTexCoord0.st;
	col=gl_Color;
}