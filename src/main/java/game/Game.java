package game;

import effects.Effect;
import entities.ennemies.Alien;
import entities.ennemies.Asteroid;
import entities.ennemies.Comet;
import entities.ennemies.Enemy;
import entities.ennemies.Boss;
import entities.towers.BlackHole;
import entities.towers.Missile;
import entities.towers.Sun;
import entities.towers.Tower;
import utilities.Coordinate;
import utilities.GamePanel;
import utilities.ImageLoader;
import utilities.PathPoints;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Thread.sleep;

/* The class begins below the enum. */

/**
 * This 'enum' just creates a new type of data (like int or char).
 * The type is 'game.GameState', and the legal values are shown below.
 * We can create variables of this type, and we can store the 
 * values shown below into those variables.
 * 
 * The alternative would have been to use integers to represent states.
 * For example, we could have said 0=setup, 1=update, etc.  I don't like
 * using integers in this way because I would have to remember what
 * they mean.  By using an enum, I can store values that look like what
 * they represent.  SETUP = The game is setting up, etc.
 */
enum GameState { SETUP, UPDATE, DRAW, WAIT, END }

/**
 * This class represents the playable game.  If you create an
 * object from this class, you have created an instance of the
 * game, complete with JFrame, panel, etc.
 * 
 * The class also has a main method so that the class can
 * be run as an application.
 * 
 * @author basilvetas
 */
public class Game implements Runnable {
    /* Static methods */
    
    /**
     * The entry point for the game.
     * 
     * @param args not used
     */
    public static void main (String[] args) {
        // Just create a game object.  The game constructor
        //   will do the rest of the work.
        
        new Game ();
          
        // Main exits, but our other thread of execution
        //   will keep going.  We could do other work here if
        //   needed.
    }
    
    /* Object fields and methods */
    private Image backdrop;				// background star image		
    private PathPoints line;			// path coordinates
    
    private GamePanel gamePanel;		// gamePanel object
    private GameState state;	   		// The current game state
    
    private int frameCounter;			// keeps track of frame updates
    private long lastTime;				// keeps track of time
    
    private boolean placingBlackHole;	// true if tower is being placed
    private Tower newBlackHole; 		// variable to hold new tower objects
    private double elapsedTime;			// time trackers
    
    private boolean placingMissile;
    private Tower newMissile;

    private boolean placingSun;			// true if tower is being placed
    private Tower newSun; 				// variable to hold new tower objects
    private boolean gameIsOver;			// indicates if game is lost
    private boolean gameIsWon;			// indicates if game is won
    private boolean bossEngaged;			// indicates if the boss is engaged
    public boolean bossDefeated;		// indicates if the boss is deefeated

    int livesCounter; 					// counter for lives left
    public int scoreCounter;					// points the user earns
    public int killsCounter;					// number of enemies destroyed
    
    /* create enemies */
    public List<Enemy> enemies;				// list of enemy objects
    
    /* create towers */
    List<Tower> towers;					// list of tower objects
    
    /* create effects */
    public List<Effect> effects;				// list of effect objects
    
    // You will declare other variables here.  These variables will last for
    //   the lifetime of the game, so don't store temporary values or loop counters
    //   here.

    public String difficulty;           // the difficulty of the game, set by user

    private int[] xPos;
    private int[] yPos;

    /**
     * Constructor:  Builds a thread of execution, then starts it
     * on 'this' object.  This extra thread of execution will be
     * responsible for doing all the work of creating, running,
     * and playing the game.
     * 
     * (Note:  Drawing the screen happens inside of -another-
     * thread of execution controlled by Java.  Fortunately, we
     * don't care, but we are aware that some other threads
     * do exist.)
     */
    public Game () {
        // The game starts in the SETUP state.
        
        state = GameState.SETUP;
        gamePanel = new GamePanel(this);
        // Create a thread of execution and run it.
        
        Thread t = new Thread(this);
        t.start();  // Our run method is now executing!!!
    }
    
