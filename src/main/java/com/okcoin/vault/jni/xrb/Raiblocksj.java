package com.okcoin.vault.jni.xrb;

import com.okcoin.vault.jni.common.Util;

public class Raiblocksj {

    static String networkType = "testnet"; // or main

    static String testnet_private1 = "34F0A37AAD20F4A260F0A5B3CB3D7FB50673212263E58A380BC10474BB039CE4";
    static String testnet_account1 = "xrb_3e3j5tkog48pnny9dmfzj1r16pg8t1e76dz5tmac6iq689wyjfpiij4txtdo";

    static String testnet_private2 = "78E5F97527656E723C86677DDD512CDD1EAAAC86250221D43E0FF554B4B06FB5";
    static String testnet_account2 = "xrb_3dh9oezfr635oyszyy9jth9cmsk8tpckb9rwpd88456hskmqkgg1f9nmd4un";

    static String cmd = null;

    static {
        System.load("/Users/oker/code/okbc/raiblocks/libraiblocks.dylib");
    }

    static public native String[] execute(String networkType, String command);


    public static void executeCommand(String networkType, String cmd, String context) {

        String[] results = Raiblocksj.execute(networkType, cmd);
        Util.dump(context, results);
    }

    public static void main(String[] args)
    {
        try {
            cmd ="getaddressbyprivatekey" + " " + testnet_private1;
            Raiblocksj.executeCommand(networkType, cmd, "getaddressbyprivatekey");


            String link = testnet_account2;
            String previous = "04270D7F11C4B2B472F2854C5A59F2A7E84226CE9ED799DE75744BD7D85FC9D9";
            String representative = "xrb_3e3j5tkog48pnny9dmfzj1r16pg8t1e76dz5tmac6iq689wyjfpiij4txtdo";
            String balance = "340282366920938463463374607431768201455";
            cmd = "createrawtransaction" + " " + link + " " + previous + " " + representative + " " + balance;
            Raiblocksj.executeCommand(networkType, cmd, "createrawtransaction");

            String utx = "{\"action\":\"block_create\",\"type\":\"state\",\"previous\":\"04270D7F11C4B2B472F2854C5A59F2A7E84226CE9ED799DE75744BD7D85FC9D9\",\"representative\":\"xrb_3e3j5tkog48pnny9dmfzj1r16pg8t1e76dz5tmac6iq689wyjfpiij4txtdo\",\"balance\":\"340282366920938463463374607431768201455\",\"link\":\"xrb_3dh9oezfr635oyszyy9jth9cmsk8tpckb9rwpd88456hskmqkgg1f9nmd4un\"}";
            String key = testnet_private1;
            cmd = "signrawtransaction " + utx + " " + key;
            Raiblocksj.executeCommand(networkType, cmd, "signrawtransaction");
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
