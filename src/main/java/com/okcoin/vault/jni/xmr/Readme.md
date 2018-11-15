\<balance detail>
=============

获取余额信息以及Max tx index
```
 // <balance detail > ends, 0s elapsed
 // jni result getBalance: <UnlockedBalance>
 // jni result getBalance: <133.997560488885>
 // jni result getBalance: <Balance>
 // jni result getBalance: <133.997560488885>
 // jni result getBalance: <MaxTxIndex>
 // jni result getBalance: <178>
```

\<export_outputs>
==============
* 用```offset_txid```指定从哪个txid开始(到剩余所有tx)导出outputs
```
<export_outputs dummy offset_txid=efdb5179e9efa6f0a1cad848df99c574e2f5c49570890664f0150fe0821a8208 >
```

* 用```begin_txindex=n end_txindex=m```指定导出txindex为n和m之间的所有tx(包括n和m), 即[n,m]
```
<export_outputs dummy begin_txindex=0 end_txindex=9 >
<export_outputs dummy begin_txindex=10 end_txindex=19 >
```

* 返回结果新增一个schema \<TxNum>, 表示导出了多少个tx
```
[2018-11-14 23:20:12]: // <export_outputs dummy offset_txid=efdb5179e9efa6f0a1cad848df99c574e2f5c49570890664f0150fe0821a8208 begin_txindex= end_txindex= > ends, 0s elapsed
 // jni result exportOutputs: <TxNum>
 // jni result exportOutputs: <8>
 // jni result exportOutputs: <TxOutputs>
 // jni result exportOutputs: <ddd66fd36c454d4fa4d1799db34bbd2214a6364e7decd5d36f7881d55507be38c67decd13210f49435a031cbb62930260e>
```

* 注意事项: ```1. begin_txindex不能大于end_txindex，否则返回0个导出结果. 2. 如果end_txindex大于MaxTxIndex，则导出[begin_txindex, MaxTxIndex]```


\<import_outputs> 无更新
====================

\<import_key_images>
====================
* 用```offset_txid```指定从哪个txid开始导入key_images
```
<import_key_images dummy offset_txid=efdb5179e9efa6f0a1cad848df99c574e2f5c49570890664f0150fe0821a8208 >
```

* 用```begin_txindex=n```指定从哪个tx index开始导入key_images. ```begin_txindex必须为之前执行\<export_outputs>时begin_txindex的值```
```
<import_key_images dummy offset_txid= begin_txindex=10 >
```

* <import_key_images>返回结果新增2个schema
```
TxNum: 表示有多少个key_images被成功导入
XmrTx: 成功导入的key_images对应的tx信息的json串
```
```
[2018-11-14 23:20:09]: // <import_key_images dummy offset_txid= begin_txindex=10 > ends, 0s elapsed
 // jni result importKeyImages: <TxNum>
 // jni result importKeyImages: <2>
 // jni result importKeyImages: <XmrTx>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":10, "txId": "1480f7e4043b36d3ca80970f7fb78b26c190c33357379d5e60220f17af007484", "spent":1, "amount":7.705373512766, "blockHeight":1108590, "spentHeight":1121136, "internalOutputIndex":0, "globalOutputIndex":398633, "keyImage": "740124c81c49a9e329357d00e2b1d1d0edfa6c8f2a1279444eed5b099ce1a0fc", "mask": "0100000000000000000000000000000000000000000000000000000000000000", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <XmrTx>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":11, "txId": "041b98b6a7f2e011dab0bb0fe9283d8fdaebb281ae2aa236a88961ff490ae3a7", "spent":1, "amount":7.705108974049, "blockHeight":1108608, "spentHeight":1121137, "internalOutputIndex":0, "globalOutputIndex":398653, "keyImage": "1648b795c3e46d464995a5631809bf2da553563b512226806feda27e68143656", "mask": "0100000000000000000000000000000000000000000000000000000000000000", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <KeyImages>
 // jni result importKeyImages: <IMPORT_KEY_IMG_SUCCEED>
 // jni result importKeyImages: <xmrSpent>
 // jni result importKeyImages: <5479.643050697350>
 // jni result importKeyImages: <xmrUnspent>
 // jni result importKeyImages: <133.997560488885>
 // jni result importKeyImages: <KeyImagesImported2Height>
 // jni result importKeyImages: <1113105>
```

