package orbit.map;

import java.util.ArrayList;
import java.util.List;

import orbit.entity.DynamicObject;
import orbit.entity.Planet;
import orbit.entity.collision.QuadTree;
import orbit.math.Circle;
import orbit.math.Rectangle;
import orbit.math.Vector2f;
import orbit.render.AssetLoader;
import orbit.render.Renderer;

public class Map {
	
	private static final int SIZE = 10000;
	
	private Circle bounds = new Circle(0, 0, SIZE);
	private QuadTree quadTree = new QuadTree(new Rectangle(-SIZE / 2, -SIZE / 2, SIZE, SIZE));
	private List<DynamicObject> objects = new ArrayList<DynamicObject>();
	private Planet planet = new Planet(250, AssetLoader.createTexture("earth.png"), new Vector2f(0, 0), 100);
	
	public void addObject(DynamicObject object) {
		if (object != null) {
			objects.add(object);
		}
	}
	
	public void update(float dt) {
		quadTree.clear();
		
		for (DynamicObject object : objects) {
			quadTree.insert(object);
		}
		
		int i = 0;
		List<DynamicObject> collisionlist = new ArrayList<DynamicObject>();
		while (i < objects.size()) {
			collisionlist.clear();
			DynamicObject object = objects.get(i);
			if (object == null || !inBounds(object) || object.getHealth() <= 0) {
				objects.remove(i);
			} else {
				quadTree.retrieve(collisionlist, object);
				
				planet.checkCollision(object);
				for (DynamicObject o : collisionlist) {
					object.checkCollision(o);
				}
				planet.applyGravity(object);
				object.update(dt);
				i++;
			}
		}
	}
	
	public void render(Renderer renderer) {
		renderer.render(planet);
		for (DynamicObject object : objects) {
			if (object != null) {
				object.render(renderer);
			}
		}
	}
	
	private boolean inBounds(DynamicObject object) {
		return Vector2f.sub(object.getPos(), new Vector2f(), null).magnitude() + object.getRadius() < bounds.getRadius();
	}
}
