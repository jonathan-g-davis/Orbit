package orbit.entity.collision;

import java.util.ArrayList;
import java.util.List;

import orbit.entity.DynamicObject;
import orbit.math.Rectangle;

public class QuadTree {
	
	private static final int OBJECTS_PER_NODE = 32;
	private static final int MAX_SPLITS = 16;
	private static final int NORTHEAST = 0;
	private static final int NORTHWEST = 1;
	private static final int SOUTHWEST = 2;
	private static final int SOUTHEAST = 3;
	
	private int level;
	private Rectangle bounds;
	private QuadTree[] nodes = new QuadTree[4];
	
	private List<DynamicObject> objects = new ArrayList<DynamicObject>();
	
	public QuadTree(Rectangle bounds) {
		this.level = 0;
		this.bounds = bounds;
	}
	
	public QuadTree(int level, Rectangle bounds) {
		this.level = level;
		this.bounds = bounds;
	}
	
	public void clear() {
		objects.clear();
		
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}
	
	public void split() {
		float x = bounds.getX();
		float y = bounds.getY();
		float width = bounds.getWidth() / 2;
		float height = bounds.getHeight() / 2;
		
		nodes[NORTHWEST] = new QuadTree(level + 1, new Rectangle(x, y, width, height));
		nodes[NORTHEAST] = new QuadTree(level + 1, new Rectangle(x + width, y, width, height));
		nodes[SOUTHWEST] = new QuadTree(level + 1, new Rectangle(x, y + height, width, height));
		nodes[SOUTHEAST] = new QuadTree(level + 1, new Rectangle(x + width, y + height, width, height));
	}
	
	private int getIndex(Rectangle object) {
		int index = -1;
		float xMid = bounds.getX() + bounds.getWidth() / 2;
		float yMid = bounds.getY() + bounds.getHeight() / 2;
		
		boolean top = object.getY() < yMid && object.getY() + object.getHeight() < yMid;
		boolean bottom = object.getY() > yMid && object.getY() + object.getHeight() > yMid;
		boolean left = object.getX() < xMid && object.getX() + object.getWidth() < xMid;
		boolean right = object.getX() > xMid && object.getX() + object.getWidth() > xMid;
		
		if (top) {
			if (left) {
				index = NORTHWEST;
			} else if (right) {
				index = NORTHEAST;
			}
		} else if (bottom) {
			if (left) {
				index = SOUTHWEST;
			} else if (right) {
				index = SOUTHEAST;
			}
		}
		
		return index;
	}
	
	public void insert(DynamicObject object) {
		if (nodes[0] != null) {
			int index = getIndex(object.getBounds());
			
			if (index != -1) {				
				nodes[index].insert(object);
				return;
			}
		}
		
		objects.add(object);
		
		if (objects.size() > OBJECTS_PER_NODE && level + 1 < MAX_SPLITS) {
			if (nodes[0] == null) {
				split();
			}
		
			int i = 0;
			while (i < objects.size()) {
				int index = getIndex(objects.get(i).getBounds());
				
				if (index != -1) {
					DynamicObject o = objects.remove(i);
					
					nodes[index].insert(o);
				} else {
					i++;
				}
			}
		}
	}
	
	public List<DynamicObject> retrieve(List<DynamicObject> list, DynamicObject o) {
		int index = getIndex(o.getBounds());
		
		if (index != -1 && nodes[0] != null) {
			nodes[index].retrieve(list, o);
		}
		
		list.addAll(objects);
		
		return list;
	}
}
