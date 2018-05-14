/**
 * Sample Project for SWEN20003: Object Oriented Software Development 2018
 * by Eleanor McMurtry, University of Melbourne
 */

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

/**
 * Main class for the game.
 * Handles initialisation, input and rendering.
 */
public class App extends BasicGame {
 	/** screen width, in pixels */
    public static final int SCREEN_WIDTH = 1024;
    /** screen height, in pixels */
    public static final int SCREEN_HEIGHT = 768;

    private static final int GAME_SPEEDUP = 5;
    
    private World world;
    private Overlay overlay;
    private static int num_speedup = 0;

    public App() {    	
        super("Shadow Wars");
    }

    @Override
    public void init(GameContainer gc)
    		throws SlickException {
    	world = new World();
    	overlay = new Overlay(Player.getMaxNumLives());

    }

    /** Update the game state for a frame.
     * @param gc The Slick game container object.
     * @param delta Time passed since last frame (milliseconds).
     */
    @Override
    public void update(GameContainer gc, int delta)
    		throws SlickException {
        // Get data about the current input (keyboard state).
        Input input = gc.getInput();

        // if the key is pressed, increment the speedup
        if(input.isKeyPressed(Input.KEY_U)){
            num_speedup++;
        }
        if(input.isKeyDown(Input.KEY_U) && num_speedup < 5){

            // speed up the game if the key is pressed
            world.update(input, delta*GAME_SPEEDUP);

        }else{
            world.update(input, delta);

            //System.out.println(num_speedup);
        }

        // exit the game if the game is determined to be over
        if(world.isGameOver()){
            gc.exit();
        }

        // if escape is hit we exit the game
        if(input.isKeyDown(Input.KEY_ESCAPE)){
            gc.exit();
        }
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param gc The Slick game container object.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(GameContainer gc, Graphics g)
    		throws SlickException {
    	world.render();
    	overlay.render(gc,g);
    }

    /** Start-up method. Creates the game and runs it.
     * @param args Command-line arguments (ignored).
     */
    public static void main(String[] args)
    		throws SlickException {
        AppGameContainer app = new AppGameContainer(new App());
        app.setShowFPS(true);
        app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
        app.start();
    }

}