#version 330 core

in vec3 position;
in vec2 texcoord;
in vec3 normal;

out vec3 vertexPos;
out vec2 textureCoord;
out vec3 faceNormal;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main() {

	vertexPos = vec3(modelMatrix * vec4(position, 1));
	textureCoord = texcoord;
	faceNormal = mat3(transpose(inverse(modelMatrix))) * normal;
	
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(position, 1.0);
}