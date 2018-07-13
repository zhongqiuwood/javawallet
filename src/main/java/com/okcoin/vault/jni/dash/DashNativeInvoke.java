package com.okcoin.vault.jni.dash;


public class DashNativeInvoke {
    public static native String[] execute(String networkType, String command);
}
