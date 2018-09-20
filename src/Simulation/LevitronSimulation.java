package Simulation;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import FPS.Timer;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import models.Points;
import models.SpinningTopModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import physics.MagneticField;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;

import java.io.File;
import java.util.ArrayList;

public class LevitronSimulation {
    private static boolean start = false;
    public static Timer time = new Timer();
    public static Loader loader = new Loader();

    //Labels
    private static GUIText fpsCounter, cameraText, accelerationYText, positionX, positionY, positionZ, angularVelocityY;
    //Cameras
    private static ArrayList<Camera> cameras = new ArrayList<>();
    private static Camera currentCamera;
    //Light
    private static Light light;

    private static boolean cameraChanged = false, renderText = true, clicked = false;

    public static void startSimulation() {
        DisplayManager.createDisplay();
        TextMaster.init(loader);
        MasterRenderer renderer = new MasterRenderer(loader);
        new SpinningTopModel();
        setupFonts();
        setupCameras();
        setupBase();

        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            // Calculating FPS and UPS
            time.updateTimer();
            if (time.isDelta()) {
                if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
                    if (!cameraChanged) {
                        cameraChanged = true;
                        try {
                            currentCamera = cameras.get(cameras.indexOf(currentCamera) + 1);
                        } catch (Exception e) {
                            currentCamera = cameras.get(0);
                        }
                        String cameraInfo = "Camera - " + (cameras.indexOf(currentCamera) + 1);
                        cameraInfo += " - Locked";
                        cameraText.setPosition(new Vector2f(0.88f, 0f));
                        cameraText.setPosition(new Vector2f(0.92f, 0f));
                        cameraText.setText(cameraInfo);
                    }
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
                    renderer.saveImage();
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
                    start = true;
                }
                Keyboard.isKeyDown(Keyboard.KEY_SPACE);
                if (Keyboard.isKeyDown(Keyboard.KEY_C) && !clicked) {
                    renderText = !renderText;
                    clicked = true;
                }
                for (Entity entity1 : SpinningTopModel.getSpinningTopObjects()) {
                    if (start) {
                        entity1.update(1.0 / 60);
                    }
                }
                currentCamera.move();
                time.updateUps();
            }
            if (time.isD()) {
                DisplayManager.updateDisplay();
                for (Entity entity1 : SpinningTopModel.getSpinningTopObjects()) {
                    renderer.ProcessEntity(entity1);
                }
                for (Entity entity1 : Points.getPoints()) {
                    renderer.ProcessEntity(entity1);
                }
                renderer.render(currentCamera, light);
                if (renderText) {
                    TextMaster.render();
                }
                accelerationYText.setText("  Acceleration Y: " + SpinningTopModel.getSpinningTopObjects().get(2).getAcceleration().y);
                angularVelocityY.setText("  Angular Velocity Y: " + SpinningTopModel.getSpinningTopObjects().get(2).getAngularVelocity().y);
                time.updateFps();
            }
            if (time.checkTime()) {
                fpsCounter.setText("  FPS: " + time.getFps());
                cameraChanged = false;
                clicked = false;
                DisplayManager.setTitle("FPS: " + time.getFps() + " | UPS: " + time.getUps());
                time.reset();
            }
        }
        TextMaster.cleanUp();
        loader.cleanUp();
        renderer.CleanUp();
        DisplayManager.closeDisplay();
    }

    private static void setupFonts() {
        FontType font = new FontType(loader.loadTexture("Fonts/segoeUI/segoeUI"), new File("res/Fonts/segoeUI/segoeUI.fnt"));
        fpsCounter = new GUIText("  FPS: ", 1f, font, new Vector2f(0f, 0f), 1f, false);
        cameraText = new GUIText("  Camera - 1", 1f, font, new Vector2f(0.9f, 0f), 1f, false);
        accelerationYText = new GUIText("  Acceleration: ", 1f, font, new Vector2f(0f, 0.04f), 1f, false);
        angularVelocityY = new GUIText("  Angular Velocity Y: " + SpinningTopModel.getSpinningTopObjects().get(2).getAngularVelocity().getY(), 1f, font, new Vector2f(0f, 0.08f), 1f, false);
        fpsCounter.setColour(255, 255, 255);
        cameraText.setColour(255, 255, 255);
        accelerationYText.setColour(255, 255, 255);
        angularVelocityY.setColour(255, 255, 255);
    }

    private static void setupCameras() {
        cameras.add(new Camera(new Vector3f(0, 0, -40), false, new Vector3f(0, -20, 0)));
        cameras.add(new Camera(new Vector3f(0, -60, -12), false, new Vector3f(0, -60, 0)));
        cameras.add(new Camera(new Vector3f(0, -120, -12), false, new Vector3f(0, -120, 0)));
        currentCamera = cameras.get(0);
        light = new Light(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
    }

    private static void setupBase() {
        //Drawing the base
        Points.addPoint(new Vector3f(0, 0, 0));
        for (int i = 0; i <= 150; i++) {
            Points.addPoint(new Vector3f((float) (0 + (MagneticField.a * Math.cos(i * 2.0f * Math.PI / 150.0f))),
                    0,
                    (float) (0 + (MagneticField.a * Math.sin(i * 2.0f * Math.PI / 150.0f)))));
        }
    }
}
