package orbit.entity;

import orbit.math.Vector3f;
import orbit.model.Material;
import orbit.model.Mesh;

public class Entity {
	
	private Mesh model;
	private Material material;
	private Vector3f position;
	private float pitch;
	private float yaw;
	private float roll;
	private float scale;
	
	public Entity(Mesh mesh, Material material) {
		this.model = mesh;
		this.material = material;
		this.position = new Vector3f();
		this.scale = 1;
	}
	
	public Entity(Mesh mesh, Material material, Vector3f position, float pitch, float yaw, float roll, float scale) {
		this.model = mesh;
		this.material = material;
		this.position = position;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
		this.scale = scale;
	}
	
	public Mesh getMesh() {
		return model;
	}
	
	public Material getMaterial() {
		return material;
	}

	public Vector3f getPos() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	public float getScale() {
		return scale;
	}
}
