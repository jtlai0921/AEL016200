package com.android;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView;

public class MyGLSurfaceView extends GLSurfaceView
							implements GLSurfaceView.Renderer {
	// �x�sOpenGL����Ψ쪺���I�y�ЩM�C��
	private FloatBuffer mVertBuf,
					 mVertColorBuf;
	
	// �x�sOpenGL���󪺳��I�bmVertBuf��������
	private ShortBuffer mIndexBuf;
	
	// OpenGL����Ψ쪺���I�y�СA�b�{�����|�N���̳]�w��mVertBuf
	// �C�@�C�O�@�ӳ��I��xyz�y��
	private float[] m3DObjVert = {
		-0.5f, -0.5f, 0.5f,
		0.5f, -0.5f, 0.5f,
		0f, -0.5f, -0.5f,
		0f, 0.5f, 0f
	};
	
	// OpenGL����Ψ쪺���I���C��A�b�{�����|�N���̳]�w��mVertColorBuf
	// �C�@�C�O�@�ӳ��I���C��A�C��Ȫ����Ǭ�rgba�Aa�Oalpha��
	private float[] m3DObjVertColor = {
			1.0f, 1.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 0.5f, 1.0f
	};
	
	// OpenGL���󪺳��I�bm3DObjVert�������ޡA�C�@�C�N��@��triangle
	// �{�����|�N���̳]�w��mIndexBuf
	private short[] m3DObjVertIndex = {
		0, 2, 1,
		3, 2, 0,
		3, 1, 2,
		1, 3, 0
	};
	
	// �̻��誺����
	private float backColorR = 1.0f,
                backColorG = 1.0f,
                backColorB = 1.0f,
                backColorA = 1.0f;

	private float mfRotaAng = 0f;

	private void setup() {
		// �إ�OpenGL�M�Ϊ�vertex buffer
		ByteBuffer vertBuf = ByteBuffer.allocateDirect(
				4 * m3DObjVert.length);
		vertBuf.order(ByteOrder.nativeOrder());
		mVertBuf = vertBuf.asFloatBuffer();
		
		// �إ�OpenGL�M�Ϊ�vertex color buffer
		ByteBuffer vertColorBuf = ByteBuffer.allocateDirect(
				4 * m3DObjVertColor.length);
		vertColorBuf.order(ByteOrder.nativeOrder());
		mVertColorBuf = vertColorBuf.asFloatBuffer();
		
		// �إ�OpenGL�M�Ϊ�index buffer
		ByteBuffer indexBuf = ByteBuffer.allocateDirect(
				2 * m3DObjVertIndex.length);
		indexBuf.order(ByteOrder.nativeOrder());
		mIndexBuf = indexBuf.asShortBuffer();

		mVertBuf.put(m3DObjVert);
		mVertColorBuf.put(m3DObjVertColor);
		mIndexBuf.put(m3DObjVertIndex);
		
		mVertBuf.position(0);
		mVertColorBuf.position(0);
		mIndexBuf.position(0);
	}

	public MyGLSurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setRenderer(this);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		
		// �M��������W�I���C��A�åB�M��z-buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT |
				   GL10.GL_DEPTH_BUFFER_BIT);
		
		// �]�w����Ψ쪺���I�y��
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertBuf);
		
		// �]�w����Ψ쪺���I���C��
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, mVertColorBuf);

		gl.glLoadIdentity();
		
		// �N����u���w���b����
		gl.glTranslatef(0f, 0f, -4f);

		// �N����u���w���b���
		gl.glRotatef(mfRotaAng, 0f, 1f, 0f);
		mfRotaAng += 1f;

		// �]�w�����I�����ި�ø�s����
		gl.glDrawElements(GL10.GL_TRIANGLES, m3DObjVertIndex.length,
						  GL10.GL_UNSIGNED_SHORT, mIndexBuf);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub

		// �]�w�z����v�Ѽ�
		// �]�w�̪�M�̻����i���d��M���k����
		// �W�U�i���d��ѥ��k�����M�ù����e����ӭp��
		final float fNEAREST = .01f,
					fFAREST = 100f,
					fVIEW_ANGLE = 45f;
		gl.glMatrixMode(GL10.GL_PROJECTION); // �������v�x�}�Ҧ�
		float fViewWidth = fNEAREST * (float) Math.tan(Math.toRadians(fVIEW_ANGLE) / 2);
		float aspectRatio = (float)width / (float)height;
		gl.glFrustumf(-fViewWidth, fViewWidth,
					  -fViewWidth / aspectRatio, fViewWidth / aspectRatio,
					  fNEAREST, fFAREST);
		gl.glMatrixMode(GL10.GL_MODELVIEW);	// �������ӼҦ�

		gl.glViewport(0, 0, width, height);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub

		// �]�w3D�������I���C��A�]�N�Oclipping wall���C��
		gl.glClearColor(backColorR, backColorG, backColorB, backColorA);

		// �]�wOpenGL���\��
		gl.glEnable(GL10.GL_DEPTH_TEST);	// ���黷�񪺾B���ĪG
		gl.glEnable(GL10.GL_CULL_FACE);	// �Ϥ�Triangle�����ϭ�
		gl.glFrontFace(GL10.GL_CCW);	// �f�ɰw���I���Ǭ�����
		gl.glCullFace(GL10.GL_BACK);	// �ϭ���Triangle�����
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);	// �ϥγ��I�}�C
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);	// �ϥ��C��}�C

		setup();
	}

}
