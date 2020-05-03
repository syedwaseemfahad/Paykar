package com.paykar.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Transactions implements Serializable {
	@Id
	@Column(name="ID")
	public String id;
	@Column(name="ACCountNUMber")
	public int accountNumber;
	@Column(name="TYPE")
	public String type;
	@Column(name="AMOUNT")
	public double amount;
	@Column(name="TIME")
	public String time;

	@Column(name="BALANCE")
	public double balance;
	public String getId() {
		return id;
	}
	public Transactions()
	{
		
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getAccNum() {
		return accountNumber;
	}
	public void setAccNum(int accNum) {
		this.accountNumber = accNum;
	}
	public String getType() {
		return type;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	@Override
	public String toString() {
		return "Transactions [id=" + id + ", accountNumber=" + accountNumber + ", type=" + type + ", amount=" + amount
				+ ", time=" + time + ", balance=" + balance + "]";
	}
	public Transactions(String id, int accountNumber, String type, double amount, String time, double balance) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.type = type;
		this.amount = amount;
		this.time = time;
		this.balance = balance;
	}
	
	
	
}
