package com.prototypeplatform.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.prototypeplatform.game.GamePro;
import com.prototypeplatform.game.screens.PlayScreen;

public class character extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion idleCharacter;

    public character(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("i8aic2"));
        this.world = world;
        defineCharacter();
        idleCharacter = new TextureRegion(getTexture(),2,2,16,16);
        setBounds(0,0,16/GamePro.PPM, 16/GamePro.PPM);
        setRegion(idleCharacter);
    }
    public void defineCharacter(){
        BodyDef bdef = new BodyDef();
        bdef.position.set((32/ GamePro.PPM),(385/ GamePro.PPM));
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5/ GamePro.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}




