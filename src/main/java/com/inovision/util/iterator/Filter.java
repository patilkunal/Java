/*
 * Copyright ? 1999 - 2007 Inovis, Inc.
 *
 * ALL RIGHTS RESERVED.  NO PART OF THIS WORK MAY BE USED OR
 * REPRODUCED IN ANY FORM WITHOUT THE PERMISSION IN WRITING
 * OF INOVIS, INC.
 */

package com.inovision.util.iterator;

/**
 * @author kpatil
 *
 */
public interface Filter<T> {

	public boolean test(T element);
}
