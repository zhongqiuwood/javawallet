package com.okcoin.vault.jni.zcash;


public class ZcashNativeInvoke {
    public static native String[] execute(String networkType, String command);
}
