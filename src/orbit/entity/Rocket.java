package orbit.entity;

import orbit.math.Vector2f;
import orbit.model.Mesh;
import orbit.model.MeshLoader;
import orbit.render.AssetLoader;
import orbit.texture.Texture;

public class Rocket extends Projectile {
	
	private static final int RADIUS = 16;
	private static final int WIDTH = 16;
	private static final int HEIGHT = 20;
	private static final Mesh MESH = MeshLoader.createQuadFromCenter(new Vector2f(WIDTH, HEIGHT));
	private static final Texture TEXTURE = AssetLoader.createTexture("bullet.png");
	private static final int MASS = 100;
	
	private static final float ACTIVE = 0.5f;
	private static final float DAMAGE = 100f;
	private float fuel = 10;
	
	public Rocket(Vector2f position, float rotation, Vector2f velocity, Vector2f force) {
		super(MESH, TEXTURE, RADIUS, MASS);
		this.position = position;
		this.rotation = rotation;
		this.velocity = velocity;
		this.applyForce(force);
		Rocket.active = ACTIVE;
		Rocket.damage = DAMAGE;
	}
	
	@Override
	public void update(float dt) {
		if (fuel > 0) {
			float usedFuel = Math.min(fuel, dt);
			fuel -= usedFuel;
			float force = usedFuel * 1000000;
			Vector2f dir = new Vector2f((float) -Math.sin(Math.toRadians(rotation)), (float) -Math.cos(Math.toRadians(rotation)));
			Vector2f.normalize(dir, dir);
			applyForce(Vector2f.mul(force, dir, null));
		}
		super.update(dt);
	}
	
	@Override
	public void checkCollision(DynamicObject other) {
		if (!(other instanceof Bullet) && isActive()) {
			super.checkCollision(other);
		}
	}
}
