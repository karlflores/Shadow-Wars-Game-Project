import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class World {
    //BACKGROUND DATA
    private Image background;
    // how many pixels the background moves each update
    private static final float BG_OFFSET_PER_SEC = 0.2f;
    private float backgroundOffset = 0;

    // enemy data
    private static final int NUM_ENEMIES = 8;
    private boolean gameOver = false;
    private int enemiesKilled = 0;
    //CONSTANTS

    private static final int ENEMY_SPACING_PX = 128;
    private static final int START_ENEMY_XPOS = 32;
    private static final int START_ENEMY_YPOS = 32;

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

        //for(int i = 0;i < NUM_ENEMIES;i++){
        //for(int i =0 ; i < 1; i++){
            // create each enemy and space the enemies out on screen based on an initial position
            // and an px-spacing between each enemy
            //sprites.add(new basicEnemy(START_ENEMY_XPOS+(ENEMY_SPACING_PX)*i,0));
        sprites.add(new Boss(512, 500));
        //}
        // System.out.println(sprites.size());
        // createEnemies();
        // System.out.println(sprites.size());
        // sprites.add(new sineEnemy(600,1000));
        // set the player and its initial location in the world
        sprites.add(new Player(INIT_PLAYER_XPOS,INIT_PLAYER_YPOS));

        // initialise the instance
        world = this;


	}

    public static World getWorld(){
	    return world;
    }

	// override the sprite method
	public void update(Input input, int delta) throws SlickException{
        // every time the world updates we add delta to the timer
        runtime+=delta;
        // Update the Background Parameters
        /*
          every time we want to update we want to move the background down at
          rate of 0.2px/ms -- the max that the background can move is 512px
          before it resets to 0
          */
        // update each sprite -- either update or remove them depending if they exist or not
        for(int i = 0; i < sprites.size(); i++) {
            if (sprites.get(i).getExistState()) {
                sprites.get(i).update(input, delta);
            }else{
                sprites.remove(i);
                i--;
            }
        }


        //System.out.println("updated");
        // loop through each sprite, check for collisions
        for(Sprite this_sprite : sprites) {
            for (Sprite other_sprite : sprites) {
                // if they are not the same sprite, check if they collide with each other
                if(this_sprite != other_sprite){
                    if(this_sprite.makesContact(other_sprite)){
                        /*
                        // if two enemies collide we don't want to delete them
                        if(this_sprite instanceof Enemy && other_sprite instanceof Enemy){
                            continue;
                        }

                        // remove both sprites from the game if they collide with each other
                        if(this_sprite instanceof playerLaser && other_sprite instanceof Player ||
                            this_sprite instanceof Player && other_sprite instanceof playerLaser) {
                            continue;
                        }
                        // if the two sprites are a playerLaser and enemy laser continue
                        if(this_sprite instanceof playerLaser && other_sprite instanceof enemyLaser ||
                                this_sprite instanceof enemyLaser && other_sprite instanceof playerLaser){
                            continue;
                        }
                        if(this_sprite instanceof enemyLaser && other_sprite instanceof Enemy ||
                                this_sprite instanceof Enemy && other_sprite instanceof enemyLaser) {
                            continue;
                        }
                         */

                        // check if an enemy was killed
                        if(this_sprite instanceof playerLaser && other_sprite instanceof Enemy ||
                                this_sprite instanceof Enemy && other_sprite instanceof playerLaser) {
                            this_sprite.contactSprite(other_sprite);
                            enemiesKilled++;
                        }else if(this_sprite instanceof Enemy && other_sprite instanceof Player ||
                                this_sprite instanceof enemyLaser && other_sprite instanceof Player){
                            // check if a player was destroyed -- therefore end the game if true
                            gameOver = true;
                        }

                    }
                }
            }
        }

        // checks if the game is over by checking how many enemies have been killed
        if(enemiesKilled == NUM_ENEMIES) {
            //gameOver = true;
        }

        // update the background attributes -- this is from the project a sample code
        backgroundOffset += BG_OFFSET_PER_SEC * delta;
        backgroundOffset = backgroundOffset % background.getHeight();
	}
	
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

    // getter method that returns if the game is over
    public boolean isGameOver(){
	    return gameOver;
    }
    // helper method to return the number of enemies currently killed in a game

    public void addSprite(Sprite sprite){
	    sprites.add(sprite);
    }

    public static void removeSprite(Sprite sprite){
	    sprites.remove(sprite);
    }

    public static Sprite getSpriteAtIndex(int index){
	    return sprites.get(index);
    }

    public int getTime(){
        return runtime;
    }

    public static int getRandomInt(int min, int max){
	    return min + randomGen.nextInt(max-min);
    }

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
}
