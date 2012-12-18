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

    protected static final int POSITION_X = 0x01;

    protected static final int POSITION_Y = 0x02;
    protected static final int POSITION_XY = 0x03;
    protected static final int SCALE_X = 0x04;
    protected static final int SCALE_Y = 0x05;
    protected static final int SCALE_XY = 0x06;
    protected static final int ROTATION = 0x07;
    protected static final int COLOR_R = 0x08;
    protected static final int COLOR_G = 0x09;
    protected static final int COLOR_B = 0xA;
    protected static final int COLOR_A = 0xB;
    protected static final int COLOR_RGB = 0xC;
    protected static final int COLOR_RGBA = 0xD;

    public enum DynamicRegistration {
	TOP_LEFT, TOP_CENTER, TOP_RIGHT, CENTER_LEFT, CENTER_CENTER, CENTER_RIGHT, BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT;
    }

    protected Rectangle bounds = new Rectangle();

    public DynamicRegistration registration = DynamicRegistration.CENTER_CENTER;

    public Vector2 position = new Vector2(0, 0);
    public Vector2 scale = new Vector2(1, 1);
    public Vector2 origin = new Vector2(0, 0);
    public Color color = new Color(1f, 1f, 1f, 1f);
    private String name = "";
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

    public Color getColor() {
	return color;
    }

    public float getHeight() {
	return 0;
    }

    public String getName() {
	return name;
    }

    public Vector2 getOrigin() {
	return origin;
    }

    public float getOriginX() {
	return origin.x;
    }

    public float getOriginY() {
	return origin.y;
    }

    public Vector2 getPosition() {
	return position;
    }

    public float getRotation() {
	return rotation;
    }

    public float getScaleX() {
	return scale.x;
    }

    public float getScaleY() {
	return scale.y;
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
	    default:
		assert false;
		return -1;
	}
    }

    public float getWidth() {
	return 0;
    }

    public float getX() {
	return position.x;
    }

    public float getY() {
	return position.y;
    }

    /**
     * Tween the A-region of the color.
     * 
     * @param targetAlpha Target A-region of the color.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateAlpha(float targetAlpha, int duration, boolean autoStart) {
	tween = Tween.to(this, COLOR_A, duration).target(targetAlpha);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the A-region of the color with easing {@link TweenEquation}.
     * 
     * @param targetAlpha Target A-region of the color.
     * @param equation {@link TweenEquation} easing to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateAlpha(float targetAlpha, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, COLOR_A, duration).target(targetAlpha).ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the B-region of the color.
     * 
     * @param targetB Target R-region of the color.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateColorB(float targetB, int duration, boolean autoStart) {
	tween = Tween.to(this, COLOR_B, duration).target(targetB);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the B-region of the color with easing {@link TweenEquation}.
     * 
     * @param targetB Target R-region of the color.
     * @param equation {@link TweenEquation} easing to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateColorB(float targetB, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, COLOR_B, duration).target(targetB).ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the G-region of the color.
     * 
     * @param targetG Target R-region of the color.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateColorG(float targetG, int duration, boolean autoStart) {
	tween = Tween.to(this, COLOR_G, duration).target(targetG);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the G-region of the color with easing {@link TweenEquation}.
     * 
     * @param targetG Target R-region of the color.
     * @param equation {@link TweenEquation} easing to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateColorG(float targetG, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, COLOR_G, duration).target(targetG).ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the R-region of the color.
     * 
     * @param targetR Target R-region of the color.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateColorR(float targetR, int duration, boolean autoStart) {
	tween = Tween.to(this, COLOR_R, duration).target(targetR);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the R-region of the color with easing {@link TweenEquation}.
     * 
     * @param targetR Target R-region of the color.
     * @param equation {@link TweenEquation} easing to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateColorR(float targetR, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, COLOR_R, duration).target(targetR).ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the RGB-region of the color.
     * 
     * @param targetR Target R-region of the color.
     * @param targetG Target G-region of the color.
     * @param targetB Target B-region of the color.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateColorRGB(float targetR, float targetG, float targetB, int duration, boolean autoStart) {
	tween = Tween.to(this, COLOR_RGB, duration).target(targetR, targetG, targetB);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the RGB-region of the color with easing {@link TweenEquation}.
     * 
     * @param targetR Target R-region of the color.
     * @param targetG Target G-region of the color.
     * @param targetB Target B-region of the color.
     * @param equation {@link TweenEquation} easing to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateColorRGB(float targetR, float targetG, float targetB, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, COLOR_RGB, duration).target(targetR, targetG, targetB).ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the RGBA-region of the color.
     * 
     * @param targetR Target R-region of the color.
     * @param targetG Target G-region of the color.
     * @param targetB Target B-region of the color.
     * @param targetA Target A-region of the color.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateColorRGBA(float targetR, float targetG, float targetB, float targetA, int duration, boolean autoStart) {
	tween = Tween.to(this, COLOR_RGBA, duration).target(targetR, targetG, targetB, targetA);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the RGBA-region of the color with easing {@link TweenEquation}.
     * 
     * @param targetR Target R-region of the color.
     * @param targetG Target G-region of the color.
     * @param targetB Target B-region of the color.
     * @param targetA Target A-region of the color.
     * @param equation {@link TweenEquation} easing to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateColorRGBA(float targetR, float targetG, float targetB, float targetA, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, COLOR_RGBA, duration).target(targetR, targetG, targetB, targetA).ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the rotation.
     * 
     * @param targetRotation Target rotation.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateRotation(float targetRotation, int duration, boolean autoStart) {
	tween = Tween.to(this, ROTATION, duration).target(targetRotation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the rotation with easing {@link TweenEquation}.
     * 
     * @param targetRotation Target rotation.
     * @param equation {@link TweenEquation} easing to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateRotation(float targetRotation, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, ROTATION, duration).target(targetRotation).ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the x scale.
     * 
     * @param targetScaleX Target x scale.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateScaleX(float targetScaleX, int duration, boolean autoStart) {
	tween = Tween.to(this, SCALE_X, duration).target(targetScaleX);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the x scale with easing {@link TweenEquation}.
     * 
     * @param targetScaleX Target x scale.
     * @param equation {@link TweenEquation} easing to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateScaleX(float targetScaleX, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, SCALE_X, duration).target(targetScaleX).ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the x and y scale.
     * 
     * @param targetScaleX Target x scale.
     * @param targetScaleY Target y scale.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateScaleXY(float targetScaleX, float targetScaleY, int duration, boolean autoStart) {
	tween = Tween.to(this, SCALE_XY, duration).target(targetScaleX, targetScaleY);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the x and y scale with easing {@link TweenEquation}.
     * 
     * @param targetScaleX Target x scale.
     * @param targetScaleY Target y scale.
     * @param equation {@link TweenEquation} easing to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateScaleXY(float targetScaleX, float targetScaleY, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, SCALE_XY, duration).target(targetScaleX, targetScaleY).ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the y scale.
     * 
     * @param targetScaleY Target y scale.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateScaleY(float targetScaleY, int duration, boolean autoStart) {
	tween = Tween.to(this, SCALE_Y, duration).target(targetScaleY);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the y scale with easing {@link TweenEquation}.
     * 
     * @param targetScaleY Target y scale.
     * @param equation {@link TweenEquation} easing to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateScaleY(float targetScaleY, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, SCALE_Y, duration).target(targetScaleY).ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the x position.
     * 
     * @param targetX Target x position.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateX(float targetX, int duration, boolean autoStart) {
	tween = Tween.to(this, POSITION_X, duration).target(targetX);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the x position with easing {@link TweenEquation}.
     * 
     * @param targetX Target x position.
     * @param equation {@link TweenEquation} easing to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateX(float targetX, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, POSITION_X, duration).target(targetX).ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the x and y position.
     * 
     * @param targetX Target x position.
     * @param targetY Target y position.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateXY(float targetX, float targetY, int duration, boolean autoStart) {
	tween = Tween.to(this, POSITION_XY, duration).target(targetX, targetY);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the x and y position with easing {@link TweenEquation}.
     * 
     * @param targetX Target x position.
     * @param targetY Target y position.
     * @param equation {@link TweenEquation} easing to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateXY(float targetX, float targetY, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, POSITION_XY, duration).target(targetX, targetY).ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the y position.
     * 
     * @param targetY Target y position.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateY(float targetY, int duration, boolean autoStart) {
	tween = Tween.to(this, POSITION_Y, duration).target(targetY);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    /**
     * Tween the y position with easing {@link TweenEquation}.
     * 
     * @param targetY Target y position.
     * @param equation {@link TweenEquation} easing to use.
     * @param duration Duration in milliseconds.
     * @param autoStart Set to false to allow {@link Tween#repeat(int, float)}.
     * @return Tween {@link Tween} instance for chaining.
     */
    public Tween interpolateY(float targetY, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, POSITION_Y, duration).target(targetY).ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    public boolean isEnabled(boolean enabled) {
	return enabled;
    }

    public boolean isVisible() {
	return visible;
    }

    public void pause() {
	tweenManager.pause();
    }

    /** Draw this display. */
    public abstract void render(SpriteBatch spriteBatch);

    public void resume() {
	tweenManager.resume();
    }

    public void setAlpha(float alpha) {
	color.a = alpha;
    }

    public void setColor(Color color) {
	this.color = color;
    }

    public void setColor(float r, float g, float b, float a) {
	color.set(r, g, b, a);
    }

    public void setEnabled(boolean enabled) {
	this.enabled = enabled;
    }

    public void setHeight(float height) {

    }

    public void setName(String name) {
	this.name = name;
    }

    public void setOrigin(float originX, float originY) {
	origin.set(originX, originY);
    }

    public void setOriginX(float originX) {
	origin.x = originX;
    }

    public void setOriginY(float originY) {
	origin.y = originY;
    }

    public void setPosition(float x, float y) {
	position.set(x, y);
    }

    /** Depends on the display dimensions. Sets the registration and origin. */
    public abstract void setRegistration(DynamicRegistration registration);

    public void setRotation(float rotation) {
	this.rotation = rotation;
    }

    public void setScale(float scale) {
	this.scale.set(scale, scale);
    }

    public void setScale(float scaleX, float scaleY) {
	scale.set(scaleX, scaleY);
    }

    public void setScaleX(float scaleX) {
	scale.x = scaleX;
    }

    public void setScaleY(float scaleY) {
	scale.y = scaleY;
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
	    default:
		assert false;
		break;
	}
    }

    public void setVisible(boolean visible) {
	this.visible = visible;
    }

    public void setWidth(float width) {

    }

    public void setX(float x) {
	position.x = x;
    }

    public void setY(float y) {
	position.y = y;
    }

    /** Basically should call updateBounds() and updateTween() */
    public abstract void update(float deltaTime);

    /** If not called, the tween won't work. */
    protected void updateTween() {
	tweenManager.update((int) (System.currentTimeMillis() - tweenDeltaTime) * tweenSpeed);
	tweenDeltaTime = System.currentTimeMillis();
    }
}
