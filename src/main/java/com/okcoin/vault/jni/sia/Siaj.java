package com.okcoin.vault.jni.sia;

import com.okcoin.vault.jni.common.Util;

public class Siaj {

    static String networkType = ""; //ignore this

    static String cmd = null;

    static {
        System.load("/Users/oker/go/src/gitlab.com/NebulousLabs/Sia/libsia.dylib");
    }

    static public native String[] execute(String networkType, String command);


    public static void executeCommand(String networkType, String cmd, String context) {

        String[] results = Siaj.execute(networkType, cmd);
        Util.dump(context, results);
    }

    public static void main(String[] args)
    {
        try {
            String seckey = "97a4f67362116f9011448e881113213ec4cfe9a676605791a97b2838cf0f3486388ea2ae690da5116f8be18cdba570fc72df9fb3c09cd853a60e3737599dbd27";

            cmd ="getaddressbyprivatekey " + seckey;
            Siaj.executeCommand(networkType, cmd, "getaddressbyprivatekey");

            String fee = "2000000000000000000000000";
            String amount = "34398329033609498745190000000";
            //from address is d874365aaca9f09dcad33009daa76c62b4c036fdd1c3c9d3ac2297d5a29c9787927341218ccc
            String from_ucs = "{\"timelock\":0,\"publickeys\":[{\"algorithm\":\"ed25519\",\"key\":\"OI6irmkNpRFvi+GM26Vw/HLfn7PAnNhTpg43N1mdvSc=\"}],\"signaturesrequired\":1}";
            //to address is cda170c94736b1ecc035758fcf34565f2013be2c7cd4b4584c62769f3b1dd71616fde29d99a9
            String to_ucs = "{\"timelock\":0,\"publickeys\":[{\"algorithm\":\"ed25519\",\"key\":\"7z1Tb+iQYJrTnTuSIZhW7f7s6JCwv5+a4CoonhHm9uQ=\"}],\"signaturesrequired\":1}";
            String refund_ucs = from_ucs;
            String spendingTx = "[{\"transaction\":{\"id\":\"2d0441d77101614aba89bdb354f3a9066a6a71b2d992345921335eceeba46814\",\"siacoininputs\":[{\"parentid\":\"f09f4742c1a67fdb10d744373c1aa9547692fe188c8a418e5bd2394c0f5724d9\",\"unlockconditions\":{\"timelock\":0,\"publickeys\":[{\"algorithm\":\"ed25519\",\"key\":\"9ANhmXhmAjkEctz+7IOxAlCHM0EIPSLfz3Syp6ySTQY=\"}],\"signaturesrequired\":1}}],\"siacoinoutputs\":[{\"id\":\"0195d81a9056466908e801c0b3f88904a473458c0d32f95992e394cf82dee22a\",\"value\":\"2096373826292013232000000000\",\"unlockhash\":\"497223c4bf5ec4350556a25900d1dbcf35d360b3ba2a94cbf1ae1c2990d6be4426b550d368f3\"},{\"id\":\"8f8d8091586e406af550d511eea1643c25eface0411e7e590fed55dbec01aef5\",\"value\":\"30209581881025472281190000000\",\"unlockhash\":\"ea1462f9ce40b58f99556e39a10336df9901d1c27aa824d86797e83a61a0780c62f7b3fcde28\"}],\"filecontracts\":[],\"filecontractrevisions\":[],\"storageproofs\":[],\"siafundinputs\":[],\"siafundoutputs\":[],\"minerfees\":[],\"arbitrarydata\":[],\"transactionsignatures\":[{\"parentid\":\"f09f4742c1a67fdb10d744373c1aa9547692fe188c8a418e5bd2394c0f5724d9\",\"publickeyindex\":0,\"timelock\":0,\"coveredfields\":{\"wholetransaction\":true,\"siacoininputs\":[],\"siacoinoutputs\":[],\"filecontracts\":[],\"filecontractrevisions\":[],\"storageproofs\":[],\"siafundinputs\":[],\"siafundoutputs\":[],\"minerfees\":[],\"arbitrarydata\":[],\"transactionsignatures\":[]},\"signature\":\"RY7AU52+hvVsL2vgHsk/Bh04CaWRJuQVsjWUivyr/P+Or2ve5qP8n5tLqUjv1KY9K8dJA+aguaW4arrp1H7fAw==\"}]},\"outputs\":[0,1]},{\"transaction\":{\"id\":\"e936e2a46e351f00ba8789c62d3e47cfe386d1c38188e428fec1d63c0388c1e1\",\"siacoininputs\":[{\"parentid\":\"0195d81a9056466908e801c0b3f88904a473458c0d32f95992e394cf82dee22a\",\"unlockconditions\":{\"timelock\":0,\"publickeys\":[{\"algorithm\":\"ed25519\",\"key\":\"uUfZNeLgnKNZ4RlcjZ1tWjsk16K4NJAdOP6tzVX4JZk=\"}],\"signaturesrequired\":1}}],\"siacoinoutputs\":[{\"id\":\"fd3031e9782f234fa751427cf21edb7cebcd0afcee5e951c8c0f31bb9aaf2848\",\"value\":\"2094373826292013232000000000\",\"unlockhash\":\"43ef3c94752e608ed414d6dfb228f445882571ae4178fb14288dccf7c97820392d1eda46de15\"}],\"filecontracts\":[],\"filecontractrevisions\":[],\"storageproofs\":[],\"siafundinputs\":[],\"siafundoutputs\":[],\"minerfees\":[\"2000000000000000000000000\"],\"arbitrarydata\":[],\"transactionsignatures\":[{\"parentid\":\"0195d81a9056466908e801c0b3f88904a473458c0d32f95992e394cf82dee22a\",\"publickeyindex\":0,\"timelock\":0,\"coveredfields\":{\"wholetransaction\":true,\"siacoininputs\":[],\"siacoinoutputs\":[],\"filecontracts\":[],\"filecontractrevisions\":[],\"storageproofs\":[],\"siafundinputs\":[],\"siafundoutputs\":[],\"minerfees\":[],\"arbitrarydata\":[],\"transactionsignatures\":[]},\"signature\":\"NJzF1nDznVIoDGaWsx4UjTvIZBPUV+zU4ouz0OYAKqXpi1dA9kbvTwyA6MD1dPaE1sPUmSf0C6t3JUSIvs1JBg==\"}]},\"outputs\":[0]}]";

            cmd = "createrawtransaction " + amount + " " + fee + " " + from_ucs + " " + to_ucs + " " + refund_ucs + " " + spendingTx;
            Siaj.executeCommand(networkType, cmd, "createrawtransaction");

          	String txBuilder = "{\"signed\":false,\"parents\":[{\"siacoininputs\":[{\"parentid\":\"0195d81a9056466908e801c0b3f88904a473458c0d32f95992e394cf82dee22a\",\"unlockconditions\":{\"timelock\":0,\"publickeys\":[{\"algorithm\":\"ed25519\",\"key\":\"OI6irmkNpRFvi+GM26Vw/HLfn7PAnNhTpg43N1mdvSc=\"}],\"signaturesrequired\":1}},{\"parentid\":\"8f8d8091586e406af550d511eea1643c25eface0411e7e590fed55dbec01aef5\",\"unlockconditions\":{\"timelock\":0,\"publickeys\":[{\"algorithm\":\"ed25519\",\"key\":\"OI6irmkNpRFvi+GM26Vw/HLfn7PAnNhTpg43N1mdvSc=\"}],\"signaturesrequired\":1}},{\"parentid\":\"fd3031e9782f234fa751427cf21edb7cebcd0afcee5e951c8c0f31bb9aaf2848\",\"unlockconditions\":{\"timelock\":0,\"publickeys\":[{\"algorithm\":\"ed25519\",\"key\":\"OI6irmkNpRFvi+GM26Vw/HLfn7PAnNhTpg43N1mdvSc=\"}],\"signaturesrequired\":1}}],\"siacoinoutputs\":[{\"value\":\"34400329033609498745190000000\",\"unlockhash\":\"d874365aaca9f09dcad33009daa76c62b4c036fdd1c3c9d3ac2297d5a29c9787927341218ccc\"},{\"value\":\"500000000000000000000\",\"unlockhash\":\"d874365aaca9f09dcad33009daa76c62b4c036fdd1c3c9d3ac2297d5a29c9787927341218ccc\"}],\"filecontracts\":[],\"filecontractrevisions\":[],\"storageproofs\":[],\"siafundinputs\":[],\"siafundoutputs\":[],\"minerfees\":[],\"arbitrarydata\":[],\"transactionsignatures\":[]}],\"siacoininputs\":[0],\"transaction\":{\"siacoininputs\":[{\"parentid\":\"ccb97feff1c37d87937e647279df4a7f58c5cc488d8168115612abc982819f93\",\"unlockconditions\":{\"timelock\":0,\"publickeys\":[{\"algorithm\":\"ed25519\",\"key\":\"OI6irmkNpRFvi+GM26Vw/HLfn7PAnNhTpg43N1mdvSc=\"}],\"signaturesrequired\":1}}],\"siacoinoutputs\":[{\"value\":\"34398329033609498745190000000\",\"unlockhash\":\"cda170c94736b1ecc035758fcf34565f2013be2c7cd4b4584c62769f3b1dd71616fde29d99a9\"}],\"filecontracts\":[],\"filecontractrevisions\":[],\"storageproofs\":[],\"siafundinputs\":[],\"siafundoutputs\":[],\"minerfees\":[\"2000000000000000000000000\"],\"arbitrarydata\":[],\"transactionsignatures\":[]}}";

            cmd = "signrawtransaction " + txBuilder + " " + "[\"" + seckey + "\"]";
            Siaj.executeCommand(networkType, cmd, "signrawtransaction");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
