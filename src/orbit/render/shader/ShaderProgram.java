package orbit.render.shader;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import orbit.math.Matrix4f;
import orbit.math.Vector3f;

public abstract class ShaderProgram {
	
	private final int id;
	private final int vertShader;
	private final int fragShader;
	
	public ShaderProgram(String vertexShader, String fragmentShader) {
		id = glCreateProgram();
		this.vertShader = loadShader(GL_VERTEX_SHADER, vertexShader);
		this.fragShader = loadShader(GL_FRAGMENT_SHADER, fragmentShader);
		attachShader(vertShader);
		attachShader(fragShader);
		bindAttributes();
		glLinkProgram(id);
		checkStatus();
		getUniformLocations();
	}
	
	protected abstract void bindAttributes();
	
	protected abstract void getUniformLocations();
	
	private void attachShader(int shaderId) {
		glAttachShader(id, shaderId);
	}
	
	protected void bindFragData(int colorNumber, String name) {
		glBindFragDataLocation(id, colorNumber, name);
	}
	
	public void start() {
		glUseProgram(id);
	}
	
	public void stop() {
		glUseProgram(0);
	}
	
	protected final void bindAttribute(int attribute, String varName) {
		glBindAttribLocation(id, attribute, varName);
	}
	
	protected final int getAttribute(String var) {
		return glGetAttribLocation(id, var);
	}
	
	public void enableAttribute(int attrib) {
		glEnableVertexAttribArray(attrib);
	}
	
	public void disableAttribute(int attrib) {
		glDisableVertexAttribArray(attrib);
	}
	
	public void specifyVertexAttribute(int location, int size, int stride, int offset) {
		glVertexAttribPointer(location, size, GL_FLOAT, false, stride, offset);
	}
	
	protected int getUniform(String var) {
		return glGetUniformLocation(id, var);
	}
	
	public void setUniform(int location, Matrix4f matrix) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		Matrix4f.store(buffer, matrix);
		buffer.flip();
		glUniformMatrix4fv(location, true, buffer);
	}
	
	public void setUniform(int location, Vector3f value) {
		glUniform3f(location, value.x, value.y, value.z);
	}
	
	public void setUniform(int location, float value) {
		glUniform1f(location, value);
	}
	
	public void setUniform(int location, int value) {
		glUniform1i(location, value);
	}
	
	public void delete() {
		stop();
		glDetachShader(id, vertShader);
		glDetachShader(id, fragShader);
		glDeleteShader(vertShader);
		glDeleteShader(fragShader);
		glDeleteProgram(id);
	}
	
	public void checkStatus() {
        int status = glGetProgrami(id, GL_LINK_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetProgramInfoLog(id));
        }
    }
	
	private static int loadShader(int type, String file) {
		int id = glCreateShader(type);
		
		StringBuilder builder = new StringBuilder();
		
		try (InputStream in = new FileInputStream("res/shaders/" + file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line).append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		glShaderSource(id, builder.toString());
		glCompileShader(id);
		
		return id;
	}
	
	public int getId() {
		return id;
	}
}
