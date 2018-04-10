import org.lwjgl.Sys;
import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
public class World {
    //BACKGROUND DATA
    private Image[] background;
    private int SCREEN_WIDTH  = 768;
    //indicating the size of the background array -- 4 parts + 2 parts for the sub images
    private int BACKGROUND_ARRAY_SIZE = 4;
    // how many pixels the background moves each update
    private float BG_OFFSET_PER_SEC = (float)0.2;
    private float bgMovement;

    private int NUM_ENEMIES = 8;
    private int enemiesKilled = 0;
    private boolean gameOver = false;
    private String ENEMY_IMG_PATH = "res/basic-enemy.png";
    private String PLAYER_IMG_PATH = "res/spaceship.png";

    private Player player;
    private Enemy[] enemies;

	public World() throws SlickException {
		// Perform initialisation logic

        // Background parameter initialisation
        this.background = new Image[BACKGROUND_ARRAY_SIZE];
        for(int i = 0; i < BACKGROUND_ARRAY_SIZE ; i++ ) {
            this.background[i] = new Image("res/space.png");
        }
        //set the initial background movement to 0 px -- every update we want this to increase by 0.2
        // max movement is 512 px -- then we want to reset this to being 0px
        this.bgMovement = 0;

        //set the number of enemies in the world;
        this.enemies = new Enemy[NUM_ENEMIES];
        for(int i = 0;i < NUM_ENEMIES;i++){
            enemies[i] = new Enemy(ENEMY_IMG_PATH,32+(128)*i,32);
        }
        // set the player and its location in the world
        this.player = new Player(PLAYER_IMG_PATH,480,688);
	}
	
	public void update(Input input, int delta) throws SlickException{
		// Update all of the sprites in the game

        //Update the Background Parameters
        /*
          every time we want to update we want to move the background down at
          rate of 0.2px/ms -- the max that the background can move is 512px
          before it resets to 0
          */
        bgMovement=(bgMovement+BG_OFFSET_PER_SEC*delta)%this.background[0].getHeight();

        // update all the enemies
        Laser[] lasersArr = player.getLasersArr();

        // loop through each of the lasers that have been fired
       // for(int i = 0;i < player.getNumLasersFired(); i++){
           //System.out.print(lasersArr[i].getX());
           //System.out.println(lasersArr[i].getY());
       // }

        // loop through each of the enemies and check if they have been contacted with another sprite
        for (int i = 0; i < this.NUM_ENEMIES; i++) {
            for(int j = 0;j < player.getNumLasersFired(); j++) {
                if(lasersArr[j].makesContact(enemies[i]) && enemies[i].getIsAlive()) {
                    enemies[i].contactSprite(lasersArr[j]);
                    enemies[i].killEnemy();
                    System.out.println("made contact");

                }
                if(enemies[i].getIsAlive()) {
                    enemies[i].update(input, delta);
                }
            }
            if(player.makesContact(enemies[i])){
                enemies[i].contactSprite(player);
                this.gameOver = true;
            }
        }

        if(numEnemiesKilled() == NUM_ENEMIES){
            this.gameOver = true;
        }

        // update the player;
        player.update(input,delta);
	}
	
	public void render() {
		// Draw all of the sprites in the game
        drawBackground();

        // draw the player
        player.render();
        // draw the enemies
        for(Enemy enemy : enemies){
            if(enemy.getRenderState()) {
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

    public boolean isGameOver(){
	    if(this.gameOver){
	        return true;
        }else{
	        return false;
        }
    }

    private int numEnemiesKilled(){
	    int killed = 0;
	    for(Enemy enemy: enemies){
	        if(!enemy.getIsAlive()){
	            killed++;
            }
        }
        return killed;
    }
}
