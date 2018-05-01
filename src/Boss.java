import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

public class Boss extends Enemy implements Shootable{
    private static final String ENEMY_IMG_PATH = "res/boss.png";
    private static final float YPOS_MOVE_RATE = 0.2f;
    private static final float XPOS_MOVE_RATE = 0.1f;
    private int xPosTo;
    private int yPosTo;
    private static final int SCORE = 50;
    private static final int[] WAIT_ARR = {5000,2000};
    private int xPos;

}
