package com.inovision.util;

import java.util.ArrayList;

//$Id: ArrayHelper.java,v 1.4 2004/08/15 00:09:50 oneovthafew Exp $

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.lang.reflect.Array;

import org.hibernate.LockMode;
import org.hibernate.type.Type;

public final class ArrayHelper {

	public static String[] toStringArray(Object[] objects) {
		int length=objects.length;
		String[] result = new String[length];
		for (int i=0; i<length; i++) {
			result[i] = objects[i].toString();
		}
		return result;
	}

	public static String[] fillArray(String value, int length) {
		String[] result = new String[length];
		Arrays.fill(result, value);
		return result;
	}

	public static int[] fillArray(int value, int length) {
		int[] result = new int[length];
		Arrays.fill(result, value);
		return result;
	}

	public static LockMode[] fillArray(LockMode lockMode, int length) {
		LockMode[] array = new LockMode[length];
		Arrays.fill(array, lockMode);
		return array;
	}

	public static String[] toStringArray(Collection coll) {
		return (String[]) coll.toArray(EMPTY_STRING_ARRAY);
	}
	
	public static String[][] to2DStringArray(Collection coll) {
		return (String[][]) coll.toArray( new String[ coll.size() ][] );
	}
	
	public static int[][] to2DIntArray(Collection coll) {
		return (int[][]) coll.toArray( new int[ coll.size() ][] );
	}
	
	public static Type[] toTypeArray(Collection coll) {
		return (Type[]) coll.toArray(EMPTY_TYPE_ARRAY);
	}

	public static int[] toIntArray(Collection coll) {
		Iterator iter = coll.iterator();
		int[] arr = new int[ coll.size() ];
		int i=0;
		while( iter.hasNext() ) {
			arr[i++] = ( (Integer) iter.next() ).intValue();
		}
		return arr;
	}

	public static boolean[] toBooleanArray(Collection coll) {
		Iterator iter = coll.iterator();
		boolean[] arr = new boolean[ coll.size() ];
		int i=0;
		while( iter.hasNext() ) {
			arr[i++] = ( (Boolean) iter.next() ).booleanValue();
		}
		return arr;
	}

	public static Object[] typecast(Object[] array, Object[] to) {
		return java.util.Arrays.asList(array).toArray(to);
	}

	//Arrays.asList doesn't do primitive arrays

	public static List toList(Object array) {
		if ( array instanceof Object[] ) return Arrays.asList( (Object[]) array ); //faster?

		int size = Array.getLength(array);
		ArrayList list = new ArrayList(size);
		for (int i=0; i<size; i++) {
			list.add( Array.get(array, i) );
		}
		return list;
	}

	public static String[] slice(String[] strings, int begin, int length) {
		String[] result = new String[length];
		for ( int i=0; i<length; i++ ) {
			result[i] = strings[begin+i];
		}
		return result;
	}

	public static Object[] slice(Object[] objects, int begin, int length) {
		Object[] result = new Object[length];
		for ( int i=0; i<length; i++ ) {
			result[i] = objects[begin+i];
		}
		return result;
	}

	public static List toList(Iterator iter) {
		List list = new ArrayList();
		while ( iter.hasNext() ) {
			list.add( iter.next() );
		}
		return list;
	}

	public static String[] join(String[] x, String[] y) {
		String[] result = new String[ x.length + y.length ];
		for ( int i=0; i<x.length; i++ ) result[i] = x[i];
		for ( int i=0; i<y.length; i++ ) result[i+x.length] = y[i];
		return result;
	}

	public static int[] join(int[] x, int[] y) {
		int[] result = new int[ x.length + y.length ];
		for ( int i=0; i<x.length; i++ ) result[i] = x[i];
		for ( int i=0; i<y.length; i++ ) result[i+x.length] = y[i];
		return result;
	}

	private ArrayHelper() {}

	public static String toString( Object[] array ) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < array.length; i++) {
			sb.append( array[i] );
			if( i<array.length-1 ) sb.append(",");
		}
		sb.append("]");
		return sb.toString();
	}

	public static boolean isAllNegative(int[] array) {
		for ( int i=0; i<array.length; i++ ) {
			if ( array[i] >=0 ) return false;
		}
		return true;
	}

	public static void addAll(Collection collection, Object[] array) {
		for ( int i=0; i<array.length; i++ ) {
			collection.add( array[i] );
		}
	}

	public static final String[] EMPTY_STRING_ARRAY = {};
	public static final int[] EMPTY_INT_ARRAY = {};
	public static final Class[] EMPTY_CLASS_ARRAY = {};
	public static final Object[] EMPTY_OBJECT_ARRAY = {};
	public static final Type[] EMPTY_TYPE_ARRAY = {};

	public static int[] getBatchSizes(int maxBatchSize) {
		int batchSize = maxBatchSize;
		int n=1;
		while ( batchSize>1 ) {
			if (batchSize==3) batchSize=4; //allow 3,2,1

			batchSize = batchSize / 2;
			n++;
		}
		int[] result = new int[n];
		batchSize = maxBatchSize;
		for ( int i=0; i<n; i++ ) {
			result[i] = batchSize;
			if (batchSize==3) batchSize=4; //allow 3,2,1

			batchSize = batchSize / 2;
		}
		return result;
	}

}