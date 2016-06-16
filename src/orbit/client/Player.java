package orbit.client;

import org.lwjgl.glfw.GLFW;

import orbit.entity.Spaceship;
import orbit.input.CursorHandler;
import orbit.input.KeyHandler;
import orbit.math.Vector2f;

public class Player {
	
	private String name = "Player";
	private Spaceship ship;
	
	public Player(Spaceship spaceship) {
		this.ship = spaceship;
	}
	
	public void update() {
		float xPos = (float) CursorHandler.getXpos();
		float yPos = (float) CursorHandler.getYpos();
		Vector2f mouseDir = Vector2f.sub(new Vector2f(xPos, yPos), ship.getPos(), null);
		Vector2f.normalize(mouseDir, mouseDir);
		Vector2f north = new Vector2f(0, -1);
		Vector2f.normalize(north, north);
		float angle = (float) Math.toDegrees(Math.acos((Vector2f.dot(mouseDir, north)) / (mouseDir.magnitude() * north.magnitude())));
		if (xPos > ship.getPos().x) angle *= -1;
		if ((""+angle).equals(""+Float.NaN)) angle = 0;
		ship.setRotation(angle);
		
		if (KeyHandler.isKeyDown(GLFW.GLFW_KEY_W)) {
			Vector2f forceVector = Vector2f.mul(1000000, new Vector2f((float) -Math.sin(Math.toRadians(ship.getRotation())), (float) -Math.cos(Math.toRadians(ship.getRotation()))), null);
			System.out.println(forceVector);
			ship.applyForce(forceVector);
		}
	}
	
	public Spaceship getSpaceship() {
		return ship;
	}
}
