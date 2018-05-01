import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

public class Boss extends Enemy implements Shootable{
    private static final String ENEMY_IMG_PATH = "res/boss.png";
    private static final float YPOS_MOVE_RATE = 0.2f;
    private static final float XPOS_MOVE_RATE = 0.1f;
    private static final int XPOS_MIN = 128;
    private static final int XPOS_MAX = 896;
    private static final int YPOS_THRESH = 72;
    private int xPosTo = 0;

    private static final int SCORE = 5000;
    private static final int TOTAL_SHOOTING_TIME = 3000;
    private static final int SHOOTING_INTERVAL = 200;
    private static final int[] WAIT_ARR = {5000,2000};
    private static final int[] LASER_OFFSET_POS = {-97,-74,74,94};
    private int healthRemaining = 60;

    public Boss(int delay) throws SlickException{
        super(ENEMY_IMG_PATH,0,SCORE,delay);
        setX(World.getRandomInt(XPOS_MIN,XPOS_MAX));

    }

    public void shootLaser(){

    }

}
