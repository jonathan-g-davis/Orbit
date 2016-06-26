package orbit.entity;

import orbit.model.Mesh;
import orbit.texture.Texture;

public abstract class Projectile extends DynamicObject {

	protected static float active = 1;
	protected static float damage = 100;
	
	private float timeAlive = 0;
	
	public Projectile(Mesh mesh, Texture texture, float collisionRadius, float mass) {
		super(mesh, texture, collisionRadius, mass);
	}
	
	@Override
	public void update(float dt) {
		timeAlive += dt;
		super.update(dt);
	}
	
	@Override
	public void checkCollision(DynamicObject other) {
		if (other instanceof Projectile) {
			super.checkCollision(other);
		}
	}
	
	public boolean isActive() {
		return timeAlive >= active;
	}
	
	public float getDamage() {
		return damage;
	}
}
