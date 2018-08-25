package com.prototypeplatform.game.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.prototypeplatform.game.GamePro;
import com.prototypeplatform.game.scenes.Hud;
import com.prototypeplatform.game.sprites.character;
import com.prototypeplatform.game.tools.B2WorldCreator;

import static sun.audio.AudioPlayer.player;


public class PlayScreen implements Screen {
    private GamePro game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private character player;

    // variables used for the Tiled Map.

    // what loads the map.
    private TmxMapLoader maploader;

    // reference to the map itself.
    private TiledMap map;

    // what renders the map itself
    private OrthogonalTiledMapRenderer renderer;

    // variables used for Box2D
    private World world;
    private Box2DDebugRenderer b2dr;

    public PlayScreen(GamePro game){
        this.game = game;

        // this is the creation of the camera.
        gamecam = new OrthographicCamera();

        // this gives the game a black bar to maintain the aspect ratio of the game.
        gamePort = new FitViewport(GamePro.V_WIDTH / GamePro.PPM, GamePro.V_HEIGHT /GamePro.PPM , gamecam);

        // creates the hun for the game
        hud = new Hud(game.batch);


        // Loads the map
        maploader = new TmxMapLoader();
        map = maploader.load("maper.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/GamePro.PPM);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()*2 -50,0);



        world = new World(new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();

        //creating the charater.
        player = new character(world);

        new B2WorldCreator(world,map);

    }


    @Override
    public void show() {

    }
    public void handleInput(float dt){

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2 ){
            player.b2body.applyLinearImpulse(new Vector2(1f, 0), player.b2body.getWorldCenter(), true);
        }


        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            player.b2body.applyLinearImpulse(new Vector2(-1f, 0), player.b2body.getWorldCenter(), true);
        }

        // if the screen is pressed
//        if(Gdx.input.isTouched()){
//            gamecam.position.x += 100 * dt;
////            gamecam.position.y -= 50 * dt;
//        }

    }

    public void update(float dt){
        handleInput(dt);

        world.step(1/60f,6,2);

        float yPos = (float) (player.b2body.getPosition().y + 0.25);
        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.position.y = yPos;



        gamecam.update();

        // renders what game cam can see
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        // need to research this more. if its not added the map will not load properly;
        update(delta);

        //clears the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // renders the game map
        renderer.render();

        b2dr.render(world, gamecam.combined);



        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        // updates the ViewPort relative to width and height.
        gamePort.update(width,height);

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
        map.dispose();
        world.dispose();
        renderer.dispose();
        b2dr.dispose();
        hud.dispose();

    }
}
