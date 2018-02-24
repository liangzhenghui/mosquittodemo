/**
 * 2018年1月28日
 * liangzhenghui
 */
package thread;

public class TestThread {
	private static int a=0;
    private static int b=0;
    public static String c;
    private  Object lockC = new Object();
    private  Object lockD = new Object();

    public synchronized  void addA() throws Exception{
    	  System.out.println(Thread.currentThread()+"addAaddAaddAaddAaddAaddAaddAaddAaddAaddA");
    	 while(true){
    	 }
    	 //Thread.sleep(5000);
    }

    public  synchronized void addB(){
    	System.out.println("addB");
    	/*synchronized(this){
    		System.out.println("enter");
    		 while(true){
        	 }
    	}*/
    }
    public  void addC(){
    	System.out.println("addC");
    	synchronized(lockC){
    		a=a+1;
    		System.out.println("acquire lockC");
    		System.out.println("a=========================="+a);
    	}
    }
    public  void addD(){
    	System.out.println("addD");
    	synchronized(lockD){
    		System.out.println("acquire lockD");
    		b=b+1;
    		System.out.println("b=========================="+b);
    		while(true){}
    	}
    }
    
    public  void addE(){
    	System.out.println("addD");
    		System.out.println("acquire lockD");
    		b=b+1;
    		System.out.println("b=========================="+b);
    		while(true){}
    }
    
    public static void main(String args[]) throws Exception{
    	/*for(int i=0;i<100;i++){
    		Thread1 t1=new Thread1(i+"");
    		//Thread2 t2=new Thread2();
    		t1.start();
    	}
    	for(int i=0;i<100;i++){
    		Thread2 t2=new Thread2(i+"");
    		//Thread2 t2=new Thread2();
    		t2.start();
    	}*/
    	Thread1 t1=new Thread1(1+"");
		//Thread2 t2=new Thread2();
		t1.start();
		Thread.sleep(3000);
		Thread2 t2=new Thread2(2+"");
		//Thread2 t2=new Thread2();
		t2.start();
	}
}
