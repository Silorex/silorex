#version 400 core

const float density = 0.0035;
const float gradient = 3; //1.5
const int MAX_LIGHTS = 16;

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[MAX_LIGHTS];
uniform bool useFakeLighting;

out vec2 pass_textureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector[MAX_LIGHTS];
out vec3 toCameraVector;
out float visibility;

void main()
{
	vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
	vec4 positionRelativeToCamera = viewMatrix * worldPosition;
	gl_Position = projectionMatrix * positionRelativeToCamera;
	pass_textureCoords = textureCoords;

	vec3 actualNormal = normal;
	if(useFakeLighting == true) { actualNormal = vec3(0, 1, 0); }
	surfaceNormal = (transformationMatrix * vec4(actualNormal, 0.0)).xyz;

	for(int i = 0; i < MAX_LIGHTS; i++)
	{
		toLightVector[i] = lightPosition[i] - worldPosition.xyz;
	}

	toCameraVector = (inverse(viewMatrix) * vec4(0, 0, 0, 1)).xyz - worldPosition.xyz;

	float distance = length(positionRelativeToCamera.xyz);
	visibility = exp(-pow((distance*density), gradient));
	visibility = clamp(visibility, 0.0, 1.0);
}