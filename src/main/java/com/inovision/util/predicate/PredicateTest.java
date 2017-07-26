package com.inovision.util.predicate;
 
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * The following class provides an example of how to use the PredicateIterator
 * and Predicate interface.
 * 
 */

public class PredicateTest {

	//Sample predicate implementation
    static Predicate<String> pred = new Predicate<String>() {
        public boolean predicate(String o) {
            return o.toString().startsWith("Hunter");
        }
    };

    public static void main(String[] args) {
        List<String> list = new LinkedList<String>();
        list.add("Hunter, Alex");
        list.add("Miller, Scott");
        list.add("Hunter, Melody");
        list.add("Fox, Eric");
        list.add("Johnson, Jack");
        list.add("Hunter, Jeff");

        Iterator<String> iter  = list.iterator();
        Iterator<String> iter2 = new PredicateIterator<String>(iter, pred);
        while (iter2.hasNext()) {
            System.out.println(iter2.next());
        }
    }
}
