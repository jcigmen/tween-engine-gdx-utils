import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.noobs2d.tweenengine.utils.DynamicText;

public class Test extends Game {

    DynamicSprite sprite;
    DynamicText text;
    Stage stage;
    SpriteBatch spriteBatch;

    public static void main(String[] args) {
	new LwjglApplication(new Test(), "tween-engine-utils test", 1280, 800, false);
    }

    @Override
    public void create() {
	Texture texture = new Texture(Gdx.files.internal("data/64x64.png"));
	TextureRegion region = new TextureRegion(texture);
	sprite = new DynamicSprite(region, 128, 128);
	// skew values are additive; meaning their values do not represent coordinates in the plane but values that will added into the existing skew, which is by default 0.
	// in this sample, we mean to say 'increase the X3 and Y3 skew by 32 over 1k milliseconds'.
	sprite.interpolateSkewX2(-10f, 2000, false).repeatYoyo(Tween.INFINITY, 2000).start(sprite.tweenManager);
	sprite.interpolateSkewX3(-10f, 2000, false).repeatYoyo(Tween.INFINITY, 2000).start(sprite.tweenManager);
	sprite.interpolateSkewX2(10f, 2000, false).delay(4000).repeatYoyo(Tween.INFINITY, 2000).start(sprite.tweenManager);
	sprite.interpolateSkewX3(10f, 2000, false).delay(4000).repeatYoyo(Tween.INFINITY, 2000).start(sprite.tweenManager);

	BitmapFont font = new BitmapFont(Gdx.files.internal("data/PRESS2P.fnt"), false);
	//	font.setFixedWidthGlyphs("Test LabelStyle");

	text = new DynamicText(font, "Test DynamicText");
	//	text.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	text.setPosition(0, 300);
	text.setScale(5f, 5f);
	LabelStyle style = new LabelStyle(font, Color.WHITE);
	Label label = new Label("Test LabelStyle and watever", style);

	stage = new Stage();
	stage.addActor(label);

	spriteBatch = new SpriteBatch();
    }

    @Override
    public void render() {
	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	stage.draw();
	spriteBatch.begin();
	sprite.render(spriteBatch);
	sprite.update(Gdx.graphics.getDeltaTime());
	text.render(spriteBatch);
	text.update(Gdx.graphics.getDeltaTime());
	spriteBatch.end();
    }
}