    /**
     * The entry point for the second thread of execution.  Our
     * game loop is entirely within this method.
     */
    public void run () {
        // Loop forever, or until the user closes the game window,
        //   whichever comes first.  ;)
        
        while (true) {
            // Test our game state, and do the appropriate action.
            
            if (state == GameState.SETUP) {
                doSetupStuff();
            }
            
            else if (state == GameState.UPDATE) {
                doUpdateTasks();
            }
            
            else if (state == GameState.DRAW) {
                // We don't actually force the drawing to happen.
                //   Instead, we 'request' it of the panel.
                
                gamePanel.repaint();  // redraw screen
                
                // We must wait for the drawing.  It will happen at some time in the near future.
                //   Since we are in an infinite loop, we could just loop until we leave the draw
                //   state.  This would waste battery life on a low power device, so instead
                //   I choose to sleep the current thread for a very short while (so that it
                //   will be briefly inactive).
                
                try { sleep(5); } catch (Exception e) {}
                
                // Do not advance the state here.  The 'draw' method will advance the state after it draws.
            }
            
            else if (state == GameState.WAIT) {
                // Wait 1/10th a second.  This code is not ideal, we'll explore a better way soon.
                
                try { sleep(100); } catch (Exception e) {}
                
                // Drawing is complete, waiting is complete.  It is time to move
                //   the objects in the game again.  Re-enter the UPDATE state.
                
                state = GameState.UPDATE;
            }
            
            else if (state == GameState.END) {
                // Do cleanup if any.  (We don't need to do anything here yet.)
            }
        }
    }

    /**
     * This setup function is called when the game is in the UPDATE state.
     * It just sets up a game, then enters any valid game state.
     */
    private void doSetupStuff () {

        // Do setup tasks
        // Create the JFrame and the JPanel
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.setTitle("Basil Vetas's Tower Defense game");
        f.setContentPane(gamePanel);
        f.pack();
        f.setVisible(true);
        
    	// creates a new ImageLoader object and loads the background image
		ImageLoader loader = ImageLoader.getLoader();
        backdrop = loader.getImage("stars.jpg");

        Object[] choixDifficultes={"FACILE","NORMAL","DIFFICILE"};
        int choix = JOptionPane.showOptionDialog(
                null,
                "Rules of the game:\n" +
                        "1. Place towers on the map to stop enemies from reaching the Earth.\n" +
                        "2. Black holes shoot star dust and are cheaper, Suns shoot sun spots and are faster.\n" +
                        "3. You earn money for stopping enemies, but as the game progresses, new enemies attack.\n" +
                        "4. If you stop 500 enemies you win, but if you lose 10 lives the game is over.",
                "Choisissez la difficulté",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, choixDifficultes, choixDifficultes[0]);

        Object[] choixNiveau={"niveau 1","niveau 2"};
        int choix2 = JOptionPane.showOptionDialog(
                null,
                "Choix du niveau",
                "Choisissez le niveau",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, choixNiveau, choixNiveau[0]);

        // default values
        String niveau = "path_1.txt";
        xPos = new int[]{0, 64, 118, 251, 298, 344, 396, 416, 437, 459, 460, 498, 542, 600, 600, 568, 535, 509, 490, 481, 456, 414, 345, 287, 227, 98, 0};
        yPos = new int[]{329, 316, 291, 189, 163, 154, 165, 186, 233, 344, 364, 415, 444, 461, 410, 396, 372, 331, 226, 195, 151, 117, 105, 117, 143, 244, 280};

        switch (choix2) {
            default:
                niveau = "path_1.txt";
                xPos = new int[]{0, 64, 118, 251, 298, 344, 396, 416, 437, 459, 460, 498, 542, 600, 600, 568, 535, 509, 490, 481, 456, 414, 345, 287, 227, 98, 0};
                yPos = new int[]{329, 316, 291, 189, 163, 154, 165, 186, 233, 344, 364, 415, 444, 461, 410, 396, 372, 331, 226, 195, 151, 117, 105, 117, 143, 244, 280};
                break;
            case 1:
                niveau = "path_2.txt";
                xPos = new int[]{0, 50, 190, 230, 270, 310, 350, 390, 430, 470, 510, 560, 600, 600, 580, 530, 490, 450, 410, 370, 330, 290, 250, 210, 70, 32, 0};
                yPos = new int[]{125, 225, 295, 375, 375, 375, 275, 225, 175, 200, 375, 375, 475, 476, 380, 300, 200, 125, 90, 150, 200, 280, 550, 280, 220, 150, 50};
                break;
        }

        // define ressources/objectives based on difficulty level
        switch (choix) {
            case 0:
                difficulty = "FACILE";
                scoreCounter = 400;		// give the user 400 points to begin
                livesCounter = 20;		// gives the player 20 lives
                System.out.printf("facile");
                break;
            case 2:
                difficulty = "DIFFICILE";
                scoreCounter = 100;		// give the user 100 points to begin
                livesCounter = 5;		// gives the player 5 lives
                System.out.printf("difficile");
                break;
            default:
                difficulty = "NORMAL";
                scoreCounter = 200;		// give the user 200 points to begin
                livesCounter = 10;		// gives the player 10 lives
                System.out.printf("normal");
                break;
        }
        killsCounter = 0;		// begin with 0 kills

        // Reset the frame counter and time 
        frameCounter = 0;
        lastTime = System.currentTimeMillis();
        
        // Use the loader to build a scanner on the path data text file, then build the 
        // path points object from the data in the file.
		ClassLoader myLoader = this.getClass().getClassLoader();
        InputStream pointStream = myLoader.getResourceAsStream(niveau);
        Scanner s = new Scanner (pointStream);
        line  = new PathPoints(s);

        // Fill enemy list with new LinkedList
        enemies = new LinkedList<Enemy>();
        
        // Fill tower list with new LinkedList
        towers = new LinkedList<Tower>();
        
        // Fill effects list with new LinkedList
        effects = new LinkedList<Effect>();
        
        // initialize
        placingBlackHole = false;
        newBlackHole = null;
        
        // initialize
        placingSun = false;
        newSun = null;

        // initialize
        placingMissile = false;
        newMissile = null;
        	
        // initialize
        gameIsOver = false;
    	gameIsWon = false;

        bossEngaged = false;
        bossDefeated = false;
        
        // Change the game state to start the game.
        state = GameState.UPDATE;  // You could also enter the 'DRAW' state.
    }
    
