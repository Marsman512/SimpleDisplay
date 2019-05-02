package me.marsman512.simpleDisplay.input.gamepad;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWGamepadState;

public class GamepadState {
	
	public GamepadState() {
		
	}
	
	private boolean[] buttons = new boolean[GLFW_GAMEPAD_BUTTON_LAST + 1];
	private float[] axes = new float[GLFW_GAMEPAD_AXIS_LAST + 1];
	
	public void set(GLFWGamepadState value) {
		for(int i = 0; i < value.buttons().capacity(); i++) {
			buttons[i] = (value.buttons(i) == GLFW_PRESS);
		}
		
		for(int i = 0; i < value.axes().capacity(); i++) {
			axes[i] = value.axes(i);
		}
	}
	
	public void set(GamepadState other) {
		for(int i = 0; i < other.buttons.length; i++) {
			buttons[i] = other.buttons[i];
		}
		
		for(int i = 0; i < other.axes.length; i++) {
			axes[i] = other.axes[i];
		}
	}
	
	public boolean getButton(int id) {
		return buttons[id];
	}
	
	public float getAxis(int id) {
		return axes[id];
	}
	
}
