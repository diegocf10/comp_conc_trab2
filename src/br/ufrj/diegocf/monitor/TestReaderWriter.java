package br.ufrj.diegocf.monitor;

class Reader implements Runnable {
    private final ReaderWriter rw;
    private final int id;

    public Reader(ReaderWriter rw, int id) {
        this.rw = rw;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (true) {
                rw.acquireReadLock(id);
                // algum trabalho
                Thread.sleep((long) (Math.random() * 1000));
                rw.releaseReadLock(id);
                Thread.sleep((long) (Math.random() * 2000));
            }
        } catch (InterruptedException e) {
        }
    }

}

class Writer implements Runnable {
    private final ReaderWriter rw;
    private final int id;

    public Writer(ReaderWriter rw, int id) {
        this.rw = rw;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (true) {
                rw.acquireWriteLock(id);
                // algum trabalho
                Thread.sleep((long) (Math.random() * 500));
                rw.releaseWriteLock(id);

                Thread.sleep((long) (Math.random() * 1000));
            }
        } catch (InterruptedException e) {
        }
    }

}

/**
 * Esta classe testa o padrão leitores-escritores
 */
public class TestReaderWriter {
    // cria 5 atuadores e 5 sensores
    private static final int NUM_ACTUATOR_SENSOR = 5;

    public static void main(String[] args) throws Exception {
        ReaderWriter rw = new ReaderWriter();
        rw.showLog(true);
        Thread[] threads = new Thread[NUM_ACTUATOR_SENSOR * 2];

        System.out.println("import verificaLE\napp = verificaLE.LE()");

        for (int i = 0; i < NUM_ACTUATOR_SENSOR; ++i) {
            threads[i] = new Thread(new Reader(rw, i));
        }

        for (int i = 0; i < NUM_ACTUATOR_SENSOR; ++i) {
            threads[i + NUM_ACTUATOR_SENSOR] = new Thread(new Writer(rw, i));
        }

        for (int i = 0, size = NUM_ACTUATOR_SENSOR * 2; i < size; ++i) {
            threads[i].start();
        }
    }
}
