package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
	
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("INSERT INTO seller "
									 + "(Name, Email, BirthDate, BaseSalary, DepartmentId)" 
									 + "VALUES "									 
									 + "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new java.sql.Date(obj.getBirthdate().getTime()));;
			ps.setDouble(4, obj.getBaseSalary());
			ps.setInt(5, obj.getDepartment().getId());			
			
			if(ps.executeUpdate() > 0 ) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}	
		}
		
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}	
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE seller "
									 + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
									 + "WHERE Id = ?");
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new java.sql.Date(obj.getBirthdate().getTime()));;
			ps.setDouble(4, obj.getBaseSalary());
			ps.setInt(5, obj.getDepartment().getId());
			ps.setInt(6, obj.getId());			
		}		
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
			ps.setInt(1, id);
			
			ps.executeUpdate();
				
		}		
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}		
		
	}	

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "+ 
									   "FROM seller INNER JOIN department "+ 
									   "ON seller.DepartmentId = department.Id "+ 
									   "WHERE seller.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()){
				Department dep = new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
				Seller sel = new Seller(rs.getInt("Id"), rs.getString("Name"), rs.getString("Email"), rs.getDate("BirthDate"), rs.getDouble("BaseSalary"), dep);
				return sel;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}	
		
	}
	
	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
									 + "FROM seller INNER JOIN department "
									 + "ON seller.DepartmentId = department.Id "
									 + "ORDER BY Name ");
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()){
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
					map.put(rs.getInt("DepartmentId"), dep);
				}			
				
				Seller sel = new Seller(rs.getInt("Id"), rs.getString("Name"), rs.getString("Email"), rs.getDate("BirthDate"), rs.getDouble("BaseSalary"), dep);;
				list.add(sel);				
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
									 + "FROM seller INNER JOIN department "
									 + "ON seller.DepartmentId = department.Id "
									 + "WHERE DepartmentId = ? "
									 + "ORDER BY Name ");
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()){
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
					map.put(rs.getInt("DepartmentId"), dep);
				}			
				
				Seller sel = new Seller(rs.getInt("Id"), rs.getString("Name"), rs.getString("Email"), rs.getDate("BirthDate"), rs.getDouble("BaseSalary"), dep);;
				list.add(sel);				
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}	
	}		

}
