package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class mainGame extends ApplicationAdapter implements Screen {

	Skeleton game;

	private static final int FRAME_COLS = 9, FRAME_ROWS = 4;

	OrthographicCamera camera;
	Animation<TextureRegion> walkLeftAnimation;
	Animation<TextureRegion> walkRightAnimation;
	Animation<TextureRegion> walkUpAnimation;
	Animation<TextureRegion> walkDownAnimation;



	private Texture walkSheet;
	private SpriteBatch spriteBatch;
	private Rectangle skeleton;


	float stateTime;

	public mainGame(Skeleton game) {

		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Skeleton.WIDTH, Skeleton.HEIGHT);

		spriteBatch = new SpriteBatch();
		walkSheet = new Texture(Gdx.files.internal("skeleton_sprite.png"));

		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS,
				walkSheet.getHeight() / FRAME_ROWS);

		TextureRegion[] walkLeft = new TextureRegion[FRAME_COLS];
		TextureRegion[] walkRight = new TextureRegion[FRAME_COLS];
		TextureRegion[] walkUp = new TextureRegion[FRAME_COLS];
		TextureRegion[] walkDown = new TextureRegion[FRAME_COLS];

		int index = 0;

		for  (int i = 0; i < FRAME_ROWS; i++){
			index = 0;
			for(int j = 0; j < FRAME_COLS; j++){
				if (i == 0)
					walkUp[index++] = tmp[i][j];
				if (i == 1)
					walkLeft[index++] = tmp[i][j];
				if (i == 2)
					walkDown[index++] = tmp[i][j];
				if (i == 3)
					walkRight[index++] = tmp[i][j];
			}
		}

		walkLeftAnimation = new Animation<TextureRegion>(0.025f, walkLeft);
		walkRightAnimation = new Animation<TextureRegion>(0.025f, walkRight);
		walkUpAnimation = new Animation<TextureRegion>(0.025f, walkUp);
		walkDownAnimation = new Animation<TextureRegion>(0.025f, walkDown);

		skeleton = new Rectangle();
		skeleton.x = 100;
		skeleton.y = 100;
		skeleton.width = 60;
		skeleton.height = 60;

		stateTime = 0f;

	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(new Texture(Gdx.files.internal("background.png")), 0, 0);


		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			game.batch.draw(walkLeftAnimation.getKeyFrame(stateTime, true), skeleton.x, skeleton.y);
			skeleton.x -= 100 * Gdx.graphics.getDeltaTime();
		}

		else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			game.batch.draw(walkRightAnimation.getKeyFrame(stateTime, true), skeleton.x, skeleton.y);
			skeleton.x += 100 * Gdx.graphics.getDeltaTime();
		}

		else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			game.batch.draw(walkUpAnimation.getKeyFrame(stateTime, true), skeleton.x, skeleton.y);
			skeleton.y += 100 * Gdx.graphics.getDeltaTime();
		}

		else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			game.batch.draw(walkDownAnimation.getKeyFrame(stateTime, true), skeleton.x, skeleton.y);
			skeleton.y -= 100 * Gdx.graphics.getDeltaTime();
		}

		else{
			game.batch.draw(walkDownAnimation.getKeyFrames()[1], skeleton.x, skeleton.y, skeleton.width, skeleton.height);
		}

		game.batch.end();

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
	}
}
