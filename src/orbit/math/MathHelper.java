package orbit.math;

public class MathHelper {
	
	public static double clamp_d(double d, double min, double max) {
		if (d < min) return min;
		if (d > max) return max;
		return d;
	}
	
	public static Matrix4f createTransformationMatrix(Vector3f translation, float pitch, float yaw, float roll, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate(pitch, yaw, roll, matrix, matrix);
		Matrix4f.scale(scale, scale, scale, matrix, matrix);
		
		return matrix;
	}
}
