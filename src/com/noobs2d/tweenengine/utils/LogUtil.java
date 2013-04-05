package com.noobs2d.tweenengine.utils;

/**
 * For the sole purpose of printing logs.
 * 
 * @author MrUseL3tter
 */
public class LogUtil {

    @SuppressWarnings("rawtypes")
    public static void print(Class classObject, String methodName, String message) {
	System.out.println("[" + System.nanoTime() + " - " + classObject.getCanonicalName() + "#" + methodName + "] " + message);
    }
}
