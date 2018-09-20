package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT= 600;
	private static final int FPS_CAP=120;

	private static long lastFrameTime;
	private static long delta;

	public static void createDisplay() {
		ContextAttribs cattribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
		PixelFormat    pformat = new PixelFormat();
		try {

			Display.create(pformat.withSamples(8), cattribs);
			/*DisplayMode displayMode = null;
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			for (int i = 0; i < Display.getAvailableDisplayModes().length; i++) {
				if (modes[i].getWidth() == WIDTH
						&& modes[i].getHeight() == HEIGHT
						&& modes[i].isFullscreenCapable())
				{
					displayMode = modes[i];
				}
			}
			Display.setFullscreen(false);
			Display.setDisplayMode(displayMode);*/
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();
	}
	
	public static void updateDisplay() {
		Display.update();
		long currentFameTime = getCurrentTime();
		delta = (currentFameTime - lastFrameTime)/1000;
		lastFrameTime = currentFameTime;



	}

	public static float getFrameTimeSeconds(){
		return delta;
	}
	
	public static void closeDisplay() {
		
		Display.destroy();
	}
	public static void setTitle(String title){
		Display.setTitle(title);
	}

	private static long getCurrentTime(){
		return Sys.getTime() * 100/Sys.getTimerResolution();
	}
	
	
	
	
	
	
	
	

}
