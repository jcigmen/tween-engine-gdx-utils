package com.noobs2d.tweenengine.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class DynamicButton extends DynamicDisplay {

    public interface DynamicButtonCallback {

	static final int UP = 0;
	static final int DOWN = 1;
	static final int HOVER = 2;
	static final int DRAG = 5;

	public void onButtonEvent(DynamicButton button, int eventType);

    }

    public enum State {
	UP, DOWN, HOVER
    }

    public DynamicButtonCallback callback;
    public Sound tapSound;
    public State state;
    public TextureRegion upstate;
    public TextureRegion hoverstate;
    public TextureRegion downstate;

    public DynamicButton(TextureRegion upstate, TextureRegion hoverstate, TextureRegion downstate, Vector2 position) {
	this.upstate = upstate;
	this.hoverstate = hoverstate;
	this.downstate = downstate;
	state = State.UP;
	this.position.set(position);
	setRegistration(registration);
    }

    @Override
    public Rectangle getBounds() {
	float x = position.x, y = position.y;
	float width = getStateRegion().getRegionWidth(), height = getStateRegion().getRegionHeight();
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

    public TextureRegion getStateRegion() {
	switch (state) {
	    case DOWN:
		return downstate;
	    case HOVER:
		return hoverstate;
	    case UP:
		return upstate;
	    default:
		return upstate;
	}
    }

    /**
     * @param x Input X.
     * @param y Input Y translated whether due to reverse-y.
     */
    public void inputDown(float x, float y) {
	if (enabled && visible && getBounds().contains(x, y))
	    if (callback != null)
		callback.onButtonEvent(this, DynamicButtonCallback.DOWN);
	    else
		state = DynamicButton.State.DOWN;
    }

    /**
     * @param x Input X.
     * @param y Input Y translated whether due to reverse-y.
     */
    public void inputDrag(float x, float y) {
	if (enabled && visible && getBounds().contains(x, y) && state == DynamicButton.State.UP) {
	    position.add(position.x - x, position.y - y);
	    if (callback != null)
		callback.onButtonEvent(this, DynamicButtonCallback.DRAG);
	    else
		state = DynamicButton.State.DOWN;
	}
	if (enabled && visible && !getBounds().contains(x, y) && state == DynamicButton.State.HOVER)
	    state = DynamicButton.State.UP;
    }

    /**
     * @param x Input X.
     * @param y Input Y translated whether due to reverse-y.
     */
    public void inputMove(float x, float y) {
	if (enabled && visible && getBounds().contains(x, y) && state == DynamicButton.State.UP)
	    if (callback != null)
		callback.onButtonEvent(this, DynamicButtonCallback.HOVER);
	    else
		state = DynamicButton.State.HOVER;
	else if (enabled && visible && !getBounds().contains(x, y) && state == DynamicButton.State.HOVER)
	    state = DynamicButton.State.UP;
    }

    /**
     * @param x Input X.
     * @param y Input Y translated whether due to reverse-y.
     */
    public void inputUp(float x, float y) {
	if (enabled && visible && !getBounds().contains(x, y) && state == DynamicButton.State.DOWN)
	    state = DynamicButton.State.UP;
	else if (enabled && visible && getBounds().contains(x, y))
	    if (getBounds().contains(x, y) && callback != null)
		callback.onButtonEvent(this, DynamicButtonCallback.UP);
	    else
		state = DynamicButton.State.HOVER;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
	if (visible) {
	    spriteBatch.setColor(color);
	    float x = position.x, y = position.y;
	    float width = getStateRegion().getRegionWidth(), height = getStateRegion().getRegionHeight();
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
	    spriteBatch.draw(getStateRegion(), x, y, origin.x, origin.y, width, height, scale.x, scale.y, rotation);
	}
    }

    @Override
    public void setRegistration(DynamicRegistration registration) {
	this.registration = registration;
    }

    @Override
    public void update(float deltaTime) {
	updateTween();
    }

}
