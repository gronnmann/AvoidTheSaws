package io.github.gronnmann.avoidthesaws.sprites;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class SpriteRotatable extends Sprite{

	
	public int rotateStep = 5;
	
	public boolean rotateLeft = true;
	
	public int rotation = 0;
	
	
	public SpriteRotatable(int x, int y, int width, int height, String img, boolean rotateLeft, int rotateStep) {
		super(x, y, width, height, img);
		this.rotateStep = rotateStep;
		this.rotateLeft = rotateLeft;
		
		if (rotateLeft){
			rotation = 360;
		}
	}
	
	
	
	
	public void update() {
		super.update();
		if (rotateLeft){
			rotation-=rotateStep;
			if (rotation < 0){
				rotation = 360;
			}
		}else{
			rotation+=rotateStep;
			if (rotation > 360) {
				rotation = 0;
			}
		}
		
	}
	
}
