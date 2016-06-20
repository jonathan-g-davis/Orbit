package orbit.math;

import orbit.engine.Clock;

public class RotationalPID extends PID {
	
	@Override
	public float update(float curPoint) {
		float time = (float) Clock.getTime();
		float dt = time - lastTime;
		lastTime = time;
		
		float e1 = setPoint - curPoint;
		float e2 = setPoint - curPoint + 360;
		float e3 = setPoint - curPoint - 360;
		error = Math.abs(e1) < Math.abs(e2) ? e1 : e2;
		error = Math.abs(error) < Math.abs(e3) ? error : e3;
		i += error * dt;
		d = (error - lastErr) / dt;
		
		lastErr = error;
		return kP * error + kI * i + kD * d;
	}
}
