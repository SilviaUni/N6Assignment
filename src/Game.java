import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import game2D.*;

// Game demonstrates how we can override the GameCore class
// to create our own 'game'. We usually need to implement at
// least 'draw' and 'update' (not including any local event handling)
// to begin the process. You should also add code to the 'init'
// method that will initialise event handlers etc. By default GameCore

// will handle the 'Escape' key to quit the game but you should
// override this with your own event handler.

/**
 * @author 2221663
 *
 */
@SuppressWarnings("serial")

public class Game extends GameCore 
{
	// Useful game constants
	static int screenWidth = 512;
	static int screenHeight = 416;

	static int GHOST_COUNT = 3;


	float 	lift = 0.005f;
	float	gravity = 0.0001f;

	// Game state flags
	boolean jump = false;
	boolean canJump;
	boolean left = false;
	boolean right = false;
	boolean isStanding = false;
	boolean firstDraw = true;

	// Game resources
	CustomAnimation standing;
	CustomSprite bgImage;
	CustomSprite bgImage2;
	CustomSprite spikes;
	CustomSprite spikes1;
	CustomAnimation iceMonsters;
	CustomSprite iceMonster;
	CustomAnimation finalSpookjes;
	CustomSprite finalSpookje;

	Player	player = null;
	ArrayList<CustomSprite> ghosts = new ArrayList<CustomSprite>();
	int[][] ghostCoordinates = new int[GHOST_COUNT][2];
	ArrayList<CustomSprite> floatingSpikes = new ArrayList<CustomSprite>();

	TileMap tmap = new TileMap();	// Our tile map, note that we load it in init()

	static long total;   
	boolean increaseTotal = true; // The score will be the total time elapsed since a crash


	/**
	 * The obligatory main method that creates
	 * an instance of our class and starts it running
	 * 
	 * @param args	The list of parameters this program might use (ignored)
	 */
	public static void main(String[] args) {

		Game gct = new Game();
		gct.init();
		// Start in windowed mode with the given screen height and width
		gct.run(false,screenWidth,screenHeight);
	}

	/**
	 * Initialise the class, e.g. set up variables, load images,
	 * create animations, register event handlers
	 */
	public void init()
	{        

		CustomSprite s;	// Temporary reference to a sprites
		CustomSprite g;

		// Load the tile map and print it out so we can check it is valid
		tmap.loadMap("maps", "map.txt");

		//load a background image
		CustomAnimation bgIm = new CustomAnimation();
		bgIm.loadAnimation("images/cave", 1, "jpg", 3000); 
		bgImage = new CustomSprite(bgIm);
		bgImage2 = new CustomSprite(bgIm);

		//load a spikes just one frame because it will be just an obstacle rather than new animation
		CustomAnimation sspikes = new CustomAnimation();
		sspikes.loadAnimation("images/spikes", 1, "png", 3000); 
		spikes = new CustomSprite(sspikes);
		spikes1 = new CustomSprite(sspikes);

		standing = new  CustomAnimation();
		standing.loadAnimation("images/Sprites/penguin/standing", 1, "png", 60);

		CustomAnimation monster = new CustomAnimation();
		monster.loadAnimation("images/Sprites/iceMonster/iceMonster", 4, "png", 800);
		iceMonster = new CustomSprite(monster);

		//load spookje animations
		CustomAnimation spookje = new CustomAnimation();
		spookje.loadAnimation("images/Sprites/funnySpookje/finalGhost", 4, "png", 800);
		finalSpookje = new CustomSprite(spookje);

		// Initialise the player with an animation
		player = new Player(standing);

		// Load a single cloud animation
		Animation ca = new Animation();
		ca.addFrame(loadImage("images/floatingSpikes.png"), 1000);

		// Create 5 clouds at random positions off the screen
		// to the right

		for (int c=0; c<3; c++) {
			s = new CustomSprite(ca);
			s.setX(screenWidth + (int)(Math.random()*200.0f));
			s.setY(20 + (int)(Math.random()*150.0f));
			s.setVelocityX(-0.02f);
			s.show();
			floatingSpikes.add(s);
		}
		//initial position of the first monster - ghost
		for (int i=0; i < GHOST_COUNT; i++)
		{
			CustomAnimation ga = new CustomAnimation();
			ga.loadAnimation("images/Sprites/ghost/ghost", 6, "png", 800);
			g = new CustomSprite(ga);
			ghosts.add(g);
		}

		// play music
		Sound soundBack = new Sound("sounds/Grasslands_Theme.wav");
		soundBack.start();
		initialiseGame();

		System.out.println(tmap);
	}

