package me.marsman512.simpleDisplay;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWVidMode;

// Just pretend this is a struct.
// I don't feel like adding getters or setters.

public class MonitorAttribs {
	public final int width, height, redBits, greenBits, blueBits, refreshRate;
	
	private MonitorAttribs(int width, int height, int redBits, int greenBits, int blueBits, int refreshRate) {
		this.width = width;
		this.height = height;
		this.redBits = redBits;
		this.greenBits = greenBits;
		this.blueBits = blueBits;
		this.refreshRate = refreshRate;
	}

	public static MonitorAttribs getMonitorAttribs(int monitor) {
		GLFWVidMode vidMode = glfwGetVideoMode(Display.getInternalMonitorID(monitor));
		MonitorAttribs toReturn = new MonitorAttribs
				(
						vidMode.width(),
						vidMode.height(),
						vidMode.redBits(),
						vidMode.greenBits(),
						vidMode.blueBits(),
						vidMode.refreshRate()
				);
		return toReturn;
	}
}
