package com.prototypeplatform.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.prototypeplatform.game.GamePro;
import com.prototypeplatform.game.screens.PlayScreen;

public class character extends Sprite {
    public enum State{STANDING, JUMPING, RUNNNING, FALLING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private Animation <TextureRegion> runner;
    private Animation <TextureRegion> jumper;
    private Boolean runningRight;
    private Animation <TextureRegion> fall;
    private float stateTimer;
    private TextureRegion idleCharacter;

    public character(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("first"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;

        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //you add +70 for each frame when running.
        for(int i = 0; i < 5; i++ ){
            if(i == 0) {
                frames.add(new TextureRegion(getTexture(), 8,110,50,75));
            }
            else{
                frames.add(new TextureRegion(getTexture(), 8 + i*70, 110,50,75));
            }
         }
        runner = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();


        for(int i = 0; i < 2; i++){
            if(i == 0) {
                frames.add(new TextureRegion(getTexture(), 205,190,48,80));
            }
            else{
                frames.add(new TextureRegion(getTexture(), 205 + 45, 190,48,80));
            }
        }
        jumper = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();
        frames.add(new TextureRegion(getTexture(), 346, 190,48,75));
        fall = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();

        defineCharacter();

        //Original coordinates for main character when idle (x:8,y:30,wid: 50, height:75)
        //the y works in multiples of 80, x is 42(i think).
        idleCharacter = new TextureRegion(getTexture(),  8,30,45,75);
        setBounds(0,0,16/(GamePro.PPM -35 ), 16/(GamePro.PPM - 35));
        setRegion(idleCharacter);
    }
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2 );
        setRegion(getFrame(dt));
    }
    //The StatTimer decides what frame to pull at what time in the animation.
    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = jumper.getKeyFrame(stateTimer);
                break;
            case RUNNNING:
                region = runner.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
                region = fall.getKeyFrame(stateTimer);
                break;
            case STANDING:
            default:
                region = idleCharacter;
                break;
        }
        //this if statement identifies if the character is facing left or right.
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true,false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true,false);
            runningRight = true;
        }
        stateTimer = (currentState == previousState)? stateTimer+dt : 0;
        previousState = currentState;
        return region;
    }
    public State getState(){
        if(b2body.getLinearVelocity().y > 0){
            return State.JUMPING;
        }
        else if(b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING){
            System.out.println("FAM you should not be seeing this");
            return State.FALLING;
        }
        else if(b2body.getLinearVelocity().x != 0){
            return State.RUNNNING;
        }
        else{return State.STANDING;}

    }

    public void defineCharacter(){
        BodyDef bdef = new BodyDef();
        bdef.position.set((32/ GamePro.PPM),(385/ GamePro.PPM));
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        //defines the circle
        CircleShape shape = new CircleShape();
        //defines its size.
        shape.setRadius(7/ GamePro.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}




