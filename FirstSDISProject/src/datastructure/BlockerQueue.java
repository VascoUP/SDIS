package datastructure;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockerQueue<T> {

    private Queue<T> queue;
    private Lock lock;
    private Condition notEmpty;

    public BlockerQueue() {
    	queue = new LinkedList<T>();
    	lock = new ReentrantLock();
    	notEmpty = lock.newCondition();
    }

    public void put(T element) throws InterruptedException {
    	System.out.println("Added element to blocker queue");
        queue.add(element);
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while(queue.isEmpty())
                notEmpty.await();

            T item = queue.remove();
            return item;
        } finally {
            lock.unlock();
        }
    }
}
