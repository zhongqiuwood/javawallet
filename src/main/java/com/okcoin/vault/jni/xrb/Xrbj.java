package com.okcoin.vault.jni.xrb;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.vault.jni.common.Util;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Xrbj  implements Runnable {

    static String networkType = "main"; // or main

    static String testnet_private1 = "11ccad6338fa5fa6c686d3d0b2f7400eafd22880d90f7be23f1fe61d67ec8b80";
    static String testnet_account1 = "xrb_3uu9k4awp679frqs5tuujwgnnkxqa5r9hysccykdgxy6wtp7r48b65a477ao";

    static String testnet_private2 = "4d6d849ea64ec78c29bb865dcb5727034c287b3d999b7ae81bfdd6c22d5cfaa5";
    static String testnet_account2 = "xrb_1oa16rzmpfcau7kgtyfykijhih4rbkhumsauqsutny915rzw17bhu7mbg9xs";

    static String cmd = null;

    static {
//        System.load("/Users/zhijie.li/IdeaProjects/raiblocks/libraiblocks.dylib");
//        System.load("/Users/oker/code/okbc/raiblocks/libraiblocks.dylib");
          System.load("/Users/oak/oklab/raiblocks/libraiblocks.dylib");

    }

    static public native String[] execute(String networkType, String command);
    public static void executeCommand(String networkType, String cmd, String context) {

        String[] results = Xrbj.execute(networkType, cmd);
        Util.dump(context, results);
    }

    public static void main(String[] args) {

        try {

            int concurrent = 30;
            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(concurrent);
            for (int i = 0; i < concurrent; ++i) {

                Xrbj xrbj = new Xrbj();
                xrbj.run();
                fixedThreadPool.execute(new Xrbj());
            }
            fixedThreadPool.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            //生成地址
            cmd ="getaddressbyprivatekey" + " " + testnet_private1;
            Xrbj.executeCommand(networkType, cmd, "getaddressbyprivatekey");

            //创建交易
            String link = "xrb_3uu9k4awp679frqs5tuujwgnnkxqa5r9hysccykdgxy6wtp7r48b65a477ao";
            String previous = "A8634B84692BCD9CC2EEBC5927F8843979E8679284E8FFFA53F54D5938A9E3EB";
            String representative = "xrb_1oa16rzmpfcau7kgtyfykijhih4rbkhumsauqsutny915rzw17bhu7mbg9xs";
            String balance = "98000000000000000000000000000";

            String action = "createrawtransaction";

            cmd = action + " " + link + " " + previous + " " + representative + " " + balance;
            Xrbj.executeCommand(networkType, cmd, action);

            //签名交易
            String utx = "{\"action\":\"block_create\",\"type\":\"state\",\"previous\":"
                    + "\"A8634B84692BCD9CC2EEBC5927F8843979E8679284E8FFFA53F54D5938A9E3EB\",\"representative\": "
                    + "\"xrb_1oa16rzmpfcau7kgtyfykijhih4rbkhumsauqsutny915rzw17bhu7mbg9xs\",\"balance\": "
                    + "\"98000000000000000000000000000\",\"link\": "
                    + "\"xrb_3uu9k4awp679frqs5tuujwgnnkxqa5r9hysccykdgxy6wtp7r48b65a477ao\"}";
            JSONObject parse = (JSONObject)JSONObject.parse(utx);
            String tx = parse.toJSONString();
            String key = testnet_private2;
            cmd = "signrawtransaction " + tx + " " + key;
            Xrbj.executeCommand(networkType, cmd, "signrawtransaction");

            //创建交易
//            cmd = "createrawtransaction" + " " + link + " " + previous + " " + representative + " " + balance;
//            Xrbj.executeCommand(networkType, cmd, "createrawtransaction");
//
//            //签名交易
//            cmd = "signrawtransaction " + tx + " " + key;
//            Xrbj.executeCommand(networkType, cmd, "signrawtransaction");
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}


//
//public class Xrbj {
//
//
//    static {
////        System.load("/Users/oker/code/okbc/raiblocks/libraiblocks.dylib");
//        System.load("/Users/oak/oklab/raiblocks/libraiblocks.dylib");
//    }
//
//
//
//    static String networkType = "testnet"; // or main
//
//    static String testnet_private1 = "34F0A37AAD20F4A260F0A5B3CB3D7FB50673212263E58A380BC10474BB039CE4";
//    static String testnet_account1 = "xrb_3e3j5tkog48pnny9dmfzj1r16pg8t1e76dz5tmac6iq689wyjfpiij4txtdo";
//
//    static String testnet_private2 = "78E5F97527656E723C86677DDD512CDD1EAAAC86250221D43E0FF554B4B06FB5";
//    static String testnet_account2 = "xrb_3dh9oezfr635oyszyy9jth9cmsk8tpckb9rwpd88456hskmqkgg1f9nmd4un";
//
//    static String cmd = null;
//
//
//    static public native String[] execute(String networkType, String command);
//
//
//    public static void executeCommand(String networkType, String cmd, String context) {
//
//        String[] results = Xrbj.execute(networkType, cmd);
//        Util.dump(context, results);
//    }
//
//    public static void main(String[] args)
//    {
//        try {
//            cmd ="getaddressbyprivatekey" + " " + testnet_private1;
//            Xrbj.executeCommand(networkType, cmd, "getaddressbyprivatekey");
//
//
//            String link = testnet_account2;
//            String previous = "04270D7F11C4B2B472F2854C5A59F2A7E84226CE9ED799DE75744BD7D85FC9D9";
//            String representative = "xrb_3e3j5tkog48pnny9dmfzj1r16pg8t1e76dz5tmac6iq689wyjfpiij4txtdo";
//            String balance = "340282366920938463463374607431768201455";
//            cmd = "createrawtransaction" + " " + link + " " + previous + " " + representative + " " + balance;
//            Xrbj.executeCommand(networkType, cmd, "createrawtransaction");
//
//            String utx = "{\"action\":\"block_create\",\"type\":\"state\",\"previous\":\"04270D7F11C4B2B472F2854C5A59F2A7E84226CE9ED799DE75744BD7D85FC9D9\",\"representative\":\"xrb_3e3j5tkog48pnny9dmfzj1r16pg8t1e76dz5tmac6iq689wyjfpiij4txtdo\",\"balance\":\"340282366920938463463374607431768201455\",\"link\":\"xrb_3dh9oezfr635oyszyy9jth9cmsk8tpckb9rwpd88456hskmqkgg1f9nmd4un\"}";
//            String key = testnet_private1;
//            cmd = "signrawtransaction " + utx + " " + key;
//            Xrbj.executeCommand(networkType, cmd, "signrawtransaction");
//        } catch (Exception e) {
//
//            System.out.println(e.getMessage());
//        }
//    }
//}
