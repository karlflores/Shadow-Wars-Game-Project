import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
// Implementation of the player class as an extension of the sprite class
public class Player extends Sprite implements Shootable{

    //constants
    private static final float MOVE_RATE = 0.5f;
    private static final int SHOOT_RATE = 350;
    private static final String PLAYER_IMG_SRC = "res/spaceship.png";

    private int lastTimeShot = 0;
    // Constructor
    public Player(float x,float y) throws SlickException{
        // update the player
        super(PLAYER_IMG_SRC,x,y);
    }

    // update method
    public void update(Input input ,int delta) throws SlickException{
        // only update the player if it exists
        if(!getExistState()){
            return;
        }
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
            if(lastTimeShot > SHOOT_RATE){
                shootLaser();
                lastTimeShot=0;
            }

        }

    }

    //override the sprite render -- need to render all the lasers that the player has fired
    public void render(){
        super.render();
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

}
