package orbit.entity;

import orbit.math.Vector2f;
import orbit.model.Mesh;
import orbit.model.MeshLoader;
import orbit.render.AssetLoader;
import orbit.texture.Texture;

public class Spaceship extends DynamicObject {
	
	private static final int SIZE = 64;
	private static final float MASS = 1000;
	private static final Mesh MESH = MeshLoader.createQuadFromCenter(new Vector2f(SIZE, SIZE));
	private static final Texture TEXTURE = AssetLoader.createTexture("spaceship.png");
	
	public Spaceship() {
		super(MESH, TEXTURE, SIZE, SIZE, MASS);
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		if (position.x < 0) position.x = 1280;
		if (position.x > 1280) position.x = 0;
		if (position.y < 0) position.y = 720;
		if (position.y > 720) position.y = 0;
	}
}