    /**
     * This function is called repeatedly (once per game 'frame').
     * The update function should change the positions of objects in the game.
     * (It could also add new enemies, detect collisions, etc.)  This
     * function is responsible for the 'physics' of the game.
     */
    private void doUpdateTasks() {
    	if(gameIsOver) {
            state = GameState.DRAW;
    		return;
    	}
    	
    	if(gameIsWon) {
            state = GameState.DRAW;
    		return;
    	}
    	
    	// See how long it was since the last frame.
        long currentTime = System.currentTimeMillis();  // Integer number of milliseconds since 1/1/1970.
        elapsedTime = ((currentTime - lastTime) / 1000.0);  // Compute elapsed seconds
        lastTime = currentTime;  // Our current time is the next frame's last time
    	
        /* I think my elapsed time may be wrong */
        
    	// for each tower, interact in this game
    	for(Tower t: new LinkedList<Tower>(towers)) {
    		t.interact(this, elapsedTime);
    		
    	}
        // for each effect, interact in this game      
    	for(Effect e: new LinkedList<Effect>(effects)) {
    		e.interact(this, elapsedTime);
    		if(e.isDone())
    			effects.remove(e);	// add to list that has reached the end	
    	}
    	
    	// Advance each enemy on the path.
    	for(Enemy e: new LinkedList<Enemy>(enemies)) {
    		e.advance();
     		if(e.getPosition().isAtTheEnd())
    		{
    			enemies.remove(e);	// add to list that has reached the end
    			livesCounter--;		// if they have reached the end, reduce lives
    		}
    	}
    	
        // Fill elements in an enemy list
        this.generateEnemies();
        
    	// increments frame counter 
    	frameCounter++;
    	
    	// Place towers if user chooses
    	this.placeBlackHoles();
    	this.placeSuns();
        this.placeMissile();
    	
    	if(livesCounter <= 0) {
            gameIsOver = true;
    		livesCounter = 0;
    	}
    	
    	if((Objects.equals(difficulty, "FACILE") && killsCounter >= 250) ||
                (Objects.equals(difficulty, "NORMAL") && killsCounter >= 500) ||
                (Objects.equals(difficulty, "DIFFICILE") && killsCounter >= 1000)) {

            bossEngaged = true;

            if(bossDefeated) {
                gameIsWon = true;
                killsCounter = 500;
            }
    	}
    	
        // After we have updated the objects in the game, we need to
        //   redraw them.  Enter the 'DRAW' state.
        
        state = GameState.DRAW;
       
        // Careful!  At ANY time after we set this state, the draw method
        //   may execute.  Don't do any further updating.
    }
    
