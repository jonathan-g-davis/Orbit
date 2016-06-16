package orbit.math;

public class MathHelper {
	
	public static double clamp_d(double d, double min, double max) {
		if (d < min) return min;
		if (d > max) return max;
		return d;
	}
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, float rotation, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate(rotation, matrix, matrix);
		Matrix4f.scale(scale, scale, matrix, matrix);
		
		return matrix;
	}
}
