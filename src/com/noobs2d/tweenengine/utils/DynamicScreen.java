package com.noobs2d.tweenengine.utils;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class DynamicScreen implements InputProcessor, Screen {

    public static class DynamicCamera extends OrthographicCamera implements TweenAccessor<DynamicCamera> {

	protected static final int TWEEN_XY = 1;
	protected static final int TWEEN_ZOOM = 2;
	protected static final int TWEEN_ROTATION = 3;

	public Tween tween;

	public TweenCallback tweenCallback;
	public TweenManager tweenManager = new TweenManager();
	public long tweenDeltaTime = System.currentTimeMillis();

	public float zoom = 1f;
	public float rotation = 0f;

	/** Only invoke once. If not called, tween will not work. */
	public static void register() {
	    Tween.registerAccessor(DynamicCamera.class, new DynamicCamera());
	}

	@Override
	public int getValues(DynamicCamera target, int type, float[] returnValues) {
	    switch (type) {
		case TWEEN_XY:
		    returnValues[0] = target.position.x;
		    returnValues[1] = target.position.y;
		    return 2;
		case TWEEN_ZOOM:
		    returnValues[0] = target.zoom;
		    return 1;
		case TWEEN_ROTATION:
		    returnValues[0] = target.rotation;
		    return 1;
		default:
		    assert false;
		    return -1;
	    }
	}

	public Tween interpolate(float targetScaleX, float targetScaleY, TweenEquation equation, int duration) {
	    tween = Tween.to(this, TWEEN_ZOOM, duration).target(targetScaleX, targetScaleY).ease(equation);
	    tweenDeltaTime = System.currentTimeMillis();
	    return tween;
	}

	public Tween interpolate(int targetRotation, TweenEquation equation, int duration) {
	    tween = Tween.to(this, TWEEN_ROTATION, duration).target(targetRotation).ease(equation);
	    tweenDeltaTime = System.currentTimeMillis();
	    return tween;
	}

	public Tween interpolate(Vector2 target, TweenEquation equation, int duration) {
	    tween = Tween.to(this, TWEEN_XY, duration).target(target.x, target.y).ease(equation);
	    tweenDeltaTime = System.currentTimeMillis();
	    return tween;
	}

	@Override
	public void setValues(DynamicCamera target, int type, float[] newValues) {
	    switch (type) {
		case TWEEN_XY:
		    target.position.set(newValues[0], newValues[1], 0);
		    break;
		case TWEEN_ZOOM:
		    target.zoom = newValues[0];
		    break;
		case TWEEN_ROTATION:
		    target.rotation = newValues[0];
		    break;
	    }
	}

	public void shake() {
	    tweenManager.update(1000);
	    // interpolate(new Vector2(position.x - 50, position.y),
	    // Linear.INOUT, 40).start(tweenManager);
	    // interpolate(new Vector2(position.x + 50, position.y),
	    // Linear.INOUT, 40).delay(40).start(tweenManager);
	    // interpolate(new Vector2(position.x - 50, position.y),
	    // Linear.INOUT, 40).delay(80).start(tweenManager);
	    // interpolate(new Vector2(position.x + 50, position.y),
	    // Linear.INOUT, 40).delay(120).start(tweenManager);
	    // interpolate(new Vector2(position.x - 50, position.y),
	    // Linear.INOUT, 40).delay(160).start(tweenManager);
	    // interpolate(new Vector2(position.x + 50, position.y),
	    // Linear.INOUT, 40).delay(200).start(tweenManager);
	    // interpolate(new Vector2(position.x - 50, position.y),
	    // Linear.INOUT, 40).delay(240).start(tweenManager);
	    // interpolate(new Vector2(position.x + 50, position.y),
	    // Linear.INOUT, 40).delay(280).start(tweenManager);
	    // interpolate(new Vector2(position.x, position.y), Linear.INOUT,
	    // 40).delay(320).start(tweenManager);
	    interpolate(new Vector2(position.x - 15, position.y), Linear.INOUT, 40).start(tweenManager);
	    interpolate(new Vector2(position.x + 15, position.y), Linear.INOUT, 40).delay(15).start(tweenManager);
	    interpolate(new Vector2(position.x - 15, position.y), Linear.INOUT, 40).delay(30).start(tweenManager);
	    interpolate(new Vector2(position.x + 15, position.y), Linear.INOUT, 40).delay(45).start(tweenManager);
	    interpolate(new Vector2(position.x - 15, position.y), Linear.INOUT, 40).delay(60).start(tweenManager);
	    interpolate(new Vector2(position.x + 15, position.y), Linear.INOUT, 40).delay(75).start(tweenManager);
	    interpolate(new Vector2(position.x - 15, position.y), Linear.INOUT, 40).delay(90).start(tweenManager);
	    interpolate(new Vector2(position.x + 15, position.y), Linear.INOUT, 40).delay(105).start(tweenManager);
	    interpolate(new Vector2(position.x, position.y), Linear.INOUT, 40).delay(120).start(tweenManager);
	}

	@Override
	public void update() {
	    tweenManager.update((int) (System.currentTimeMillis() - tweenDeltaTime));
	    tweenDeltaTime = System.currentTimeMillis();
	    super.projection.setToOrtho2D(position.x, position.y, viewportWidth * zoom, viewportHeight * zoom, near, far);
	    //	    super.projection.setToOrtho2D(position.x * zoom, position.y * zoom, viewportWidth * zoom, viewportHeight * zoom, near, far);
	}

	@Override
	public void update(boolean updateFrustum) {
	    // TODO Auto-generated method stub

	}

    }

    protected AssetManager assetManager;

    protected Game game;

    protected DynamicCamera camera;

    protected SpriteBatch batch;

    public DynamicScreen(Game game) {
	super();
	Gdx.input.setInputProcessor(this);
	this.game = game;
	camera = new DynamicCamera();

	camera.viewportWidth = Gdx.graphics.getWidth();
	camera.viewportHeight = Gdx.graphics.getHeight();
	camera.near = 1;
	camera.far = -1;
	Matrix4 projection = new Matrix4();
	projection.setToOrtho2D(camera.position.x * camera.zoom, camera.position.y * camera.zoom, Gdx.graphics.getWidth() * camera.zoom, Gdx.graphics.getHeight() * camera.zoom, -1, 1);

	batch = new SpriteBatch(100);
	batch.setProjectionMatrix(projection);
    }

    public DynamicScreen(Game game, float viewportWidth, float viewportHeight) {
	super();
	Gdx.input.setInputProcessor(this);
	this.game = game;
	camera = new DynamicCamera();

	camera.viewportWidth = viewportWidth;
	camera.viewportHeight = viewportHeight;
	camera.near = -1;
	camera.far = 1;
	Matrix4 projection = new Matrix4();
	projection.setToOrtho2D(camera.position.x * camera.zoom, camera.position.y * camera.zoom, viewportWidth * camera.zoom, viewportHeight * camera.zoom, -1, 1);

	batch = new SpriteBatch(100);
	batch.setProjectionMatrix(projection);

    }

    @Override
    public void dispose() {
	// TODO Auto-generated method stub

    }

    public DynamicCamera getCamera() {
	return camera;
    }

    @Override
    public void hide() {
	// TODO Auto-generated method stub

    }

    @Override
    public boolean keyDown(int keycode) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean keyTyped(char character) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean keyUp(int keycode) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
	onTouchMove(screenX * camera.zoom, screenY * camera.zoom);
	return false;
    }

    /**
     * Invoked when a touch-down event triggers, but with coordinates already translated as due to
     * camera position. Take note that if you override this method, you MUST NOT override
     * touchDown(float, float, int, int) for this method to be invoked automatically.
     * 
     * @param x Translated X
     * @param y Translated Y
     * @param pointer
     * @param button
     */
    public void onTouchDown(float x, float y, int pointer, int button) {

    }

    public void onTouchDrag(float x, float y, float pointer) {

    }

    public void onTouchMove(float x, float y) {

    }

    public void onTouchUp(float x, float y, int pointer, int button) {

    }

    @Override
    public void pause() {
	// TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
	camera.update();
    }

    @Override
    public void resize(int viewportWidth, int viewportHeight) {
	//	camera.viewportWidth = viewportWidth;
	//	camera.viewportHeight = viewportHeight;
    }

    @Override
    public void resume() {
	// TODO Auto-generated method stub

    }

    @Override
    public boolean scrolled(int amount) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public void show() {
	Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
	//	float fx = (float) x / Gdx.graphics.getWidth() * camera.viewportWidth * camera.zoom;
	//	float fy = (float) y / Gdx.graphics.getHeight() * camera.viewportHeight * camera.zoom;
	//	System.out.println("GDX: " + Gdx.graphics.getWidth() + "\t" + Gdx.graphics.getHeight());
	//	System.out.println("CAM: " + camera.viewportWidth + "\t" + camera.viewportHeight);
	//	System.out.println("ZOOM: " + camera.zoom);
	//	System.out.println("FF: " + fx + "\t" + fy);
	//	x = (int) fx;
	//	y = (int) fy;
	//	System.out.println("FX: " + x + "\t" + y);
	//	x += camera.position.x;
	//	y += camera.position.y;
	//	x -= camera.viewportWidth * camera.zoom / 2;
	//	y -= camera.viewportHeight * camera.zoom / 2;
	onTouchDown(x * camera.zoom, y * camera.zoom, pointer, button);
	return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
	onTouchDrag(x * camera.zoom, y * camera.zoom, pointer);
	return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
	onTouchUp(x * camera.zoom, y * camera.zoom, pointer, button);
	return false;
    }
}
