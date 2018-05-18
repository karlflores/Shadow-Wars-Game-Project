import org.newdawn.slick.*;

import java.util.ArrayList;
/**
 *A class that controls the overlay of the game -- including a start screen? the lives, the score on the screen
 */
public class Overlay {
    //
    private ArrayList<Life> lives = new ArrayList<>();

    private final static int LIVES_SPACING = 40;
    private final static int INIT_LIVE_XPOS = 20;
    private final static int TEXT_OFFSETX = 20;
    private final static int TEST_OFFSETY= 738;

    private int score = 0;
    private final static String SCORE_STR = "Score: ";
    private static Overlay overlay;

    /**
     * Constructor: create the overlay object
     * @param numLives : Integer representing the number of lives the player should have
     * @throws SlickException
     */
    public Overlay(int numLives) throws SlickException{
        for(int i = 0; i < numLives; i++){
            // add the sprites
            lives.add(new Life(INIT_LIVE_XPOS+LIVES_SPACING*i));
        }

        overlay = this;
    }

    public void addScore(int add){
        if(add > 0){
            this.score += add;
        }
    }

    /**
     * Method to render the overlay to the screen
     * @param gameContainer : gameContainer of the game
     * @param g : graphics attribute of the game
     * @throws SlickException
     */
    public void render(GameContainer gameContainer, Graphics g) throws SlickException{
        // render the lives
        for(Life life: lives){
            life.render();
        }

        // render the score
        g.drawString(SCORE_STR + score, TEXT_OFFSETX, TEST_OFFSETY);
    }

    /**
     * Method to remove a life from being rendered to the screen
     */
    public void removeLife(){
        // remove the last item in the array list -- so that we remove the right most sprite
        if(lives.size() > 0) {
            int lastIndex = lives.size() - 1;
            lives.remove(lastIndex);
        }
    }

    /**
     * Get the overlay instance
     * @return : the overlay instance
     */
    public static Overlay getOverlay(){
        return overlay;
    }

}
