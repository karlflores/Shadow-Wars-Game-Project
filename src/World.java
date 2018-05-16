import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class that models the world of the game
 */
public class World {
    //BACKGROUND DATA
    private Image background;
    // how many pixels the background moves each update
    private static final float BG_OFFSET_PER_SEC = 0.2f;
    private float backgroundOffset = 0;

    // enemy data
    private boolean gameOver = false;

    // player data
    // CONSTANTS
    private static final int INIT_PLAYER_XPOS = 488;
    private static final int INIT_PLAYER_YPOS = 640;

    private static ArrayList<Sprite> sprites = new ArrayList<>();

    // world instance
    private static World world;

    // timer
    private static int runtime;

    // random generator -- seed based on the current time
    private static Random randomGen = new Random(System.currentTimeMillis());

    // test file
    private static final String WAVES_SRC = "res/waves.txt";

    // constructor
	public World() throws SlickException {

        // Background parameter initialisation
        runtime = 0;

        background = new Image("res/space.png");

        // set the initial background movement to 0 px -- every update we want this to increase by 0.2
        // max movement is 512 px -- then we want to reset this to being 0px

        // create the enemies in the world based on the max number of enemies at the
        // specified locations
        // sprites.add(new Boss(512, 500));
        createEnemies();

        // set the player and its initial location in the world
        sprites.add(new Player(INIT_PLAYER_XPOS,INIT_PLAYER_YPOS));

        // initialise the instance
        world = this;

	}

    /**
     * World getter
     * @return : return the current instance of the world
     */
    public static World getWorld(){
	    return world;
    }

    /**
     * Method to update the world attributes
     * @param input : keyboard input
     * @param delta : time in ms since the last app update
     * @throws SlickException
     */
	public void update(Input input, int delta) throws SlickException{
        // every time the world updates we add delta to the timer
        runtime+=delta;

        // update each sprite -- either update or remove them depending if they exist or not
        for(int i = 0; i < sprites.size(); i++) {
            if (sprites.get(i).getExistState()) {
                sprites.get(i).update(input, delta);
            }else{
                sprites.remove(i);
                i--;
            }
        }

        // variable to store the powerup that is created when an enemy dies
        Powerup powerupCreated = null;
        // loop through each sprite, check for collisions

        for(Sprite this_sprite : sprites) {

            for (Sprite other_sprite : sprites) {
                // System.out.println(this_sprite.toString() + other_sprite.toString());
                // if they are not the same sprite, check if they collide with each other
                if(this_sprite != other_sprite){
                    // now we check for collisions
                    if(this_sprite.makesContact(other_sprite)){

                        // check if an enemy was killed
                        if(this_sprite instanceof playerLaser && other_sprite instanceof Enemy) {

                            // check if the enemy is off the screen -- if it is, then we continue
                            // this is to ensure that the laser does not destroy the enemy
                            if(other_sprite.getY() < 0){
                                continue;
                            }

                            // check if the boss was damaged
                            if (other_sprite instanceof Boss) {
                                ((Boss) other_sprite).decreaseHealth();

                                // destroy the laser that came into contact with the boss
                                this_sprite.setExistState(false);
                            } else {
                                // we kill every other sprite -- set their exist state to false
                                this_sprite.contactSprite(other_sprite);

                                // get the location of death
                                float x = other_sprite.getX();
                                float y = other_sprite.getY();

                                // create a power up on death
                                powerupCreated = createPowerup(x,y);

                            }
                        }else if(this_sprite instanceof Player && other_sprite instanceof Powerup){

                            // activate the power up when the player cones into contact with it
                            ((Powerup) other_sprite).activate(((Player)this_sprite));

                            // set the power up to not existing anymore
                            other_sprite.setExistState(false);

                        // if the enemy collides with the player or the enemy laser collides with the player
                        // we need to decrease the players health
                        }else if(this_sprite instanceof Enemy && other_sprite instanceof Player){

                            // if the player is still in immunity we skip over this player

                            if(((Player)other_sprite).getImmunityTimer() > 0){
                                continue;
                            }

                            // loose a life of the player
                            ((Player) other_sprite).looseLife();

                            // check if a player was destroyed -- therefore end the game if true
                            if(((Player) other_sprite).getNumLives() == 0) {
                                gameOver = true;
                            }
                            this_sprite.setExistState(false);

                        }else if(this_sprite instanceof enemyLaser && other_sprite instanceof Player){

                            if(((Player)other_sprite).getImmunityTimer() > 0){
                                continue;
                            }

                            // loose a life of the player
                            ((Player) other_sprite).looseLife();

                            // check if a player was destroyed -- therefore end the game if true
                            if(((Player) other_sprite).getNumLives() == 0) {
                                gameOver = true;
                            }
                        }

                    }
                }
            }
        }

        // this is for score checking -- we loop through the array list looking for enemies that have been killed
        // we add the score of these enemies to our total score
        for(Sprite this_sprite : sprites) {
            // if we are iterating at the boss sprite -- need to check its health
            if (this_sprite instanceof Boss) {
                int health = ((Boss) this_sprite).getHealth();
                // System.out.println(health);

                // check if the boss has been defeated
                if (health == 0) {

                    int score = ((Boss) this_sprite).getScore();
                    Overlay.getOverlay().addScore(score);

                    // kill the sprite and set the game to being game over
                    this_sprite.setExistState(false);
                }
            } else if(this_sprite instanceof Enemy && !this_sprite.getExistState()) {
                int score = ((Enemy)this_sprite).getScore();
                Overlay.getOverlay().addScore(score);
            }
        }

        // now we need to add all the powerups created

        if(powerupCreated != null) {
            addSprite(powerupCreated);
        }


        // update the background attributes -- this is from the project a sample code
        backgroundOffset += BG_OFFSET_PER_SEC * delta;
        backgroundOffset = backgroundOffset % background.getHeight();
	}

