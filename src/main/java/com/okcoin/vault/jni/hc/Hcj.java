package com.okcoin.vault.jni.hc;

import com.okcoin.vault.jni.common.Util;

public class Hcj {

    static String cmd = null;

    static {
        System.load("/home/meng/work/go/src/github.com/HcashOrg/hcwallet/libhcwallet.so");
    }

    static public native String[] execute(String command);

    public static String[] executeCommand(String cmd, String context) {

        String[] results = Hcj.execute(cmd);
        Util.dump(context, results);
        return results;
    }

    public static void main(String[] args)
    {
        try {
            /* open hcwallet */
            String[] results;
            cmd = "openhcwallet -u rpcuser -P rpcpass --pass 123456";
            Hcj.executeCommand(cmd, "openhcwallet");

            String seckey = "9995335dfb8074beb2ff6b207e0fd32d6883273fa3d730955ebca7100f904954";
            cmd ="getaddressbyprivatekey " + seckey;
            Hcj.executeCommand(cmd, "getaddressbyprivatekey");

            /* create unsigned transaction */
            String numoftxin = "1 ";
            String txin1 = "3f34b7feb5af823258dfd806370dc604b0953b8f9e0abe89a637d6ce8d40907d 0 ";
            String toamount = "HsDU3Mz8EvffEBuCEqJwiLoVAAUDhwXsMXr 30000000 ";
            String chargeamount = "HsMDowUazGqhVwEXyh8AdUcm66hGGW6gW2f 160000000";
            cmd = "createrawtransaction " + numoftxin + txin1 + toamount + chargeamount;
            results = Hcj.executeCommand(cmd, "createrawtransaction");

            
            /* sign raw transation */
            String rawTx = results[0] + " ";
            String privkeys = "9995335dfb8074beb2ff6b207e0fd32d6883273fa3d730955ebca7100f904954";
            cmd = "signrawtransaction " + rawTx + privkeys;
            results = Hcj.executeCommand(cmd, "signrawtransaction");

            /* send raw transation */
            String signedTx = results[0];
            cmd = "sendsignedtransaction " + signedTx;
            results =  Hcj.executeCommand(cmd, "sendsignedtransaction");

            /* close hcwallet */
            cmd = "closehcwallet";
            Hcj.executeCommand(cmd, "closehcwallet");


        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
