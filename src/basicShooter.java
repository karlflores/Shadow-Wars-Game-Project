import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

public class basicShooter extends Enemy{

    private static final String ENEMY_IMG_PATH = "res/basic-shooter.png";
    private static final float MOVE_RATE = 0.2f;
    private static final int SCORE = 200;
    private static final int SHOOT_DELAY = 3500;
    private static final int Y_END_MIN = 48;
    private static final int Y_END_MAX = 464;
    private final int yThresh;
    private int timeLastFired = 0;


    /**
     * Constructor: create the basic shooter at the required x position
     * @param x : Float -- x location to create the enemy at
     * @param delay : Float -- how long the enemy should do nothing
     * @throws SlickException
     */
    public basicShooter(float x,int delay) throws SlickException{
        super(ENEMY_IMG_PATH,x,SCORE,delay);
        yThresh = World.getRandomInt(Y_END_MIN,Y_END_MAX);
        // System.out.println(yThresh);
        // the ave
    }

    /**
     * Update method
     * @param input
     * @param delta
     * @throws SlickException
     */
    public void update(Input input, int delta) throws  SlickException{
        super.update(input,delta);
        timeLastFired+=delta;
        // if we have reached the threshold y value we can start firing the laser at required intervals
        // set the Y location of the Enemy
        if(getTimeExists() > getDelay()) {
            setY(getY() + MOVE_RATE * delta);


            if (timeLastFired > SHOOT_DELAY) {
                shootLaser();
                timeLastFired = 0;
                //shootLaser();
                // ensure that we can only fire one laser per SHOOT_DELAY INTERVAL
            }
        }
    }

    // calculate the offset of the xPos for the sine enemy

    /**
     * Method to make the enemy shoot a laser
     */
    public void shootLaser(){
        // create a new laser at the middle of the current location of the player
        try{
            enemyLaser tempLaser = new enemyLaser(getX()+getWidth()/2,getY()+getHeight()/2);
            // add the laser to the world
            World.getWorld().addSprite(tempLaser);
        }catch(SlickException e){
            e.printStackTrace();
        }
    }

    /**
     * Set the y location of this enemy that it is renedered at on the screen
     * only allows to set it above the threshold
     * @param y : Float -- the y coord to set to
     */
    public void setY(float y){
        if(y < yThresh){
            super.setY(y);
        }
    }

}