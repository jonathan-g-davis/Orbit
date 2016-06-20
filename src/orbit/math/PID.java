package orbit.math;

import orbit.engine.Clock;

public class PID {
	
	protected float setPoint = 0;
	protected float kP = 0;
	protected float kI = 0;
	protected float kD = 0;
	
	protected float error = 0;
	protected float lastErr = 0;
	protected float lastTime = (float) Clock.getTime();
	protected float i = 0;
	protected float d = 0;
	
	public PID() {
		
	}
	
	
	
	public PID(float setPoint, float kP, float kI, float kD) {
		super();
		this.setPoint = setPoint;
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
	}

	public float update(float curPoint) {
		float time = (float) Clock.getTime();
		float dt = time - lastTime;
		lastTime = time;
		
		error = setPoint - curPoint;
		i += error * dt;
		d = (error - lastErr) / dt;
		
		lastErr = error;
		return kP * error + kI * i + kD * d;
	}

	public void setSetPoint(float setPoint) {
		this.setPoint = setPoint;
	}



	public void setProportionalConstant(float kP) {
		this.kP = kP;
	}

	public void setIntegralTime(float tI) {
		this.kI = tI;
	}



	public void setDerivativeTime(float tD) {
		this.kD = tD;
	}
}
