package com.paykar;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.paykar.exceptions.AccountNumberDoesNotExistsException;
import com.paykar.exceptions.InsufficientBalanceException;
import com.paykar.exceptions.InvalidLoginCredentialsException;
import com.paykar.exceptions.MobileNumberRegisteredException;
import com.paykar.exceptions.ResourceNotFoundException;
import com.paykar.exceptions.UserNameExistsException;
import com.paykar.bean.PaykarAccount;
import com.paykar.bean.Transactions;
import com.paykar.service.PaykarServiceFunctions;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
@Transactional 
public class PaykarController {


	@Autowired
	public PaykarServiceFunctions service;
	
	
	// http request url for getting all accounts
	@GetMapping("/all")
	public List<PaykarAccount> getAll() {
		return service.getAll();
	}
	

	// http request url for getting specific account based on username
	@GetMapping("/all/{mobileNumber}")
	public PaykarAccount getAccount(@PathVariable String mobileNumber) throws ResourceNotFoundException,EmptyResultDataAccessException {
		
			return service.fetchPaykarAccount(mobileNumber);
		
	}
	
	@GetMapping("/all/{mobileNumber}/name")
	public String getName(@PathVariable String mobileNumber) throws ResourceNotFoundException {
			return service.fetchPaykarAccount(mobileNumber).getName();
		
	}
	// http request url for getting balance amount of an account
	@GetMapping("/all/{mobileNumber}/bal")
	public double getBalance(@PathVariable String mobileNumber) throws ResourceNotFoundException {
			return service.showBalance(mobileNumber);
		
	}

	// http request url to validate user
	@GetMapping("/all/mobileNumberexists/{mobileNumber}")
	public boolean mobileNumberExists(@PathVariable String mobileNumber)  {
		return service.mobileNumberExists(mobileNumber);
	}

	// http request url to validate user
	@GetMapping("/all/accexists/{accNum}")
	public boolean accExists(@PathVariable int accNum) {
		return service.accExists(accNum);
	}
	
	
	// http request url to validate user
	@GetMapping("/all/pass/{mobileNumber}/{password}")
	public boolean check(@PathVariable String mobileNumber, @PathVariable String password) throws ResourceNotFoundException, InvalidLoginCredentialsException {
		return service.check(mobileNumber, password);
	}
	
	// http request url to deposit amount
	@GetMapping("/all/deposit/{mobileNumber}/{amount}")
	public double deposit(@PathVariable String mobileNumber, @PathVariable double amount) throws ResourceNotFoundException, InvalidLoginCredentialsException {
			return service.deposit(mobileNumber, amount);
		
	}

	
	// http request url to withdraw amount
	@GetMapping("/all/withdraw/{mobileNumber}/{amount}")
	public double withdraw(@PathVariable String mobileNumber, @PathVariable double amount) throws ResourceNotFoundException, InsufficientBalanceException, InvalidLoginCredentialsException {
			return service.withdraw(mobileNumber, amount);
		
	}

	// http request url to transfer amount
	@GetMapping("/all/ft/{mobileNumber}/{mobileNumber2}/{amount}")
	public double fundTransfer(@PathVariable String mobileNumber, @PathVariable String mobileNumber2, @PathVariable double amount) throws ResourceNotFoundException, AccountNumberDoesNotExistsException, InsufficientBalanceException, InvalidLoginCredentialsException {
			return service.fundTransfer(mobileNumber,mobileNumber2,amount);
	}

	// http request url to print transactions
	@GetMapping("/all/transactions/{mobileNumber}")
	public List<Transactions> printTransactions(@PathVariable String mobileNumber) throws ResourceNotFoundException {
			return service.printTransactions(mobileNumber);
	
	}

	
	
	// http request url to SignUp
		@GetMapping("/all/SignUp/{mobileNumber}/{email}")
		public boolean SignUp(@PathVariable String mobileNumber,@PathVariable String email) throws ResourceNotFoundException, MobileNumberRegisteredException {
				return service.signUp(mobileNumber,email);
		
		}

		
		
	
	// http request url to add new account
	@PostMapping("/all/add")
	public int addAccount(@RequestBody PaykarAccount a) throws MobileNumberRegisteredException, UserNameExistsException {
		return service.addAccount(a);
	}


	// http request url for edit account
	@PutMapping("/all/{mobileNumber}")
	public PaykarAccount edit(@RequestBody PaykarAccount a, @PathVariable String mobileNumber) {
		service.editPaykarAccount(a);
		return a;
	}
	
	
	// http request url to delete account
	@DeleteMapping("/all/delete/{mobileNumber}")
	public void deleteAccount(@PathVariable String mobileNumber) throws ResourceNotFoundException {
		service.deleteById(mobileNumber);
	}

	
}
