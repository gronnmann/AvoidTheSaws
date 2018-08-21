package io.github.gronnmann.avoidthesaws.sprites;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class SpriteRotatable extends Sprite{

	public SpriteRotatable(int x, int y, int width, int height, String img) {
		super(x, y, width, height, img);
	}
	
	public int rotation = 0;
	
	public void update() {
		super.update();
		rotation+=5;
		if (rotation > 360) {
			rotation = 0;
		}
	}
	
}
