import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;

public class Origin {

	private GLUquadric quadric;

	public void display(GL2 gl) {
		GLU glu = new GLU();
		quadric = glu.gluNewQuadric();
		// draw a place holder GLUT cone drawn at the origin and then
		// rotated so that it is upright.
		gl.glColor3d(0, 1, 0);
		gl.glPushMatrix();
		gl.glRotated(180, 1, 0, 0);
		glu.gluSphere(quadric, 0.01, 20, 20);
		gl.glPopMatrix();
	}
}
