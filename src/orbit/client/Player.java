package orbit.client;

import org.lwjgl.glfw.GLFW;

import orbit.entity.Spaceship;
import orbit.input.CursorHandler;
import orbit.input.KeyHandler;
import orbit.map.Map;
import orbit.math.Vector2f;
import orbit.render.Camera;

public class Player {
	
	private String name = "Player";
	private Spaceship ship;
	private Camera camera = new Camera();
	
	public Player(Spaceship spaceship) {
		this.ship = spaceship;
	}
	
	public void update(Map map) {
		float xPos = (float) CursorHandler.getXpos() + camera.getPos().x;
		float yPos = (float) CursorHandler.getYpos() + camera.getPos().y;
		Vector2f mouseDir = Vector2f.sub(new Vector2f(xPos, yPos), ship.getPos(), null);
		Vector2f.normalize(mouseDir, mouseDir);
		Vector2f north = new Vector2f(0, -1);
		Vector2f.normalize(north, north);
		float angle = (float) Math.toDegrees(Math.acos((Vector2f.dot(mouseDir, north)) / (mouseDir.magnitude() * north.magnitude())));
		if (xPos > ship.getPos().x) angle *= -1;
		if ((""+angle).equals(""+Float.NaN)) angle = 0;
		ship.rotateToAngle(angle);
		
		if (KeyHandler.isKeyDown(GLFW.GLFW_KEY_W)) {
			ship.thrust();
		}
		if (KeyHandler.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
			ship.fire(map);
		}
		if (ship.getHealth() > 0) camera.setPos(Vector2f.sub(ship.getPos(), new Vector2f(640, 360), null));
		else camera.setPos(new Vector2f(0, 0));
	}
	
	public String getName() {
		return name;
	}
	
	public Spaceship getSpaceship() {
		return ship;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public float getHealth() {
		return ship.getHealth();
	}
}
