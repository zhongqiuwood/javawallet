package com.okcoin.vault.jni.xmr;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class WalletBase {

    protected List<String> params = new ArrayList<String>();

    protected String address = Moneroj.ADDRESS;
    protected String viewkey = Moneroj.VIEW_KEY;
    protected String spendkey = Moneroj.SPEND_KEY;
    protected String daemonUrl = Moneroj.DAEMON_URL;
    protected Moneroj xmrj = new Moneroj();

    protected void setDaemonAddress(List<String> p) {
        p.add("--daemon-address");
        p.add(daemonUrl);
    }


    protected List<String> createParams() {
        List<String> p = new ArrayList<String>();
        p.add("--do-not-relay");
        p.add("--do_not_interact");

        String uuid = UUID.randomUUID().toString().toLowerCase();
        p.add("--invoke_id");
        p.add(uuid);

        setDaemonAddress(p);
        if (Moneroj.ENABLE_TESTNET) {
            p.add("--testnet");
        }
        p.add("--log-file");
        p.add("/Users/oak/go/src/github.com/okblockchainlab/javawallet/wallet_data/xmr/testnet/_xmr.json");
        p.add("--log-level");
        p.add(Moneroj.logLevel);
        p.add("--password");
        p.add("1");
        return p;
    }
}

