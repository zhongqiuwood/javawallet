package com.okcoin.vault.jni.xmr;

import com.okcoin.vault.jni.common.Util;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Moneroj implements Runnable {

    public static final String HOT_WALLET_PATH =
            "/Users/oak/go/src/github.com/okblockchainlab/javawallet/wallet_data/xmr/mainnet/mainnet3";

    public static final String DAEMON_URL = "127.0.0.1:18081";
//    public static final String DAEMON_URL = "192.168.149.229:58081";

    ////////////////////////////////////////////
    // main net
    public static final boolean ENABLE_TESTNET = false;

    public static final String SPEND_KEY =
            "f11d529fcf40303ad4f2791610f4f510d7613e33dbfb89cc59a4fea8173a670c";

    public static final String VIEW_KEY =
            "232b946a299f307e1383a616cd3a721ab3ab159fc75b11253a75ad99a1617602";

    public static final String ADDRESS =
            "4AVJbZPdQFZ3umfGYATvt26mnTyGaCW8zP9MyJPBM77fbEPWSYj3wRMeztLVnBsNTHHeiqTnAxDRpKoF44ENxp5RHd31xqM";

    public static final String TARGET_ADDRESS =
            "48PjH3ksv1fiXniKvKvyH5UtFs5WhfS2Vf7U3TwzdRJtCc7HJWvCQe56dRahyhQyTAViXZ8Nzk4gQg6o4BJBMUoxNy8y8g7";
    ///////////////////////////////////////////

    static {
        // linux: github.com/monero-project/monero/build/debug/src/wallet/libwallet.so
        // mac:   github.com/monero-project/monero/build/debug/src/wallet/libwallet.dylib
        System.load("/Users/oak/go/src/github.com/okchain/monero_static/build/dynamic_on/src/simplewallet_so/libmonerod.dylib");
    }

    public static final String XMR_VIEW_KEY    = "SecretViewKey";
    public static final String XMR_ADDRESS     = "ColdWalletAddress";
    public static final String XMR_UNSIGNED_TX = "UnsignedTx";
    public static final String XMR_SIGNED_TX   = "SignedTx";
    public static final String XMR_KEY_IMAGES  = "KeyImages";
    public static final String XMR_TX_OUTPUTS  = "TxOutputs";
    public static final String XMR_TX_ID       = "TxId";
    public static final String XMR_ERROR       = "Error";
    public static final String XMR_BALANCE                = "Balance";
    public static final String XMR_UNLOCKED_BALANCE       = "UnlockedBalance";


    public static void main(String[] args) {
        try {
            int concurrent = 1;
            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(concurrent);
            for (int i = 0; i < concurrent; ++i) {
                fixedThreadPool.execute(new Moneroj());
            }

            fixedThreadPool.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        try {
            String[] result;
            Cold c = new Cold();
            Hot h  = new Hot();

            boolean createWallet = false;
            createWallet = true;
            if (createWallet) {
                result = c.createWallet();
                Util.dump("create cold wallet", result);

                result = h.createWallet();
                Util.dump("create hot wallet", result);
            }

            byte[][] balanceList = h.getBalance();
            dumpResult("getBalance", balanceList, false);

            if (true) { return; }

            boolean exports_outputs = true;

             if (exports_outputs) {

                 System.out.printf("========================================================================================\n");
                 System.out.printf("============================== hot wallet exports outputs ===============================\n");
                 System.out.printf("========================================================================================\n");

                 // 发生在offsetTxid之前的tx不会被导出
                 String offsetTxid;
                 offsetTxid = "";
//            offsetTxid = "61b0439644ac86eac365437d222088818b640470af8c7b2f2234a6ae1fbf6b32";
                 byte[][] outputsResult = h.exportOutputs(offsetTxid);
                 dumpResult("exportOutputs", outputsResult, true);

                 byte[] outputs = getResultBySchema(XMR_TX_OUTPUTS, outputsResult);
                 System.out.printf("outputs returned. size<%d>\n", outputsResult.length);


                 System.out.printf("========================================================================================\n");
                 System.out.printf("====================== cold wallet exports KeyImages By Outputs =========================\n");
                 System.out.printf("========================================================================================\n");

                 byte[][] keyImagesResult = c.exportKeyImagesByOutputs(outputs);

                 dumpResult("exportKeyImagesByOutputs", keyImagesResult, true);

                 byte[] keyImages = getResultBySchema(XMR_KEY_IMAGES, keyImagesResult);

                 System.out.printf("keyImages returned. size<%d>\n", keyImagesResult.length);

                 if (keyImages.length == 0) {
                     System.out.printf("Failed to export keyImages. size<%d>\n", keyImagesResult.length);
                     return;
                 }

                 System.out.printf("========================================================================================\n");
                 System.out.printf("============================== importKeyImages =========================================\n");
                 System.out.printf("========================================================================================\n");

                 h.importKeyImages(keyImages, offsetTxid);
             }
            System.out.printf("========================================================================================\n");
            System.out.printf("============================== produceUnsignedTx =======================================\n");
            System.out.printf("========================================================================================\n");

            byte[][] unsignedTxRes = h.produceUnsignedTx(Moneroj.TARGET_ADDRESS, "unimportant", "0.0001");
            byte[] unsignedTx = getResultBySchema(XMR_UNSIGNED_TX, unsignedTxRes);

            System.out.printf("unsigned_monero_tx returned. size<%d>\n", unsignedTx.length);
            dumpResult("produceUnsignedTx", unsignedTxRes, true);

            if (unsignedTx.length == 0) {
                System.out.printf("Failed to produce unsigned_monero_tx. size<%d>\n", unsignedTx.length);
                return;
            }

            boolean stop = false;
//            stop = true;

            if (stop) {
                return;
            }

            System.out.printf("========================================================================================\n");
            System.out.printf("============================== signTransaction =========================================\n");
            System.out.printf("========================================================================================\n");

            byte[][] signResult = c.signTransaction(unsignedTx);
            dumpResult("signTransaction", signResult, true);

            byte[] signedTx = getResultBySchema(XMR_SIGNED_TX, signResult);
            byte[] txidByte = getResultBySchema(XMR_TX_ID, signResult);

            String txid = byteArray2String(txidByte);

            System.out.printf("signTransaction returned. %s <%s>, %s size<%d>\n",
                    XMR_TX_ID,
                    txid,
                    XMR_SIGNED_TX,
                    signedTx.length);

            if (signedTx.length == 0) {
                System.out.printf("Failed to sign tx. size<%d>\n", signedTx.length);
                return;
            }

            boolean submit = false;
//            submit = true;

            if (submit) {
                byte[][] submitResult = h.submitTransaction(signedTx);
                System.out.printf("submitResult returned. size<%d>\n", submitResult.length);

                for (byte[] res : submitResult) {
                    System.out.printf("submitResult. <%s>\n", byteArray2String(res));
                }
            }

        } catch (UnsupportedEncodingException e) {

            System.out.println(e.getMessage());

        } catch (Exception e) {
            System.out.println("// catch Exception");
            System.out.println(e.getMessage());
        }
    }


    public static void dumpResult(String fname, byte[][] results, boolean sizeOnly) {

        for (int i = 0; i < results.length; i++) {

            if (sizeOnly && i % 2 == 1) {
                System.out.printf("// %s: size <%d>\n", fname,
                        byteArray2String(results[i]).length());
            } else {
                System.out.printf("// %s: <%s>\n", fname,
                        byteArray2String(results[i]));
            }
        }
    }


    public static byte[] getResultBySchema(String schema, byte[][] results) {

        for (int i = 0; i < results.length; i += 2) {

            String resultSchema = byteArray2String(results[i]);
            if (resultSchema.compareToIgnoreCase(schema) == 0) {
                return results[i + 1];
            }
        }

        return null;
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

    /////////////////////////////////////////////////////////////////////////////
    // test met
//    public static final String TARGET_ADDRESS =
//            "A1puKBLCKkdgB8D2LkGzQPYU8mYjVohwEgoAqcLKjQ2PCgAci6T5wMsVHhXekcPP7sEYqkR4KBGddAbErMTJhd737f9oAAD";
//
//    public static final String SPEND_KEY =
//            "cac0eb97cddd955c7ef0a992c44077bbb875221845889afad1d4e761e045740e";
//
//    public static final String VIEW_KEY =
//            "512330f6544e50a3ad233f3b23a44eea2f7a78d16d20c2d4ed3d74eb07627806";
//
//    public static final String ADDRESS =
//            "9sXXpRtxYgh5yMYexKccr4dx35JM8rc1e8M7UDBfFCQmSgau1mQxTLk3j8MStt4CCnd8C99BGTw9uQ4DqhwtKq8r32pBpxe";
    ////////////////////////////////////////////////////////////////////////////

//    public static final String SPEND_KEY =
//            "2ecf6967f6697795647ac61bc5a5323f0c2c5f835e68d00072db1d85c1855305";
//
//    public static final String VIEW_KEY =
//            "b580aee166efde144eb1d1a174aad918d7486ead4677825408b741cb7200aa07";
//
//    public static final String ADDRESS =
//            "9u7McB2tQJQQbd2jrncdRW43m72Y5vbrQDt459FQB7SHbyCCnwuaBhSgvmdnQykqQecpguE8Csnms9nzoAK5QxzMGF75xqj";
}

//[wallet A2PZyj]: help
//        Commands:
//        account
//        account new <label text with white spaces allowed>
//        account switch <index>
//        account label <index> <label text with white spaces allowed>
//        account tag <tag_name> <account_index_1> [<account_index_2> ...]
//        account untag <account_index_1> [<account_index_2> ...]
//        account tag_description <tag_name> <description>
//        address [ new <label text with white spaces allowed> | all | <index_min> [<index_max>] | label <index> <label text with white spaces allowed>]
//        address_book [(add ((<address> [pid <id>])|<integrated address>) [<description possibly with whitespaces>])|(delete <index>)]
//        balance [detail]
//        bc_height
//        blackball <output public key> | <filename> [add]
//        blackballed <output public key>
//        check_reserve_proof <address> <signature_file> [<message>]
//        check_spend_proof <txid> <signature_file> [<message>]
//        check_tx_key <txid> <txkey> <address>
//        check_tx_proof <txid> <address> <signature_file> [<message>]
//        donate [index=<N1>[,<N2>,...]] [<priority>] [<ring_size>] <amount> [<payment_id>]
//        encrypted_seed
//        export_key_images <file>
//        export_multisig_info <filename>
//        export_outputs <file>
//        export_raw_multisig_tx <filename>
//        fee
//        finalize_multisig <string> [<string>...]
//        get_description
//        get_reserve_proof (all|<amount>) [<message>]
//        get_spend_proof <txid> [<message>]
//        get_tx_key <txid>
//        get_tx_note <txid>
//        get_tx_proof <txid> <address> [<message>]
//        help [<command>]
//        import_key_images <file>
//        import_multisig_info <filename> [<filename>...]
//        import_outputs <file>
//        incoming_transfers [available|unavailable] [verbose] [index=<N1>[,<N2>[,...]]]
//        integrated_address [<payment_id> | <address>]
//        locked_transfer [index=<N1>[,<N2>,...]] [<priority>] [<ring_size>] <addr> <amount> <lockblocks> [<payment_id>]
//        make_multisig <threshold> <string1> [<string>...]
//        password
//        payment_id
//        payments <PID_1> [<PID_2> ... <PID_N>]
//        prepare_multisig
//        print_ring <key_image> | <txid>
//          refresh
//          rescan_bc
//          rescan_spent
//          save
//          save_bc
//          save_known_rings
//          save_watch_only
//          seed
//          set <option> [<value>]
//        set_daemon <host>[:<port>]
//        set_description [free text note]
//        set_log <level>|{+,-,}<categories>
//          set_ring <key_image> absolute|relative <index> [<index>...]
//        set_tx_note <txid> [free text note]
//        show_transfer <txid>
//          show_transfers [in|out|pending|failed|pool] [index=<N1>[,<N2>,...]] [<min_height> [<max_height>]]
//        sign <file>
//          sign_multisig <filename>
//          sign_transfer [export]
//          spendkey
//          start_mining [<number_of_threads>] [bg_mining] [ignore_battery]
//        status
//        stop_mining
//        submit_multisig <filename>
//          submit_transfer
//          sweep_all [index=<N1>[,<N2>,...]] [<priority>] [<ring_size>] <address> [<payment_id>]
//        sweep_below <amount_threshold> [index=<N1>[,<N2>,...]] [<priority>] [<ring_size>] <address> [<payment_id>]
//        sweep_single [<priority>] [<ring_size>] <key_image> <address> [<payment_id>]
//        sweep_unmixable
//        transfer [index=<N1>[,<N2>,...]] [<priority>] [<ring_size>] <address> <amount> [<payment_id>]
//        transfer_original [index=<N1>[,<N2>,...]] [<priority>] [<ring_size>] <address> <amount> [<payment_id>]
//        unblackball <output public key>
//        unspent_outputs [index=<N1>[,<N2>,...]] [<min_amount> [<max_amount>]]
//        verify <filename> <address> <signature>
//          version
//          viewkey
//          wallet_info

//./monero-wallet-cli -h
//    Wallet options:

// 交易所可能需要用到的命令 ：
//  --daemon-address arg                  Use daemon instance at <host>:<port>
//  --daemon-host arg                     Use daemon instance at host <arg> instead of localhost
//  --password arg                        Wallet password (escape/quote as needed)
//  --daemon-port arg (=0)                Use daemon instance at port <arg> instead of 18081
//  --daemon-login arg                    Specify username[:password] for daemon RPC client
//  --testnet                             For testnet. Daemon must also belaunched with --testnet flag
//  --wallet-file arg                     Use wallet <arg>
//  --generate-from-view-key arg          Generate incoming-only wallet from view key
//  --generate-from-spend-key arg         Generate deterministic wallet from spend key
//  --unsigned_tx_hex arg                 Specify unsigned_tx_hex
//  --spend_key arg                       Specify the Secret spend key to restore a wallet
//  --view_key arg                        Specify the secret view key to restore a wallet
//  --address arg                         Specify a wallet address in case of restoring the wallet from its secret view key
//  --do_not_interact                     Disable interaction mode.
//  --log-file arg                        Specify log file
//  --log-level arg                       0-4 or categories
//  --max-log-file-size arg (=104850000)  Specify maximum log file size [B]
//  --command arg


//  交易所可能暂时不需要用的命令 ：
//  --stagenet                            For stagenet. Daemon must also be launched with --stagenet flag
//  --restricted-rpc                      Restricts to view-only commands
//  --shared-ringdb-dir                   arg (=/Users/zhongqiu/.shared-ringdb, /Users/zhongqiu/.shared-ringdb/testnet if 'testnet') Set shared ring database path
//  --generate-new-wallet arg             Generate new wallet and save it to <arg>
//  --generate-from-device arg            Generate new wallet from device and save it to <arg>
//  --password-file arg                   Wallet password file
//  --generate-from-keys arg              Generate wallet from private keys
//  --generate-from-multisig-keys arg     Generate a master wallet from multisig wallet keys
//  --generate-from-json arg              Generate wallet from JSON format file
//  --mnemonic-language arg               Language for mnemonic
//  --restore-deterministic-wallet        Recover wallet using Electrum-style mnemonic seed
//  --restore-multisig-wallet             Recover multisig wallet using Electrum-style mnemonic seed
//  --non-deterministic                   Generate non-deterministic view and spend keys
//  --electrum-seed arg                   Specify Electrum seed for wallet recovery/creation
//  --trusted-daemon                      Enable commands which rely on a trusted daemon
//  --max-concurrency arg (=1)            Max number of threads to use for a parallel job
//  --config-file arg                     Config file
//  --allow-mismatched-daemon-version     Allow communicating with a daemon that uses a different RPC version
//  --restore-height arg (=0)             Restore from specific blockchain height
//  --do-not-relay                        The newly created transaction will not be relayed to the monero network
//  --create-address-file                 Create an address file for new wallets
//  --subaddress-lookahead arg            Set subaddress lookahead sizes to <major>:<minor>
//  --use-english-language-names          Display English language names


// -----------------------------------------------------------
// testnet 测试账户，里面有16个币
// -----------------------------------------------------------
//    address: 9u7McB2tQJQQbd2jrncdRW43m72Y5vbrQDt459FQB7SHbyCCnwuaBhSgvmdnQykqQecpguE8Csnms9nzoAK5QxzMGF75xqj
//    seed: agile enhanced pierce motherly adept obliged bubble himself lexicon azure border haunted tawny leech zebra turnip anchor annoyed long dude ruined talent owls petals talent
//    spendkey:
//    secret: 2ecf6967f6697795647ac61bc5a5323f0c2c5f835e68d00072db1d85c1855305
//    public: 3347032ee408358d13a5f77faf468512386a2a08ef78214d017db0aeca5252d1
//    viewkey:
//    secret: b580aee166efde144eb1d1a174aad918d7486ead4677825408b741cb7200aa07
//    public: 10a91384eeb279eeb553547bf1caf3d62abee81ab4089e3491f685bf0a391287

// -----------------------------------------------------------
// testnet 测试账户B，收币用
// -----------------------------------------------------------
//    Address: A1puKBLCKkdgB8D2LkGzQPYU8mYjVohwEgoAqcLKjQ2PCgAci6T5wMsVHhXekcPP7sEYqkR4KBGddAbErMTJhd737f9oAAD
//    seed: sack ledge olympics coal cake mixture topic cavernous ahead hairy dusted macro germs aquarium friendly beware unnoticed unfit fountain either foes fidget silk syringe macro
//
//    spendkey
//    secret: fd4f1b209f2c1d6657d856e27055fd4a03619140c2ff19ff08eee70fff43500d
//    public: e482901a5c4c12ea3599381a9cf278bc2243759c38d7d9edecc69ee04e22dc45
//    viewkey
//    secret: b0012956e57c6261bd8b91daa8c8c8b605aee403f8c1ef436728c3e060e8d40d
//    public: cd48c6fc2e0d72a91f5324836e8766510107a51708e278395638b2e5baa57e3b

