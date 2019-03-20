package com.okcoin.vault.jni.xmr;

import java.io.UnsupportedEncodingException;
import java.util.List;

class Cold extends WalletBase {

    public Cold() {
        walletDataPath = WalletKey.getCOLD_WALLET_PATH();
    }

    protected List<String> createColdWalletParams() {
        List<String> params = super.createParams();

//        if (Moneroj.STORE_KEYS) {
//            params.add("--wallet-file");
//            params.add(walletDataPath);
//        } else {
//            params.add("--do_not_store_keys");
//            params.add("--generate-from-spend-key");
//            params.add(walletDataPath);
//        }

        params.add("--spend_key");
        params.add(spendkey);

        params.add("--command");
        return params;
    }

//    public String[] createWallet() {
//
//        if (walletExists()) {
//            return null;
//        }
//
//        params = super.createParams();
//
//        if (Moneroj.STORE_KEYS) {
//            params.add("--do_not_store_keys");
//            params.add("--start_height");
//            params.add("0");
//            params.add("--address");
//            params.add(address);
//            params.add("--generate-from-spend-key");
//            params.add(walletDataPath);
//        }
//
//        params.add("--spend_key");
//        params.add(spendkey);
//        return XmrNativeInvoke.execute(params.toArray(), null, null);
//    }

    protected void setDaemonAddress(List<String> p) {
//         cold wallet connects nobody
        p.add("--daemon-address");
        p.add("dummy:10000");
    }

    public byte[][] getBalance() {
        params = createColdWalletParams();
        params.add("balance");
        params.add("detail");

        byte[][] res = XmrNativeInvoke.transcation(params.toArray(), null, null,null, null);
        return res;
    }

    public byte[][] exportKeyImagesByOutputs(byte[] outputs) {
        params = createColdWalletParams();
        params.add("import_outputs");
        params.add("dummy");
        byte[][] res = XmrNativeInvoke.transcation(params.toArray(), Moneroj.XMR_TX_OUTPUTS, outputs,null, null);
        return res;
    }

    public byte[][] signTransaction(byte[] unsignedTx) {
        params = createColdWalletParams();
        params.add("sign_transfer");
        //---------------------------------------------------------------------------------------
        // 非常重要，如果用RPC /sendrawtransaction 发送交易，务必将 useRPCsendrawtransaction 设为true
        //---------------------------------------------------------------------------------------
        //---------------------------------------------------------------------------------------
        // 非常重要，如果用RPC /sendrawtransaction 发送交易，务必将 useRPCsendrawtransaction 设为true
        //---------------------------------------------------------------------------------------
        //---------------------------------------------------------------------------------------
        // 非常重要，如果用RPC /sendrawtransaction 发送交易，务必将 useRPCsendrawtransaction 设为true
        //---------------------------------------------------------------------------------------
        boolean useRPCsendrawtransaction = false;
        if (useRPCsendrawtransaction) {
            // 生成未加密的签名交易hex
            params.add("export");
        }
        byte[][] res = XmrNativeInvoke.transcation(params.toArray(), Moneroj.XMR_UNSIGNED_TX, unsignedTx,null, null);
        return res;
    }

}
