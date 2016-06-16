package orbit.entity;

import orbit.math.Vector2f;
import orbit.model.Mesh;
import orbit.texture.Texture;

public class Bullet extends Entity {

	public Bullet(Mesh mesh, Texture texture, Vector2f position, float rotation, float scale) {
		super(mesh, texture, position, rotation, scale);
	}
}
