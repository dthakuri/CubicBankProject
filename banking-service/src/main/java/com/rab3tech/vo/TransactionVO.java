package com.rab3tech.vo;

public class TransactionVO {
	private int payeeID;
	private String debitAccountNumber;
	private String description;
	private String customerId;
	private float transferAmount;
	
	public int getPayeeID() {
		return payeeID;
	}
	public void setPayeeID(int payeeID) {
		this.payeeID = payeeID;
	}
	public String getDebitAccountNumber() {
		return debitAccountNumber;
	}
	public void setDebitAccountNumber(String debitAccountNumber) {
		this.debitAccountNumber = debitAccountNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public float getTransferAmount() {
		return transferAmount;
	}
	public void setTransferAmount(float transferAmount) {
		this.transferAmount = transferAmount;
	}
	@Override
	public String toString() {
		return "TransactionVO [payeeID=" + payeeID + ", debitAccountNumber=" + debitAccountNumber + ", description="
				+ description + ", customerId=" + customerId + ", transferAmount=" + transferAmount + "]";
	}
	
}
