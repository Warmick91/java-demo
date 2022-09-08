package edu.damago.java.struct;

import java.util.Arrays;

/**
 * Demo for arrays and objects.
 */
public class ArrayAndObjectDemo {

	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 */
	static public void main (final String[] args) {
		final long[] values = new long[args.length];
		for (int index = 0; index < args.length; ++index)
			values[index] = Long.parseLong(args[index].trim());
		System.out.println(Arrays.toString(values));

		final Account account = new Account();
		account.accountIdentity = values[0];
		account.ownerReference = values[1];
		account.balance = values[2];
		System.out.format("Account(id=%d, owner=%d, balance=%d)%n", account.accountIdentity, account.ownerReference, account.balance);
	}


	static public class Account {
		public long accountIdentity;
		public long ownerReference;
		public long balance;
	}
}
