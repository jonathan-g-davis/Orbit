package orbit.entity.weapon;

import orbit.entity.Rocket;
import orbit.map.Map;
import orbit.math.Vector2f;

public class RocketLauncher extends Weapon {
	
	private static final float ROUNDS_PER_MINUTE = 25;
	
	public RocketLauncher() {
		clock.setTimer(1 / (ROUNDS_PER_MINUTE / 60f));
	}
	
	private boolean canFire() {
		return clock.checkTimer();
	}
	
	@Override
	public void fire(Vector2f pos, float rotation, Vector2f velocity, Map map) {
		if (canFire()) {
			Vector2f dir = new Vector2f((float) -Math.sin(Math.toRadians(rotation)), (float) -Math.cos(Math.toRadians(rotation)));
			Rocket rocket = new Rocket(pos, rotation, Vector2f.copy(velocity, null), Vector2f.mul(1000000, dir, null));
			map.addObject(rocket);
		}
	}
}
