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
```shell
git clone https://github.com/okblockchainlab/stellar-core.git
cd stellar-core
export COIN_DEPS=`pwd`/depslib
./build.sh #only run this script if you first time build the project
./runbuild.sh
ls *.so
ls *.dylib
```

### raiblocks
```shell
git clone https://github.com/okblockchainlab/raiblocks.git
cd raiblocks
export COIN_DEPS=`pwd`/depslib
./build.sh #only run this script if you first time build the project
./runbuild.sh
ls *.so
ls *.dylib
```

### bitcoindiamond
```shell
git clone https://github.com/okblockchainlab/BitcoinDiamond.git
cd BitcoinDiamond
export COIN_DEPS=`pwd`/depslib
./build.sh #only run this script if you first time build the project
./runbuild.sh
ls *.so
ls *.dylib
```

### zen
```shell
git clone https://github.com/okblockchainlab/zen.git
cd zen
./build.sh #only run this script if you first time build the project
./runbuild.sh
ls *.so
ls *.dylib
```

### Sia
```shell
export GOPATH=$GOPATH:/your/go/path/directory  #设置GOPATH路径
cd /your/go/path/directory
git clone https://github.com/okblockchainlab/Sia.git ./gitlab.com/NebulousLabs/Sia
cd ./gitlab.com/NebulousLabs/Sia
./build.sh #run this script only if you first time build the project
./runbuild.sh
./runtest.sh
```
