package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;

import hr.fer.oprpp1.custom.collections.demo.EvenIntegerTester;

/**
 * Class used for testing.
 * @author Tin Jukić
 *
 */
public class LinkedListCollectionAddTest {

	public static void main(String[] args) {
		Collection col1 = new LinkedListIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();
//		Collection col2 = new LinkedListIndexedCollection();
//		Collection col1 = new ArrayIndexedCollection();
		col1.add(2);
		col1.add(3);
		col1.add(4);
		col1.add(5);
		col1.add(6);
		col2.add(12);
		System.out.println(Arrays.toString(col1.toArray()));
		System.out.println(Arrays.toString(col2.toArray()));
		col2.addAllSatisfying(col1, new EvenIntegerTester());
		col2.forEach(System.out::println);
	}

}
