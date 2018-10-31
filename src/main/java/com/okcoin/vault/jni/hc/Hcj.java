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
            /* 创建交易和发送交易传递的参数 */
            cmd = "openhcwallet --rpcconnect=127.0.0.1:9001 -u rpcuser -P rpcpass --pass 123456";
            /* 签名和生成地址传递的参数 */
            //cmd = "openhcwallet --pass=123456";
            Hcj.executeCommand(cmd, "openhcwallet");

            String seckey = "9995335dfb8074beb2ff6b207e0fd32d6883273fa3d730955ebca7100f904954";
            cmd ="getaddressbyprivatekey " + seckey;
            Hcj.executeCommand(cmd, "getaddressbyprivatekey");

            /* create unsigned transaction */
            String numoftxin = "1 ";
            String txin1 = "dbe4c85af685fc009b6bc1639e6f374a8b939079ef5eff8e6700cfb16f9eedc9 1 ";
            String toamount = "HsDU3Mz8EvffEBuCEqJwiLoVAAUDhwXsMXr 10000000 ";
            String chargeamount = "HsMDowUazGqhVwEXyh8AdUcm66hGGW6gW2f 140000000";
            cmd = "createrawtransaction " + numoftxin + txin1 + toamount + chargeamount;
            results = Hcj.executeCommand(cmd, "createrawtransaction");

            
            /* sign raw transation */
            String rawTx = "0100000001c9ed9e6fb1cf00678eff5eef7990938b4a376f9e63c16b9b00fc85f65ac8e4db0100000000ffffffff02003b58080000000000001976a914690da62ca4632fdf56804223973111fdf004ce7a88ac809698000000000000001976a91413fdabc1052e0f6a5637d29e66b6ca6da5f0a45b88ac000000000000000001ffffffffffffffff00000000ffffffff00 ";
            String privkeys = "9995335dfb8074beb2ff6b207e0fd32d6883273fa3d730955ebca7100f904954 ";
            /* 引用的vout的scriptPubKey,多个vout只需在后面用空格隔开追加即可,要与创建交易时候的顺序对应 */
            String scriptPubKey = "76a914690da62ca4632fdf56804223973111fdf004ce7a88ac";
            cmd = "signrawtransaction " + rawTx + privkeys + scriptPubKey;
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
