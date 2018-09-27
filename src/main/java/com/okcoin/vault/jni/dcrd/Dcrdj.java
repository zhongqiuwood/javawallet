package com.okcoin.vault.jni.dcrd;

import com.okcoin.vault.jni.common.Util;

public class Dcrdj {

    static String networkType = "testnet"; //or main

    static String cmd = null;

    static {
        System.load("/Users/oker/go/src/github.com/decred/dcrd/libdcrd.dylib");
    }

    static public native String[] execute(String networkType, String command);


    public static void executeCommand(String networkType, String cmd, String context) {

        String[] results = Dcrdj.execute(networkType, cmd);
        Util.dump(context, results);
    }

    public static void main(String[] args)
    {
        try {
            String seckey = "PtWTh9iJ53s5h4MdRhDvz2he2dbZg8hjAJcrJ3fpk34Vcq92LuEYY";

            cmd ="getaddressbyprivatekey " + seckey;
            Dcrdj.executeCommand(networkType, cmd, "getaddressbyprivatekey");

            String inputs = "[{\"txid\":\"a6c492ac1740d6f38c056ed2e45166c7e015614cd2f8428ae1425dd7dbf2f9c8\",\"vout\":0,\"tree\":0,\"txtype\":0,\"address\":\"TseFQTGF1jbiNxnoxeNnjZ8fQhFSuyngM84\",\"account\":\"default\",\"scriptPubKey\":\"76a914911c6152572870b5323815ca16b302222082b24a88ac\",\"amount\":15,\"confirmations\":3,\"spendable\":true}]";
            String to = "{\"Tsn545kxwZF2r7XVdyAwamtjxWEe3bCQAiX\":0.2}";
            String lockTime = "";
            String expiry = "";

            cmd = "createrawtransaction " + inputs + " " + to;
            if (lockTime.length() > 0) {
              cmd += " " + lockTime;
            }
            if (expiry.length() > 0) {
              cmd += " " + expiry;
            }
            Dcrdj.executeCommand(networkType, cmd, "createrawtransaction");

            String rawTx = "0100000001c8f9f2dbd75d42e18a42f8d24c6115e0c76651e4d26e058cf3d64017ac92c4a60000000000ffffffff01002d31010000000000001976a914e6e838dee0da1112168a8ac7231f17093be0a9e288ac000000000000000001002f68590000000000000000ffffffff00";
            String privkeys = "[\"PtWTh9iJ53s5h4MdRhDvz2he2dbZg8hjAJcrJ3fpk34Vcq92LuEYY\"]";
            String flags = "ALL";

            cmd = "signrawtransaction " + rawTx + " " + inputs + " " + privkeys + " " + flags;
            Dcrdj.executeCommand(networkType, cmd, "signrawtransaction");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
