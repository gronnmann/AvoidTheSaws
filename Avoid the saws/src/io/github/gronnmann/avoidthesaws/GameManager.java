package io.github.gronnmann.avoidthesaws;

import java.util.ArrayList;
import java.util.Random;

import io.github.gronnmann.avoidthesaws.sprites.Bird;
import io.github.gronnmann.avoidthesaws.sprites.Saw;
import io.github.gronnmann.avoidthesaws.sprites.Sprite;

public class GameManager {
	private static ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	
	private static int level = 1;
	
	private static int spikes = 5, defSpikes = 5;
	
	public static int gameState = 0;
	
	
	public static ArrayList<Sprite> getSprites(){
		return sprites;
	}
	public static void addSprite(Sprite s){
		sprites.add(s);
	}
	
	public static void removeSprite(Sprite s){
		sprites.remove(s);
	}
	
	public static void triggerNewLevelGeneration(boolean startup) {
		
		
		if (!startup) {
			level++;
		}else {
			level = 1;
		}
		
		if (level > AvoidTheSaws.highScore) {
			AvoidTheSaws.highScore = level;
		}
		
		for (Sprite s : sprites) {
			if (!(s instanceof Bird)) {
				s.x = -1000;
				s.y = -1000;
			}
		}
		
		
		if (spikes < 5) {
			spikes++;
		}
		
		
		for (int i = 0; i<=spikes; i++) {
			generateRandomBlade();
		}
		
	}
	
	public static void triggerNewLevelGeneration() {
		triggerNewLevelGeneration(false);
	}
	
	public static int getLevel() {
		return level;
	}
	
	public static int getGameState() {
		return gameState;
	}
	
	public static int getSpikes() {
		return spikes;
	}
	public static int getActiveSpikes() {
		int ac = 0;
		for (Sprite s : sprites) {
			if (s instanceof Saw) {
				ac++;
			}
		}
		return ac;
	}
	
	public static void restartGame() {
		spikes = 3;
		
	}
	
	
	public static void start() {
		triggerNewLevelGeneration(true);
		gameState = 1;
	}
	
	public static void die() {
		gameState = -1;
		Game.bird.x = 10;
		Game.bird.y = AvoidTheSaws.HEIGHT/2-35;
		
		if (level > AvoidTheSaws.highScore) {
			AvoidTheSaws.highScore = level;
			AvoidTheSaws.saveHighScore();
		}
		
		level = 1;
		spikes = defSpikes;
		
		
	}
	
	
	public static Saw generateRandomBlade() {
		Random r = new Random();
		
		int posX = r.nextInt(AvoidTheSaws.WIDTH);
		int posY = r.nextInt(AvoidTheSaws.HEIGHT);
		
		/*while (Sprite.collidesWithSprite(posX, posY)){
			posX = r.nextInt(AvoidTheSaws.WIDTH);
			posY = r.nextInt(AvoidTheSaws.HEIGHT);
		}*/
		
		
		while (posX < AvoidTheSaws.WIDTH/10) {
			posX = r.nextInt(AvoidTheSaws.WIDTH);
		}
		
		while (posX < AvoidTheSaws.WIDTH/5  && AvoidTheSaws.HEIGHT/2+AvoidTheSaws.HEIGHT/3 > posY && posY > AvoidTheSaws.HEIGHT/2-AvoidTheSaws.HEIGHT/3) {
			posY = r.nextInt(AvoidTheSaws.HEIGHT);
		}
		
		
		int size = (r.nextInt(2)+1)*AvoidTheSaws.HEIGHT/12;
		
		Saw saw = new Saw(posX, posY, size, size);
		
		addSprite(saw);
		
		return saw;
	}
}
