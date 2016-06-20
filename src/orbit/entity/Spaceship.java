package orbit.entity;

import orbit.math.MathHelper;
import orbit.math.RotationalPID;
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
	
	private static final float THRUST_FORCE = 200000;
	private static final float RCS_FORCE = 50000;
	private static final float GYRO_TORQUE = 100000;

	protected RotationalPID pid = new RotationalPID();
	
	public Spaceship() {
		super(MESH, TEXTURE, SIZE, SIZE, MASS);
		
		pid.setProportionalConstant(0.7f);
		pid.setDerivativeTime(0.6f);
		pid.setIntegralTime(0.0f);
	}
	
	public Projectile fire() {
		if (health > 0) {
			Vector2f dir = new Vector2f((float) -Math.sin(Math.toRadians(rotation)), (float) -Math.cos(Math.toRadians(rotation)));
			Vector2f pos = Vector2f.add(Vector2f.mul(SIZE/2, dir, null), position, null);
			Bullet bullet = new Bullet(pos, rotation + 90, Vector2f.copy(velocity, null), Vector2f.mul(200000, dir, null));
			return bullet;
		} else {
			return null;
		}
	}
	
	public void thrust() {
		Vector2f forceVector = new Vector2f((float) -Math.sin(Math.toRadians(rotation)), (float) -Math.cos(Math.toRadians(rotation)));
		applyForce(Vector2f.mul(THRUST_FORCE, forceVector, null));
	}
	
	public void rotateToAngle(float angle) {
		pid.setSetPoint(angle);
		float output = pid.update(rotation);
		float torque = MathHelper.clamp_f(output * momentOfInertia, -GYRO_TORQUE, GYRO_TORQUE);
		applyTorque(torque);
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		/*if (position.x < 0 && velocity.x < 0) velocity.x*=-1;
		if (position.x > 1280 && velocity.x > 0) velocity.x*=-1;
		if (position.y < 0 && velocity.y < 0) velocity.y*=-1;
		if (position.y > 720 && velocity.y > 0) velocity.y*=-1;*/
		
	}
}
