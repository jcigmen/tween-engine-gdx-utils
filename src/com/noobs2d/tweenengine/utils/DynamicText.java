package com.noobs2d.tweenengine.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class DynamicText extends DynamicDisplay {

    private BitmapFont bitmapFont;

    private String text;

    private HAlignment alignment;

    private float wrapWidth;

    public DynamicText(BitmapFont bitmapFont, String text) {
	this.bitmapFont = new BitmapFont(bitmapFont.getData().getFontFile(), bitmapFont.getRegion(), false);
	this.text = text;
	alignment = HAlignment.CENTER;
	wrapWidth = Gdx.graphics.getWidth();
	setRegistration(registration);
    }

    public DynamicText(BitmapFont bitmapFont, String text, HAlignment alignment) {
	this.bitmapFont = new BitmapFont(bitmapFont.getData().getFontFile(), bitmapFont.getRegion(), false);
	this.text = text;
	this.alignment = alignment;
	wrapWidth = Gdx.graphics.getWidth();
	setRegistration(registration);
    }

    @Override
    public void dispose() {
	bitmapFont.dispose();
    }

    public BitmapFont getBitmapFont() {
	return bitmapFont;
    }

    @Override
    public Rectangle getBounds() {
	float x = position.x, y = position.y;
	float width = bitmapFont.getWrappedBounds(text, wrapWidth * scale.x).width * scale.x, height = bitmapFont.getWrappedBounds(text, wrapWidth * scale.x).height * scale.y;
	bounds.x = x;
	bounds.y = y;
	bounds.width = width;
	bounds.height = height;
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
	    case CENTER_RIGHT:
		bounds.x = x - width;
		bounds.y = y - height / 2;
		break;
	    case CENTER_LEFT:
		bounds.x = x - width / 2;
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

    public String getText() {
	return text;
    }

    public float getWrapWidth() {
	return wrapWidth;
    }

    @Override
    public void render(SpriteBatch batch) {
	if (visible) {
	    batch.setColor(color);
	    float x = position.x, y = position.y;
	    float width = bitmapFont.getBounds(text).width, height = bitmapFont.getWrappedBounds(text, wrapWidth * scale.x).height;
	    switch (registration) {
		case BOTTOM_CENTER:
		    x -= width / 2;
		    y += height;
		    break;
		case BOTTOM_LEFT:
		    y += height;
		    break;
		case BOTTOM_RIGHT:
		    x -= width;
		    y += height;
		    break;
		case CENTER_CENTER:
		    x -= width / 2;
		    y += height / 2;
		    break;
		case CENTER_LEFT:
		    y += height / 2;
		    break;
		case TOP_CENTER:
		    x -= width / 2;
		    break;
		case TOP_RIGHT:
		    x -= width;
		    break;
		case CENTER_RIGHT:
		    x -= width;
		    y += height / 2;
		    break;
	    }
	    bitmapFont.drawWrapped(batch, text, x, y, width, alignment);
	}
    }

    public void setBitmapFont(BitmapFont bitmapFont) {
	this.bitmapFont = bitmapFont;
    }

    @Override
    public void setRegistration(DynamicRegistration registration) {
	this.registration = registration;
	float x = position.x, y = position.y;
	float width = bitmapFont.getBounds(text).width * scale.x, height = bitmapFont.getWrappedBounds(text, wrapWidth * scale.x).height * scale.y;
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
    public void setScale(float scale) {
	super.scale.set(scale, scale);
	bitmapFont.setScale(scale, scale);
    }

    @Override
    public void setScale(float scaleX, float scaleY) {
	scale.set(scaleX, scaleY);
	bitmapFont.setScale(scaleX, scaleY);
    }

    public void setText(String text) {
	this.text = text;
    }

    public void setWrapWidth(float wrapWidth) {
	this.wrapWidth = wrapWidth;
    }

    @Override
    public void update(float delta) {
	if (enabled) {
	    bitmapFont.setColor(color);
	    bitmapFont.setScale(scale.x, scale.y);
	    updateTween();
	}
    }

}
