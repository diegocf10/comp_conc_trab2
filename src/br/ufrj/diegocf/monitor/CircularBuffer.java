package br.ufrj.diegocf.monitor;

import java.util.Iterator;

/**
 * Esta classe implementa buffer circular com capacidade fixa.
 *
 * O buffer segue ordem FIFO, sobreescrevendo o elemento mais antigo caso o
 * buffer estiver cheio
 *
 * @author Diego Costa Ferreira
 *
 */
public class CircularBuffer<T> implements Iterable<T> {
    private int count;
    private int head;
    private int tail;
    private Object[] elements;

    public CircularBuffer(int capacity) {
        elements = new Object[capacity];
        head = 0;
        count = 0;
        tail = -1;
    }

    /**
     * Adiciona um elemento ao buffer
     * @param elem
     */
    public void add(T elem) {
        tail = (tail + 1) % elements.length;
        elements[tail] = (Object) elem;
        if (count == elements.length) {
            head = (head + 1) % elements.length;
        } else {
            ++count;
        }
    }

    /**
     * Retorna uma string representando a coleção
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (T value : this) {
            sb.append(value + " ");
        }
        sb.append("]");

        return sb.toString();
    }

    /**
     * Retorna um iterator
     */
    @Override
    public Iterator<T> iterator() {
        return new CircularBufferIterator();
    }

    /**
     * Retorna uma coleção iterável
     * @return
     */
    public Iterable<T> reverse() {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new ReversedIterator();
            }
        };
    }

    private class ReversedIterator implements Iterator<T> {
        private int start = tail;
        private int size = count;

        @Override
        public boolean hasNext() {
            return size > 0;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T next() {
            T elem = (T) elements[start];
            --start;
            if (start == -1) {
                start = elements.length - 1;
            }
            --size;
            return elem;
        }
    }

    private class CircularBufferIterator implements Iterator<T> {
        private int start = head;
        private int size = count;

        @Override
        public boolean hasNext() {
            return size > 0;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T next() {
            --size;
            T value = (T) elements[start];
            start = (start + 1) % elements.length;
            return value;
        }

    }

}