package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Department dep = new Department(1, "Stock");
		System.out.println("------------ TEST 0: department object -------------");
		System.out.println(dep);
		System.out.println();
		
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		//sellerDao.findById(3);
		Seller seller = sellerDao.findById(3);
		//Seller seller = new Seller(21, "Diego", "diego@gmail.com", new Date(), 3000.00, dep);
				
		System.out.println("------------ TEST 1: seller findById -------------");		
		System.out.println(seller);
		System.out.println();
		
		System.out.println("------------ TEST 2: seller findByDepartment -------------");		
		List<Seller> list = sellerDao.findByDepartment(dep);
		for(Seller obj : list) {		
			System.out.println(obj);
		}
		System.out.println();		
		
		System.out.println("------------ TEST 3: seller findByAll -------------");	
		list = sellerDao.findAll();
		for(Seller obj : list) {		
			System.out.println(obj);
		}
		System.out.println();
		
		System.out.println("------------ TEST 4: seller Insert -------------");
		Seller newSeller = new Seller (null, "Greg", "greg@gmail.com", new Date(), 3500.00, dep);
		sellerDao.insert(newSeller);
		int lastId = newSeller.getId();
		System.out.println("Inserted! New id = " + lastId);
		System.out.println();

		System.out.println("------------ TEST 5: seller Update -------------");		
		seller = sellerDao.findById(5);
		seller.setName("Cartman Plus");
		sellerDao.update(seller);
		System.out.println("Updated succeeded!");
		System.out.println();		
		
		System.out.println("------------ TEST 6: seller Delete -------------");
		sellerDao.deleteById(11);
		System.out.println("Deleted succeeded!");
		System.out.println();
	}

}
