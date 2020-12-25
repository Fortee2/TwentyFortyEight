package com.fenchurchtech.twentyfortyeight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fenchurchtech.twentyfortyeight.logic.Splash;
import com.fenchurchtech.twentyfortyeight.screens.GameScreen;
import com.fenchurchtech.twentyfortyeight.screens.SplashScreen;
import com.fenchurchtech.twentyfortyeight.screens.InputDecoder;

public class MainGame extends Game {
	public SpriteBatch batch;
	public BitmapFont bitmapFont;

	@Override
	public void create () {
		batch =  new SpriteBatch();
		bitmapFont = new BitmapFont();

		SplashScreen gs = new SplashScreen(this);
		this.setScreen(gs);

		Gdx.input.setInputProcessor(new InputDecoder(gs));

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
