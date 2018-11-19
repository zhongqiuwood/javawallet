package com.okcoin.vault.jni.xmr;

public class WalletKey {

    static String HOT_WALLET_PATH;
    static String COLD_WALLET_PATH;

    String ADDRESS;
    String SPEND_KEY;
    String VIEW_KEY;
    String TARGET_ADDRESS;

//    public static String XMR_VERSION = "013";
    public static String XMR_VERSION = "";

    public static String XMR_LOG = "/Users/oak/go/src/github.com/okblockchainlab/javawallet/wallet_data/xmr/testnet/_xmr"
            + XMR_VERSION + ".json";

    public static final String WALLET_TOP =
            "/Users/oak/go/src/github.com/okblockchainlab/javawallet/wallet_data/xmr/testnet";

    public static String getHOT_WALLET_PATH() {
        return HOT_WALLET_PATH;
    }

    public static String getCOLD_WALLET_PATH() {
        return COLD_WALLET_PATH;
    }
    public String getADDRESS() {
        return ADDRESS;
    }
    public String getSPEND_KEY() {
        return SPEND_KEY;
    }
    public String getVIEW_KEY() {
        return VIEW_KEY;
    }
    public String getTARGET_ADDRESS() {
        return TARGET_ADDRESS;
    }

    public WalletKey(String name) {

        String version = "";
        if (XMR_VERSION.length() > 0) {
            version = XMR_VERSION + "_";
        }

        HOT_WALLET_PATH  = WALLET_TOP + "/test_" + version + name;
        COLD_WALLET_PATH = WALLET_TOP + "/_test_cold_" + version + name;

        if (name.compareToIgnoreCase("A2PZ") == 0) {

            ADDRESS        = "A2PZyjDZT1JjnmdSXAsPb5hv9ne7Tty9b2qaLrw7s3zSMsxAsd8fcAoVWt2xFapSX4UYDce7gDeHjXRyKb5t45TcGhYmY9y";
            VIEW_KEY       = "6e6749ca79e1dedcf514b7fe52035b8cb04c693afe456d36b91f56c08842260a";
            SPEND_KEY      = "d7a66a48134551b44739f372ae42c2ce9675cb86286e5c69d07e47d90ff3b308";
            TARGET_ADDRESS = "9u7McB2tQJQQbd2jrncdRW43m72Y5vbrQDt459FQB7SHbyCCnwuaBhSgvmdnQykqQecpguE8Csnms9nzoAK5QxzMGF75xqj";

        } else if (name.compareToIgnoreCase("9sXX") == 0) {

            ADDRESS        = "9sXXpRtxYgh5yMYexKccr4dx35JM8rc1e8M7UDBfFCQmSgau1mQxTLk3j8MStt4CCnd8C99BGTw9uQ4DqhwtKq8r32pBpxe";
            SPEND_KEY      = "cac0eb97cddd955c7ef0a992c44077bbb875221845889afad1d4e761e045740e";
            VIEW_KEY       = "512330f6544e50a3ad233f3b23a44eea2f7a78d16d20c2d4ed3d74eb07627806";
            TARGET_ADDRESS = "A2PZyjDZT1JjnmdSXAsPb5hv9ne7Tty9b2qaLrw7s3zSMsxAsd8fcAoVWt2xFapSX4UYDce7gDeHjXRyKb5t45TcGhYmY9y";

        } else if (name.compareToIgnoreCase("A1pu") == 0) {

            ADDRESS        = "A1puKBLCKkdgB8D2LkGzQPYU8mYjVohwEgoAqcLKjQ2PCgAci6T5wMsVHhXekcPP7sEYqkR4KBGddAbErMTJhd737f9oAAD";
            SPEND_KEY      = "fd4f1b209f2c1d6657d856e27055fd4a03619140c2ff19ff08eee70fff43500d";
            VIEW_KEY       = "b0012956e57c6261bd8b91daa8c8c8b605aee403f8c1ef436728c3e060e8d40d";
            TARGET_ADDRESS = "A2PZyjDZT1JjnmdSXAsPb5hv9ne7Tty9b2qaLrw7s3zSMsxAsd8fcAoVWt2xFapSX4UYDce7gDeHjXRyKb5t45TcGhYmY9y";

        } else if (name.compareToIgnoreCase("9u7M") == 0) {

            ADDRESS        = "9u7McB2tQJQQbd2jrncdRW43m72Y5vbrQDt459FQB7SHbyCCnwuaBhSgvmdnQykqQecpguE8Csnms9nzoAK5QxzMGF75xqj";
            SPEND_KEY      = "2ecf6967f6697795647ac61bc5a5323f0c2c5f835e68d00072db1d85c1855305";
            VIEW_KEY       = "b580aee166efde144eb1d1a174aad918d7486ead4677825408b741cb7200aa07";
            TARGET_ADDRESS = "A2PZyjDZT1JjnmdSXAsPb5hv9ne7Tty9b2qaLrw7s3zSMsxAsd8fcAoVWt2xFapSX4UYDce7gDeHjXRyKb5t45TcGhYmY9y";

        } else if (name.compareToIgnoreCase("y") == 0) {

        }
    }
}

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


////////////////////////////////////////////
// main net
//    public static final String SPEND_KEY =
//            "f11d529fcf40303ad4f2791610f4f510d7613e33dbfb89cc59a4fea8173a670c";
//    public static final String VIEW_KEY =
//            "232b946a299f307e1383a616cd3a721ab3ab159fc75b11253a75ad99a1617602";
//    public static final String ADDRESS =
//            "4AVJbZPdQFZ3umfGYATvt26mnTyGaCW8zP9MyJPBM77fbEPWSYj3wRMeztLVnBsNTHHeiqTnAxDRpKoF44ENxp5RHd31xqM";
//    public static final String TARGET_ADDRESS =
//            "48PjH3ksv1fiXniKvKvyH5UtFs5WhfS2Vf7U3TwzdRJtCc7HJWvCQe56dRahyhQyTAViXZ8Nzk4gQg6o4BJBMUoxNy8y8g7";
