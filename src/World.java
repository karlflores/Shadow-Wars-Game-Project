import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
public class World {
    //BACKGROUND DATA
    private Image[] background;
    //indicating the size of the background array -- 4 parts + 2 parts for the sub images
    private int BACKGROUND_ARRAY_SIZE = 4;
    // how many pixels the background moves each update
    private float BG_OFFSET_PER_SEC = (float)0.2;
    private float bgMovement;


	public World() throws SlickException {
		// Perform initialisation logic

        // Background parameter initialisation
        this.background = new Image[BACKGROUND_ARRAY_SIZE];
        for(int i = 0; i < BACKGROUND_ARRAY_SIZE ; i++ ) {
            this.background[i] = new Image("res/space.png");
        }
        //s et the initial background movement to 0 px -- every update we want this to increase by 0.2
        // max movement is 512 px -- then we want to reset this to being 0px
        this.bgMovement = 0;


	}
	
	public void update(Input input, int delta) {
		// Update all of the sprites in the game

        //Update the Background Parameters
        /*
          every time we want to update we want to move the background down at
          rate of 0.2px/ms -- the max that the background can move is 512px
          before it resets to 0
          */
        bgMovement=(bgMovement+BG_OFFSET_PER_SEC/delta)%this.background[0].getHeight();
	}
	
	public void render() {
		// Draw all of the sprites in the game
        drawBackground();
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
	    //check if background is not Null
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
}
