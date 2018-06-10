
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


class Dashj {

    public static void main(String[] args) {

        String priKey = "XGgCcF9BXLhBDB2FXgiEcb9rHYiJuQF2QRSnvm9zgm4VJDd4V97d";
        Dashj dashj = new Dashj();
        String address = dashj.GetAddressByPrivateKey(priKey);
        // should equal XGgCcF9BXLhBDB2FXgiEcb9rHYiJuQF2QRSnvm9zgm4VJDd4V97d
        System.out.println(address);


        //list format txid:vout,txid:vout
        String inputList = "4d49a71ec9da436f71ec4ee231d04f292a29cd316f598bb7068feccabdc59485:0";
        //list format amout:address,amount:address
        String outputList = "0.001:XijDvbYpPmznwgpWD3DkdYNfGmRP2KoVSk";
        // "privatekeys\"  A json array of base58-encoded private keys for signing\n"
        // "[ (json array of strings, or 'null' if none provided)\n"
        // "\"privatekey\"   (string) private key in base58-encoding\n"
        // "      ,...\n"
        // "    ]\n"
        String priKeys = "[\"7qYrzJZWqnyCWMYswFcqaRJypGdVceudXPSxmZKsngN7fyo7aAV\"]"
        //". \"prevtxs\" An json array of previous dependent transaction outputs\n
        // [               (json array of json objects, or 'null' if none provided)\n"
        // {\n"
        // \"txid\":\"id\",             (string, required) The transaction id\n"
        // \"vout\":n,                  (numeric, required) The output number\n"
        // \"scriptPubKey\": \"hex\",   (string, required) script key\n"
        // \"redeemScript\": \"hex\"    (string, required for P2SH) redeem script\n"
        // }\n"
        // ,...\n"
        // ]\n
        String preTxs = "[{\"txid\":\"4d49a71ec9da436f71ec4ee231d04f292a29cd316f598bb7068feccabdc59485\",\"vout\":0,\"scriptPubKey\":\"76a91491b24bf9f5288532960ac687abb035127b1d28a588ac\"}]";
        //SignTx(inputJson, outputJson, "", priKeys, preTxs);

        dashj.SignTranscation(inputList, outputList, "", priKey, preTxs);
    }

    public static String byteArray2String(byte[] input) {
        String res = null;
        try {

            String charsetNameISO = "ISO-8859-1";
            res = new String(input, charsetNameISO);

        } catch (UnsupportedEncodingException e) {

            System.out.println(e.getMessage());

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
        return res;
    }

    public static void dump(String context, String[] result) {

        System.out.printf("//-------------------------------------------------------\n");
        System.out.println("// Dump result return from JNI: " + context);
        for (int i = 0; i < result.length; ++i) {
            System.out.println("// " + result[i]);
        }
        System.out.println("//-------------------------------------------------------");
    }

    static {
        // linux: github.com/monero-project/monero/build/debug/src/wallet/libwallet.so
        // mac:   github.com/monero-project/monero/build/debug/src/wallet/libwallet.dylib
        System.load("/Users/shine/Desktop/code/dash/src/dash_tx.dylib");
    }

    /**
     * 利用私钥获取钱包地址
     * @return 返回钱包的地址
     * @priKey base58 private key
     */
    public native String GetAddressByPrivateKey(String priKey);

    /**
     * 签名交易
     * @param：参加main函数的demo
     * @return 返回hex值包括： signed tx
     */
    public native String SignTranscation(
            String inputList,
            String outAddrList,
            String outDataList,
            String priKeysJson,
            String prevTxsJson
    );

}