* XmrTx json说明
```
txindex: // tx所属地址中的索引，tx生成的时间越早，索引越小，最小为0
txid: // tx id
spent: // 是否已经使用过，0为未使用过. 必须为0才可以使用
amount: // 该tx包含多少个币
block_height: // 在哪个块转入
internal_output_index: //
global_output_index: //
spent_height: // 在哪个块转出，如果spent_height是0, spent肯定也是0，表示未花费过。
key_image: //
mask: //
rct: // 是否是 Ring Confidential Transaction
key_image_known: // 是否已经导入key image
pk_index: 0,
subaddr_index: 0/0,
key_image_partial: // 必须为0才可以使用
```
* 发起转账前根据策略挑选unspent txid
一个tx必须满足下面的条件才可被用于转账
```
spent==0 && key_image_partial==0
```

提币后tx状态(从热钱包里观察)
=====================
执行export_outputs/import_key_images后
1. "spent":0,  "spentHeight":0, : 表示这个tx未被花费
1. "spent":1,  "spentHeight":0, : 表示这个tx已被花费，但是还未被在某一个区块内打包
1.  "spent":1,  "spentHeight":H, : 表示这个tx已被花费，并且已经被在第H个区块内打包，但是需要在H+10个区块后才可以被确认(确认高度10个块)


充币后tx状态(从热钱包里观察)
=====================
1. 执行\<balance detail>后，MaxTxIndex,Balance和UnlockedBalance都没有变化，说明充值tx还未被打包
1. 执行\<balance detail>后，MaxTxIndex,Balance增加了，但是UnlockedBalance没有变化，说明充值tx已被打包在H块中(这个时候可以用export_outputs获取最新的tx json)
```
 "txIndex":179, "txId": "27415db6b157b815ddd0225ac11715dc0bb2fa48e2d2bc81a2074b639330c1a5", "spent":0, "amount":2.000000000000, "blockHeight":H
```
1. 等区块高度为H+10，执行\<balance detail>后，Balance和UnlockedBalance都增加，说明充值tx已被确认，可以花费该tx



 \<transfer> 用指定的unspent txid转账
============================

热钱包执行"transfer"命令调用***produceUnsignedTx***函数，新增一个参数String preferred_txids指定unspent txid，多个txid之间用","隔开，不能有空格。
如果preferred_txids为空，则按照以前的方式由so(C++)挑选unspent tx。
```
//        preferred_txids = "24c84f6702f89c4b36e5af14c4ad0fa4b4a95da50fa428f38f7c512bf95a8bc4," +
//                "8981c9ee27c330198b9c71ef04c7cd719545e66dcc51e41f4c2db43906bddf3b," +
//                "877c5feaa2483504e00b84510e06fca5db23af5ce42a6ef3f12618c48647c3b1,";

    public byte[][] produceUnsignedTx(String targetAddress, String priority, String amount, String preferred_txids) {

        params = createHotWalletParams();

        params.add("transfer");
        params.add("preferred_txid=" + preferred_txids);
        params.add(priority);
        params.add(targetAddress);
        params.add(amount);
        byte[][] res = XmrNativeInvoke.transcation(params.toArray(), null, null,null, null);
        return res;
    }
```

transfer out log
=========

