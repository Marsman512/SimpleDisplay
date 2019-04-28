package me.marsman512.simpleDisplay.input;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;

import me.marsman512.simpleDisplay.*;

public class Mouse {
	
	// Default MouseEventHandler object
	public static final MouseEventHandler defMouseHandler = new MouseEventHandler() {

		@Override
		public void mousePressed(int button, int mods) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(int button) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(double x, double y) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseScrolled(double amt) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	// Handler tracker
	private static MouseEventHandler currentHandler = defMouseHandler;
	
	// Getters and setters
	public static MouseEventHandler getCurrentHandler() {
		return currentHandler;
	}
	
	public static void setCurrentHandler(MouseEventHandler value) {
		if(value != null)
			currentHandler = value;
		else
			throw new NullPointerException("You can't have a null MouseEventHandler!");
	}
	
	// GLFW callbacks
	private static final GLFWMouseButtonCallbackI mouseBCB = (long window, int button, int action, int mods) -> {
		if(action == GLFW_PRESS)
			currentHandler.mousePressed(button, mods);
		else if(action == GLFW_RELEASE)
			currentHandler.mouseReleased(button);
	};
	
	private static final GLFWCursorPosCallbackI mousePosCB = (long window, double x, double y) -> currentHandler.mouseMoved(x, y);
	private static final GLFWScrollCallbackI mouseScrollCB = (long window, double x, double y) -> currentHandler.mouseScrolled(y);
	
	/**
	 * Used in Display.start(). Please do not use this method!
	 */
	public static void init() {
		long windowID = Display.getWindowID();
		glfwSetMouseButtonCallback(windowID, mouseBCB);
		glfwSetCursorPosCallback(windowID, mousePosCB);
		glfwSetScrollCallback(windowID, mouseScrollCB);
		// O-o
	}
}
