import java.util.Random;

import javax.swing.JOptionPane;

import game2D.Sound;
import game2D.Tile;
import game2D.TileMap;

/**
 * Checks and handles collisions with the tile map for the
 * given sprite 's'. Initial functionality is limited...
 * 
 * @param s			The Sprite to check collisions for
 * @param elapsed	How time has gone by
 */

public class Collision  {

	public Collision() {
		

	}
	public static boolean handleTileMapCollisions(long elapsed, TileMap tmap, Player player, float gravity)
	{
		Sound byeBye;
		boolean canJump = true;
		try {
			// This method should check actual tile map collisions. For
			// now it just checks if the player has gone off the bottom
			// of the tile map.

			//try-fail collision: sprite(player) - map (kill me now :( )
			int playerHeight = player.getHeight();
			int playerWidth = player.getWidth();
			float playerX = player.getX();
			float playerY = player.getY();
			int playerGridX = (int)(playerX / tmap.getTileWidth());
			int playerGridY = (int)((playerY + playerHeight) / tmap.getTileHeight());
			Tile tile = tmap.getTile(playerGridX, playerGridY);
			char t = tile.getCharacter();

			//down solid ground under player
			if((playerY + playerHeight) >= tile.getYC() && !nonSolidCharacters(t)){
				player.setY(tile.getYC() - playerHeight);
			}
			if (nonSolidCharacters(t) && nonSolidCharacters(tmap.getTileChar(playerGridX +1, playerGridY))){
				canJump = false;
			}

			//right collision on the ground
			if(!nonSolidCharacters(tmap.getTileChar(playerGridX, playerGridY - 1 )) 
					|| !nonSolidCharacters(tmap.getTileChar(playerGridX + 1, playerGridY - 1 ))
					&& player.getVelocityY() < 0){
				player.setVelocityY(- player.getVelocityY());
				player.setY(playerY + 2);
			}

			//left of the player on the ground
			if ((playerX + playerWidth) >= tmap.getTileXC(playerGridX + 1, playerGridY)
					&& !nonSolidCharacters(tmap.getTileChar(playerGridX + 1, playerGridY))
					&& nonSolidCharacters(t)){
				player.setY(tmap.getTileYC(playerGridX + 1, playerGridY) - playerHeight);
			}

			//right of the player
			if ((playerX + playerWidth > tmap.getTileXC(playerGridX + 1, playerGridY - 1))
					&& player.getVelocityY() >= 0.25) {
				if (!nonSolidCharacters(tmap.getTileChar(playerGridX + 1, playerGridY - 1))){
					player.setX(tmap.getTileXC(playerGridX + 1, playerGridY) - playerWidth);
				}
			}

			// left of the player
			if (playerX <= tile.getXC() + 2) {
				if (!nonSolidCharacters(tmap.getTileChar(playerGridX - 1, playerGridY - 1))) {
					player.setX(tile.getXC() + 2);
				}
			}
		}
		catch (Exception e) {
			byeBye = new Sound("sounds/Bye_Have_a_Great_Time.wav");
			byeBye.start();
			JOptionPane.showMessageDialog(null, "You fall off the map, try again!");
			System.exit(0);
		}
		return canJump;
	}

	public static boolean checkForGround(Player player, TileMap tmap) {
		int tileX = (int)(player.getX() / tmap.getMapWidth());
		int tileY = (int)((player.getY() + player.getHeight()) / tmap.getMapHeight());
		char t = tmap.getTileChar(tileX, tileY);
		if (t == 'b' || t == 'd' || t == 't') {
			return true;
		}
		return false;
	}

	public static boolean nonSolidCharacters (char t){
		return ((t == '.') || (t == 'w'));
	}

	//general box collision
	public static boolean boundingBoxCollision(Player s1, CustomSprite s2) {
		return ((s1.getX() + s1.getWidth() >= s2.getX())
				&& (s1.getX() <= s2.getX() + s2.getWidth())
				&& (s1.getY() + s1.getWidth() >= s2.getY() 
				&& (s1.getY() <= s2.getX() + s2.getHeight())));   	
	}

	//kill monster form the top
	public static boolean collisionTop(Player s1, CustomSprite s2) {
		if (boundingBoxCollision(s1, s2)) {
			float sprite1Bottom = s1.getY() + s1.getHeight();
			float sprite2Top = s2.getY();

			if(sprite1Bottom - sprite2Top <= 5){
				//s2.setDead(true);
				return true;
			}
		}
		return false;
	}
	
	//collision used for the floating spikes
	public static boolean collisionBottom(Player s1, CustomSprite s2) {
		if (boundingBoxCollision(s1, s2)) {
			float sprite1Top = s1.getY();
			float sprite2Bottom = s2.getY()+ s1.getHeight();

			if(sprite1Top <= sprite2Bottom ){
				return true;
			}
		}
		return false;
	}
	
	public static boolean boundingCicleCollision(Player s1, CustomSprite s2) {
		int dx,dy,minimum;
		
		dx = (int) (s1.getX() - s2.getX());
		dy = (int) (s1.getY() - s2.getY());
		minimum = (int) (s1.getRadius() + s2.getRadius());
		
		return (((dx*dx) + (dy*dy)) < (minimum*minimum));
	}
}
