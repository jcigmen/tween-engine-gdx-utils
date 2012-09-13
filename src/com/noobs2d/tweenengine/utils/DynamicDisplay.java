package com.noobs2d.tweenengine.utils;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * A wrapper class for easy implementation of the tween-engine. A DynamicDisplay can be
 * interpolated, assigned a registration point, and has a rectangle bounds. The only method that
 * must be called in an instance of DynamicDisplay is the render(SpriteBatch) and update(float)
 * methods.
 */
public abstract class DynamicDisplay implements TweenAccessor<DynamicDisplay> {

    protected static final int TWEEN_X = 1;
    protected static final int TWEEN_Y = 2;
    protected static final int TWEEN_XY = 3;
    protected static final int TWEEN_SCALE_X = 4;
    protected static final int TWEEN_SCALE_Y = 5;
    protected static final int TWEEN_SCALE_XY = 6;
    protected static final int TWEEN_ROTATION = 7;
    protected static final int TWEEN_ALPHA = 8;
    protected static final int TWEEN_RGB = 10;
    protected static final int TWEEN_RGBA = 11;
    protected static final int TWEEN_ALL = 12;

    public enum DynamicRegistration {
	TOP_LEFT, TOP_CENTER, TOP_RIGHT, LEFT_CENTER, CENTER_CENTER, RIGHT_CENTER, BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT;
    }

    protected Rectangle bounds = new Rectangle();

    public DynamicRegistration registration = DynamicRegistration.CENTER_CENTER;
    public Vector2 position = new Vector2(0, 0);
    public Vector2 scale = new Vector2(1, 1);
    public Color color = new Color(1f, 1f, 1f, 1f);
    public float rotation = 0f;

    /**
     * Whether this cannot and will not invoke collision callbacks.
     */
    public boolean enabled = true;

    /**
     * Whether this will not be rendered.
     */
    public boolean visible = true;

    public Tween tween;
    public TweenCallback tweenCallback;
    public long tweenDeltaTime = System.currentTimeMillis();
    public TweenManager tweenManager = new TweenManager();
    public float tweenSpeed = 1f;
    public Vector2 origin = new Vector2(0, 0);

