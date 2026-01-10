package game.time;

public class GameTime {
	
	public int minute = 0;
	public int hour = 0;
	public int day = 0;
	
	private double timeCounter = 0;
	
	// 1s IN REAL = 1m IN GAME
	public static final double REAL_SEC_PER_GAME_MIN = 1.0;
	
	public void update(double delta) {
		timeCounter += delta;
		
		if(timeCounter >= REAL_SEC_PER_GAME_MIN) {
			minute++;
			timeCounter = 0;
			if(minute >= 60) {
				minute =0;
				hour++;
				if(hour>=24) {
					hour =0;
					day++;
				}
			}
		}
	}

}
