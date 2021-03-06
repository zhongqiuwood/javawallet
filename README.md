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
### zcash_centos
```
git clone https://github.com/okblockchainlab/zcash
cd zcash/zcutil
./build_ok_centos.sh
cd ../zcash/src
make -f Makefile_src_centos
ls *.so
ls *.dylib
```

### zcash_mac
```
git clone https://github.com/okblockchainlab/zcash
cd zcash/zcutil
#手动修改代码 configure.ac  l498 -pie->-W1,-pie
./build.sh
cd ../zcash/src
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
export GOPATH=/your/go/path/directory  #设置GOPATH路径
cd $GOPATH/src
git clone https://github.com/okblockchainlab/Sia.git ./gitlab.com/NebulousLabs/Sia
cd ./gitlab.com/NebulousLabs/Sia
./build.sh #run this script only if you first time build the project
./runbuild.sh
ls *.so
ls *.dylib
```

### dcrd
```shell
export GOPATH=/your/go/path/directory  #设置GOPATH路径
cd $GOPATH/src
git clone https://github.com/okblockchainlab/dcrd.git ./github.com/decred/dcrd
cd ./github.com/decred/dcrd
./build.sh #run this script only if you first time build the project
./runbuild.sh
ls *.so
ls *.dylib
```

### nebulas
```shell
export GOPATH=/your/go/path/directory  #设置GOPATH路径
cd $GOPATH/src
git clone https://github.com/okblockchainlab/go-nebulas.git ./github.com/nebulasio/go-nebulas
cd ./github.com/nebulasio/go-nebulas
export COIN_DEPS=`pwd`/depslib
./build.sh #run this script only if you first time build the project
./runbuild.sh
ls *.so
ls *.dylib
```

### bytom
```shell
export GOPATH=/your/go/path/directory  #设置GOPATH路径
cd $GOPATH/src
git clone https://github.com/okblockchainlab/bytom.git ./github.com/bytom
cd ./github.com/bytom
./build.sh #run this script only if you first time build the project
./runbuild.sh
ls *.so
ls *.dylib
```

### starchain
```shell
yum install glide # setup glide, whick is a package management for golang.
export GOPATH=/your/go/path/directory  #设置GOPATH路径
cd $GOPATH/src
git clone https://github.com/okblockchainlab/starchain.git ./starchain
cd ./starchain
./build.sh #run this script only if you first time build the project
./runbuild.sh
ls *.so
ls *.dylib
```
