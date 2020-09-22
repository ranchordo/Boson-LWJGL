#version 120

varying vec4 diffuse;
varying vec4 col;
void main() {
	gl_FragColor=(gl_LightSource[1].ambient+diffuse) * col;
}