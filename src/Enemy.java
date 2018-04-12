import org.newdawn.slick.SlickException;
// Implements the enemy class as an extension of the sprite abstract class
// This currently does nothing but allows for extension
public class Enemy extends Sprite{
    //constructor class
    public Enemy(String imageSrc, float x, float y) throws SlickException{
        super(imageSrc,x,y);
    }
    // override setY() -- ensure that the enemy is located within the bounds of the board
    public void setY(float y){
        if(y>=0){
            super.setY(y);
        }
    }
}
