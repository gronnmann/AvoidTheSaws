package io.github.gronnmann.avoidthesaws;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import io.github.gronnmann.avoidthesaws.sprites.Bird;
import io.github.gronnmann.avoidthesaws.sprites.Sprite;
import io.github.gronnmann.avoidthesaws.sprites.SpriteRotatable;

public class Game extends JPanel implements Runnable{

	private Image background, playButton;
	
	private int WIDTH, HEIGHT;
	
	
	private int playButtonWidth;
	
	
	public static Bird bird;
	
	private TimerTask t;
	
	private boolean fullScreen = false;
	
	public Game(int width, int height){
		
		playButtonWidth = width/8;
		
		
		background = new ImageIcon("res/sky.png").getImage().getScaledInstance(width, height, Image.SCALE_FAST);
		
		
		playButton = new ImageIcon("res/play.png").getImage().getScaledInstance(playButtonWidth, playButtonWidth, Image.SCALE_FAST);
		
		
		this.WIDTH = width;
		this.HEIGHT = height;
		
		
		bird = new Bird(10, HEIGHT/2-35, height/10, height/10);
		bird.moveY = 1;
		
		GameManager.addSprite(bird);
		
		
		while (GameManager.getActiveSpikes() < GameManager.getSpikes()) {
			GameManager.generateRandomBlade();
		}
		
		t = new TimerTask() {
			
			@Override
			public void run() {
				AvoidTheSaws.game.run();
			}
		};
		new Timer().scheduleAtFixedRate(t, 20, 10);
	}
	
	
	public void paintComponent(Graphics gr){
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D)gr;
		
		g.drawImage(background, 0, 0, this);
		 
		for (Sprite s : GameManager.getSprites()){
			
			
			if (s instanceof SpriteRotatable) {
				
				SpriteRotatable rot = (SpriteRotatable)s;
				
				AffineTransform rotateBackup = g.getTransform();
				
				AffineTransform newTransform = AffineTransform.getRotateInstance(Math.toRadians(rot.rotation), 
						rot.getX()+(rot.width/2), rot.getY()+(rot.height/2));
				
				g.setTransform(newTransform);
				
				g.drawImage(s.getImage(), s.getX(), s.getY(), this);
				
				g.setTransform(rotateBackup);
				
				continue;
			}
			
			
			g.drawImage(s.getImage(), s.getX(), s.getY(), this);
		}
		
		g.setColor(Color.YELLOW);
		
		int scoreFontSize = 36;
		String score = "Score: ";
		String highScore = "High score: ";
		
		
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, scoreFontSize));
		int howManySpaces = (int) Math.log10(GameManager.getLevel()) + 1;
		
		g.drawString(score + GameManager.getLevel(), WIDTH-howManySpaces*scoreFontSize/2-scoreFontSize/2*(score.length()+1), scoreFontSize);
		g.drawString(highScore + AvoidTheSaws.highScore, WIDTH-howManySpaces*scoreFontSize/2-scoreFontSize/2*(highScore.length()+1), 2*scoreFontSize);

		
		
		
		
		//Play menu
		if (GameManager.getGameState() != 1) {
			
			
			g.setColor(Color.WHITE);
			
			Font gameFont = new Font(Font.SANS_SERIF, Font.BOLD, 72);
			g.setFont(gameFont);
			
			String drawnBig = "Avoid the Saws";
			
			g.drawString(drawnBig, WIDTH/2-36*drawnBig.length()/2, HEIGHT/2-playButtonWidth);
			
			
			//START AND EXIT HINTS
			g.setColor(Color.YELLOW);
			g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 36));
			String spaceToStart = "Please press SPACE to start the game.";
			String spaceToExit = "Please press ESCAPE to exit the game.";

			g.drawString(spaceToStart, WIDTH/2-18*spaceToStart.length()/2, HEIGHT/2+playButtonWidth);
			g.drawString(spaceToExit, WIDTH/2-18*spaceToExit.length()/2, HEIGHT/2+playButtonWidth+36);
			
			
			g.drawImage(playButton, WIDTH/2-playButtonWidth/2, HEIGHT/2-playButtonWidth/2, this);
			
			if (GameManager.getGameState() == -1) {
				g.setColor(Color.RED);
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 100));
				String gameOver = "Game Over";
				g.drawString(gameOver, WIDTH/2-50*gameOver.length()/2, HEIGHT/2);
			}
			
			
			
		}
		
		
	}
	
	public void keyReleased(KeyEvent e){
		if (e.getKeyCode() == KeyEvent.VK_SPACE){
			
			
			if (GameManager.getGameState() != 1) {
				GameManager.start();
			}
			
			bird.jump();
		}else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			AvoidTheSaws.saveHighScore();
			System.exit(0);
		}else if (e.getKeyCode() == KeyEvent.VK_F11) {
			fullScreenToggle();
			
		}
	}
	
	public void fullScreenToggle() {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		int w = AvoidTheSaws.DEFAULT_WIDTH;
		int h = AvoidTheSaws.DEFAULT_HEIGHT;
		
		if (!fullScreen) {
			
			w = (int) screenSize.getWidth();
			h = (int) screenSize.getHeight();
			
			AvoidTheSaws.window.setLocation(0, 0);
		}else {
			AvoidTheSaws.window.setLocation((w / 2 - AvoidTheSaws.DEFAULT_WIDTH/2), h / 2 - AvoidTheSaws.DEFAULT_HEIGHT/2);
		}
		
		this.WIDTH = w;
		this.HEIGHT = h;
		
		AvoidTheSaws.WIDTH = w;
		AvoidTheSaws.HEIGHT = h;
		
		AvoidTheSaws.window.setSize(w, h);
		
		background = new ImageIcon("res/sky.png").getImage().getScaledInstance(w, h, Image.SCALE_FAST);
		playButton = new ImageIcon("res/play.png").getImage().getScaledInstance(playButtonWidth, playButtonWidth, Image.SCALE_FAST);
		bird.width = h/10;
		bird.height = h/10;
		bird.rescale();
		
		fullScreen = !fullScreen;
	}
	
	public void keyPressed(KeyEvent e){
		
	}
	
	
	
	
	@Override
	public void run() {
		
		
		if (GameManager.getGameState() != 1) {
			repaint();
			return;
		}
		
		ArrayList<Sprite> toRemove = new ArrayList<>();
		
		for (Sprite s : (ArrayList<Sprite>) GameManager.getSprites().clone()){
			s.update();
			
			if (s.getX() > WIDTH*1.1 || s.getX() < 0){
				toRemove.add(s);
			}
			if (s.getY() > HEIGHT*1.1 || s.getY() < 0){
				toRemove.add(s);
			}
		}
		GameManager.getSprites().removeAll(toRemove);
		
		repaint();
	}

}
