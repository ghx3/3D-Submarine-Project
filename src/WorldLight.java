import com.jogamp.opengl.GL2;

public class WorldLight {
	public void setup(GL2 gl) {
		float ambient[] = { 1f, 1f, 1f, 0f };

		float diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float position[] = { 10, 10, 10, 1f };

		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_AMBIENT, ambient, 0);
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_DIFFUSE, diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_SPECULAR, specular, 0);
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION, position, 0);

	}
}