import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Enemy extends Sprite{
    private boolean isAlive = true;
    //constructor class
    public Enemy(String imageSrc, float x, float y) throws SlickException{
        super(imageSrc,x,y);
    }
    public void killEnemy(){
        this.isAlive = false;
    }
    public boolean getIsAlive(){
        return isAlive;
    }
    /*
    public void update(Input input,int delta) throws SlickException{

    }
    */

}
