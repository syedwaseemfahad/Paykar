package com.paykar.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "paykaraccount")
public class PaykarAccount {
public  PaykarAccount()
{
	
}
	@Column(name="NAME")
	public String name;
	@Column(name="MOBILENUMBER")
	public String mobileNumber;
	@Column(name="PASSWORD")
	public String password;
	@Column(name="EMAIL")
	public String email;
	@Id
	@Column(name="ACCOUNTNUMBER")
	public int accountNumber;
	@Column(name="BALANCE")
	public double balance;
	public PaykarAccount(String name, String mobileNumber, String password, String email, int accountNumber,
			double balance) {
		super();
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.email = email;
		this.accountNumber = accountNumber;
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "PaykarAccount [name=" + name + ", mobileNumber=" + mobileNumber + ", password=" + password + ", email="
				+ email + ", accountNumber=" + accountNumber + ", balance=" + balance + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
}
