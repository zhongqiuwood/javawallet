#!/usr/bin/env bash

ORIGINAL_DIR=$PWD
cd src/main/java
javah -jni com.okcoin.vault.jni.xmr.Moneroj
mv com_okcoin_vault_jni_xmr_Moneroj.h $ORIGINAL_DIR
