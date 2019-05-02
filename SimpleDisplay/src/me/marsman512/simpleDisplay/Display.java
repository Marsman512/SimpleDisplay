package me.marsman512.simpleDisplay;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.opengl.GL11C.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import java.lang.Math;

import org.lwjgl.system.MemoryStack;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL;
import org.lwjgl.PointerBuffer;

import me.marsman512.simpleDisplay.input.*;
import me.marsman512.simpleDisplay.input.gamepad.*;

public class Display {
	
	private static long f_windowID;
	private static int f_width, f_height;
	private static int f_fbWidth, f_fbHeight;
	private static int f_defX, f_defY;
	private static WindowAttribs f_defWinAttribs;
	
	// Handler
	public static final WindowEventHandler defWinHandler = new WindowEventHandler() {};
	
	private static WindowEventHandler currentHandler = defWinHandler;
	
	public static WindowEventHandler getCurrentWindowEventHandler() {
		return currentHandler;
	}
	
	public static void setCurrentWindowEventHandler(WindowEventHandler value) {
		if(value != null)
			currentHandler = value;
		else
			throw new NullPointerException("You can't have a null WindowEventHandler!");
	}
	
	// Callbacks
	private static final GLFWWindowCloseCallbackI windowCloseCB = (long windowID) -> currentHandler.windowClosed();
	
	private static final GLFWWindowSizeCallbackI windowSizeCB = (long windowID, int width, int height) -> {
		updateSizeVariables();
		currentHandler.windowResized(width, height);
	};
	
	private static final GLFWWindowIconifyCallbackI windowMinCB = (long windowID, boolean iconified) -> {
		if(iconified)
			currentHandler.windowMinimized();
		else
			currentHandler.windowRestored();
	};
	
