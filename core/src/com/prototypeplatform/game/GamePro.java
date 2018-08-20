package com.prototypeplatform.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.prototypeplatform.game.screens.PlayScreen;

public class GamePro extends Game {
	public SpriteBatch batch;
	public static int V_WIDTH = 400; // Virtual width
	public static int V_HEIGHT = 208; // Virtual Height
	public static final float PPM  = 100f;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	

}
