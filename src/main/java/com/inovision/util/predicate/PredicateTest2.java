package com.inovision.util.predicate;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * -----------------------------------------------------------------------------
 * The following class provides an example of how to use the PredicateIterator
 * and Predicate interface.
 * 
 */

public class PredicateTest2 {

	//Sample predicate implementation
    static Predicate<Audit> pred = new Predicate<Audit>() {
        public boolean predicate(Audit o) {
            return (o.getRetention() < 9999);
        }
    };

    public static void main(String[] args) {
        List<Audit> list = new LinkedList<Audit>();
        list.add(new Audit(1));
        list.add(new Audit(10000));
        list.add(new Audit(100));
        list.add(new Audit(1000));
        list.add(new Audit(100000));
        list.add(new Audit(12));

        Iterator<Audit> iter  = list.iterator();
        Iterator<Audit> iter2 = new PredicateIterator<Audit>(iter, pred);
        while (iter2.hasNext()) {
            System.out.println(iter2.next());
        }
    }
}
