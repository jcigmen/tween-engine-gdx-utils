import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.noobs2d.tweenengine.utils.DynamicDisplay.DynamicRegistration;
import com.noobs2d.tweenengine.utils.DynamicSprite;

public class Test extends Game {

    DynamicSprite sprite;
    SpriteBatch spriteBatch;

    public static void main(String[] args) {
	new LwjglApplication(new Test(), "tween-engine-utils test", 400, 400, false);
    }

    @Override
    public void create() {
	Texture texture = new Texture(Gdx.files.internal("data/32x32.png"));
	TextureRegion region = new TextureRegion(texture);
	sprite = new DynamicSprite(region, 200, 200);
	sprite.scale.set(3f, 3f);
	sprite.setRegistration(DynamicRegistration.TOP_RIGHT);
	sprite.interpolateRotation(360, Linear.INOUT, 1000, true);

	spriteBatch = new SpriteBatch();
    }

    @Override
    public void render() {
	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	spriteBatch.begin();
	sprite.render(spriteBatch);
	sprite.update(Gdx.graphics.getDeltaTime());
	spriteBatch.end();
    }
}
