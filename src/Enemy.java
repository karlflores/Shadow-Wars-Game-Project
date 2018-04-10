import org.newdawn.slick.SlickException;

public class Enemy extends Sprite{
    //constructor class
    public Enemy(String imageSrc, float x, float y) throws SlickException{
        super(imageSrc,x,y);
    }
    // allows for further abstraction and functions for the enemy player when needed
}
