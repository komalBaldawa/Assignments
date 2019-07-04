package com.cogni.record;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

public class ManipulationsInData {

	/**
	 * This method will check whether the reference number is duplicate
	 * @param refNo - current record reference number
	 * @param transactions - All successful transactions
	 * @return true if the transaction is duplicate
	 */
	public boolean checkDuplicates(int refNo, ArrayList<Transaction> transactions) {
		for(Transaction trans: transactions) {
			if(trans.getReference() == refNo)
				return true;
		}
		return false;
	}
	
	
	/**
	 * This method checks the end balance after the transaction
	 * @param startBal
	 * @param mutation
	 * @param endBal
	 * @return true if the End Balance is not correct
	 */
	public boolean isCorrectEndBalance(float startBal, float mutation, float endBal) {
		float requiredEndBal = startBal + mutation;
		double temp = Math.round(requiredEndBal * 100.0) / 100.0;
		if((float) temp != endBal)
			return true;
		return false;
	}
	
	
	/**
	 * This method writes the failed transactions to the output file
	 * @param failedTransactions - all faled transaction array
	 * @param file - output file to store the records in
	 */
	public void writeToExcel(ArrayList<Transaction> failedTransactions, File file) {
		try {
	        FileWriter outputfile = new FileWriter(file); 
	  
	        CSVWriter writer = new CSVWriter(outputfile); 
	  
	        String[] header = { "Reference", "ClassDescription"}; 
	        writer.writeNext(header); 
	  
	        for(Transaction trans: failedTransactions) {
	        	String[] data1 = {new Integer(trans.getReference()).toString(), trans.getDescription()};
	        	writer.writeNext(data1); 
	        }
	  
	        writer.close(); 
	    } 
	    catch (IOException e) { 
	        // TODO Auto-generated catch block 
	        e.printStackTrace(); 
	    } 
	}
	
	/**
	 * This method is to generate output file for failed records while scanning csv file
	 * @param failedTransactions
	 */
	public void generateOutputFile(ArrayList<Transaction> failedTransactions) {
		 File file = new File("documents/failedTransactionReport.csv");
		 writeToExcel(failedTransactions, file);
		    
	}
	
	/**
	 * This method is to generate output file for failed records while scanning xml file
	 * @param failedTransactions
	 */
	public void generateOutputFileFromXML(ArrayList<Transaction> failedTransactions) {
		File file = new File("documents/failedTransactionReportFromXml.csv");
		 writeToExcel(failedTransactions, file);
	}
}