* t1: 提交交易(转出1个币)之前，5笔unspent
```
 // jni result importKeyImages: <{"version": 0.12, "txIndex":174, "txId": "aa604ed8b565e6b104a38fb50d576e027819672836f5cf35b52c5bfa704f0525", "spent":1, "amount":101.998300768885, "blockHeight":1225194, "spentHeight":1225656, "internalOutputIndex":0, "globalOutputIndex":536058, "keyImage": "3e82e9c9458cc0d9522915bdcf2c440c45c186fbfe61ea6b3319f5f2654cc3ce", "mask": "d3a0f6bfa3ecb622e8c3ed6dfd005a173446adf9be8976271cccd22dcdbeab03", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":175, "txId": "847be92aed30ab32ab63662e32fc46057f17894f6c58e0a826ae6054b95a5af7", "spent":0, "amount":2.999259720000, "blockHeight":1225199, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536065, "keyImage": "95f760c613612d8892a9c2b41cd83d2f53d08c905584cf38f8d8f2db8267b2d9", "mask": "bfccd5c4ccd89e3ff4b2e96cc1d650676355d095a91caf867159e5384601aa01", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":176, "txId": "2086303602dcd3bdd4131b2a3e14d24d863825770545204b9a704c103960cec8", "spent":0, "amount":12.000000000000, "blockHeight":1225226, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536094, "keyImage": "bfcb247933326a7baa7d9ef2a03fa706b35356e0e0c3d9b5efa42c8eb8ed2425", "mask": "a75c8b632a2b54a205471b207884dbe28a4033bb495de339faa62257eb141908", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":177, "txId": "1c1efb7b06a23db574b4760336c8dfb6fad77afbf268957da4bd0b4fec55f034", "spent":1, "amount":5.000000000000, "blockHeight":1225226, "spentHeight":1225656, "internalOutputIndex":0, "globalOutputIndex":536096, "keyImage": "5441bb19160aafff3595270c720fa5b5b0dbf3434a3bc769a28fe5aaf02d359f", "mask": "fbf117e128d5001dd5b3cd743d6d3a3903ae1a2f549c64c936b6b2188f3b570a", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":178, "txId": "a3e073d4e4a54508224afa80c6d1fd8ef22de9520e9442dcfcc49a72159a6850", "spent":0, "amount":7.000000000000, "blockHeight":1225226, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536098, "keyImage": "e9f403d9e0b10c02841ece446ed2f904c3ce0414c7d1ed6dcad9f62126b53405", "mask": "bd2b42a892e6dd087ba298eade12bfd9d2b12100b90d8f8f60069cc74b873b02", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":179, "txId": "27415db6b157b815ddd0225ac11715dc0bb2fa48e2d2bc81a2074b639330c1a5", "spent":1, "amount":2.000000000000, "blockHeight":1225626, "spentHeight":1225652, "internalOutputIndex":0, "globalOutputIndex":536500, "keyImage": "a4ad4f691be4949ec77661ce7f6482b79e6e45e8024cc2a85b5a179eb79149d5", "mask": "119aa7366ad265121d94332f4cce98fe4576f19098c1f20ec03dd0c375a36505", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":180, "txId": "dd4b7a54194016179ea5187b8fdff7379462fb2558c94f1707fb3da47cef5596", "spent":0, "amount":3.999260380000, "blockHeight":1225652, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536528, "keyImage": "33c445d2adf079c5d3229e016ea57db9500f6b59c73cf0483f9be559e4e02e9e", "mask": "18a6cdae9e969c699b8e2d5e442f33f21d006e4dd3423692e78f87f85acb2d00", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":181, "txId": "588afe5fdb77f7f38da9f85f9781c44c408f4083e52b064adcbd655fac8e72bc", "spent":0, "amount":106.497561148885, "blockHeight":1225656, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536534, "keyImage": "6551b54e2af0feb1b99e1e4121b07b194d050a0f3d0b9c9af415add3c1f29906", "mask": "b000fabe2f2f2f7ee6f5fd44750314a0216060683039bd72ad2ffdb2d06d640f", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <KeyImages>
```

