package orbit.entity;

import orbit.math.Vector2f;
import orbit.model.Mesh;
import orbit.texture.Texture;

public abstract class Planet extends Entity {

	public Planet(Mesh mesh, Texture texture, Vector2f position, float scale) {
		super(mesh, texture, position, 0, scale);
	}
}
