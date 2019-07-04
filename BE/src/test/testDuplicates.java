package test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.cogni.record.ManipulationsInData;
import com.cogni.record.Transaction;

public class testDuplicates {

	@Test
	public void test() {
		ManipulationsInData data = new ManipulationsInData();
		int ref = 102033;
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		Transaction trans = new Transaction();
		trans.setReference(ref);
		trans.setAccNumber("IBAN000003342");
		trans.setDescription("Tickets for Bek");
		transactions.add(trans);
		boolean actual = data.checkDuplicates(ref, transactions);
		assertEquals(true, actual);
	}

}