* t2: 刚提交交易之后 没有任何新tx id，但是175和180被标记为spent，spentHeight为0表示还未被打包在某区块
```
 // jni result importKeyImages: <{"version": 0.12, "txIndex":174, "txId": "aa604ed8b565e6b104a38fb50d576e027819672836f5cf35b52c5bfa704f0525", "spent":1, "amount":101.998300768885, "blockHeight":1225194, "spentHeight":1225656, "internalOutputIndex":0, "globalOutputIndex":536058, "keyImage": "3e82e9c9458cc0d9522915bdcf2c440c45c186fbfe61ea6b3319f5f2654cc3ce", "mask": "d3a0f6bfa3ecb622e8c3ed6dfd005a173446adf9be8976271cccd22dcdbeab03", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":175, "txId": "847be92aed30ab32ab63662e32fc46057f17894f6c58e0a826ae6054b95a5af7", "spent":1, "amount":2.999259720000, "blockHeight":1225199, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536065, "keyImage": "95f760c613612d8892a9c2b41cd83d2f53d08c905584cf38f8d8f2db8267b2d9", "mask": "bfccd5c4ccd89e3ff4b2e96cc1d650676355d095a91caf867159e5384601aa01", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":176, "txId": "2086303602dcd3bdd4131b2a3e14d24d863825770545204b9a704c103960cec8", "spent":0, "amount":12.000000000000, "blockHeight":1225226, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536094, "keyImage": "bfcb247933326a7baa7d9ef2a03fa706b35356e0e0c3d9b5efa42c8eb8ed2425", "mask": "a75c8b632a2b54a205471b207884dbe28a4033bb495de339faa62257eb141908", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":177, "txId": "1c1efb7b06a23db574b4760336c8dfb6fad77afbf268957da4bd0b4fec55f034", "spent":1, "amount":5.000000000000, "blockHeight":1225226, "spentHeight":1225656, "internalOutputIndex":0, "globalOutputIndex":536096, "keyImage": "5441bb19160aafff3595270c720fa5b5b0dbf3434a3bc769a28fe5aaf02d359f", "mask": "fbf117e128d5001dd5b3cd743d6d3a3903ae1a2f549c64c936b6b2188f3b570a", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":178, "txId": "a3e073d4e4a54508224afa80c6d1fd8ef22de9520e9442dcfcc49a72159a6850", "spent":0, "amount":7.000000000000, "blockHeight":1225226, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536098, "keyImage": "e9f403d9e0b10c02841ece446ed2f904c3ce0414c7d1ed6dcad9f62126b53405", "mask": "bd2b42a892e6dd087ba298eade12bfd9d2b12100b90d8f8f60069cc74b873b02", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":179, "txId": "27415db6b157b815ddd0225ac11715dc0bb2fa48e2d2bc81a2074b639330c1a5", "spent":1, "amount":2.000000000000, "blockHeight":1225626, "spentHeight":1225652, "internalOutputIndex":0, "globalOutputIndex":536500, "keyImage": "a4ad4f691be4949ec77661ce7f6482b79e6e45e8024cc2a85b5a179eb79149d5", "mask": "119aa7366ad265121d94332f4cce98fe4576f19098c1f20ec03dd0c375a36505", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":180, "txId": "dd4b7a54194016179ea5187b8fdff7379462fb2558c94f1707fb3da47cef5596", "spent":1, "amount":3.999260380000, "blockHeight":1225652, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536528, "keyImage": "33c445d2adf079c5d3229e016ea57db9500f6b59c73cf0483f9be559e4e02e9e", "mask": "18a6cdae9e969c699b8e2d5e442f33f21d006e4dd3423692e78f87f85acb2d00", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":181, "txId": "588afe5fdb77f7f38da9f85f9781c44c408f4083e52b064adcbd655fac8e72bc", "spent":0, "amount":106.497561148885, "blockHeight":1225656, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536534, "keyImage": "6551b54e2af0feb1b99e1e4121b07b194d050a0f3d0b9c9af415add3c1f29906", "mask": "b000fabe2f2f2f7ee6f5fd44750314a0216060683039bd72ad2ffdb2d06d640f", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <KeyImages>
```

