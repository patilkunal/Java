package com.inovision.util.predicate;
 
/**
 * The following interface will provide only one method named "predicate". This
 * interface can be used with other classes to allow an Iterator 
 * (or Enumeration) to only return elements that pass some preliminary test.
 * 
 */

public interface Predicate<T> {

    boolean predicate(T element);

}
