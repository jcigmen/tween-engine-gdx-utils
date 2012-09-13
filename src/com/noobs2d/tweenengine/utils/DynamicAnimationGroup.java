package com.noobs2d.tweenengine.utils;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class DynamicAnimationGroup extends DynamicDisplay {

    public ArrayList<DynamicAnimation> displays = new ArrayList<DynamicAnimation>();

    public DynamicAnimationGroup(ArrayList<DynamicAnimation> displays) {
	this.displays = displays;
    }

    @Override
    public Rectangle getBounds() {
	float x = position.x, y = position.y;
	float width = displays.get(getLargestAreaIndex()).getKeyFrame().getRegionWidth();
	float height = displays.get(getLargestAreaIndex()).getKeyFrame().getRegionHeight();
	bounds.width = width * scale.x;
	bounds.height = height * scale.y;
	switch (registration) {
	    case BOTTOM_CENTER:
		bounds.x = x - width / 2;
		break;
	    case BOTTOM_RIGHT:
		bounds.x = x - width;
		break;
	    case CENTER_CENTER:
		bounds.x = x - width / 2;
		bounds.y = y - height / 2;
		break;
	    case LEFT_CENTER:
		bounds.x = x - width;
		bounds.y = y - height / 2;
		break;
	    case TOP_CENTER:
		bounds.x = x - width / 2;
		bounds.y = y - height;
		break;
	    case TOP_LEFT:
		bounds.x = x - width;
		bounds.y = y - height;
		break;
	    case TOP_RIGHT:
		bounds.y = y - height;
		break;
	}
	return bounds;
    }

    /**
     * Get the display among the collection with the largest area.
     * 
     * @return DynamicAnimation with the largest area.
     */
    public DynamicAnimation getLargestAreaDisplay() {
	float area = 0f;
	int largestAreaIndex = 0;
	for (int i = 0; i < displays.size(); i++)
	    if (displays.get(i).getKeyFrame().getRegionWidth() * displays.get(i).getKeyFrame().getRegionHeight() > area) {
		largestAreaIndex = i;
		area = displays.get(largestAreaIndex).getKeyFrame().getRegionWidth() * displays.get(largestAreaIndex).getKeyFrame().getRegionHeight();
	    }
	return displays.get(largestAreaIndex);
    }

    /**
     * Get the display index among the collection with the largest area.
     * 
     * @return DynamicAnimation index with the largest area.
     */
    public int getLargestAreaIndex() {
	float area = 0f;
	int index = 0;
	for (int i = 0; i < displays.size(); i++)
	    if (displays.get(i).getKeyFrame().getRegionWidth() * displays.get(i).getKeyFrame().getRegionHeight() > area)
		index = i;
	return index;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
	if (visible) {
	    spriteBatch.setColor(color);
	    for (int i = 0; i < displays.size(); i++) {
		float x = displays.get(i).position.x + position.x, y = displays.get(i).position.y + position.y;
		float scaleX = displays.get(i).scale.x;
		float scaleY = displays.get(i).scale.y;
		float width = displays.get(i).getKeyFrame().getRegionWidth();
		float height = displays.get(i).getKeyFrame().getRegionHeight();
		float rotation = displays.get(i).rotation;
		float originX = displays.get(i).origin.x;
		float originY = displays.get(i).origin.y;
		switch (registration) {
		    case BOTTOM_CENTER:
			origin.set(width / 2, 0);
			x = position.x - width / 2;
			break;
		    case BOTTOM_LEFT:
			origin.set(0, 0);
			break;
		    case BOTTOM_RIGHT:
			origin.set(width, 0);
			x = position.x - width;
			break;
		    case CENTER_CENTER:
			origin.set(width / 2, height / 2);
			x = position.x - width / 2;
			y = position.y - height / 2;
			break;
		    case RIGHT_CENTER:
			origin.set(width, height / 2);
			x = position.x - width;
			y = position.y - height / 2;
			break;
		    case LEFT_CENTER:
			origin.set(0, height / 2);
			y = position.y - height / 2;
			break;
		    case TOP_CENTER:
			origin.set(width / 2, height);
			x = position.x - width / 2;
			y = position.y - height;
			break;
		    case TOP_LEFT:
			origin.set(0, height);
			y = position.y - height;
			break;
		    case TOP_RIGHT:
			origin.set(width, height);
			x = position.x - width;
			y = position.y - height;
			break;
		}
		spriteBatch.draw(displays.get(i).getKeyFrame(), x, y, originX, originY, width, height, scaleX, scaleY, rotation);
	    }
	}
    }

    public void reset() {
	for (int i = 0; i < displays.size(); i++) {
	    displays.get(i).frameIndex = 0;
	    displays.get(i).visible = true;
	    displays.get(i).enabled = true;
	    displays.get(i).tweenManager.update(1000);
	}
    }

    @Override
    public void setRegistration(DynamicRegistration registration) {
	this.registration = registration;
    }

    @Override
    public void update(float deltaTime) {
	if (enabled)
	    for (int i = 0; i < displays.size(); i++)
		displays.get(i).update(deltaTime);
    }

}
