package orbit.entity;

import orbit.math.Rectangle;
import orbit.math.Vector2f;
import orbit.model.Mesh;
import orbit.texture.Texture;

public abstract class DynamicObject extends Entity {
	
	private Rectangle bounds;
	
	protected float health = 100;
	protected Vector2f netForce = new Vector2f();
	protected Vector2f acceleration = new Vector2f();
	protected Vector2f velocity = new Vector2f();
	protected float mass;
	protected float netTorque = 0;
	protected float angAcceleration = 0;
	protected float angVelocity = 0;
	protected float momentOfInertia;
	
	public DynamicObject(Mesh mesh, Texture texture, int width, int height, float mass) {
		super(mesh, texture);
		this.bounds = new Rectangle(position.x - width, position.y - height, position.x + width, position.y + height);
		this.mass = mass;
		this.momentOfInertia = (2.0f/5.0f) * mass * (width * width + height * height);
	}
	
	public void checkCollision(DynamicObject other) {
		if (this == other) return;
		if (other instanceof Projectile) {
			if (!((Projectile) other).isActive()) return;
		}
		Rectangle oBounds = other.getBounds();
		
		float xmin1 = bounds.getXMin();
		float xmax1 = bounds.getXMax();
		float ymin1 = bounds.getYMin();
		float ymax1 = bounds.getYMax();
		float xmin2 = oBounds.getXMin();
		float xmax2 = oBounds.getXMax();
		float ymin2 = oBounds.getYMin();
		float ymax2 = oBounds.getYMax();
		
		boolean xCollide = xmax1 > xmin2 && xmin1 < xmax2;
		boolean yCollide = ymax1 > ymin2 && ymin1 < ymax2;
		if (xCollide && yCollide) {
			this.health = 0;
			other.health = 0;
			System.out.println(this + " " + other);
			System.out.println(xmin1 + " " + xmax1 + "\t" + xmin2 + " " + xmax2);
		}
	}
	
	public void applyForce(Vector2f force) {
		Vector2f.add(force, netForce, netForce);
	}
	
	public void applyAcceleration(Vector2f accel) {
		Vector2f.add(accel, acceleration, acceleration);
	}
	
	public void applyTorque(float newton_meters) {
		netTorque += newton_meters;
	}
	
	public void applyAngAcceleration(float rad_sec2) {
		angVelocity += rad_sec2;
	}
	
	public void update(float dt) {
		bounds.setX(position.x - bounds.getWidth() / 2);
		bounds.setY(position.y - bounds.getHeight() / 2);
		
		Vector2f accel = new Vector2f(netForce.x / mass, netForce.y / mass);
		applyAcceleration(accel);
		netForce.x = 0;
		netForce.y = 0;
		
		Vector2f.add(Vector2f.mul(dt, acceleration, null), velocity, velocity);
		acceleration.x = 0;
		acceleration.y = 0;
		Vector2f.add(Vector2f.mul(dt, velocity, null), position, position);
		
		float angAccel = netTorque / momentOfInertia;
		applyAngAcceleration(angAccel);
		netTorque = 0;

		angVelocity += angAcceleration * dt;
		angAcceleration = 0;
		rotation += Math.toDegrees(angVelocity * dt);
		rotation = rotation % 360f;
	}
	
	public float getHealth() {
		return health;
	}
	
	public Vector2f getNetForce() {
		return netForce;
	}
	
	public Vector2f getAcceleration() {
		return acceleration;
	}
	
	public Vector2f getVelocity() {
		return velocity;
	}
	
	public float getNetTorque() {
		return netTorque;
	}
	
	public float getAngAcceleration() {
		return angAcceleration;
	}
	
	public float getAngVelocity() {
		return angVelocity;
	}
	
	public int getWidth() {
		return (int) bounds.getWidth();
	}
	
	public int getHeight() {
		return (int) bounds.getHeight();
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public float getMass() {
		return mass;
	}
	
	public float getMomentOfInertia() {
		return momentOfInertia;
	}
	
	@Override
	public void setPosition(Vector2f position) {
		bounds.setX(position.x - bounds.getWidth() / 2);
		bounds.setY(position.y - bounds.getHeight() / 2);
		super.setPosition(position);
	}
}
