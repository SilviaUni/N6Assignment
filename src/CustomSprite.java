import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import game2D.Animation;
import game2D.Sprite;


public class CustomSprite extends Sprite {

	private boolean dead;
	private boolean turnRight = true;
	
	public CustomSprite(Animation anim) {
		super(anim);
		this.dead = false;
	}
	
	public void draw(Graphics2D g)
    {
    	if (!render) return;

    	if (turnRight) {
    		g.drawImage(getImage(),(int)x+xoff,(int)y+yoff,null);
    	} else {
    		g.drawImage(getImage(), (int)x+xoff + getHeight(), (int)y+yoff, -getWidth(), getHeight(), null);
    	}
    }
	
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public boolean isTurnRight() {
		return turnRight;
	}

	public void setTurnRight(boolean turnRight) {
		this.turnRight = turnRight;
	}
}
