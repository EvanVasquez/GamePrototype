package com.prototypeplatform.game.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.prototypeplatform.game.GamePro;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewPort; // new camera for the hud, stays only in one place.

    private Integer worldTimer;
    private float timerCount;
    private Integer score;

    Label countdownLabel; // Scene2D calls widgets as Labels
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label PlayerNameLabel;

    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timerCount = 0;
        score = 0;
        viewPort = new FitViewport(GamePro.V_WIDTH,GamePro.V_HEIGHT, new OrthographicCamera());

        //kinda like an empty box.
        stage = new Stage(viewPort, sb);


        // table organizes whats in the stage.
        Table table = new Table();
        table.top();

        //Table is now the size of the stage.
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //String format tell how the String will be represented, new LabelStyle is going to use BitmapFont and its going to be white.

        scoreLabel = new Label(String.format("%6d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        levelLabel = new Label("Level 1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("World 1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        PlayerNameLabel = new Label("This is the HUD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(PlayerNameLabel).expandX().top();
//        table.add(worldLabel).expandX().top();
//        table.add(timeLabel).expandX().top();
//        table.row(); //makes a new row for everything else;
//        table.add(scoreLabel).expandX().top();
//        table.add(levelLabel).expandX().top();
//        table.add(countdownLabel).expand().top();

        stage.addActor(table); //adds table to stage.
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
