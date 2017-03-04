import java.util.Random;

import game2D.Animation;

/** 
 * A class to handle all character actions
 * @author
 *
 */

public class Player extends CustomSprite {
	
	public final float WALKING_SPEED = 0.1f;
	CustomAnimation animation;
	CustomAnimation currentAnimation;
	public final String PLAYER_PATH = "images/Sprites/penguin/";

	public Player(Animation anim) {
		super(anim);
	}
	
	public void walkRight() {
		animation = new CustomAnimation();
		animation.loadAnimation(PLAYER_PATH + "walking", 7, "png", 160);
		setAnimation(animation);
		setVelocityX(WALKING_SPEED);
		playAnimation();
	}
	
	public void walkLeft() {
		animation = new CustomAnimation();
		animation.loadAnimation(PLAYER_PATH + "walkingLeft", 7, "png", 160);
		setAnimation(animation);
		setVelocityX(-WALKING_SPEED);
		playAnimation();
	}
	
	public void stand() {
		animation = new CustomAnimation();
		animation.loadAnimation(PLAYER_PATH + "standing", 1, "png", 160);
		setAnimation(animation);
		setVelocityX(0);
		playAnimation();
	}
	
	public void dying(){
		animation = new CustomAnimation();
		animation.loadAnimation(PLAYER_PATH + "dying", 1, "png", 160);
		setAnimation(animation);
		setVelocityX(0);
		playAnimation();
	}
	
	public void isJump() {
		animation = new CustomAnimation();
		animation.loadAnimation(PLAYER_PATH + "jump", 10, "png", 160);
		setAnimation(animation);
		playAnimation();
		
	}
	
	public void setJumpVelocity(long elapsed) {
		setVelocityY((float)(getVelocityY() + 0.0015 * elapsed) );
	}
	
	public CustomAnimation getCurrentAnimation() {
		return currentAnimation;
	}
}
