package com.okcoin.vault.jni.xmr;


import java.io.UnsupportedEncodingException;


class Hot extends WalletBase {

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

    public byte[] exportOutputs(String offsetTxid) throws UnsupportedEncodingException {
        params = initParams();
        params.add("export_outputs");
        params.add("wallet_hot/op");

        if (offsetTxid != null && offsetTxid.length() > 0) {
            params.add(offsetTxid);
        }

        byte[][] res = xmrj.transcation(params.toArray(), null, null,null, null);
        return res[res.length - 1];
    }

    public void importKeyImages(byte[] keyImages, String offsetTxid) {
        params = initParams();
        params.add("import_key_images");
        params.add("wallet_hot/ki");

        if (offsetTxid != null && offsetTxid.length() > 0) {
            params.add(offsetTxid);
        }

        xmrj.transcation(params.toArray(), Moneroj.XMR_KEY_IMAGES, keyImages,null, null);
    }

    public byte[] produceUnsignedTx(String targetAddress, String priority, String amount) {

        params = initParams();

        params.add("transfer");
        params.add(priority);
        params.add(targetAddress);
        params.add(amount);
        byte[][] res = xmrj.transcation(params.toArray(), null, null,null, null);
        return res[res.length - 1];
    }

    public byte[][] submitTransaction(byte[] signedTx) {

        params = initParams();
        params.add("submit_transfer");
        byte[][] res = xmrj.transcation(params.toArray(), Moneroj.XMR_SIGNED_TX, signedTx,null, null);
        return res;
    }

}

