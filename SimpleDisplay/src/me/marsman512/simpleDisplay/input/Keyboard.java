package me.marsman512.simpleDisplay.input;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWCharCallbackI;

import me.marsman512.simpleDisplay.*;

public class Keyboard {
	
	// Default KeyboardEventHandler object
	public static final KeyboardEventHandler defKeyHandler = new KeyboardEventHandler() {
		@Override
		public void keyPressed(int key, int mods) {
			
		}

		@Override
		public void keyReleased(int key) {
			
		}
		
		@Override
		public void keyRepeated(int key) {
				
		}

		@Override
		public void inputChar(char c) {
			
		}
	};
	
	// KeyboardEventHandler tracker
	private static KeyboardEventHandler currentHandler = defKeyHandler;
	
	// Getter and setter
	public static KeyboardEventHandler getCurrentHandler() {
		return currentHandler;
	}
	
	public static void setCurrentHandler(KeyboardEventHandler value) {
		if(value != null)
			currentHandler = value;
		else
			throw new NullPointerException("You can't have a null KeyboardEventHandler!");
	}
	
	// GLFW callbacks
	private static GLFWKeyCallbackI keyCB = (long window, int key, int scancode, int action, int mods) -> {
		if(action == GLFW_REPEAT)
			currentHandler.keyRepeated(key);
		else if(action == GLFW_PRESS)
			currentHandler.keyPressed(key, mods);
		else if(action == GLFW_RELEASE)
			currentHandler.keyReleased(key);
	};
	
	private static GLFWCharCallbackI charCB = (long window, int codepoint) -> 
		currentHandler.inputChar((char)codepoint);
	
	// Public methods that have nothing to do with the handler
		
	/**
	 * @param key
	 * @return GLFW's name for a particular key
	 */
	public static String getKeyName(int key) {
		return glfwGetKeyName(key, 0);
	}
		
	/**
	 * This method is used in Display.start(). Please do not use it!
	 */
	public static void init() {
		long windowID = Display.getWindowID();
		glfwSetKeyCallback(windowID, keyCB);
		glfwSetCharCallback(windowID, charCB);
	}
}
