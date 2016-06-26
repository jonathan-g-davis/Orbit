package orbit.entity;

import orbit.math.Vector2f;
import orbit.model.MeshLoader;
import orbit.texture.Texture;

public class Planet extends Entity {
	
	protected float gravitationalAccel;
	protected float radius;
	
	public Planet(float radius, Texture texture, Vector2f position, float gravitationalAccel) {
		super(MeshLoader.createQuadFromCenter(new Vector2f(radius * 2, radius * 2)), texture, position, 0, 1);
		this.radius = radius;
		this.gravitationalAccel = gravitationalAccel;
	}
	
	public void applyGravity(DynamicObject object) {
		Vector2f dir = Vector2f.normalize(Vector2f.sub(position, object.getPos(), null), null);
		object.applyAcceleration(Vector2f.mul(gravitationalAccel, dir, null));
	}
	
	public void checkCollision(DynamicObject object) {
		if (Vector2f.sub(position, object.getPos(), null).magnitude() < radius) {
			object.setHealth(0);
		}
	}
}
