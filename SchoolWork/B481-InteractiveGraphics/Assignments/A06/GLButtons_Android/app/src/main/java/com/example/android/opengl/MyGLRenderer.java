package com.example.android.opengl;

import javax.microedition.khronos.opengles.GL10;
// GL10 is only needed for Android GLSurfaceView.Renderer callbacks, e.g. onSurfaceCreated() etc.

import javax.microedition.khronos.egl.EGLConfig;

import android.opengl.GLES10;
import android.opengl.GLSurfaceView;
import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Pipe;
import java.util.Timer;
import java.util.TimerTask;

import android.opengl.Matrix;
import android.util.Log;

import static javax.microedition.khronos.opengles.GL10.GL_LIGHT0;
import static javax.microedition.khronos.opengles.GL10.GL_LIGHTING;
import static javax.microedition.khronos.opengles.GL10.GL_MODELVIEW;

/**
 * MyGLRenderer provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */


/** Magenta
GLES20.glUniform4f(this.myColorUniform,
        gColorData[3][0],
        gColorData[2][1],
        gColorData[3][2],
        gColorData[3][3]);

 CYAN
 GLES20.glUniform4f(this.myColorUniform,
 gColorData[1][0],
 gColorData[0][1],
 gColorData[1][2],
 gColorData[0][3]);


 YELLOW
 GLES20.glUniform4f(this.myColorUniform,
 gColorData[3][0],
 gColorData[3][1],
 gColorData[0][2],
 gColorData[3][3]);

 **/


public class MyGLRenderer implements GLSurfaceView.Renderer {

    //Solution
    public int num_verts = 0, high_vert = 0, num_high_verts = 0;
    public int mode = 1;    // mode 0 for proximity, mode 1 for insert
    private int ROTATE = 0, TXY = 1, TXZ = 2, SCALE = 3;
    private int OBJ1 = 0, OBJ2 = 1, OBJ3 = 2, CAMERA = 3;
    private int topSelection = ROTATE, bottomSelection = OBJ1;
    //End
    private int color = 0;
    private int sourceColor1 = 3;
    private int sourceColor2 = 1;

    private Timer timer = new Timer();

    private int filterColor;
    // cube data
    public float[] cubeData = {
            // Front
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,

            // Right
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,

            // Back
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,

            // Left
            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,

            // Top
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,

            // Bottom
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
    };


    public float[] vertices = {

            //Y axis
            0, 0, 0,
            0, 10, 0,


            //Y ticks
            0, 1, 0,
            1, 1, 0,

            0, 1, 0,
            0, 1, 1,

            0, 2, 0,
            1, 2, 0,

            0, 2, 0,
            0, 2, 1,

            0, 3, 0,
            1, 3, 0,

            0, 3, 0,
            0, 3, 1,

            0, 4, 0,
            1, 4, 0,

            0, 4, 0,
            0, 4, 1,

            0, 5, 0,
            1, 5, 0,

            0, 5, 0,
            0, 5, 1,

            0, 6, 0,
            1, 6, 0,

            0, 6, 0,
            0, 6, 1,

            0, 7, 0,
            1, 7, 0,

            0, 7, 0,
            0, 7, 1,

            0, 8, 0,
            1, 8, 0,

            0, 8, 0,
            0, 8, 1,

            0, 9, 0,
            1, 9, 0,

            0, 9, 0,
            0, 9, 1,

            0, 10, 0,
            1, 10, 0,

            0, 10, 0,
            0, 10, 1,




            //X axis
            0, 0, 0,
            10,0, 0,


            //X Ticks
            1, 0, 0,
            1, 1, 0,

            1, 0, 0,
            1, 0, 1,

            2, 0, 0,
            2, 1, 0,

            2, 0, 0,
            2, 0, 1,

            3, 0, 0,
            3, 1, 0,

            3, 0, 0,
            3, 0, 1,

            4, 0, 0,
            4, 1, 0,

            4, 0, 0,
            4, 0, 1,

            5, 0, 0,
            5, 1, 0,

            5, 0, 0,
            5, 0, 1,

            6, 0, 0,
            6, 1, 0,

            6, 0, 0,
            6, 0, 1,

            7, 0, 0,
            7, 1, 0,

            7, 0, 0,
            7, 0, 1,

            8, 0, 0,
            8, 1, 0,

            8, 0, 0,
            8, 0, 1,

            9, 0, 0,
            9, 1, 0,

            9, 0, 0,
            9, 0, 1,

            10, 0, 0,
            10, 1, 0,

            10, 0, 0,
            10, 0, 1,


            //Z axis
            0, 0, 0,
            0, 0, 10,

            //Z Tics

            0, 0, 1,
            1, 0, 1,

            0, 0, 1,
            0, 1, 1,

            0, 0, 2,
            0, 1, 2,

            0, 0, 2,
            1, 0, 2,

            0, 0, 3,
            0, 1, 3,

            0, 0, 3,
            1, 0, 3,

            0, 0, 4,
            1, 0, 4,

            0, 0, 4,
            0, 1, 4,

            0, 0, 5,
            0, 1, 5,

            0, 0, 5,
            1, 0, 5,

            0, 0, 6,
            0, 1, 6,

            0, 0, 6,
            1, 0, 6,

            0, 0, 7,
            1, 0, 7,

            0, 0, 7,
            0, 1, 7,

            0, 0, 8,
            0, 1, 8,

            0, 0, 8,
            1, 0, 8,

            0, 0, 9,
            0, 1, 9,

            0, 0, 9,
            1, 0, 9,

            0, 0, 10,
            0, 1, 10,

            0, 0, 10,
            1, 0, 10




    };
    // cube 2 data
    public float[] cubeData2 = {
            // Front
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,

            // Right
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,

            // Back
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,

            // Left
            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,

            // Top
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,

            // Bottom
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
    };

