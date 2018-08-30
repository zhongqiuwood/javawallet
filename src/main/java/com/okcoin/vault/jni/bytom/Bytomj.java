package com.okcoin.vault.jni.bytom;

import com.okcoin.vault.jni.common.Util;

public class Bytomj {

    static String networkType = ""; //ignore this

    static String cmd = null;

    static {
        System.load("/Users/oker/go/src/github.com/bytom/libbytom.dylib");
    }

    static public native String[] execute(String networkType, String command);


    public static void executeCommand(String networkType, String cmd, String context) {

        String[] results = Bytomj.execute(networkType, cmd);
        Util.dump(context, results);
    }

    public static void main(String[] args)
    {
        try {
            String seckey = "98b94afc04a4dcff39e63bf48d01da8c6057f2564b35def6210add648316584510fd3d91011aeba9d41f1654c0c8232d1f94eb807a89a35e1501c4effb5c4d5a";

            cmd ="getaddressbyprivatekey " + seckey;
            Bytomj.executeCommand(networkType, cmd, "getaddressbyprivatekey");

            String inputs = "{\"base_transaction\":null,\"actions\":[{\"type\":\"spend_account_unspent_output\",\"utxo\":{\"account_alias\":\"default\",\"account_id\":\"0BKBR2D2G0A02\",\"address\":\"bm1qx7ylnhszg24995d5e0nftu9e87kt9vnxcn633r\",\"amount\":624000000000,\"asset_alias\":\"BTM\",\"asset_id\":\"ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff\",\"change\":false,\"control_program_index\":12,\"id\":\"5af9d3c9b69470983377c1fc0c9125c4ac3bfd32c8d505f2a6042aade8503bc9\",\"program\":\"00143789f9de0242aa52d1b4cbe695f0b93facb2b266\",\"source_id\":\"233d1dd49e591980f98e11f333c6c28a867e78448e272011f045131df5aa260b\",\"source_pos\":0,\"valid_height\":12}}],\"ttl\":0,\"time_range\":0,\"xpub\":\"f7bf3b320f828566dff61ec222d4cdbcea032892c8c542b3b6a1c3c725ec356210fd3d91011aeba9d41f1654c0c8232d1f94eb807a89a35e1501c4effb5c4d5a\"}";
            cmd = "createrawtransaction " + inputs;
            Bytomj.executeCommand(networkType, cmd, "createrawtransaction");

            String rawTx = "{\"raw_transaction\":\"07010001016c016a233d1dd49e591980f98e11f333c6c28a867e78448e272011f045131df5aa260bffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff80c0b1ca9412000121d34d78dfbf3d7fd75ed36e3669ae767756f871b7baf797f46fdddf69c6f66f6eba010000\",\"signing_instructions\":[{\"position\":0,\"witness_components\":[{\"type\":\"raw_tx_signature\",\"quorum\":1,\"keys\":[{\"xpub\":\"f7bf3b320f828566dff61ec222d4cdbcea032892c8c542b3b6a1c3c725ec356210fd3d91011aeba9d41f1654c0c8232d1f94eb807a89a35e1501c4effb5c4d5a\",\"derivation_path\":[\"010100000000000000\",\"0c00000000000000\"]}],\"signatures\":null},{\"type\":\"data\",\"value\":\"a42b4998ff44281e6594c7e83b055bd7324a99f5fa27169f5cb5f92e6bb7d922\"}]}],\"allow_additional_actions\":false}";
            cmd = "signrawtransaction " + seckey + " " + rawTx;
            Bytomj.executeCommand(networkType, cmd, "signrawtransaction");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
