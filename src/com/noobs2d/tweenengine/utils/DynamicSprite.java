package com.noobs2d.tweenengine.utils;

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class DynamicSprite extends DynamicDisplay {

    private static final int SKEW_XY1 = 0xF1;
    private static final int SKEW_XY2 = 0xF2;
    private static final int SKEW_XY3 = 0xF3;
    private static final int SKEW_XY4 = 0xF4;
    private static final int SKEW_X1 = 0xA1;
    private static final int SKEW_Y1 = 0xA2;
    private static final int SKEW_X2 = 0xB1;
    private static final int SKEW_Y2 = 0xB2;
    private static final int SKEW_X3 = 0xC1;
    private static final int SKEW_Y3 = 0xC2;
    private static final int SKEW_X4 = 0xD1;
    private static final int SKEW_Y4 = 0xD2;

    private Sprite sprite;
    private float skewX1;
    private float skewY1;

    private float skewX2;

    private float skewY2;
    private float skewX3;
    private float skewY3;
    private float skewX4;
    private float skewY4;

    public DynamicSprite(TextureRegion region) {
	float width = 0;
	float height = 0;
	if (region != null) {
	    sprite = new Sprite(region);
	    width = region.getRegionWidth();
	    height = region.getRegionHeight();
	}
	setRegistration(registration);
	bounds.set(0 - width / 2, 0 - height / 2, width, height);
	skewX1 = 0;
	skewY1 = 0;
	skewX2 = 0;
	skewY2 = 0;
	skewX3 = 0;
	skewY3 = 0;
	skewX4 = 0;
	skewY4 = 0;
    }

    public DynamicSprite(TextureRegion region, float x, float y) {
	float width = 0;
	float height = 0;
	if (region != null) {
	    sprite = new Sprite(region);
	    width = region.getRegionWidth();
	    height = region.getRegionHeight();
	}
	position.set(x, y);
	setRegistration(registration);
	bounds.set(x - width / 2, y - height / 2, width, height);
	skewX1 = 0;
	skewY1 = 0;
	skewX2 = 0;
	skewY2 = 0;
	skewX3 = 0;
	skewY3 = 0;
	skewX4 = 0;
	skewY4 = 0;
    }

    public DynamicSprite(TextureRegion region, float x, float y, DynamicRegistration registration) {
	float width = 0, height = 0;
	region = new TextureRegion(region);
	if (region != null) {
	    sprite = new Sprite(region);
	    width = region.getRegionWidth();
	    height = region.getRegionHeight();
	}
	position.set(x, y);
	setRegistration(registration);
	bounds.set(x - width / 2, y - height / 2, width, height);
	skewX1 = 0;
	skewY1 = 0;
	skewX2 = 0;
	skewY2 = 0;
	skewX3 = 0;
	skewY3 = 0;
	skewX4 = 0;
	skewY4 = 0;
    }

    @Override
    public void dispose() {
	sprite.getTexture().dispose();
    }

    @Override
    public Rectangle getBounds() {
	float x = position.x, y = position.y;
	float width = sprite.getRegionWidth(), height = sprite.getRegionHeight();
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
		bounds.y = y - height / 2;
		break;
	    case CENTER_RIGHT:
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
    public float getHeight() {
	return sprite.getHeight();
    }

    @Override
    public float getRotation() {
	return sprite.getRotation();
    }

    /**
     * @return the skewX1
     */
    public float getSkewX1() {
	return skewX1;
    }

    /**
     * @return the skewX2
     */
    public float getSkewX2() {
	return skewX2;
    }

    /**
     * @return the skewX3
     */
    public float getSkewX3() {
	return skewX3;
    }

    /**
     * @return the skewX4
     */
    public float getSkewX4() {
	return skewX4;
    }

    /**
     * @return the skewY1
     */
    public float getSkewY1() {
	return skewY1;
    }

    /**
     * @return the skewY2
     */
    public float getSkewY2() {
	return skewY2;
    }

    /**
     * @return the skewY3
     */
    public float getSkewY3() {
	return skewY3;
    }

    /**
     * @return the skewY4
     */
    public float getSkewY4() {
	return skewY4;
    }

    public Texture getTexture() {
	return sprite.getTexture();
    }

    @Override
    public int getValues(DynamicDisplay target, int type, float[] returnValues) {
	switch (type) {
	    case POSITION_X:
		returnValues[0] = target.position.x;
		return 1;
	    case POSITION_Y:
		returnValues[0] = target.position.y;
		return 1;
	    case POSITION_XY:
		returnValues[0] = target.position.x;
		returnValues[1] = target.position.y;
		return 2;
	    case SCALE_X:
		returnValues[0] = target.scale.x;
		return 1;
	    case SCALE_Y:
		returnValues[0] = target.scale.y;
		return 1;
	    case SCALE_XY:
		returnValues[0] = target.scale.x;
		returnValues[1] = target.scale.y;
		return 2;
	    case ROTATION:
		returnValues[0] = target.rotation;
		return 1;
	    case COLOR_R:
		returnValues[0] = target.color.r;
		return 1;
	    case COLOR_G:
		returnValues[0] = target.color.g;
		return 1;
	    case COLOR_B:
		returnValues[0] = target.color.b;
		return 1;
	    case COLOR_A:
		returnValues[0] = target.color.a;
		return 1;
	    case COLOR_RGB:
		returnValues[0] = target.color.r;
		returnValues[1] = target.color.g;
		returnValues[2] = target.color.b;
		return 3;
	    case COLOR_RGBA:
		returnValues[0] = target.color.r;
		returnValues[1] = target.color.g;
		returnValues[2] = target.color.b;
		returnValues[3] = target.color.a;
		return 4;
	    case SKEW_XY1:
		returnValues[0] = ((DynamicSprite) target).getSkewX1();
		returnValues[1] = ((DynamicSprite) target).getSkewY1();
		return 2;
	    case SKEW_XY2:
		returnValues[0] = ((DynamicSprite) target).getSkewX2();
		returnValues[1] = ((DynamicSprite) target).getSkewY2();
		return 2;
	    case SKEW_XY3:
		returnValues[0] = ((DynamicSprite) target).getSkewX3();
		returnValues[1] = ((DynamicSprite) target).getSkewY3();
		return 2;
	    case SKEW_XY4:
		returnValues[0] = ((DynamicSprite) target).getSkewX4();
		returnValues[1] = ((DynamicSprite) target).getSkewY4();
		return 2;
	    case SKEW_X1:
		returnValues[0] = ((DynamicSprite) target).getSkewX1();
		return 1;
	    case SKEW_X2:
		returnValues[0] = ((DynamicSprite) target).getSkewX2();
		return 1;
	    case SKEW_X3:
		returnValues[0] = ((DynamicSprite) target).getSkewX3();
		return 1;
	    case SKEW_X4:
		returnValues[0] = ((DynamicSprite) target).getSkewX4();
		return 1;
	    case SKEW_Y1:
		returnValues[0] = ((DynamicSprite) target).getSkewY1();
		return 1;
	    case SKEW_Y2:
		returnValues[0] = ((DynamicSprite) target).getSkewY2();
		return 1;
	    case SKEW_Y3:
		returnValues[0] = ((DynamicSprite) target).getSkewY3();
		return 1;
	    case SKEW_Y4:
		returnValues[0] = ((DynamicSprite) target).getSkewY4();
		return 1;
	    default:
		assert false;
		return -1;
	}
    }

    @Override
    public float getWidth() {
	return sprite.getWidth();
    }

    public Tween interpolateSkewX1(float targetSkewX1, int duration, boolean autoStart) {
	tween = Tween.to(this, SKEW_X1, duration).target(targetSkewX1);
	setTweenDeltaTime(System.currentTimeMillis());
	if (autoStart)
	    tween.start(getTweenManager());
	return tween;
    }

    public Tween interpolateSkewX2(float targetSkewX2, int duration, boolean autoStart) {
	tween = Tween.to(this, SKEW_X2, duration).target(targetSkewX2);
	setTweenDeltaTime(System.currentTimeMillis());
	if (autoStart)
	    tween.start(getTweenManager());
	return tween;
    }

    public Tween interpolateSkewX3(float targetSkewX3, int duration, boolean autoStart) {
	tween = Tween.to(this, SKEW_X3, duration).target(targetSkewX3);
	setTweenDeltaTime(System.currentTimeMillis());
	if (autoStart)
	    tween.start(getTweenManager());
	return tween;
    }

    public Tween interpolateSkewX4(float targetSkewX4, int duration, boolean autoStart) {
	tween = Tween.to(this, SKEW_X4, duration).target(targetSkewX4);
	setTweenDeltaTime(System.currentTimeMillis());
	if (autoStart)
	    tween.start(getTweenManager());
	return tween;
    }

    public Tween interpolateSkewXY1(float targetSkewX1, float targetSkewY1, int duration, boolean autoStart) {
	tween = Tween.to(this, SKEW_XY1, duration).target(targetSkewX1, targetSkewY1);
	setTweenDeltaTime(System.currentTimeMillis());
	if (autoStart)
	    tween.start(getTweenManager());
	return tween;
    }

    public Tween interpolateSkewXY2(float targetSkewX2, float targetSkewY2, int duration, boolean autoStart) {
	tween = Tween.to(this, SKEW_XY2, duration).target(targetSkewX2, targetSkewY2);
	setTweenDeltaTime(System.currentTimeMillis());
	if (autoStart)
	    tween.start(getTweenManager());
	return tween;
    }

    public Tween interpolateSkewXY3(float targetSkewX3, float targetSkewY3, int duration, boolean autoStart) {
	tween = Tween.to(this, SKEW_XY3, duration).target(targetSkewX3, targetSkewY3);
	setTweenDeltaTime(System.currentTimeMillis());
	if (autoStart)
	    tween.start(getTweenManager());
	return tween;
    }

    public Tween interpolateSkewXY4(float targetSkewX4, float targetSkewY4, int duration, boolean autoStart) {
	tween = Tween.to(this, SKEW_XY4, duration).target(targetSkewX4, targetSkewY4);
	setTweenDeltaTime(System.currentTimeMillis());
	if (autoStart)
	    tween.start(getTweenManager());
	return tween;
    }

    public Tween interpolateSkewY1(float targetSkewY1, int duration, boolean autoStart) {
	tween = Tween.to(this, SKEW_Y1, duration).target(targetSkewY1);
	setTweenDeltaTime(System.currentTimeMillis());
	if (autoStart)
	    tween.start(getTweenManager());
	return tween;
    }

    public Tween interpolateSkewY2(float targetSkewY2, int duration, boolean autoStart) {
	tween = Tween.to(this, SKEW_Y2, duration).target(targetSkewY2);
	setTweenDeltaTime(System.currentTimeMillis());
	if (autoStart)
	    tween.start(getTweenManager());
	return tween;
    }

    public Tween interpolateSkewY3(float targetSkewY3, int duration, boolean autoStart) {
	tween = Tween.to(this, SKEW_Y3, duration).target(targetSkewY3);
	setTweenDeltaTime(System.currentTimeMillis());
	if (autoStart)
	    tween.start(getTweenManager());
	return tween;
    }

    public Tween interpolateSkewY4(float targetSkewY4, int duration, boolean autoStart) {
	tween = Tween.to(this, SKEW_Y4, duration).target(targetSkewY4);
	setTweenDeltaTime(System.currentTimeMillis());
	if (autoStart)
	    tween.start(getTweenManager());
	return tween;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
	if (visible) {
	    spriteBatch.setColor(color);
	    float x = position.x, y = position.y;
	    float width = sprite.getRegionWidth(), height = sprite.getRegionHeight();
	    float skewX1 = this.skewX1, skewX2 = this.skewX2, skewX3 = this.skewX3, skewX4 = this.skewX4;
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
	    sprite.setPosition(x, y);
	    sprite.setOrigin(origin.x, origin.y);
	    sprite.setScale(scale.x, scale.y);
	    sprite.setRotation(rotation);
	    sprite.setColor(color);
	    sprite.getVertices()[SpriteBatch.X1] += skewX1;
	    sprite.getVertices()[SpriteBatch.Y1] += skewY1;
	    sprite.getVertices()[SpriteBatch.X2] += skewX2;
	    sprite.getVertices()[SpriteBatch.Y2] += skewY2;
	    sprite.getVertices()[SpriteBatch.X3] += skewX3;
	    sprite.getVertices()[SpriteBatch.Y3] += skewY3;
	    sprite.getVertices()[SpriteBatch.X4] += skewX4;
	    sprite.getVertices()[SpriteBatch.Y4] += skewY4;
	    sprite.draw(spriteBatch);
	    //spriteBatch.draw(sprite, x, y, origin.x, origin.y, sprite.getRegionWidth(), sprite.getRegionHeight(), scale.x, scale.y, rotation);
	}
    }

    @Override
    public void setRegistration(DynamicRegistration registration) {
	this.registration = registration;
    }

    /**
     * @param skewX1 the skewX1 to set
     */
    public void setSkewX1(float skewX1) {
	this.skewX1 = skewX1;
    }

    /**
     * @param skewX2 the skewX2 to set
     */
    public void setSkewX2(float skewX2) {
	this.skewX2 = skewX2;
    }

    /**
     * @param skewX3 the skewX3 to set
     */
    public void setSkewX3(float skewX3) {
	this.skewX3 = skewX3;
    }

    /**
     * @param skewX4 the skewX4 to set
     */
    public void setSkewX4(float skewX4) {
	this.skewX4 = skewX4;
    }

    public void setSkewXY1(float skewX1, float skewY1) {
	this.skewX1 = skewX1;
	this.skewY1 = skewY1;
    }

    public void setSkewXY2(float skewX2, float skewY2) {
	this.skewX2 = skewX2;
	this.skewY2 = skewY2;
    }

    public void setSkewXY3(float skewX3, float skewY3) {
	this.skewX3 = skewX3;
	this.skewY3 = skewY3;
    }

    public void setSkewXY4(float skewX4, float skewY4) {
	this.skewX4 = skewX4;
	this.skewY4 = skewY4;
    }

    /**
     * @param skewY1 the skewY1 to set
     */
    public void setSkewY1(float skewY1) {
	this.skewY1 = skewY1;
    }

    /**
     * @param skewY2 the skewY2 to set
     */
    public void setSkewY2(float skewY2) {
	this.skewY2 = skewY2;
    }

    /**
     * @param skewY3 the skewY3 to set
     */
    public void setSkewY3(float skewY3) {
	this.skewY3 = skewY3;
    }

    /**
     * @param skewY4 the skewY4 to set
     */
    public void setSkewY4(float skewY4) {
	this.skewY4 = skewY4;
    }

    @Override
    public void setValues(DynamicDisplay target, int type, float[] newValues) {
	switch (type) {
	    case POSITION_X:
		target.position.x = newValues[0];
		break;
	    case POSITION_Y:
		target.position.y = newValues[0];
		break;
	    case POSITION_XY:
		target.position.set(newValues[0], newValues[1]);
		break;
	    case SCALE_X:
		target.scale.x = newValues[0];
		break;
	    case SCALE_Y:
		target.scale.y = newValues[0];
		break;
	    case SCALE_XY:
		target.scale.set(newValues[0], newValues[1]);
		break;
	    case ROTATION:
		target.rotation = newValues[0];
		break;
	    case COLOR_R:
		target.color.r = newValues[0];
		break;
	    case COLOR_G:
		target.color.g = newValues[0];
		break;
	    case COLOR_B:
		target.color.b = newValues[0];
		break;
	    case COLOR_A:
		target.color.a = newValues[0];
		break;
	    case COLOR_RGB:
		target.color.r = newValues[0];
		target.color.g = newValues[1];
		target.color.b = newValues[2];
		break;
	    case COLOR_RGBA:
		target.color.r = newValues[0];
		target.color.g = newValues[1];
		target.color.b = newValues[2];
		target.color.a = newValues[3];
		break;
	    case SKEW_XY1:
		((DynamicSprite) target).setSkewXY1(newValues[0], newValues[1]);
		break;
	    case SKEW_XY2:
		((DynamicSprite) target).setSkewXY2(newValues[0], newValues[1]);
		break;
	    case SKEW_XY3:
		((DynamicSprite) target).setSkewXY3(newValues[0], newValues[1]);
		break;
	    case SKEW_XY4:
		((DynamicSprite) target).setSkewXY4(newValues[0], newValues[1]);
		break;
	    case SKEW_X1:
		((DynamicSprite) target).setSkewX1(newValues[0]);
		break;
	    case SKEW_X2:
		((DynamicSprite) target).setSkewX2(newValues[0]);
		break;
	    case SKEW_X3:
		((DynamicSprite) target).setSkewX3(newValues[0]);
		break;
	    case SKEW_X4:
		((DynamicSprite) target).setSkewX4(newValues[0]);
		break;
	    case SKEW_Y1:
		((DynamicSprite) target).setSkewY1(newValues[0]);
		break;
	    case SKEW_Y2:
		((DynamicSprite) target).setSkewY2(newValues[0]);
		break;
	    case SKEW_Y3:
		((DynamicSprite) target).setSkewY3(newValues[0]);
		break;
	    case SKEW_Y4:
		((DynamicSprite) target).setSkewY4(newValues[0]);
		break;
	    default:
		assert false;
		break;
	}
    }

    @Override
    public void update(float delta) {
	if (enabled)
	    updateTween();
    }
}
