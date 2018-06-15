package com.okcoin.vault.jni.digibyte;

import com.okcoin.vault.jni.common.Util;

public class Digibytej {

    static {
        System.load("/Users/oak/go/src/github.com/okblockchainlab/digibyte/src/digibyted.dylib");
    }

    static public native String[] execute(String networkType, String command);

    public static void main(String[] args)
    {
        try {
            String networkType = "main"; // or testnet

            String getaddressbyprivatekey ="getaddressbyprivatekey L43NUb18bYnsZr8SaudQtLczX2SJz6KQG6tnXWJTM46po7m4m49h";
            String[] address = com.okcoin.vault.jni.qtum.Qtumj.execute(networkType, getaddressbyprivatekey);
            Util.dump("getaddressbyprivatekey", address);

            String createrawtransaction = "createrawtransaction [{\"txid\":\"b4cc287e58f87cdae59417329f710f3ecd75a4ee1d2872b7248f50977c8493f3\",\"vout\":1" +
                    ",\"scriptPubKey\":\"a914b10c9df5f7edf436c697f02f1efdba4cf399615187\",\"redeemScript\"" +
                    ":\"512103debedc17b3df2badbcdd86d5feb4562b86fe182e5998abd8bcd4f122c6155b1b21027e940bb73ab8732bfdf7f9216ecefca5b94d6df834e77e108f68e66f126044c052ae\"}] " +
                    "{\"MQ3Jx2krKJbDgAcxJrXvL73KXH4zVf8mWg\":11}";

            String[] createResults = com.okcoin.vault.jni.qtum.Qtumj.execute(networkType, createrawtransaction);
            Util.dump("createrawtransaction", createResults);

            String signrawtransaction = "signrawtransaction " +
                    "0200000001f393847c97508f24b772281deea475cd3e0f719f321794e5da7cf8587e28ccb40100000000ffffffff0100ab90410000000017a914b10c9df5f7edf436c697f02f1efdba4cf39961518700000000 " +
                    "[{\"txid\":\"b4cc287e58f87cdae59417329f710f3ecd75a4ee1d2872b7248f50977c8493f3\",\"vout\":1,\"scriptPubKey\":\"a914b10c9df5f7edf436c697f02f1efdba4cf399615187\"" +
                    ",\"redeemScript\":\"512103debedc17b3df2badbcdd86d5feb4562b86fe182e5998abd8bcd4f122c6155b1b21027e940bb73ab8732bfdf7f9216ecefca5b94d6df834e77e108f68e66f126044c052ae\"}]" +
                    " [\"KzsXybp9jX64P5ekX1KUxRQ79Jht9uzW7LorgwE65i5rWACL6LQe\",\"Kyhdf5LuKTRx4ge69ybABsiUAWjVRK4XGxAKk2FQLp2HjGMy87Z4\"]";
            String[] signResults = com.okcoin.vault.jni.qtum.Qtumj.execute(networkType, signrawtransaction);
            Util.dump("signrawtransaction", signResults);

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
