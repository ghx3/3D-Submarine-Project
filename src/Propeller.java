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
		for (int i = 0; i < 4; i++) {
			// draw the propeller
			gl.glRotated(i * 90, 1, 0, 0);
			gl.glPushMatrix();

			gl.glTranslated(0.6, 0, -0.3);
			gl.glScaled(0.02, 0.1, 0.15);

			glut.glutSolidCone(0.8, 2, 20, 20);

			glu.gluSphere(quadric, 0.8, 20, 20);
			gl.glPopMatrix();
		}
	}
}