package com.example.amigo;



import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.*;
import android.graphics.*;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;




public class RTexturedCubeRenderer implements Renderer {

	private Context context;

	private LMultiTexturedCube cube;

	/* The initial light values */
	private float[] lightAmbient = {0.5f, 0.5f, 0.5f, 1.0f};
	private float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
	private float[] lightPosition = {0.0f, 0.0f, 2.0f, 1.0f};
		
	/* The buffers for our light values */
	private FloatBuffer lightAmbientBuffer;
	private FloatBuffer lightDiffuseBuffer;
	private FloatBuffer lightPositionBuffer;
	
	/** Is light enabled */
	private boolean light = false;
	
	public RTexturedCubeRenderer(Context context) {

		this.context = context;
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(lightAmbient.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightAmbientBuffer = byteBuf.asFloatBuffer();
		lightAmbientBuffer.put(lightAmbient);
		lightAmbientBuffer.position(0);
		
		byteBuf = ByteBuffer.allocateDirect(lightDiffuse.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightDiffuseBuffer = byteBuf.asFloatBuffer();
		lightDiffuseBuffer.put(lightDiffuse);
		lightDiffuseBuffer.position(0);
		
		byteBuf = ByteBuffer.allocateDirect(lightPosition.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightPositionBuffer = byteBuf.asFloatBuffer();
		lightPositionBuffer.put(lightPosition);
		lightPositionBuffer.position(0);
		cube = new LMultiTexturedCube();

	}




	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		// Set the background color to black ( rgba ).

		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);

		
		//And there'll be light!
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbientBuffer);		//Setup The Ambient Light
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuseBuffer);		//Setup The Diffuse Light
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPositionBuffer);	//Position The Light
		gl.glEnable(GL10.GL_LIGHT0);											//Enable Light 0
		
	
		//Settings
		gl.glDisable(GL10.GL_DITHER);				//Disable dithering
		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping
		gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
		gl.glClearDepthf(1.0f); 					//Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); 			//The Type Of Depth Testing To Do
		
		//Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); 
				
		//Load the texture for the cube once during Surface creation
		cube.loadGLTexture(gl, this.context);

	}

	float angle;

	public void onDrawFrame(GL10 gl) {

		// Clears the screen and depth buffer.

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		// Replace the current matrix with the identity matrix

		gl.glLoadIdentity();

		// Translates 4 units into the screen.

		gl.glTranslatef(0, 0, -5);  //���簢���� ��ġ(x,y) x = �¿�, y= ����, z = ������ü ũ��

		//gl.glRotatef(20, 1, 0, 0); // ī�޶� ���� �ణ ��￩�� ������ ���̵��� �Ѵ�

		gl.glRotatef(150, BluetoothChat.Addre[0],BluetoothChat.Addre[1],BluetoothChat.Addre[2]);
		
		// gl.glRotatef(angle, 0, 1, 0); // ���������� ȸ��
		// gl.glRotatef(angle, 0, -1, 0); // �������� ȸ��
		// gl.glRotatef(angle, 1, 0, 0 ); // �Ʒ��� ȸ��
		// gl.glRotatef(angle, -1, 0, 0 ); // ���� ȸ��
		// gl.glRotatef(angle, 0, 0, -1 ); // �ð���� ȸ��
		// gl.glRotatef(angle, 0, 0, -1 ); // �ð� �ݴ���� ȸ��


		// Draw our scene.

		cube.draw(gl);

		angle += 1; //ȸ���ϴ� �ӵ�

	}




	public void onSurfaceChanged(GL10 gl, int width, int height) {

		// Sets the current view port to the new size.

		gl.glViewport(0, 0, width, height);

		// Select the projection matrix

		gl.glMatrixMode(GL10.GL_PROJECTION);

		// Reset the projection matrix

		gl.glLoadIdentity();

		// Calculate the aspect ratio of the window

		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,	1000.0f);

		// Select the modelview matrix

		gl.glMatrixMode(GL10.GL_MODELVIEW);

		// Reset the modelview matrix

		gl.glLoadIdentity();

	} 

}