    /** Only invoke once. If not called, tween will not work. */
    public static void register() {
	Tween.setCombinedAttributesLimit(9);
	Tween.registerAccessor(DynamicDisplay.class, new DynamicDisplay() {

	    @Override
	    public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	    }

	    @Override
	    public void render(SpriteBatch spriteBatch) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void setRegistration(DynamicRegistration registration) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void update(float deltaTime) {
		// TODO Auto-generated method stub

	    }
	});
    }

    /**
     * Get the Rectangle bounds of this DynamicDisplay.
     * 
     * @return Rectangle bounds
     */
    public abstract Rectangle getBounds();

    @Override
    public int getValues(DynamicDisplay target, int type, float[] returnValues) {
	switch (type) {
	    case TWEEN_X:
		returnValues[0] = target.position.x;
		return 1;
	    case TWEEN_Y:
		returnValues[0] = target.position.y;
		return 1;
	    case TWEEN_XY:
		returnValues[0] = target.position.x;
		returnValues[1] = target.position.y;
		return 2;
	    case TWEEN_SCALE_X:
		returnValues[0] = target.scale.x;
		return 1;
	    case TWEEN_SCALE_Y:
		returnValues[0] = target.scale.y;
		return 1;
	    case TWEEN_SCALE_XY:
		returnValues[0] = target.scale.x;
		returnValues[1] = target.scale.y;
		return 2;
	    case TWEEN_ROTATION:
		returnValues[0] = target.rotation;
		return 1;
	    case TWEEN_ALPHA:
		returnValues[0] = target.color.a;
		return 1;
	    case TWEEN_RGB:
		returnValues[0] = target.color.r;
		returnValues[1] = target.color.g;
		returnValues[2] = target.color.b;
		return 3;
	    case TWEEN_RGBA:
		returnValues[0] = target.color.r;
		returnValues[1] = target.color.g;
		returnValues[2] = target.color.b;
		returnValues[3] = target.color.a;
		return 4;
	    case TWEEN_ALL:
		returnValues[0] = target.position.x;
		returnValues[1] = target.position.y;
		returnValues[2] = target.scale.x;
		returnValues[3] = target.scale.y;
		returnValues[4] = target.rotation;
		returnValues[5] = target.color.r;
		returnValues[6] = target.color.g;
		returnValues[7] = target.color.b;
		returnValues[8] = target.color.a;
		return 9;
	    default:
		assert false;
		return -1;
	}
    }

    /**
     * Tween the properties, targeting the properties from the argument DynamicDisplay.
     * 
     * @param target Source DynamicDisplay which the target values will come from.
     * @param equation TweenEquation to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow Tween.repeat().
     * @return Tween instance.
     */
    public Tween interpolateAll(DynamicDisplay target, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, TWEEN_ALL, duration);
	float[] targetValues = new float[9];
	targetValues[0] = target.position.x;
	targetValues[1] = target.position.y;
	targetValues[2] = target.scale.x;
	targetValues[3] = target.scale.y;
	targetValues[4] = target.rotation;
	targetValues[5] = target.color.r;
	targetValues[6] = target.color.g;
	targetValues[7] = target.color.b;
	targetValues[8] = target.color.a;
	tween.target(targetValues);
	tween.ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the alpha property of the color.
     * 
     * @param alpha Target alpha.
     * @param equation TweenEquation to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow Tween.repeat().
     * @return Tween instance.
     */
    public Tween interpolateAlpha(float alpha, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, TWEEN_ALPHA, duration);
	tween.target(alpha);
	tween.ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the color property.
     * 
     * @param rgb Must be a float array with a length of EXACTLY 3.
     * @param equation TweenEquation to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow Tween.repeat().
     * @return Tween instance.
     */
    public Tween interpolateRGB(float[] rgb, TweenEquation equation, int duration, boolean autoStart) {
	if (rgb.length != 3)
	    throw new IllegalArgumentException("The argument array must have a length of exactly 3 (R, G, B).");
	tween = Tween.to(this, TWEEN_RGB, duration);
	tween.target(rgb[0], rgb[1], rgb[2]);
	tween.ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the color property.
     * 
     * @param rgba Must be a float array with a length of EXACTLY 4.
     * @param equation TweenEquation to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow Tween.repeat().
     * @return Tween instance.
     */
    public Tween interpolateRGBA(float[] rgba, TweenEquation equation, int duration, boolean autoStart) {
	if (rgba.length != 4)
	    throw new IllegalArgumentException("The argument array must have a length of exactly 4 (R, G, B, A).");
	tween = Tween.to(this, TWEEN_RGBA, duration);
	tween.target(rgba[0], rgba[1], rgba[2], rgba[3]);
	tween.ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the rotation property.
     * 
     * @param rotation Target rotation.
     * @param equation TweenEquation to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow Tween.repeat().
     * @return Tween instance.
     */
    public Tween interpolateRotation(float rotation, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, TWEEN_ROTATION, duration);
	tween.target(rotation);
	tween.ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the x-scale.
     * 
     * @param scaleX Target x-scale.
     * @param equation TweenEquation to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow Tween.repeat().
     * @return Tween instance.
     */
    public Tween interpolateScaleX(float scaleX, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, TWEEN_SCALE_X, duration);
	tween.target(scaleX);
	tween.ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the X and Y scale.
     * 
     * @param scale Vector with the x and y as the target scale.
     * @param equation TweenEquation to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow Tween.repeat().
     * @return Tween instance.
     */
    public Tween interpolateScaleXY(Vector2 scale, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, TWEEN_SCALE_XY, duration);
	tween.target(scale.x, scale.y);
	tween.ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the y-scale.
     * 
     * @param scaleY Target y-scale.
     * @param equation TweenEquation to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow Tween.repeat().
     * @return Tween instance.
     */
    public Tween interpolateScaleY(float scaleY, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, TWEEN_SCALE_Y, duration);
	tween.target(scaleY);
	tween.ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the x position.
     * 
     * @param x Target x position.
     * @param equation TweenEquation to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow Tween.repeat().
     * @return Tween instance.
     */
    public Tween interpolateX(float x, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, TWEEN_X, duration);
	tween.target(x);
	tween.ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the X and Y position.
     * 
     * @param position Vector that holds the target X and Y position.
     * @param equation TweenEquation to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow Tween.repeat().
     * @return Tween instance.
     */
    public Tween interpolateXY(Vector2 position, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, TWEEN_XY, duration);
	tween.target(position.x, position.y);
	tween.ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the Y position.
     * 
     * @param scaleY Target Y position.
     * @param equation TweenEquation to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow Tween.repeat().
     * @return Tween instance.
     */
    public Tween interpolateY(float y, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, TWEEN_Y, duration);
	tween.target(y);
	tween.ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /** Draw this display. */
    public abstract void render(SpriteBatch spriteBatch);

    /** Depends on the display dimensions. Sets the registration and origin. */
    public abstract void setRegistration(DynamicRegistration registration);

    @Override
    public void setValues(DynamicDisplay target, int type, float[] newValues) {
	switch (type) {
	    case TWEEN_X:
		target.position.x = newValues[0];
		break;
	    case TWEEN_Y:
		target.position.y = newValues[0];
		break;
	    case TWEEN_XY:
		target.position.set(newValues[0], newValues[1]);
		break;
	    case TWEEN_SCALE_X:
		target.scale.x = newValues[0];
		break;
	    case TWEEN_SCALE_Y:
		target.scale.y = newValues[0];
		break;
	    case TWEEN_SCALE_XY:
		target.scale.set(newValues[0], newValues[1]);
		break;
	    case TWEEN_ROTATION:
		target.rotation = newValues[0];
		break;
	    case TWEEN_ALPHA:
		target.color.a = newValues[0];
		break;
	    case TWEEN_RGB:
		target.color.r = newValues[0];
		target.color.g = newValues[1];
		target.color.b = newValues[2];
		break;
	    case TWEEN_RGBA:
		target.color.r = newValues[0];
		target.color.g = newValues[1];
		target.color.b = newValues[2];
		target.color.a = newValues[3];
	    case TWEEN_ALL:
		target.position.set(newValues[0], newValues[1]);
		target.scale.set(newValues[2], newValues[3]);
		target.rotation = newValues[4];
		target.color.set(newValues[5], newValues[6], newValues[7], newValues[8]);
		break;
	}
    }

    /** Basically should call updateBounds() and updateTween() */
    public abstract void update(float deltaTime);

    /** If not called, the tween won't work. */
    protected void updateTween() {
	tweenManager.update((int) (System.currentTimeMillis() - tweenDeltaTime) * tweenSpeed);
	tweenDeltaTime = System.currentTimeMillis();
    }
}
