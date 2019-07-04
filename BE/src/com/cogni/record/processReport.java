package com.cogni.record;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class processReport {

	public static void main(String[] args) {

		processReport myreport = new processReport();
		String csvFile = "documents/records.csv";
		String xmlFile = "documents/records.xml";
		
		/**
		 * When the application will be used in real time, we can have the files from server.
		 * We can have a if condition for the filetype and accordingly we can call one of the below methods.
		 */
		//to generate report from the CSV file
		myreport.parseCSV(csvFile);
		
		//to generate report from SML file
		myreport.parseXML(xmlFile);

	}

	/**
	 * This method will generate the output file with failed transactions.
	 * @param csvFile - path to the file
	 */
	public void parseCSV(String csvFile) {

		ManipulationsInData transManipilation = new ManipulationsInData();
		String line = "";
		String cvsSplitBy = ",";
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		ArrayList<Transaction> failedTransactions = new ArrayList<Transaction>();

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			while ((line = br.readLine()) != null) {

				if (!line.split(cvsSplitBy)[0].toString().equals("Reference")) {
					String[] transaction = line.split(cvsSplitBy);
					Transaction trans = new Transaction();
					trans.setReference(Integer.parseInt(transaction[0]));
					trans.setAccNumber(transaction[1]);
					trans.setDescription(transaction[2]);
					trans.setStartBal(Float.valueOf(transaction[3].trim()).floatValue());
					trans.setMutation(Float.valueOf(transaction[4].trim()).floatValue());
					trans.setEndBal(Float.valueOf(transaction[5].trim()).floatValue());

					boolean isDuplicate = transManipilation.checkDuplicates(trans.getReference(), transactions);
					boolean isCorrectEndBalance = transManipilation.isCorrectEndBalance(trans.getStartBal(),trans.getMutation(), trans.getEndBal());

					if (isDuplicate || isCorrectEndBalance) {
						failedTransactions.add(trans);
					} else {
						transactions.add(trans);
					}
				}

			}
			transManipilation.generateOutputFile(failedTransactions);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will generate the output file with failed transactions which will be generated from the provided xml file.
	 * @param xmlFile - path to the file
	 */
	public void parseXML(String xmlFile) {

		ManipulationsInData transManipilation = new ManipulationsInData();
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		ArrayList<Transaction> failedTransactions = new ArrayList<Transaction>();
		try {

			File fXmlFile = new File(xmlFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("record");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					Transaction trans = new Transaction();
					trans.setReference(Integer.parseInt(eElement.getAttribute("reference")));
					trans.setAccNumber(eElement.getElementsByTagName("accountNumber").item(0).getTextContent());
					trans.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
					trans.setStartBal(Float.valueOf(eElement.getElementsByTagName("startBalance").item(0).getTextContent().trim()).floatValue());
					trans.setMutation(Float.valueOf(eElement.getElementsByTagName("mutation").item(0).getTextContent().trim()).floatValue());
					trans.setEndBal(Float.valueOf(eElement.getElementsByTagName("endBalance").item(0).getTextContent().trim()).floatValue());

					boolean isDuplicate = transManipilation.checkDuplicates(trans.getReference(), transactions);
					boolean isCorrectEndBalance = transManipilation.isCorrectEndBalance(trans.getStartBal(),
							trans.getMutation(), trans.getEndBal());

					if (isDuplicate || isCorrectEndBalance) {
						failedTransactions.add(trans);
					} else {
						transactions.add(trans);
					}

				}

				transManipilation.generateOutputFileFromXML(failedTransactions);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
