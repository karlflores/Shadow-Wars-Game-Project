import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

import java.util.ArrayList;

// Implementation of the player class as an extension of the sprite class
public class Player extends Sprite implements Shootable{

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
    // Constructor
    public Player(float x,float y) throws SlickException{
        // update the player
        super(PLAYER_IMG_SRC,x,y);

        shield = new Image(SHIELD_SRC);
    }

    // update method
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

    //override the sprite render -- need to render all the lasers that the player has fired
    public void render(){
        super.render();

        if(immunityTimer > 0){
            shield.draw(getX()-(-getWidth()+shield.getWidth())/2,getY()-(shield.getHeight()-getHeight())/2);
        }

    }

    // override setY() -- handles the top of the screen
    public void setY(float y){
        // the player cannot move past the top of the screen;
        if(y>=0 && y<=App.SCREEN_HEIGHT-getHeight()){
            super.setY(y);
        }
    }

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

    public void looseLife(){
        if(this.numLives > 0){
            numLives--;

            // remove a life from the overlay
            Overlay.getOverlay().removeLife();
        }
    }

    public int getNumLives(){
        return numLives;
    }
    public static int getMaxNumLives(){
        return MAX_NUM_LIVES;
    }

    public int getImmunityTimer() {
        return immunityTimer;
    }

    public void setImmunityTimer(int time){
        immunityTimer = time;
    }

    public void setShootRateTimer(int time){
        shootRateTimer = time;
    }

}
