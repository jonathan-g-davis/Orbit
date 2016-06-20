package orbit.map;

import java.util.ArrayList;
import java.util.List;

import orbit.engine.JiveEngine;
import orbit.entity.DynamicObject;
import orbit.entity.Planet;
import orbit.entity.Projectile;
import orbit.entity.collision.QuadTree;
import orbit.math.Rectangle;
import orbit.math.Vector2f;
import orbit.model.MeshLoader;
import orbit.render.AssetLoader;
import orbit.render.Renderer;

public class Map {
	
	private static final int SIZE = 10000;
	
	private Rectangle bounds = new Rectangle(-SIZE / 2, -SIZE / 2, SIZE, SIZE);
	private QuadTree quadTree = new QuadTree(bounds);
	private List<DynamicObject> objects = new ArrayList<DynamicObject>();
	private Planet planet = new Planet(MeshLoader.createQuadFromCenter(new Vector2f(250, 250)), AssetLoader.createTexture("spaceship.png"), new Vector2f(500, 250), 1, 100);
	
	public void addObject(DynamicObject object) {
		if (object != null) {
			objects.add(object);
		}
	}
	
	public void update(float dt) {
		quadTree.clear();
		int count = 0;
		for (DynamicObject object : objects) {
			quadTree.insert(object);
			if (object instanceof Projectile) count++;
		}
		JiveEngine.getWindow().setTitle(count+"");
		
		int i = 0;
		List<DynamicObject> collisionlist = new ArrayList<DynamicObject>();
		while (i < objects.size()) {
			collisionlist.clear();
			DynamicObject object = objects.get(i);
			if (object == null || !inBounds(object) || object.getHealth() <= 0) {
				objects.remove(i);
			} else {
				quadTree.retrieve(collisionlist, object);
				
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
				renderer.render(object);
			}
		}
	}
	
	private boolean inBounds(DynamicObject object) {
		Rectangle oBounds = object.getBounds();
		
		boolean fitX = oBounds.getX() > bounds.getX() && oBounds.getX() + oBounds.getWidth() < bounds.getX() + bounds.getWidth();
		boolean fitY = oBounds.getY() > bounds.getY() && oBounds.getY() + oBounds.getHeight() < bounds.getY() + bounds.getHeight();
		
		return fitX && fitY;
	}
}
