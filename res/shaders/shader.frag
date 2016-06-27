#version 330 core

in vec2 textureCoords;

out vec4 color;

uniform sampler2D texImage;
uniform vec4 objColor;

void main() {

	color = objColor * vec4(texture(texImage, textureCoords));
}