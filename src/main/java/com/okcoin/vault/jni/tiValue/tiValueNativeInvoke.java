package com.okcoin.vault.jni.tiValue;
import java.io.BufferedInputStream;  
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
  
public class tiValueNativeInvoke {
    public static String base_cmd = "/home/meng/work/github.com/tivalueproject/TiValue_so/tiValue ";
    public static String wallet_list_cmd = "cmd wallet_list ";
    public static String open_cmd = "cmd wallet_open ";
    public static String create_cmd = "cmd wallet_create ";
    public static String unlock_cmd = "cmd wallet_unlock ";
    public static String transfer_cmd = "cmd okcoin_wallet_transfer_to_address ";
    public static String broadcast_cmd = "cmd okcoin_network_broadcast_transaction ";
    public static String config = "init --data-dir=/home/meng/work/github.com/tivalueproject/TiValue_so/data ";
    //public static String config = "";
    public static String wallet_name = "oker ";
    public static String passwd = "okeroker ";
    public static String unlock_time = "99999 ";
    public static String transfer_asset = "TV ";
    public static String transfer_cnt = "1000 ";
    public static String private_key = "a4b7356107c1098ed8e6a71f47c6298e01145f0ab7beaed9b3894e280beb14ce ";
    public static String to_account = "TV5MbvgSVpRfxKVkDwRsFr6sSkW9oX2LFt2fXVpsRufvwHDGScPi ";


    public static String exec_cmd(String cmd) {
        String result = "";
        String exception = "";
        Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象  
        try {  
            Process p = run.exec(cmd);// 启动另一个进程来执行命令  
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));  
            String lineStr;  
            while ((lineStr = inBr.readLine()) != null) {
                System.out.println(lineStr);
                if (lineStr.contains("okcoin_fail"))
                    return lineStr;
                result = lineStr;
            } 
            
            if (p.waitFor() != 0) {  
                if (p.exitValue() == 1) {//p.exitValue()==0表示正常结束，1：非正常结束
                    System.err.println("命令执行失败!"); 
                    inBr.close();  
                    in.close();  
                    return "fail";
                }
            }
            inBr.close();  
            in.close(); 
        } catch (Exception e) {  
            e.printStackTrace();
        }

        return result;
    }

    public static boolean wallet_exit(String name) {
        String result = "";
        Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象  
        try {  
            Process p = run.exec(base_cmd + config + wallet_list_cmd);// 启动另一个进程来执行命令  
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));  
            String lineStr;  
            while ((lineStr = inBr.readLine()) != null) {
                result += lineStr;
            } 
            
            if (p.waitFor() != 0) {  
                if (p.exitValue() == 1) {//p.exitValue()==0表示正常结束，1：非正常结束  
                    System.err.println("命令执行失败!"); 
                    inBr.close();  
                    in.close();
                }
            }  
            inBr.close();  
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result.contains(name))
            return true;
        else
            return false;
    }

    public static void main(String[] args) {  
        String result = "";
        if (!wallet_exit("oker"))
            exec_cmd(base_cmd + config + create_cmd + wallet_name + passwd);

        result = exec_cmd(base_cmd + config + open_cmd + wallet_name + unlock_cmd + unlock_time + passwd 
            + transfer_cmd + transfer_cnt + transfer_asset + private_key + to_account);
        exec_cmd(base_cmd + config + open_cmd + wallet_name + unlock_cmd + unlock_time + passwd 
            + broadcast_cmd + result);
    }  
} 