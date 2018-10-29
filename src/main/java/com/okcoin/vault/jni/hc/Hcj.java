package com.okcoin.vault.jni.hc;

import com.okcoin.vault.jni.common.Util;

public class Hcj {

    static String cmd = null;

    static {
        System.load("/home/meng/work/go/src/github.com/HcashOrg/hcwallet/libhcwallet.so");
    }

    static public native String[] execute(String command);

    public static void executeCommand(String cmd, String context) {

        String[] results = Hcj.execute(cmd);
        Util.dump(context, results);
    }

    public static void main(String[] args)
    {
        try {
            /* open hcwallet */
            cmd = "openhcwallet -u rpcuser -P rpcpass --pass 123456 --testnet";
            Hcj.executeCommand(cmd, "openhcwallet");

            String seckey = "PtWU8TjP3KBZJQ2ycoakD6sLLotyW2Ftss6CaroazJVJhzayTemwV";
            cmd ="getaddressbyprivatekey " + seckey;
            Hcj.executeCommand(cmd, "getaddressbyprivatekey");

            /* create unsigned transaction */
            String numoftxin = "1 ";
            String txin1 = "953f8c4fc1688ff4a2e8622003797535fb9c97ffee97b36fb82fe0ddba327723 1 ";
            String toamount = "Tsp4P1rZZf3TevgkG8M8BH8kxpyzfCN5oR9 50000000 ";
            String chargeamount = "Tsai18bMCXwiyB4cC3gGE1pHqZ7zNDHjCJQ 140000000";
            cmd = "createrawtransaction " + numoftxin + txin1 + toamount + chargeamount;
            Hcj.executeCommand(cmd, "createrawtransaction");

            
            /* sign raw transation */
            String rawTx = "0100000001237732badde02fb86fb397eeff979cfb357579032062e8a2f48f68c14f8c3f950100000000ffffffff02003b58080000000000001976a9146a43a3cd20d7353dd9f6459520d697976911c3a288ac80f0fa020000000000001976a914fcb7dcaa8a2b9a214045d5de40efc470bbba6d4a88ac000000000000000001ffffffffffffffff00000000ffffffff00 ";
            String privkeys = "PtWU8TjP3KBZJQ2ycoakD6sLLotyW2Ftss6CaroazJVJhzayTemwV";
            cmd = "signrawtransaction " + rawTx + privkeys;
            Hcj.executeCommand(cmd, "signrawtransaction");

            /* send raw transation */
            String signedTx = "0100000001237732badde02fb86fb397eeff979cfb357579032062e8a2f48f68c14f8c3f950100000000ffffffff02003b58080000000000001976a9146a43a3cd20d7353dd9f6459520d697976911c3a288ac80f0fa020000000000001976a914fcb7dcaa8a2b9a214045d5de40efc470bbba6d4a88ac000000000000000001ffffffffffffffff00000000ffffffff6a47304402206d010e0810c98f33775bc4cf2dce296c585b70d21e86decb6bcc4a4a4de751140220299f555f0a42e3fa73a938b07b7740f0523286cc992b3e515822f6b57538384d0121023e4356d4d44eb16dd16c8146e4c530c6adaaae0b5c7a3cb38917630f9e2e58d4";
            cmd = "sendsignedtransaction " + signedTx;
            Hcj.executeCommand(cmd, "sendsignedtransaction");

            /* close hcwallet */
            cmd = "closehcwallet";
            Hcj.executeCommand(cmd, "closehcwallet");


        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
