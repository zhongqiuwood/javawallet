package com.okcoin.vault.jni.starchain;

import com.okcoin.vault.jni.common.Util;

public class Starchainj {

    static String networkType = ""; //ignore this

    static String cmd = null;

    static {
        System.load("/Users/oker/go/src/starchain/libstarchain.dylib");
    }

    static public native String[] execute(String networkType, String command);


    public static void executeCommand(String networkType, String cmd, String context) {

        String[] results = Starchainj.execute(networkType, cmd);
        Util.dump(context, results);
    }

    public static void main(String[] args)
    {
        try {
            String seckey = "de47f09131a701b1012ab205df484ec43dd73daa6d42a0fead004a16307d7fdd";

            cmd ="getaddressbyprivatekey " + seckey;
            Starchainj.executeCommand(networkType, cmd, "getaddressbyprivatekey");

            String inputs = "{\"assetid\":\"23d4d5aeb154126332cc9aa5dd0a4ce3bb4cf3d2df507bd9a85ab6b7b9c9bbf4\",\"pubkey\":\"036f9c4bc2c75334da4c145848769c6128dd7edafa8059f28da5feb60e3d17e3df\",\"fee_per_target\":\"1\",\"targets\":[{\"address\":\"SfgWjUW6H1NvFUgTBhrdxs6uf6upy2Tc5G\",\"value\":\"100\"},{\"address\":\"SbH2nd8p7veM2LG6uJsu6dDRnmww7B94ai\",\"value\":\"200\"}],\"unspent_info\":[{\"txid\":\"1da2fcb9a6487c7df825dd7324cacc4ac5b5c4d0e22d82594bc5365e0b7b9dcc\",\"index\":0,\"value\":\"150\"},{\"txid\":\"1da2fcb9a6487c7df825dd7324cacc4ac5b5c4d0e22d82594bc5365e0b7b9dcc\",\"index\":1,\"value\":\"200\"}],\"input_tx\":[{\"txid\":\"1da2fcb9a6487c7df825dd7324cacc4ac5b5c4d0e22d82594bc5365e0b7b9dcc\",\"outputs\":[{\"assetid\":\"23d4d5aeb154126332cc9aa5dd0a4ce3bb4cf3d2df507bd9a85ab6b7b9c9bbf4\",\"value\":150,\"programhash\":\"e0f1a129f50fc717fe746c5d041d0054874c8609\"},{\"assetid\":\"23d4d5aeb154126332cc9aa5dd0a4ce3bb4cf3d2df507bd9a85ab6b7b9c9bbf4\",\"value\":200,\"programhash\":\"e0f1a129f50fc717fe746c5d041d0054874c8609\"}]}]}";
            cmd = "createrawtransaction " + inputs;
            Starchainj.executeCommand(networkType, cmd, "createrawtransaction");

            String rawTx = "{\"TxData\":\"800001001335353737303036373931393437373739343130021da2fcb9a6487c7df825dd7324cacc4ac5b5c4d0e22d82594bc5365e0b7b9dcc00001da2fcb9a6487c7df825dd7324cacc4ac5b5c4d0e22d82594bc5365e0b7b9dcc010003f4bbc9b9b7b65aa8d97b50dfd2f34cbbe34c0adda59acc32631254b1aed5d4230003164e02000000c9b43543291886dfa20961bdea57cf0f0f945dcdf4bbc9b9b7b65aa8d97b50dfd2f34cbbe34c0adda59acc32631254b1aed5d42300e721a2040000009962cf555c2b9b0a142cfcec1787b1b534863096f4bbc9b9b7b65aa8d97b50dfd2f34cbbe34c0adda59acc32631254b1aed5d42300f2052a01000000e0f1a129f50fc717fe746c5d041d0054874c860900\",\"input_tx\":[{\"Txid\":\"1da2fcb9a6487c7df825dd7324cacc4ac5b5c4d0e22d82594bc5365e0b7b9dcc\",\"outputs\":[{\"AssetID\":\"23d4d5aeb154126332cc9aa5dd0a4ce3bb4cf3d2df507bd9a85ab6b7b9c9bbf4\",\"Value\":150,\"ProgramHash\":\"e0f1a129f50fc717fe746c5d041d0054874c8609\"},{\"AssetID\":\"23d4d5aeb154126332cc9aa5dd0a4ce3bb4cf3d2df507bd9a85ab6b7b9c9bbf4\",\"Value\":200,\"ProgramHash\":\"e0f1a129f50fc717fe746c5d041d0054874c8609\"}]}]}";
            cmd = "signrawtransaction " + seckey + " " + rawTx;
            Starchainj.executeCommand(networkType, cmd, "signrawtransaction");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
