package com.noobs2d.tweenengine.utils;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class DynamicAnimation extends DynamicDisplay {

    public final TextureRegion[] keyFrames;

    public float frameDuration;
    public int frameIndex;
    public int frameCount;
    public boolean isPlaying = true;
    public boolean isRepeating = true;
    public float timeElapsed = 0;
    public float stateTime = 0;

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

    public TextureRegion getKeyFrame() {
	return keyFrames[frameIndex];
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
	if (visible) {
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
	    spriteBatch.draw(getKeyFrame(), x, y, origin.x, origin.y, width, height, scale.x, scale.y, rotation);
	}
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
