package orbit.entity;

import orbit.model.Mesh;
import orbit.texture.Texture;

public abstract class Projectile extends DynamicObject {

	protected static float active = 1;
	
	private float timeAlive = 0;
	
	public Projectile(Mesh mesh, Texture texture, int width, int height, float mass) {
		super(mesh, texture, width, height, mass);
	}
	
	@Override
	public void update(float dt) {
		timeAlive += dt;
		super.update(dt);
	}
	
	public boolean isActive() {
		return timeAlive >= active;
	}
}
