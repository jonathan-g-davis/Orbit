package orbit.entity;

import orbit.math.Vector2f;
import orbit.model.Mesh;
import orbit.texture.Texture;

public class Planet extends Entity {
	
	protected float gravitationalAccel;
	
	public Planet(Mesh mesh, Texture texture, Vector2f position, float scale, float gravitationalAccel) {
		super(mesh, texture, position, 0, scale);
		this.gravitationalAccel = gravitationalAccel;
	}
	
	public void applyGravity(DynamicObject object) {
		Vector2f dir = Vector2f.normalize(Vector2f.sub(position, object.getPos(), null), null);
		object.applyAcceleration(Vector2f.mul(gravitationalAccel, dir, null));
	}
}
