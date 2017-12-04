package com.inovisionsoftware;

import sun.security.jca.Providers;
import java.security.*;
import java.security.Provider.Service;

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