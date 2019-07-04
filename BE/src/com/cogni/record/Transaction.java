package com.cogni.record;

public class Transaction {
	private int reference;
	private String accNumber;
	private float startBal;
	private float mutation;
	private float endBal;
	private String description;
	public int getReference() {
		return reference;
	}
	public void setReference(int reference) {
		this.reference = reference;
	}
	public String getAccNumber() {
		return accNumber;
	}
	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}
	public float getStartBal() {
		return startBal;
	}
	public void setStartBal(float startBal) {
		this.startBal = startBal;
	}
	public float getMutation() {
		return mutation;
	}
	public void setMutation(float mutation) {
		this.mutation = mutation;
	}
	public float getEndBal() {
		return endBal;
	}
	public void setEndBal(float endBal) {
		this.endBal = endBal;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
