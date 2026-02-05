import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class BankAccount {
    private int balance = 1000;
    private Lock lock = new ReentrantLock();

   
    public synchronized void deposit(int amount) {
        balance += amount;
        System.out.println(Thread.currentThread().getName() +
                " deposited " + amount + ", Balance: " + balance);
    }

   
    public void withdraw(int amount) {
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                System.out.println(Thread.currentThread().getName() +
                        " withdrew " + amount + ", Balance: " + balance);
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " insufficient balance");
            }
        } finally {
            lock.unlock();
        }
    }
}


class UserThread extends Thread {
    private BankAccount account;

    UserThread(BankAccount account, String name) {
        super(name);
        this.account = account;
    }

    public void run() {
        account.deposit(200);
        account.withdraw(300);
    }
}


public class MultiThreadDemo {

    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        UserThread t1 = new UserThread(account, "Thread-1");
        UserThread t2 = new UserThread(account, "Thread-2");
        UserThread t3 = new UserThread(account, "Thread-3");

        t1.start();
        t2.start();
        t3.start();
    }
}
