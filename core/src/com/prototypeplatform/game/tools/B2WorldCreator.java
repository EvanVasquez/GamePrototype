package com.prototypeplatform.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.prototypeplatform.game.GamePro;
import com.prototypeplatform.game.sprites.character;
import com.prototypeplatform.game.sprites.door;
import com.prototypeplatform.game.sprites.spikes;

public class B2WorldCreator {

    public B2WorldCreator(World world, TiledMap map){

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        Shape newb;
        FixtureDef fdef = new FixtureDef();
        Body body;

//        // body for polygon tiles.
//        for(MapObject object : map.getLayers().get(2).getObjects().getByType(PolygonMapObject.class)){
//
//            Polygon polygon = ((PolygonMapObject) object).getPolygon();
//
//            bdef.type = BodyDef.BodyType.StaticBody;
//            bdef.position.set( polygon.getX(), polygon.getY());
//
//            body = world.createBody(bdef);
//
//
//            float[] new_vertices = polygon.getVertices().clone();
//
//            shape.set(new_vertices);
//            fdef.shape = shape;
//            body.createFixture(fdef);
//
//
//        }

        // body for polyline tiles.
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(PolylineMapObject.class)){

            float [] vertices = ((PolylineMapObject) object).getPolyline().getTransformedVertices();
            Vector2[] worldVertices = new Vector2[vertices.length/2];

            for(int i = 0; i < worldVertices.length; i++){
                worldVertices[i] = new Vector2(vertices[i*2]/ GamePro.PPM,vertices[i*2+1]/GamePro.PPM);
            }
            ChainShape cs = new ChainShape();
            cs.createChain(worldVertices);

            newb = cs;

            bdef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bdef);
            body.createFixture(newb, 1.0f);
            newb.dispose();



//            fdef.shape = shape;
//            body.createFixture(fdef);


        }

        // body and fixtures for the ground
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ GamePro.PPM, (rect.getY() + rect.getHeight()/2) / GamePro.PPM );

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth()/2)/GamePro.PPM , (rect.getHeight()/2)/GamePro.PPM);
            fdef.shape =shape;
            body.createFixture(fdef);
        }


        //body and fixtures for the spikes
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new spikes(world, map, rect);
        }

        //body and fixtures for the door
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

           new door(world,map,rect);
        }
    }
}
