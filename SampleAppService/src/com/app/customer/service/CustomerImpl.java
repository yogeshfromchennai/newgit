package com.app.customer.service;

import com.app.customer.dao.CustomerDao;
import com.app.customer.vo.Customer;
import com.app.framework.exception.HexApplicationException;
import com.app.customer.util.BootStrapper;
import java.util.List;

public class CustomerImpl implements ICustomer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -834079942480093517L;
	private CustomerDao customerDao;

	public CustomerImpl() {
		this.customerDao = (CustomerDao) BootStrapper.getService().getBean("CustomerDao");
	}

	public String insert(Customer object) throws HexApplicationException {
	
		System.out.println("inside insert in Service ");			
		return customerDao.insert(object);
		
	}


	public String deleteAll(List<Customer> customers) throws HexApplicationException {
	
		System.out.println("inside deleteAll in Service ");		
		return customerDao.deleteAll(customers);
	
	}

	public String update(Customer customer) throws HexApplicationException {
	
		System.out.println("inside update in Service ");
		return customerDao.update(customer);
	
	}

	public List<Customer> getAllCustomer() throws HexApplicationException {
	
		System.out.println("inside getAllCustomer in Service ");	
		return customerDao.getAllCustomer();
	
	}

	public List<Customer> search(String searchValue, String searchColumn) throws HexApplicationException {

		System.out.println("Entering into service implementation : " + searchValue + "***" +searchColumn );
				
		return customerDao.search(searchValue, searchColumn);
	
	}


}
