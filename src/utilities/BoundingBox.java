/**
 * BoundingBox complete class for SWEN20003: Object Oriented Software Development 2018
 * by Eleanor McMurtry, University of Melbourne
 */
package utilities;

import org.newdawn.slick.Image;

public class BoundingBox {
	private float left;
	private float top;
	private float width;
	private float height;
	
	public BoundingBox(float x, float y, float width, float height) {
		this.width = width;
		this.height = height;
		setX(x);
		setY(y);
	}
	public BoundingBox(Image img, float x, float y) {
		width = img.getWidth();
		height = img.getHeight();
		setX(x);
		setY(y);
	}
	public BoundingBox(BoundingBox bb) {
		width = bb.width;
		height = bb.height;
		left = bb.left;
		top = bb.top;
	}
	
	/*
	 * Sets the y position to the center of the image -- the x location is unchanged
	*/
	public void setX(float x) {
		left = x;
	}
	public void setY(float y) {
		top = y - height / 2;
	}
	public void setWidth(float w) {
		width = w;
	}
	public void setHeight(float h) {
		height = h;
	}
	
	public float getLeft() {
		return left;
	}
	public float getTop() {
		return top;
	}
	public float getRight() {
		return left + width;
	}
	public float getBottom() {
		return top + height;
	}
	
	public float getWidth() {
		return width;
	}
	public float getHeight() {
		return height;
	}
	
	public boolean intersects(BoundingBox other) {
	//	System.out.println("check intersection");
		return !(other.left > getRight()
			  || other.getRight()  < left
			  || other.top > getBottom()
			  || other.getBottom() < top);
	}
}
