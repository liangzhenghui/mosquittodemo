/**
 * 2018年1月28日
 * liangzhenghui
 */
package thread;

public class Thread1 extends Thread {
	private static TestThread a =new TestThread();
	public String name;
	public Thread1(String name){
		this.name=name;
	}
	@Override
	public void run() {
		System.out.println("threadname="+name);
		try {
			//a是静态变量,所以不管new 所以线程1和线程2都是使用了同一个对象,当线程1拿到对象a的时候,线程2只能一直字啊等待
			//new TestThread().addD();
			//a.addD();
			new TestThread().addD();
			//new TestThread导致 每次都是不同的对象,所以不会锁定
			//new TestThread().addA();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
