package com.prototypeplatform.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.prototypeplatform.game.GamePro;

public class character extends Sprite {
    public World world;
    public Body b2body;

    public character(World world){
        this.world = world;
        defineCharacter();
    }
    public void defineCharacter(){
        BodyDef bdef = new BodyDef();
        bdef.position.set((32/ GamePro.PPM),(32/ GamePro.PPM));
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5/ GamePro.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}




