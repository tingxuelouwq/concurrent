本包讲解了关于Callable和Future的知识

1、java.util.concurrent.Callable：与Runnable类似，但是带有返回值，并且可以抛出一个异常	

2、java.util.concurrent.Future：可以对具体的Runnable或者Callable任务进行取消、查询是否取消/完成、获取执行结果
boolean isDone()方法true的情形：
(1)任务正常完成
(2)任务执行过程中抛出异常
(3)任务被取消
boolean cancel(boolean mayInterruptIfRunning)方法：
(1)如果任务未开始执行，则返回true
(2)如果任务正在进行中，则mayInterrruptIfRunning表示是否允许中断(取消)正在执行的任务
(3)如果任务执行结束，则返回false

3、java.util.concurrent.FutureTask：既可以包装Runnable或者Callable，从而被线程执行；又可以作为Future得到执行的结果、取消任务、查询任务是否取消/完成
实例见FutureTest.java