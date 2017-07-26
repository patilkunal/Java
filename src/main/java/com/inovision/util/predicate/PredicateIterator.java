package com.inovision.util.predicate;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * -----------------------------------------------------------------------------
 * The following class implements the Iterator interface and provides access
 * to a predicate that will filter out certains elements using the Predicate
 * interface.
 * 
 */

public class PredicateIterator<T> implements Iterator<T> {

    Iterator<T> iter;
    Predicate<T> pred;
    T next;
    boolean doneNext = false;

    public PredicateIterator(Iterator<T> iter, Predicate<T> pred) {
        this.iter = iter;
        this.pred = pred;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public boolean hasNext() {
        doneNext = true;
        boolean hasNext;
        while (hasNext = iter.hasNext()) {
            next = iter.next();
            if (pred.predicate(next)) {
                break;
            }
        }
        return hasNext;
    }

    public T next() {
        if (!doneNext) {
            boolean has = hasNext();
            if (!has) {
                throw new NoSuchElementException();
            }
        }
        doneNext = false;
        return next;
    }

}