    /**
     * Method to render the world to the screen
     */
	public void render() {

        // Tile the background image -- USING THE CODE IN THE PROJECT A SAMPLE SOLUTION
        for (int i = 0; i < App.SCREEN_WIDTH; i += background.getWidth()) {
            for (int j = -background.getHeight() + (int)backgroundOffset; j < App.SCREEN_HEIGHT; j += background.getHeight()) {
                background.draw(i, j);
            }
        }

        // draw all the sprites in the image
        for(Sprite sprite : sprites){
            sprite.render();
        }
	}

    /**
     * Method to create all the enemies at the start of the game
     * @throws SlickException
     */
    private void createEnemies() throws SlickException{
        try (BufferedReader br = new BufferedReader(new FileReader(WAVES_SRC))){
            String txt;
            while((txt = br.readLine())!= null){

                // skip over the comment lines
                if(txt.contains("#")){
                    txt = br.readLine();
                }
                processInputLine(txt);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Helper method process the line of the enemies file
     * @param line : a line from the enemies.txt file
     * @throws SlickException
     */
    private void processInputLine(String line) throws SlickException{
	    String[] input;
	    String className;
	    int x;
	    int delay;

	    input = line.split(",");

	    // if the length of the input is wrong, then do nothing
	    if(input.length != 3){
	        return;
        }

        // else we assume that the line is in the correct format
        className = input[0];
	    x = Integer.parseInt(input[1]);
	    delay = Integer.parseInt(input[2]);

	    System.out.println(className + " "+ x +" "+ delay);

        // might need to switch to a switch statement here ---
        if(className.equals("BasicEnemy")){
            addSprite(new basicEnemy(x,delay));
        }else if(className.equals("SineEnemy")){
            addSprite(new sineEnemy(x,delay));
        }else if(className.equals("BasicShooter")){
            addSprite(new basicShooter(x,delay));
        }else if(className.equals("Boss")){
            addSprite(new Boss(x, delay));
        }
    }

    /**
     * Method to create a powerup
     * A powerup is created only 1/20 times it is called and there is an equal
     * chance of both powerups being created.
     * @param x : Integer representing the x coord of the powerup
     * @param y : Integer representing the y coord of the powerup
     * @return (Powerup) either null, shield or shotSpreed Powerups
     * @throws SlickException
     */
    private Powerup createPowerup(float x, float y) throws SlickException{
	    int target = 5;
	    int result = World.getRandomInt(0,10);
        // System.out.println(result);
	    // 5% chance of creating a power up
	    if(result == target){
	        result = World.getRandomInt(0,2);
	        // System.out.println(result);
	        // if 0 -- create the shield 1 -- create the shotSpeed powerup
	        if(result == 0){

                return new Shield(x,y);
            }else{
	            return new shotSpeed(x,y);
            }
        }

        return null;
    }

    /**
     * @param min: minimum value
     * @param max: maximum value
     * @return: random integer between min and max (inclusive)
     */
    public static int getRandomInt(int min, int max){
        return min + randomGen.nextInt(max-min+1);
    }

    /**
     * Method to add a sprite to the current sprite array list
     * @param sprite : the sprite to tbe added
     */
    public void addSprite(Sprite sprite){
        sprites.add(sprite);
    }
    // GETTERS AND SETTERS

    public int getTime(){
        return runtime;
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public static void removeSprite(Sprite sprite){
        sprites.remove(sprite);
    }

    public static Sprite getSpriteAtIndex(int index){
        return sprites.get(index);
    }

}
