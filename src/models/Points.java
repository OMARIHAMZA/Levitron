package models;

import Entities.Entity;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import textures.ModelTexture;

import java.util.ArrayList;

public class Points {
    private static ArrayList<Entity> points = new ArrayList<>();
    private static Loader loader = new Loader();

    public static void addPoint(Vector3f position){
        if (points.size() < 1000){
            RawModel model = OBJLoader.loadObjModel("point", loader, true);
            ModelTexture texture = new ModelTexture(loader.loadTexture("point"));
            TexturedModel texturedModel = new TexturedModel(model, texture);
            points.add(new Entity(texturedModel, new Vector3f(position.x,position.y,position.z) , 0 , 0 , 0 , 10));
        }
    }

    public static ArrayList<Entity> getPoints() {
        return points;
    }
}
