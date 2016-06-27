package orbit.entity.weapon;

import orbit.entity.Bullet;
import orbit.map.Map;
import orbit.math.Vector2f;

public class GatlingGun extends Weapon {
	
	private static final int ROUNDS_PER_MINUTE = 2000;
	
	public GatlingGun() {
		clock.setTimer(1 / (ROUNDS_PER_MINUTE / 60f));
	}
	
	private boolean canFire() {
		return clock.checkTimer();
	}
	
	@Override
	public void fire(Vector2f pos, float rotation, Vector2f velocity, Map map) {
		if (canFire()) {
			Vector2f dir = new Vector2f((float) -Math.sin(Math.toRadians(rotation)), (float) -Math.cos(Math.toRadians(rotation)));
			Vector2f.normalize(dir, dir);
			Bullet bullet = new Bullet(pos, rotation, Vector2f.copy(velocity, null), Vector2f.mul(200000, dir, null));
			map.addObject(bullet);
		}
	}
}
