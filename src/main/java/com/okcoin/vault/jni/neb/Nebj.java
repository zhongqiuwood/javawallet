package com.okcoin.vault.jni.neb;

import com.okcoin.vault.jni.common.Util;

public class Nebj {

    static String networkType = ""; //ignore this

    static String cmd = null;

    static {
        System.load("/Users/oker/go/src/github.com/nebulasio/go-nebulas/libneb.dylib");
    }

    static public native String[] execute(String networkType, String command);


    public static void executeCommand(String networkType, String cmd, String context) {

        String[] results = Nebj.execute(networkType, cmd);
        Util.dump(context, results);
    }

    public static void main(String[] args)
    {
        try {
            String seckey = "c9312e9c279309ec9df68bd374124676f2bf9d050a53c6fab35045c62d15cc9c";

            cmd ="getaddressbyprivatekey " + seckey;
            Nebj.executeCommand(networkType, cmd, "getaddressbyprivatekey");

            String chainID = "100";
            String from = "n1HSJnmDxvUWejKjKkWQvBKCK1d7AVFZckq";
            String to = "n1TcMVWCuE6vLfhugpSQdk92vHH7thGLnZt";
            String value = "10000000";
            String nonce = "1";
            String gasPrice = "1000000";
            String gasLimit = "2000000";
            String binary = "";
            cmd = "createrawtransaction " + chainID + " " + from + " " + to + " " + value + " " + nonce + " " + gasPrice + " " + gasLimit + " " + binary;
            Nebj.executeCommand(networkType, cmd, "createrawtransaction");

            String rawTx = "121a1957200c68cba489da88c9c8887df46ce13a9024c1c31c55ca7a1a1a19578fa4152b27a53c34eb79718e722f5631fcd1daa90b94b66f221000000000000000000000000000989680280a30b49afddb053a080a0662696e61727940644a10000000000000000000000000000f42405210000000000000000000000000001e8480";
            String prvkey = "734daad005b7a69c42d7c25e286bc5f376ac757f4869f0d521245b46af74b7e6";
            cmd = "signrawtransaction " + rawTx + " " + prvkey;
            Nebj.executeCommand(networkType, cmd, "signrawtransaction");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
