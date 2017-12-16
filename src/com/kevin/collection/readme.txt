本包讲解了线程安全的集合

1、较早的线程安全集合
Vector -> ArrayList
HashTable -> HashMap
Collections.synchronizedCollection(Collection<E> c)
Collections.synchronizedList(List<E> c)	-> CopyOnWriteArrayList
Collections.synchronizedSet(Set<E> c)  -> CopyOnWriteArraySet
Collections.synchronizedMap(Map<K, V> c)  -> ConcurrentHashMap
Collections.synchronizedSortedSet(SortedSet<E> c)  -> ConcurrentSkipListSet
Collections.synchronizedSortedMap(SortedMap<K, V> c)  -> ConcurrentSkipListMap
实例见SynchronizedListTest.java

2、ConcurrentHashMap
使用分段锁，get操作不加锁，put/remove操作加锁。每次先一次hash找到segment，再一次hash找到key对应的链表头结点

3、ConcurrentLinkedQueue
采用FIFO结构，使用CAS非阻塞方式实现并发，即head/tail并非总是指向队头/队尾节点。
offer(e)
(1)找到尾节点
(2)casNext(null,n);
(3)更新尾节点，即如果越过超过1个节点，则更新tail
poll()
(1)删除头结点
(2)更新头结点，即如果越过至少1个节点，则更新head，并让旧head指向自己

4、CopyOnWriteArrayList/CopyOnWriteArraySet
读写分离。读时不加锁，写时加锁，并对底层数组进行拷贝，在拷贝的数组中添加元素后，再赋给原数组