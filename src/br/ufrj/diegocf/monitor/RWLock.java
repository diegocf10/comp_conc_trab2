package br.ufrj.diegocf.monitor;

/*
 *  Interface RWLock
 *
 */
public interface RWLock {

    void acquireReadLock(int id) throws InterruptedException;

    void releaseReadLock(int id) throws InterruptedException;

    void acquireWriteLock(int id) throws InterruptedException;

    void releaseWriteLock(int id) throws InterruptedException;

}
