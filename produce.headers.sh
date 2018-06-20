#!/usr/bin/env bash

ORIGINAL_DIR=$PWD
cd src/main/java
javah -jni com.okcoin.vault.jni.xmr.Moneroj
javah -jni com.okcoin.vault.jni.dash.Dashj
javah -jni com.okcoin.vault.jni.qtum.Qtumj
javah -jni com.okcoin.vault.jni.digibyte.Digibytej
mv com_okcoin_vault_jni_*.h $ORIGINAL_DIR
