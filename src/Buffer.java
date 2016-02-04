import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by k3rnel on 4/2/16.
 */
public class Buffer {

    private Queue<String> buffer;
    private ReentrantLock lockBuffer = new ReentrantLock();
    private Condition emptyQueue, fullQueue;
    private int tam;

    public Buffer(int tam){
        buffer = new LinkedList<>();
        emptyQueue = lockBuffer.newCondition();
        fullQueue = lockBuffer.newCondition();
        this.tam = tam;
    }

    public String readElement(){
        String element = null;

        lockBuffer.lock();
        System.out.println("Intento obtener un elemento");

        while (buffer.size() == 0) {
            try {
                emptyQueue.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        element = buffer.poll();
        fullQueue.signal();

        lockBuffer.unlock();

        System.out.println("He obtenido un elemento");
        return element;
    }

    public void writeElement(String element){
        lockBuffer.lock();

        while (buffer.size() == tam) {
            try {
                fullQueue.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        buffer.add(element);
        emptyQueue.signal();
        lockBuffer.unlock();
    }
}
