package com.okcoin.vault.jni.xmr;

import com.okcoin.vault.jni.common.Util;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Moneroj implements Runnable {

    public static final String DAEMON_URL = "127.0.0.1:28081";
//    public static final String DAEMON_URL = "192.168.149.229:58081";

    public static final boolean ENABLE_TESTNET = true;
    public static String HOT_WALLET_PATH;
    public static String ADDRESS;
    public static String SPEND_KEY;
    public static String VIEW_KEY;
    public static String TARGET_ADDRESS;

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
    public static final String XMR_MAX_TXINDEX            = "MaxTxIndex";


    public static boolean importKeyImages = true;
    public static boolean exportKeyImagesByOutputs = true;
    public static boolean getBalance_only = false;
    public static boolean export_outputs = false;
    public static boolean transfer = false;
    public static boolean sign = false;
    public static boolean submit = true;


    public static String begin_txindex = "";
    public static String end_txindex = "";
    public static String preferredTxid = "";

    public static String amount = "0.1";
    public static String logLevel = "4";
    public static String offsetTxid = "";
    public static String WALLET_NAME = "9sxx";
    public static String TRANSFER_FEE = "unimportant"; //one of: "default", "unimportant", "normal", "elevated", "priority"

    public static void main(String[] args) {

//        WALLET_NAME = "A1pu";
//        WALLET_NAME = "A2PZ";
        WALLET_NAME = "9sxx";
        WALLET_NAME = "9u7m";

        logLevel = "4";
        amount = "1";

//        offsetTxid = "efdb5179e9efa6f0a1cad848df99c574e2f5c49570890664f0150fe0821a8208"; // 171
//        offsetTxid = "27681366ae050457d866b79c3e6dc1b83bc7106010b5633a58f3e8c76445905a"; //170
//        offsetTxid = "17d0be4d322142fcab59662886e315bca2f3e269c733da97e956e9894a45e630"; //169

//        offsetTxid = "a3a68a1c370235f4e558d938586b025f3ed305cd35d6ebc3e566e39b13a701e6";
//        offsetTxid = "188861c9d1f4d59a5930d3e8925117812230f2dc1753fbc15044b9cb7863c1e7";
//        preferredTxid = "17d0be4d322142fcab59662886e315bca2f3e269c733da97e956e9894a45e630"; // 177
//                "b15c11d7bd55f14d9dcfdfa911119535755200217d1a32356f8222e93f6d868b," +
//                "fb94d5431fb4ac3df2b73a22b6a965594bc5265f480c3c801a4264d013af4b79," +
//                "0e33f0b4303ecbbd0b85a23e86e2cf19f7aaa40f8e35ef51689df780f8aa176c";

//        preferredTxid = "82b7205905684bda2ac987b3d48eab3e099a7bdac689ea6ba89785a2eabca948"
//        +",260a952e438bedd944cdfc11c404bd88fbc233f6ed85d349483a0b4ba660c422"
//                + ",9682b9e4faee18fbb53a86702dba57be61e6a8a752c44f465b80fb3f45afbcbd"
//        +",3329fb7a14907c0916b5a177f11658a366740a7593d970ecee095929b8fcfe38"
//        ;


        begin_txindex = "192";
        end_txindex = null;
//        getBalance_only = true;
        export_outputs = true;
//        exportKeyImagesByOutputs = false;
//        importKeyImages = false;

        transfer = true;
        sign = true;
        submit = true;

        try {
            WalletKey wkey = new WalletKey(WALLET_NAME);
            ADDRESS = wkey.getADDRESS();
            HOT_WALLET_PATH = wkey.getHOT_WALLET_PATH();
            TARGET_ADDRESS = wkey.getTARGET_ADDRESS();
            SPEND_KEY = wkey.getSPEND_KEY();
            VIEW_KEY = wkey.getVIEW_KEY();

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

            result = h.createWallet();
            if (result != null) {
                Util.dump("create hot wallet", result);
            }

            byte[][] balanceList = h.getBalance();
            Util.dumpResult("getBalance", balanceList, false);

            if (getBalance_only) {
                return;
            }

            if (export_outputs) {
                exportAndImport(c, h, offsetTxid, begin_txindex, end_txindex);
                balanceList = h.getBalance();
                Util.dumpResult("getBalance", balanceList, false);
            }

            if (!transfer) {
                return;
            }

            System.out.printf("========================================================================================\n");
            System.out.printf("============================== produceUnsignedTx =======================================\n");
            System.out.printf("========================================================================================\n");

            byte[][] unsignedTxRes = h.produceUnsignedTx(Moneroj.TARGET_ADDRESS, TRANSFER_FEE, amount, preferredTxid);
            Util.dumpResult("produceUnsignedTx", unsignedTxRes, true);

            byte[] err = Util.getResultBySchema(XMR_ERROR, unsignedTxRes);
            byte[] unsignedTx = null;
            if (err == null) {
                unsignedTx = Util.getResultBySchema(XMR_UNSIGNED_TX, unsignedTxRes);
                if (unsignedTx == null || unsignedTx.length == 0) {
                    System.out.printf("Failed to produce unsigned_monero_tx. size<%d>\n", unsignedTx.length);
                    return;
                }
                System.out.printf("unsigned_monero_tx returned. size<%d>\n", unsignedTx.length);
            } else {
                return;
            }


//            boolean signAsync = true;
//            signAsync = false;
//
//            if (signAsync) {
//
//                int concurrent = 3;
//                ExecutorService fixedThreadPool = Executors.newFixedThreadPool(concurrent);
//                for (int i = 0; i < concurrent; ++i) {
//                    fixedThreadPool.execute(new ActionSign(c, unsignedTx));
//                }
//
//                fixedThreadPool.shutdown();
//            }

            if (sign) {
                System.out.printf("============================== signTransaction =========================================\n");
                System.out.printf("========================================================================================\n");

                byte[][] signResult = c.signTransaction(unsignedTx);
                Util.dumpResult("signTransaction", signResult, true);

                byte[] signedTx = Util.getResultBySchema(XMR_SIGNED_TX, signResult);
                byte[] txidByte = Util.getResultBySchema(XMR_TX_ID, signResult);

                String txid = Util.byteArray2String(txidByte);

                System.out.printf("signTransaction returned. %s <%s>, %s size<%d>\n",
                        XMR_TX_ID,
                        txid,
                        XMR_SIGNED_TX,
                        signedTx.length);

                if (signedTx.length == 0) {
                    System.out.printf("Failed to sign tx. size<%d>\n", signedTx.length);
                    return;
                }

                if (submit) {
                    byte[][] submitResult = h.submitTransaction(signedTx);
                    System.out.printf("submitResult returned. size<%d>\n", submitResult.length);

                    Util.dumpResult("submitResult", submitResult, false);

                }
            }

        } catch (UnsupportedEncodingException e) {

            System.out.println(e.getMessage());

        } catch (Exception e) {
            System.out.println("// catch Exception");
            System.out.println(e.getMessage());
        }
    }

    void exportAndImport(Cold c, Hot h, String offsetTxid, String beginTxindex, String endTxindex) {

        try {

            System.out.printf("========================================================================================\n");
            System.out.printf("============================== hot wallet exports outputs ===============================\n");
            System.out.printf("========================================================================================\n");

            byte[][] outputsResult = h.exportOutputs(offsetTxid, beginTxindex, endTxindex);
            Util.dumpResult("exportOutputs", outputsResult, true);

            byte[] outputs = Util.getResultBySchema(XMR_TX_OUTPUTS, outputsResult);
            System.out.printf("outputs returned. size<%d>\n", outputsResult.length);


            byte[] keyImages = null;
            if (exportKeyImagesByOutputs) {
                System.out.printf("========================================================================================\n");
                System.out.printf("====================== cold wallet exports KeyImages By Outputs =========================\n");
                System.out.printf("========================================================================================\n");

                byte[][] keyImagesResult = c.exportKeyImagesByOutputs(outputs);

                Util.dumpResult("exportKeyImagesByOutputs", keyImagesResult, true);

                keyImages = Util.getResultBySchema(XMR_KEY_IMAGES, keyImagesResult);

                System.out.printf("keyImages returned. size<%d>\n", keyImagesResult.length);

                if (keyImages == null || keyImages.length == 0) {
                    System.out.printf("Failed to export keyImages. size<%d>\n", keyImagesResult.length);
                    return;
                }
            }

            if (importKeyImages) {
                System.out.printf("========================================================================================\n");
                System.out.printf("============================== importKeyImages =========================================\n");
                System.out.printf("========================================================================================\n");

                byte[][] importResult = h.importKeyImages(keyImages, offsetTxid, beginTxindex);
                Util.dumpResult("importKeyImages", importResult, false);
            }
        }catch (UnsupportedEncodingException e) {

            System.out.println(e.getMessage());

        } catch (Exception e) {
            System.out.println("// catch Exception");
            System.out.println(e.getMessage());
        }

    }
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