	/**
	 * You will probably want to put code to restart a game in
	 * a separate method so that you can call it to restart
	 * the game.
	 */
	public void initialiseGame() {
		total = 0;

		int ghostHeight = ghosts.get(0).getHeight();
		ghostCoordinates[0][0] = tmap.getTileWidth() * 20;
		ghostCoordinates[0][1] = tmap.getTileHeight() * 12 - ghostHeight;
		ghostCoordinates[1][0] = tmap.getTileWidth() * 50;
		ghostCoordinates[1][1] = tmap.getTileHeight() * 12 - ghostHeight;
		ghostCoordinates[2][0] = tmap.getTileWidth() * 78;
		ghostCoordinates[2][1] = tmap.getTileHeight() * 12 - ghostHeight;
		for (int i = 0; i < ghosts.size(); i++){
			ghosts.get(i).setX(ghostCoordinates[i][0]);
			ghosts.get(i).setY(ghostCoordinates[i][1]);
			ghosts.get(i).show();
		}

		spikes.setX(tmap.getTileWidth() * 70);
		spikes.setY(330);
		spikes.setVelocityX(0);
		spikes.setVelocityY(0);
		spikes.show(); 

		spikes1.setX(tmap.getTileWidth() * 72);
		spikes1.setY(330);
		spikes1.setVelocityX(0);
		spikes1.setVelocityY(0);
		spikes1.show(); 

		iceMonster.setX(900);
		iceMonster.setY(338);
		iceMonster.setVelocityX(0);
		iceMonster.setVelocityY(0);
		iceMonster.show();

		finalSpookje.setX(9300);
		finalSpookje.setY(250);
		finalSpookje.setVelocityX(0);
		finalSpookje.setVelocityY(0);
		finalSpookje.show();

		bgImage.setX(0);
		bgImage.setY(0);
		bgImage.setVelocityX(0);
		bgImage.setVelocityY(0);
		bgImage.show();

		bgImage2.setX(bgImage.getWidth());
		bgImage2.setY(0);
		bgImage2.setVelocityX(0);
		bgImage2.setVelocityY(0);
		bgImage2.show();

		player.setX(50);
		player.setY(230);
		player.setVelocityX(0);
		player.setVelocityY(0.15f);
		player.show();
	}

	/**
	 * Draw the current state of the game
	 */
	public void draw(Graphics2D g)
	{    	
		// Be careful about the order in which you draw objects - you
		// should draw the background first, then work your way 'forward'
		// in order to see where the player is.
		int xo = 0;
		int yo = 0;
		if (player.getX() <= screenWidth / 2){
			xo=0;
		}
		else {
			xo = -(int)player.getX() + screenWidth / 2;
		}

		//draw a background image
		bgImage.draw(g);
		bgImage2.draw(g);
		bgImage.setOffsets(xo, yo);
		bgImage2.setOffsets(xo, yo);

		// Apply offsets to sprites then draw them
		for (CustomSprite s: floatingSpikes) {
			s.setOffsets(xo,yo);
			s.draw(g);
		}

		//draw ghost
		for (CustomSprite ghost: ghosts) {
			if (!ghost.isDead()){
				ghost.setOffsets(xo,yo);
				ghost.draw(g);
				increaseTotal = true;
			}
			else {
				ghost.setOffsets(xo, yo);
				if (ghost.isDead()) {
					if (ghost.getRotation() < 80f) {
						ghost.setY(ghost.getY() + 0.2f);
						ghost.setRotation(ghost.getRotation() + 2);
					}
					ghost.drawTransformed(g); 
					ghost.setAnimationFrame(1);
					ghost.setAnimationSpeed(0);
				}
				else {
					ghost.draw(g);
					player.dying();
				}
			}
		}
		// Apply offsets to player and draw 
		player.setOffsets(xo, yo);
		//player.draw(g);
		player.draw(g);

		spikes.setOffsets(xo, yo);
		spikes.draw(g);
		spikes1.setOffsets(xo, yo);
		spikes1.draw(g);

		if ((Collision.boundingBoxCollision(player, iceMonster)
				&& Collision.boundingCicleCollision(player, iceMonster))== false && !iceMonster.isDead()){
			iceMonster.setOffsets(xo, yo);
			iceMonster.draw(g);
			increaseTotal = true;
		}
		else {
			iceMonster.setRotation(80f);
			iceMonster.drawTransformed(g); 
			iceMonster.setOffsets(xo, yo);
			iceMonster.setAnimationFrame(1);
			iceMonster.setAnimationSpeed(0);
			jump = false;
			if (!iceMonster.isDead()) {
				iceMonster.setDead(true);
				increaseTotal = false;
			}
		}

		finalSpookje.setOffsets(xo, yo);
		finalSpookje.draw(g);

		// Apply offsets to tile map and draw  it
		tmap.draw(g,xo,yo);    

		// Show score and status information
		if (increaseTotal) {
			String msg = String.format("Score: %d", total);
			g.setColor(Color.white);
			g.drawString(msg, getWidth() - 70, 90);
		}
	}

