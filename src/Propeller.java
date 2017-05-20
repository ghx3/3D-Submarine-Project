import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;

public class Propeller {// extends Submarine{
	GLU glu = new GLU();
	private GLUquadric quadric;
	// private int angle;

	public void draw(GL2 gl) {
		GLUT glut = new GLUT();
		quadric = glu.gluNewQuadric();
		for(int i = 0; i< 4; i++){
		// draw the propeller
			gl.glRotated(i*90, 0, 0, 0);
		gl.glPushMatrix();
		
			
			gl.glTranslated(0.6, 0, -0.3);
			gl.glScaled(0.02, 0.1, 0.15);
			gl.glColor3d(0.72, 0.91, 0.52);
			glut.glutSolidCone(0.8, 2, 20, 20);
			gl.glColor3d(0.96, 0.65, 0.13);
			glu.gluSphere(quadric, 0.8, 20, 20);
		gl.glPopMatrix();
		}
/*
		// 2nd
		gl.glPushMatrix();
		// gl.glRotated(angle, 1, 0, 0);
			gl.glRotated(150, 0, 0, 0);
			gl.glTranslated(0.6, 0, -0.3);
			gl.glScaled(0.02, 0.1, 0.15);
			gl.glColor3d(0.72, 0.91, 0.52);
			glut.glutSolidCone(0.8, 2, 20, 20);
			gl.glColor3d(0.96, 0.65, 0.13);
			glu.gluSphere(quadric, 0.8, 20, 20);
		gl.glPopMatrix();

		// 3rd
		gl.glPushMatrix();
		// gl.glRotated(angle, 1, 0, 0);
			gl.glRotated(60, 0, 0, 0);
			gl.glTranslated(0.6, 0, -0.3);
			gl.glScaled(0.02, 0.1, 0.15);
			gl.glColor3d(0.72, 0.91, 0.52);
			glut.glutSolidCone(0.8, 2, 20, 20);
			gl.glColor3d(0.96, 0.65, 0.13);
			glu.gluSphere(quadric, 0.8, 20, 20);
		gl.glPopMatrix();

		// 4th
		gl.glPushMatrix();
		// gl.glRotated(angle, 1, 0, 0);
			gl.glRotated(-300, 0, 0, 0);
			gl.glTranslated(0.6, 0, 0.3);
			gl.glScaled(0.02, 0.1, 0.15);
			gl.glColor3d(0.72, 0.91, 0.52);
			glut.glutSolidCone(0.8, -2, 20, 20);
			gl.glColor3d(0.96, 0.65, 0.13);
			glu.gluSphere(quadric, 0.8, 20, 20);
		gl.glPopMatrix();
		// angle += 10; */
	}

}