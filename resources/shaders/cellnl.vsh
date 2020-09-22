#version 120

varying vec4 col;
void main() {
	gl_Position=ftransform();
	col=gl_Color;
}