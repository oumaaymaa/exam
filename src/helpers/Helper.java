package helpers;

import java.awt.Color;

public class Helper {
	public static float lerp(float a, float b, float f)
	{
	    return a + f * (b - a);
	}
	
	public static int iLerp(float a, float b, float f)
	{
	    return (int) Math.floor(a + f * (b - a));
	}
	
	public static Color lerpColor(Color a, Color b, float f) {
		return new Color(iLerp(a.getRed(),b.getRed(),f),
				iLerp(a.getGreen(),b.getGreen(),f),
				iLerp(a.getBlue(),b.getBlue(),f));
	}
}
