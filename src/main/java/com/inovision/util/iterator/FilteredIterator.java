/*
 * Copyright ? 1999 - 2007 Inovis, Inc.
 *
 * ALL RIGHTS RESERVED.  NO PART OF THIS WORK MAY BE USED OR
 * REPRODUCED IN ANY FORM WITHOUT THE PERMISSION IN WRITING
 * OF INOVIS, INC.
 */

package com.inovision.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author kpatil
 *
 */
public class FilteredIterator<T> implements Iterator<T> {

    Iterator<T> iter;
    Filter<T> filter;
    T next;
    boolean doneNext = false;
	
    public FilteredIterator(Iterator<T> iter, Filter<T> filter) {
    	this.iter = iter;
    	this.filter = filter;
    }
    
	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
        doneNext = true;
        boolean hasNext;
        while (hasNext = iter.hasNext()) {
            next = iter.next();
            if (filter.test(next)) {
                break;
            }
        }
        return hasNext;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
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

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