	/**
	 * Update any sprites and check for collisions
	 * 
	 * @param elapsed The elapsed time between this call and the previous call of elapsed
	 */    
	public void update(long elapsed)
	{
		player.setAnimationSpeed(1.0f);

		if (right) {
			player.setWalkRightVelocity();
			player.setTurnRight(true);
		}

		if (left) {
			player.setWalkLeftVelocity();
			player.setTurnRight(false);
		}

		if (right && canJump && !jump) {
			player.walk();
			player.setAnimationSpeed(50);
		}

		if (left && canJump && !jump) {
			player.walk();
			player.setAnimationSpeed(50);

		}
		if (isStanding && canJump) {
			player.stand();
			player.setAnimationSpeed(100);
		}

		if (jump && canJump) {
			Sound jumpSound = new Sound("sounds/jumpSounds.wav");
			jumpSound.start();
			player.isJump();
			player.setAnimationSpeed(1.8f);
			player.setVelocityY(-0.5f);
			jump = false;
		} 

		if(player.getVelocityY() < 0.25f && elapsed != 0){
			player.setJumpVelocity(elapsed);
		}
		//move the map according to the player position
		if (player.getVelocityX() > 0 ){
			bgImage.setVelocityX(-0.02f);
			bgImage2.setVelocityX(-0.02f);
		}
		else if (player.getVelocityX() < 0 ){
			bgImage.setVelocityX(0.02f);
			bgImage2.setVelocityX(0.02f);
		}
		else {
			bgImage.setVelocityX(0);
			bgImage2.setVelocityX(0);
		}
		if(player.getX()- Game.screenWidth / 2 <= 0){
			bgImage.setVelocityX(0);
			bgImage2.setVelocityX(0);
		}

		//switch the backgriound images
		if(player.getX()- Game.screenWidth / 2 >= bgImage2.getX()){
			bgImage.setX(bgImage2.getX()+ bgImage2.getWidth());
		}
		else{
			bgImage.setX(bgImage2.getX()- bgImage2.getWidth());
		}
		if(player.getX()- Game.screenWidth / 2 >= bgImage.getX()){
			bgImage2.setX(bgImage.getX()+ bgImage.getWidth());
		}
		else {
			bgImage2.setX(bgImage.getX()- bgImage.getWidth());
		}

		for (CustomSprite s: floatingSpikes){
			s.update(elapsed);
			if (s.getX() < player.getX() - 500 ){
				s.setX(player.getX() + 250);
				s.update(elapsed);
			}
		}

		for (CustomSprite ghost: ghosts)
			ghost.update(elapsed);

		// Now update the sprites animation and position
		bgImage.update(elapsed);
		bgImage2.update(elapsed);
		player.update(elapsed);
		iceMonster.update(elapsed);
		finalSpookje.update(elapsed);

		// Then check for any collisions that may have occurred
		canJump = Collision.handleTileMapCollisions(elapsed, tmap, player, gravity);
		if (Collision.boundingBoxCollision(player, spikes)){
			player.dying();
			player.isDead();
			Sound pokingSound = new Sound("sounds/screaming.wav");
			pokingSound.start();
			JOptionPane.showMessageDialog(null, "You have been poked! :( ");
			stop();
		}

		//Collision with ice monster
		if (Collision.boundingBoxCollision(player, iceMonster) && !iceMonster.isDead()){
			jump = Collision.boundingBoxCollision(player, iceMonster);
			total += 100;
			Sound nope = new Sound("sounds/no01.wav");
			nope.start();
		}

		for (CustomSprite enemyGhost : ghosts ){
			System.out.println(enemyGhost.isDead());
			if (Collision.collisionTop(player, enemyGhost) && !enemyGhost.isDead()){
				jump = true;
				canJump = true;
				total += 100;
				Sound nope = new Sound("sounds/no01.wav");
				nope.start();
				enemyGhost.setDead(true);
			} else if (Collision.boundingBoxCollision(player, enemyGhost) && !enemyGhost.isDead()) {
				player.setDead(true);
			}
		}

		for (CustomSprite floatinSpikes : floatingSpikes ){
			if (Collision.boundingBoxCollision(player, floatinSpikes)){
				player.dying();
			}
		}

		if (Collision.boundingBoxCollision(player, finalSpookje)){
			jump = Collision.boundingBoxCollision(player, finalSpookje);
			JOptionPane.showMessageDialog(null, "Gratz! You have killed the final Spookje! You have gathered " + total);
		}
	}


	/**
	 * Override of the keyPressed event defined in GameCore to catch our
	 * own events
	 * 
	 *  @param e The event that has been generated
	 */
	public void keyPressed(KeyEvent e) 
	{ 
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_ESCAPE) stop();

		if (key == KeyEvent.VK_UP){
			jump = true;
		} 

		if (key == KeyEvent.VK_LEFT) {
			left = true;
			right = false;
			isStanding = false;
		}

		if (key == KeyEvent.VK_RIGHT) {
			right = true;
			left = false;
			isStanding = false;
		}

		/**if (key == KeyEvent.VK_S) {
			
		}*/
	}

	public void keyReleased(KeyEvent e) { 

		int key = e.getKeyCode();

		// Switch statement instead of lots of ifs...`
		// Need to use break to prevent fall through.
		switch (key)
		{
		case KeyEvent.VK_ESCAPE : stop(); break;
		case KeyEvent.VK_UP     : jump = false; break;
		case KeyEvent.VK_LEFT	: left = false;
		player.stand();
		isStanding = true;
		break;
		case KeyEvent.VK_RIGHT	: right = false; 
		player.stand(); 
		isStanding = true;
		break;
		default :  break;
		}
	}

}