    /**
     * Draws all the game objects, then enters the wait state.
     * 
     * @param g a valid graphics object.
     */
    public void draw(Graphics g) {
        // If we're not in the DRAW state, do not draw!
        
        if (state != GameState.DRAW)
            return;
        	
        // Draw the background image.
        g.drawImage(backdrop, 0, 0, null); 
     
        // Draw the path from the level selection
        g.setColor(new Color (0,76, 153));
        g.fillPolygon(xPos, yPos, 27);
        
        // Draw planet 
        g.setColor(new Color(65,105,225));
        g.fillArc(550, 385, 100, 100, 90, 180);
        g.setColor(Color.GREEN);
        int[] xCor = new int[]{600, 588, 574, 566, 557, 557, 563, 572, 576, 584, 600};
        int[] yCor = new int[]{459, 464, 462, 453, 454, 448, 438, 435, 422, 414, 415};
        g.fillPolygon(xCor, yCor, 11);
        
        // Draw the line along the path.
        //line.drawLine(g);
        
        // draw all enemies before menu bar
    	for(Enemy e: new LinkedList<Enemy>(enemies))
    		e.draw(g);
    	
        // draw all towers in list
        for(Tower t: new LinkedList<Tower>(towers))
        	t.draw(g);
        	
    	// draw all towers in list
    	for(Effect s: new LinkedList<Effect>(effects))
    		s.draw(g);
    	
        // draw menu bar
        g.setColor(Color.BLACK);
        g.fillRect(600, 0, 800, 800);
        g.fillRect(0, 600, 800, 800);
        
        // draw score & life counters to menu bar
        g.setColor(Color.WHITE);
        g.setFont(new Font("Lucidia Sans", Font.BOLD, 16));
        g.drawString("Lives Remaining: " + livesCounter, 605, 100);	// lives counter
        g.drawString("Money Earned: " + scoreCounter, 605, 150);	// score counter
        g.drawString("Enemies Stopped: " + killsCounter, 605, 200);
        g.drawString("Blackhole Cost: 100", 610, 380);				// cost for black hole towers
        g.drawString("Sun Cost: 300", 640, 530);					// cost for sun towers
        g.drawString("Missile tower cost : 60", 610, 680);
        g.setFont(new Font("Lucidia Sans", Font.ITALIC, 28));		
        g.drawString("Planet Defense", 600, 50);					// writes title
        g.drawLine(600, 50, 800, 50);								// underscore
        g.drawString("Towers", 640, 240);							// writes towers
        g.drawLine(620, 240, 780, 240);								// underscore	
        
        // draw box around blackhole icon
        g.setColor(new Color(224, 224, 224));
        g.fillRect(650, 250, 100, 100);
        
        // draw tower in menu area
        BlackHole blackhole = new BlackHole(new Coordinate(700, 300));
        blackhole.draw(g);
        
        // draw box around sun icon
        g.setColor(new Color(224, 224, 224));
        g.fillRect(650, 400, 100, 100);
        
        // draw tower in menu area
        Sun sun = new Sun(new Coordinate(700, 450));
        sun.draw(g);

        // draw box around missile icon
        g.setColor(new Color(224, 224, 224));
        g.fillRect(650, 550, 100, 100);

        // draw tower missile in menu area
        Missile missile = new Missile(new Coordinate(700, 600));
        missile.draw(g);
        
        // draws blackhole object with mouse movements
        if(newBlackHole != null)
        	newBlackHole.draw(g);

        // draws sun object with mouse movements
        if(newSun != null)
        	newSun.draw(g);

        // draws Missile Towers object with mouse movements
        if(newMissile != null)
            newMissile.draw(g);
        
        ImageLoader loader = ImageLoader.getLoader();	
		Image endGame = loader.getImage("game_over.png"); // load game over image
    	
        if(livesCounter <= 0)										// if game is lost
        	g.drawImage(endGame, 0, 0, null);						// draw "game over"

            if((Objects.equals(difficulty, "FACILE") && killsCounter >= 250) ||
                    (Objects.equals(difficulty, "NORMAL") && killsCounter >= 500) ||
                    (Objects.equals(difficulty, "DIFFICILE") && killsCounter >= 1000)) {
                // if game is lost
                g.setFont(new Font("Braggadocio", Font.ITALIC, 90));
                g.drawString("You Win!!!", 10, 250);                    // draw "game over"
            }

		
        // Drawing is now complete.  Enter the WAIT state to create a small
        //   delay between frames.
        
        state = GameState.WAIT;
    }
    
    /**
     * Generates a stream of enemies
     * 
     */
    public void generateEnemies() {
    	// adds enemies to list dependent on how many frames have passed
        if(bossEngaged) {
            enemies.add(new Boss(line.getStart()));
        } else {
            if(frameCounter % 30 == 0) {							    // slow
                enemies.add(new Asteroid(line.getStart()));
            }
            else if(frameCounter % 25 == 0 && frameCounter >= 50) {	    // slow
                enemies.add(new Asteroid(line.getStart()));
            }
            else if(frameCounter % 20 == 0 && frameCounter >= 100) {    // medium
                enemies.add(new Asteroid(line.getStart()));
                enemies.add(new Alien(line.getStart()));
            }
            else if(frameCounter % 15 == 0 && frameCounter >= 150) {	// medium
                enemies.add(new Asteroid(line.getStart()));
                enemies.add(new Alien(line.getStart()));
            }
            else if(frameCounter % 10 == 0 && frameCounter >= 200) {	// fast
                enemies.add(new Asteroid(line.getStart()));
                enemies.add(new Alien(line.getStart()));
                enemies.add(new Comet(line.getStart()));
            }
            else if(frameCounter % 5 == 0 && frameCounter >= 250) {	    // fast
                enemies.add(new Asteroid(line.getStart()));
                enemies.add(new Alien(line.getStart()));
                enemies.add(new Comet(line.getStart()));
            }
        }
    }

