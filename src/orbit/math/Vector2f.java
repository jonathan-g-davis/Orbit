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
	
	public Vector2f(Vector2i vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public float magnitude() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public static Vector2f normalize(Vector2f src, Vector2f dest) {
		Vector2f result = new Vector2f(src.x / src.magnitude(), src.y / src.magnitude());
		copy(result, dest);
		return result;
	}
	
	public static Vector2f add(Vector2f a, Vector2f b, Vector2f dest) {
		Vector2f result = new Vector2f(a.x + b.x, a.y + b.y);
		copy(result, dest);
		return result;
	}
	
	public static Vector2f sub(Vector2f a, Vector2f b, Vector2f dest) {
		Vector2f result = new Vector2f(a.x - b.x, a.y - b.y);
		copy(result, dest);
		return result;
	}
	
	public static Vector2f mul(float a, Vector2f b, Vector2f dest) {
		Vector2f result = new Vector2f(a * b.x, a * b.y);
		copy(result, dest);
		return result;
	}
	
	public static float dot(Vector2f a, Vector2f b) {
		return a.x * b.x + a.y * b.y;
	}

	public static Vector2f copy(Vector2f src, Vector2f dest) {
		if (dest != null) {
			dest.x = src.x;
			dest.y = src.y;
			return dest;
		} else {
			Vector2f clone = new Vector2f();
			clone.x = src.x;
			clone.y = src.y;
			return clone;
		}
	}
	
	@Override
	public String toString() {
		return "Vector3f<"+x+", "+y+">";
	}
}
