import java.util.ArrayList;

/**
 * Created by k3rnel on 4/2/16.
 */
public class Main {

    public static void main(String [] args){
        Buffer buffer = new Buffer(10);
        ArrayList<Thread> threads = new ArrayList<>();

        Runnable rRead = new Runnable() {
            @Override
            public void run() {
                  buffer.readElement();
            }
        };

        Runnable rWrite = new Runnable() {
            @Override
            public void run() {
                buffer.writeElement("this is an element!");
            }
        };


        Thread t;

        for (int i = 0; i < 10; i++){
            t = new Thread(rRead);
            threads.add(t);
            t.start();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++){
            t = new Thread(rWrite);
            threads.add(t);
            t.start();
        }


        for (Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
