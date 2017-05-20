import java.util.ArrayList;

import com.jogamp.opengl.GL2;

public class Grid {
	int size = 100;
	double r,g,b,a;
	int level ;
	 
	
	public Grid(int size, double r, double d, double e, double a, int level ){
		this.size = size;
		this.r = r;
		this.g = d;
		this.b = d;
		this.a = a;
		this.level = level;
	}
	public void draw(GL2 gl) {
		// TODO Auto-generated method stub
		
		// draw land surface
		gl.glPushMatrix();
		gl.glTranslated(-50, level, -50);
		gl.glColor4d(r, g, b,a);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glLineWidth(30);
		for (int j = 0; j < size; j ++) {
			for (int i = 0; i < size; i ++) {
				
				gl.glBegin(GL2.GL_QUADS);
				gl.glNormal3d(0, 1, 0);
				gl.glVertex3d(j, 0, i);
				gl.glNormal3d(0, 1, 0);
				gl.glVertex3d(j+1, 0, i);
				gl.glNormal3d(0, 1, 0);
				gl.glVertex3d(j+1, 0, i+1);
				gl.glNormal3d(0, 1, 0);
				gl.glVertex3d(j, 0, i+1);
				gl.glEnd();
			}
		}
		
		gl.glPopMatrix();
		
		
		// draw sea surface
	/*	
		gl.glPushMatrix();
		gl.glTranslated(0, 2, 0);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glColor4d(0.3, 0.7, 0.7,0.1);
		gl.glColor4d(0.28, 0.87, 1,0.9);
		gl.glLineWidth(30);
		for (int i = 0; i < 30; i += 2) {
			for (int j = 0; j < 30; j += 2) {

				gl.glBegin(GL2.GL_QUADS);
				gl.glNormal3d(0, 1, 0);
				gl.glVertex3d(-i, 0, -j);
				gl.glNormal3d(0, 1, 0);
				gl.glVertex3d(-i, 0, j);
				gl.glNormal3d(0, 1, 0);
				gl.glVertex3d(i, 0, j);
				gl.glNormal3d(0, 1, 0);
				gl.glVertex3d(i, 0, -j);
				gl.glEnd();
			}
		}
		
		gl.glPopMatrix();
		*/
	}
	
	/*public Grid(int size, float[]color){
	 * this.size = size;
	 * this.color = color;
	 * }
	 * 
	 * +normal3d;
	 * color4d(r,g,b,a);
	 * for (int z = 0; z<size; z++){
	 * 
	 * for(int x = 0; x< size ; x++){
	 * vertex3d(x, 0, z);
	 * vertex3d(x+1,0,z);
	 * vertex3d(x+1,0,z+1);
	 * vertext3d(x,0,z+1);
	 * }
	 * }
	 * */

}