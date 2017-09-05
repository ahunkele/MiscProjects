//
//  Shader.vsh
//  ...



uniform float u_FoV;
uniform float u_Aspect;
uniform float u_Near;
uniform float u_Far;

uniform float u_Width;
uniform float u_Height;

uniform float u_cam_TX;
uniform float u_cam_TY;

uniform float u_TX;
uniform float u_TY;
uniform float u_TZ;

uniform float u_Angle;

uniform int Theflag;
uniform int Theflag2;

attribute vec4 a_Position;
attribute vec4 a_normal;

uniform float u_Animation;

//attribute vec4 a_Color;


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

mat4 scale3D(float pTX, float pTY, float pTZ){
    return mat4(pTX,        0.0,           0.0,          0.0,
                0.0,        pTY,           0.0,          0.0,
                0.0,        0.0,           pTZ,          0.0,
                0.0,        0.0,           0.0,          1.0);
}

// 3D translation matrix:
mat4 myTranslate(float pTX, float pTY, float pTZ) {
    // the returned matrix is defined "transposed", i.e. the last row
    //   is really the last column as used in matrix multiplication:
    return mat4(  1.0,         0.0,         0.0,      0.0,
                0.0,         1.0,         0.0,      0.0,
                0.0,         0.0,         1.0,      0.0,
                pTX,         pTY,         pTZ,      1.0   );
}

mat4 myRotate3DX(float pAngle) {
    return mat4( 1.0, 0.0, 0.0, 0.0,
                 0.0, cos(pAngle), sin(pAngle), 0.0,
                 0.0, -sin(pAngle), cos(pAngle), 0.0,
                 0.0, 0.0, 0.0, 1.0);
}

mat4 myRotate3DY(float pAngle) {
    return mat4( cos(pAngle), 0.0, -sin(pAngle), 0.0,
                 0.0, 1.0, 0.0, 0.0,
                 -sin(pAngle), 0.0, cos(pAngle), 0.0,
                 0.0, 0.0, 0.0, 1.0);
}





// 3D perspective transformation matrix:
mat4 myGLUPerspective(in float pFoV, in float pAspect, in float pNear, in float pFar)  {

    mat4 PerspectiveMatrix = mat4(0.0);

    float lAngle = (pFoV / 180.0) * 3.14159;
    float lFocus = 1.0 / tan ( lAngle * 0.5 );


    PerspectiveMatrix[0][0] = lFocus / pAspect;
    PerspectiveMatrix[1][1] = lFocus;
    PerspectiveMatrix[2][2] = (pFar + pNear) / (pNear - pFar);
    PerspectiveMatrix[2][3] = -1.0;
    PerspectiveMatrix[3][2] = (2.0 * pFar * pNear) / (pNear - pFar);

    return PerspectiveMatrix;
}




varying vec2 var_Position;
varying vec4 var_Color;

void main() {

        //v_color = a_color;
        vec3 lightPos = vec3(0.0, 5.0, 0.0);
        vec3 rotateAxis = vec3(0.0, 0.0, 0.0);
        mat4 projectionMatrix = myGLUPerspective(u_FoV, u_Aspect, u_Near, u_Far);
        mat4 viewMatrix =  myTranslate(u_cam_TX, 0.0, 0.0);
        mat4 modelMatrix = myTranslate( u_TX, u_TY, -15.0) * scale3D(1.3, 1.3, 1.3) * myRotate3DY(u_Animation) ;


        float df = max(0.0, dot(a_normal.xyz, normalize(lightPos)));


        gl_PointSize = 10.0;
        gl_Position = projectionMatrix * viewMatrix *modelMatrix * a_Position;

        var_Position = gl_Position.xy;

        var_Color = df *  vec4(0.5, 0.5, 0.5, 1.0);


}