#!/usr/bin/env bash

cd src/main/java

#ln -snfv src/main/java/com/okcoin/vault/jni/xmr/Moneroj.java Moneroj.java

javah -jni com.okcoin.vault.jni.xmr.Moneroj

mv com_okcoin_vault_jni_xmr_Moneroj.h ../../..
