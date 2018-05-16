import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

import java.util.ArrayList;

/**
 * This class is the Implementation of the player class
 */
public class Player extends Sprite{

    //constants
    private static final float MOVE_RATE = 0.5f;
    private static final int INIT_SHOOT_RATE = 350;
    private static final int POWERUP_SHOOTRATE = 150;
    private static final String PLAYER_IMG_SRC = "res/spaceship.png";
    private static final String SHIELD_SRC = "res/shield.png";
    private static final int MAX_NUM_LIVES = 3;
    private static final int IMMUNITY_TIME = 3000;
    private int numLives = 3;
    private int lastTimeShot = 0;
    private int powerupTimer = 0;
    private int shootRate = INIT_SHOOT_RATE;
    private int shootRateTimer = 0;
    private int immunityTimer = IMMUNITY_TIME;
    private Image shield;

    /**
     * Constructor: create the player at coordinates x and y
     * @param x
     * @param y
     * @throws SlickException
     */
    public Player(float x,float y) throws SlickException{
        // update the player
        super(PLAYER_IMG_SRC,x,y);

        shield = new Image(SHIELD_SRC);
    }

    /**
     * Update method that overrides its parent -- this method updates the relevant attributes for the player
     * @param input :
     * @param delta :
     * @throws SlickException
     */
    public void update(Input input ,int delta) throws SlickException{
        // only update the player if it exists
        if(!getExistState()){
            return;
        }

        /*
        * CONTROLLING THE POWERUP PARAMETERS -- THESE ARE SET WHEN ACTIVATE IS CALLED
         */
        // decrease the immunity timer if it exists
        if(immunityTimer > 0 && powerupTimer <= 0){
            immunityTimer -= delta;
        }

        // controls the shoot-rate power up
        if(shootRateTimer > 0){
            shootRateTimer-=delta;
            shootRate = POWERUP_SHOOTRATE;
        }else{

            // force the shoot rate to be this
            shootRate = INIT_SHOOT_RATE;
        }

        // call the super update method first
        super.update(input,delta);

        // incrase the laser shot counter
        lastTimeShot+=delta;
        // move according to key presses
        if(input.isKeyDown(Input.KEY_UP)){
            // move up -- decrease Y
            setY(getY()-MOVE_RATE*delta);
        }
        if(input.isKeyDown(Input.KEY_DOWN)){
            // move down -- increase Y
            setY(getY()+MOVE_RATE*delta);
        }
        if(input.isKeyDown(Input.KEY_LEFT)){
            // move left -- decrease X
            setX(getX()-MOVE_RATE*delta);
        }
        if(input.isKeyDown(Input.KEY_RIGHT)){
            // move right -- increase X
            setX(getX()+MOVE_RATE*delta);
        }

        // laser fired if space bar is hit
        if(input.isKeyDown(Input.KEY_SPACE)){

            // double check this condition
            if(lastTimeShot > shootRate){
                shootLaser();
                lastTimeShot=0;
            }

        }

    }

    /**
     * override the sprite render -- need to render all the lasers that the player has fired
     */
    public void render(){
        super.render();

        if(immunityTimer > 0){
            shield.draw(getX()-(-getWidth()+shield.getWidth())/2,getY()-(shield.getHeight()-getHeight())/2);
        }

    }

    /**
     * method to make the player shoot a laser at its current position
     */
    public void shootLaser(){
        // create a new laser at the middle of the current location of the player
        try{
            playerLaser tempLaser = new playerLaser(getX()+getWidth()/2,getY()+getHeight()/2);
            // add the laser to the world
            World.getWorld().addSprite(tempLaser);
        }catch(SlickException e){
            e.printStackTrace();
        }
    }

    /**
     * public method to make the player loose a life
     * This method is called outside of the player class so that the player looses a life
     * when it comes into contact with an enemy or a bullet
     */
    public void looseLife(){
        if(this.numLives > 0){
            numLives--;

            // remove a life from the overlay
            Overlay.getOverlay().removeLife();

            // set the immunity timer to 3000ms
            setImmunityTimer(IMMUNITY_TIME);
        }
    }

    /*
    * GETTER AND SETTER METHODS FOR THE PLAYER
     */

    /**
     * GETTER override setY() -- handles the top of the screen
     * @param y : the y coord that is set at
     */
    public void setY(float y){
        // the player cannot move past the top of the screen;
        if(y>=0 && y<=App.SCREEN_HEIGHT-getHeight()){
            super.setY(y);
        }
    }

    /**
     * Get the number of lives the player currently has
     * @return : numLives
     */
    public int getNumLives(){
        return numLives;
    }

    /**
     * Get the MAX NUM lives a player can have
     * @return MAX_NUM_LIVES
     */
    public static int getMaxNumLives(){
        return MAX_NUM_LIVES;
    }

    /**
     * Get how many ms of immunity the player currently has
     * @return
     */
    public int getImmunityTimer() {
        return immunityTimer;
    }

    /**
     * Set how much time of immunity the player has
     * @param time : time in ms
     */
    public void setImmunityTimer(int time){
        immunityTimer = time;
    }

    /**
     * Set how much time the player has increased shoot rate
     * @param time : time in ms
     */
    public void setShootRateTimer(int time){
        shootRateTimer = time;
    }

    /**
     * get the how much time the player has increased shooting rate
     * @return : time in ms
     */
    public int getShootRateTimer(){
        return shootRateTimer;
    }

}
