package com.noobs2d.tweenengine.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class DynamicSprite extends DynamicDisplay {

    public TextureRegion region;

    public DynamicSprite(TextureRegion region, float x, float y) {
	this.region = region;
	position.set(x, y);
	float width = 0;
	float height = 0;
	try {
	    width = region.getRegionWidth();
	    height = region.getRegionHeight();
	} catch (NullPointerException e) {
	    // Just in case someone uses new TextureRegion(), which results into a null region.getRegionWidth(). Whatever.
	}
	setRegistration(registration);
	bounds.set(x - width / 2, y - height / 2, width, height);
    }

    @Override
    public Rectangle getBounds() {
	float x = position.x, y = position.y;
	float width = region.getRegionWidth(), height = region.getRegionHeight();
	bounds.x = x;
	bounds.y = y;
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

    @Override
    public void render(SpriteBatch spriteBatch) {
	if (visible) {
	    spriteBatch.setColor(color);
	    float x = position.x, y = position.y;
	    float width = region.getRegionWidth(), height = region.getRegionHeight();
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
	    spriteBatch.draw(region, x, y, origin.x, origin.y, region.getRegionWidth(), region.getRegionHeight(), scale.x, scale.y, rotation);
	}
    }

    @Override
    public void setRegistration(DynamicRegistration registration) {
	this.registration = registration;
    }

    @Override
    public void update(float deltaTime) {
	if (enabled)
	    updateTween();
    }
}
