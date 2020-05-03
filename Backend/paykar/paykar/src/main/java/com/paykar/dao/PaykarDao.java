package com.paykar.dao;

import java.util.List;


import com.paykar.bean.PaykarAccount;
import com.paykar.bean.Transactions;
import com.paykar.exceptions.AccountNumberDoesNotExistsException;
import com.paykar.exceptions.InsufficientBalanceException;
import com.paykar.exceptions.InvalidLoginCredentialsException;
import com.paykar.exceptions.MobileNumberRegisteredException;
import com.paykar.exceptions.ResourceNotFoundException;
import com.paykar.exceptions.UserNameExistsException;

public interface PaykarDao {

	public int addAccount(PaykarAccount acc)throws MobileNumberRegisteredException, UserNameExistsException; ;
	public double showBalance(String mobileNumber) throws ResourceNotFoundException;;
	public double deposit(String mobileNumber,double amount)  throws ResourceNotFoundException, InvalidLoginCredentialsException;
	public double withdraw(String mobileNumber,double amount)throws ResourceNotFoundException, InsufficientBalanceException, InvalidLoginCredentialsException;
	public double fundTransfer(String mobileNumber,String mobileNumber2, double amount)throws ResourceNotFoundException, AccountNumberDoesNotExistsException, InsufficientBalanceException, InvalidLoginCredentialsException;
	public List<Transactions> printTransactions(String un) throws ResourceNotFoundException;

	
	public List<PaykarAccount> getAll();
	public PaykarAccount fetchPaykarAccount(String mobileNumber)  throws ResourceNotFoundException;
	public void editPaykarAccount(PaykarAccount a);
	public void deleteById(String mobileNumber) throws ResourceNotFoundException;

	
	public boolean check(String mobileNumber,String pw) throws ResourceNotFoundException, InvalidLoginCredentialsException;
	public boolean mobileNumberExists(String mobileNumber) ;
	public boolean accExists(int accNum2);
	public String  getTransId();
	public int getAccNum();
	 public boolean emailExists(String email);
	 public boolean signUp(String mobileNumber,String email ) throws MobileNumberRegisteredException;

}
