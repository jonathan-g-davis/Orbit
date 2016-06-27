package orbit.entity;

import java.util.ArrayList;
import java.util.List;

import orbit.entity.weapon.Weapon;
import orbit.map.Map;
import orbit.math.MathHelper;
import orbit.math.RotationalPID;
import orbit.math.Vector2f;
import orbit.model.Mesh;
import orbit.model.MeshLoader;
import orbit.particle.ParticleSystem;
import orbit.render.AssetLoader;
import orbit.render.Renderer;
import orbit.texture.Texture;

public class Spaceship extends DynamicObject {
	
	private static final int RADIUS = 32;
	private static final int SIZE = 64;
	private static final float MASS = 1000;
	private static final Mesh MESH = MeshLoader.createQuadFromCenter(new Vector2f(SIZE, SIZE));
	private static final Texture TEXTURE = AssetLoader.createTexture("spaceship.png");
	
	private static final float THRUST_FORCE = 100000;
	//private static final float RCS_FORCE = 50000;
	private static final float GYRO_TORQUE = 100000;

	protected ParticleSystem particleSystem;
	protected Weapon weapon;
	protected RotationalPID pid = new RotationalPID();
	
	public Spaceship(Weapon weapon) {
		super(MESH, TEXTURE, RADIUS, MASS);
		this.weapon = weapon;
		
		List<String> textures = new ArrayList<String>();
		textures.add("smoke_1.png");
		textures.add("smoke_2.png");
		textures.add("smoke_3.png");
		particleSystem = new ParticleSystem(textures);
		particleSystem.setParticlesPerSecond(50);
		particleSystem.setParticleVelocity(0);
		particleSystem.setScale(20);
		particleSystem.setParticleLife(2);
		particleSystem.setAngleVariance(15);
		particleSystem.setLifeVariance(1);
		particleSystem.setVelocityVariance(0);
		particleSystem.setScaleVariance(10);
		
		pid.setProportionalConstant(0.7f);
		pid.setDerivativeTime(0.6f);
		pid.setIntegralTime(0.0f);
	}
	
	public void fire(Map map) {
		if (health > 0) {
			Vector2f dir = new Vector2f((float) -Math.sin(Math.toRadians(rotation)), (float) -Math.cos(Math.toRadians(rotation)));
			Vector2f.normalize(dir, dir);
			Vector2f pos = Vector2f.add(Vector2f.mul(SIZE/2, dir, null), position, null);
			weapon.fire(pos, rotation, velocity, map);
		} else {
			return;
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
	public void render(Renderer renderer) {
		particleSystem.render(renderer);
		super.render(renderer);
	}
	
	@Override
	public void update(float dt) {
		Vector2f dir = new Vector2f((float) -Math.sin(Math.toRadians(rotation)), (float) -Math.cos(Math.toRadians(rotation)));
		particleSystem.setPosition(Vector2f.add(Vector2f.mul(-SIZE / 2, dir, null), position, null));
		particleSystem.setAngle(this.rotation);
		particleSystem.update(dt);
		super.update(dt);
	}
}
