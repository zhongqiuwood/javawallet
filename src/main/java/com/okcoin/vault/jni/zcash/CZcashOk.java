package com.okcoin.vault.jni.zcash;

public class CZcashOk {

    static {
    	   System.load("/home/zcash/src/zcash-cli-ok.so");
    }

    static public native String[] execute(String networkType, String command);

    public static void dump(String context, String[] result) {

        System.out.printf("//-------------------------------------------------------\n");
        System.out.println("// Dump result return from JNI: " + context);
        for (int i = 0; i < result.length; ++i) {
            System.out.println("// " + result[i]);
        }
        System.out.printf("//end -------------------------------------------------------\n");
        
    }

    public static void main_mul(String[] args) {
    	
    	String networkType = "main"; // or testnet
    	try {
            String createrawtransaction2 ="createmultisig 2 [\"03d29f0b96f332e50d6014cb91c334214ecb8caf2881a97e7d944bdf4e5fd6a39e\",\"03f97e079ccae21e1ee65d5ee64e5c27d7d6ce9a867cec75e9736ad5f258329e02\"]";


            String[] createResults = CZcashOk.execute(networkType, createrawtransaction2);
            CZcashOk.dump("createmultisig", createResults);
            
    	 } catch (Exception e) {
             System.out.println(e.getMessage());
         }
    	
    }
    public static void main_onlineNet(String[] args)
    {
        try {
            String networkType = "main"; // or testnet
            
            String createrawtransaction2 ="createrawtransaction "+
            	      "[{\"txid\":\"b4cc287e58f87cdae59417329f710f3ecd75a4ee1d2872b7248f50977c8493f3\"," +
            	      "\"vout\":1,\"scriptPubKey\":\"a914b10c9df5f7edf436c697f02f1efdba4cf399615187\"," +
            	      "\"redeemScript\":\"512103debedc17b3df2badbcdd86d5feb4562b86fe182e5998abd8bcd4f122c6155b1b21027e940bb73ab8732bfdf7f9216ecefca5b94d6df834e77e108f68e66f126044c052ae\"}] " +
            	      "{\"t3ahmeUm2LWXPUJPx9QMheGtqTEfdDdgr7p\":11}";


            String[] createResults = CZcashOk.execute(networkType, createrawtransaction2);
            CZcashOk.dump("createrawtransaction", createResults);

            String signrawtransaction = "signrawtransaction " +
                    "030000807082c40301f393847c97508f24b772281deea475cd3e0f719f321794e5da7cf8587e28ccb40100000000ffffffff0100ab90410000000017a914b10c9df5f7edf436c697f02f1efdba4cf399615187000000005550050000 " +
                    "[{\"txid\":\"b4cc287e58f87cdae59417329f710f3ecd75a4ee1d2872b7248f50977c8493f3\",\"vout\":1,\"amount\":0.01,\"scriptPubKey\":\"a914b10c9df5f7edf436c697f02f1efdba4cf399615187\"" +
                    ",\"redeemScript\":\"512103debedc17b3df2badbcdd86d5feb4562b86fe182e5998abd8bcd4f122c6155b1b21027e940bb73ab8732bfdf7f9216ecefca5b94d6df834e77e108f68e66f126044c052ae\"}]" +
                    " [\"KzsXybp9jX64P5ekX1KUxRQ79Jht9uzW7LorgwE65i5rWACL6LQe\",\"Kyhdf5LuKTRx4ge69ybABsiUAWjVRK4XGxAKk2FQLp2HjGMy87Z4\"]";
            String[] signResults = CZcashOk.execute(networkType, signrawtransaction);
            CZcashOk.dump("signrawtransaction", signResults);
         } catch (Exception e) {
            System.out.println(e.getMessage());
            }
    }

