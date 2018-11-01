#!/usr/bin/env bash

ORIGINAL_DIR=$PWD
cd src/main/java
javah -jni com.okcoin.vault.jni.xmr.XmrNativeInvoke
javah -jni com.okcoin.vault.jni.zcash.ZcashNativeInvoke
javah -jni com.okcoin.vault.jni.dash.DashNativeInvoke
javah -jni com.okcoin.vault.jni.qtum.QtumNativeInvoke
javah -jni com.okcoin.vault.jni.digibyte.DigibyteNativeInvoke
javah -jni com.okcoin.vault.jni.xrb.Xrbj

mv com_okcoin_vault_jni_*.h $ORIGINAL_DIR
