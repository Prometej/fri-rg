/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.jernejp.org.core.devices;

import java.util.LinkedList;
import java.util.List;
import si.jernejp.org.core.events.WindowChangeListener;

/**
 *
 * @author Jernej
 */
public class WindowDevice {

    public long windowId;
    public int width = 0;
    public int height = 0;
    private final List<WindowChangeListener> changeListeners = new LinkedList<>();

    public void windowCallback(long window, int width, int height) {
        if (width > 0 && height > 0 && (this.width != width || this.height != height)) {
            this.width = width;
            this.height = height;
            for (WindowChangeListener changeListener : changeListeners) {
                System.out.println("Notified" + changeListener.toString());
                changeListener.notifyChange(width, height);
            }
        }
    }

    public void initCall() {
        for (WindowChangeListener changeListener : changeListeners) {
            System.out.println("Notified" + changeListener.toString());
            changeListener.notifyChange(width, height);
        }
    }

    public void addListener(WindowChangeListener listener) {
        changeListeners.add(listener);
    }
}
