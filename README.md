## 编译对应的so文件

### xmr
```
git clone https://github.com/okblockchainlab/monero
cd monero
./runbuild.sh
ls src/*.so
ls src/*.dylib
```

### dash
```
git clone https://github.com/okblockchainlab/dash
cd dash
./runbuild.sh
ls src/*.so
ls src/*.dylib
```

### digibyte
```
git clone https://github.com/okblockchainlab/digibyte
cd digibyte
./runbuild.sh
ls src/*.so
ls src/*.dylib
```

### qtum
```
git clone https://github.com/okblockchainlab/qtum
cd qtum
./runbuild.sh
ls src/*.so
ls src/*.dylib
```
### zcash
```
git clone https://github.com/okblockchainlab/zcash
cd zcash/src
make -f Makefile_src
ls *.so
ls *.dylib