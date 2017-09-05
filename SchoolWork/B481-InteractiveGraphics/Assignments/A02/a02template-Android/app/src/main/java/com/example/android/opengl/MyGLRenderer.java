package com.example.android.opengl;

import javax.microedition.khronos.opengles.GL10;
// GL10 is only needed for Android GLSurfaceView.Renderer callbacks, e.g. onSurfaceCreated() etc.

import javax.microedition.khronos.egl.EGLConfig;

import android.opengl.GLSurfaceView;
import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.util.Log;

/**
 * MyGLRenderer provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private float[] gVertexData = {
        // vertex data structured thus:
        //  positionX, positionY, etc.

        // we're not really using these starting values in the assignment 02 template,
        //  since we're setting all vertex data from touch events.
            10.0f,  10.0f,
            100.0f, 200.0f,
            50.0f,  30.0f,
            300.0f, 200.0f,
            10.0f,  10.0f,
            100.0f, 200.0f,
            50.0f,  30.0f,
            300.0f, 200.0f,
            10.0f,  10.0f,
            100.0f, 200.0f,
            10.0f,  10.0f,
            100.0f, 200.0f,
            50.0f,  30.0f,
            300.0f, 200.0f,
            10.0f,  10.0f,
            100.0f, 200.0f,
            50.0f,  30.0f,
            300.0f, 200.0f,
            10.0f,  10.0f,
            100.0f, 200.0f
    };

    private float[][] gColorData = {
            // color data in RGBA float format
            {0.0f, 1.0f, 0.0f, 1.0f},
            {0.0f, 0.0f, 1.0f, 1.0f},
            {1.0f, 0.0f, 0.0f, 1.0f}
    };


    private FloatBuffer myVertexBuffer;

    private static final String TAG = "MyGLRenderer";

    private int myGLESProgram = -1;

    private int myViewPortWidth = -1;
    private int myViewPortHeight = -1;

    private int myWidthUniform = -1;
    private int myHeightUniform = -1;
    private int myColorUniform = -1;
    private int myVertexAttribute = -1;

    private float myTouchXbegin = -1.0f;
    private float myTouchYbegin = -1.0f;
    private float myTouchXcurrent = -1.0f;
    private float myTouchYcurrent = -1.0f;
    private float myTouchXold = -1.0f;
    private float myTouchYold = -1.0f;
    private int myTouchPhase = -1;

    private int touchesX = 0;
    private int touchesY = 1;

    private int touches = 0;


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
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);

        // allocate a byte buffer, for as many floting point numbers
        //   as there are individual coordinates (one for each x, y, etc.),
        //   multiplied times 4, since there are 4 bytes for each float:
        lByteBuffer = ByteBuffer.allocateDirect(gVertexData.length * 4);

        // set the Java byte buffer for all vertices:
        lByteBuffer.order(ByteOrder.nativeOrder());
        this.myVertexBuffer = lByteBuffer.asFloatBuffer();
        this.myVertexBuffer.put(gVertexData);
        this.myVertexBuffer.position(0);

        this.myVertexAttribute = GLES20.glGetAttribLocation(this.myGLESProgram, "a_Position");

        GLES20.glVertexAttribPointer(
                myVertexAttribute, 2,
                GLES20.GL_FLOAT, false,
                0, myVertexBuffer);

        // get location of uniform variables in the shaders:
        this.myWidthUniform = GLES20.glGetUniformLocation(this.myGLESProgram, "u_Width");
        this.myHeightUniform = GLES20.glGetUniformLocation(this.myGLESProgram, "u_Height");
        this.myColorUniform = GLES20.glGetUniformLocation(this.myGLESProgram, "u_Color");

        // glViewport(x, y, width, height)
        GLES20.glViewport ( 0, 0, this.myViewPortWidth, this.myViewPortHeight );

        // Set the background color:
        GLES20.glClearColor( 0.0f, 0.0f, 0.0f, 1.0f );


    } // end of setupGL()


    // ------------------------------------------------------------------------
    private void draw() {
        GLES20.glUseProgram(myGLESProgram);

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // pass current viewport dimensions to the vertex shader:
        GLES20.glUniform1f(this.myWidthUniform, this.myViewPortWidth);
        GLES20.glUniform1f(this.myHeightUniform, this.myViewPortHeight);




        this.myVertexBuffer.put(gVertexData);
        this.myVertexBuffer.position(0);



        // update the byte buffer for vertices:


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
                myVertexAttribute, 2,
                GLES20.GL_FLOAT, false,
                0, myVertexBuffer);

        System.out.println("Drawing the vertex buffer [" + myVertexBuffer + "]");

        GLES20.glLineWidth(1.0f);

        // what color to use for the first line:
        GLES20.glUniform4f(this.myColorUniform,
                gColorData[0][0],
                gColorData[0][1],
                gColorData[0][2],
                gColorData[0][3]);

        // draw the first line in the array:
        // these are the parameters:
        //   glDrawArrays(mode, first, count)

        if(touchesX/2 >= 2) {
            GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, touchesX/2);


            // what color to use for the second line:
            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[1][0],
                    gColorData[1][1],
                    gColorData[1][2],
                    gColorData[1][3]);
        }
            // draw the second line in the array:
            //GLES20.glDrawArrays( GLES20.GL_LINES_, 2, 2 );

        if(touchesX/2 >= 1){
            // what color to use for the highlight point:
            // what color to use for the second line:
            GLES20.glUniform4f(this.myColorUniform,
                    gColorData[2][0],
                    gColorData[2][1],
                    gColorData[2][2],
                    gColorData[2][3]);

            GLES20.glDrawArrays(GLES20.GL_POINTS, 0, touchesX/2);
        }
    } // end of draw()
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

        touches++;
        // the position where the finger begins touching the screen:
        this.myTouchXbegin = (float)(x);
        this.myTouchYbegin = this.myViewPortHeight - (float)(y) - 1.0f;

        // the position where the finger currently touches the screen:
        this.myTouchXcurrent = this.myTouchXbegin;
        this.myTouchYcurrent = this.myTouchYbegin;


        //TODO
        if(pixelProximity(myTouchXcurrent, myTouchYcurrent)){

        }
        else {

            gVertexData[touchesX] = this.myTouchXcurrent;
            gVertexData[touchesX + 1] = this.myTouchYcurrent;

            if (touchesX == 20 || touchesY == 19) {
                touchesX = 20;
            } else {
                touchesX += 2;
            }
        }


        // the last known position of the finger touching the screen:
        //   at redraw or at new (first) touch event:
        this.myTouchXold = this.myTouchXcurrent;
        this.myTouchYold = this.myTouchYcurrent;

        // we are in the "we've just began" phase of the touch event sequence:
        this.myTouchPhase = 1;

        System.out.println(lMessage);


    }

    public void touchesMoved(float x, float y) {
        String lMessage = "Touch Moved to " + x + " , " + y;

        // store "current" to "old" touch coordinates
        this.myTouchXold = this.myTouchXcurrent;
        this.myTouchYold = this.myTouchYcurrent;


        // get new "current" touch coordinates
        this.myTouchXcurrent = (float)(x);
        this.myTouchYcurrent = this.myViewPortHeight - (float)(y) - 1.0f;

        gVertexData[touchesX -2] = this.myTouchXcurrent;
        gVertexData[touchesX -1] = this.myTouchYcurrent;

        // we are in the "something has moved" phase of the touch event sequence:
        this.myTouchPhase = 2;

        System.out.println(lMessage);

    }

    public void touchesEnded(float x, float y) {
        String lMessage = "Touch Ended at " + x + " , " + y;

        touches++;
        // store "current" to "old" touch coordinates
        this.myTouchXold = this.myTouchXcurrent;
        this.myTouchYold = this.myTouchYcurrent;


        // get new "current" touch coordinates
        this.myTouchXcurrent = (float)(x);
        this.myTouchYcurrent = this.myViewPortHeight - (float)(y) - 1.0f;

        gVertexData[touchesX] = this.myTouchXcurrent;
        gVertexData[touchesX + 1] = this.myTouchYcurrent;

        // we are in the "something has moved" phase of the touch event sequence:
        this.myTouchPhase = 3;

        System.out.println(lMessage);
    }

    public double euclidDist(float x1, float y1, float x2, float y2){
        float deltaX = Math.abs(x1 - x2);
        float deltaY = Math.abs(y1 - y2);
        return (double) Math.sqrt(deltaX*deltaX + deltaY*deltaY);
    }

    
    public boolean pixelProximity(float x, float y){
        for(int i = 0; i < 21; i+= 2) {
            if (euclidDist(x, y, gVertexData[i], gVertexData[i++]) <= 3){
                System.out.println("CLOSE CLOSE CLOSE CLOSE CLOSE CLOSE ");
                return true;
            }
            else{
                return false;
            }
        }
            return false;
    }
}