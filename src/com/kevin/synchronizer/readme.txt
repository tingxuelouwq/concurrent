本包讲解了关于同步器的知识(同步器：使一个线程能够等待另一个线程的现象，实现线程之间的协作)

1、java.util.concurrent.CountDownLatch：可以让一个线程集等待直到计数变为0
实例见CountDownLatchTest.java

2、java.util.concurrent.CyclicBarrier：让一组线程等待至某个状态后，再全部同时执行
实例见CyclicBarrierTest*.java

3、java.util.concurrent.Semaphore：可以控制同时访问的线程个数，通过acquire()请求一个许可，如果没有则等待；通过release()释放一个许可
实例见SemaphoreTest*.java