    // cube 3 data
    public float[] cubeData3 = {
            // Front
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,

            // Right
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,

            // Back
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,

            // Left
            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,

            // Top
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,

            // Bottom
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
    };



    public float[] normals= {
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,

            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,

            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,

            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,

            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,


    };

    private float[][] gColorData = {
            // color data in RGBA float format
            {0.0f, 1.0f, 0.0f, 1.0f},
            {0.0f, 0.0f, 1.0f, 1.0f},
            {1.0f, 0.0f, 0.0f, 1.0f},
            {1.0f, 1.0f, 1.0f, 1.0f}
    };


    private FloatBuffer myVertexBuffer;
    private FloatBuffer myVertexBuffer2;


    private static final String TAG = "MyGLRenderer";

    private int myGLESProgram = -1;

    private int myViewPortWidth = -1;
    private int myViewPortHeight = -1;

    private int myWidthUniform = -1;
    private int myHeightUniform = -1;
    private int myColorUniform = -1;
    private int myVertexAttribute = -1;
    private int myVertexAttribute2 = -1;
    private int myCamTXUniform = -1;
    private int myCamTYUniform = -1;
    private int myTXUniform = -1;
    private int myTYUniform = -1;
    private int myTZUniform = -1;

    private int myAnimationUniform = -1;

    public float myFov = 95.0f;
    public float myAspect = 1.0f;

    public float myTouchXbegin = -1.0f;
    public float myTouchYbegin = -1.0f;
    public float myTouchZbegin = -1.0f;
    public float myTouchZcurrent = -1.0f;
    public float myTouchXcurrent = -1.0f;
    public float myTouchYcurrent = -1.0f;
    public float myTouchXold = -1.0f;
    public float myTouchYold = -1.0f;
    public float myTouchZold = -1.0f;
    public int myTouchPhase = -1;

    public float myTX = 0.0f;
    public float myTY = 0.0f;
    public float myTZ = 0.0f;

    public int myFoVUniform = -1;
    public int myAspectUniform = 1;
    public int myNearUniform = -1;
    public int myFarUniform = -1;
    public int myAngleUniform = -1;

    public int flag = -1;
    public int flag2 = -1;

    public int index = 0;
    public int index2 = 0;

    public boolean loop = false;

    public float angles = 0.0f;
    public float AnimationAngles = 0.0f;


    // public Line mLine;
    public String vertexShaderCode = "", fragmentShaderCode = "";


