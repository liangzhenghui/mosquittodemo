/**
 * 2018年2月9日
 * liangzhenghui
 */
package mosquittojavaclienttest;

import org.junit.Test;

public class TestStatic {
	static void demo(){
		System.out.println("--------------------------");
	}
	@Test
	public void testD(){
		TestStatic.demo();
		TestStatic.demo();
	}
}
