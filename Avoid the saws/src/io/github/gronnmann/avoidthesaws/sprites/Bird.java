package io.github.gronnmann.avoidthesaws.sprites;

import io.github.gronnmann.avoidthesaws.AvoidTheSaws;
import io.github.gronnmann.avoidthesaws.GameManager;

public class Bird extends Sprite{

	public Bird(int x, int y, int width, int height) {
		super(x, y, width, height, "bird");
	}
	
	
	int jumpStrenghtY = -50;
	
	int jumpStrenghtX = 0;
	
	int moveXDef = 3;
	
	public void update(){
		super.update();

		
		if (jumpStrenghtX > 0){
			//jumpStrenghtX--;
			
			moveX = moveXDef;
		}else{
			moveX = 0;
		}
		
		if (jumpStrenghtY != -50){
			jumpStrenghtY--;
			
			
			moveY = (int) (0.3*jumpStrenghtY);
		}else{
			moveY = -10;
		}
		
		
	}
	
	public void rescale(){
		super.rescale();
		moveXDef = AvoidTheSaws.WIDTH/(AvoidTheSaws.DEFAULT_WIDTH/3);
	}
	
	public void processMovement(){
		super.processMovement();
		
		if (x > AvoidTheSaws.WIDTH){
			
			x = 0;
			
			GameManager.triggerNewLevelGeneration();
			
		}
		if (y < 0 || y > AvoidTheSaws.HEIGHT){
			GameManager.die();
		}
		
		for (Sprite s : GameManager.getSprites()) {
			
			if (s.equals(this))continue;
			
			if (s.getBounds().intersects(this.getBounds()) && s.isDeadly()) GameManager.die();
		}
		
	}
	
	public void jump(){
		jumpStrenghtY = 35;
		jumpStrenghtX = (int) (AvoidTheSaws.WIDTH/18.57);
	}
	
}