    // ------------------------------------------------------------------------
    // initialize OpenGL ES:
    // ------------------------------------------------------------------------
    private void setupGL() {

        // a Java byte buffer where to store the Vertex Buffer:
        ByteBuffer lByteBuffer;

        System.out.println("setupGL() ---------------------------------- ");
        String lGL_VERSION = GLES20.glGetString(GLES20.GL_VERSION);
        System.out.println("setupGL() - glGetString(GL_VERSION) returned " + lGL_VERSION);
        String lGL_SHADING_LANGUAGE_VERSION = GLES20.glGetString(GLES20.GL_SHADING_LANGUAGE_VERSION);
        System.out.println("setupGL() - glGetString(GL_SHADING_LANGUAGE_VERSION) returned " + lGL_SHADING_LANGUAGE_VERSION);
        System.out.println("setupGL() ---------------------------------- ");


        // get shaders ready -- load, compile, link GLSL code into GPU program:
        if (!this.loadShaders()) {
            System.err.println("setupGL() hasn't been successful in creating OpenGL shaders");
        }

        // in 2D, we don't need depth/Z-buffer:
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        // allocate a byte buffer, for as many floting point numbers
        //   as there are individual coordinates (one for each x, y, etc.),
        //   multiplied times 4, since there are 4 bytes for each float:
        lByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);

        // set the Java byte buffer for all vertices:
        lByteBuffer.order(ByteOrder.nativeOrder());
        this.myVertexBuffer = lByteBuffer.asFloatBuffer();
        this.myVertexBuffer.put(vertices);
        this.myVertexBuffer.position(0);

        this.myVertexAttribute = GLES20.glGetAttribLocation(this.myGLESProgram, "a_Position");
        this.myVertexAttribute2 = GLES20.glGetAttribLocation(this.myGLESProgram, "a_normal");

        GLES20.glVertexAttribPointer(
                myVertexAttribute, 3,
                GLES20.GL_FLOAT, false,
                0, myVertexBuffer);

        this.myVertexBuffer2 = lByteBuffer.asFloatBuffer();
        this.myVertexBuffer2.put(normals);
        this.myVertexBuffer2.position(0);

        GLES20.glVertexAttribPointer(
                myVertexAttribute2, 3,
                GLES20.GL_FLOAT, false,
                0, myVertexBuffer);

        // get location of uniform variables in the shaders:
        this.myWidthUniform = GLES20.glGetUniformLocation(this.myGLESProgram, "u_Width");
        this.myHeightUniform = GLES20.glGetUniformLocation(this.myGLESProgram, "u_Height");
        this.myColorUniform = GLES20.glGetUniformLocation(this.myGLESProgram, "u_Color");
        // get location of uniform variables in the shaders which we need to track changes in X and Y coordinates based on touch input for centroid proximity detection.
        this.myCamTXUniform = GLES20.glGetUniformLocation(this.myGLESProgram, "u_cam_TX");
        this.myCamTYUniform = GLES20.glGetUniformLocation(this.myGLESProgram, "u_cam_TY");
        this.myTXUniform = GLES20.glGetUniformLocation(this.myGLESProgram, "u_TX");
        this.myTYUniform = GLES20.glGetUniformLocation(this.myGLESProgram, "u_TY");
        this.myTZUniform = GLES20.glGetUniformLocation(this.myGLESProgram, "u_TZ");
        // lab12
        this.myFoVUniform = GLES20.glGetUniformLocation(this.myGLESProgram, "u_FoV");
        this.myAspectUniform = GLES20.glGetUniformLocation(this.myGLESProgram, "u_Aspect");
        this.myNearUniform = GLES20.glGetUniformLocation(this.myGLESProgram, "u_Near");
        this.myFarUniform = GLES20.glGetUniformLocation(this.myGLESProgram, "u_Far");

        this.myAngleUniform = GLES20.glGetUniformLocation(this.myGLESProgram, "u_Angle");

        this.myAnimationUniform = GLES20.glGetUniformLocation(this.myGLESProgram, "u_Animation");

