import java.io.File;
import java.io.IOException;

import com.jogamp.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class Grid {
	// set grid size
	int size = 256;
	// set color
	double r, g, b, a;
	// set sea bed and sea surface level
	int level;
	// set texture variable
	private Texture texture;
	
	//change grid height
	
	// constructor for the grid
	public Grid(int size, double r, double g, double b, double a, int level, String textureFileName) {
		this.size = size;
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		this.level = level;
		// get the image file
		try {
			File img = new File(textureFileName);
			texture = TextureIO.newTexture(img, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// draw the grid
	public void draw(GL2 gl) {
		gl.glShadeModel(GL2.GL_SMOOTH);
		// TODO Auto-generated method stub
		texture.bind(gl);
		texture.enable(gl);

		gl.glPushMatrix();
		gl.glTranslated(-50, level, -50);
		gl.glColor4d(r, g, b,a);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glLineWidth(30);
		//gl.glEnable(GL2.GL_NORMALIZE);
		
		for (int j = 0; j < size; j ++) {
			for (int i = 0; i < size; i ++) {
				gl.glBegin(GL2.GL_QUADS);
				gl.glNormal3d(0, 1, 0);
				gl.glTexCoord2d(0, 0);
				gl.glVertex3d(j, 0, i);
				gl.glNormal3d(0, 1, 0);
				gl.glTexCoord2d(1, 0);
				gl.glVertex3d(j+1, 0, i);
				gl.glNormal3d(0, 1, 0);
				gl.glTexCoord2d(1, 1);
				gl.glVertex3d(j+1, 0, i+1);
				gl.glNormal3d(0, 1, 0);
				gl.glTexCoord2d(0, 1);
				gl.glVertex3d(j, 0, i+1);
				gl.glEnd();
			}
		}
			gl.glPopMatrix();
			texture.disable(gl);
		}
}