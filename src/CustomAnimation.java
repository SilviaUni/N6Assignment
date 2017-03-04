

import game2D.Animation;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Overwrite Animation
 * so it can handle picture by picture animations
 * @author 
 *
 */
public class CustomAnimation extends Animation {

	public CustomAnimation() {
		super();
	}

	public void loadAnimation(String fileName, int frames, String ext, int frameDuration)
    {
    	Image[] animation = new Image[frames];
    	for(int i = 0; i < animation.length; i++) {
    		animation[i] = new ImageIcon(fileName + i + '.' + ext).getImage();
    	}
  
    	for (int i=0; i<animation.length; i++)
    	{
    		addFrame(animation[i], frameDuration);
    	}
    }
}