        this.flag = GLES20.glGetUniformLocation(this.myGLESProgram, "Theflag");
        this.flag2 = GLES20.glGetUniformLocation(this.myGLESProgram, "Theflag2");

        this.myTZ = -15.0f;

        // glViewport(x, y, width, height)
        GLES20.glViewport ( 0, 0, this.myViewPortWidth, this.myViewPortHeight );

        // Set the background color:
        GLES20.glClearColor( 0.0f, 0.0f, 0.0f, 1.0f );


        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                AnimationAngles += .02;
                if(AnimationAngles > 0.5f){
                    AnimationAngles = 0.0f;
                }


            }
        }, 0, 70); // 1000 = 1 Sek.


    } // end of setupGL()



    // ------------------------------------------------------------------------
    private void draw() {
        GLES20.glUseProgram(myGLESProgram);

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // pass current viewport dimensions to the vertex shader:
        GLES20.glUniform1f(this.myWidthUniform, this.myViewPortWidth);
        GLES20.glUniform1f(this.myHeightUniform, this.myViewPortHeight);
        GLES20.glUniform1f(this.myFoVUniform, this.myFov);
        GLES20.glUniform1f(this.myAspectUniform, (float)this.myViewPortWidth/(float)this.myViewPortHeight);
        GLES20.glUniform1f(this.myNearUniform, .3f);
        GLES20.glUniform1f(this.myFarUniform, 1000.0f);

        GLES20.glUniform1i(this.flag, this.index);
        GLES20.glUniform1i(this.flag2, this.index2);



        GLES20.glUniform1f(this.myAnimationUniform, AnimationAngles);

        // update the byte buffer for vertices:
        this.myVertexBuffer.put(vertices);
        this.myVertexBuffer.position(0);

        GLES20.glUniform1f(this.myHeightUniform, this.angles);

        // enable which vertex attributes the buffer data is going to use:
        GLES20.glEnableVertexAttribArray(myVertexAttribute);
        GLES20.glEnableVertexAttribArray(myVertexAttribute2);

        // now call glVertexAttribPointer() to specify the location and data format
        //   of the array of generic vertex attributes at index,
        //   to be used at rendering time, when glDrawArrays() is going to be called.
        //
        // public func glVertexAttribPointer(indx: GLuint, _ size: GLint,
        //   _ type: GLenum, _ normalized: GLboolean,
        //   _ stride: GLsizei, _ ptr: UnsafePointer<Void>)
        // see https://www.khronos.org/opengles/sdk/docs/man/xhtml/glVertexAttribPointer.xml

        GLES20.glVertexAttribPointer(
                myVertexAttribute, 3,
                GLES20.GL_FLOAT, false,
                0, myVertexBuffer);

        GLES20.glLineWidth(1.0f);

        GLES20.glUniform1f(this.myTXUniform, 0.0f);
        GLES20.glUniform1f(this.myTYUniform, 0.0f);


        GLES20.glUniform1f(this.myCamTXUniform, (-0.02f)*this.myTX);
        GLES20.glUniform1f(this.myCamTYUniform, (0.01f)*this.myTY);

        for (int i=0; i<3; i++) {

            // pushing TX and TY to shader
            if (i == 0)
                GLES20.glUniform1f(this.myTXUniform, -5);
            else if (i == 1)
                GLES20.glUniform1f(this.myTXUniform, 5);
            else if (i == 2) {
                GLES20.glUniform1f(this.myTXUniform, 0);

            }

            GLES20.glUniform1f(this.myTYUniform, this.myTY);
            GLES20.glUniform1f(this.myTZUniform, this.myTZ);


            // draw cube face 1

            // update the byte buffer for vertices:
            this.myVertexBuffer.put(cubeData);
            this.myVertexBuffer.position(0);

            // enable which vertex attributes the buffer data is going to use:
            GLES20.glEnableVertexAttribArray(myVertexAttribute);


            // now call glVertexAttribPointer() to specify the location and data format
            //   of the array of generic vertex attributes at index,
            //   to be used at rendering time, when glDrawArrays() is going to be called.
            //
            // public func glVertexAttribPointer(indx: GLuint, _ size: GLint,
            //   _ type: GLenum, _ normalized: GLboolean,
            //   _ stride: GLsizei, _ ptr: UnsafePointer<Void>)
            // see https://www.khronos.org/opengles/sdk/docs/man/xhtml/glVertexAttribPointer.xml

            GLES20.glVertexAttribPointer(
                    myVertexAttribute, 3,
                    GLES20.GL_FLOAT, false,
                    0, myVertexBuffer);

            GLES20.glLineWidth(1.0f);


            if (index2 == 0) {
                sourceColor2 = 1;
                if (i == 2) {
                    BlockColor(color);
                } else if (i == 1) {
                    BlockColor(0);
                } else {
                    BlockColor(sourceColor2);
                }

            } else if (index2 == 1) {
                sourceColor2 = 3;
                if (i == 2) {
                    BlockColor(color);
                } else if (i == 1) {
                    BlockColor(0);
                } else {
                    BlockColor(sourceColor2);
                }

            } else if (index2 == 2) {
                sourceColor2 = 2;
                if (i == 2) {
                    BlockColor(color);
                } else if (i == 1) {
                    BlockColor(0);
                } else {
                    BlockColor(sourceColor2);
                }

            }
            if(index2 == 3){
                if(i == 0){
                    BlockColor(sourceColor2);
                }
            }
            if(index == 3){
                if(i == 0){
                    BlockColor(sourceColor1);
                }else if( i == 1){
                    CalcFilterBlockColor(color, sourceColor1,sourceColor2);
                }
                else if(i == 2){
                    BlockColor(color);
                }
            }


            if (index == 0) {

                if (i == 2) {
                    BlockColor(color);
                } else if (i == 1) {
                    BlockColor(0);
                } else {
                    sourceColor1 = 3;
                    BlockColor(sourceColor1);
                }

            } else if (index == 1) {

                if (i == 2) {
                    BlockColor(color);
                } else if (i == 1) {
                    BlockColor(0);
                } else {
                    sourceColor1 = 1;
                    BlockColor(sourceColor1);
                }
            } else if (index == 2) {

                    if (i == 2) {
                        BlockColor(color);
                    } else if (i == 1) {
                        BlockColor(0);
                    } else {
                        sourceColor1 = 2;
                        BlockColor(sourceColor1);
                    }
            }
        }


        GLES20.glDisable(GLES20.GL_CULL_FACE);

        // No depth testing
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);

        // Enable blending
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE);

    }
    // end of draw()
    // ------------------------------------------------------------------------



    // ------------------------------------------------------------------------
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // called when the surface is created or recreated

        // this method is a convenient place to put code to create EGL resources
        // that need to be created when the rendering starts,
        // and that need to be recreated when the EGL context is lost.

        this.setupGL();
    }


    // ------------------------------------------------------------------------
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // called after the surface is created and whenever the OpenGL ES surface size changes

        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        this.myViewPortHeight = height;
        this.myViewPortWidth = width;

    }


    // ------------------------------------------------------------------------
    @Override
    public void onDrawFrame(GL10 unused) {

        // called to draw the current frame
        this.draw();
    }


    // ------------------------------------------------------------------------
    private boolean loadShaders() {
        this.myGLESProgram = GLES20.glCreateProgram();

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, this.vertexShaderCode);

        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, this.fragmentShaderCode);

        GLES20.glAttachShader(myGLESProgram, vertexShader);
        GLES20.glAttachShader(myGLESProgram, fragmentShader);
        GLES20.glLinkProgram(myGLESProgram);

        return true;

    } // end of loadShaders()


    // ------------------------------------------------------------------------
    /**
     * Utility method for compiling a OpenGL shader.
     *
     * <p><strong>Note:</strong> When developing shaders, use the checkGlError()
     * method to debug shader coding errors.</p>
     *
     * @param type - Vertex or fragment shader type.
     * @param shaderCode - String containing the shader code.
     * @return - Returns an id for the shader.
     */
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     *
     * <pre>
     * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }


    public void touchesBegan(float x, float y) {
        String lMessage = "Touch Began at " + x + " , " + y;

        // the position where the finger begins touching the screen:
        this.myTouchXbegin = (float)(x);
        this.myTouchYbegin = this.myViewPortHeight - (float)(y) - 1.0f;

        // the position where the finger currently touches the screen:
        this.myTouchXcurrent = this.myTouchXbegin;
        this.myTouchYcurrent = this.myTouchYbegin;

        // the last known position of the finger touching the screen:
        //   at redraw or at new (first) touch event:
        this.myTouchXold = this.myTouchXcurrent;
        this.myTouchYold = this.myTouchYcurrent;


        // we are in the "we've just began" phase of the touch event sequence:
        this.myTouchPhase = 1;

        //Solution
        testForProx(x, y);
        if (mode == 1 && num_verts<10) {
            //vertices[num_verts*2] = x;
            //vertices[num_verts*2 + 1] = myViewPortHeight - (float)(y) - 1.0f;
            //num_verts++;
        }

        System.out.println(lMessage);
    }

    public void CalcFilterBlockColor(int color, int sourceColor, int sourceColorTwo){
        if(sourceColor == 1 & sourceColorTwo == 1) {
            if (color == 0) {
                faceColor(1);
            } else if (color == 1){
                faceColor(1);
            }

            else if( color == 2){
                faceColor(-1);
            }

            else if( color == 3){
                faceColor(-1);
            }
        }

        else if(sourceColor == 2 & sourceColorTwo == 2) {
            if (color == 0) {
                faceColor(2);
            } else if (color == 1){
                faceColor(-1);
            }

            else if( color == 2){
                faceColor(2);
            }

            else if( color == 3){
                faceColor(-1);
            }
        }

        else if(sourceColor == 3 & sourceColorTwo == 3) {
            if (color == 0) {
                faceColor(3);
            } else if (color == 1){
                faceColor(-1);
            }

            else if( color == 2){
                faceColor(-1);
            }

            else if( color == 3){
                faceColor(3);
            }
        }

        //Top Green, bottom red or vice versa
        else if(sourceColor == 3 & sourceColorTwo == 1 || sourceColor == 1 & sourceColorTwo == 3 ) {
            if (color == 0) {
                faceColor(4);
            } else if (color == 1){
                faceColor(1);
            }
            else if( color == 2){
                faceColor(-1);
            }

            else if( color == 3){
                faceColor(3);
            }
        }

        else if(sourceColor == 2 & sourceColorTwo == 1 || sourceColor == 1 & sourceColorTwo == 2 ) {
            if (color == 0) {
                faceColor(6);
            } else if (color == 1){
                faceColor(1);
            }
            else if( color == 2){
                faceColor(2);
            }

            else if( color == 3){
                faceColor(-1);
            }
        }

        else if(sourceColor == 2 & sourceColorTwo == 3 || sourceColor == 3 & sourceColorTwo == 2 ) {
            if (color == 0) {
                faceColor(5);
            } else if (color == 1){
                faceColor(-1);
            }
            else if( color == 2){
                faceColor(2);
            }

            else if( color == 3){
                faceColor(3);
            }
        }


    }

    public void faceColor(int color){
        // 0 = White, 1 = red, 2 = blue, 3 = Green, 4 = YELLOW, 5= CYAN, 6 = Magenta
        if(color == 0) {
            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[3][2],
                    gColorData[3][3]);

            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 18);

            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[3][2],
                    gColorData[3][3]);

            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[3][2],
                    gColorData[3][3]);

            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 18, 18);
        }else if(color == 1){
            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[3][2],
                    gColorData[3][3]);

            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 18);

            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[3][2],
                    gColorData[3][3]);

            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[2][0],
                    gColorData[2][1],
                    gColorData[2][2],
                    gColorData[2][3]);

            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 18, 18);
        }else if(color == 2){
            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[3][2],
                    gColorData[3][3]);

            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 18);

            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[3][2],
                    gColorData[3][3]);

            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[1][0],
                    gColorData[1][1],
                    gColorData[1][2],
                    gColorData[1][3]);

            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 18, 18);
        }else if(color == 3){
            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[3][2],
                    gColorData[3][3]);

            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 18);

            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[3][2],
                    gColorData[3][3]);

            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[1][0],
                    gColorData[0][1],
                    gColorData[0][2],
                    gColorData[0][3]);


            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 18, 18);
        }
        else if( color == 4){
            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[3][2],
                    gColorData[3][3]);

            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 18);

            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[3][2],
                    gColorData[3][3]);

            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[0][2],
                    gColorData[3][3]);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 18, 18);
        }
        else if( color == 5){
            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[3][2],
                    gColorData[3][3]);

            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 18);

            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[3][2],
                    gColorData[3][3]);

            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[1][0],
                    gColorData[0][1],
                    gColorData[1][2],
                    gColorData[0][3]);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 18, 18);

        }
        else if (color == 6){
            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[3][2],
                    gColorData[3][3]);

            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 18);

            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[3][2],
                    gColorData[3][3]);

            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[2][1],
                    gColorData[3][2],
                    gColorData[3][3]);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 18, 18);
        }
        else{
            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[3][2],
                    gColorData[3][3]);

            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 18);

            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[0][0],
                    gColorData[0][0],
                    gColorData[0][0],
                    gColorData[0][0]);


            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 18, 36);

            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[0][0],
                    gColorData[0][0],
                    gColorData[0][0],
                    gColorData[0][0]);

            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 18, 18);
        }
    }

    public void BlockColor(int color){
        // 0 = White, 1 = red, 2 = blue, 3 = Green, 4 = YELLOW, 5= CYAN, 6 = Magenta
        if(color == 0) {
            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[3][2],
                    gColorData[3][3]);

            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);
        }else if(color == 1){
            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[2][0],
                    gColorData[2][1],
                    gColorData[2][2],
                    gColorData[2][3]);

            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);
        }else if(color == 2){
            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[1][0],
                    gColorData[1][1],
                    gColorData[1][2],
                    gColorData[1][3]);

            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);
        }else if(color == 3){
            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[1][0],
                    gColorData[0][1],
                    gColorData[0][2],
                    gColorData[0][3]);


            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);
        }
        else if( color == 4){
            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[3][1],
                    gColorData[0][2],
                    gColorData[3][3]);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);
        }
        else if( color == 5){

            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[1][0],
                    gColorData[0][1],
                    gColorData[1][2],
                    gColorData[0][3]);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);

        }
        else if (color == 6){
            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[3][0],
                    gColorData[2][1],
                    gColorData[3][2],
                    gColorData[3][3]);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);
        }
        else{
            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[0][0],
                    gColorData[0][0],
                    gColorData[0][0],
                    gColorData[0][0]);


            //Solution

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);
        }
    }

    public void touchesMoved(float x, float y) {
        String lMessage = "Touch Moved to " + x + " , " + y;

        // store "current" to "old" touch coordinates
        this.myTouchXold = this.myTouchXcurrent;
        this.myTouchYold = this.myTouchYcurrent;

        // get new "current" touch coordinates
        this.myTouchXcurrent = (float) (x);
        this.myTouchYcurrent = this.myViewPortHeight - (float) (y) - 1.0f;


        // we are in the "something has moved" phase of the touch event sequence:
        this.myTouchPhase = 2;

        System.out.println(lMessage);

        //Solution
        if (mode == 0) {
            for (int i = 0; i < num_high_verts; i++) {
                vertices[high_vert * 2 + i * 2] += myTouchXcurrent - myTouchXold;
                vertices[high_vert * 2 + i * 2 + 1] += myTouchYcurrent - myTouchYold;
            }
        }

        // a03: update tX and tY to send to shader. Also applies to this lab, I think.
        if (this.index2 == 3) {
            if (this.index == 1) {
                if (myTouchYold > myTouchYcurrent) {
                    //this.myAspect += 1.0;
                } else if (myTouchYold < myTouchYcurrent) {
                   // this.myAspect -= 1.0;
                }
            }
            else if (this.index == 3) {


            }
        } else {
            if(this.index == 0) {
                if (myTouchYold > myTouchYcurrent) {
                    this.angles -= .04;
                    myTX += (myTouchXcurrent - myTouchXold);
                } else if (myTouchYold < myTouchYcurrent) {
                    this.angles += .04;
                    myTX += (myTouchXcurrent - myTouchXold);
                }
            }
            else if(this.index == 1) {
                //myTY += (myTouchYcurrent - myTouchYold);
                myTX += (myTouchXcurrent - myTouchXold);
            } else if (this.index == 2) {
                if (myTouchYold > myTouchYcurrent) {
                    myTZ -= 1.0;
                    myTX += (myTouchXcurrent - myTouchXold);
                }
                else if (myTouchYold < myTouchYcurrent) {
                    myTZ += 1.0;
                    myTX += (myTouchXcurrent - myTouchXold);
                }
            }
            else if(this.index == 3){

                //myTY += (myTouchYcurrent - myTouchYold);
                myTX += (myTouchXcurrent - myTouchXold);
            }

        }
    }

    public void touchesEnded(float x, float y) {
        String lMessage = "Touch Ended at " + x + " , " + y;

        // store "current" to "old" touch coordinates
        this.myTouchXold = this.myTouchXcurrent;
        this.myTouchYold = this.myTouchYcurrent;

        // get new "current" touch coordinates
        this.myTouchXcurrent = (float)(x);
        this.myTouchYcurrent = this.myViewPortHeight - (float)(y) - 1.0f;

        // we are in the "something has moved" phase of the touch event sequence:
        this.myTouchPhase = 3;

        System.out.println(lMessage);

        //Solution
        mode = 1;
    }


    public void testForProx(float x0, float y0) {
        y0 = this.myViewPortHeight - (float)(y0) - 1.0f;
        // test for point proximimity
        for (int i = 0; i < num_verts; i++) {
            double x1 = vertices[i * 2];
            double y1 = vertices[i * 2 + 1];
            double dist = Math.sqrt((x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1));
            // if closer than 20 pixels, mark the vertex for highlighting
            if (dist < 20) {
                high_vert = i;
                num_high_verts = 1;
                mode = 0;  //proximity
                return;
            }
        }

        // test for line proximity
        for (int i = 0; i < (num_verts - 1); i++) {
            double x1 = vertices[i * 2];
            double y1 = vertices[i * 2 + 1];
            double x2 = vertices[i * 2 + 2];
            double y2 = vertices[i * 2 + 3];
            // calculate orthogonal unit vector n
            double nx = -(y2 - y1);
            double ny = (x2 - x1);
            double n_mag = Math.sqrt(nx * nx + ny * ny);
            nx /= n_mag;
            ny /= n_mag;
            // calculate h
            double h = Math.abs(nx * (x0 - x1) + ny * (y0 - y1));
            // if (x0,y0) is within 15 pixels of the edge
            if (h < 15) {
                // calculate unit vector v
                double vx = (x2 - x1);
                double vy = (y2 - y1);
                double v_mag = Math.sqrt(vx * vx + vy * vy);
                vx /= v_mag;
                vy /= v_mag;
                // calculate l
                double l = (vx * (x0 - x1) + vy * (y0 - y1));
                // if (x0,y0) is on the line segment, mark the edge for highlighting
                if ((l >= 0) && (l < v_mag)) {
                    high_vert = i;
                    num_high_verts = 2;
                    mode = 0;   // proximity
                    return;
                }
            }
        }
    }

    public boolean castButtonHit(int indexs){
        //Do animation creating new blocks
        // calculate filter on far left block

        if(indexs == 3){
            return true;
        }
        return false;
    }

    public boolean changeTouched(int indexs){
        if(indexs == 3){
            return true;
        }
        return false;
    }

    public void topSelectionChanged(int index) {
        System.out.println("topSelectionChanged() ---------------------------------- ");
        System.out.println("topSelectionChanged() - received " + index);
        System.out.println("topSelectionChanged() ---------------------------------- ");
        this.index = index;
    }

    public void bottomSelectionChanged(int index) {

        System.out.println("bottomSelectionChanged() ---------------------------------- ");
        System.out.println("bottomSelectionChanged() - received " + index);
        System.out.println("bottomSelectionChanged() ---------------------------------- ");
        this.index2 = index;
        if(changeTouched(index)){
            if(color==3){
                color = 0;
            }
            else{
                color++;
            }
        }
    }

}
