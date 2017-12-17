本包讲解了关于多线程同步方面的知识，即Lock和synchronized关键字

1、
java.util.concurrent.locks.ReentrantLock
ReentrantLock()方法可以构建一个可重入锁。可重入锁保持一个持有计数来跟踪对lock方法的嵌套调用。

2、
java.util.concurrent.locks.Lock
Condition newCondition()方法可以返回一个与该锁相关的条件对象

3、
java.util.concurrent.locks.Condition
void await()方法将该线程放到条件的等待集中
void signalAll()方法解除该条件的等待集中的所有线程的阻塞状态，并且一旦锁成为可用的，它们中的某个将从await调用返回，获得该锁并从被阻塞的地方继续执行

4、
java.util.concurrent.locks.Lock
void lock()方法获取该锁，如果该锁已被其他线程获取，则该线程等待。该方法不能响应中断
void unlock()方法释放该锁
boolean tryLock()方法尝试获得该锁，如果成功则返回true。
boolean tyrLock(long time, TimeUnit unit) throws InterruptedException方法在给定时间内尝试获得该锁，如果成功则返回true。该方法能够响应中断
void lockInterruptibly() throws InterruptedException方法获得该锁，如果该锁已被其他线程获取，则该线程等待。该方法能够响应中断

5、Lock和synchronized的区别
(1)Lock是一个接口，而synchronized是Java中的关键字，synchronized是内置的语言实现
(2)synchronized方法或synchronized代码块在执行完毕后，或者发生异常时，会自动释放线程占有的锁；而Lock必须要用户去手动释放锁，如果没有主动释放锁，就很有可能导致死锁
(3)Lock可以让等待锁的线程响应中断，比如带计时的tryLock方法或者lockInterruptibly方法；而synchronized不能让等待锁的线程响应中断
(4)通过Lock可以知道有没有成功获得锁，而synchronized则无法办到

6、
java.util.concurrent.locks.ReentrantReadWriteLock
Lock readLock()方法可以得到一个被多个读操作共用的读锁，但会排斥所有写操作
Lock writeLock()方法可以得到一个写锁，排斥其他所有的读操作和写操作

7、volatile关键字
(1)可以保证线程之间的可见性
(2)不能保证原子性
(3)能够禁止指令重排

8、final变量
(1)写final域：在构造函数内，对一个final域的写入happen before将这个域的引用赋给一个引用变量
(2)读final域：读一个对象的final域之前，一定会先读包含这个final域的对象的引用

9、
java.lang.ThreadLocal：避免共享变量
每个Thread都维护了一个ThreadLocalMap映射表，这个映射表的key是ThreadLocal实例本身(弱引用)，value是真正需要存储的Object。即ThreadLocal为每一个线程都提供了变量的副本。

