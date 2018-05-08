package eth;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WaitSeconds {

	    private final static Lock lock = new ReentrantLock();
	    private final static Condition condition = lock.newCondition();

	    public static void waitHere(long waitTime) {
	        lock.lock();
	        try {
	            condition.await(waitTime, TimeUnit.SECONDS);
	        } catch (InterruptedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        lock.unlock();
	    }


}

