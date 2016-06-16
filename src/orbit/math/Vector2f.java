package orbit.math;

public class Vector2f implements Vector {
	
	public float x;
	public float y;

	public Vector2f() {
		
	}
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "Vector3f<"+x+", "+y+">";
	}
}
