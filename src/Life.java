import org.newdawn.slick.SlickException;

/**
 * Class representing the Life sprite for the overlay
 */
public class Life extends Sprite{
    private final static int Y_POS = 696;
    private final static String LIVES_SRC_PATH = "res/lives.png";

    /**
     * Constructor : create the life object on the screen
     * @param x : location at which it should be rendered 
     * @throws SlickException
     */
    public Life(float x) throws SlickException{
        super(LIVES_SRC_PATH,x,Y_POS);
    }

}
