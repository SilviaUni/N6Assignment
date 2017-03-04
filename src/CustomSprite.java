import game2D.Animation;
import game2D.Sprite;


public class CustomSprite extends Sprite {

	private boolean dead;
	public CustomSprite(Animation anim) {
		super(anim);
		this.dead = false;
	}
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
}
