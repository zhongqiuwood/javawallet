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
```

### stellar
```
git clone --recursive https://github.com/okblockchainlab/stellar-core.git
cd stellar-core/ok-wallet
mkdir build
cd build
cmake ..
make
ls *.so
ls *.dylib
```

### raiblocks
```shell
git clone --recursive https://github.com/okblockchainlab/raiblocks.git
cd raiblocks/ok-wallet
mkdir build
cd build
## for testnet, set ACTIVE_NETWORK=rai_test_network; for release version, set CMAKE_BUILD_TYPE=Release; the default BOOST_ROOT is /usr/local/boost if you don't set BOOST_ROOT variable.
cmake -DACTIVE_NETWORK=rai_live_network -DCMAKE_BUILD_TYPE=Debug -DBOOST_ROOT=[boost_root] ..
make
ls *.so
ls *.dylib
```
