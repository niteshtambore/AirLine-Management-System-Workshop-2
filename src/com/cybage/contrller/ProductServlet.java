package com.cybage.contrller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cybage.Dao.ProductDao;
import com.cybage.Model.Product;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDao productDao;
       

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		productDao=new ProductDao();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action =request.getServletPath();
		
		switch (action) {
		case "/new":
			try {
				showNewForm(request, response);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			break;
			
		case "/insert":
			try {
				insertProduct(request, response);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			break;
			
		case "/delete":
			try {
				deleteProduct(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "update":
			try {
				showEditForm(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		break;
	
		default:
			
			break;
		}
	}
		
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException ,SQLException
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("productForm.jsp");
			dispatcher.forward(request, response);
		}
			
	private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException 
	{

			String pname=request.getParameter("pname");
			int price=request.getIntHeader("price");
			Product newProduct = new Product(pname,price);
			productDao.insertProduct(newProduct);
			response.sendRedirect("list");
		}
	
	// edit

	private void showEditForm (HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
	int pid = Integer.parseInt(request.getParameter("pid"));

	Product existingProduct;

	try {

	existingProduct = productDao. selectProduct(pid);

	RequestDispatcher dispatcher = request.getRequestDispatcher ("productForm.jsp") ; 
		request.setAttribute("product", existingProduct);

	dispatcher. forward (request, response);

	} catch (Exception e) {

	// TODO Auto-generated catch block e.printStackTrace();

	}
	}
	
	//update
//	private boolean updateProduct(Product product)throws SQLException{
//		boolean rowUpdated;
//		try(Connection connection=getConnection();
//				PreparedStatement pst = connection.prepareStatement(updateProduct);)
		
//		return rowUpdated;
		
//	}
	
	//delete
	
	private void deleteProduct(HttpServletRequest request,HttpServletResponse response)throws SQLException,IOException,ServletException{
		int pid=Integer.parseInt(request.getParameter("id"));
		try {
			productDao.deleteProduct(pid);
		} catch (Exception e) {
			// TODO: handle exception
		}
		response.sendRedirect("list");
	}
	
	
}


	
	
