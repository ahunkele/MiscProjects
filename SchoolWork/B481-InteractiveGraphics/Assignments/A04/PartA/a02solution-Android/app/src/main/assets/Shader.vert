//
//  Shader.vsh
//  a02template
//
//  Created by Mitja Hmeljak on 2017-02-13.
//

uniform float u_Width;
uniform float u_Height;

uniform float p0;
uniform float p1;
uniform float p2;
uniform float p3;
uniform float p4;
uniform float p5;
uniform float p6;
uniform float p7;

uniform bool flag;

attribute vec4 a_Position;


mat4 myOrtho2D(float pLeft, float pRight, float pBottom, float pTop) {
    float lNear = -1.0;
    float lFar = 1.0;
    float rl = pRight-pLeft;
    float tb = pTop-pBottom;
    float fn = lFar-lNear;
    // the returned matrix is defined "transposed", i.e. the last row
    //   is really the last column as used in matrix multiplication:
    return mat4(2.0/rl,             0.0,                0.0,               0.0,
                0.0,                2.0/tb,             0.0,               0.0,
                0.0,                0.0,                -2.0/fn,           0.0,
                -(pRight+pLeft)/rl, -(pTop+pBottom)/tb, -(lFar+lNear)/fn,  1.0 );
}

vec4 Bezier(float delta, float ap0, float ap1, float ap2, float ap3, float ap4, float ap5, float ap6, float ap7){
    vec4 pt0 = vec4(ap0, ap1, 0.0, 0.0);
    vec4 pt1 = vec4(ap2, ap3, 0.0, 0.0);
    vec4 pt2 = vec4(ap4, ap5, 0.0, 0.0);
    vec4 pt3 = vec4(ap6, ap7, 0.0, 0.0);

    float t = delta;
    float tSq = t * t;
    float oneMT = 1.0 - t;
    float oneMTSq = oneMT * oneMT;
    //return vec4(0.0);
    float xBez = oneMTSq * oneMT * pt0.x + 3.0 * t * oneMTSq * pt1.x + 3.0 * tSq * oneMT * pt2.x + tSq * t * pt3.x;
    float yBez = oneMTSq * oneMT * pt0.y + 3.0 * t * oneMTSq * pt1.y + 3.0 * tSq * oneMT * pt2.y + tSq * t * pt3.y;


    return vec4(xBez, yBez, 0.0, 1.0);
}

// define a varying variable:
varying vec2 var_Position;

void main() {

    mat4 projectionMatrix = myOrtho2D(0.0, u_Width, 0.0, u_Height);

    gl_PointSize = 10.0;

    if(flag){
             gl_Position = projectionMatrix * Bezier(a_Position.x, p0, p1, p2, p3, p4, p5, p6, p7);
    }
    else{
     gl_Position = projectionMatrix * a_Position;

    }


    // the value for var_Position is set in this vertex shader,
    // then it goes through the interpolator before being
    // received (interpolated!) by a fragment shader:
    var_Position = gl_Position.xy;
}
