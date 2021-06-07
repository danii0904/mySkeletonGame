package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class menuGame implements Screen {

    final Skeleton game;
    OrthographicCamera camera;

    public menuGame(Skeleton game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Skeleton.WIDTH, Skeleton.HEIGHT);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(new Texture(Gdx.files.internal("background.png")),0 ,0);

        game.font.setColor(Color.WHITE );
        game.font.draw(game.batch, "Skeleton Dance!", 50, 550);
        game.batch.end();

        if ( (Gdx.input.isTouched())){
            game.setScreen(new mainGame(game));
            dispose();
        }


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

    }
}