    /**
     * Method for placing black holes on the screen
     */
    public void placeBlackHoles() {
        /* I need to make it so you can't place towers on path or off the screen */

        // variable to hold mouse location
        Coordinate mouseLocation = new Coordinate(gamePanel.mouseX, gamePanel.mouseY);

        // moves the tower object as mouse moves
        if(gamePanel.mouseX > 650 && gamePanel.mouseX < 750 &&
                gamePanel.mouseY > 250 && gamePanel.mouseY < 350 &&
                gamePanel.mouseIsPressed && scoreCounter >= 100)
        {	// if mouse is pressed on tower icon, create a new object
            placingBlackHole = true;
            newBlackHole = new BlackHole(mouseLocation);
        }
        else if(gamePanel.mouseX > 0 && gamePanel.mouseX < 600 &&
                gamePanel.mouseY > 0 && gamePanel.mouseY < 600 &&
                gamePanel.mouseIsPressed && placingBlackHole
                && line.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 60)
        {	// if mouse is pressed on game screen, place tower on game screen
            newBlackHole.setPosition(mouseLocation);
            towers.add(new BlackHole(mouseLocation));
            scoreCounter -= 100;
            newBlackHole = null;
            placingBlackHole = false;
        }

        // moves tower object with mouse movements
        if(newBlackHole != null)
        {
            newBlackHole.setPosition(mouseLocation);
        }
    }

    public void placeMissile() {
    	/* I need to make it so you can't place towers on path or off the screen */
    	
    	 // variable to hold mouse location
    	Coordinate mouseLocation = new Coordinate(gamePanel.mouseX, gamePanel.mouseY);
    	
    	// moves the tower object as mouse moves
    	if(gamePanel.mouseX > 650 && gamePanel.mouseX < 750 && 
    		gamePanel.mouseY > 550 && gamePanel.mouseY < 650 && 
    		gamePanel.mouseIsPressed && scoreCounter >= 60)
    	{	// if mouse is pressed on tower icon, create a new object
	    		placingMissile = true;
	    		newMissile = new Missile(mouseLocation);
    	}    
    	else if(gamePanel.mouseX > 0 && gamePanel.mouseX < 600 && 
        	gamePanel.mouseY > 0 && gamePanel.mouseY < 600 &&
        	gamePanel.mouseIsPressed && placingMissile
        	&& line.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 60)
    	{	// if mouse is pressed on game screen, place tower on game screen
	    		newMissile.setPosition(mouseLocation);
	    		towers.add(new Missile(mouseLocation));
	    		scoreCounter -= 60;
	    		newMissile = null;
	    		placingMissile = false;	
    	}
    	
    	// moves tower object with mouse movements
    	if(newMissile != null)
    	{
    		newMissile.setPosition(mouseLocation);
    	}	
    }
    
    /**
     * Method for placing suns on the screen
     */
    public void placeSuns() {
        /* I need to make it so you can't place towers on path or off the screen */

        // variable to hold mouse location
        Coordinate mouseLocation = new Coordinate(gamePanel.mouseX, gamePanel.mouseY);

        // moves the tower object as mouse moves
        if(gamePanel.mouseX > 650 && gamePanel.mouseX < 750 &&
                gamePanel.mouseY > 400 && gamePanel.mouseY < 500 &&
                gamePanel.mouseIsPressed && scoreCounter >= 300)
        {	// if mouse is pressed on tower icon, create a new object
            placingSun = true;
            newSun = new Sun(mouseLocation);
        }
        else if(gamePanel.mouseX > 0 && gamePanel.mouseX < 600 &&
                gamePanel.mouseY > 0 && gamePanel.mouseY < 600 &&
                gamePanel.mouseIsPressed && placingSun
                && line.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 60)
        {	// if mouse is pressed on game screen, place tower on game screen
            newSun.setPosition(mouseLocation);
            towers.add(new Sun(mouseLocation));
            scoreCounter -= 300;
            newSun = null;
            placingSun = false;
        }

        // moves tower object with mouse movements
        if(newSun != null)
        {
            newSun.setPosition(mouseLocation);
        }
    }
}	