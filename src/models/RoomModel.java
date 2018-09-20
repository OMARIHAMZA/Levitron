package models;

import Entities.Entity;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import textures.ModelTexture;

import java.util.ArrayList;

public class RoomModel {
    private static ArrayList<Entity> roomParts = new ArrayList<>();
    private Loader loader = new Loader();
    private RawModel model;
    private ModelTexture texture;
    private TexturedModel texturedModel;

    public RoomModel() {


     /*   //0 Sofas
        model = OBJLoader.loadObjModel("Room/Sofas/sofas", loader);
        texture=new ModelTexture(loader.loadTexture("Room/Sofas/red_texture"));
        texturedModel =  new TexturedModel(model,texture);
        roomParts.add(new Entity(texturedModel , new Vector3f(0,0,0) , 0 , 0 , 0 , 1));

        //1 Floor
        model = OBJLoader.loadObjModel("Room/Floor/floor", loader);
        texture=new ModelTexture(loader.loadTexture("Room/Floor/tile"));
        texturedModel =  new TexturedModel(model,texture);
        roomParts.add(new Entity(texturedModel , new Vector3f(0,0,0) , 0 , 0 , 0 , 1));

        //2 Gray Pillow
        model = OBJLoader.loadObjModel("Room/Gray pillow/gray pillow", loader);
        texture=new ModelTexture(loader.loadTexture("Room/Gray pillow/gray pillow"));
        texturedModel =  new TexturedModel(model,texture);
        roomParts.add(new Entity(texturedModel , new Vector3f(0,0,0) , 0 , 0 , 0 , 1));

        //3 Black Pillow
        model = OBJLoader.loadObjModel("Room/Black pillow/black pillow", loader);
        texture=new ModelTexture(loader.loadTexture("Room/Black pillow/black pillow"));
        texturedModel =  new TexturedModel(model,texture);
        roomParts.add(new Entity(texturedModel , new Vector3f(0,0,0) , 0 , 0 , 0 , 1));

        //4 Carpet
        model = OBJLoader.loadObjModel("Room/Carpet/carpet", loader);
        texture=new ModelTexture(loader.loadTexture("Room/Carpet/carpet"));
        texturedModel =  new TexturedModel(model,texture);
        roomParts.add(new Entity(texturedModel , new Vector3f(0,0,0) , 0 , 0 , 0 , 1));

        //5 Desk
        model = OBJLoader.loadObjModel("Room/Desk/desk", loader);
        texture=new ModelTexture(loader.loadTexture("Room/Desk/desk"));
        texturedModel =  new TexturedModel(model,texture);
        roomParts.add(new Entity(texturedModel , new Vector3f(-200,0,0) , 0 , 0 , 0 , 1));
                    *//* Walls *//*
        //Left wall
        model = OBJLoader.loadObjModel("Room/Walls/Left Wall/left wall", loader);
        texture=new ModelTexture(loader.loadTexture("Room/Walls/walls_texture"));
        texturedModel =  new TexturedModel(model,texture);
        roomParts.add(new Entity(texturedModel , new Vector3f(0,0,0) , 180 , 180 , 180 , 1));

        //Left wall Shelf
        model = OBJLoader.loadObjModel("Room/Walls/Left Wall/Shelf/shelf", loader);
        texture=new ModelTexture(loader.loadTexture("Room/Walls/Left Wall/Shelf/shelf"));
        texturedModel =  new TexturedModel(model,texture);
        roomParts.add(new Entity(texturedModel , new Vector3f(-4000,0,0) , 0 , 0 , 0 , 1));

        //Left wall Shelf Sculpture
        model = OBJLoader.loadObjModel("Room/Walls/Left Wall/Shelf/Sculpture/sculpture", loader);
        texture=new ModelTexture(loader.loadTexture("Room/Walls/Left Wall/Shelf/Sculpture/sculpture"));
        texturedModel =  new TexturedModel(model,texture);
        roomParts.add(new Entity(texturedModel , new Vector3f(-200,0,0) , 0 , 0 , 0 , 1));

        //Front Wall
        model = OBJLoader.loadObjModel("Room/Walls/Front Wall/front wall", loader);
        texture=new ModelTexture(loader.loadTexture("Room/Walls/walls_texture"));
        texturedModel =  new TexturedModel(model,texture);
        roomParts.add(new Entity(texturedModel , new Vector3f(0,0,0) , 0 , 180 ,0  , 1));

        //Ceiling
        model = OBJLoader.loadObjModel("Room/Ceiling/Ceiling", loader);
        texture=new ModelTexture(loader.loadTexture("Room/Walls/walls_texture"));
        texturedModel =  new TexturedModel(model,texture);
        roomParts.add(new Entity(texturedModel , new Vector3f(0,0,0) , 0 , 0 ,0  , 1));

        //Gibson Board
        model = OBJLoader.loadObjModel("Room/Ceiling/Gibson Board", loader);
        texture=new ModelTexture(loader.loadTexture("Room/Ceiling/gibson"));
        texturedModel =  new TexturedModel(model,texture);
        roomParts.add(new Entity(texturedModel , new Vector3f(0,0,0) , 0 , 0 ,0  , 1));

       *//* //Paint
        model = OBJLoader.loadObjModel("Room/Walls/Back Wall/Paint", loader);
        texture=new ModelTexture(loader.loadTexture("Room/Ceiling/gibson"));
        texturedModel =  new TexturedModel(model,texture);
        roomParts.add(new Entity(texturedModel , new Vector3f(0,0,0) , 0 , 0 ,0  , 1));*//*

        //Right Wall
        model = OBJLoader.loadObjModel("Room/Walls/Right Wall/Right wall", loader);
        texture=new ModelTexture(loader.loadTexture("Room/Walls/walls_texture"));
        texturedModel =  new TexturedModel(model,texture);
        roomParts.add(new Entity(texturedModel , new Vector3f(0,0,0) , 0 , 0 ,0  , 1));

        //Right Wall Shelf
        model = OBJLoader.loadObjModel("Room/Walls/Right Wall/Shelf/Shelf", loader);
        texture=new ModelTexture(loader.loadTexture("Room/Walls/Left Wall/Shelf/shelf"));
        texturedModel =  new TexturedModel(model,texture);
        roomParts.add(new Entity(texturedModel , new Vector3f(0,0,0) , 0 , 0 ,0  , 1));

        //TV
        model = OBJLoader.loadObjModel("Room/Walls/Right Wall/TV/tv", loader);
        texture=new ModelTexture(loader.loadTexture("Room/Walls/Left Wall/Shelf/shelf"));
        texturedModel =  new TexturedModel(model,texture);
        roomParts.add(new Entity(texturedModel , new Vector3f(0,0,0) , 0 , 0 ,0  , 1));

        //CUP
        model = OBJLoader.loadObjModel("Room/Walls/Right Wall/CUP/cup", loader);
        texture=new ModelTexture(loader.loadTexture("Room/Walls/Right Wall/CUP/cup"));
        texturedModel =  new TexturedModel(model,texture);
        roomParts.add(new Entity(texturedModel , new Vector3f(0,0,0) , 0 , 0 ,0  , 1));

        //Window Frame
        model = OBJLoader.loadObjModel("Room/Window Frame/Window Frame", loader);
        texture=new ModelTexture(loader.loadTexture("Room/Window Frame/Window Frame"));
        texturedModel =  new TexturedModel(model,texture);
        roomParts.add(new Entity(texturedModel , new Vector3f(0,0,0) , 0 , 0 ,0  , 1));

*/


    }
    public static ArrayList<Entity> getRoomPart(){
        return roomParts;
    }
    public static Entity getDeskEntity(){
      return roomParts.get(5);
    }
}
