package com.okcoin.vault.jni.xmr;

import java.util.ArrayList;
import java.util.List;

class WalletBase {

    protected List<String> params = new ArrayList<String>();

    protected String address = Moneroj.ADDRESS;
    protected String viewkey = Moneroj.VIEW_KEY;
    protected String spendkey = Moneroj.SPEND_KEY;
    protected Moneroj xmrj = new Moneroj();

    protected void setDaemonAddress(List<String> p) {
        p.add("--daemon-address");
//        p.add("localhost:28081");
        p.add("192.168.149.229:58081");
    }

    protected List<String> createParams() {
        List<String> p = new ArrayList<String>();
        p.add("--do-not-relay");
        p.add("--do_not_interact");
        setDaemonAddress(p);
//        p.add("--testnet");
        p.add("--log-file");
        p.add("/Users/oak/go/src/github.com/okblockchainlab/javawallet/xmr.json");
        p.add("--log-level");
        p.add("1");
        p.add("--password");
        p.add("1");
        return p;
    }
}

