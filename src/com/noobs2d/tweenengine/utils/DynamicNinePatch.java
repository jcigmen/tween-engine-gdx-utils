package com.noobs2d.tweenengine.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class DynamicNinePatch extends DynamicDisplay {

    public NinePatch ninePatch;
    public float width = 0;
    public float height = 0;

    /**
     * @param texture Texture of the ninepatch.
     * @param width Width of the ninepatch upon rendering.
     * @param height Height of the ninepatch upon rendering
     * @param corners MUST be a size of 4. Left, right, top, bottom, respectively.
     **/
    public DynamicNinePatch(Texture texture, float width, float height, int[] corners) {
	ninePatch = new NinePatch(texture, corners[0], corners[1], corners[2], corners[3]);
	this.width = width;
	this.height = height;
	setRegistration(registration);
    }

    @Override
    public Rectangle getBounds() {
	float x = position.x, y = position.y;
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

    @Override
    public void render(SpriteBatch spriteBatch) {
	if (visible) {
	    spriteBatch.setColor(color);
	    float x = position.x, y = position.y;
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
		case CENTER_RIGHT:
		    origin.set(width, height / 2);
		    x = position.x - width;
		    y = position.y - height / 2;
		    break;
		case CENTER_LEFT:
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
	    ninePatch.draw(spriteBatch, x, y, width, height);
	}
    }

    @Override
    public void setRegistration(DynamicRegistration registration) {
	this.registration = registration;
	float x = position.x, y = position.y;
	float width = ninePatch.getTotalWidth(), height = ninePatch.getTotalHeight();
	bounds.width = width * scale.x;
	bounds.height = height * scale.y;
	switch (registration) {
	    case BOTTOM_CENTER:
		origin.set(x - width * scale.x / 2, y);
		break;
	    case BOTTOM_LEFT:
		origin.set(x, y);
		break;
	    case BOTTOM_RIGHT:
		origin.set(x - width * scale.x, y);
		break;
	    case CENTER_CENTER:
		origin.set(width / 2, height / 2);
		break;
	    case CENTER_LEFT:
		origin.set(x - width * scale.x, y - height * scale.y / 2);
		break;
	    case CENTER_RIGHT:
		origin.set(x, y - height * scale.y / 2);
		break;
	    case TOP_CENTER:
		origin.set(x - width * scale.x / 2, y - height * scale.y);
		break;
	    case TOP_LEFT:
		origin.set(x - width * scale.x, y - height * scale.y);
		break;
	    case TOP_RIGHT:
		origin.set(x, y - height * scale.y);
		break;
	}
    }

    @Override
    public void update(float deltaTime) {
	if (enabled)
	    updateTween();
    }

}
