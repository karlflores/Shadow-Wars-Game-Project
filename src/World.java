import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import java.util.ArrayList;
import java.util.Random;

public class World {
    //BACKGROUND DATA
    private Image[] background;
    //indicating the size of the background array -- 4 parts + 2 parts for the sub images
    private static final int BACKGROUND_ARRAY_SIZE = 4;
    // how many pixels the background moves each update
    private static final float BG_OFFSET_PER_SEC = 0.2f;
    private float bgMovement;

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

    // constructor
	public World() throws SlickException {

        // Background parameter initialisation
        runtime = 0;
        background = new Image[BACKGROUND_ARRAY_SIZE];
        for(int i = 0; i < BACKGROUND_ARRAY_SIZE ; i++ ) {
            background[i] = new Image("res/space.png");
        }
        // set the initial background movement to 0 px -- every update we want this to increase by 0.2
        // max movement is 512 px -- then we want to reset this to being 0px
        bgMovement = 0;

        // create the enemies in the world based on the max number of enemies at the
        // specified locations

        for(int i = 0;i < NUM_ENEMIES;i++){
            // create each enemy and space the enemies out on screen based on an initial position
            // and an px-spacing between each enemy
            sprites.add(new basicShooter(START_ENEMY_XPOS+(ENEMY_SPACING_PX)*i,500));
        }
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
        bgMovement=(bgMovement+BG_OFFSET_PER_SEC*delta)%background[0].getHeight();

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
                        this_sprite.contactSprite(other_sprite);

                        // check if an enemy was killed
                        if(this_sprite instanceof playerLaser && other_sprite instanceof Enemy) {
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
            gameOver = true;
        }
	}
	
	public void render() {
        // draw the background
        drawBackground();

        // draw all the sprites in the image
        for(Sprite sprite : sprites){
            sprite.render();
        }
	}

	private void drawBackground(){
	    //need to tile the background into 4 parts
        //image is 512 x 512 px
        // app is 1024 x 768 px -- therefore need to render image at (0,0), (0,513), (513, 0) (513,513)
        updateBackgroundScroll(background[0],0,0);
        updateBackgroundScroll(background[1],0,background[1].getHeight()+1);
        updateBackgroundScroll(background[2],background[2].getHeight()+1,0);
        updateBackgroundScroll(background[3],background[3].getHeight()+1,background[3].getHeight()+1);
    }

    private void updateBackgroundScroll(Image background,int x,int y){
	    //split up the background segment into two segments -- the part that is moved down,
        // the part that is cropped and should be rendered at the top of the original segment
        // we then print the part that is on the screen at the bottom, then print the part that is off the screen
        // at the bottom part of that segment

        //get the two parts of the image based on how far the background image has moved so far
        Image splitOriginalSegment = background.getSubImage(0,0,background.getWidth(),
                background.getHeight()-(int)bgMovement);
        Image splitCropSegment = background.getSubImage(0,background.getHeight()-(int)bgMovement,
                background.getWidth(), (int)bgMovement);

        //print the original segment below the split off segment using the specified location on the app (x,y)
        splitOriginalSegment.draw(x,y+bgMovement);
        splitCropSegment.draw(x,y);
    }

    // getter method that returns if the game is over
    public boolean isGameOver(){
	    return gameOver;
    }
    // helper method to return the number of enemies currently killed in a game

    public void addSprite(Sprite sprite){
	    System.out.println("added sprite");
	    sprites.add(sprite);
	    System.out.println("skjhfksdjhf");
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
	    return min + randomGen.nextInt(max);
    }

}
