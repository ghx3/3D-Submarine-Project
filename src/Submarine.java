
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;

public class Submarine {
	public double heading  = 0;
	//get submarine location
	private double[] location = {0,0,0};
	public Movement depth = Movement.UP;
	public Movement propulsion = Movement.FORWARD;
	public Movement turn = Movement.NONE;
	boolean move = false;
	private GLUquadric circle;
	private GLUquadric quadric;
	private Propeller prop = new Propeller();
	private int angle;
	public void drawRotateP(GL2 gl){
		if( move == true)
		gl.glRotated(angle, 1, 0, 0);
		prop.draw(gl);
		angle+=10;
	}
	public double[] getLocation() {
		return location;
	}
	public void draw(GL2 gl) {
		GLU glu = new GLU();
		GLUT glut = new GLUT();
		circle = glu.gluNewQuadric();
		quadric = glu.gluNewQuadric();
		gl.glRotated(heading, 0, 1, 0);	
		// draw a place holder GLUT cone drawn at the origin and then
		// rotated so that it is upright.
		gl.glColor3d(0.35, 0.55, 0.75);
		gl.glPushMatrix();
		
		gl.glTranslated(location[0], location[1],location[2]);
		
		gl.glPushMatrix();
			// draw the submarine body
				gl.glColor3d(0.5, 0.49, 0.49);
				gl.glPushMatrix();
					gl.glScaled(2, 0.8, 0.7);
					glut.glutSolidSphere(0.3, 20, 20);
				gl.glPopMatrix();
				drawRotateP(gl);
			gl.glPopMatrix();
			
			
		
		
			// draw fore planes
			gl.glPushMatrix();
				gl.glColor3d(0.28, 0.96, 1);
				gl.glRotated(-90, 1, 0, 0);
				gl.glTranslated(0, 0, 0.2);
				gl.glScaled(0.1, 0.055, 0.1);
				glu.gluCylinder(quadric, 1, 1, 1, 20, 1);
				gl.glTranslated(0, 0, 1);
				glu.gluQuadricDrawStyle(circle, GLU.GLU_FILL);
				glu.gluDisk(circle, 0, 1, 20, 20);
			gl.glPopMatrix();
			
			// draw side planes
			gl.glPushMatrix();
				gl.glColor3d(1, 0.28, 0.83);
				gl.glRotated(-90, 0, 0, -1);
				gl.glTranslated(0.25, 0, 0);
				gl.glScaled(0.01, 0.055, 0.2);
				glu.gluCylinder(quadric, 1, 1, 1, 20, 1);
				gl.glTranslated(0, 0, 1);
				glu.gluQuadricDrawStyle(circle, GLU.GLU_FILL);
				glu.gluDisk(circle, 0, 1, 20, 20);
			gl.glPopMatrix();
			
			//2nd side planes
			gl.glPushMatrix();
				gl.glColor3d(1, 0.28, 0.83);
				gl.glRotated(-90, 0, 0, -1);
				gl.glTranslated(0.25, 0, -0.2);
				gl.glScaled(0.01, 0.055, 0.2);
				glu.gluCylinder(quadric, 1, 1, 1, 20, 1);
				glu.gluQuadricDrawStyle(circle, GLU.GLU_FILL);
				glu.gluDisk(circle, 0, 1, 20, 20);
			gl.glPopMatrix();
			
		gl.glPopMatrix();
	}
	/* cos(heading) = x/ speed ; x = cos(heading) * speed;
	 * new x = old x + cos(heading) * speed;
	 * 
	 * sin(heading) z / speed; z = sin(heading) * speed; new z = old z + sin(heading) * speed;
	 */
	
	
	/*sea.getLevel();
	 * if(sub y + speed <= seaLevel){
	 * 	subY += speed;
	 * }else{
	 *  subY = seaLevel;
	 * }
	 * 
	 * moveUp(){
	 * 	subY = speed;
	 * }
	 */
	public void moveUp(){
		if (location[1] <=3){
		location[1] += 0.01; 
		}
		else{
			
		}
		move = false;
	}
	
	public void moveDown(){
		if(location[1]>=-3){
			location[1] -=0.01;
		}else{
			
		}
		move = false;
	}
	
	public void moveForward(){
		
		location[0] -= 0.1;
		//location[2] -= 0.1;
		
		move = true;
	}
	public void moveBackward(){
		location[0] -= Math.cos(Math.toRadians(heading)) * (0.01);
		location[2] -= Math.sin(Math.toRadians(heading)) *(0.01);
		
		move = true;
	}
	public void moveLeft(){
		//location[0] -= Math.cos(Math.toRadians(heading)) * (0.01);
		//location[2] -= Math.sin(Math.toRadians(heading)) *(0.01);
		heading+= 0.5;
	    heading = heading % 360;
		move = true;
	}
	public void moveRigth(){
		heading-= 0.5;
		heading = heading % 360;
		move = true;
	}
	double getheading(double angle){
		return heading = angle;
	}
	
}
