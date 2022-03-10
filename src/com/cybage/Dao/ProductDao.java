package com.cybage.Dao;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.cybage.Model.*;


public class ProductDao {

	public static final String URL = "jdbc:mysql://localhost:3307/PMS";
    public static final String Product = "root";
    public static final String PASSWORD = "1234";
    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    
    private static final String insertProductSql="insert into product "+" (pname,price) values " +"(?,?)";
    
    private static final String selectProductId="select pid,pname,price from product where pid=?";
    private static final String selectAllProducts="select * from product";
    private static final String deleteProduct="delete from product where pid =?;";
    private static final String updateProduct="update product set pname = ?, price=? where pid=?;";
    
    
    public  ProductDao() {
		
	}
    
    public static Connection getConnection() {
    	Connection connection=null;
    	
    	try {
    		Class.forName("jdbdDriver");
			connection=DriverManager.getConnection(URL,Product,PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
    	}
		
    public void insertProduct(Product product) throws SQLException{
		System.out.println(insertProductSql);
		try (Connection connection = getConnection()){
			
			PreparedStatement stmt = connection.prepareStatement(insertProductSql);
			
			stmt.setInt(1, product.getPid());
			stmt.setString(2, product.getPname());
			stmt.setInt(3, product.getPrice());
			
			System.out.println(stmt);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();		
		}
	}
    
 // select Product by id

    public Product selectProduct(int pid) {

    Product product = null;
    try (Connection connection = getConnection();

    PreparedStatement preparedStatement = connection.prepareStatement(selectProductId);) {

    preparedStatement.setInt(1, pid);

    System.out.println(preparedStatement);

    // Step 3: Execute the query or update query 
    ResultSet rs = preparedStatement.executeQuery();

    // Step 4: Process the ResultSet object
    while (rs.next()) {
    	String pname =rs.getString("pname");
    	int price = rs.getInt("price");
    	product = new Product(pid, pname, price);

    } 
    }catch (SQLException e) { 
    	printSQLException(e);

    }
    return product;
  }
    
    
 // select all product 
    public List<Product> selectAllUsers () {
    	List<Product> product = new ArrayList<>();

    // using try-with-resources to avoid closing resources (boiler plate code) // Step 1: Establishing a Connection 
    	try (Connection connection = getConnection();

    // Step 2:Create a statement using connection object
    			PreparedStatement preparedStatement = connection.prepareStatement (selectAllProducts) ;){
    			System.out.println(preparedStatement);

    // Step 3: Execute the query or update query 
    			ResultSet rs = preparedStatement.executeQuery();

    // Step 4: Process the ResultSet object. 
    			while (rs.next()) { 
    				int pid = rs.getInt ("pid") ; 
    				String pname = rs.getString("pname");
    				int price = rs.getInt("price");

    product.add(new Product(pid, pname, price));

    }

    } catch (SQLException e) { printSQLException(e);

    }

    return product;

    }

    // update product
 

    public boolean updateProduct (Product product) throws SQLException {

    boolean rowUpdated;

    try (Connection connection = getConnection();

    PreparedStatement statement = connection.prepareStatement (updateProduct);) {

    System.out.println ("updated Product: "+statement);

    statement.setString(2, product.getPname());

    statement.setInt (1, product.getPid());

    statement.setInt (3, product.getPrice());

    rowUpdated = statement.executeUpdate() > 0 ;

    }

    return rowUpdated;
    
    }
    
    //Delete Product
    
    public boolean deleteProduct(int pid) throws SQLException{
    	boolean rowDeleted = false;
    	try (Connection connection=getConnection();
    			PreparedStatement pst=connection.prepareStatement(deleteProduct);){
    				pst.setInt(1,pid);
    				rowDeleted = pst.executeUpdate()>0;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
		return rowDeleted;
    	
		
	}
    
    private void printSQLException (SQLException ex) {

    	for(Throwable e: ex) {
    		if (e instanceof SQLException) {
    	}
    		e.printStackTrace(System.err);

    	System.err.println("SQLState: "+ ((SQLException) e).getSQLState());
    	System.err.println("Error Code: " + ((SQLException) e).getErrorCode());

    	System.err.println("Message: "+e.getMessage()); 
    	Throwable t=ex.getCause();

    	while (t != null) {

    	System.out.println("Cause: " + t);

    	t = t.getCause();
    	}
    	}
    }

}
