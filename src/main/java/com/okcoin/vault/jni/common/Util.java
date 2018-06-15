package com.okcoin.vault.jni.common;

public class Util {
    public static void dump(String context, String[] result) {

        System.out.printf("//-------------------------------------------------------\n");
        System.out.println("// Dump result returned from JNI: " + context);
        for (int i = 0; i < result.length; ++i) {
            System.out.println("// " + result[i]);
        }
        System.out.println("//-------------------------------------------------------");
    }
}
