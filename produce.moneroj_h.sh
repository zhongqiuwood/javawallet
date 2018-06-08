#!/usr/bin/env bash

ln -snfv src/main/java/Moneroj.java Moneroj.java

javah -jni Moneroj

rm Moneroj.java

mv Moneroj.h ../wallet

