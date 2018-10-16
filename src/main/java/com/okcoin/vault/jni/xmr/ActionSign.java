package com.okcoin.vault.jni.xmr;

import com.okcoin.vault.jni.common.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.okcoin.vault.jni.xmr.Moneroj.XMR_SIGNED_TX;
import static com.okcoin.vault.jni.xmr.Moneroj.XMR_TX_ID;

public class ActionSign implements Runnable {

    Cold c;
    byte[] unsignedTx;
    byte[][] result;

    public ActionSign(Cold cold, byte[] unsignedTx) {
        this.c = cold;
        this.unsignedTx = unsignedTx;
    }

    public byte[][] getResult() {
        return result;
    }

    public void run() {
        try {

            System.out.printf("============================== signTransaction =========================================\n");
            System.out.printf("========================================================================================\n");

            result = c.signTransaction(unsignedTx);
            Util.dumpResult("signTransaction", result, true);

//            byte[] signedTx = Util.getResultBySchema(XMR_SIGNED_TX, signResult);
//            byte[] txidByte = Util.getResultBySchema(XMR_TX_ID, signResult);
//
//            String txid = Util.byteArray2String(txidByte);
//
//            System.out.printf("signTransaction returned. %s <%s>, %s size<%d>\n",
//                    XMR_TX_ID,
//                    txid,
//                    XMR_SIGNED_TX,
//                    signedTx.length);

//            if (signedTx.length == 0) {
//                System.out.printf("Failed to sign tx. size<%d>\n", signedTx.length);
//                return;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
