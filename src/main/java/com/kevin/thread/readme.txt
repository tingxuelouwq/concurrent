本包讲解关于线程状态的知识，即Thread类中的方法

1、sleep方法会让线程睡眠，交出CPU，让CPU去执行其他的任务，但不会释放锁。例子见TestSleep.java
2、yield方法会让线程交出CPU，让CPU去执行其他的任务，但也不会释放锁。注：让出CPU后，CPU仍可能又会执行这个线程。
3、join方法会把指定的线程加入到当前线程并执行，而当前线程处于等待状态，直到指定线程执行完毕或者超时。例子见TestJoin*.java
4、interrupt方法：
(1)向线程发送中断请求，并将线程的中断状态设置为true；
(1)如果线程处于阻塞状态，则抛出一个InterruptedException异常，并清除中断状态；
(2)不能中断正在运行的线程；
例子见TestInterrupt*.java
5、setDeamon方法可以将线程设置为守护线程。当只剩下守护线程时，虚拟机就退出了，因此守护线程应该永远不去访问固有资源，如文件、数据库，因为它会在任何时候甚至在一个操作的中间发生中断
例子见TestDeamon.txt
6、setUncaughtExceptionHandler方法可以为线程设置非检查型异常处理器。例子见TestUncaughtExceptionHandler.java