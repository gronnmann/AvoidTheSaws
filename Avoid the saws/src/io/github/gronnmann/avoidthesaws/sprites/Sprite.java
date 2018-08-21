package io.github.gronnmann.avoidthesaws.sprites;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Sprite {
	public int moveX = 0, moveY = 0;
	public int x, y;
	public int width, height;
	protected Image img;
	
	protected boolean deadly = false;
	
	public Sprite(int x, int y, int width, int height, String img){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.img = new ImageIcon("res/" + img + ".png").getImage();
		this.img = this.img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}
	
	
	public Image getImage(){
		return img;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x, y, width, height);
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public void update(){
		processMovement();
	}
	
	protected void processMovement(){
		x+=moveX;
		y-=moveY;
	}
	public boolean isDeadly() {
		return deadly;
	}
}
