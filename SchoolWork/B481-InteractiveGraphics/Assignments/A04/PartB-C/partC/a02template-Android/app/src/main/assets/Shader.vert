//
//  Shader.vsh
//  ...



uniform float u_FoV;
uniform float u_Aspect;
uniform float u_Near;
uniform float u_Far;

uniform float u_Width;
uniform float u_Height;

uniform float u_TX;
uniform float u_TY;

uniform float u_Angle;

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

//  3D translation matrix:
mat4 myTranslate(float ptX, float ptY, float ptZ) {
    // the returned matrix is defined "transposed", i.e. the last row
    //   is really the last column as used in matrix multiplication:
    return mat4(  1.0,         0.0,         0.0,      0.0,
                0.0,         1.0,         0.0,      0.0,
                0.0,         0.0,         1.0,      0.0,
                ptX,         ptY,         ptZ,      1.0   );
}

// D perspective transformation matrix:
mat4 myGLUPerspective(in float pFoV, in float pAspect, in float pNear, in float pFar)  {

    mat4 PerspectiveMat = mat4(0.0);

    float lAngle = (pFoV / 180.0) * 3.14159;
    float lFocus = 1.0 / tan ( lAngle * 0.5 );


    PerspectiveMat[0][0] = lFocus / pAspect;
    PerspectiveMat[1][1] = lFocus;
    PerspectiveMat[2][2] = (pFar + pNear) / (pNear - pFar);
    PerspectiveMat[2][3] = -1.0;
    PerspectiveMat[3][2] = (2.0 * pFar * pNear) / (pNear - pFar);

    return PerspectiveMat;
}

// trying to rotate about x, y axis with z invariant not working???
mat4 myRotate3D(float pAngle) {
    return mat4( cos(pAngle), -sin(pAngle), 0.0, 0.0,
                -sin(pAngle), cos(pAngle), 0.0, 0.0,
                0.0, 0.0, 1.0, 0.0,
                0.0, 0.0, 0.0, 1.0 );
}

varying vec2 var_Position;


void main() {

    //  define a projectionMatrix using myGLUPerspective()
    //  with perspective parameters as from provided new uniform variables
    mat4  projectionMatrix = myGLUPerspective(u_FoV, u_Aspect, u_Near, u_Far);

    //mat4 rotMat = ( (myTranslate(u_TX * 0.05, u_TY * 0.05, -15.0))
    //                    * (myRotate3D(u_Angle))
    //                    * (myTranslate(((-1)*u_FixedX), ((-1)*u_FixedY))) );

 //mat4 modelViewMatrix = ( (myTranslate(u_TX * 0.05, u_TY * 0.05, -15.0)) * (rotMat) );
    mat4 modelViewMatrix = myTranslate(u_TX * 0.05, u_TY * 0.05, -15.0); // Trying to take the TX and TY from a03 task A and multiplying by a coefficient of 0.05.
    //mat4 modelViewMatrix = myTranslate(-3.0, -5.0, -15.0);

    gl_PointSize = 10.0;

    gl_Position = projectionMatrix * modelViewMatrix * a_Position;



    var_Position = gl_Position.xy;

}