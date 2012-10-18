package com.noobs2d.tweenengine.utils;

import java.util.List;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.audio.Sound;

public class DynamicCallback {

    public static final class InvisibleOnEnd implements TweenCallback {

	final DynamicDisplay target;

	public InvisibleOnEnd(DynamicDisplay target) {
	    this.target = target;
	}

	@Override
	public void onEvent(int eventType, BaseTween<?> baseTween) {
	    if (eventType == TweenCallback.COMPLETE)
		target.visible = false;
	}

    }

    public static final class OnAfterDelay implements TweenCallback {

	final Sound sound;

	public OnAfterDelay(Sound sound) {
	    this.sound = sound;
	}

	@Override
	public void onEvent(int eventType, BaseTween<?> baseTween) {
	    if (eventType == TweenCallback.ANY && baseTween.getCurrentTime() > baseTween.getDelay())
		sound.play();
	}

    }

    public static final class RemoveFromCollectionOnEnd implements TweenCallback {

	final List<?> collection;
	final DynamicDisplay target;

	public RemoveFromCollectionOnEnd(List<?> collection, DynamicDisplay target) {
	    this.collection = collection;
	    this.target = target;
	}

	@Override
	public void onEvent(int eventType, BaseTween<?> baseTween) {
	    if (eventType == TweenCallback.COMPLETE)
		collection.remove(target);
	}
    }

    public static final class ReturnValues implements TweenCallback {

	final DynamicDisplay source;
	final DynamicDisplay target;

	public ReturnValues(DynamicDisplay source) {
	    target = source;
	    this.source = new DynamicSprite(null, source.position.x, source.position.y);
	    this.source.scale.set(source.scale);
	}

	@Override
	public void onEvent(int eventType, BaseTween<?> baseTween) {
	    if (eventType == TweenCallback.COMPLETE) {
		target.position.set(source.position);
		target.scale.set(source.scale);
	    }
	}

    }

    public static final class VisibleOnStart implements TweenCallback {

	final DynamicDisplay target;

	public VisibleOnStart(DynamicDisplay target) {
	    this.target = target;
	}

	@Override
	public void onEvent(int eventType, BaseTween<?> source) {
	    if (source.getCurrentTime() >= source.getDelay())
		target.visible = true;
	}

    }
}
