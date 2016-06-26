package orbit.entity.weapon;

import orbit.engine.Clock;
import orbit.map.Map;
import orbit.math.Vector2f;

public abstract class Weapon {
	
	protected Clock clock = new Clock();
	
	public abstract void fire(Vector2f pos, float rotation, Vector2f velocity, Map map);
}
