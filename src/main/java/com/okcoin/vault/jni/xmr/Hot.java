package com.okcoin.vault.jni.xmr;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

class Hot extends WalletBase {

    public Hot() {
        walletDataPath = WalletKey.getHOT_WALLET_PATH();
    }


    public String[] createWallet() throws UnsupportedEncodingException {
        if (walletExists()) {
            return null;
        }
        params = createParams();
        params.add("--view_key");
        params.add(viewkey);
        params.add("--start_height");
        params.add("0");
        params.add("--address");
        params.add(address);
        params.add("--generate-from-view-key");
        params.add(walletDataPath);
        return XmrNativeInvoke.execute(params.toArray(), null, null);
    }

    protected List<String> createHotWalletParams() {
        List<String> p = super.createParams();
        p.add("--wallet-file");
        p.add(walletDataPath);
        p.add("--command");

        return p;
    }

    public byte[][] getBalance()  {
        params = createHotWalletParams();
        params.add("balance");
        params.add("detail");

        byte[][] res = XmrNativeInvoke.transcation(params.toArray(), null, null,null, null);

        return res;
    }

    // exportOutputs usage:
    // #1: 仅指定 offset_txid, 导出的tx: [offset_txid, tail]
    // #2: 仅指定 begin_txindex, 导出的tx: [begin_txindex, tail]
    // #3: 仅指定 begin_txindex 和 end_txindex, 导出的tx: [begin_txindex, end_txindex]
    // #4: offset_txid, begin_txindex 和 end_txindex都指定, begin_txindex 和 end_txindex会被忽略, 导出的tx: [offset_txid, tail]
    public byte[][] exportOutputs(String offsetTxid, String begin_txindex, String end_txindex) throws UnsupportedEncodingException {
        params = createHotWalletParams();
        params.add("export_outputs");
        params.add("dummy");
        params.add("offset_txid=" + offsetTxid);
        params.add("begin_txindex=" + begin_txindex);
        params.add("end_txindex=" + end_txindex);

        byte[][] res = XmrNativeInvoke.transcation(params.toArray(), null, null,null, null);
        return res;
    }

    // importKeyImages usage:
    // #1: 仅指定 offset_txid, 导入的image: [offset_txid, offset_txid + len(keyImages)]
    // #2: 仅指定 begin_txindex, 导入的image: [begin_txindex, begin_txindex + len(keyImages)]
    // #3: offset_txid, begin_txindex都指定, begin_txindex会被忽略, 导入的image: [offset_txid, offset_txid + len(keyImages)]
    public byte[][] importKeyImages(byte[] keyImages, String offsetTxid, String begin_txindex) {
        params = createHotWalletParams();
        params.add("import_key_images");
        params.add("dummy");
        params.add("offset_txid=" + offsetTxid);
        params.add("begin_txindex=" + begin_txindex);

        return XmrNativeInvoke.transcation(params.toArray(), Moneroj.XMR_KEY_IMAGES, keyImages,null, null);
    }

    public byte[][] produceUnsignedTx(String targetAddress, String priority, String amount, String preferred_txid) {

        params = createHotWalletParams();

        params.add("transfer");
        params.add("preferred_txid=" + preferred_txid);
        params.add(priority);
        params.add(targetAddress);
        params.add(amount);
        byte[][] res = XmrNativeInvoke.transcation(params.toArray(), null, null,null, null);
        return res;
    }

    public byte[][] submitTransaction(byte[] signedTx) {

        params = createHotWalletParams();
        params.add("submit_transfer");
        byte[][] res = XmrNativeInvoke.transcation(params.toArray(), Moneroj.XMR_SIGNED_TX, signedTx,null, null);
        return res;
    }


}