* t3: 175和180被打包在"spentHeight":1225674，同时找零tx 182也可以获取到，因为182是175&180的找零，所以182的blockHeight和175&180的spentHeight一样。此时区块1225674未被确认，所以182不能花费(UnlockedBalance不包括182)
```
 // jni result getBalance: <UnlockedBalance>
 // jni result getBalance: <125.497561148885>
 // jni result getBalance: <Balance>
 // jni result getBalance: <131.495341658885>
 // jni result getBalance: <MaxTxIndex>
 // jni result getBalance: <182>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":174, "txId": "aa604ed8b565e6b104a38fb50d576e027819672836f5cf35b52c5bfa704f0525", "spent":1, "amount":101.998300768885, "blockHeight":1225194, "spentHeight":1225656, "internalOutputIndex":0, "globalOutputIndex":536058, "keyImage": "3e82e9c9458cc0d9522915bdcf2c440c45c186fbfe61ea6b3319f5f2654cc3ce", "mask": "d3a0f6bfa3ecb622e8c3ed6dfd005a173446adf9be8976271cccd22dcdbeab03", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":175, "txId": "847be92aed30ab32ab63662e32fc46057f17894f6c58e0a826ae6054b95a5af7", "spent":1, "amount":2.999259720000, "blockHeight":1225199, "spentHeight":1225674, "internalOutputIndex":0, "globalOutputIndex":536065, "keyImage": "95f760c613612d8892a9c2b41cd83d2f53d08c905584cf38f8d8f2db8267b2d9", "mask": "bfccd5c4ccd89e3ff4b2e96cc1d650676355d095a91caf867159e5384601aa01", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":176, "txId": "2086303602dcd3bdd4131b2a3e14d24d863825770545204b9a704c103960cec8", "spent":0, "amount":12.000000000000, "blockHeight":1225226, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536094, "keyImage": "bfcb247933326a7baa7d9ef2a03fa706b35356e0e0c3d9b5efa42c8eb8ed2425", "mask": "a75c8b632a2b54a205471b207884dbe28a4033bb495de339faa62257eb141908", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":177, "txId": "1c1efb7b06a23db574b4760336c8dfb6fad77afbf268957da4bd0b4fec55f034", "spent":1, "amount":5.000000000000, "blockHeight":1225226, "spentHeight":1225656, "internalOutputIndex":0, "globalOutputIndex":536096, "keyImage": "5441bb19160aafff3595270c720fa5b5b0dbf3434a3bc769a28fe5aaf02d359f", "mask": "fbf117e128d5001dd5b3cd743d6d3a3903ae1a2f549c64c936b6b2188f3b570a", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":178, "txId": "a3e073d4e4a54508224afa80c6d1fd8ef22de9520e9442dcfcc49a72159a6850", "spent":0, "amount":7.000000000000, "blockHeight":1225226, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536098, "keyImage": "e9f403d9e0b10c02841ece446ed2f904c3ce0414c7d1ed6dcad9f62126b53405", "mask": "bd2b42a892e6dd087ba298eade12bfd9d2b12100b90d8f8f60069cc74b873b02", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":179, "txId": "27415db6b157b815ddd0225ac11715dc0bb2fa48e2d2bc81a2074b639330c1a5", "spent":1, "amount":2.000000000000, "blockHeight":1225626, "spentHeight":1225652, "internalOutputIndex":0, "globalOutputIndex":536500, "keyImage": "a4ad4f691be4949ec77661ce7f6482b79e6e45e8024cc2a85b5a179eb79149d5", "mask": "119aa7366ad265121d94332f4cce98fe4576f19098c1f20ec03dd0c375a36505", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":180, "txId": "dd4b7a54194016179ea5187b8fdff7379462fb2558c94f1707fb3da47cef5596", "spent":1, "amount":3.999260380000, "blockHeight":1225652, "spentHeight":1225674, "internalOutputIndex":0, "globalOutputIndex":536528, "keyImage": "33c445d2adf079c5d3229e016ea57db9500f6b59c73cf0483f9be559e4e02e9e", "mask": "18a6cdae9e969c699b8e2d5e442f33f21d006e4dd3423692e78f87f85acb2d00", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":181, "txId": "588afe5fdb77f7f38da9f85f9781c44c408f4083e52b064adcbd655fac8e72bc", "spent":0, "amount":106.497561148885, "blockHeight":1225656, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536534, "keyImage": "6551b54e2af0feb1b99e1e4121b07b194d050a0f3d0b9c9af415add3c1f29906", "mask": "b000fabe2f2f2f7ee6f5fd44750314a0216060683039bd72ad2ffdb2d06d640f", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":182, "txId": "e5ed44e9ac1d9c57a35ce59ea2df8ea15a52ecc28957861baec66d60592f8a1c", "spent":0, "amount":5.997780510000, "blockHeight":1225674, "spentHeight":0, "internalOutputIndex":1, "globalOutputIndex":536555, "keyImage": "083738d730c329971b55faaf7963ee8a068ebbe0ba1cdb8e16b5f6508dbbbb80", "mask": "a7ece5f13846597251f1663384d381e5c6acd855c3ca8fb720e50d6a8f01cb0b", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <KeyImages>
```