    public static void main(String[] args)
    {
        try {
            String networkType = "testnet"; // or testnet
            
            //address
            //String getaddressbyprivatekey ="ok_getAddress ST15NyNF44nKEXYZMC9B9jLQFvAw5bJid5Uh32RGqSBD8Bdkwq6q";
            //String[] address = CZcashOk.execute(networkType, getaddressbyprivatekey);
            //CZcashOk.dump("getaddressbyprivatekey", address);
            
            //1, t_addr-->t_addr
            String createrawtransaction ="z_createrawtransaction_ok "+
          	      "[{\"txid\":\"94eb546f0b4a71e5c2bfe99fd74820011c518cbe97baa0f98f977186fb2c3513\"," +
          	      "\"vout\":0}] " +
          	      "[] [{\"address\":\"tm9jF2FiQFTbKbC5pwVmt7Mf6Du9AdhD3Xn\",\"amount\":1},{\"address\":\"tmXnHFEVFRcuiphN9t6nJ5fyZSrJxYZ3vme\",\"amount\":1.999999}]";

            String[] createResults = CZcashOk.execute(networkType, createrawtransaction);
            CZcashOk.dump("z_createrawtransaction_ok", createResults);
            //030000807082c40301f393847c97508f24b772281deea475cd3e0f719f321794e5da7cf8587e28ccb40100000000ffffffff0100ab90410000000017a914b10c9df5f7edf436c697f02f1efdba4cf39961518700000000fc4f050000
                 
            String signrawtransaction = "z_signrawtransaction_ok " + createResults[1] +  " "+
                    //"030000807082c40301f393847c97508f24b772281deea475cd3e0f719f321794e5da7cf8587e28ccb40100000000ffffffff0100e1f5050000000017a914b10c9df5f7edf436c697f02f1efdba4cf39961518700000000fd4f0500000000191ba85b " +
                    "[{\"txid\":\"94eb546f0b4a71e5c2bfe99fd74820011c518cbe97baa0f98f977186fb2c3513\",\"vout\":0,\"amount\":3,\"scriptPubKey\":\"76a9141a80218c2ecb5c9cd7c89b662ad16a87648e1dfe88ac\"" +
                                        ",\"redeemScript\":\"512103debedc17b3df2badbcdd86d5feb4562b86fe182e5998abd8bcd4f122c6155b1b21027e940bb73ab8732bfdf7f9216ecefca5b94d6df834e77e108f68e66f126044c052ae\"}]" +
                    " [\"cP1WkVoZvao1oXeLThGDB1L1fb9FKipVT7BwA2s8bMN4Fcat3Sm9\"]";
            String[] signResults = CZcashOk.execute(networkType, signrawtransaction);
            CZcashOk.dump("z_signrawtransaction_ok", signResults);
            
            String sendRawaction = "sendrawtransaction " + signResults[1]
     		   //"0400008085202f89000180c3c901000000001976a91484c9bb6eb654d9495852caa8dad3c8f8d16349d488ac0000000060f80300000000000000000000000100000000000000001027000000000000f08c67d7b4d345ef576860f266cbbda51ab47c226871758b1b3a8f9bc98e42b70eacb7811741ec023bed50013c0d4c650eeda0c55eae7a24777f1004f90c5548b24448f6ccb792fc17a10acd2cc4283d54cbf1faa4e2bd6e78970a79e7edda072cdf07f3c9c71fe961b037158bf399c2ae34fc8b9d9e0f442739880569201ccd5cbe85b9550aa2d55691b5f1c92a21962b4d84a84e109053b267d96467d12f54a90876361981b877bef178962f1b9b6b71a5fe0435a58fe62f78cabc5a0a887330d0d69f8543580e22fda874d9d5ffa80522d924a26f15f8c54aedb5797f07bcc1daae8256f05df6650c1dee467d9492d83ea5497ab3de7d873b04286d414c94d10dfbbb7c4d03f22d6824b912737747e43790c224f1094cef9c78f35f096498ae3e85f68800a32414ee75725765170db217a8f09584951d0adce151badfe5c207f013b017d32c7611c110fc8ac118e9ae3cba708463eed021102ea948d2419862a5b8a8b248d5583ac6dcea6aabf35d1a75ce1d432b679ceaa34d0e29d37af602abe06e5e6a721fd0b1edd303bae8c62d1a04544a4f0ed862e7562dc166d8da490675d420f21f420c5dac5f7dcb185b8b062bd0033b33088ccc6ebe0904025e4941edc0733d13851e6c81e589f1d313dabd47dbfa49e127e58a93c8cd1a422495c27b2f106e4037aa52b6f712e0c7fe72fb871bf67c50c4facd5d8eba62b07d30cadb2e6e2c0c572658b8e12ef555910fc38757b4c4021e816402ee7ed89829c420e4ad36fe35eded710b4c0bdcb420d7ab8a36921c92bb2965c51636c69f477db3bda05c69b877058887318c2828e979bca74c2bb5cb299e6ab2e7b5738036a85f2f19bc5c33df34710db828fb9885fb36b7c6bfca9ddaa14ab66abb8304d5e40e9b38e4a8573bd9d6965bf605638cc9a54e21fc0053ba6c9032d03b5c0d6c23128b215626423f6b469b88b898753354f8061939fd3260d614515899979648f744463b75496a1d9b775d480b2e9e9663175da1d42ac063d3033746c94e5d0fa472b859c8ec039683fc4749a7522ed4cc11cdf4f564829d6967d9f290334dae2039043cf2154ecde0c27fc20f20e519d939d7b76ea201612cbef3baab9706a692f3eb30f1989ad2de2f0b174a4567d2c7dcb5c9bea28a49a7b1c573ee2fb4f10aa99443fd122a34f93a16265fa8ee800de2bfb362454c2dc444543473a3e5377d8a02f6d5f4d97376a44ffc336143e94215dddaac988a55a8d4b3c8fc0b11469201e5bd25293237d0cfa099986103be81fe5eb8c8d1a703035f479cd6a63a27e08f9d925d82584883f76b467a724ea106b04da3c3e040713e4944267cf2b6996d85c0a80783df1c8ba73e89ad810ae47501e0da6fecffa37db31ed508720182c2dda56a5b4461339e8f4a011c8aae3c287c176588089d01885e049902b894cfb2b8ce3f9376ba4fc20f6eb363faff2f4a5c5ba447b3b7be19e6c04c51a20c766f15a9c5ef2a00ab98fc73c9a7377389ab6a73ba69c00dccf79ecb53b1a5cec367b6cd6b15091521687918d6167052e06755bb55599b3561af74a2bdf64f8881b68d7e720358df76bb764e60ab1731dc68651948dae11423c9495edb6e24e8da763014420a7eb01e6b9fda1c0688f9a9551e2bfaa6451c538a47ef7bf78d9b494503b2da2c4c0e1a6e3d510edfc3880a503db59ba96e38c8cc60c6c8e98bbde884154e32d3882db2a602a3c83949bb7c21bb5a6a58ceabc49ea1b50505d026f798b69bd6ec1e87ef92c0d50dad0896b4ff0f62eb8cfda13b5859209cc3fcad075f7fa7f5c2d8485df0f4a1851f505602b1e5b1f11500141868a3b695e7ecc8e06fc92c0aaa89937263158336cb864fdae50f95ee6adee6059129c1d52160ea5d00e3813d2395e9c6d9f3f1428592be8e08a08a98920ac786083b49e070de635185feca173ffebbcccade301895d6361864ebfe786e00d66b77113f8b5aaa376cee10a224d25bb75bc41e01c4444814e9e9e8402537e44ea289fe77dff34677a01be4809bdfcd6ed984a2b5e89dc31834331a61833c0bf8c3296a9a61efaa49190841f71199f57853e7c1f1616e476f42e515d03858c250909e1cec2f5913914ee5895f5ff6af9264a69c53777217f1f5eb36c899dc55545d7dab357bff66a7fd78cc688679bc18266f33eed1327a7574ab04be30824aa845000d33ca3a23c213cddc623260cfffd62cb7c679339a1f7df491269e9bf3481d08a2d99cdc1d55609ad8135033de3e34dd8b21bf639726419bd9efe67189c708c41bf4058546dcf69884863ba4f76b20e88ddb6ccadec70f939de9ef8503b4d143302b54c1f6897c2d33c74d4cfa27f60f6900947312a1435f63d699318f282e77a87a6075faf36533614ffa1403252d2f117dc6e71404b8700365b7f609fbb58b58a7262edcf2e4f4d67e7cddbd47986e2643aa8838519aff01b17f54278526827736ee1f6214bacbe066109148a159e9c903fce183e2ede90e"
     		      ;
     		   //String[] sendResults = CZcashOk.execute(networkType, sendRawaction);
     		   //CZcashOk.dump("sendrawtransaction", signResults);

            //2 , t_addr-->t_addr
            //          -->z_addr
            
           /*  String createrawtransaction ="z_createrawtransaction_ok "+
		  "[{\"txid\":\"7c4723695d20968f31c354e40f9bfa4aa94a4adfb6862ce3eff16165f6b603bd\"," +
		  "\"vout\":0,\"scriptPubKey\":\"76a91414b4953536f201642ebfb592c728a34f122b477188ac\"," +
		  "\"redeemScript\":\"512103debedc17b3df2badbcdd86d5feb4562b86fe182e5998abd8bcd4f122c6155b1b21027e940bb73ab8732bfdf7f9216ecefca5b94d6df834e77e108f68e66f126044c052ae\"}] " +
		  "[] [{\"address\":\"ztcfDz4EezXeV64euJJR9wzYa7fLTz4zKCmSvDMKJzWPB2NPvRM985hW6tZcsiWqoLEkf7tMGG8gNj4FAyDgdT9Kh247ibM\",\"amount\":1.5},{\"address\":\"tmVAvYUTW29Zqgw92gw12VJkyMJd5s6pmej\",\"amount\":0.499}]";
		
		  String[] createResults = CZcashOk.execute(networkType, createrawtransaction);
		  CZcashOk.dump("z_createrawtransaction_ok", createResults);
		  
		 
		  String signrawtransaction = "z_signrawtransaction_ok " + createResults[1] + " " +
		 // "030000807082c40301f393847c97508f24b772281deea475cd3e0f719f321794e5da7cf8587e28ccb40100000000ffffffff0100e1f5050000000017a914b10c9df5f7edf436c697f02f1efdba4cf399615187000000005b5005000000015f7a6341417353554771446f696b335034634768365467767444546744696f5047353478705166436450557a53676d5234595451624353334b5963694a6b4644584152444a614e72784d646b6e72614a6b446a7773376f50525947387a64423900e1f5050000000000191ba85b " +
		  "[{\"txid\":\"7c4723695d20968f31c354e40f9bfa4aa94a4adfb6862ce3eff16165f6b603bd\",\"vout\":1,\"amount\":0.01,\"scriptPubKey\":\"a914b10c9df5f7edf436c697f02f1efdba4cf399615187\"" +
                                                 ",\"redeemScript\":\"512103debedc17b3df2badbcdd86d5feb4562b86fe182e5998abd8bcd4f122c6155b1b21027e940bb73ab8732bfdf7f9216ecefca5b94d6df834e77e108f68e66f126044c052ae\"}]" +
		  " [\"cUbJc9ncSEuAJZsN2eX8SaS1XUNv2Fkc9tjp3WbHnPUBpsFScB1U\"]";
		  String[] signResults = CZcashOk.execute(networkType, signrawtransaction);
		  CZcashOk.dump("z_signrawtransaction_ok", signResults);
		  
		  String sendRawaction = "sendrawtransaction " + signResults[1]
		   //"0400008085202f89000180c3c901000000001976a91484c9bb6eb654d9495852caa8dad3c8f8d16349d488ac0000000060f80300000000000000000000000100000000000000001027000000000000f08c67d7b4d345ef576860f266cbbda51ab47c226871758b1b3a8f9bc98e42b70eacb7811741ec023bed50013c0d4c650eeda0c55eae7a24777f1004f90c5548b24448f6ccb792fc17a10acd2cc4283d54cbf1faa4e2bd6e78970a79e7edda072cdf07f3c9c71fe961b037158bf399c2ae34fc8b9d9e0f442739880569201ccd5cbe85b9550aa2d55691b5f1c92a21962b4d84a84e109053b267d96467d12f54a90876361981b877bef178962f1b9b6b71a5fe0435a58fe62f78cabc5a0a887330d0d69f8543580e22fda874d9d5ffa80522d924a26f15f8c54aedb5797f07bcc1daae8256f05df6650c1dee467d9492d83ea5497ab3de7d873b04286d414c94d10dfbbb7c4d03f22d6824b912737747e43790c224f1094cef9c78f35f096498ae3e85f68800a32414ee75725765170db217a8f09584951d0adce151badfe5c207f013b017d32c7611c110fc8ac118e9ae3cba708463eed021102ea948d2419862a5b8a8b248d5583ac6dcea6aabf35d1a75ce1d432b679ceaa34d0e29d37af602abe06e5e6a721fd0b1edd303bae8c62d1a04544a4f0ed862e7562dc166d8da490675d420f21f420c5dac5f7dcb185b8b062bd0033b33088ccc6ebe0904025e4941edc0733d13851e6c81e589f1d313dabd47dbfa49e127e58a93c8cd1a422495c27b2f106e4037aa52b6f712e0c7fe72fb871bf67c50c4facd5d8eba62b07d30cadb2e6e2c0c572658b8e12ef555910fc38757b4c4021e816402ee7ed89829c420e4ad36fe35eded710b4c0bdcb420d7ab8a36921c92bb2965c51636c69f477db3bda05c69b877058887318c2828e979bca74c2bb5cb299e6ab2e7b5738036a85f2f19bc5c33df34710db828fb9885fb36b7c6bfca9ddaa14ab66abb8304d5e40e9b38e4a8573bd9d6965bf605638cc9a54e21fc0053ba6c9032d03b5c0d6c23128b215626423f6b469b88b898753354f8061939fd3260d614515899979648f744463b75496a1d9b775d480b2e9e9663175da1d42ac063d3033746c94e5d0fa472b859c8ec039683fc4749a7522ed4cc11cdf4f564829d6967d9f290334dae2039043cf2154ecde0c27fc20f20e519d939d7b76ea201612cbef3baab9706a692f3eb30f1989ad2de2f0b174a4567d2c7dcb5c9bea28a49a7b1c573ee2fb4f10aa99443fd122a34f93a16265fa8ee800de2bfb362454c2dc444543473a3e5377d8a02f6d5f4d97376a44ffc336143e94215dddaac988a55a8d4b3c8fc0b11469201e5bd25293237d0cfa099986103be81fe5eb8c8d1a703035f479cd6a63a27e08f9d925d82584883f76b467a724ea106b04da3c3e040713e4944267cf2b6996d85c0a80783df1c8ba73e89ad810ae47501e0da6fecffa37db31ed508720182c2dda56a5b4461339e8f4a011c8aae3c287c176588089d01885e049902b894cfb2b8ce3f9376ba4fc20f6eb363faff2f4a5c5ba447b3b7be19e6c04c51a20c766f15a9c5ef2a00ab98fc73c9a7377389ab6a73ba69c00dccf79ecb53b1a5cec367b6cd6b15091521687918d6167052e06755bb55599b3561af74a2bdf64f8881b68d7e720358df76bb764e60ab1731dc68651948dae11423c9495edb6e24e8da763014420a7eb01e6b9fda1c0688f9a9551e2bfaa6451c538a47ef7bf78d9b494503b2da2c4c0e1a6e3d510edfc3880a503db59ba96e38c8cc60c6c8e98bbde884154e32d3882db2a602a3c83949bb7c21bb5a6a58ceabc49ea1b50505d026f798b69bd6ec1e87ef92c0d50dad0896b4ff0f62eb8cfda13b5859209cc3fcad075f7fa7f5c2d8485df0f4a1851f505602b1e5b1f11500141868a3b695e7ecc8e06fc92c0aaa89937263158336cb864fdae50f95ee6adee6059129c1d52160ea5d00e3813d2395e9c6d9f3f1428592be8e08a08a98920ac786083b49e070de635185feca173ffebbcccade301895d6361864ebfe786e00d66b77113f8b5aaa376cee10a224d25bb75bc41e01c4444814e9e9e8402537e44ea289fe77dff34677a01be4809bdfcd6ed984a2b5e89dc31834331a61833c0bf8c3296a9a61efaa49190841f71199f57853e7c1f1616e476f42e515d03858c250909e1cec2f5913914ee5895f5ff6af9264a69c53777217f1f5eb36c899dc55545d7dab357bff66a7fd78cc688679bc18266f33eed1327a7574ab04be30824aa845000d33ca3a23c213cddc623260cfffd62cb7c679339a1f7df491269e9bf3481d08a2d99cdc1d55609ad8135033de3e34dd8b21bf639726419bd9efe67189c708c41bf4058546dcf69884863ba4f76b20e88ddb6ccadec70f939de9ef8503b4d143302b54c1f6897c2d33c74d4cfa27f60f6900947312a1435f63d699318f282e77a87a6075faf36533614ffa1403252d2f117dc6e71404b8700365b7f609fbb58b58a7262edcf2e4f4d67e7cddbd47986e2643aa8838519aff01b17f54278526827736ee1f6214bacbe066109148a159e9c903fce183e2ede90e"
		      ;
		   //String[] sendResults = CZcashOk.execute(networkType, sendRawaction);
		   //CZcashOk.dump("sendrawtransaction", signResults);
            */
           
            //3 , z_addr-->t_addr
            //          -->z_addr
            /*
            String createrawtransaction ="z_createrawtransaction_ok "+
    	      "[] [{\"txid\":\"e8ec15cf68280d12df258f4bdd13c0f0f1de52f4021c2469d95121c861976ed1\"," +
    	      "\"jsindex\":0}] " +
    	      "[{\"address\":\"tmMpUC68e27EUuCUkFoR4AvF4zeZusi4jUk\",\"amount\":0.1},{\"address\":\"ztftXj8Ga7BdjTfLwNNYz3443vhtm6uGnaAegwvQGziBuiFtbCCd58zthsFk6Z3Jfc3FCxDknJZN3PtvpfEwzHVJ8ALHZxw\",\"amount\":0.1}]";

	      String[] createResults = CZcashOk.execute(networkType, createrawtransaction);
	      CZcashOk.dump("z_createrawtransaction_ok", createResults);
	      
	      
	      String signrawtransaction = "z_signrawtransaction_ok " + createResults[1] +
	             // "0400008085202f89000180c3c901000000001976a91484c9bb6eb654d9495852caa8dad3c8f8d16349d488ac0000000060f80300000000000000000000000001d16e9761c82151d969241c02f452def1f0c013dd4b8f25df120d2868cf15ece800000000000000000182048ef7e2e9a32727d1b9a7466f9af9c7b2ab62e02e20e6148f15e769aaeaddf9a9bdd099ac2de23eb4e38cc25a44e9d62327716e38a57b0408be00af9c863548d4a320b0d8167ef452ec70aa23d29bfc5c9c3f9b7433a23f1d5f6f4788618d00e1f5050000000001b8d1c93c6c6356547a25c160a7ff08eb320a28379731d0ac03b5022fe37d0b7f016edde927d3bb04021b065ef35fe5738977660512b782b6c1a23325ee3777453410000001252e0ab70d4e49b90c43cda43a7ae3f2facfa3983cbb69124efece6682772321000001ea0642400be668e20ac048100300c85d344287b97afc98fa91796b14ac200152000190060192b6c8a4993f3eb880c76c2ee13786d405d88513ba0ca8a36754d9b8ec01a6548cc34bb2bb8c2cb1a633260402026ed76d0c5eb7946c2fcda2ab629f87dd0000019c45ed879bb07b3258ebb5643892c22d707f469af2137ea591fe0eaa04636190000000014a16ee55bb6cab4005abf9065ea418c4e8f8e5d76e7d2548be24e1c8926c2f8006df6a6688f09715e9f584137af63a92170d68ff95265f233633609a58acafe95c4fde5cd8e67ea268795a6c06d39cb0196e9cd257dfcdb426df55701904c1fa5fe6aae9728babaff5f04a75f0b5e91d054148814a131ae854ec235ca45560ee80f96d7472090e72c878525200aad665c0722d3f5f753f07757f6ccaf1b58bebc4ae5fd666bf27937bc2548df7759cf83c0edf83599eaef88b98c469bfb000e6f556d94ea2decc5507c37609185c2f29ddec8165e2c717e83319de227833f8409f01017ba8b61c337b3faaa550919407ff9bfa8eb503e81b8ccc446a7e595713e306bd0151c909f00b9b400a59fbe328e1e9ff698f19e5d8f5006b225172405409f9f0150801edc0991dc6e8937d97f3a1acdd3df474b89815643709d7fe2c08beb2231f1b8c01d575b51ecae331c5265995b140da9d59b92d105d50bc05f900c2266c28faa21d014063d13afa9198b01e7e6176bd2223b02455e0f8a2c1c39deedde0b1eaf287b901b824ccf9b3ba2c767b2b4ac1b335549c1783cbb903fe307e813927269376fbc50001f61bf3e3bc8017b836fde269b9e8bade05db92e5d36ba4422550150f9df84f860001e54d2ebc6f51dea94f6e9e123a8430053cb8ef0d0d68cdc33edd4c85cfc9c0a8f08c67d7b4d345ef576860f266cbbda51ab47c226871758b1b3a8f9bc98e42b7015f7a746674586a3847613742646a54664c774e4e597a333434337668746d3675476e61416567777651477a6942756946746243436435387a746873466b365a334a666333464378446b6e4a5a4e33507476706645777a48564a38414c485a787780c3c9010000000000bb09b876" +
	            //"0400008085202f89000180c3c901000000001976a91484c9bb6eb654d9495852caa8dad3c8f8d16349d488ac0000000060f80300000000000000000000000001d16e9761c82151d969241c02f452def1f0c013dd4b8f25df120d2868cf15ece800000000000000000182048ef7e2e9a32727d1b9a7466f9af9c7b2ab62e02e20e6148f15e769aaeaddf9a9bdd099ac2de23eb4e38cc25a44e9d62327716e38a57b0408be00af9c863548d4a320b0d8167ef452ec70aa23d29bfc5c9c3f9b7433a23f1d5f6f4788618d00e1f5050000000001b8d1c93c6c6356547a25c160a7ff08eb320a28379731d0ac03b5022fe37d0b7f016edde927d3bb04021b065ef35fe5738977660512b782b6c1a23325ee3777453410000001252e0ab70d4e49b90c43cda43a7ae3f2facfa3983cbb69124efece6682772321000001ea0642400be668e20ac048100300c85d344287b97afc98fa91796b14ac200152000190060192b6c8a4993f3eb880c76c2ee13786d405d88513ba0ca8a36754d9b8ec01a6548cc34bb2bb8c2cb1a633260402026ed76d0c5eb7946c2fcda2ab629f87dd0000019c45ed879bb07b3258ebb5643892c22d707f469af2137ea591fe0eaa04636190000000014a16ee55bb6cab4005abf9065ea418c4e8f8e5d76e7d2548be24e1c8926c2f8006df6a6688f09715e9f584137af63a92170d68ff95265f233633609a58acafe95c4fde5cd8e67ea268795a6c06d39cb0196e9cd257dfcdb426df55701904c1fa5fe6aae9728babaff5f04a75f0b5e91d054148814a131ae854ec235ca45560ee80f96d7472090e72c878525200aad665c0722d3f5f753f07757f6ccaf1b58bebc4ae5fd666bf27937bc2548df7759cf83c0edf83599eaef88b98c469bfb000e6f556d94ea2decc5507c37609185c2f29ddec8165e2c717e83319de227833f8  "...
	              " []" +
	              " [\"ST15NyNF44nKEXYZMC9B9jLQFvAw5bJid5Uh32RGqSBD8Bdkwq6q\"]";
	      String[] signResults = CZcashOk.execute(networkType, signrawtransaction);
	      CZcashOk.dump("z_signrawtransaction_ok", signResults);
	           
	       String sendRawaction = "sendrawtransaction " + signResults[1]
	       //"0400008085202f89000180c3c901000000001976a91484c9bb6eb654d9495852caa8dad3c8f8d16349d488ac0000000060f80300000000000000000000000100000000000000001027000000000000f08c67d7b4d345ef576860f266cbbda51ab47c226871758b1b3a8f9bc98e42b70eacb7811741ec023bed50013c0d4c650eeda0c55eae7a24777f1004f90c5548b24448f6ccb792fc17a10acd2cc4283d54cbf1faa4e2bd6e78970a79e7edda072cdf07f3c9c71fe961b037158bf399c2ae34fc8b9d9e0f442739880569201ccd5cbe85b9550aa2d55691b5f1c92a21962b4d84a84e109053b267d96467d12f54a90876361981b877bef178962f1b9b6b71a5fe0435a58fe62f78cabc5a0a887330d0d69f8543580e22fda874d9d5ffa80522d924a26f15f8c54aedb5797f07bcc1daae8256f05df6650c1dee467d9492d83ea5497ab3de7d873b04286d414c94d10dfbbb7c4d03f22d6824b912737747e43790c224f1094cef9c78f35f096498ae3e85f68800a32414ee75725765170db217a8f09584951d0adce151badfe5c207f013b017d32c7611c110fc8ac118e9ae3cba708463eed021102ea948d2419862a5b8a8b248d5583ac6dcea6aabf35d1a75ce1d432b679ceaa34d0e29d37af602abe06e5e6a721fd0b1edd303bae8c62d1a04544a4f0ed862e7562dc166d8da490675d420f21f420c5dac5f7dcb185b8b062bd0033b33088ccc6ebe0904025e4941edc0733d13851e6c81e589f1d313dabd47dbfa49e127e58a93c8cd1a422495c27b2f106e4037aa52b6f712e0c7fe72fb871bf67c50c4facd5d8eba62b07d30cadb2e6e2c0c572658b8e12ef555910fc38757b4c4021e816402ee7ed89829c420e4ad36fe35eded710b4c0bdcb420d7ab8a36921c92bb2965c51636c69f477db3bda05c69b877058887318c2828e979bca74c2bb5cb299e6ab2e7b5738036a85f2f19bc5c33df34710db828fb9885fb36b7c6bfca9ddaa14ab66abb8304d5e40e9b38e4a8573bd9d6965bf605638cc9a54e21fc0053ba6c9032d03b5c0d6c23128b215626423f6b469b88b898753354f8061939fd3260d614515899979648f744463b75496a1d9b775d480b2e9e9663175da1d42ac063d3033746c94e5d0fa472b859c8ec039683fc4749a7522ed4cc11cdf4f564829d6967d9f290334dae2039043cf2154ecde0c27fc20f20e519d939d7b76ea201612cbef3baab9706a692f3eb30f1989ad2de2f0b174a4567d2c7dcb5c9bea28a49a7b1c573ee2fb4f10aa99443fd122a34f93a16265fa8ee800de2bfb362454c2dc444543473a3e5377d8a02f6d5f4d97376a44ffc336143e94215dddaac988a55a8d4b3c8fc0b11469201e5bd25293237d0cfa099986103be81fe5eb8c8d1a703035f479cd6a63a27e08f9d925d82584883f76b467a724ea106b04da3c3e040713e4944267cf2b6996d85c0a80783df1c8ba73e89ad810ae47501e0da6fecffa37db31ed508720182c2dda56a5b4461339e8f4a011c8aae3c287c176588089d01885e049902b894cfb2b8ce3f9376ba4fc20f6eb363faff2f4a5c5ba447b3b7be19e6c04c51a20c766f15a9c5ef2a00ab98fc73c9a7377389ab6a73ba69c00dccf79ecb53b1a5cec367b6cd6b15091521687918d6167052e06755bb55599b3561af74a2bdf64f8881b68d7e720358df76bb764e60ab1731dc68651948dae11423c9495edb6e24e8da763014420a7eb01e6b9fda1c0688f9a9551e2bfaa6451c538a47ef7bf78d9b494503b2da2c4c0e1a6e3d510edfc3880a503db59ba96e38c8cc60c6c8e98bbde884154e32d3882db2a602a3c83949bb7c21bb5a6a58ceabc49ea1b50505d026f798b69bd6ec1e87ef92c0d50dad0896b4ff0f62eb8cfda13b5859209cc3fcad075f7fa7f5c2d8485df0f4a1851f505602b1e5b1f11500141868a3b695e7ecc8e06fc92c0aaa89937263158336cb864fdae50f95ee6adee6059129c1d52160ea5d00e3813d2395e9c6d9f3f1428592be8e08a08a98920ac786083b49e070de635185feca173ffebbcccade301895d6361864ebfe786e00d66b77113f8b5aaa376cee10a224d25bb75bc41e01c4444814e9e9e8402537e44ea289fe77dff34677a01be4809bdfcd6ed984a2b5e89dc31834331a61833c0bf8c3296a9a61efaa49190841f71199f57853e7c1f1616e476f42e515d03858c250909e1cec2f5913914ee5895f5ff6af9264a69c53777217f1f5eb36c899dc55545d7dab357bff66a7fd78cc688679bc18266f33eed1327a7574ab04be30824aa845000d33ca3a23c213cddc623260cfffd62cb7c679339a1f7df491269e9bf3481d08a2d99cdc1d55609ad8135033de3e34dd8b21bf639726419bd9efe67189c708c41bf4058546dcf69884863ba4f76b20e88ddb6ccadec70f939de9ef8503b4d143302b54c1f6897c2d33c74d4cfa27f60f6900947312a1435f63d699318f282e77a87a6075faf36533614ffa1403252d2f117dc6e71404b8700365b7f609fbb58b58a7262edcf2e4f4d67e7cddbd47986e2643aa8838519aff01b17f54278526827736ee1f6214bacbe066109148a159e9c903fce183e2ede90e"
	          ;
	       //String[] sendResults = CZcashOk.execute(networkType, sendRawaction);
	       //CZcashOk.dump("sendrawtransaction", signResults);
            */
       
       
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


/*  test  key
 *
         tmC8UPrrJFq27wVG28aNBQ6pmHbWxVZkuww  cP1WkVoZvao1oXeLThGDB1L1fb9FKipVT7BwA2s8bMN4Fcat3Sm9 0.0000025
ztftXj8Ga7BdjTfLwNNYz3443vhtm6uGnaAegwvQGziBuiFtbCCd58zthsFk6Z3Jfc3FCxDknJZN3PtvpfEwzHVJ8ALHZxw  ST14Trpy3NrPqchtb1zSt79KqRnJFtkjggyoGNXTqqeXroBjDtmL  0.0000008

tmBbqB89pvrSQnzGjXCA2z8FaYz94ER54My  2
cUbJc9ncSEuAJZsN2eX8SaS1XUNv2Fkc9tjp3WbHnPUBpsFScB1U

  tmMpUC68e27EUuCUkFoR4AvF4zeZusi4jUk
cRkoZBfqGzz91NeHJHx8en2bfooHAwS7gxhLk6PKr6NGkhwCySBg

ztfFkibtzU4k1PP3XV7y8AB9NCchSppsvVjWiVamDqhbHXip1srdRacb2CuAnbmQFDL1TiKq6q166jpbBAX3yXnZX1wbQZF  100
ST14DSfko7MZ1PREootCuvz8tsntzyhhcNETh2JwGRkbBiiiZPSy

 ztb1pFVCBi4rfKfMhVzbLidPbwo1mDUoeqKDDfDr2DZkmtpoTZX1zwdUZKd9MbZoHkcBrLFeWaMsMBB8Vxe8BHeQw7BcZUP
ST15NyNF44nKEXYZMC9B9jLQFvAw5bJid5Uh32RGqSBD8Bdkwq6q

 t-t, z

 purse:
ztcfDz4EezXeV64euJJR9wzYa7fLTz4zKCmSvDMKJzWPB2NPvRM985hW6tZcsiWqoLEkf7tMGG8gNj4FAyDgdT9Kh247ibM
ST13W8nXcDKgWVN7FCAA5vSWqHtACU5VTJ9KYCi962kgmvzCkZDm

 tmVAvYUTW29Zqgw92gw12VJkyMJd5s6pmej
cNxNvo7sfD57y1yFwPXdxwXNRJ9nDVBggDuWVjc9GK1Pt1hvP7eq

 t-t
 tmNRn8FJPQUeLsPyYGxFtk12hwXMPDzTQT3
cV5aHU3qi2do4TVPUJGHEE2x7WaRM2hKwY3VkMH2GkRbnC7hdPJe

 //ok
t1dHjM6rXy7PZXmMdjLre7WKVb25QZNYYUA  L4N2SLLE48g9RaNg4RgMVc8X1MsQZtVmFodJJ61ixhC3bN6htpzn   0.000001

zcAAsSUGqDoik3P4cGh6TgvtDTgDioPG54xpQfCdPUzSgmR4YTQbCS3KYciJkFDXARDJaNrxMdknraJkDjws7oPRYG8zdB9 0.000001
        SKxsdNt9z4VEknQGR8yeXPsKdgEqcQWjMhc6r6qiSxpYYZa8ZB9Y
ZiVKQFeoXCR6qEzJpjZBUVopdLHLG9iubmkafNNG42FFZrzEcCDm2iXPoRbrFENDsWLqu3fLrHUGrqPbmChH5FNh5a7r2u3jV

t1eXyyNYqZQgwTiiWVS49YVHbQFY3VMNhKY
        L34GS9MbkjsEq1T8chvShzRxF4e3ituTKysoV8BaNJDmyDft8Bij  0.0000008
https://zcashfaucet.info/ref/t1eXyyNYqZQgwTiiWVS49YVHbQFY3VMNhKY

 zcgr7niKBCKLAbsdxbpE8UR3dqQXZB1xmYcPbupPZDuopT7t2zcQaoA56JidJBKmJRXMyFfTArbr8bmvEGG3STnWiEocRe8 0.000001
SKxsGQyZRXLwvaWFFmDq7R6MWJcRC3DfVCmu66SWWxha95xJCHdh

 t1YEFZZtMjbQcChP9YhCiDSBWJCstE4W2p8
KyjcUvGQdHj2sjYkNJ6SX1cHXChgv6gCq9QFZCFCV4JwmBmUEJQZ   0.000001

password:bf5e03d521df8156930f2f6aef9185a6
//des
t1RK9K8MCGdhTKoJNaBnanNStKH4JRUwAcU
        L2aoM8T48J8cdtAwtNL9Kq8j7LHPw2FD8jfmdu7e14dqaW5vS2pm

zcERitwwJj9eH7nudU4xK4BGauJAK9JgzK3qiTQfnbMug2sqSZj8FiaMLUKMGnJ1RTa6eQnVXUogEoSCjhQgY7uV57E1TH9

./zcash-cli z_sendmany 'ztfFkibtzU4k1PP3XV7y8AB9NCchSppsvVjWiVamDqhbHXip1srdRacb2CuAnbmQFDL1TiKq6q166jpbBAX3yXnZX1wbQZF' "[{\"amount\":2.0,\"address\":\"tmBbqB89pvrSQnzGjXCA2z8FaYz94ER54My\"}]"

opid-fcc43cac-1e4f-4aee-b216-e116c19ccf36
 */
