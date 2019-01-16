package com.okcoin.vault.jni.common;

import org.bitcoinj.core.Utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class Util {

    public static final String XMR_UNSIGNED_TX = "UnsignedTx";
    public static final String XMR_SIGNED_TX   = "SignedTx";
    public static final String XMR_KEY_IMAGES  = "KeyImages";
    public static final String XMR_TX_OUTPUTS  = "TxOutputs";

    public static void dump(String context, String[] result) {

        System.out.printf("//-------------------------------------------------------\n");
        System.out.println("// Dump result returned from JNI: " + context);
        for (int i = 0; i < result.length; ++i) {
            System.out.println("// " + result[i]);
        }
        System.out.println("//-------------------------------------------------------");
    }



    public static void dumpResult(String fname, byte[][] results, boolean sizeOnly) {
        boolean encode = false;

        for (int i = 0; i < results.length; i++) {

            if (sizeOnly && i % 2 == 1) {
                String value = Util.byteArray2String(results[i]);

                if (encode) {
                    value = Utils.HEX.encode(results[i]);
                    encode = false;
                }
                System.out.printf(" // jni result %s: <%s>\n", fname,value);
            } else {
                String schema = Util.byteArray2String(results[i]);
                System.out.printf(" // jni result %s: <%s>\n", fname, schema);
                if (schema.compareToIgnoreCase(XMR_UNSIGNED_TX) == 0 ||
                        schema.compareToIgnoreCase(XMR_SIGNED_TX) == 0 ||
                        schema.compareToIgnoreCase(XMR_KEY_IMAGES) == 0 ||
                        schema.compareToIgnoreCase(XMR_TX_OUTPUTS) == 0 ) {
                    encode = true;
                }
            }
        }
    }

    public static byte[] getResultBySchema(String schema, byte[][] results) {

        for (int i = 0; i < results.length; i += 2) {

            String resultSchema = Util.byteArray2String(results[i]);
            if (resultSchema.compareToIgnoreCase(schema) == 0) {
                return results[i + 1];
            }
        }

        return null;
    }

    public static List<String> getResultListBySchema(String schema, byte[][] results) {
        List resultList = new ArrayList();

        for (int i = 0; i < results.length; i += 2) {

            String resultSchema = Util.byteArray2String(results[i]);
            if (resultSchema.compareToIgnoreCase(schema) == 0) {
                resultList.add(Util.byteArray2String(results[i + 1]));
            }
        }

        return resultList;
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
}

