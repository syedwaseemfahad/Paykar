package com.paykar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paykar.exceptions.MobileNumberRegisteredException;
import com.paykar.exceptions.AccountNumberDoesNotExistsException;
import com.paykar.exceptions.InsufficientBalanceException;
import com.paykar.exceptions.InvalidLoginCredentialsException;
import com.paykar.exceptions.ResourceNotFoundException;
import com.paykar.exceptions.UserNameExistsException;
import com.paykar.bean.PaykarAccount;
import com.paykar.bean.Transactions;
import com.paykar.dao.PaykarDaoFunctions;



@Service
public class PaykarServiceFunctions implements PaykarService {
	
	@Autowired
	PaykarDaoFunctions paykarDao=new PaykarDaoFunctions();
	String s;
	int t;
	public PaykarServiceFunctions(){
		
	}
	@Override
	public int addAccount(PaykarAccount account) throws MobileNumberRegisteredException, UserNameExistsException {
		account.setAccountNumber(paykarDao.getAccNum());
		t= paykarDao.addAccount(account);
		return t;
	}

	@Override
	public double showBalance(String mobileNumber) throws ResourceNotFoundException {

		return paykarDao.showBalance(mobileNumber);
	}

	@Override
	public double deposit(String mobileNumber,double amount) throws ResourceNotFoundException, InvalidLoginCredentialsException  {
		return paykarDao.deposit(mobileNumber,amount);
	}

	@Override
	public double withdraw(String mobileNumber,double amount) throws ResourceNotFoundException, InsufficientBalanceException, InvalidLoginCredentialsException {
		return paykarDao.withdraw(mobileNumber,amount);
	}

	@Override
	public double fundTransfer(String mobileNumber,String mobileNumber2, double amount)  throws ResourceNotFoundException, AccountNumberDoesNotExistsException, InsufficientBalanceException, InvalidLoginCredentialsException {
		return paykarDao.fundTransfer(mobileNumber,mobileNumber2,amount);
	}

	@Override
	public List<Transactions> printTransactions(String mobileNumber) throws ResourceNotFoundException  {
		List<Transactions> transactionsList= paykarDao.printTransactions(mobileNumber);
		return transactionsList;
	}
	
	@Override
	public boolean check(String mobileNumber,String password)throws ResourceNotFoundException, InvalidLoginCredentialsException {
		return paykarDao.check(mobileNumber,password);
	}
	
	@Override
	public boolean mobileNumberExists(String mobileNumber) {
		return paykarDao.mobileNumberExists(mobileNumber);
	}

	@Override
	public boolean accExists(int accountNumber2){
		return paykarDao.accExists(accountNumber2);
	}
	
	@Override
	public List<PaykarAccount> getAll() {
		return paykarDao.getAll();
	}

	@Override
	public PaykarAccount fetchPaykarAccount(String mobileNumber) throws ResourceNotFoundException {
		PaykarAccount list=paykarDao.fetchPaykarAccount(mobileNumber);
		return list;
		
	}
	@Override
	public void editPaykarAccount(PaykarAccount account)  {
		paykarDao.editPaykarAccount(account);
		
	}
	@Override
	public void deleteById(String mobileNumber) throws ResourceNotFoundException {
		paykarDao.deleteById(mobileNumber);
	}
	@Override
	 public boolean emailExists(String email)
	 {
		return paykarDao.emailExists(email);
	 }
	@Override
	 public boolean signUp(String mobileNumber,String email ) throws MobileNumberRegisteredException
	 {
		 return paykarDao.signUp(mobileNumber, email);
	 }

	

}
