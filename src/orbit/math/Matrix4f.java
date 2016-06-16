package orbit.math;

import java.nio.FloatBuffer;

public class Matrix4f implements Matrix {
	
	public float m00;
	public float m01;
	public float m02;
	public float m03;
	public float m10;
	public float m11;
	public float m12;
	public float m13;
	public float m20;
	public float m21;
	public float m22;
	public float m23;
	public float m30;
	public float m31;
	public float m32;
	public float m33;
	
	public Matrix4f() {
		
	}
	
	public void setIdentity() {
		this.m00 = 1;
		this.m11 = 1;
		this.m22 = 1;
		this.m33 = 1;
	}
	
	public static Matrix4f multiply(Matrix4f a, Matrix4f src, Matrix4f dest) {
		Matrix4f result = new Matrix4f();
		
		result.m00 = src.m00 * a.m00 + src.m01 * a.m10 + src.m02 * a.m20 + src.m03 * a.m30;
		result.m01 = src.m00 * a.m01 + src.m01 * a.m11 + src.m02 * a.m21 + src.m03 * a.m31;
		result.m02 = src.m00 * a.m02 + src.m01 * a.m12 + src.m02 * a.m22 + src.m03 * a.m32;
		result.m03 = src.m00 * a.m03 + src.m01 * a.m13 + src.m02 * a.m23 + src.m03 * a.m33;
		result.m10 = src.m10 * a.m00 + src.m11 * a.m10 + src.m12 * a.m20 + src.m13 * a.m30;
		result.m11 = src.m10 * a.m01 + src.m11 * a.m11 + src.m12 * a.m21 + src.m13 * a.m31;
		result.m12 = src.m10 * a.m02 + src.m11 * a.m12 + src.m12 * a.m22 + src.m13 * a.m32;
		result.m13 = src.m10 * a.m03 + src.m11 * a.m13 + src.m12 * a.m23 + src.m13 * a.m33;
		result.m20 = src.m20 * a.m00 + src.m21 * a.m10 + src.m22 * a.m20 + src.m23 * a.m30;
		result.m21 = src.m20 * a.m01 + src.m21 * a.m11 + src.m22 * a.m21 + src.m23 * a.m31;
		result.m22 = src.m20 * a.m02 + src.m21 * a.m12 + src.m22 * a.m22 + src.m23 * a.m32;
		result.m23 = src.m20 * a.m03 + src.m21 * a.m13 + src.m22 * a.m23 + src.m23 * a.m33;
		result.m30 = src.m30 * a.m00 + src.m31 * a.m10 + src.m32 * a.m20 + src.m33 * a.m30;
		result.m31 = src.m30 * a.m01 + src.m31 * a.m11 + src.m32 * a.m21 + src.m33 * a.m31;
		result.m32 = src.m30 * a.m02 + src.m31 * a.m12 + src.m32 * a.m22 + src.m33 * a.m32;
		result.m33 = src.m30 * a.m03 + src.m31 * a.m13 + src.m32 * a.m23 + src.m33 * a.m33;
		
		copy(result, dest);
		
		return result;
	}
	
	public static Matrix4f translate(Vector2f translation, Matrix4f src, Matrix4f dest) {
		Matrix4f translationMatrix = new Matrix4f();
		translationMatrix.setIdentity();
		
		translationMatrix.m03 = translation.x;
		translationMatrix.m13 = translation.y;
		translationMatrix.m23 = 0;
		
		return multiply(translationMatrix, src, dest);
	}
	
	public static Matrix4f rotate(float rotation, Matrix4f src, Matrix4f dest) {
		float rotationRad = (float) Math.toRadians(-rotation);
		
		Matrix4f rotationMatrix = new Matrix4f();
		rotationMatrix.setIdentity();
		rotationMatrix.m00 = (float) Math.cos(rotationRad);
		rotationMatrix.m01 = (float) -Math.sin(rotationRad);
		rotationMatrix.m10 = (float) Math.sin(rotationRad);
		rotationMatrix.m11 = (float) Math.cos(rotationRad);
		
		return multiply(rotationMatrix, src, dest);
	}
	
