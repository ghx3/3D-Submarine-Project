import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.FloatBuffer;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

public class BuildEventListener implements GLEventListener, KeyListener {

	private Origin o = new Origin();

	private Submarine s = new Submarine();
	private static GLCanvas canvas;
	private Camera camera = new Camera(canvas);
	private boolean frame = false;
	private static int windowWidth = 800;
	private static int windowHeight = 800;
	private Grid bed = null;
	private Grid sea = null;
	// private GLU glu = new GLU();

	private WorldLight wl = new WorldLight();

	@Override
	public void display(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();

		// clear the depth and color buffers
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		// gl.glClearColor(0.3f,0.3f,0.1f,1.0f);
		// using default camera & projection

		// set up lights
		this.lights(gl);
		this.spotLight(gl);
		//if(s.getY()>= 5.0){
		//s.backLight(gl);
		//}
		gl.glPolygonMode(gl.GL_FRONT_AND_BACK, frame ? gl.GL_LINE : gl.GL_FILL);

		// draw the starting solid cone and the coordinate system
		gl.glPushMatrix();
		o.display(gl);
		gl.glPopMatrix();
		// draw the camera
		camera.setLookAt(s.getX(), s.getY(), s.getZ());
		camera.setA(s.getHeading() + 90);
		camera.setEl(10);
		camera.draw(gl);
		
		if(s.getY() >= 5.0){
			gl.glEnable(GL2.GL_LIGHT2);
		}else{
			gl.glDisable(GL2.GL_LIGHT2);
		}
		
		
		// change camera
		if (Math.abs(s.getX()) > 45 || Math.abs(s.getZ()) > 45) {
			System.out.println("TURN BACK");
			s.turnAround();
		}
		// draw submarine body part
		s.draw(gl);
		// draw grid
		sea.draw(gl);
		bed.draw(gl);
		gl.glClearColor(0.25f, 0.64f, 0.72f, 0.5f);
		gl.glEnable(GL2.GL_FOG);
		float[] fogColor = {0.25f, 0.64f, 0.72f, 0.5f };
		
		gl.glFogi(GL2.GL_FOG_MODE, GL2.GL_EXP);
		gl.glFogi(GL2.GL_FOG_MODE, GL2.GL_EXP2); // Fog Mode
		gl.glFogi(GL2.GL_FOG_MODE, GL2.GL_LINEAR);
		gl.glFogfv(GL2.GL_FOG_COLOR, fogColor, 0); // Set Fog Color
		gl.glFogf(GL2.GL_FOG_DENSITY, 0.95f); // How Dense Will The Fog Be
		gl.glHint(GL2.GL_FOG_HINT, GL2.GL_NICEST); // Fog Hint Value
		gl.glFogf(GL2.GL_FOG_START, 0.5f); // Fog Start Depth
		gl.glFogf(GL2.GL_FOG_END, 10.0f); // Fog End Depth
		
		gl.glEnable(GL2.GL_NORMALIZE);

		gl.glFlush();

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		// initial gird parameter
		bed = new Grid(200, 1, 1, 1, 1, /* 0.79, 0.74, 0.57 */ -7, "textures/seabed.jpg");
		sea = new Grid(200, 1, 1, 1, 1, /* 0.28, 0.87, 1, 0.9, */ 5, "textures/seaface.jpg");
		wl.setup(gl);
		gl.setSwapInterval(1);

		// enable depth test and set shading mode
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_TEXTURE_2D);

		gl.glShadeModel(GL2.GL_SMOOTH);
		// camera = new Camera(canvas);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_NORMALIZE);

	}

	public void spotLight(GL2 gl) {
		float ambient[] = { 1f, 1f, 1f, 1.0f };

		float diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float position[] = { s.getX(), s.getY() - 0.2f, s.getZ(), 1 };
		float direction[] = { 0f, -1f, 0f };

		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, ambient, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, specular, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, position, 0);
		gl.glLightf(GL2.GL_LIGHT1, GL2.GL_CONSTANT_ATTENUATION, 1.5f);
		gl.glLightf(GL2.GL_LIGHT1, GL2.GL_LINEAR_ATTENUATION, 0.5f);
		gl.glLightf(GL2.GL_LIGHT1, GL2.GL_QUADRATIC_ATTENUATION, 0.2f);

		gl.glLightf(GL2.GL_LIGHT1, GL2.GL_SPOT_CUTOFF, 30.0f);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPOT_DIRECTION, direction, 0);
		gl.glLightf(GL2.GL_LIGHT1, GL2.GL_SPOT_EXPONENT, 2.0f);
		// enable lights
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT1);
	
	}

	private void lights(GL2 gl) {

		// lighting stuff
		float ambient[] = { 0, 0, 0, 1 };
		float diffuse[] = { 0.3f, 0.3f, 0.3f, 1 };
		float specular[] = { 1, 1, 1, 1 };
		float position0[] = { 1, 1, 1, 0 };
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, position0, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specular, 0);

		// enable lights
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);

		// draw using standard glColor
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		camera.newWindowSize(width, height);

	}

	public static void main(String[] args) {
		Frame frame = new Frame("Assignment 2");
		canvas = new GLCanvas();
		BuildEventListener app = new BuildEventListener();
		canvas.addGLEventListener(app);
		canvas.addKeyListener(app);
		frame.add(canvas);

		// initial key mapping for the program
		System.out.println("Key mapping: \n" + "--------------------------------------------\n"
				+ "UP/DOWN ARROWS: Increase depth (dive) or decrease depth (surface)\n"
				+ "W/S: Move forward or backward\n" + "A/D: Yaw (turn) left or right \n");

		frame.setSize(windowWidth, windowHeight);
		final FPSAnimator animator = new FPSAnimator(canvas, 60);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// Run this on another thread than the AWT event queue to
				// make sure the call to Animator.stop() completes before
				// exiting
				new Thread(new Runnable() {

					@Override
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});
		// Center frame
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		canvas.setFocusable(true);
		canvas.requestFocus();
		animator.start();

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			s.moveUp = true;
			System.out.println("submarine is going up");
		}
		if (key == KeyEvent.VK_DOWN) {
			s.moveDown = true;
			System.out.println("submarine is going down");
		}
		if (key == KeyEvent.VK_W) {
			s.moveForward = true;
			// s.acceleration = 0.02;
			System.out.println("submarine is going forward");
		}
		if (key == KeyEvent.VK_S) {
			s.moveBackward = true;
			// s.acceleration = -0.02;
			System.out.println("submarine is going backward");
		}
		if (key == KeyEvent.VK_A) {
			s.moveLeft = true;
			System.out.println("submarine is going leftside");
		}
		if (key == KeyEvent.VK_D) {
			s.moveRight = true;
			System.out.println("submarine is going rightside");
		}
		if (key == KeyEvent.VK_L) {
			frame = !frame;
			System.out.println("wireframe mode");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
		// case KeyEvent.VK_W:
		// case KeyEvent.VK_S:
		// s.acceleration = 0;
		// break;
		case KeyEvent.VK_A:
			s.moveLeft = false;
			s.resetRoll();
			break;
		case KeyEvent.VK_D:
			s.moveRight = false;
			s.resetRoll();
			break;
		case KeyEvent.VK_UP:
			s.moveUp = false;
			s.resetPitch();
			break;
		case KeyEvent.VK_DOWN:
			s.moveDown = false;
			s.resetPitch();
			break;
		case KeyEvent.VK_W:
			s.moveForward = false;
			break;
		case KeyEvent.VK_S:
			s.moveBackward = false;

			break;
		}

	}

}