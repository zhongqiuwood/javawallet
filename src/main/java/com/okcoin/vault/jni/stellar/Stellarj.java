package com.okcoin.vault.jni.stellar;

import com.okcoin.vault.jni.common.Util;

public class Stellarj {

    static String networkType = "testnet"; // or main
    static String dataDir = "/Users/oker/code/okbc/stellar-core/ok-wallet/build";

    static String testnet_seed1 = "SBCRVCYITA3WRDNODQOPUKNMSAMFF7YZZMITJABUHWWIGDOBFZKVNEHO";
    static String testnet_address1 = "GBHVJDEOZU2CZY7QA6AUE5GOZF3TE4BXFTJVMPUS52X6TGKTIZ75H4US";

    static String testnet_seed2 = "SD2WEMOWNWP4WIQKHP2KVZH6ZJX2NODMCXTFASZLK24Y5Z33FS7NYCW4";
    static String testnet_address2 = "GA4SNMFYYT77VX7NPUXTDDVNX6FIAF2IIZEJEOFKBZ2U5GPX2OU6O7BW";

    static String cmd = null;

    static {
        System.load("/Users/oker/code/okbc/stellar-core/ok-wallet/build/libstellar-core.dylib");
    }

    static public native String[] execute(String networkType, String command);


    public static void executeCommand(String networkType, String cmd, String context) {

        String[] results = Stellarj.execute(networkType, cmd);
        Util.dump(context, results);
    }

    public static void main(String[] args)
    {
        try {

            cmd ="getaddressbyprivatekey " + testnet_seed1;
            Stellarj.executeCommand(networkType, cmd, "getaddressbyprivatekey");


            String from = testnet_address1;
            String to = testnet_address2;
            String count = "100";
            cmd = "createrawtransaction " + from + " " + to + " " + count + " " + dataDir;
            Stellarj.executeCommand(networkType, cmd, "createrawtransaction");

            String utx = "000000004F548C8ECD342CE3F007814274CEC9773270372CD3563E92EEAFE99953467FD300000064008F3CF0000000030000000000000000000000010000000000000001000000003926B0B8C4FFFADFED7D2F318EADBF8A80174846489238AA0E754E99F7D3A9E70000000000000000000000640000000000000000";
            String seed = testnet_seed1;
            cmd = "signrawtransaction " + utx + " " + seed + " " + dataDir;
            Stellarj.executeCommand(networkType, cmd, "signrawtransaction");
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
