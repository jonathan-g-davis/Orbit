package orbit.entity;

import java.util.ArrayList;
import java.util.List;

import orbit.math.Vector2f;
import orbit.model.Mesh;
import orbit.model.MeshLoader;
import orbit.particle.ParticleSystem;
import orbit.render.AssetLoader;
import orbit.render.Renderer;
import orbit.texture.Texture;

public class Rocket extends Projectile {
	
	private static final int RADIUS = 16;
	private static final int WIDTH = 8;
	private static final int HEIGHT = 20;
	private static final Mesh MESH = MeshLoader.createQuadFromCenter(new Vector2f(WIDTH, HEIGHT));
	private static final Texture TEXTURE = AssetLoader.createTexture("rocket.png");
	private static final int MASS = 100;
	
	private static final float ACTIVE = 0.5f;
	private static final float DAMAGE = 100f;
	private float fuel = 5;
	private ParticleSystem particleSystem;
	
	public Rocket(Vector2f position, float rotation, Vector2f velocity, Vector2f force) {
		super(MESH, TEXTURE, RADIUS, MASS);
		this.position = position;
		this.rotation = rotation;
		this.velocity = velocity;
		this.applyForce(force);
		Rocket.active = ACTIVE;
		Rocket.damage = DAMAGE;
		
		List<String> textures = new ArrayList<String>();
		textures.add("smoke_1.png");
		textures.add("smoke_2.png");
		textures.add("smoke_3.png");
		particleSystem = new ParticleSystem(textures);
		particleSystem.setParticlesPerSecond(100);
		particleSystem.setParticleVelocity(0);
		particleSystem.setScale(16);
		particleSystem.setParticleLife(2);
		particleSystem.setAngleVariance(15);
		particleSystem.setLifeVariance(1);
		particleSystem.setVelocityVariance(0);
		particleSystem.setScaleVariance(8);
	}
	
	@Override
	public void render(Renderer renderer) {
		super.render(renderer);
		particleSystem.render(renderer);
	}
	
	@Override
	public void update(float dt) {
		if (fuel > 0) {
			float usedFuel = Math.min(fuel, dt);
			fuel -= usedFuel;
			float force = usedFuel * 2500000;
			Vector2f dir = new Vector2f((float) -Math.sin(Math.toRadians(rotation)), (float) -Math.cos(Math.toRadians(rotation)));
			Vector2f.normalize(dir, dir);
			applyForce(Vector2f.mul(force, dir, null));
			
			particleSystem.setPosition(Vector2f.add(Vector2f.mul(-HEIGHT / 2, dir, null), position, null));
			particleSystem.setAngle(this.rotation);
			particleSystem.update(dt);
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
