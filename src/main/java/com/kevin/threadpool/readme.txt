本包讲解了关于线程池的知识

1、类的层次关系图
	Executor
			|
	ExecutorService -------------------------
			|								|
	AbstractExecutorService	       ScheduledExecutorService
			|								|
	ThreadPoolExecutor ---------- ScheduledThreadPoolExecutor
	
2、ThreadPool中的几个重要参数(corePoolSize/blockingQueue/maximumPoolSize/keepAliveTime/allowCoreThreadTimeOut)
(1)如果当前线程池中的线程数目小于corePoolSize，则每来一个任务，就会创建一个线程去执行这个任务
(2)如果当前线程池中的线程数目大于等于corePoolSize，则每来一个任务，会尝试将其添加到阻塞队列中。若添加成功，则该任务会等待空闲线程将其取出并执行；若添加失败(一般来说是因为阻塞队列已满)，则会尝试创建一个新的线程去执行这个任务
(3)如果当前线程池中的线程数目达到maximumPoolSize，则会采取任务拒绝策略进行处理
(4)如果当前线程池中的线程数目大于corePoolSize，此时如果某个线程的空闲时间超过keepAliveTime，则该线程会被终止，直至线程池中的线程数目不大于corePoolSize；如果设置allowCoreThreadTimeOut为true，则核心池中的线程空闲时间超过keepAliveTime，该线程也会被终止
实例见ThreadPoolTest.java

3、常用的几种线程池
newCachedThreadPool()：创建一个带缓存的线程池，对于每个任务，如果有空闲线程可用，则立即让该线程执行该任务，否则创建一个新的线程执行该任务。线程空闲时间为60s
【new ThreadPoolExecutor(0, Integer_MAXVALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>())】

newFixedThreadPool(int nThreads)：创建一个具有固定大小的线程池
【new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlocingQueue<Runnable>()))】

newSingleThreadExecutor()：创建大小为1的线程池，有一个线程执行提交的任务，一个接着一个
【new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()))】

newScheduledThreadPool(int nThreads)：创建执行预定任务的固定大小的线程池(替代java.util.Timer)
newSingleThreadScheduledExecutor()：创建执行预定任务的，大小为1的线程池

实例见ThreadPoolTest2.java

4、java.util.concurrent.ExecutorCompletionService：可以先构造一个执行器，然后通过该执行器构造ExectorCompletionService，并提交任务，最后通过take()方法取得执行结果
实例见CompletionServiceTest*.java