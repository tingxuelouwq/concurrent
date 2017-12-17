本包讲解了关于阻塞队列的知识

有界队列
ArrayBlockingQueue：用数组实现的阻塞队列，有界队列，需要指定最大容量
LinkedBlockingQueue：用链表实现的阻塞队列，有界队列(默认容量为Integer.MAX_VALUE)，可以指定最大容量
LinkedBlockingDequeue：用双端链表实现的阻塞队列，有界队列(默认容量为Integer.MAX_VALUE)，可以指定最大容量
实例见CP*.java(使用阻塞队列和不使用阻塞队列实现的消费者-生产者模式)，以及BlockingQueueTest.java

无界队列
PriorityBlockingQueue：用优先队列实现的阻塞队列，无界队列(会自动扩容)
DelayQueue：
1、基于优先队列实现的延时阻塞队列，无界队列。队列中的元素必须实现Delayed接口，在创建元素时可以指定多久才能从队列中获取当前元素，只有在延迟期满时才能从队列中提取元素。
2、DelayQueue的应用场景：(1)缓存系统的设计，可以用DelayQueue保存缓存元素的有效期，使用一个线程循环查询DelayQueue，一旦能从DelayQueue中获取元素时，说明缓存有效期到了；(2)定时任务调度，DelayQueue保存当天将会执行的任务和执行时间，一旦从DelayQueue中获取到任务就开始执行，从比如TimerQueue就是使用DelayQueue实现的。
实例见SessionCache.java

SynchronousQueue：一个没有数据缓冲的阻塞队列，因此调用peek()方法会返回null，size()方法会返回0，isEmpty()方法会返回true。生产者线程对其进行的put操作必须等待消费者的take操作，反过来也一样
实例见SynchronousQueueTest.java

TransferQueue(其实现类为LinkedTransferQueue)：
1、一般的BlockingQueue在队列未满的情况下，往队列中put元素是不会阻塞的，为了满足往无界队列中put元素也阻塞的需求，出现了TransferQueue。在调用transfer(E e)方法存放元素时将发生阻塞，直到有消费者从队列中take存放的元素，也就是说，如果在transfer(E e)的元素之前还有别的元素，则依旧阻塞，直到存放的那个元素被消费者取走
2、从功能角度来讲，LinkedTransferQueue实际上是LinkedBlockingQueue(我们可以直接使用BlockingQueue的方法)、SynchronousQueue(我们可以使用transfer方法)、ConcurrentLinkedQueue(内部使用CAS非阻塞算法)的超集
实例见TransferQueueTest*.java