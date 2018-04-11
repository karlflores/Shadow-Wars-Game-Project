import org.lwjgl.Sys;
import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
public class World {
    //BACKGROUND DATA
    private Image[] background;
    //indicating the size of the background array -- 4 parts + 2 parts for the sub images
    private static int BACKGROUND_ARRAY_SIZE = 4;
    // how many pixels the background moves each update
    private static float BG_OFFSET_PER_SEC = (float)0.2;
    private float bgMovement;

    // enemy data
    private static int NUM_ENEMIES = 8;
    private boolean gameOver = false;
    private String ENEMY_IMG_PATH = "res/basic-enemy.png";
    private Enemy[] enemies;
    //player data
    private String PLAYER_IMG_PATH = "res/spaceship.png";
    private Player player;

	public World() throws SlickException {

        // Background parameter initialisation
        this.background = new Image[BACKGROUND_ARRAY_SIZE];
        for(int i = 0; i < BACKGROUND_ARRAY_SIZE ; i++ ) {
            this.background[i] = new Image("res/space.png");
        }
        // set the initial background movement to 0 px -- every update we want this to increase by 0.2
        // max movement is 512 px -- then we want to reset this to being 0px
        this.bgMovement = 0;

        // create the enemies in the world based on the max number of enemies at the
        // specified locations
        this.enemies = new Enemy[NUM_ENEMIES];
        for(int i = 0;i < NUM_ENEMIES;i++){
            enemies[i] = new Enemy(ENEMY_IMG_PATH,32+(128)*i,32);
        }
        // set the player and its location in the world
        this.player = new Player(PLAYER_IMG_PATH,480,688);
	}
	
	public void update(Input input, int delta) throws SlickException{
        //Update the Background Parameters
        /*
          every time we want to update we want to move the background down at
          rate of 0.2px/ms -- the max that the background can move is 512px
          before it resets to 0
          */
        bgMovement=(bgMovement+BG_OFFSET_PER_SEC*delta)%this.background[0].getHeight();

        // update all the enemies
        Laser[] lasersArr = player.getLasersArr();

        // loop through each of the enemies and check if they have been contacted with another sprite
        for (int i = 0; i < this.NUM_ENEMIES; i++){
            // loop through all the lasers that have currently been fired
            for(int j = 0;j < player.getNumLasersFired(); j++) {
                if(lasersArr[j].makesContact(enemies[i]) && enemies[i].getExistState()) {
                    enemies[i].contactSprite(lasersArr[j]);
                }
                // if the enemy exists we should update it
                if(enemies[i].getExistState()) {
                    enemies[i].update(input, delta);
                }
            }
            // checks if the player makes contact with the enemy
            if(player.makesContact(enemies[i])){
                enemies[i].contactSprite(player);
                this.gameOver = true;
            }
        }

        // checks if the game is over by checking how many enemies have been killed
        if(numEnemiesKilled() == NUM_ENEMIES){
            this.gameOver = true;
        }
        // finally we update the player;
        player.update(input,delta);
	}
	
	public void render() {
        // draw the background
        drawBackground();

        // draw the player
        player.render();

        // draw the enemies
        for(Enemy enemy : enemies){
            if(enemy.getExistState()) {
                enemy.render();
            }
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
	    return this.gameOver;
    }
    // helper method to return the number of enemies currently killed in a game
    private int numEnemiesKilled(){
	    int killed = 0;
	    for(Enemy enemy: enemies){
	        if(!enemy.getExistState()){
	            killed++;
            }
        }
        return killed;
    }
}
