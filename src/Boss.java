import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
import java.lang.Math;

/**
 * Class representing the boss enemy
 */
public class Boss extends Enemy{
    private static final String ENEMY_IMG_PATH = "res/boss.png";
    private static final float YPOS_MOVE_RATE = 0.2f;
    private static final float XPOS_MOVE_RATE_START = 0.2f;
    private static final float XPOS_MOVE_RATE_SHOOT = 0.1f;
    private static final int XPOS_MIN = 128;
    private static final int XPOS_MAX = 896;
    private static final int YPOS_THRESH = 72;
    private float randXPos = 512;
    private float randXPosShoot = 512;


    private static final int SCORE = 5000;
    private static final int TOTAL_SHOOTING_TIME = 3000;
    private static final int SHOOTING_INTERVAL = 200;

    private static final int WAIT_5000 = 5000;
    private static final int WAIT_2000 = 2000;
    private static final int[] LASER_OFFSET_POS = {-97,-74,74,94};
    private int healthRemaining = 60;

    private int behaviourTimer = 0;
    private int moveTimer = 0;
    private int timeLastFired = 0;
    private int waitTimer = 0;

    /**
     * CONSTRUCTOR: create the boss object
     * @param x : Integer representing the x coord to create the boss at
     * @param delay : Integer representing how long the boss should do nothing
     * @throws SlickException
     */
    public Boss(int x, int delay) throws SlickException{
        super(ENEMY_IMG_PATH,x,SCORE,delay);

        // set the initial location value
        randXPos = World.getRandomInt(XPOS_MIN, XPOS_MAX);
        setY(getY()-getHeight());

    }

    /**
     * Override the Enemy update method
     * @param input : keyboard input
     * @param delta : Integer representing the time since last update (in ms)
     * @throws SlickException
     */
    public void update(Input input, int delta) throws SlickException {
        super.update(input, delta);

        // tolerance for equality
        float EPS  = 1f;

        // if we have not passed the delay period, then we do nothing else
        if(getTimeExists() < getDelay()) {
            return;
        }

        // for the first 5000 seconds, just update the counter
        if(behaviourTimer < WAIT_5000) {
            behaviourTimer+= delta;
            waitTimer = 0 ;
            randXPos = World.getRandomInt(XPOS_MIN + this.getWidth()/2, XPOS_MAX - this.getWidth()/2);
            randXPosShoot = World.getRandomInt(XPOS_MIN + this.getWidth()/2, XPOS_MAX - this.getWidth()/2);

            System.out.println(randXPos+randXPosShoot);
            if(getY() < YPOS_THRESH){
                setY(getY() + delta * YPOS_MOVE_RATE);
            }

        }else{
            // once we have done waited 5000ms we can step through the rest of the loop
            // we can move towards the x position now

            // we have not started to time the movement yet
            if (waitTimer == 0) {
                // move towards the set x position
                if(getX() > randXPos){
                    setX(getX() - XPOS_MOVE_RATE_START * delta);
                } else if(getX() < randXPos) {
                    setX(getX() + XPOS_MOVE_RATE_START * delta);
                }
                // if we have reached this x Position -- we need to wait 2000ms
                if (Math.abs(getX() - randXPos) < EPS) {
                    waitTimer += delta;
                }
            }else {
                waitTimer+=delta;

                // if we have reached 2000ms then we can pick another x position
                if (waitTimer > WAIT_2000) {

                    // start to move to the next x location
                    if (getX() > randXPos) {
                        setX(getX() - XPOS_MOVE_RATE_SHOOT * delta);
                    } else if (getX() < randXPos) {
                        setX(getX() + XPOS_MOVE_RATE_SHOOT * delta);
                    }
                    // we should fire every 200ms
                    // only shoot for 3000ms
                    if (waitTimer < WAIT_2000 + TOTAL_SHOOTING_TIME) {
                        timeLastFired += delta;

                        // ensure that we only fire once every 200 ms
                        if (timeLastFired > SHOOTING_INTERVAL) {
                            shootLaser();
                            timeLastFired = 0;
                        }
                    } else {

                        // if we are at the second location, we can reset the  behaviour timer
                        if (Math.abs(getX() - randXPos) < EPS) {
                            // set the behaviour timer to zero so we can reset the process
                            behaviourTimer = 0;
                        }
                    }
                }else{
                    // we set the randXPos to being the second position -- this is the position we want to move to
                    // when we are moving and shooting
                    randXPos = randXPosShoot;
                }
            }
        }
    }

    /**
     * Method to make the boss shoot a laser
     */
    public void shootLaser(){
        // create a new laser at the middle of the current location of the player
        try{
            for(int offset: LASER_OFFSET_POS) {
                enemyLaser tempLaser = new enemyLaser(getX() + getWidth() / 2 + offset,
                        getY() + getHeight()/2);
                // add the laser to the world
                World.getWorld().addSprite(tempLaser);
            }
        }catch(SlickException e){
            e.printStackTrace();
        }
    }

    /**
     * Method to decrease the health of the boss when a player shoots it
     */
    public void decreaseHealth(){
        if(healthRemaining > 0){
            healthRemaining--;
        }
    }

    /**
     * Get the health of the boss
     * @return : Integer representing the remaining health of the boss (in shots)
     */
    public int getHealth(){
        return healthRemaining;
    }

}
