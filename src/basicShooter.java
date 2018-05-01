import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

public class basicShooter extends Enemy implements Shootable{

    private static final String ENEMY_IMG_PATH = "res/basic-shooter.png";
    private static final float MOVE_RATE = 0.2f;
    private static final int SCORE = 200;
    private static final int SHOOT_DELAY = 3500;
    private static final int Y_END_MIN = 48;
    private static final int Y_END_MAX = 464;
    private final int yThresh;
    private int timeLastFired = 0;

    public basicShooter(float x,int delay) throws SlickException{
        super(ENEMY_IMG_PATH,x,MOVE_RATE,SCORE,delay);
        yThresh = World.getRandomInt(Y_END_MIN,Y_END_MAX);
        // System.out.println(yThresh);
        // the ave
    }

    public void update(Input input, int delta) throws  SlickException{
        super.update(input,delta);
        timeLastFired+=delta;
        // if we have reached the threshold y value we can start firing the laser at required intervals
        // set the Y location of the Enemy
        if(getTimeExists() > getDelay()){
            setY(getY()+MOVE_RATE*delta);
        }

        if(timeLastFired > SHOOT_DELAY){
            shootLaser();
            timeLastFired = 0;
            //shootLaser();
            // ensure that we can only fire one laser per SHOOT_DELAY INTERVAL
        }
    }

    // calculate the offset of the xPos for the sine enemy

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
    // update y values until they are at threshold
    public void setY(float y){
        if(y < yThresh){
            super.setY(y);
        }
    }

}