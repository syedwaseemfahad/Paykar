package com.paykar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.paykar.bean.PaykarAccount;
import com.paykar.exceptions.MobileNumberRegisteredException;
import com.paykar.exceptions.ResourceNotFoundException;
import com.paykar.service.PaykarServiceFunctions;
import com.paykar.exceptions.AccountNumberDoesNotExistsException;
import com.paykar.exceptions.InsufficientBalanceException ;
import com.paykar.exceptions.InvalidLoginCredentialsException;

@SpringBootTest
class PaykarApplicationTests {
	@Autowired
	public PaykarServiceFunctions service;

	@Test
	public void test1() throws  InsufficientBalanceException, ResourceNotFoundException, InvalidLoginCredentialsException {
		double amt=service.deposit("6050505099",100);
		assertEquals(100,amt);
	}
	
	@Test
	public void test2() throws MobileNumberRegisteredException {
		assertThrows(Exception.class,()->
		{
			service.addAccount(new PaykarAccount("Hariii","7878787878","kjhgjkl","12000@gmail.com",0,0));
		});
	}
	@Test
	public void test3() throws ResourceNotFoundException, InvalidLoginCredentialsException, InsufficientBalanceException {
		assertThrows(InsufficientBalanceException.class,()->
		{
			service.withdraw("6050505099",50000);
		});
	}
	@Test
	public void test3a() throws ResourceNotFoundException, InsufficientBalanceException, InvalidLoginCredentialsException {
		double amt=service.withdraw("6050505099",150);
		assertEquals(150,amt);
	}
	public void test3b() throws ResourceNotFoundException, AccountNumberDoesNotExistsException, InsufficientBalanceException {
		assertThrows(AccountNumberDoesNotExistsException.class,()->
		{
			double amount=service.withdraw("9876533218",100);
		});
	}
	
	@Test
	public void test4() throws ResourceNotFoundException, InvalidLoginCredentialsException, InsufficientBalanceException {
		double amt=service.deposit("6050505099",200);
		assertEquals(200,amt);
	}
	
	@Test
	public void test4a() throws ResourceNotFoundException, InvalidLoginCredentialsException, InsufficientBalanceException {
		assertThrows(ResourceNotFoundException.class,()->
		{
			double amount=service.deposit("9876233218",100);
		});
	}
	@Test
	public void test5() throws ResourceNotFoundException, InvalidLoginCredentialsException, InsufficientBalanceException, AccountNumberDoesNotExistsException {
		
			double amount=service.fundTransfer("6050505099","9876543218",300);
			assertEquals(300,amount);

		
	}
	@Test
	public void test5b() throws ResourceNotFoundException, InvalidLoginCredentialsException, InsufficientBalanceException, AccountNumberDoesNotExistsException {
		assertThrows(AccountNumberDoesNotExistsException.class,()->
		{
			double amount=service.fundTransfer("6050505099","9876543918",300);

		});
	}
	@Test
	public void test6() throws ResourceNotFoundException, InvalidLoginCredentialsException {
		double amt=service.showBalance("7878787878");
		assertEquals(0,amt);
	}
	@Test
	public void test6a() throws ResourceNotFoundException, InvalidLoginCredentialsException {
		assertThrows(ResourceNotFoundException.class,()->
		{
		double amt=service.showBalance("7878787879");
		});
		
	}
	@Test
	public void test7() throws ResourceNotFoundException, InvalidLoginCredentialsException, InsufficientBalanceException {
		assertThrows(InsufficientBalanceException.class,()->
		{
			double amount=service.fundTransfer("6050505099","9876543218",50000);
		});
	}
	@Test
	public void test8() throws ResourceNotFoundException, AccountNumberDoesNotExistsException, InsufficientBalanceException {
		assertThrows(AccountNumberDoesNotExistsException.class,()->
		{
			service.fundTransfer("6050505099","9876533218",100);
		});
	}
	


}
