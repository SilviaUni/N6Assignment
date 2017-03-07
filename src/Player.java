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
	
	public void walk() {
		animation = new CustomAnimation();
		animation.loadAnimation(PLAYER_PATH + "walking", 7, "png", 160);
		setAnimation(animation);
	}
	
	public void stand() {
		animation = new CustomAnimation();
		animation.loadAnimation(PLAYER_PATH + "standing", 1, "png", 160);
		setAnimation(animation);
		setVelocityX(0);
	}
	
	public void dying(){
		animation = new CustomAnimation();
		animation.loadAnimation(PLAYER_PATH + "dying", 1, "png", 160);
		setAnimation(animation);
		setVelocityX(0);
	}
	
	public void isJump() {
		animation = new CustomAnimation();
		animation.loadAnimation(PLAYER_PATH + "jump", 10, "png", 160);
		setAnimation(animation);
	}
	
	public void setJumpVelocity(long elapsed) {
		setVelocityY((float)(getVelocityY() + 0.0015 * elapsed));
	}
	
	public void setWalkRightVelocity() {
		setVelocityX(WALKING_SPEED);
	}
	
	public void setWalkLeftVelocity() {
		setVelocityX(-WALKING_SPEED);
	}
	
	public CustomAnimation getCurrentAnimation() {
		return currentAnimation;
	}
}
