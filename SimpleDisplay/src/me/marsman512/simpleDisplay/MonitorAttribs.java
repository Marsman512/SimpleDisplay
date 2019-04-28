package me.marsman512.simpleDisplay;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWVidMode;

// Just pretend this is a struct.
// I don't feel like adding getters or setters.

public class MonitorAttribs {
	public int width, height, redBits, greenBits, blueBits, refreshRate;
	private MonitorAttribs() {}
	
	
	
	public static MonitorAttribs getMonitorAttribs(int monitor) {
		MonitorAttribs toReturn = new MonitorAttribs();
		
		
		
		return toReturn;
	}
}
