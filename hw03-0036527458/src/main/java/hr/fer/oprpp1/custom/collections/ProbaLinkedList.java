package hr.fer.oprpp1.custom.collections;

public class ProbaLinkedList {

	public static void main(String[] args) {
		LinkedListIndexedCollection<String> l = new LinkedListIndexedCollection<>();
		l.add("prvi");
		l.add("drugi");
		l.add("treci");
		
		System.out.println(l.size());
		
		var elements = l.toArray();
		for(var element : elements) {
			System.out.println(element);
		}
	}

}
