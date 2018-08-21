package io.github.gronnmann.avoidthesaws.sprites;

import io.github.gronnmann.avoidthesaws.AvoidTheSaws;

public class Saw extends SpriteRotatable{

	
	public Saw(int x, int y, int width, int height) {
		super(x, y, width, height, "saw");
		deadly = true;
	}
	
	
	public void update(){
		super.update();
	}
	
}
