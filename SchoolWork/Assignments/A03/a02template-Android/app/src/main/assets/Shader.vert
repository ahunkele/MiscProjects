//
//  Shader.vsh
//  a03template
//
//  Created by Mitja Hmeljak on 2017-03-02.
//  Copyright 2017 B481 Spring 2017. All rights reserved.
//

uniform float u_Width;
uniform float u_Height;

uniform float u_TX;
uniform float u_TY;

attribute vec4 a_Position;

mat4 myOrtho2D(float pLeft, float pRight, float pBottom, float pTop) {
    float lNear = -1.0;
    float lFar = 1.0;
    float rl = pRight-pLeft;
    float tb = pTop-pBottom;
    float fn = lFar-lNear;
    // the returned matrix is defined "transposed", i.e. the last row
    //   is really the last column as used in matrix multiplication:
    return mat4(  2.0/rl,                0.0,              0.0,  0.0,
                0.0,             2.0/tb,              0.0,  0.0,
                0.0,                0.0,          -2.0/fn,  0.0,
                -(pRight+pLeft)/rl, -(pTop+pBottom)/tb, -(lFar+lNear)/fn,  1.0 );
}

mat4 myTranslate2D(float pTX, float pTY) {
    // the returned matrix is defined "transposed", i.e. the last row
    //   is really the last column as used in matrix multiplication:
    return mat4(  1.0,         0.0,         0.0,      0.0,
                  0.0,         1.0,         0.0,      0.0,
                  0.0,         0.0,         1.0,      0.0,
                  pTX,         pTY,         0.0,      1.0   );
}

mat4 myRotate2D(float Angle) {
    return mat4( cos(Angle), sin(Angle), 0.0, 0.0,
                -sin(Angle), cos(Angle), 0.0, 0.0,
                0.0, 0.0, 1.0, 0.0,
                0.0, 0.0, 0.0, 1.0 );
}

// define a varying variable:
varying vec2 var_Position;

void main() {

    gl_PointSize = 10.0;

    mat4 projectionMatrix = myOrtho2D(0.0, u_Width, 0.0, u_Height);

    mat4 modelViewMatrix = myTranslate2D(u_TX, u_TY);


    // whenever my rotation matrix is in the main method nothing is drawn to the screen
   // mat4 rotMat = myTranslate2D(u_FixedX, u_FixedY) * myRotate2D(u_Angle) * myTranslate2D((-1.0)*u_FixedX, (-1.0)*u_FixedY);

    gl_Position = projectionMatrix * modelViewMatrix * a_Position;

    // the value for var_Position is set in this vertex shader,
    // then it goes through the interpolator before being
    // received (interpolated!) by a fragment shader:
    var_Position = gl_Position.xy;
}