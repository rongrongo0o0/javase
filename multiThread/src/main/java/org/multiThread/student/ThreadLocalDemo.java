package org.multiThread.student;

import java.util.Random;

/**
 * ThreadLocal线程局部变量测试类
 * 
 * @Title:ThreadLocalDemo.java
 * @Package:org.multiThread
 * @Description:
 * @author:yangrong
 * @date:2018年7月5日上午10:15:03
 * @updater:
 * @version:V1.0
 * @updates:
 */
public class ThreadLocalDemo implements Runnable {
	// 创建线程局部变量
	private static final ThreadLocal studentLocal = new ThreadLocal();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		accessStudent();
	}

	/**
	 * 测试业务方法
	 */
	public void accessStudent() {
		String currentTdName = Thread.currentThread().getName();
		System.out.println(currentTdName + " is running!");
		// 产生一个随机数并打印
		Random random = new Random();
		int age = random.nextInt(100);
		System.out.println("thread " + currentTdName + " set age to:" + age);
		// 获取一个Student对象，并将随机数年龄插入到对象属性中
		Student student = getStudent();
		student.setAge(age);
		System.out.println("thread " + currentTdName + " first read age is:" + student.getAge());
		try {
			Thread.sleep(500);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		System.out.println("thread " + currentTdName + " second read age is:" + student.getAge());
	}

	public static void main(String[] args) {
		ThreadLocalDemo td = new ThreadLocalDemo();
		Thread t1 = new Thread(td, "a");
		Thread t2 = new Thread(td, "b");
		t1.start();
		t2.start();
	}

	protected Student getStudent() {
		// 获取本地线程变量并强制转换为Student类型
		Student student = (Student) studentLocal.get();
		// 线程首次执行此方法的时候，studentLocal.get()肯定为空
		if (student == null) {
			// 创建一个student对象，并保存到本地线程变量studentLocal中
			student = new Student();
			studentLocal.set(student);
		}
		return student;
	}
}
