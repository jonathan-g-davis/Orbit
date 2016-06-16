#version 330 core

in vec3 vertexPos;
in vec2 textureCoord;
in vec3 faceNormal;

out vec4 color;

struct Material {
	sampler2D diffuse;
	sampler2D specular;
	float shininess;
};

uniform vec3 viewPos;
uniform Material material;

void main() {

	color = vec4(texture(material.diffuse, textureCoord));
}