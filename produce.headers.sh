#!/usr/bin/env bash

ORIGINAL_DIR=$PWD
cd src/main/java
javah -jni com.okcoin.vault.jni.xmr.Moneroj
javah -jni com.okcoin.vault.jni.dash.Dashj
mv com_okcoin_vault_jni_*.h $ORIGINAL_DIR
