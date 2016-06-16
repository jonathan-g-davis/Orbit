package orbit.map;

import java.util.ArrayList;
import java.util.List;

import orbit.entity.DynamicObject;
import orbit.render.Renderer;

public class Map {
	
	private List<DynamicObject> objects = new ArrayList<DynamicObject>();
	
	public void addObject(DynamicObject object) {
		objects.add(object);
	}
	
	public void update(float dt) {
		for (DynamicObject object : objects) {
			object.update(dt);
		}
	}
	
	public void render(Renderer renderer) {
		for (DynamicObject object : objects) {
			object.render(renderer);
		}
	}
}
