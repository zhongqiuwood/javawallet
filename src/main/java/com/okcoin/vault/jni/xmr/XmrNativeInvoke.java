package com.okcoin.vault.jni.xmr;

/**
 * Created by yanpeiling on 2018.6.8.
 */
public class XmrNativeInvoke {
    /**
     * 可执行 monero cli命令行任何操作
     * 包括创建viewonly钱包和viewandsend钱包
     *
     * @param monero-wallet-cli 命令行参数，详见 monero-wallet-cli -h
     * @return 返回钱包的地址和viewkey
     * @keys 保留, 暂时不用
     * @values 保留, 暂时不用
     */
    public static native String[] execute(Object[] params, String[] keys, String[] values);


    /**
     * 执行 monero cli命令行
     * 包括 export_outputs/import_outputs/export_keyimages/import_keyimages/transfer/sign_transfer/submit_transfer
     *
     * @param monero-wallet-cli 命令行参数，详见 monero-wallet-cli -h
     * @key 上述几种命令的参数名
     * @value 上述几种命令的参数值
     * @return 返回hex值包括： outputs/keyimages/unsigned tx/signed tx
     * @keys 保留, 暂时不用
     * @values 保留, 暂时不用
     */
    public static native byte[][] transcation(Object[] params, String key, byte[] value, String[] keys, String[] values);
}
