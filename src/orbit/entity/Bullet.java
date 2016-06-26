package orbit.entity;

import orbit.math.Vector2f;
import orbit.model.Mesh;
import orbit.model.MeshLoader;
import orbit.render.AssetLoader;
import orbit.texture.Texture;

public class Bullet extends Projectile {
	
	private static final int RADIUS = 4;
	private static final int WIDTH = 4;
	private static final int HEIGHT = 8;
	private static final Mesh MESH = MeshLoader.createQuadFromCenter(new Vector2f(WIDTH, HEIGHT));
	private static final Texture TEXTURE = AssetLoader.createTexture("bullet.png");
	private static final int MASS = 10;
	
	private static final float ACTIVE = 0.1f;
	private static final float DAMAGE = 11f;
	
	public Bullet(Vector2f position, float rotation, Vector2f velocity, Vector2f force) {
		super(MESH, TEXTURE, RADIUS, MASS);
		this.position = position;
		this.rotation = rotation;
		this.velocity = velocity;
		this.applyForce(force);
		Bullet.active = ACTIVE;
		Bullet.damage = DAMAGE;
	}
	
	@Override
	public void checkCollision(DynamicObject other) {
		if (!(other instanceof Bullet) && isActive()) {
			super.checkCollision(other);
		}
	}
}
