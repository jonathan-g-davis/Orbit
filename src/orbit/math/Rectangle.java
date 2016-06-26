package orbit.math;

public class Rectangle {
	
	private float x;
	private float y;
	private float width;
	private float height;
	private Vector2f[] vertices = new Vector2f[4];
	
	public Rectangle(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new Vector2f();
		}
		recalculateVertices();
	}

	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}

	public void setX(float x) {
		this.x = x;
		recalculateVertices();
	}

	public void setY(float y) {
		this.y = y;
		recalculateVertices();
	}

	public void setWidth(float width) {
		this.width = width;
		recalculateVertices();
	}

	public void setHeight(float height) {
		this.height = height;
		recalculateVertices();
	}
	
	public Vector2f[] getVertices() {
		return vertices;
	}
	
	private void recalculateVertices() {
		vertices[0].x = x + width;
		vertices[0].y = y;
		vertices[1].x = x;
		vertices[1].y = y;
		vertices[2].x = x;
		vertices[2].y = y + height;
		vertices[3].x = x + width;
		vertices[3].y = y + height;
	}
	
	public float getXMin() {
		float x = vertices[0].x;
		for (int i = 1; i < vertices.length; i++) {
			if (vertices[i].x < x) x = vertices[i].x;
		}
		return x;
	}
	
	public float getYMin() {
		float y = vertices[0].y;
		for (int i = 1; i < vertices.length; i++) {
			if (vertices[i].y < y) y = vertices[i].y;
		}
		return y;
	}
	
	public float getXMax() {
		float x = vertices[0].x;
		for (int i = 1; i < vertices.length; i++) {
			if (vertices[i].x > x) x = vertices[i].x;
		}
		return x;
	}
	
	public float getYMax() {
		float y = vertices[0].y;
		for (int i = 1; i < vertices.length; i++) {
			if (vertices[i].y > y) y = vertices[i].y;
		}
		return y;
	}
	
	@Override
	public String toString() {
		return "Rectangle ["+x+", "+y+"] ["+width+", "+height+"]";
	}
}
