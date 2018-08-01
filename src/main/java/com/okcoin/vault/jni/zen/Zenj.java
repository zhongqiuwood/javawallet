package com.okcoin.vault.jni.zen;

import com.okcoin.vault.jni.common.Util;

public class Zenj {

    static String networkType = "main"; // or testnet

    static String cmd = null;

    static {
        System.load("/Users/oker/code/okbc/zen/libzen.dylib");
    }

    static public native String[] execute(String networkType, String command);


    public static void executeCommand(String networkType, String cmd, String context) {

        String[] results = Zenj.execute(networkType, cmd);
        Util.dump(context, results);
    }

    public static void main(String[] args)
    {
        try {
            String pk = "L5gULKo1bDyPfKvu94DjG5VY1HZESCzzSybedzQSCaz3KKd6VVmn";

            cmd ="getaddressbyprivatekey " + pk;
            Zenj.executeCommand(networkType, cmd, "getaddressbyprivatekey");

            String prevout = "[{\"txid\":\"b4cc287e58f87cdae59417329f710f3ecd75a4ee1d2872b7248f50977c8493f3\"," +
                             "\"vout\":1,\"scriptPubKey\":\"a914b10c9df5f7edf436c697f02f1efdba4cf399615187\"," +
                             "\"redeemScript\":\"512103debedc17b3df2badbcdd86d5feb4562b86fe182e5998abd8bcd4f122c6155b1b21027e940bb73ab8732bfdf7f9216ecefca5b94d6df834e77e108f68e66f126044c052ae\"}]";

            cmd = "createrawtransaction " + prevout + " " + "{\"znntDrEWVbxgwGJZS9qgZBter22KuXw5Cda\":11}";
            Zenj.executeCommand(networkType, cmd, "createrawtransaction");

            cmd = "signrawtransaction " +
                  "0100000001f393847c97508f24b772281deea475cd3e0f719f321794e5da7cf8587e28ccb40100000000ffffffff0100ab9041000000003c76a914ef1caef64ee1e2aff877b7e8e9713dc25b2289f288ac200206260143838b5ff52dc2eb7b4b8099d4e4c99dc3ef19794289a2cd4c10070000b400000000 " +
                  prevout + " " +
                   "[\"KzsXybp9jX64P5ekX1KUxRQ79Jht9uzW7LorgwE65i5rWACL6LQe\",\"Kyhdf5LuKTRx4ge69ybABsiUAWjVRK4XGxAKk2FQLp2HjGMy87Z4\"]";
            Zenj.executeCommand(networkType, cmd, "signrawtransaction");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
