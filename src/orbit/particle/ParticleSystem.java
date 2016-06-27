package orbit.particle;

import java.util.ArrayList;
import java.util.List;

import orbit.math.Vector2f;
import orbit.math.Vector4f;
import orbit.render.AssetLoader;
import orbit.render.Renderer;
import orbit.texture.Texture;

public class ParticleSystem {
	
	private float executionTime = 0;
	private float particlesPerSecond = 0;
	private float particleLife = 0;
	private Vector2f position = new Vector2f();
	private float angle = 0;
	private float particleVelocity = 0;
	private float scale = 0;
	
	private float lifeVariance = 0;
	private float angleVariance = 0;
	private float velocityVariance = 0;
	private float scaleVariance = 0;
	
	private List<Texture> textures = new ArrayList<Texture>();
	private List<Particle> particles = new ArrayList<Particle>();
	
	public ParticleSystem(List<String> textureFiles) {
		for (String tex : textureFiles) {
			textures.add(AssetLoader.createTexture(tex));
		}
	}
	
	public void render(Renderer renderer) {
		for (Particle particle : particles) {
			renderer.render(particle);
		}
	}
	
	public void update(float dt) {
		executionTime += dt;
		int numParticles = (int) Math.round(executionTime * particlesPerSecond);
		if (numParticles > 0) executionTime = 0;
		
		for (int i = 0; i < numParticles; i++) {
			Texture texture = textures.get((int) (Math.random() * textures.size()));
			Vector4f color = new Vector4f(1, 1, 1, 1);
			Vector2f position = Vector2f.copy(this.position, null);
			float velocity = this.particleVelocity + (float) ((Math.random() * 2 - 1) * this.velocityVariance);
			float angle = this.angle + (float) ((Math.random() * 2 - 1) * this.angleVariance);
			float lifetime = this.particleLife + (float) ((Math.random() * 2 - 1) * this.lifeVariance);
			float size = this.scale + (float) ((Math.random() * 2 - 1) * this.scaleVariance);
			Particle particle = new Particle(texture, color, position, velocity, angle, lifetime, size);
			particles.add(particle);
		}
		
		int i = 0;
		while (i < particles.size()) {
			Particle particle = particles.get(i);
			if (particle.isAlive()) {
				particle.update(dt);
				i++;
			} else {
				particles.remove(i);
			}
		}
	}

	public void setParticlesPerSecond(float particlesPerSecond) {
		this.particlesPerSecond = particlesPerSecond;
	}

	public void setParticleLife(float particleLife) {
		this.particleLife = particleLife;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public void setParticleVelocity(float particleVelocity) {
		this.particleVelocity = particleVelocity;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void setLifeVariance(float lifeVariance) {
		this.lifeVariance = lifeVariance;
	}

	public void setAngleVariance(float angleVariance) {
		this.angleVariance = angleVariance;
	}

	public void setVelocityVariance(float velocityVariance) {
		this.velocityVariance = velocityVariance;
	}

	public void setScaleVariance(float scaleVariance) {
		this.scaleVariance = scaleVariance;
	}
}