	/**
	 * Calls glfwInit() and creates a window with the supplied parameters.
	 */
	public static void start(ContextAttribs ctx, PixelFormat pForm, WindowAttribs wAttribs) {
		f_defWinAttribs = wAttribs;
		
		// Start GLFW.
		if(!glfwInit())
			throw new IllegalStateException("Failed to initialize GLFW!");
		
		// Get the primary monitor, as we'll need it throughout this function.
		long primaryMonitor = glfwGetPrimaryMonitor();
		
		// Make sure the window hints are default.
		glfwDefaultWindowHints();
		
		// Context attributes
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, ctx.majorVersion);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, ctx.minorVersion);
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, ctx.debugContext ? GLFW_TRUE : GLFW_FALSE);
		if(ctx.before(ContextAttribs.GL32())) {
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_ANY_PROFILE);
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_FALSE);
		} else {
			glfwWindowHint(GLFW_OPENGL_PROFILE, ctx.coreProfile ? GLFW_OPENGL_CORE_PROFILE : GLFW_OPENGL_COMPAT_PROFILE);
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, ctx.forwardCompat ? GLFW_TRUE : GLFW_FALSE);
		}
		
		// Pixel format
		GLFWVidMode vidMode = glfwGetVideoMode(primaryMonitor);
		glfwWindowHint(GLFW_RED_BITS,   Math.min(pForm.redBits,   vidMode.redBits()   ));
		glfwWindowHint(GLFW_GREEN_BITS, Math.min(pForm.greenBits, vidMode.greenBits() ));
		glfwWindowHint(GLFW_BLUE_BITS,  Math.min(pForm.blueBits,  vidMode.blueBits()  ));
		glfwWindowHint(GLFW_ALPHA_BITS,   pForm.alphaBits  );
		glfwWindowHint(GLFW_DEPTH_BITS,   pForm.depthBits  );
		glfwWindowHint(GLFW_STENCIL_BITS, pForm.stencilBits);
		glfwWindowHint(GLFW_SAMPLES,      pForm.samples    );
		
		// General hints
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // glfwShowWindow() is called later
		glfwWindowHint(GLFW_RESIZABLE, wAttribs.resizable ? GLFW_TRUE : GLFW_FALSE);
		
		// Time to make the window
		f_windowID = glfwCreateWindow(wAttribs.width, wAttribs.height, wAttribs.title, 0, wAttribs.fullscreen ? primaryMonitor : 0);
		if(f_windowID == 0)
			throw new RuntimeException("Failed to create the window! Please set a GLFW error callback to learn more!");
		
		// Make sure we have the window's real size
		updateSizeVariables();
		
		// Center the window on the screen, or it'll look ugly.
		f_defX = ((vidMode.width() - f_width) / 2);
		f_defY = ((vidMode.height() - f_height) / 2);
		glfwSetWindowPos(f_windowID, f_defX, f_defY);
		
		// Create an OpenGL context
		glfwMakeContextCurrent(f_windowID);
		GL.createCapabilities(ctx.forwardCompat);
		glViewport(0, 0, f_fbWidth, f_fbHeight);
		
		// Enable v-sync, if that's your thing.
		glfwSwapInterval(wAttribs.vsync ? 1 : 0);
		
		// Start accepting keyboard, mouse, and gamepad input
		Keyboard.init();
		Mouse.init();
		Gamepad.start();
		
		// Set WindowEventHandler callbacks
		glfwSetWindowCloseCallback(f_windowID, windowCloseCB);
		glfwSetWindowIconifyCallback(f_windowID, windowMinCB);
		glfwSetWindowSizeCallback(f_windowID, windowSizeCB);
		
		// Show the window at long last
		if(wAttribs.startVisible)
			glfwShowWindow(f_windowID);
	}
	
	public static void showWindow() {
		glfwShowWindow(f_windowID);
	}
	
	/**
	 * Calls glfwPollEvents(), and swaps the windows buffers
	 */
	public static void update() {
		glfwPollEvents();
		Gamepad.update();
		
		glfwSwapBuffers(f_windowID);
	}
	
	/**
	 * Returns whether or not GLFW thinks the window should close.
	 * @return glfwWindowShouldClose(window);
	 */
	public static boolean shouldClose() {
		return glfwWindowShouldClose(f_windowID);
	}
	
	/**
	 * Use this function to close the application.
	 * DO NOT USE stop() TO CLOSE THE PROGRAM!!!
	 * USE stop() TO CLEAN UP AT THE END OF A PROGRAM!!!
	 */
	public static void setClose(boolean value) {
		glfwSetWindowShouldClose(f_windowID, value);
	}
	
	/**
	 * Destroys the window and its context, then calls glfwTerminate().
	 */
	public static void stop() {
		glfwFreeCallbacks(f_windowID);
		glfwDestroyWindow(f_windowID);
		
		glfwTerminate();
	}
	
	/**
	 * @param
	 */
	public static void setFullscreen(int monitorID) {
		if(glfwGetWindowMonitor(f_windowID) != 0)
			return;
		
		PointerBuffer monitors = glfwGetMonitors();
		if(monitorID >= monitors.capacity())
			throw new IllegalArgumentException("There is no monitor " + monitorID + "!");
		
		GLFWVidMode vidMode = glfwGetVideoMode(monitors.get(monitorID));
		glfwSetWindowMonitor(
								f_windowID,
								monitors.get(monitorID),
								0,
								0,
								Math.min(f_width, vidMode.width()), 
								Math.min(f_height, vidMode.height()), 
								vidMode.refreshRate()
							);
		
		updateSizeVariables();
	}
	
	public static void setWindowed() {
		if(glfwGetWindowMonitor(f_windowID) == 0)
			return;
		
		GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowMonitor(
				f_windowID, 0, 
				f_defX, f_defY, 
				f_defWinAttribs.width, f_defWinAttribs.height, 
				GLFW_DONT_CARE);
		
		updateSizeVariables();
	}
	
	private static void updateSizeVariables() {
		try(MemoryStack stack = MemoryStack.stackPush())
		{
			IntBuffer width = stack.callocInt(1);
			IntBuffer height = stack.callocInt(1);
			IntBuffer fbWidth = stack.callocInt(1);
			IntBuffer fbHeight = stack.callocInt(1);
			
			glfwGetWindowSize(f_windowID, width, height);
			glfwGetFramebufferSize(f_windowID, fbWidth, fbHeight);
			
			f_width = width.get(0);
			f_height = height.get(0);
			f_fbWidth = fbWidth.get(0);
			f_fbHeight = fbHeight.get(0);
		}
	}
	
	public static long getWindowID() { return f_windowID; } // Returns the GLFW window handle
	public static int getWindowWidth() { return f_width; }
	public static int getWindowHeight() { return f_height; }
	public static int getFrameBufferWidth() { return f_fbWidth; }
	public static int getFrameBufferHeight() { return f_fbHeight; }
}
