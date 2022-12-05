package keyActions;

import action.Action;
import controllers.IllegalTimeValueException;
import controllers.TimeController;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class KeyHolder implements Runnable {
    private final Robot robot;
    private final List<Integer> keyEventKeyCodes;
    private final Thread thread;
    private TimeController timeControllerNano = null;
    private int runningTimeSeconds;
    private Action actionOnStop;

    public KeyHolder() throws AWTException {
        robot = new Robot();
        keyEventKeyCodes = new ArrayList<>();
        runningTimeSeconds = 10;
        thread = new Thread(this);
    }

    public void setRunningTimeSeconds(int runningTimeSeconds) {
        this.runningTimeSeconds = runningTimeSeconds;
    }

    public void forceStop() {
        if (timeControllerNano != null)
            timeControllerNano.over();
    }

    public void addKey(int keycode) {
        keyEventKeyCodes.add(keycode);
    }

    public void clearAllKeys() {
        keyEventKeyCodes.clear();
    }

    public List<String> getKeyNameList() {
        List<String> ret = new ArrayList<>();
        for (Integer keyEventKeyCode : keyEventKeyCodes) {
            ret.add(KeyEvent.getKeyText(keyEventKeyCode));
        }
        return ret;
    }

    public void start() {
        if (!thread.isAlive()) {
            thread.start();
        }
    }

    public void setActionOnStop(Action actionOnStop) {
        this.actionOnStop = actionOnStop;
    }

    @Override
    public void run() {
        try {
            timeControllerNano = new TimeController((long) (runningTimeSeconds * 1E+09));
        } catch (IllegalTimeValueException e) {
            throw new RuntimeException(e);
        }
        timeControllerNano.start();
        while (!timeControllerNano.isOver()) {
            for (Integer keyEventKeyCode : keyEventKeyCodes) {
                robot.keyPress(keyEventKeyCode);
            }
            robot.delay(20);
        }
        for (Integer keyEventKeyCode : keyEventKeyCodes) {
            robot.keyRelease(keyEventKeyCode);
        }
        timeControllerNano = null;
        if (actionOnStop != null)
            actionOnStop.execute();
    }
}
