package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		//-------------------------------- DEPARTEMENT ------------------------------------
		
		DepartmentDao depDao = DaoFactory.createDepartmentDao();

		System.out.println("------------ TEST 1: department Insert -------------");
		Department dep = new Department(null, "Executive");
		
		// dep2.setName("Library");
		depDao.insert(dep);
		int lastId = dep.getId();
		System.out.println("Inserted! New id = " + lastId);
		System.out.println();

		System.out.println("------------ TEST 2: department Update -------------");
		dep.setName("Human Resources");
		dep.setId(5);
		depDao.update(dep);
		System.out.println("Updated succeeded");
		System.out.println();

		System.out.println("------------ TEST 3: department Delete -------------");
		depDao.deleteById(lastId);
		System.out.println("Delete succeeded");
		System.out.println();
		
		System.out.println("------------ TEST 4: department findById -------------");
		Department dep2 = depDao.findById(4);		
		System.out.println(dep2);
		System.out.println();		
		 
		System.out.println("------------ TEST 5: department findAll -------------");			
		List<Department> list = depDao.findAll();
		for(Department obj : list) {		
			System.out.println(obj);
		}	

	}

}
