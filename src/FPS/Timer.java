package FPS;

public class Timer {

    private long timer, lastTime, currTime;
    private double deltaTime, ns, delta, dFps, d;
    private int fps, ups;

    public void updateTimer() {
        currTime = System.nanoTime();
        long difference = currTime - lastTime;
        deltaTime = difference;
        delta += (difference) / ns;
        d += (difference) / dFps;
        lastTime = currTime;
    }

    public Timer() {
        timer = System.currentTimeMillis();
        lastTime = System.nanoTime();
        currTime = lastTime;
        ns = 1000000000 / 60.0;
        delta = 0.0;
        dFps = 1000000000 / 60.0;
        d = 0.0;
        fps = 0;
        ups = 0;
    }

    public boolean isDelta() {
        return delta >= 1.0f;
    }

    public boolean isD() {
        return d >= 1.0f;
    }

    public void updateFps() {
        fps++;
        d = 0.0;
    }

    public void updateUps() {
        delta--;
        ups++;
    }

    public void reset() {
        fps = 0;
        ups = 0;
        timer += 1000;
    }

    public int getfps() {
        return fps;
    }

    public boolean checkTime() {
        return System.currentTimeMillis() > timer + 1000;
    }

    public int getFps() {
        return fps;
    }

    public int getUps() {
        return ups;
    }

    public double returnDeltaTime() {
        return deltaTime;
    }
}
