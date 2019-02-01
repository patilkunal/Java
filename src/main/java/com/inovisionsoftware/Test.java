package com.inovisionsoftware;

import java.security.Provider;
import java.security.Provider.Service;

import sun.security.jca.Providers;

@SuppressWarnings("restriction")
public class Test {
	public static void main(String args[]) {
for (Provider p : Providers.getProviderList().providers()) {
            for (Service s : p.getServices()) {
                if (s.getType().equals("SecureRandom")) {
                    System.out.println( s.getAlgorithm());
                }
            }
        }		
	}
}