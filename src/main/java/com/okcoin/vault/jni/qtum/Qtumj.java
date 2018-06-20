package com.okcoin.vault.jni.qtum;

import com.okcoin.vault.jni.common.Util;

public class Qtumj {

    static String networkType = "main"; // or testnet
    static String cmd = null;
    static {
        System.load("/Users/oak/go/src/github.com/okblockchainlab/qtum/src/qtumd.dylib");
    }

    static public native String[] execute(String networkType, String command);


    public static void executeCommand(String networkType, String cmd, String context) {

        String[] results = Qtumj.execute(networkType, cmd);
        Util.dump(context, results);
    }

    public static void main(String[] args)
    {
        try {
            cmd = "addmultisigaddress 2 [\"03093bd8a834216f8ead1dfa4c8522f554357a8cad561a47fb035135b8e65506cc\"," +
                    "\"0220ac8980f225b4ad23057f7ae1cea8492306113ae9e49a90d184005d95c19846\"]";
            Qtumj.executeCommand(networkType, cmd, "addmultisigaddress");

//            String pk = "L25c2U81YS4TxgBKRC4jj4EUeBB2JU9SD7WLFWQU22EfBohJn6MJ";
//            String pk = "L3erMx5JiiSU3sDyasDL6g4N3hMFu5boStjwLBiU8n5zk7JjNSid";
//            String pk = "L3HWXXm2oQSmZ7FqX2bVigkGmFeMaE3zCW1opv51F5FEnA2o8v5t";
//            String pk = "L3evNKFggYYUkNmoQDJDLZ4BqpvUyXzLRXYcKae5jzjhMencmFiA";
//            String pk = "5d17c59d90a212732a6e1e138691df1b3a1e3b62e75eeea8be864e14834251ce";
//            String pk = "L43NUb18bYnsZr8SaudQtLczX2SJz6KQG6tnXWJTM46po7m4m49h";

            String pk = "L5gULKo1bDyPfKvu94DjG5VY1HZESCzzSybedzQSCaz3KKd6VVmn";

            cmd ="getaddressbyprivatekey " + pk;
            Qtumj.executeCommand(networkType, cmd, "getaddressbyprivatekey");


            cmd = "createrawtransaction [{\"txid\":\"b4cc287e58f87cdae59417329f710f3ecd75a4ee1d2872b7248f50977c8493f3\",\"vout\":1" +
                    ",\"scriptPubKey\":\"a914b10c9df5f7edf436c697f02f1efdba4cf399615187\",\"redeemScript\"" +
                    ":\"512103debedc17b3df2badbcdd86d5feb4562b86fe182e5998abd8bcd4f122c6155b1b21027e940bb73ab8732bfdf7f9216ecefca5b94d6df834e77e108f68e66f126044c052ae\"}] " +
                    "{\"MQ3Jx2krKJbDgAcxJrXvL73KXH4zVf8mWg\":11}";
            Qtumj.executeCommand(networkType, cmd, "createrawtransaction");

            cmd = "signrawtransaction " +
                    "0200000001f393847c97508f24b772281deea475cd3e0f719f321794e5da7cf8587e28ccb40100000000ffffffff0100ab90410000000017a914b10c9df5f7edf436c697f02f1efdba4cf39961518700000000 " +
                    "[{\"txid\":\"b4cc287e58f87cdae59417329f710f3ecd75a4ee1d2872b7248f50977c8493f3\",\"vout\":1,\"scriptPubKey\":\"a914b10c9df5f7edf436c697f02f1efdba4cf399615187\"" +
                    ",\"redeemScript\":\"512103debedc17b3df2badbcdd86d5feb4562b86fe182e5998abd8bcd4f122c6155b1b21027e940bb73ab8732bfdf7f9216ecefca5b94d6df834e77e108f68e66f126044c052ae\"}]" +
                    " [\"KzsXybp9jX64P5ekX1KUxRQ79Jht9uzW7LorgwE65i5rWACL6LQe\",\"Kyhdf5LuKTRx4ge69ybABsiUAWjVRK4XGxAKk2FQLp2HjGMy87Z4\"]";
            Qtumj.executeCommand(networkType, cmd, "single-signrawtransaction");


            cmd = "signrawtransaction 0200000001b2fa59b9b4d3858e5d560407d68b9336420490043a2daabd31a83f0449807fdd0000000000ffffffff01c0175302000000001976a914f1b5be282a20fb932a648fc4c9decb253399979488ac00000000 [{\"txid\":\"dd7f8049043fa831bdaa2d3a0490044236938bd60704565d8e85d3b4b959fab2\",\"vout\":0,\"scriptPubKey\":\"a91404a477f8bfe145d24fdef8b219e84b3f6b09c75687\",\"redeemScript\":\"522102cc37dcc588eb08484d0d74bee9c7e7f6f818129e49abc04b25e842c6247be56c2102cf0215383cf70e8318ab09e5ec88235e878c1a4e54d6fe8e37f9da21928856eb210212360211f166a465c0e346f8538512784e9c0d5b0b9a15b465a33e6af1c1500253ae\"}] [\"L2S6o32XWJFo3xZ1hdgTh98XYtM565veYERXZZamWLUqCBADLYXs\"]";
            Qtumj.executeCommand(networkType, cmd, "multi-signrawtransaction");


        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
