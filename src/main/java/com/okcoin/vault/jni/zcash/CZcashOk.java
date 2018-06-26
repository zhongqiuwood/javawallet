package com.okcoin.vault.jni;

public class CZcashOk {

    static {
    	   System.load("/Users/wangwenfeng01/git/wallet/zcash/src/zcash-cli-ok.so"); 
    }

    static public native String[] execute(String networkType, String command);

    public static void dump(String context, String[] result) {

        System.out.printf("//-------------------------------------------------------\n");
        System.out.println("// Dump result return from JNI: " + context);
        for (int i = 0; i < result.length; ++i) {
            System.out.println("// " + result[i]);
      
        }
    }
    
    public static void main(String[] args)
    {
        try {
            String networkType = "main"; // or testnet
            

            //String getaddressbyprivatekey ="getaddressbyprivatekey L4N2SLLE48g9RaNg4RgMVc8X1MsQZtVmFodJJ61ixhC3bN6htpzn";
            //String[] address = CZcashOk.execute(networkType, getaddressbyprivatekey);
            //CZcashOk.dump("getaddressbyprivatekey", address);

            
            
            String createrawtransaction2 ="createrawtransaction "+
            	      "[{\"txid\":\"b4cc287e58f87cdae59417329f710f3ecd75a4ee1d2872b7248f50977c8493f3\"," +
            	      "\"vout\":1,\"scriptPubKey\":\"a914b10c9df5f7edf436c697f02f1efdba4cf399615187\"," +
            	      "\"redeemScript\":\"512103debedc17b3df2badbcdd86d5feb4562b86fe182e5998abd8bcd4f122c6155b1b21027e940bb73ab8732bfdf7f9216ecefca5b94d6df834e77e108f68e66f126044c052ae\"}] " +
            	      "{\"t3ahmeUm2LWXPUJPx9QMheGtqTEfdDdgr7p\":11}";


            String[] createResults = CZcashOk.execute(networkType, createrawtransaction2);
            CZcashOk.dump("createrawtransaction", createResults);

            String signrawtransaction = "signrawtransaction " +
                    "0100000001f393847c97508f24b772281deea475cd3e0f719f321794e5da7cf8587e28ccb40100000000ffffffff0100ab90410000000017a914b10c9df5f7edf436c697f02f1efdba4cf39961518700000000 " +
                    "[{\"txid\":\"b4cc287e58f87cdae59417329f710f3ecd75a4ee1d2872b7248f50977c8493f3\",\"vout\":1,\"scriptPubKey\":\"a914b10c9df5f7edf436c697f02f1efdba4cf399615187\"" +
                    ",\"redeemScript\":\"512103debedc17b3df2badbcdd86d5feb4562b86fe182e5998abd8bcd4f122c6155b1b21027e940bb73ab8732bfdf7f9216ecefca5b94d6df834e77e108f68e66f126044c052ae\"}]" +
                    " [\"KzsXybp9jX64P5ekX1KUxRQ79Jht9uzW7LorgwE65i5rWACL6LQe\",\"Kyhdf5LuKTRx4ge69ybABsiUAWjVRK4XGxAKk2FQLp2HjGMy87Z4\"]";
            String[] signResults = CZcashOk.execute(networkType, signrawtransaction);
            CZcashOk.dump("signrawtransaction", signResults);

         } catch (Exception e) {
            System.out.println(e.getMessage());
//0100000001f393847c97508f24b772281deea475cd3e0f719f321794e5da7cf8587e28ccb40100000000ffffffff0100ab90410000000017a914b10c9df5f7edf436c697f02f1efdba4cf39961518700000000
//0100000001f393847c97508f24b772281deea475cd3e0f719f321794e5da7cf8587e28ccb40100000000ffffffff0100ab90410000000017a914b10c9df5f7edf436c697f02f1efdba4cf39961518700000000

            }
    }

    public static void main1(String[] args)
    {
        try {
            String networkType = "main"; // or testnet

            //String getaddressbyprivatekey ="ok_getAddress ST15NyNF44nKEXYZMC9B9jLQFvAw5bJid5Uh32RGqSBD8Bdkwq6q";
            //String[] address = CZcashOk.execute(networkType, getaddressbyprivatekey);
            //CZcashOk.dump("getaddressbyprivatekey", address);

            String createrawtransaction = "z_createrawtransaction_ok [{\"txid\":\"7c4723695d20968f31c354e40f9bfa4aa94a4adfb6862ce3eff16165f6b603bd\",\"vout\":0" +
                    ",\"sequence\":0}] [] " +
                    "[{\"address\":\"tmMpUC68e27EUuCUkFoR4AvF4zeZusi4jUk\", \"amount\":0.5,\"memo\":\"\"}]";

            String[] createResults = CZcashOk.execute(networkType, createrawtransaction);
            CZcashOk.dump("createrawtransaction", createResults);

            /*String signrawtransaction = "signrawtransaction " +
                    "0200000001f393847c97508f24b772281deea475cd3e0f719f321794e5da7cf8587e28ccb40100000000ffffffff0100ab90410000000017a914b10c9df5f7edf436c697f02f1efdba4cf39961518700000000 " +
                    "[{\"txid\":\"b4cc287e58f87cdae59417329f710f3ecd75a4ee1d2872b7248f50977c8493f3\",\"vout\":1,\"scriptPubKey\":\"a914b10c9df5f7edf436c697f02f1efdba4cf399615187\"" +
                    ",\"redeemScript\":\"512103debedc17b3df2badbcdd86d5feb4562b86fe182e5998abd8bcd4f122c6155b1b21027e940bb73ab8732bfdf7f9216ecefca5b94d6df834e77e108f68e66f126044c052ae\"}]" +
                    " [\"KzsXybp9jX64P5ekX1KUxRQ79Jht9uzW7LorgwE65i5rWACL6LQe\",\"Kyhdf5LuKTRx4ge69ybABsiUAWjVRK4XGxAKk2FQLp2HjGMy87Z4\"]";
            String[] signResults = CZcashOk.execute(networkType, signrawtransaction);
            CZcashOk.dump("signrawtransaction", signResults);
            */

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    
}
