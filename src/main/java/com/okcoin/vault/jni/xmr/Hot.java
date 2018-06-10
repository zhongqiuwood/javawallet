package com.okcoin.vault.jni.xmr;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


class Hot extends WalletBase {
    protected String walletDataPath;

    public Hot() {
        walletDataPath = Moneroj.HOT_WALLET;
    }

    public String[] createWallet() throws UnsupportedEncodingException {
        params = createParams();
        params.add("--view_key");
        params.add(viewkey);
        params.add("--start_height");
        params.add("0");
        params.add("--address");
        params.add(address);
        params.add("--generate-from-view-key");
        params.add(walletDataPath);
        return xmrj.execute(params.toArray(), null, null);
    }

    protected List<String> createHotWalletParams() {
        List<String> p = super.createParams();
        p.add("--wallet-file");
        p.add(walletDataPath);
        p.add("--command");

        return p;
    }

    public byte[] exportOutputs(String offsetTxid) throws UnsupportedEncodingException {
        params = createHotWalletParams();
        params.add("export_outputs");
        params.add("wallet_hot/op");

        if (offsetTxid != null && offsetTxid.length() > 0) {
            params.add(offsetTxid);
        }

        byte[][] res = xmrj.transcation(params.toArray(), null, null,null, null);
        return res[res.length - 1];
    }

    public void importKeyImages(byte[] keyImages, String offsetTxid) {
        params = createHotWalletParams();
        params.add("import_key_images");
        params.add("wallet_hot/ki");

        if (offsetTxid != null && offsetTxid.length() > 0) {
            params.add(offsetTxid);
        }

        xmrj.transcation(params.toArray(), Moneroj.XMR_KEY_IMAGES, keyImages,null, null);
    }

    public byte[] produceUnsignedTx(String targetAddress, String priority, String amount) {

        params = createHotWalletParams();

        params.add("transfer");
        params.add(priority);
        params.add(targetAddress);
        params.add(amount);
        byte[][] res = xmrj.transcation(params.toArray(), null, null,null, null);
        return res[res.length - 1];
    }

    public byte[][] submitTransaction(byte[] signedTx) {

        params = createHotWalletParams();
        params.add("submit_transfer");
        byte[][] res = xmrj.transcation(params.toArray(), Moneroj.XMR_SIGNED_TX, signedTx,null, null);
        return res;
    }

}

