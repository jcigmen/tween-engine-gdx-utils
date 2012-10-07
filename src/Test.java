import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.noobs2d.tweenengine.utils.DynamicSprite;

public class Test extends Game {

    DynamicSprite sprite;
    SpriteBatch spriteBatch;

    public static void main(String[] args) {
	new LwjglApplication(new Test(), "tween-engine-utils test", 256, 256, false);
    }

    @Override
    public void create() {
	Texture texture = new Texture(Gdx.files.internal("data/64x64.png"));
	TextureRegion region = new TextureRegion(texture);
	sprite = new DynamicSprite(region, 128, 128);
	// skew values are additive; meaning their values do not represent coordinates in the plane but values that will added into the existing skew, which is by default 0.
	// in this sample, we mean to say 'increase the X3 and Y3 skew by 32 over 1k milliseconds'.
	sprite.interpolateSkewXY3(32f, 32f, 1000, true);
	sprite.interpolateSkewXY1(-108f, -108f, 1000, true);
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
