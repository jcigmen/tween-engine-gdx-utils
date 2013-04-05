package com.noobs2d.tweenengine.utils;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class DynamicAnimation extends DynamicDisplay {

    protected final TextureRegion[] keyFrames;
    protected float frameDuration;
    protected int frameIndex;
    protected int frameCount;
    protected boolean isPlaying = true;
    protected boolean isRepeating = true;
    protected float timeElapsed = 0;
    protected float stateTime = 0;

    public DynamicAnimation(DynamicAnimation clone) {
	keyFrames = clone.keyFrames;
	frameDuration = clone.frameDuration;
	frameIndex = clone.frameIndex;
	frameCount = clone.frameCount;
    }

    public DynamicAnimation(float frameDuration, List<TextureRegion> keyFrames) {
	this.keyFrames = new TextureRegion[keyFrames.size()];
	this.frameDuration = frameDuration;

	for (int index = 0; index < keyFrames.size(); index++)
	    this.keyFrames[index] = keyFrames.get(index);
	setRegistration(registration);
    }

    public DynamicAnimation(float frameDuration, TextureRegion... keyFrames) {
	this.keyFrames = keyFrames;
	this.frameDuration = frameDuration;
	frameCount = keyFrames.length;
	setRegistration(registration);
    }

    public DynamicAnimation(Texture texture, int offsetX, int offsetY, int frameWidth, int frameHeight, int framesPerRow, int frameCount, float frameDuration) {
	keyFrames = new TextureRegion[frameCount];
	this.frameDuration = frameDuration;
	this.frameCount = frameCount;

	int textureIndex = 0, x = offsetX, y = offsetY;
	int numRows = frameCount % framesPerRow == 0 ? frameCount / framesPerRow : frameCount / framesPerRow + 1;
	for (int rows = 1; rows <= numRows; rows++) {
	    for (int columns = 1; columns <= framesPerRow; columns++)
		if (textureIndex == frameCount)
		    columns = framesPerRow + 1;
		else {
		    keyFrames[textureIndex] = new TextureRegion(texture, x, y, frameWidth, frameHeight);
		    x += frameWidth;
		    textureIndex++;
		}

	    x = offsetX;
	    y += frameHeight;
	}
	setRegistration(registration);
    }

    @Override
    public Rectangle getBounds() {
	float x = position.x, y = position.y;
	float width = getKeyFrame().getRegionWidth(), height = getKeyFrame().getRegionHeight();
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

    /**
     * @return the frameDuration
     */
    public float getFrameDuration() {
	return frameDuration;
    }

    /**
     * @return the frameIndex
     */
    public int getFrameIndex() {
	return frameIndex;
    }

    @Override
    public float getHeight() {
	return getKeyFrame().getRegionWidth();
    }

    public TextureRegion getKeyFrame() {
	return keyFrames[frameIndex];
    }

    /**
     * @return the keyFrames
     */
    public TextureRegion[] getKeyFrames() {
	return keyFrames;
    }

    /**
     * @return the stateTime
     */
    public float getStateTime() {
	return stateTime;
    }

    /**
     * @return the timeElapsed
     */
    public float getTimeElapsed() {
	return timeElapsed;
    }

    @Override
    public float getWidth() {
	return getKeyFrame().getRegionWidth();
    }

    /**
     * @return the isPlaying
     */
    public boolean isPlaying() {
	return isPlaying;
    }

    /**
     * @return the isRepeating
     */
    public boolean isRepeating() {
	return isRepeating;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
	if (visible) {
	    //	    LogUtil.print(this.getClass(), "render(SpriteBatch)", "frameIndex: " + frameIndex);
	    spriteBatch.setColor(color);
	    float x = position.x, y = position.y;
	    float width = getKeyFrame().getRegionWidth(), height = getKeyFrame().getRegionHeight();
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
	    spriteBatch.draw(getKeyFrame(), x, y, origin.x, origin.y, width, height, scale.x, scale.y, rotation);
	}
    }

    public void reset() {
	stateTime = 0;
	frameIndex = 0;
    }

    @Override
    public void setRegistration(DynamicRegistration registration) {
	this.registration = registration;
    }

    @Override
    public void update(float deltaTime) {
	if (enabled) {
	    timeElapsed += deltaTime;
	    if (isPlaying) {
		stateTime += deltaTime;
		frameIndex = (int) (stateTime / frameDuration);
		if (!isRepeating)
		    frameIndex = Math.min(keyFrames.length - 1, frameIndex);
		else
		    frameIndex = frameIndex % keyFrames.length;
	    }
	    updateTween();
	}
    }

}
