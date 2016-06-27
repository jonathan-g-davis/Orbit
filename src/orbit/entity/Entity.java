package orbit.entity;

import orbit.math.Vector2f;
import orbit.math.Vector4f;
import orbit.model.Mesh;
import orbit.render.Renderer;
import orbit.texture.Texture;

public class Entity {
	
	private Mesh model;
	private Texture texture;
	protected Vector4f color = new Vector4f(1, 1, 1, 1);
	protected Vector2f position = new Vector2f();
	protected float rotation = 0;
	protected float scale = 1;
	
	public Entity(Mesh mesh, Texture texture) {
		this.model = mesh;
		this.texture = texture;
	}
	
	public Entity(Mesh mesh, Texture texture, Vector2f position, float rotation, float scale) {
		this.model = mesh;
		this.texture = texture;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public void render(Renderer renderer) {
		renderer.render(this);
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public Mesh getMesh() {
		return model;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public Vector4f getColor() {
		return color;
	}

	public Vector2f getPos() {
		return position;
	}

	public float getRotation() {
		return rotation;
	}
	
	public float getScale() {
		return scale;
	}
}
