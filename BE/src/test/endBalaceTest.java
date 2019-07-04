package test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.cogni.record.ManipulationsInData;
import com.cogni.record.Transaction;

public class endBalaceTest {

	@Test
	public void test() {
		ManipulationsInData data = new ManipulationsInData();
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		Transaction trans = new Transaction();
		trans.setReference(194261);
		trans.setAccNumber("NL91RABO0315273637");
		trans.setDescription("Clothes from Jan Bakker");
		trans.setStartBal((float) 21.6);
		trans.setMutation((float) -41.83);
		trans.setEndBal((float) -20.23);
		boolean actual = data.isCorrectEndBalance(trans.getStartBal(), trans.getMutation(), trans.getEndBal());
		assertEquals(false, actual);
	}

}
