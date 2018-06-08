
import java.util.ArrayList;
import java.util.List;


class WalletBase {

    protected List<String> params = new ArrayList<String>();

    protected String walletDataPath;
    protected String address = Moneroj.ADDRESS;
    protected String viewkey = Moneroj.VIEW_KEY;
    protected String spendkey = Moneroj.SPEND_KEY;
    protected Moneroj xmrj = new Moneroj();

    protected void setDaemonAddress(List<String> p) {
        p.add("--daemon-address");
        p.add("localhost:28081");
    }

    // --testnet --password 1 --wallet-file /Users/oak/go/src/github.com/monero-project/monero/src/javawallet/testcold/cold --command

    protected List<String> initParams() {
        List<String> p = createParams();
        p.add("--wallet-file");
        p.add(walletDataPath);
        p.add("--command");

        return p;
    }

    protected List<String> createParams() {
        List<String> p = new ArrayList<String>();
        p.clear();
        p.add("--do-not-relay");
        p.add("--do_not_interact");
        setDaemonAddress(p);
        p.add("--testnet");
        p.add("--log-level");
        p.add("4");
        p.add("--password");
        p.add("1");
        return p;
    }

}