* t4: 此时区块高度为1225684,  区块1225674被确认，UnlockedBalance和Balance值一样，tx182可以被花费
```
 // jni result getBalance: <UnlockedBalance>
 // jni result getBalance: <131.495341658885>
 // jni result getBalance: <Balance>
 // jni result getBalance: <131.495341658885>
 // jni result getBalance: <MaxTxIndex>
 // jni result getBalance: <182>
```

transfer in log
=========
* t1: 被充10个币之前,4笔upspent
```
 // jni result importKeyImages: <{"version": 0.12, "txIndex":175, "txId": "847be92aed30ab32ab63662e32fc46057f17894f6c58e0a826ae6054b95a5af7", "spent":1, "amount":2.999259720000, "blockHeight":1225199, "spentHeight":1225674, "internalOutputIndex":0, "globalOutputIndex":536065, "keyImage": "95f760c613612d8892a9c2b41cd83d2f53d08c905584cf38f8d8f2db8267b2d9", "mask": "bfccd5c4ccd89e3ff4b2e96cc1d650676355d095a91caf867159e5384601aa01", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":176, "txId": "2086303602dcd3bdd4131b2a3e14d24d863825770545204b9a704c103960cec8", "spent":0, "amount":12.000000000000, "blockHeight":1225226, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536094, "keyImage": "bfcb247933326a7baa7d9ef2a03fa706b35356e0e0c3d9b5efa42c8eb8ed2425", "mask": "a75c8b632a2b54a205471b207884dbe28a4033bb495de339faa62257eb141908", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":177, "txId": "1c1efb7b06a23db574b4760336c8dfb6fad77afbf268957da4bd0b4fec55f034", "spent":1, "amount":5.000000000000, "blockHeight":1225226, "spentHeight":1225656, "internalOutputIndex":0, "globalOutputIndex":536096, "keyImage": "5441bb19160aafff3595270c720fa5b5b0dbf3434a3bc769a28fe5aaf02d359f", "mask": "fbf117e128d5001dd5b3cd743d6d3a3903ae1a2f549c64c936b6b2188f3b570a", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":178, "txId": "a3e073d4e4a54508224afa80c6d1fd8ef22de9520e9442dcfcc49a72159a6850", "spent":0, "amount":7.000000000000, "blockHeight":1225226, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536098, "keyImage": "e9f403d9e0b10c02841ece446ed2f904c3ce0414c7d1ed6dcad9f62126b53405", "mask": "bd2b42a892e6dd087ba298eade12bfd9d2b12100b90d8f8f60069cc74b873b02", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":179, "txId": "27415db6b157b815ddd0225ac11715dc0bb2fa48e2d2bc81a2074b639330c1a5", "spent":1, "amount":2.000000000000, "blockHeight":1225626, "spentHeight":1225652, "internalOutputIndex":0, "globalOutputIndex":536500, "keyImage": "a4ad4f691be4949ec77661ce7f6482b79e6e45e8024cc2a85b5a179eb79149d5", "mask": "119aa7366ad265121d94332f4cce98fe4576f19098c1f20ec03dd0c375a36505", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":180, "txId": "dd4b7a54194016179ea5187b8fdff7379462fb2558c94f1707fb3da47cef5596", "spent":1, "amount":3.999260380000, "blockHeight":1225652, "spentHeight":1225674, "internalOutputIndex":0, "globalOutputIndex":536528, "keyImage": "33c445d2adf079c5d3229e016ea57db9500f6b59c73cf0483f9be559e4e02e9e", "mask": "18a6cdae9e969c699b8e2d5e442f33f21d006e4dd3423692e78f87f85acb2d00", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":181, "txId": "588afe5fdb77f7f38da9f85f9781c44c408f4083e52b064adcbd655fac8e72bc", "spent":0, "amount":106.497561148885, "blockHeight":1225656, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536534, "keyImage": "6551b54e2af0feb1b99e1e4121b07b194d050a0f3d0b9c9af415add3c1f29906", "mask": "b000fabe2f2f2f7ee6f5fd44750314a0216060683039bd72ad2ffdb2d06d640f", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":182, "txId": "e5ed44e9ac1d9c57a35ce59ea2df8ea15a52ecc28957861baec66d60592f8a1c", "spent":0, "amount":5.997780510000, "blockHeight":1225674, "spentHeight":0, "internalOutputIndex":1, "globalOutputIndex":536555, "keyImage": "083738d730c329971b55faaf7963ee8a068ebbe0ba1cdb8e16b5f6508dbbbb80", "mask": "a7ece5f13846597251f1663384d381e5c6acd855c3ca8fb720e50d6a8f01cb0b", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <KeyImages>
 // jni result importKeyImages: <IMPORT_KEY_IMG_SUCCEED>
 // jni result importKeyImages: <xmrSpent>
 // jni result importKeyImages: <5600.639871566235>
 // jni result importKeyImages: <xmrUnspent>
 // jni result importKeyImages: <131.495341658885>
 // jni result importKeyImages: <KeyImagesImported2Height>
 // jni result importKeyImages: <1225674>
 ```

