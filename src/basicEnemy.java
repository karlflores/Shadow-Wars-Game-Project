import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;


public class basicEnemy extends Enemy{
    private static final String ENEMY_IMG_PATH = "res/basic-enemy.png";
    private static final float MOVE_RATE = 0.2f;
    private static final int SCORE = 50;

    public basicEnemy(float x,int delay) throws SlickException{
        super(ENEMY_IMG_PATH,x,SCORE,delay);
    }
    public void update(Input input, int delta) throws SlickException{
        super.update(input, delta);
        // set the Y location of the Enemy
        if(getTimeExists() > getDelay()){
            setY(getY()+MOVE_RATE*delta);
        }
    }


}
