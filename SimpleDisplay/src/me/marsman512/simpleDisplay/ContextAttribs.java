package me.marsman512.simpleDisplay;

// Just pretend this is a struct.
// I don't feel like adding getters or setters.
public class ContextAttribs {
	public int majorVersion = 1, minorVersion = 1;
	public boolean coreProfile = false, forwardCompat = false, debugContext = false;
	
	/**
	 * @return Settings for an OpenGL 3.2 context
	 */
	public static ContextAttribs GL32() {
		ContextAttribs toReturn = new ContextAttribs();
		toReturn.majorVersion = 3;
		toReturn.minorVersion = 2;
		return toReturn;
	}
	
	/**
	 * @return settings for the minimum supported context supported by MacOSX.
	 */
	public static ContextAttribs MacOSXContext() {
		ContextAttribs toReturn = GL32();
		toReturn.coreProfile = true;
		toReturn.forwardCompat = true;
		
		return toReturn;
	}
	
	/**
	 * Compare two versions of OpenGL.
	 * @param other
	 */
	public boolean before(ContextAttribs other) {
		if(this.majorVersion < other.majorVersion)
			return true;
		if(this.majorVersion == other.majorVersion && this.minorVersion < other.minorVersion)
			return true;
		return false;
	}
	
	/**
	 * Compare two versions of OpenGL.
	 * @param other
	 */
	
	public boolean after(ContextAttribs other) {
		if(this.majorVersion > other.majorVersion)
			return true;
		if(this.majorVersion == other.majorVersion && this.minorVersion > other.minorVersion)
			return true;
		return false;
	}
}
