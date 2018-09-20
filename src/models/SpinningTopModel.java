package models;

import Entities.Entity;
import GUI.SimulationValues;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import textures.ModelTexture;

import java.util.ArrayList;

public class SpinningTopModel {
//    public static Vector3f position = new Vector3f(0.005f, 8,  0.005f);
    public static Vector3f position = new Vector3f(SimulationValues.positionX, SimulationValues.positionY,  SimulationValues.positionZ);

    private static ArrayList<Entity> spinningTopObjects = new ArrayList<>();

    public SpinningTopModel() {

        //Body
        Loader loader = new Loader();
        RawModel model = OBJLoader.loadObjModel("SpinningTop/body/body", loader, true);
        ModelTexture texture = new ModelTexture(loader.loadTexture("SpinningTop/body/body"));
        TexturedModel texturedModel = new TexturedModel(model, texture);
        spinningTopObjects.add(new Entity(texturedModel, position , 0 , 0 , 0 , 1));
        //Body Ring
        model = OBJLoader.loadObjModel("SpinningTop/BodyRing/bodyring", loader,true);
        texture =new ModelTexture(loader.loadTexture("SpinningTop/BodyRing/bodyring"));
        texturedModel =  new TexturedModel(model, texture);
        spinningTopObjects.add(new Entity(texturedModel, position , 0 , 0 , 0 , 1));
        //Rings
        model = OBJLoader.loadObjModel("SpinningTop/rings/rings", loader,true);
        texture =new ModelTexture(loader.loadTexture("SpinningTop/rings/rings"));
        texturedModel =  new TexturedModel(model, texture);
        spinningTopObjects.add(new Entity(texturedModel, position , 0 , 0 , 0 , 1));
        //Ball
        model = OBJLoader.loadObjModel("SpinningTop/Ball/ball", loader,true);
        texture =new ModelTexture(loader.loadTexture("SpinningTop/Ball/ball"));
        texturedModel =  new TexturedModel(model, texture);
        spinningTopObjects.add(new Entity(texturedModel, position , 0 , 0 , 0 , 1));
    }

    public static void rotateModel(int rotX , int rotY , int rotZ){
        for (Entity entity:spinningTopObjects){
            entity.increaseRotation(rotX , rotY , rotZ);
        }
    }

    public static ArrayList<Entity> getSpinningTopObjects() {
        return spinningTopObjects;
    }
}
