package com.okcoin.vault.jni;

public class CZcashj {

	//javah -classpath bin -jni com.okcoin.vault.jni.CZcashj

    //public native void sayHello();  
	
	public native String getAddress(String privateKey);
	
	public native byte[] produceUnsignedTx(String targetAddress, String amount);
	
	public native byte[] signTransaction(byte[] unsignedTx);
    
    
    static{  
        System.out.println(System.getProperty("java.library.path"));  
        System.load("/Users/wangwenfeng01/git/wallet/zcash/src/zcash-cli-ok.so");  
    }
	
	
	public void sayHello(){
		System.out.printf("hellow world");
	}
  
  
  
  
    public static void main(String[] args){  
    	CZcashj h = new CZcashj();  
        //String strRet = h.getAddress("SKxsdNt9z4VEknQGR8yeXPsKdgEqcQWjMhc6r6qiSxpYYZa8ZB9Y");  
        // strRet = h.getAddress("SKxsdNt9z4VEknQGR8yeXPsKdgEqcQWjMhc6r6qiSxpYYZa8ZB9Y"); 
        byte byteRet[] = h.produceUnsignedTx("ztrzHqJ8aN61rg31atWTyFETRZhQXZbKnP4m3bHPsooSD1gBxfkcxJVnZZMZRjXRgiGr6vDaQEKpk7iosgdWhKrhYVo2p7g", "3.000000");
        
       // System.out.printf( String(byteRet));
        //byte byteEnter[] = { 0x01, 0x02, 0x03};
        //byte byteSign[] = h.signTransaction(byteEnter);
        
    }  

}
