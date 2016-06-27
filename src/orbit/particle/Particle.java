package orbit.particle;

import orbit.math.Vector2f;
import orbit.math.Vector4f;
import orbit.model.Mesh;
import orbit.model.MeshLoader;
import orbit.texture.Texture;

public class Particle {
	
	private static final Mesh MESH = MeshLoader.createQuadFromCenter(new Vector2f(1, 1));
	
	private Texture texture;
	private Vector4f color;
	private Vector2f position;
	private Vector2f velocity;
	private float angle;
	private float alive;
	private float lifetime;
	private float scale;
	
	public Particle(Texture texture, Vector4f color, Vector2f position, float velocity, float angle, float lifetime, float size) {
		this.texture = texture;
		this.color = color;
		this.position = position;
		
		Vector2f dir = new Vector2f((float) -Math.sin(Math.toRadians(angle)), (float) -Math.cos(Math.toRadians(angle)));
		Vector2f.normalize(dir, dir);
		this.velocity = Vector2f.mul(velocity, dir, null);
		
		this.angle = angle;
		this.lifetime = lifetime;
		this.scale = size;
	}
	
	public void update(float dt) {
		this.alive += dt;
		Vector2f.add(position, Vector2f.mul(dt, velocity, null), position);
		color.w = Math.max((this.lifetime - this.alive) / this.lifetime, 0);
		scale -= dt;
	}
	
	public boolean isAlive() {
		return alive <= lifetime;
	}
	
	public Vector2f getPos() {
		return position;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public Vector4f getColor() {
		return color;
	}
	
	public float getScale() {
		return scale;
	}
	
	public static Mesh getMesh() {
		return MESH;
	}
}
