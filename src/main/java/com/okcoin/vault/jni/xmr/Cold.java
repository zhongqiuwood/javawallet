package com.okcoin.vault.jni.xmr;

import java.util.List;

class Cold extends WalletBase {

    protected List<String> createColdWalletParams() {
        List<String> params = super.createParams();

        params.add("--spend_key");
        params.add(spendkey);

        params.add("--command");
        return params;
    }

    public String[] createWallet() {

        params = super.createParams();
        params.add("--spend_key");
        params.add(spendkey);

        return XmrNativeInvoke.execute(params.toArray(), null, null);
    }

    protected void setDaemonAddress(List<String> p) {
        // cold wallet connects nobody
        p.add("--daemon-address");
        p.add("nobody:nobody");
    }

    public byte[] exportKeyImagesByOutputs(byte[] outputs) {
        params = createColdWalletParams();
        params.add("import_outputs");
        params.add("dummy");
        byte[][] res = XmrNativeInvoke.transcation(params.toArray(), Moneroj.XMR_TX_OUTPUTS, outputs,null, null);
        return res[res.length - 1];
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
        return XmrNativeInvoke.transcation(params.toArray(), Moneroj.XMR_UNSIGNED_TX, unsignedTx,null, null);
    }

}
