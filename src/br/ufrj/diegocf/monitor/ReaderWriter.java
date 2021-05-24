package br.ufrj.diegocf.monitor;

/**
 * Esta classe implementa o padrão leitor-escritor com prioridade para escrita.
 *
 * @author Diego Costa Ferreira
 *
 */
public class ReaderWriter implements RWLock {
    private boolean printLog = false;
    private int numReaders = 0;
    private int numWriters = 0;
    private int waitingWriters = 0;

    /**
     * Adquire lock para ler
     *
     * @param id identificador depuração
     */
    @Override
    public synchronized void acquireReadLock(int id) throws InterruptedException {
        while (numWriters > 0 || waitingWriters > 0) {
            //System.out.printf("Leitor bloqueado [%d] [%d, %d escritores aguardando]\n", id, numWriters, waitingWriters);
            if (printLog)
                System.out.printf("app.leitor_bloqueado(%d)\n", id);
            wait();
        }
        ++this.numReaders;
        //System.out.printf("Leitor lendo [%d]\n", id);
        if (printLog)
            System.out.printf("app.leitor_lendo(%d)\n", id);
    }

    /**
     * Libera lock para ler
     *
     * @param id identificador para depuração
     */
    @Override
    public synchronized void releaseReadLock(int id) throws InterruptedException {
        --numReaders;
        if (numReaders == 0) {
            notify();
        }
        // System.out.printf("Leitor saindo [%d]\n", id);
        if (printLog)
            System.out.printf("app.leitor_saindo(%d)\n", id);
    }

    /**
     * Adquire lock para escrever
     *
     * @param id identificador para depuração
     */
    @Override
    public synchronized void acquireWriteLock(int id) throws InterruptedException {
        ++waitingWriters;
        while (numReaders > 0 || numWriters > 0) {
            // System.out.printf("Escritor bloqueado [%d] [%d leitores lendo, %d]\n", id, numReaders, numWriters);
            if (printLog)
                System.out.printf("app.escritor_bloqueado(%d)\n", id);
            wait();
        }
        --waitingWriters;
        ++numWriters;
        // System.out.printf("Escritor escrevendo [%d]\n", id);
        if (printLog)
            System.out.printf("app.escritor_escrevendo(%d)\n", id);
    }

    /**
     * Libera lock para escrever
     *
     * @param id identificador para depuração
     */
    @Override
    public synchronized void releaseWriteLock(int id) throws InterruptedException {
        --numWriters;
        notifyAll();
        //System.out.printf("Escritor saindo [%d]\n", id);
        if (printLog)
            System.out.printf("app.escritor_saindo(%d)\n", id);
    }

    public void showLog(boolean show) {
        printLog = show;
    }

}
