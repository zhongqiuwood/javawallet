package com.okcoin.vault.jni.xmr;

import java.io.UnsupportedEncodingException;

class Moneroj {

    public static void main(String[] args)
    {

        try {
            String[] result;
            Cold c = new Cold();
            Hot h  = new Hot();

            boolean createWallet = false;
            createWallet = true;

            if (createWallet) {
                result = c.createWallet();
                Moneroj.dump("create cold wallet", result);

                result = h.createWallet();
                Moneroj.dump("create hot wallet", result);
            }

            System.out.printf("========================================================================================\n");
            System.out.printf("============================== hot wallet exports outputs ===============================\n");
            System.out.printf("========================================================================================\n");

            // 发生在offsetTxid之前的tx不会被导出
            String offsetTxid;
            offsetTxid = "dummy";
//            offsetTxid = "1d3586a1415186ee25b5cb37d587b0e339ce1cab73f51dc76dc134b5b1ee1137";
            byte[] outputs = h.exportOutputs(offsetTxid);
            System.out.printf("outputs returned. size<%d>\n", outputs.length);


            System.out.printf("========================================================================================\n");
            System.out.printf("====================== cold wallet exports KeyImages By Outputs =========================\n");
            System.out.printf("========================================================================================\n");

            byte[] keyImages = c.exportKeyImagesByOutputs(outputs);
            System.out.printf("keyImages returned. size<%d>\n", keyImages.length);

            if (keyImages.length == 0) {
                System.out.printf("Failed to export keyImages. size<%d>\n", keyImages.length);
                return;
            }

            System.out.printf("========================================================================================\n");
            System.out.printf("============================== importKeyImages =========================================\n");
            System.out.printf("========================================================================================\n");

            h.importKeyImages(keyImages, offsetTxid);

            System.out.printf("========================================================================================\n");
            System.out.printf("============================== produceUnsignedTx =======================================\n");
            System.out.printf("========================================================================================\n");

            byte[] unsignedTx = h.produceUnsignedTx(Moneroj.TARGET_ADDRESS, "priority", "0.0166");
            System.out.printf("unsigned_monero_tx returned. size<%d>\n", unsignedTx.length);

            if (unsignedTx.length == 0) {
                System.out.printf("Failed to produce unsigned_monero_tx. size<%d>\n", unsignedTx.length);
                return;
            }

            System.out.printf("========================================================================================\n");
            System.out.printf("============================== signTransaction =========================================\n");
            System.out.printf("========================================================================================\n");

            byte[][] signResult = c.signTransaction(unsignedTx);

            if (signResult.length != 4) {
                System.out.printf("Failed to sign transaction. signResult size<%d>\n", signResult.length);
                return;
            }

            String txid = byteArray2String(signResult[1]);
            byte[] signedTx = signResult[3];

            System.out.printf("signTransaction returned. %s <%s>, %s size<%d>\n",
                    byteArray2String(signResult[0]),
                    txid,
                    byteArray2String(signResult[2]),
                    signedTx.length);

            if (signedTx.length == 0) {
                System.out.printf("Failed to sign tx. size<%d>\n", signedTx.length);
                return;
            }

            boolean submit = false;
            submit = true;

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

            System.out.println(e.getMessage());
        }
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
        System.load("/Users/oak/go/src/github.com/monero-project/monero/build/debug/src/wallet/libwallet.dylib");
    }

    /**
     * 可执行 monero cli命令行任何操作
     * 包括创建viewonly钱包和viewandsend钱包
     *
     * @param monero-wallet-cli 命令行参数，详见 monero-wallet-cli -h
     * @return 返回钱包的地址和viewkey
     * @keys 保留, 暂时不用
     * @values 保留, 暂时不用
     */
    public native String[] execute(Object[] params, String[] keys, String[] values);


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
    public native byte[][] transcation(Object[] params, String key, byte[] value, String[] keys, String[] values);

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


    public static final String XMR_VIEW_KEY    = "SecretViewKey";
    public static final String XMR_ADDRESS     = "ColdWalletAddress";
    public static final String XMR_UNSIGNED_TX = "UnsignedTx";
    public static final String XMR_SIGNED_TX   = "SignedTx";
    public static final String XMR_KEY_IMAGES  = "KeyImages";
    public static final String XMR_TX_OUTPUTS  = "TxOutputs";
    public static final String XMR_TX_ID       = "TxId";
    public static final String XMR_ERROR       = "Error";


    public static final String HOT_WALLET =
            "/Users/zhongqiu/go/src/github.com/monero-project/monero/src/javawallet/wallet_hot/hot";

    public static final String COLD_WALLET =
            "/Users/zhongqiu/go/src/github.com/monero-project/monero/src/javawallet/wallet_cold/cold";

    public static final String SPEND_KEY =
            "2ecf6967f6697795647ac61bc5a5323f0c2c5f835e68d00072db1d85c1855305";

    public static final String VIEW_KEY =
            "b580aee166efde144eb1d1a174aad918d7486ead4677825408b741cb7200aa07";

    public static final String ADDRESS =
            "9u7McB2tQJQQbd2jrncdRW43m72Y5vbrQDt459FQB7SHbyCCnwuaBhSgvmdnQykqQecpguE8Csnms9nzoAK5QxzMGF75xqj";

    public static final String TARGET_ADDRESS =
            "A1puKBLCKkdgB8D2LkGzQPYU8mYjVohwEgoAqcLKjQ2PCgAci6T5wMsVHhXekcPP7sEYqkR4KBGddAbErMTJhd737f9oAAD";
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
