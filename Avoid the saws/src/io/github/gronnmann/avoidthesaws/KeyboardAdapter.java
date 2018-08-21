package io.github.gronnmann.avoidthesaws;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardAdapter extends KeyAdapter{

	public void keyPressed(KeyEvent e){
		AvoidTheSaws.game.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e){
		AvoidTheSaws.game.keyReleased(e);
	}
}
