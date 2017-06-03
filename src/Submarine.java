
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;

public class Submarine {
	private double heading = 0;
	private double pitch = 0;
	private double roll = 0;
	
	private double velocity = 0.3;
	//static private double maxVelocity = 0.3f;
	static public double acceleration = 0.00f;
	//static private double fraction = 0.02f;
	
	//set submarine location coordinate
	private float[] Location = {0,0,0};
	
	private GLUquadric circle;
	private GLUquadric quadric;
	private Propeller prop = new Propeller();
	
	boolean moveUp = false;
	boolean moveDown = false;
	boolean moveLeft = false;
	boolean moveRight = false;
	boolean moveForward = false;
	boolean moveBackward = false;
	//set rotate propeller angle
	private int angle;
	
	//get the submarine location coordinate
	public float getX() {
		return Location[0];
	}

	public float getY() {
		return Location[1];

	}

	public float getZ() {
		return Location[2];
	}
	
	/*
	//change the submarine moving state
	public void update() {
		
		//get speed for submarine moving
		velocity += acceleration;
		//to decide whether submarine going backwards or forwards
		if (acceleration > 0)
		{
			velocity = Math.min(velocity, maxVelocity);
		}
		else if (acceleration < 0)
		{
			velocity = Math.max(velocity, -maxVelocity);
		}
		else
		{
			if (velocity > 0) velocity = Math.max(velocity - fraction, 0.0f);
			if (velocity < 0) velocity = Math.min(velocity + fraction, 0.0f);
		}
		//propeller rotate speed
		angle += (int) (velocity * 36.0f);
		
		Location[0] -= Math.cos(Math.toRadians(heading)) * velocity;
		Location[2] += Math.sin(Math.toRadians(heading)) * velocity;
	}
	*/
	
	public void draw(GL2 gl) {
		//update();

		GLU glu = new GLU();
		GLUT glut = new GLUT();
		circle = glu.gluNewQuadric();
		quadric = glu.gluNewQuadric();

		// draw a place holder GLUT cone drawn at the origin and then
		// rotated so that it is upright.
		//gl.glDisable(GL2.GL_COLOR_MATERIAL);
		
		gl.glPushMatrix();

		gl.glTranslated(Location[0], Location[1], Location[2]);
		gl.glRotated(heading, 0, 1, 0);
		gl.glRotated(roll, 1, 0, 0);
		gl.glRotated(pitch, 0, 0, 1);
		
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, new float[] {1,1,1,1}, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, new float[] { 0,0,0, 1 }, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE,new float[] { 0.5f,0.5f,0.5f, 1 },0) ;
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR,new float[] { 0.5f,0.5f,0.5f, 1 } , 0);
		gl.glPushMatrix();
		
		// draw the submarine body
		
		gl.glPushMatrix();
		gl.glScaled(2, 0.8, 0.7);
		glut.glutSolidSphere(0.3, 20, 20);

		gl.glPopMatrix();

		gl.glRotated(angle, 1, 0, 0);
		prop.draw(gl);

		gl.glPopMatrix();

		// draw fore planes
		gl.glPushMatrix();
	
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

		gl.glRotated(-90, 0, 0, -1);
		gl.glTranslated(0.25, 0, 0);
		gl.glScaled(0.01, 0.055, 0.2);
		glu.gluCylinder(quadric, 1, 1, 1, 20, 1);
		gl.glTranslated(0, 0, 1);
		glu.gluQuadricDrawStyle(circle, GLU.GLU_FILL);
		glu.gluDisk(circle, 0, 1, 20, 20);
		gl.glPopMatrix();

		// 2nd side planes
		gl.glPushMatrix();
		gl.glRotated(-90, 0, 0, -1);
		gl.glTranslated(0.25, 0, -0.2);
		gl.glScaled(0.01, 0.055, 0.2);
		glu.gluCylinder(quadric, 1, 1, 1, 20, 1);
		glu.gluQuadricDrawStyle(circle, GLU.GLU_FILL);
		glu.gluDisk(circle, 0, 1, 20, 20);
		gl.glPopMatrix();
		
		gl.glPopMatrix();
		
		
		if(moveUp){
			moveUp();
		}
		if(moveDown){
			moveDown();
		}
		if(moveLeft){
			moveLeft();
		}
		if(moveRight){
			moveRight();
		}
		if(moveForward){
			moveForward();
		}
		if(moveBackward){
			moveBackward();
		}
	}
	public void backLight (GL2 gl){
		float ambient[] = { 0, 0, 0, 1 };

		float diffuse[] = { 0, 0.7f, 1.0f, 1.0f };
		float specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float positionDirection[] = { getX(), getY(), getZ() };
		float position[] = { 0, 5, 0, 1};
		
		gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_AMBIENT, ambient, 0);
		gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_DIFFUSE, diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_SPECULAR, specular, 0);
		gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_POSITION, position, 0);
		gl.glLightf(GL2.GL_LIGHT3, GL2.GL_SPOT_CUTOFF, 15.0f);
		gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_SPOT_DIRECTION, positionDirection, 0);
		
		
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT3);
	}
	public void moveUp() {
		if (Location[1] <= 5) {
			Location[1] += 0.1;
		}
		if(pitch > -15 &&Location[1]< 5){
			pitch -=5;	
		}
		else if(Location[1] >= 5){
			resetPitch();
		}
		
	}
	
	public void moveDown() {
		if (Location[1] >= -6) {
			Location[1] -= 0.1;
		}
		
		if(pitch <15 && Location[1] > -6){
			pitch += 5;
		}
		else if(Location[1] <= -6){
			resetPitch();
		}
	}

	public void moveLeft() {
		heading += 5;
		heading = heading % 360;
		if(roll < 20){
		roll+= 5;
		}
	}

	public void moveRight() {
		heading -= 5;
		heading = heading % 360;
		if(roll >-20)
		roll -= 5;
		
	}
	public void moveForward(){
		Location[0] -= Math.cos(Math.toRadians(heading)) * velocity;
		Location[2] += Math.sin(Math.toRadians(heading)) * velocity;
		angle += (int) (velocity * 360.0f);
		
	}
	
	public void moveBackward(){
		Location[0] += Math.cos(Math.toRadians(heading)) * velocity;
		Location[2] -= Math.sin(Math.toRadians(heading)) * velocity;
		angle -= (int) (velocity * 360.0f);

	}
	

	public double getHeading() {
		return heading;
	}
	public void resetPitch(){
		pitch = 0;
	}
	public void resetRoll(){
		roll = 0;
	}
	
	//change scene
	public void turnAround() {
		heading += 90;
	}

}
