package hr.fer.oprpp1.hw04.db;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw04.db.ComparisonOperators;
import hr.fer.oprpp1.hw04.db.ConditionalExpression;
import hr.fer.oprpp1.hw04.db.FieldValueGetters;
import hr.fer.oprpp1.hw04.db.IComparisonOperator;
import hr.fer.oprpp1.hw04.db.IFilter;
import hr.fer.oprpp1.hw04.db.StudentDB;
import hr.fer.oprpp1.hw04.db.StudentDatabase;
import hr.fer.oprpp1.hw04.db.StudentRecord;

class Tester {

	@Test
	void getDatabase() {
		StudentDB sdb = new StudentDB();
		StudentDatabase studentDatabase = sdb.readFromFile();
		if(studentDatabase == null)
			fail();
	}
	
	@Test
	void forJMBAGTest() {
		StudentDB sdb = new StudentDB();
		StudentDatabase studentDatabase = sdb.readFromFile();
		
		if(studentDatabase == null) fail();
		else {
			StudentRecord student = studentDatabase.forJMBAG("0000000003");
			assertEquals("0000000003", student.getJmbag());
			assertEquals("Bosnić", student.getLastName());
			assertEquals("Andrea", student.getFirstName());
			assertEquals(4, student.getFinalGrade());
		}
	}
	
	@Test
	void forJMBAGTwoLastNamesTest() {
		StudentDB sdb = new StudentDB();
		StudentDatabase studentDatabase = sdb.readFromFile();
		
		if(studentDatabase == null) fail();
		else {
			StudentRecord student = studentDatabase.forJMBAG("0000000015");
			assertEquals("0000000015", student.getJmbag());
			assertEquals("Glavinić Pecotić", student.getLastName());
			assertEquals("Kristijan", student.getFirstName());
			assertEquals(4, student.getFinalGrade());
		}
	}
	
	@Test
	void filterMethodTrueTest() {
		class PrivateClass1 implements IFilter {
			@Override
			public boolean accepts(StudentRecord record) {
				return true;
			}
		}
		
		StudentDB sdb = new StudentDB();
		StudentDatabase studentDatabase = sdb.readFromFile();
		
		if(studentDatabase == null) fail();
		else {
			var list = studentDatabase.filter(new PrivateClass1());
			assertEquals(63, list.size());
		}
	}
	
	@Test
	void filterMethodFalseTest() {
		class PrivateClass2 implements IFilter {
			@Override
			public boolean accepts(StudentRecord record) {
				return false;
			}
		}
		
		StudentDB sdb = new StudentDB();
		StudentDatabase studentDatabase = sdb.readFromFile();
		
		if(studentDatabase == null) fail();
		else {
			var list = studentDatabase.filter(new PrivateClass2());
			assertEquals(0, list.size());
		}
	}
	
	@Test
	void strategy1Test() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertEquals(true, oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	void strategy2Test() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertEquals(false, oper.satisfied("Mirna", "Jasna"));
	}
	
	@Test
	void strategy3Test() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertEquals(false, oper.satisfied("Jasna", "Mirna"));
	}
	
	@Test
	void strategy4Test() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertEquals(true, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	void strategy5Test() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertEquals(true, oper.satisfied("Jasna", "Jasmina"));
	}
	
	@Test
	void strategy6Test() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertEquals(false, oper.satisfied("Jasna", "Jasmina"));
	}
	
	@Test
	void strategy7Test() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertEquals(true, oper.satisfied("Jasna", "Jasna"));
	}
	
	@Test
	void strategy8Test() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertEquals(true, oper.satisfied("Jasna", "Jasna"));
	}
	
	@Test
	void strategy9Test() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertEquals(true, oper.satisfied("Jasna", "Jasna"));
	}
	
	@Test
	void strategy10Test() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertEquals(true, oper.satisfied("Jasna", "Jasmina"));
	}
	
	@Test
	void strategy11Test() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("Zagreb", "Aba*"));
	}
	
	@Test
	void strategy12Test() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("Zagreb", "*ba"));
	}
	
	@Test
	void strategy13Test() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("AAA", "AA*AA"));
	}
	
	@Test
	void strategy14Test() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(true, oper.satisfied("AAAA", "AA*AA"));
	}
	
	@Test
	void strategy15Test() {
		StudentDB sdb = new StudentDB();
		StudentDatabase studentDatabase = sdb.readFromFile();
		StudentRecord record = studentDatabase.forJMBAG("0000000003");
		
		assertEquals("Andrea", FieldValueGetters.FIRST_NAME.get(record));
	}
	
	@Test
	void strategy16Test() {
		StudentDB sdb = new StudentDB();
		StudentDatabase studentDatabase = sdb.readFromFile();
		StudentRecord record = studentDatabase.forJMBAG("0000000003");
		
		assertEquals("Bosnić", FieldValueGetters.LAST_NAME.get(record));
	}
	
	@Test
	void strategy17Test() {
		StudentDB sdb = new StudentDB();
		StudentDatabase studentDatabase = sdb.readFromFile();
		StudentRecord record = studentDatabase.forJMBAG("0000000003");
		
		assertEquals("0000000003", FieldValueGetters.JMBAG.get(record));
	}
	
	@Test
	void strategy18Test() {
		StudentDB sdb = new StudentDB();
		StudentDatabase studentDatabase = sdb.readFromFile();
		StudentRecord record = studentDatabase.forJMBAG("0000000003");
		
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME,
				"Bos*",
				ComparisonOperators.LIKE
				);
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record), expr.getStringLiteral());
		assertEquals(true, recordSatisfies);
	}
	
	@Test
	void queryFilter1Test() {
		StudentDB sdb = new StudentDB();
		StudentDatabase db = sdb.readFromFile();
		
		QueryParser parser = new QueryParser(" jmbag =\"0000000003\" ");
		
		if(parser.isDirectQuery()) {
			StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
			assertEquals("0000000003", r.getJmbag());
		} else {
			fail();
			for(StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
				// do something
			}
		}
	}
	
	@Test
	void queryFilter2Test() {
		StudentDB sdb = new StudentDB();
		StudentDatabase db = sdb.readFromFile();
		
		QueryParser parser = new QueryParser(" jmbag =\"0000000003\" and lastName>=\"Bosnić\" and firstName LIKE \"An*a\" ");
		
		if(parser.isDirectQuery()) {
			fail();
			StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
			assertEquals("0000000003", r.getJmbag());
		} else {
			for(StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
				assertEquals("0000000003", r.getJmbag());
				assertEquals("Bosnić", r.getLastName());
				assertEquals("Andrea", r.getFirstName());
			}
		}
	}

}
