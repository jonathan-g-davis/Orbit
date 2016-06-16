#version 330 core

in vec2 textureCoords;

out vec4 color;

uniform sampler2D texImage;

void main() {

	color = vec4(texture(texImage, textureCoords));
}