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
    private static final int THREAD_SIZE = 10;

    public static void main(String[] args) throws Exception {
        ReaderWriter rw = new ReaderWriter();
        rw.showLog(true);
        Thread[] threads = new Thread[THREAD_SIZE];

        System.out.println("import verificaLE\napp = verificaLE.LE()");

        for (int i = 0; i < 6; ++i) {
            threads[i] = new Thread(new Reader(rw, i));
        }

        for (int i = 6; i < 10; ++i) {
            threads[i] = new Thread(new Writer(rw, i));
        }

        for (int i = 0; i < THREAD_SIZE; ++i) {
            threads[i].start();
        }
    }
}
