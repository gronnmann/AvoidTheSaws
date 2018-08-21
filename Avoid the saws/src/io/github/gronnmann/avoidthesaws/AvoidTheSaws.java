package io.github.gronnmann.avoidthesaws;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;

public class AvoidTheSaws {
	
	
	protected static JFrame window;
	
	public static Game game;
	
	private static Thread gameThread;
	
	public static int WIDTH = 1300, HEIGHT = 700, DEFAULT_WIDTH = 1300, DEFAULT_HEIGHT = 700;
	
	public static int highScore = 0;
	
	private static File highscoreFile;
	
	public static void main(String[] args){
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		/*WIDTH = (int) screenSize.getWidth();
		HEIGHT = (int) screenSize.getHeight();*/
		
		
		game = new Game(WIDTH, HEIGHT);
		
		gameThread = new Thread(game);
		
		gameThread.start();
		
		
		
		window = new JFrame();
		window.setSize(WIDTH, HEIGHT);
		window.setResizable(false);
		window.setName("Avoid the Saws");
		window.setUndecorated(true);
		
		window.setBackground(Color.WHITE);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(game);
		
		window.addKeyListener(new KeyboardAdapter());
		
		window.setVisible(true);
		

		
		window.setLocation((int) screenSize.getWidth() / 2 - WIDTH/2, (int) screenSize.getHeight() / 2 - HEIGHT/2);	
		
		highscoreFile = new File(System.getProperty("user.home"), "AvoidTheSaws.stats");
		if (!highscoreFile.exists()) {
			try {
				highscoreFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (highscoreFile != null) {
			try {
				BufferedReader hsReader = new BufferedReader(new FileReader(highscoreFile));
				
				String line = hsReader.readLine();
				
				while (line != null) {
					
					if (line.startsWith("highScore:")) {
						highScore = Integer.parseInt(line.split(":")[1]);
					}
					
					line = hsReader.readLine();
				}
				
				

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveHighScore() {
		if (highscoreFile == null)return;
		
		try {
			FileWriter highscoreWriter = new FileWriter(highscoreFile, false);
			highscoreWriter.append("highScore:" + highScore);
			highscoreWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
