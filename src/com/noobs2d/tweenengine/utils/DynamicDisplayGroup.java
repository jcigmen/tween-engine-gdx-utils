package com.noobs2d.tweenengine.utils;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class DynamicDisplayGroup extends DynamicDisplay {

    public ArrayList<DynamicDisplay> displays = new ArrayList<DynamicDisplay>();
    private boolean inheritParentColor = true;

    public DynamicDisplayGroup() {

    }

    public DynamicDisplayGroup(ArrayList<DynamicDisplay> displays) {
	this.displays = displays;
    }

    public DynamicDisplayGroup(DynamicDisplay... displays) {
	for (int i = 0; i < displays.length; i++)
	    this.displays.add(displays[i]);
    }

    public void add(DynamicDisplay display) {
	displays.add(display);
    }

    public DynamicDisplay get(int index) {
	return displays.get(index);
    }

    @Override
    public Rectangle getBounds() {
	float x = position.x, y = position.y;
	float width = displays.get(getLargestAreaIndex()).getWidth();
	float height = displays.get(getLargestAreaIndex()).getHeight();
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
	    case CENTER_LEFT:
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

    public DynamicDisplay getByName(String name) {
	for (int i = 0; i < displays.size(); i++)
	    if (displays.get(i).getName().equals(name))
		return displays.get(i);
	System.out.println("[DynamicDisplayGroup#getByName(String)] Child " + name + " not found!");
	return null;

    }

    /**
     * Get the display among the collection with the largest area.
     * 
     * @return DynamicAnimation with the largest area.
     */
    public DynamicDisplay getLargestAreaDisplay() {
	float area = 0f;
	int largestAreaIndex = 0;
	for (int i = 0; i < displays.size(); i++)
	    if (displays.get(i).getWidth() * displays.get(i).getHeight() > area) {
		largestAreaIndex = i;
		area = displays.get(largestAreaIndex).getWidth() * displays.get(largestAreaIndex).getHeight();
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
	    if (displays.get(i).getWidth() * displays.get(i).getHeight() > area)
		index = i;
	return index;
    }

    /**
     * @return the inheritParentColor
     */
    public boolean isInheritParentColor() {
	return inheritParentColor;
    }

    @Override
    public void pauseTween() {
	for (int i = 0; i < displays.size(); i++)
	    displays.get(i).pauseTween();
    }

    public DynamicDisplay remove(int index) {
	return displays.remove(index);
    }

    public DynamicDisplay removeByName(String name) {
	for (int i = 0; i < displays.size(); i++)
	    if (displays.get(i).getName().equals(name))
		return displays.remove(i);
	return null;
    }

    @Override
    public void render(SpriteBatch batch) {
	if (visible)
	    for (int i = 0; i < displays.size(); i++) {
		DynamicDisplay target = displays.get(i);
		float originX = target.getOriginX();
		float originY = target.getOriginY();
		if (inheritParentColor) {
		    target.setColor(color);
		    batch.setColor(color);
		}
		target.setPosition(target.getX() + position.x, target.getY() + position.y);
		target.setOrigin(origin.x, origin.y);
		target.render(batch);
		target.setPosition(target.getX() - position.x, target.getY() - position.y);
		target.setOrigin(originX, originY);
	    }
    }

    public void reset() {
	for (int i = 0; i < displays.size(); i++) {
	    displays.get(i).setVisible(true);
	    displays.get(i).setEnabled(true);
	    displays.get(i).endTween();
	}
    }

    @Override
    public void resumeTween() {
	for (int i = 0; i < displays.size(); i++)
	    displays.get(i).resumeTween();
    }

    /**
     * @param inheritParentColor the inheritParentColor to set
     */
    public void setInheritParentColor(boolean inheritParentColor) {
	this.inheritParentColor = inheritParentColor;
    }

    @Override
    public void setRegistration(DynamicRegistration registration) {
	for (int i = 0; i < displays.size(); i++)
	    this.registration = registration;
	//	    displays.get(i).setRegistration(registration);
    }

    public int size() {
	return displays.size();
    }

    @Override
    public void update(float deltaTime) {
	if (enabled)
	    for (int i = 0; i < displays.size(); i++)
		displays.get(i).update(deltaTime);
    }

}
