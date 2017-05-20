import com.jogamp.opengl.GL2;

public class OriginLocator  {

		
		public void draw(GL2 gl){
			gl.glDisable(GL2.GL_LIGHTING);
			gl.glLineWidth(30);
			gl.glBegin(GL2.GL_LINES);
			// x-axis 
			gl.glColor3d(1, 0, 0);
            gl.glVertex3d(0, 0, 0);
            gl.glVertex3d(10, 0, 0);
            // y-axis 
            gl.glColor3d(0, 1, 0);
            gl.glVertex3d(0, 0, 0);
            gl.glVertex3d(0, 10, 0);
            // z-axis 
            gl.glColor3d(0, 0, 1);
            gl.glVertex3d(0, 0, 0);
            gl.glVertex3d(0, 0, 10);

			gl.glEnd();
			gl.glEnable(GL2.GL_LIGHTING);
		}

	}


