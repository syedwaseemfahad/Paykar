package com.paykar.dao;
import java.time.LocalDateTime; 
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paykar.exceptions.MobileNumberRegisteredException;
import com.paykar.exceptions.AccountNumberDoesNotExistsException;
import com.paykar.exceptions.EmailRegisteredException;
import com.paykar.exceptions.InsufficientBalanceException;
import com.paykar.exceptions.InvalidLoginCredentialsException;
import com.paykar.exceptions.ResourceNotFoundException;
import com.paykar.exceptions.UserNameExistsException;

import com.paykar.bean.PaykarAccount;
import com.paykar.bean.Transactions;
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class


@Repository
public class PaykarDaoFunctions implements PaykarDao {
	
	
	
	@Autowired
	@PersistenceContext
	EntityManager entityManager;
	
	
	
		public int addAccount(PaykarAccount acc) {
        PaykarAccount p=new PaykarAccount(acc.getName(),acc.getMobileNumber(),acc.getPassword(),acc.getEmail(),acc.getAccountNumber(),acc.getBalance());
        entityManager.persist(p);
        return acc.getAccountNumber();
	}
		
		
		
		
	@Transactional
	@Override
	public List<PaykarAccount> getAll() {
		String qStr = "SELECT a from PaykarAccount a";
		TypedQuery<PaykarAccount> query = entityManager.createQuery(qStr, PaykarAccount.class);
		List<PaykarAccount> list=query.getResultList();
		return list;
		
	}
	
	
	
	
	
	@Transactional
	@Override
	public PaykarAccount fetchPaykarAccount(String mobileNumber) throws ResourceNotFoundException {
		String Str = "SELECT a FROM PaykarAccount a where mobileNumber=:mobileNumber";
		TypedQuery<PaykarAccount> query = entityManager.createQuery(Str, PaykarAccount.class);
		PaykarAccount a=null;
		try
		{
		query.setParameter("mobileNumber", mobileNumber);
	       a= query.getSingleResult();
		}
		catch(Exception e)
		{
			throw new ResourceNotFoundException();
		}
		return a;
	}
	
	
	
	
	
	@Transactional
	@Override
	public void editPaykarAccount(PaykarAccount a) {
		String Str = "SELECT a FROM PaykarAccount a where mobileNumber=:mobileNumber";
		TypedQuery<PaykarAccount> query = entityManager.createQuery(Str, PaykarAccount.class);
		query.setParameter("mobileNumber", a.getMobileNumber());
		PaykarAccount acc= query.getResultList().get(0);
		acc.setName(a.getName());
		acc.setPassword(a.getPassword());
		acc.setEmail(a.email);
		acc.setMobileNumber(a.mobileNumber);
		entityManager.merge(acc);
		
	}
	
	
	
	
	
	@Transactional
	@Override
	public void deleteById(String mobileNumber) throws ResourceNotFoundException {
		String s = "" +mobileNumber;
		String Str = "SELECT a.accountNumber FROM PaykarAccount a where a.mobileNumber=:mobileNumber";
		TypedQuery<Integer> query1 = entityManager.createQuery(Str, Integer.class);
		query1.setParameter("mobileNumber", mobileNumber);
		int num1 = query1.getSingleResult().intValue();
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaykarAccount> theQuery = currentSession.createQuery("DELETE FROM PaykarAccount where accountNumber=:num");
		theQuery.setParameter("num", num1);
		theQuery.executeUpdate();
	}
	
	
	
	
	
	@Transactional
	@Override
	public double showBalance(String mobileNumber) throws ResourceNotFoundException {
			PaykarAccount acc=fetchPaykarAccount(mobileNumber);
			return acc.getBalance();
	}
	
	
	
	
	
	
	
	@Transactional
	@Override
	public double deposit(String mobileNumber,double amount) throws ResourceNotFoundException, InvalidLoginCredentialsException {
		 LocalDateTime myDateObj = LocalDateTime.now();
		    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		    String date = myDateObj.format(myFormatObj);
		    try
		    {
		PaykarAccount acc=fetchPaykarAccount(mobileNumber);
		double bal=acc.getBalance()+amount;
		acc.setBalance(bal);
		entityManager.merge(acc);
		String id=getTransId();
		Transactions p=new Transactions(id,acc.getAccountNumber(),"Self-Credit",amount,date.toString(),acc.getBalance());
		entityManager.persist(p);
		
		return amount;		 
		    }
		    catch(Exception e)
		    {
		    	throw new ResourceNotFoundException();
		    }
	}
	
	
	
	
	
	
	
	@Transactional
	@Override
	public double withdraw(String mobileNumber,double amount) throws ResourceNotFoundException, InsufficientBalanceException, InvalidLoginCredentialsException {
		 LocalDateTime myDateObj = LocalDateTime.now();
		    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		    PaykarAccount acc=null;
		    String date = myDateObj.format(myFormatObj);
		    double bal=0.0;
		    try
		    {
		     acc=fetchPaykarAccount(mobileNumber);
			 bal=acc.getBalance()-amount;
		    }
		    catch(Exception e)
		    {
		    	throw new ResourceNotFoundException();
		    }
		if(bal>=0.0) {
			
			acc.setBalance(bal);
			entityManager.merge(acc);
			String id=getTransId();
			Transactions p=new Transactions(id,acc.getAccountNumber(),"Self-Debit",amount,date.toString(),acc.getBalance());
			entityManager.persist(p);
			return amount;
			}
		else
		{
			
		    	throw new InsufficientBalanceException("Insufficient Balance");
			
		    
		}
		    
	}
	
	
	
	
	
	
	
