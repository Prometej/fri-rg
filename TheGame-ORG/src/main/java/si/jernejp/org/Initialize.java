/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.jernejp.org;

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.logging.Logger;
import org.lwjgl.BufferUtils;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateStandardCursor;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetCursor;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.nglfwGetFramebufferSize;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import org.lwjgl.opengl.GLUtil;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLDebugMessageCallback;
import si.jernejp.org.core.devices.MouseDevice;
import si.jernejp.org.core.devices.WindowDevice;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import si.jernejp.org.core.GameRuntime;
import si.jernejp.org.core.IGame;
import si.jernejp.org.core.Configuration;
import si.jernejp.org.core.devices.KeyboardDevice;
import si.jernejp.org.engine.GameEngine;
import si.jernejp.org.engine.Renderer;
import si.jernejp.org.engine.Scene;
import si.jernejp.org.engine.Transformation;
import si.jernejp.org.engine.objects.Camera;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.system.MemoryUtil.memAddress;

/**
 *
 * @author Jernej
 */
public class Initialize {

    private static final Logger LOG = Logger.getLogger("Initialize");
    private long window;
    private final Configuration configuration;
    private final WindowDevice windowDevice;
    private final MouseDevice inputDevice;
    private final KeyboardDevice keyboardDevice;
    private final IGame game;
    private GLDebugMessageCallback debugProc;

    public Initialize(Configuration configuration, WindowDevice windowDevice, MouseDevice inputDevice, KeyboardDevice keyboardDevice, IGame game) {
        this.configuration = configuration;
        this.windowDevice = windowDevice;
        this.keyboardDevice = keyboardDevice;
        this.inputDevice = inputDevice;
        this.game = game;
    }

    private void initOpenGL() throws IOException {
        glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        //glfwWindowHint(GLFW_SAMPLES, 4);

        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode vidmode = glfwGetVideoMode(monitor);
        if (!configuration.get(Boolean.class, "windowed")) {
            windowDevice.width = vidmode.width();
            windowDevice.height = vidmode.height();
        }
        window = glfwCreateWindow(windowDevice.width, windowDevice.height, configuration.get(String.class, "title"), !configuration.get(Boolean.class, "windowed") ? monitor : 0L, NULL);
        windowDevice.windowId = window;
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        glfwSetCursor(window, glfwCreateStandardCursor(configuration.get(Integer.class, "cursorType")));
        glfwSetFramebufferSizeCallback(window, windowDevice::windowCallback);
        glfwSetKeyCallback(window, keyboardDevice::keyboardCallback);
        glfwSetCursorPosCallback(window, inputDevice::cursorCallback);
        glfwSetMouseButtonCallback(window, inputDevice::mouseCallback);
        glfwMakeContextCurrent(window);
        glfwSwapInterval(0);
        glfwShowWindow(window);
        IntBuffer framebufferSize = BufferUtils.createIntBuffer(2);
        nglfwGetFramebufferSize(window, memAddress(framebufferSize), memAddress(framebufferSize) + 4);
        //width = framebufferSize.get(0);
        //height = framebufferSize.get(1);
        GLCapabilities caps = GL.createCapabilities();
        if (!caps.OpenGL20) {
            throw new AssertionError("This demo requires OpenGL 2.0.");
        }
        debugProc = (GLDebugMessageCallback) GLUtil.setupDebugMessageCallback();
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE);
    }

    public void run() {
        try {
            initOpenGL();
            Camera camera = new Camera();
            Transformation transformation = new Transformation();
            Renderer renderer = new Renderer();
            Scene scene = new Scene();
            windowDevice.addListener(inputDevice);
            windowDevice.addListener(transformation);
            GameEngine gameEngine = new GameEngine(camera, transformation, renderer, scene);
            windowDevice.initCall();
            new GameRuntime(gameEngine, windowDevice, inputDevice, keyboardDevice).loop();
            //Exit GameEngine Loop
            if (debugProc != null) {
                debugProc.free();
            }
            glfwDestroyWindow(window);
        } catch (Throwable t) {
            LOG.severe(t.toString());
            t.printStackTrace();
        } finally {
            glfwTerminate();
        }
    }

}
