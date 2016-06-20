package orbit.entity;

import orbit.math.Vector2f;
import orbit.model.Mesh;
import orbit.model.MeshLoader;
import orbit.render.AssetLoader;
import orbit.texture.Texture;

public class Bullet extends Projectile {
	
	
	public static final int WIDTH = 8;
	public static final int HEIGHT = 16;
	public static final Mesh MESH = MeshLoader.createQuadFromCenter(new Vector2f(WIDTH, HEIGHT));
	public static final Texture TEXTURE = AssetLoader.createTexture("bullet.png");
	public static final int MASS = 10;
	
	public Bullet(Vector2f position, float rotation, Vector2f velocity, Vector2f force) {
		super(MESH, TEXTURE, WIDTH, HEIGHT, MASS);
		this.position = position;
		this.rotation = rotation;
		this.velocity = velocity;
		this.applyForce(force);
		Bullet.active = 0.1f;
	}
	
	@Override
	public void checkCollision(DynamicObject other) {
		if (!(other instanceof Bullet) && isActive()) {
			super.checkCollision(other);
		}
	}
}