	@Transactional
	@Override
	public double fundTransfer(String mobileNumber,String mobileNumber2, double amount) throws ResourceNotFoundException, AccountNumberDoesNotExistsException, InsufficientBalanceException, InvalidLoginCredentialsException {
		 LocalDateTime myDateObj = LocalDateTime.now();
		    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		    String date = myDateObj.format(myFormatObj);
		PaykarAccount acc=fetchPaykarAccount(mobileNumber);
		double bal=acc.getBalance()-amount;
		if(bal>=0.0) 
		{
			
			if(mobileNumberExists(mobileNumber2)) 
			{
				acc.setBalance(bal);
	
				String command = "SELECT a FROM PaykarAccount a WHERE a.mobileNumber=:user";
				TypedQuery<PaykarAccount> query = entityManager.createQuery(command, PaykarAccount.class);
				query.setParameter("user", mobileNumber2);
	
				PaykarAccount acc2=query.getSingleResult();
				if(acc2!=null) 
				{
					acc.setBalance(bal);
					acc2.setBalance(acc2.getBalance()+amount);
					String id=getTransId();
					Transactions t1=new Transactions(id,acc.getAccountNumber(),"Debited to "+acc2.mobileNumber,amount,date.toString(),acc.getBalance());
					entityManager.persist(t1);
					String id2=getTransId();
					Transactions t2=new Transactions(id2,acc2.getAccountNumber(),"Credited from "+acc.mobileNumber,amount,date.toString(),acc2.getBalance());
					entityManager.persist(t2);
					
					entityManager.merge(acc);
					entityManager.merge(acc2);
				}
			}
			else 
			{
			throw new AccountNumberDoesNotExistsException("Invalid Phone Number");
			}
			return amount;
	
		}
		else 
		{
			throw new InsufficientBalanceException("Insufficient Balance");

		}
			 
					
	}
	
	
	
	
	
	
	
	
	
	
	
	@Transactional
	@Override
	public boolean accExists(int accNum2) {
		String command = "SELECT a.accountNumber FROM PaykarAccount a";
		TypedQuery<Integer> query = entityManager.createQuery(command, Integer.class);
		List<Integer> list=query.getResultList();
		if(list.contains(accNum2)) {
			return true;
		}
		else 
			return false;
			}
	
	
	
	
	
	
	
	
	
	
	
	@Transactional
	@Override
	public List<Transactions> printTransactions(String mobileNumber) throws ResourceNotFoundException {
		PaykarAccount acc=fetchPaykarAccount(mobileNumber);
		String qStr = "SELECT a FROM Transactions a WHERE a.accountNumber=:user order by id desc";
		TypedQuery<Transactions> query = entityManager.createQuery(qStr, Transactions.class);
		query.setParameter("user",acc.getAccountNumber());
		List<Transactions> list=query.getResultList();
		return list;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Transactional
	@Override
	public String getTransId() {

		String qStr = "SELECT max(a.id) from Transactions a";
		TypedQuery<String> query = entityManager.createQuery(qStr, String.class);
		String c=query.getSingleResult();
		if(c!=null) {
			String t1="TN"+String.format("%04d", Integer.parseInt(c.substring(2))+1);
			return t1;
		}
		else
			return "TN0001";
	}
	
	
	
	
	
	
	
	
	
	
	
	@Transactional
	@Override
	public boolean check(String mobileNumber, String password) throws ResourceNotFoundException, InvalidLoginCredentialsException{
		PaykarAccount acc;
		String command = "SELECT a FROM PaykarAccount a WHERE a.mobileNumber=:mobileNumber";
		TypedQuery<PaykarAccount> query = entityManager.createQuery(command, PaykarAccount.class);
		query.setParameter("mobileNumber", mobileNumber);
		try {
		acc=query.getResultList().get(0);
		}
		catch(Exception e) {
			throw new ResourceNotFoundException();
		}
		if(acc!=null && acc.getPassword().equals(password)) {
			return true;
		}
		else {
			throw new InvalidLoginCredentialsException("Hmm, that's not the right password. Please try again");
		}
	}
	
	
	
	
	
	
	
	
	
	@Transactional
	@Override
	public boolean mobileNumberExists(String mobileNumber){
		String command = "SELECT a.mobileNumber FROM PaykarAccount a";
		TypedQuery<String> query = entityManager.createQuery(command, String.class);
		List<String> list=query.getResultList();
		if(list.contains(mobileNumber)) {
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	@Transactional
	@Override
	public boolean emailExists(String email){
		String command = "SELECT a.email FROM PaykarAccount a";
		TypedQuery<String> query = entityManager.createQuery(command, String.class);
		List<String> list=query.getResultList();
		if(list.contains(email)) {
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	@Transactional
	@Override
	public int getAccNum() {
		String qStr = "SELECT count(a.accountNumber) from PaykarAccount a";
		TypedQuery<Long> query = entityManager.createQuery(qStr, Long.class);
		long c=query.getSingleResult();
		if(c!=0) {
			String qStr1 = "SELECT a.accountNumber FROM PaykarAccount a where a.accountNumber=(SELECT MAX(a.accountNumber) FROM PaykarAccount a)";
			TypedQuery<Integer> query1 = entityManager.createQuery(qStr1, Integer.class);
			int a=query1.getSingleResult();
			return ++a;
		}
		else
			return 111;
	
	}
	
	
	
	
	
	
	
	
	
	
	
	@Transactional
	@Override
	public boolean signUp(String mobileNumber,String email ) throws MobileNumberRegisteredException 
	{
		if(mobileNumberExists(mobileNumber))
		{
			throw new MobileNumberRegisteredException("Mobile Number Already Registered");
		}
		else if(emailExists(email))
		{
			throw new  MobileNumberRegisteredException("Email Already Registered");

		}
		else
		{
			return true;
		}
		
	}
	
	
}
