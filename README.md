# Shadow Wars - Part A 

## **UPDATES**

### Date - 5/4/2018
- Game now has a scrolling background. Since the background is 1024 x 768 pixels, we can render 4 x 512 background pixels in a 2 x 2 grid that covers the application screen. For each background space that is rendered we can determine how much of the background has been moved down based on the time elapsed and the background rate. So we then split this image into two parts -- i.e if an image is supposed to move down by x pixels. We know that 512-x height of the image would be moved down such that it renders at the bottom of the 512x512 image space. Then we take the bottom x height of the image and then we render that at the top of the image. Therefore if we think of this as moving the image over a 512 px square. The part that is not inside the square is just rendered to the top of the square such that the whole square is filled out. We repeat this for the rest of 4 background squares and since they tile, the background looks seamless. This gives the illusion of a scrolling background.
- 8 enemies are now rendered to the application screen at the correct locations 
- The player is able to move around within the application screen using the arrow keys 
- Added the functionality of the lasers being shot when space bar is hit. When we instantiate the player we create an array of lasers that have not yet been created yet. When spacebar is hit we create a new laser object and we populate that laser array. We also keep track of the number of laser shots fired so that we can iterate through the laser array very easily. 

### Date - 11/4/2018
- Used the BoundingBox detection to detect collisions. When update is called we also update the location of each sprites bounding box. 
- Enemies are now eliminated and not rendered to the screen when a laser shot or the player hits it. Here we also count how many enemies are killed and if this equals the initial number of enemies we end the game. If the player also hits the enemy we also end the game.
- There was a bug that allowed the enemy to be killed when the laser shot was to the left of the enemy but was not intersecting it. 
- Modified the bounding box file to move the location of x to the edge of the image instead of in the middle. Now it solves the BUG above. 
- Cleaned up the comments and the structure of the code. Now instead of using seperate instance variables to track whether a laser shot exists or if an enemy is killed, we just use a sprite instance variable called exists that tracks if a sprite should be rendered to the screen, updated or skipped over when looping through arrays.

### **TODO**: 
- Further testing in terms of movement speed of the player, laser background scrolling 
- Need to implement a way for the sprite to be deleted from the game when it does not exist anymore. Currently we just don't update its parameters or we just skip through it when iterating through the arrays (enemies or laserArr). Need to set to null so that the garbage collector can take care of it. But a problem was noted when I first tried implementing it. Kept getting a null reference error because it tried to access a null element. 
    - thought of using a queue or stack to track the laser shots so we can just pop the laser of the stack when it is deleted, same with the enemies. Or we can just push the enemy or laser shots to the end of the array (from its current location) so that if we track the number of enemies or lasers that have been so far we just need to iterate up to that point. Furthermore this would mean that when we change when an element does not exist anymore we just need to decrement the number of shots / enemies remaining so that it does not always just incrementally increase. 

