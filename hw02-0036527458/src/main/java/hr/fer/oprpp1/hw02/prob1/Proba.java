package hr.fer.oprpp1.hw02.prob1;

public class Proba {

	public static void main(String[] args) {
		char [] data;
		String s = "Proba za\n rad!";
		data = new char[s.length()];
		
		for(int i = 0; i < s.length(); i++) data[i] = s.charAt(i);
		
		for(char e : data) System.out.println(e);
	}

}
