package com.noobs2d.tweenengine.utils;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;

public class DynamicValue implements TweenAccessor<DynamicValue> {

    private Tween tween;
    private TweenManager tweenManager = new TweenManager();

    private long tweenDeltaTime = System.currentTimeMillis();
    private float tweenSpeed = 1f;
    private float value = 0f;

    public static void register() {
	Tween.registerAccessor(DynamicValue.class, new DynamicValue(0f));
    }

    public DynamicValue(float value) {
	this.value = value;
    }

    public DynamicValue(float value, float targetValue) {
	this.value = value;
	interpolate(targetValue, Linear.INOUT, (int) targetValue * 1000, true);
    }

    public DynamicValue(float value, float targetValue, int delay) {
	this.value = value;
	interpolate(targetValue, Linear.INOUT, (int) targetValue * 1000, true).delay(delay);
    }

    public DynamicValue(float value, float targetValue, int duration, int delay) {
	this.value = value;
	interpolate(targetValue, Linear.INOUT, duration, true).delay(delay);
    }

    public DynamicValue(float value, float targetValue, int duration, int delay, boolean autostart) {
	this.value = value;
	interpolate(targetValue, Linear.INOUT, duration, autostart).delay(delay);
    }

    public Tween getTween() {
	return tween;
    }

    public TweenManager getTweenManager() {
	return tweenManager;
    }

    public float getTweenSpeed() {
	return tweenSpeed;
    }

    public float getValue() {
	return value;
    }

    @Override
    public int getValues(DynamicValue target, int tweenType, float[] returnValues) {
	returnValues[0] = target.value;
	return 1;
    }

    public Tween interpolate(float targetValue, TweenEquation equation, int duration, boolean autoStart) {
	tween = Tween.to(this, 1, duration);
	tween.target(targetValue);
	tween.ease(equation);
	tweenDeltaTime = System.currentTimeMillis();
	if (autoStart)
	    tween.start(tweenManager);
	return tween;
    }

    public void pauseTween() {
	tweenManager.pause();
    }

    public void resumeTween() {
	tweenManager.resume();
    }

    public void setTween(Tween tween) {
	this.tween = tween;
    }

    public void setTweenCallback(TweenCallback tweenCallback) {
	tween.setCallback(tweenCallback);
    }

    public void setTweenCallbackTriggers(int flags) {
	tween.setCallbackTriggers(flags);
    }

    public void setTweenSpeed(float tweenSpeed) {
	this.tweenSpeed = tweenSpeed;
    }

    @Override
    public void setValues(DynamicValue target, int tweenType, float[] newValues) {
	target.value = newValues[0];
    }

    public void update(float deltaTime) {
	tweenManager.update((int) (System.currentTimeMillis() - tweenDeltaTime) * tweenSpeed);
	tweenDeltaTime = System.currentTimeMillis();
    }

}
