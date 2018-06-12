package com.okcoin.vault.jni.qtum;

public class Qtumj {

    static {
        System.load("/Users/oak/go/src/github.com/okblockchainlab/qtum/src/qtumd.dylib");
    }

    static public native String[] execute(String command);

    public static void dump(String context, String[] result) {

        System.out.printf("//-------------------------------------------------------\n");
        System.out.println("// Dump result return from JNI: " + context);
        for (int i = 0; i < result.length; ++i) {
            System.out.println("// " + result[i]);
        }
        System.out.println("//-------------------------------------------------------");
    }

    public static void main(String[] args)
    {
        try {

            String createrawtransaction = "createrawtransaction [{\"txid\":\"b4cc287e58f87cdae59417329f710f3ecd75a4ee1d2872b7248f50977c8493f3\",\"vout\":1" +
                    ",\"scriptPubKey\":\"a914b10c9df5f7edf436c697f02f1efdba4cf399615187\",\"redeemScript\"" +
                    ":\"512103debedc17b3df2badbcdd86d5feb4562b86fe182e5998abd8bcd4f122c6155b1b21027e940bb73ab8732bfdf7f9216ecefca5b94d6df834e77e108f68e66f126044c052ae\"}] " +
                    "{\"MQ3Jx2krKJbDgAcxJrXvL73KXH4zVf8mWg\":11}";

            String[] createResults = Qtumj.execute(createrawtransaction);
            Qtumj.dump("createrawtransaction", createResults);

            String signrawtransaction = "signrawtransaction " +
                    "0200000001f393847c97508f24b772281deea475cd3e0f719f321794e5da7cf8587e28ccb40100000000ffffffff0100ab90410000000017a914b10c9df5f7edf436c697f02f1efdba4cf39961518700000000 " +
                    "[{\"txid\":\"b4cc287e58f87cdae59417329f710f3ecd75a4ee1d2872b7248f50977c8493f3\",\"vout\":1,\"scriptPubKey\":\"a914b10c9df5f7edf436c697f02f1efdba4cf399615187\"" +
                    ",\"redeemScript\":\"512103debedc17b3df2badbcdd86d5feb4562b86fe182e5998abd8bcd4f122c6155b1b21027e940bb73ab8732bfdf7f9216ecefca5b94d6df834e77e108f68e66f126044c052ae\"}]" +
                    " [\"KzsXybp9jX64P5ekX1KUxRQ79Jht9uzW7LorgwE65i5rWACL6LQe\",\"Kyhdf5LuKTRx4ge69ybABsiUAWjVRK4XGxAKk2FQLp2HjGMy87Z4\"]";
            String[] signResults = Qtumj.execute(signrawtransaction);
            Qtumj.dump("signrawtransaction", signResults);

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
