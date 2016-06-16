package orbit.math;

public class Vector3f implements Vector {
	
	public float x;
	public float y;
	public float z;
	
	public Vector3f() {
		
	}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float dot(Vector3f src) {
		float result = this.x * src.x + this.y * src.y;
		
		return result;
	}
	
	public Vector3f dot(Vector3f src, Vector3f dest) {
		Vector3f result = new Vector3f();
		
		result.x = this.y * src.z - this.z * src.y;
		result.y = this.z * src.x - this.x * src.z;
		result.z = this.x * src.y - this.y * src.x;
		copy(result, dest);
		
		return result;
	}
	
	public static void copy(Vector3f src, Vector3f dest) {
		if (dest != null) {
			dest.x = src.x;
			dest.y = src.y;
			dest.z = src.z;
		}
	}
	
	@Override
	public String toString() {
		return "Vector3f<"+x+", "+y+", "+z+">";
	}
}
