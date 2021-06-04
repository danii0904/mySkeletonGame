package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class mainGame extends ApplicationAdapter {

	Skeleton game;

	// Constant rows and columns of the sprite sheet
	private static final int FRAME_COLS = 9, FRAME_ROWS = 4;

	// Objects used
	Animation<TextureRegion> walkLeftAnimation;
	Animation<TextureRegion> walkRightAnimation;
	Animation<TextureRegion> walkUpAnimation;
	Animation<TextureRegion> walkDownAnimation;// Must declare frame type (TextureRegion)

	private Texture walkSheet;
	private SpriteBatch spriteBatch;
	private Rectangle skeleton;


	// A variable for tracking elapsed time for the animation
	float stateTime;

	public mainGame(Skeleton game) {

		this.game = game;

		spriteBatch = new SpriteBatch();
		// Load the sprite sheet as a Texture
		walkSheet = new Texture(Gdx.files.internal("skeleton_sprite.png"));


		// Use the split utility method to create a 2D array of TextureRegions. This is
		// possible because this sprite sheet contains frames of equal size and they are
		// all aligned.
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS,
				walkSheet.getHeight() / FRAME_ROWS);

		// Place the regions into a 1D array in the correct order, starting from the top
		// left, going across first. The Animation constructor requires a 1D array.

		TextureRegion[] walkLeft = new TextureRegion[FRAME_COLS];
		TextureRegion[] walkRight = new TextureRegion[FRAME_COLS];
		TextureRegion[] walkUp = new TextureRegion[FRAME_COLS];
		TextureRegion[] walkDown = new TextureRegion[FRAME_COLS];

		int index = 0;

		for  (int i = 0; i < FRAME_COLS; i++){
			index = 0;
			for(int j = 0; j < FRAME_COLS; j++){
				if (i == 0)
					walkDown[index++] = tmp[i][j];
				if (i == 1)
					walkRight[index++] = tmp[i][j];
				if (i == 2)
					walkUp[index++] = tmp[i][j];
				if (i == 3)
					walkLeft[index++] = tmp[i][j];

			}
		}

		walkLeftAnimation = new Animation<TextureRegion>(0.025f, walkLeft);
		walkRightAnimation = new Animation<TextureRegion>(0.025f, walkRight);
		walkUpAnimation = new Animation<TextureRegion>(0.025f, walkUp);
		walkDownAnimation = new Animation<TextureRegion>(0.025f, walkDown);

		skeleton = new Rectangle();
		skeleton.x = 800 / 2 - 64 / 2;
		skeleton.y = 20;
		skeleton.width = 64;
		skeleton.height = 64;

		spriteBatch = new SpriteBatch();
		stateTime = 0f;

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

		// Get current frame of animation for the current stateTime
		TextureRegion currentLeftFrame = walkLeftAnimation.getKeyFrame(stateTime, true);
		TextureRegion currentRightFrame = walkRightAnimation.getKeyFrame(stateTime, true);
		TextureRegion currentUpFrame = walkUpAnimation.getKeyFrame(stateTime, true);
		TextureRegion currentDownFrame = walkDownAnimation.getKeyFrame(stateTime, true);

		spriteBatch.begin();

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			game.batch.draw(walkLeftAnimation.getKeyFrame(stateTime, true), skeleton.x, skeleton.y);
			skeleton.x -= 100 * Gdx.graphics.getDeltaTime();
		}

		else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			spriteBatch.draw(walkRightAnimation.getKeyFrame(stateTime, true), skeleton.x, skeleton.y);
			skeleton.x += 100 * Gdx.graphics.getDeltaTime();
		}

		else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			spriteBatch.draw(walkUpAnimation.getKeyFrame(stateTime, true), skeleton.x, skeleton.y);
			skeleton.y += 100 * Gdx.graphics.getDeltaTime();
		}

		else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			spriteBatch.draw(walkDownAnimation.getKeyFrame(stateTime, true), skeleton.x, skeleton.y);
			skeleton.y -= 100 * Gdx.graphics.getDeltaTime();
		}

		else{
			game.batch.draw(walkDownAnimation.getKeyFrames()[1], skeleton.x, skeleton.y, skeleton.width, skeleton.height);
		}

		spriteBatch.end();

		spriteBatch.begin();
		spriteBatch.draw(currentLeftFrame, 50, 50); // Draw current frame at (50, 50)
		spriteBatch.draw(currentRightFrame, 50, 50); // Draw current frame at (50, 50)
		spriteBatch.draw(currentUpFrame, 50, 50); // Draw current frame at (50, 50)
		spriteBatch.draw(currentDownFrame, 50, 50); // Draw current frame at (50, 50)
		spriteBatch.end();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() { // SpriteBatches and Textures must always be disposed
		spriteBatch.dispose();
		walkSheet.dispose();
	}
}
