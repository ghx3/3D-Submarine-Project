import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;




public class BuildEventListener implements GLEventListener,KeyListener {

	private Origin o = new Origin();
	private OriginLocator axis = new OriginLocator();
	private Submarine s = new Submarine();
	//private Propeller p = new Propeller();
	private static GLCanvas canvas;
	private Camera camera = new Camera(canvas); 
	private boolean frame = false;
	private int windowWidth, windowHeight;
    private Grid grid = new Grid(100, 1, 0.79, 0.74,0.57, -3);
    private Grid grid1 = new Grid(100, 0.28, 0.87, 1,0.9, 3);
    
	@Override
	public void display(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();
		GLU glu = new GLU ();
		// clear the depth and color buffers
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		
		// using default camera & projection
		
		// set up lights
		this.lights(gl);
		
		//gl.glMatrixMode(GL2.GL_MODELVIEW);
		//gl.glLoadIdentity();
		
		
		gl.glPolygonMode(gl.GL_FRONT_AND_BACK, frame ? gl.GL_LINE : gl.GL_FILL);
   	 	
		
		// draw the starting solid cone
		o.display(gl);
		// draw the coordinate system
		axis.draw(gl);
		// draw submarine body part
		
		//p.draw(gl);
		s.draw(gl);
		
		// draw the camera 
		camera.draw(gl);
		//camera.setA(angle);
		//camera.setAl(45);
		// draw grid
		grid.draw(gl);
		grid1.draw(gl);
		
		
		
		gl.glFlush();

	}
	
	

	
	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.setSwapInterval(1);

		// enable depth test and set shading mode
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glShadeModel(GL2.GL_SMOOTH);
		//camera = new Camera(canvas);
		
	}

	private void lights(GL2 gl) {

		// lighting stuff
		float ambient[] = { 0, 0, 0, 1 };
		float diffuse[] = { 1, 1, 1, 1 };
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
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		height = (height == 0) ? 1 : height; // prevent divide by zero
		this.windowWidth = windowWidth;
		this.windowHeight =windowHeight;
	}
	
	

	public static void main(String[] args) {
		Frame frame = new Frame("Assignment 2");
		canvas = new GLCanvas();
		BuildEventListener app = new BuildEventListener();
		canvas.addGLEventListener(app);
		canvas.addKeyListener(app);
		frame.add(canvas);
		
		//initial key mapping for the program
		System.out.println("Key mapping: \n"+
				"--------------------------------------------\n"+
				"UP/DOWN ARROWS: Increase depth (dive) or decrease depth (surface)\n"+
				"W/S: Move forward or backward\n"+
				"A/D: Yaw (turn) left or right \n");
		
		frame.setSize(800, 800);
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
		switch (key){
		case KeyEvent.VK_UP:
			 s.moveUp();
			 System.out.println("submarine is going up");
			 
			 break;
		case KeyEvent.VK_DOWN:
			 s.moveDown();
			 System.out.println("submarine is going down");
			 break;
			 
		
		case KeyEvent.VK_W:
			 s.moveForward();
			 System.out.println("submarine is going forward"); 
			 break;
		case KeyEvent.VK_S:
			 s.moveBackward();
			 System.out.println("submarine is going backward");
			 break;
		case KeyEvent.VK_A:
			 s.moveLeft();
			 System.out.println("submarine is going left side");
			 break;
		case KeyEvent.VK_D:
			 s.moveRigth();
			 System.out.println("submarine is going right");
			 break;
		case KeyEvent.VK_L:
			 frame= !frame;
			 System.out.println("wireframe mode");
			 break;
		case KeyEvent.KEY_TYPED:
			System.out.println("invalid input");
		}
		System.out.println("Event Fires");
		double[] pos = s.getLocation();
		camera.setLookAt(pos[0], pos[1], pos[2] );
		
		camera.setLocation(pos[0], pos[1], pos[2] + 2);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int	key = e.getKeyCode();
		if(key == KeyEvent.VK_UP){
			s.depth = Movement.NONE;
		}
		if(key == KeyEvent.VK_DOWN){
			s.depth = Movement.NONE;
		}
		if(key == KeyEvent.VK_W){
			s.propulsion = Movement.NONE;
		}
		if(key == KeyEvent.VK_S){
			s.propulsion = Movement.NONE;
		}
		if(key == KeyEvent.VK_A){
			s.turn = Movement.NONE;
		}
		if(key == KeyEvent.VK_D){
			s.turn = Movement.NONE;
		}
	
	}

}