* t2: 刚被充10个币(来自tx183 "blockHeight":1225687),5笔upspent, 此时"blockHeight":1225687未被确认，183不可被花费
 ```
 [2018-11-15 02:21:16]: // <balance detail > ends, 0s elapsed
 // jni result getBalance: <UnlockedBalance>
 // jni result getBalance: <131.495341658885>
 // jni result getBalance: <Balance>
 // jni result getBalance: <141.495341658885>
 // jni result getBalance: <MaxTxIndex>
 // jni result getBalance: <183>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":175, "txId": "847be92aed30ab32ab63662e32fc46057f17894f6c58e0a826ae6054b95a5af7", "spent":1, "amount":2.999259720000, "blockHeight":1225199, "spentHeight":1225674, "internalOutputIndex":0, "globalOutputIndex":536065, "keyImage": "95f760c613612d8892a9c2b41cd83d2f53d08c905584cf38f8d8f2db8267b2d9", "mask": "bfccd5c4ccd89e3ff4b2e96cc1d650676355d095a91caf867159e5384601aa01", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":176, "txId": "2086303602dcd3bdd4131b2a3e14d24d863825770545204b9a704c103960cec8", "spent":0, "amount":12.000000000000, "blockHeight":1225226, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536094, "keyImage": "bfcb247933326a7baa7d9ef2a03fa706b35356e0e0c3d9b5efa42c8eb8ed2425", "mask": "a75c8b632a2b54a205471b207884dbe28a4033bb495de339faa62257eb141908", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":177, "txId": "1c1efb7b06a23db574b4760336c8dfb6fad77afbf268957da4bd0b4fec55f034", "spent":1, "amount":5.000000000000, "blockHeight":1225226, "spentHeight":1225656, "internalOutputIndex":0, "globalOutputIndex":536096, "keyImage": "5441bb19160aafff3595270c720fa5b5b0dbf3434a3bc769a28fe5aaf02d359f", "mask": "fbf117e128d5001dd5b3cd743d6d3a3903ae1a2f549c64c936b6b2188f3b570a", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":178, "txId": "a3e073d4e4a54508224afa80c6d1fd8ef22de9520e9442dcfcc49a72159a6850", "spent":0, "amount":7.000000000000, "blockHeight":1225226, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536098, "keyImage": "e9f403d9e0b10c02841ece446ed2f904c3ce0414c7d1ed6dcad9f62126b53405", "mask": "bd2b42a892e6dd087ba298eade12bfd9d2b12100b90d8f8f60069cc74b873b02", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":179, "txId": "27415db6b157b815ddd0225ac11715dc0bb2fa48e2d2bc81a2074b639330c1a5", "spent":1, "amount":2.000000000000, "blockHeight":1225626, "spentHeight":1225652, "internalOutputIndex":0, "globalOutputIndex":536500, "keyImage": "a4ad4f691be4949ec77661ce7f6482b79e6e45e8024cc2a85b5a179eb79149d5", "mask": "119aa7366ad265121d94332f4cce98fe4576f19098c1f20ec03dd0c375a36505", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":180, "txId": "dd4b7a54194016179ea5187b8fdff7379462fb2558c94f1707fb3da47cef5596", "spent":1, "amount":3.999260380000, "blockHeight":1225652, "spentHeight":1225674, "internalOutputIndex":0, "globalOutputIndex":536528, "keyImage": "33c445d2adf079c5d3229e016ea57db9500f6b59c73cf0483f9be559e4e02e9e", "mask": "18a6cdae9e969c699b8e2d5e442f33f21d006e4dd3423692e78f87f85acb2d00", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":181, "txId": "588afe5fdb77f7f38da9f85f9781c44c408f4083e52b064adcbd655fac8e72bc", "spent":0, "amount":106.497561148885, "blockHeight":1225656, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536534, "keyImage": "6551b54e2af0feb1b99e1e4121b07b194d050a0f3d0b9c9af415add3c1f29906", "mask": "b000fabe2f2f2f7ee6f5fd44750314a0216060683039bd72ad2ffdb2d06d640f", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":182, "txId": "e5ed44e9ac1d9c57a35ce59ea2df8ea15a52ecc28957861baec66d60592f8a1c", "spent":0, "amount":5.997780510000, "blockHeight":1225674, "spentHeight":0, "internalOutputIndex":1, "globalOutputIndex":536555, "keyImage": "083738d730c329971b55faaf7963ee8a068ebbe0ba1cdb8e16b5f6508dbbbb80", "mask": "a7ece5f13846597251f1663384d381e5c6acd855c3ca8fb720e50d6a8f01cb0b", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <{"version": 0.12, "txIndex":183, "txId": "e2a9b895b2a074f64298d37ea4fb7f494ece516fa84bb2ac959cc791c43da761", "spent":0, "amount":10.000000000000, "blockHeight":1225687, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536569, "keyImage": "dfdff4956b90677903ed5591cc80641f99d24741cf78324cb10b3f374f7a852e", "mask": "1b5c8a95a211082d260de1b1b669d1fc7cdad84a011c6b25800a159983e47102", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result importKeyImages: <KeyImages>
 // jni result importKeyImages: <IMPORT_KEY_IMG_SUCCEED>
 // jni result importKeyImages: <xmrSpent>
 // jni result importKeyImages: <5600.639871566235>
 // jni result importKeyImages: <xmrUnspent>
 // jni result importKeyImages: <141.495341658885>
 // jni result importKeyImages: <KeyImagesImported2Height>
 // jni result importKeyImages: <1225687>
 ```
* t3: 此时区块高为1225697, tx183被确认, 可以被花费
 ```
 // jni result importKeyImages: <{"version": 0.12, "txIndex":183, "txId": "e2a9b895b2a074f64298d37ea4fb7f494ece516fa84bb2ac959cc791c43da761", "spent":0, "amount":10.000000000000, "blockHeight":1225687, "spentHeight":0, "internalOutputIndex":0, "globalOutputIndex":536569, "keyImage": "dfdff4956b90677903ed5591cc80641f99d24741cf78324cb10b3f374f7a852e", "mask": "1b5c8a95a211082d260de1b1b669d1fc7cdad84a011c6b25800a159983e47102", "rct":1, "keyImageKnown":1, "pkIndex":0, "keyImagePartial":0}>
 // jni result getBalance: <UnlockedBalance>
 // jni result getBalance: <141.495341658885>
 // jni result getBalance: <Balance>
 // jni result getBalance: <141.495341658885>
 // jni result getBalance: <MaxTxIndex>
 // jni result getBalance: <183>
 ```