package com.okcoin.vault.jni.xmr;

import java.util.ArrayList;
import java.util.List;


class Cold extends WalletBase {

    public Cold() {
        walletDataPath = Moneroj.COLD_WALLET;
    }

    public String[] createWallet() {

        params = createParams();

        params.add("--spend_key");
        params.add(spendkey);

        params.add("--generate-from-spend-key");
        params.add(walletDataPath);

        return xmrj.execute(params.toArray(), null, null);
    }

    protected void setDaemonAddress(List<String> p) {
        // cold wallet connects nobody
        p.add("--daemon-address");
        p.add("nobody:nobody");
    }

//    public void importOutputs(byte[] outputs) {
//        params = initParams();
//        params.add("import_outputs");
//        params.add("wallet_cold/op");
//        xmrj.transcation(params.toArray(), Moneroj.XMR_TX_OUTPUTS, outputs,null, null);
//    }
//
//    public byte[] exportKeyImages() {
//        params = initParams();
//        params.add("export_key_images");
//        params.add("wallet_cold/ki");
//        byte[][] res = xmrj.transcation(params.toArray(), null, null,null, null);
//        return res[res.length - 1];
//    }


    public byte[] exportKeyImagesByOutputs(byte[] outputs) {
        params = initParams();
        params.add("import_outputs");
        params.add("wallet_cold/op");
        byte[][] res = xmrj.transcation(params.toArray(), Moneroj.XMR_TX_OUTPUTS, outputs,null, null);
        return res[res.length - 1];
    }

    public byte[][] signTransaction(byte[] unsignedTx) {
        params = initParams();
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
        return xmrj.transcation(params.toArray(), Moneroj.XMR_UNSIGNED_TX, unsignedTx,null, null);
    }

}
