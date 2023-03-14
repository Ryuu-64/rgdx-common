#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
varying vec2 v_resolution;
uniform sampler2D u_texture;

float normalProbabilityDensity(in float x, in float sigma) {
    return 0.39894 * exp(-(x * x) / (2.0 * sigma * sigma)) / sigma;
}

vec4 gaussianBlur(sampler2D texture) {
    const int kernelSize = 65;
    const int halfKernelSize = (kernelSize - 1) / 2;
    float kernel[kernelSize];
    vec4 color = vec4(0.0);

    float sigma = 3.0;
    float kernelSum = 0.0;
    for (int i = 0; i <= halfKernelSize; i++)
    {
        kernel[halfKernelSize - i] = normalProbabilityDensity(float(i), sigma);
        kernel[halfKernelSize + i] = kernel[halfKernelSize - i];
    }

    for (int i = 0; i < kernelSize; i++)
    {
        kernelSum += kernel[i];
    }

    for (int i = 0; i < kernelSize; i++)
    {
        kernel[i] = kernel[i] / kernelSum;
    }

    for (int i = -halfKernelSize; i <= halfKernelSize; i++)
    {
        for (int j = -halfKernelSize; j <= halfKernelSize; j++)
        {
            color += kernel[halfKernelSize + i] * kernel[halfKernelSize + j] * texture2D(u_texture, v_texCoords.xy + vec2(float(i), float(j)) / v_resolution).rgba;
        }
    }

    return color;
}

void main() {
    gl_FragColor = gaussianBlur(u_texture) * v_color;
}