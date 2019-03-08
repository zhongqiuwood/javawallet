package com.okcoin.vault.jni.yoyow;

public class YoyoNativeInvoke {

    static {
        System.load("/home/meng/work/github.com/okblockchainlab/yoyow-core/build/programs/yoyow_sign/libyoyow_sign.so");
    }

    static public native String sign(String tx, String wif_private, String chain_id);
    static public native String generateKey(String brain_key, int sequence_number);
    static public native String privateTopublic(String wif);
    static public native String baseTransaction(String last_irreversible_block_id, String last_irreversible_block_time, String expiration);
    static public native String generateTransaction(String last_irreversible_block_id, 
                                                    String last_irreversible_block_time,
                                                    String from, 
                                                    String to, 
                                                    String amount, 
                                                    String memo,
                                                    String from_memo_private_wif,
                                                    String to_memo_public_key,
                                                    String current_fees_json,
                                                    String expiration,
                                                    String asset_id
                                                );
    static public native String decryptMemo(String memo_json, String memo_private_wif);

    public static void main(String[] args)
    {
        try {
            String tx = "{\"ref_block_num\": 0,\"ref_block_prefix\": 0,\"expiration\": \"1970-01-01T00:00:00\",\"operations\": [[0, {\"fee\": {\"total\": {\"amount\": 20000,\"asset_id\": 0},\"options\": {\"from_csaf\": {\"amount\": 20000,\"asset_id\": 0}}},\"from\": 25997,\"to\": 26264,\"amount\": {\"amount\": 100000,\"asset_id\": 0}}]],\"signatures\": []}";
            String wif = "5JuTEwvFqnhjWRtvdUhTxVDhZBG71QgiRTmRV1B5Ph4jFmWBM7F";
            String chain = "ae4f234c75199f67e526c9478cf499dd6e94c2b66830ee5c58d0868a3179baf6";
            String result = YoyoNativeInvoke.sign(tx, wif, chain);
            System.out.println(result);

            result = YoyoNativeInvoke.generateKey(chain, (int)0);
            System.out.println(result);

            result = YoyoNativeInvoke.privateTopublic(result);
            System.out.println(result);

            result = YoyoNativeInvoke.baseTransaction("01d2df3dc73f6f04072c117ca4dcae1d46876f44", "2018-09-18T01:51:06", "30");
            System.out.println(result);

            result = YoyoNativeInvoke.generateTransaction("01d2df3dc73f6f04072c117ca4dcae1d46876f44",
                                                    "2018-09-18T01:51:06",
                                                    "263324063",
                                                    "26264",
                                                    "10",
                                                    "memo",
                                                    "5JPTHdCy5QNhgKsTeQGxPRxM5FYj9MKDLoNLvTcrKQgg5GqAPa7",
                                                    "YYW6yyVSHhKGq9a3BZ99HMRLygbxUf5JHo3vChTBtGXtuvwzZRLnv",
                                                    "{\"parameters\":[[0,{\"fee\":20000,\"price_per_kbyte\":100000,\"min_real_fee\":0,\"min_rf_percent\": 0}]]}",
                                                    "30",
                                                    "0");
            System.out.println(result);

            result = YoyoNativeInvoke.decryptMemo( "{\"from\":\"YYW5q8zUko5Evds7dnh486afwavF7oW8aje7DeusGABVMTfcChFvn\",\"to\":\"YYW6nBpB5aCTL84bA34qVwaskRtoz7XaTW66LzLy3pKP7ta5mZVrs\",\"nonce\":\"6558778315539393896\",\"message\":\"0437fc73cd356964784a3b169b9b6db3ac642fa278719a82acad1a4f3ae8ce16\"}", 
                               "5JPTHdCy5QNhgKsTeQGxPRxM5FYj9MKDLoNLvTcrKQgg5GqAPa7");
            System.out.println(result);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
