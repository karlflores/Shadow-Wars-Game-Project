import org.newdawn.slick.SlickException;

public class Life extends Sprite{
    private final static int Y_POS = 696;
    private final static String LIVES_SRC_PATH = "res/lives.png";

    public Life(float x) throws SlickException{
        super(LIVES_SRC_PATH,x,Y_POS);
    }

}