	public static Matrix4f scale(float scalex, float scaley, Matrix4f src, Matrix4f dest) {
		Matrix4f scaleMatrix = new Matrix4f();
		
		scaleMatrix.m00 = scalex;
		scaleMatrix.m11 = scaley;
		scaleMatrix.m22 = 1;
		scaleMatrix.m33 = 1;
		
		return multiply(scaleMatrix, src, dest);
	}
	
	public static Matrix4f perspective(float fov, float aspect, float near, float far) {
		Matrix4f perspective = new Matrix4f();
		
		float fovy = (float) (2 * Math.atan(Math.tan(Math.toRadians(fov / 2))) / aspect);
		float top, bottom, right, left;
		top = (float) (near * Math.tan(fovy / 2));
		bottom = -top;
		right = top * aspect;
		left = -right;
		
		perspective.m00 = (2 * near) / (right - left);
		perspective.m02 = (right + left) / (right - left);
		perspective.m11 = (2 * near) / (top - bottom);
		perspective.m12 = (top + bottom) / (top - bottom);
		perspective.m22 = -(far + near) / (far - near);
		perspective.m23 = -(2 * far * near) / (far - near);
		perspective.m32 = -1;
		
		return perspective;
	}
	
	public static Matrix4f orthographic(float width, float height) {
		Matrix4f orthographic = new Matrix4f();
		
		float left = 0;
		float right = width;
		float top = 0;
		float bottom = height;
		float far = 1;
		float near = -1;
		
		orthographic.m00 = 2 / (right - left);
		orthographic.m03 = -(right + left) / (right - left);
		orthographic.m11 = 2 / (top - bottom);
		orthographic.m13 = -(top + bottom) / (top - bottom);
		orthographic.m22 = 2 / (far - near);
		orthographic.m23 = - (far + near) / (far - near);
		orthographic.m33 = 1;
		
		return orthographic;
	}
	
	public static void copy(Matrix4f src, Matrix4f dest) {
		if (dest != null) {
			dest.m00 = src.m00;
			dest.m01 = src.m01;
			dest.m02 = src.m02;
			dest.m03 = src.m03;
			dest.m10 = src.m10;
			dest.m11 = src.m11;
			dest.m12 = src.m12;
			dest.m13 = src.m13;
			dest.m20 = src.m20;
			dest.m21 = src.m21;
			dest.m22 = src.m22;
			dest.m23 = src.m23;
			dest.m30 = src.m30;
			dest.m31 = src.m31;
			dest.m32 = src.m32;
			dest.m33 = src.m33;
		}
	}
	
	public static void store(FloatBuffer buffer, Matrix4f src) {
		if (buffer.capacity() < 16) {
			throw new RuntimeException("Buffer must have a minimum capacity of 16");
		}
		buffer.put(src.m00);
		buffer.put(src.m01);
		buffer.put(src.m02);
		buffer.put(src.m03);
		buffer.put(src.m10);
		buffer.put(src.m11);
		buffer.put(src.m12);
		buffer.put(src.m13);
		buffer.put(src.m20);
		buffer.put(src.m21);
		buffer.put(src.m22);
		buffer.put(src.m23);
		buffer.put(src.m30);
		buffer.put(src.m31);
		buffer.put(src.m32);
		buffer.put(src.m33);
	}
	
	@Override
	public String toString() {
		String s = ""
				+ m00 + " " + m01 + " " + m02 + " " + m03 + "\n"
				+ m10 + " " + m11 + " " + m12 + " " + m13 + "\n"
				+ m20 + " " + m21 + " " + m22 + " " + m23 + "\n"
				+ m30 + " " + m31 + " " + m32 + " " + m33;
		return s;
	}
}
