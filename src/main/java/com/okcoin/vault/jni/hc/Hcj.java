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
            // cmd = "openhcwallet --rpcconnect=127.0.0.1:9001 -u rpcuser -P rpcpass --pass 123456";
            /* 签名和生成地址传递的参数 */
            cmd = "openhcwallet --pass=123456";
            Hcj.executeCommand(cmd, "openhcwallet");

            String seckey = "9995335dfb8074beb2ff6b207e0fd32d6883273fa3d730955ebca7100f904954";
            cmd ="getaddressbyprivatekey " + seckey;
            Hcj.executeCommand(cmd, "getaddressbyprivatekey");

            /* create unsigned transaction */
            // String numoftxin = "2 ";
            // String txin1 = "9ffd4c7f7506e2b03372777c6cbf1e95dcc336971d88c1d51875d40c85b9e80d 0 ";
            // String txin2 = "4cb48b3ef17d186e103316e680d2dd3ddee3b4cc9567420e0ad9802462f525014 1 ";
            // String toamount = "HsDU3Mz8EvffEBuCEqJwiLoVAAUDhwXsMXr 10000000 ";
            // String chargeamount = "HsMDowUazGqhVwEXyh8AdUcm66hGGW6gW2f 140000000";
            // cmd = "createrawtransaction " + numoftxin + txin1 + txin2 + toamount + chargeamount;
            // results = Hcj.executeCommand(cmd, "createrawtransacticon");

            
            /* sign raw transation */
            String rawTx = "01000000020de8b9850cd47518d5c1881d9736c3dc951ebf6c7c777233b0e206757f4cfd9f0000000000ffffffff1450522f460298ade0207456c9b4e3de3dddd280e61633106e187df13e8bb44c0100000000ffffffff02809698000000000000001976a91413fdabc1052e0f6a5637d29e66b6ca6da5f0a45b88ac003b58080000000000001976a914690da62ca4632fdf56804223973111fdf004ce7a88ac000000000000000002ffffffffffffffff00000000ffffffff00ffffffffffffffff00000000ffffffff00 ";
            String privkeys = "f7217a9d76751de0fb1d9f9d1d47f1f36205e4909263c890980273849f36a77f 9995335dfb8074beb2ff6b207e0fd32d6883273fa3d730955ebca7100f904954";
            /* 引用的vout的scriptPubKey,多个vout只需在后面用空格隔开追加即可,要与创建交易时候的顺序对应 */
            String scriptPubKey = "76a914690da62ca4632fdf56804223973111fdf004ce7a88ac";
            cmd = "signrawtransaction " + rawTx + privkeys;
            results = Hcj.executeCommand(cmd, "signrawtransaction");

            /* send raw transation */
            // String signedTx = results[0];
            // cmd = "sendsignedtransaction " + signedTx;
            // results =  Hcj.executeCommand(cmd, "sendsignedtransaction");

            // /* close hcwallet */
            // cmd = "closehcwallet";
            // Hcj.executeCommand(cmd, "closehcwallet");


        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
