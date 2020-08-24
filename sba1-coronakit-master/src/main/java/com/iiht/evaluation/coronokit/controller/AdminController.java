package com.iiht.evaluation.coronokit.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iiht.evaluation.coronokit.dao.ProductMasterDao;
import com.iiht.evaluation.coronokit.exception.ProductException;
import com.iiht.evaluation.coronokit.model.ProductMaster;
import com.iiht.evaluation.coronokit.service.ProductService;
import com.iiht.evaluation.coronokit.service.ProductServiceImpl;



@WebServlet({ "/adminlogin", "/listproducts", "/newproduct","/insertproduct", "/deleteproduct", "/editproduct", "/updateproduct", "/logout" })
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductMasterDao productMasterDao;
	private ProductService productService;
	
	
	public void setProductMasterDao(ProductMasterDao productMasterDao) {
		this.productMasterDao = productMasterDao;
	}

	public void init(ServletConfig config) {
		String jdbcURL = config.getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername = config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = config.getServletContext().getInitParameter("jdbcPassword");

		this.productMasterDao = new ProductMasterDao(jdbcURL, jdbcUsername, jdbcPassword);
		
		productService=new ProductServiceImpl();

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getServletPath();
		String viewName = "";
		try {
			switch (url) {
			case "/adminlogin" : 
				viewName = adminLogin(request, response);
				break;
			case "/newproduct":
				viewName = showNewProductForm(request, response);
				break;
			case "/insertproduct":
				viewName = insertProduct(request, response);
				break;
			case "/deleteproduct":
				viewName = deleteProduct(request, response);
				break;
			case "/editproduct":
				viewName = showEditProductForm(request, response);
				break;
			case "/updateproduct":
				viewName = insertProduct(request, response);
				break;
			case "/listproducts":
				viewName = listAllProducts(request, response);
				break;	
			case "/logout":
				viewName = adminLogout(request, response);
				break;	
			default : viewName = "notfound.jsp"; break;		
			}
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		RequestDispatcher dispatch = 
					request.getRequestDispatcher(viewName);
		dispatch.forward(request, response);
		
		
	}

	private String adminLogout(HttpServletRequest request, HttpServletResponse response) {
		String view="";
		HttpSession session = request.getSession();
		session.removeAttribute("adminUser");
		session.removeAttribute("newUser");
		view="index.jsp";
		return view;		
	}

	private String listAllProducts(HttpServletRequest request, HttpServletResponse response) {
		String view = "";

		try {
			List<ProductMaster> products = productService.getAllProducts(productMasterDao);
			request.setAttribute("products", products);
			view = "listproducts.jsp";
		} catch (ProductException e) {
			request.setAttribute("errMsg", e.getMessage());
			view = "errorPage.jsp";
		}
		return view;
	}

	private String showEditProductForm(HttpServletRequest request, HttpServletResponse response) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		String view = "";

		try {
			ProductMaster product = productService.getProduct(pid,productMasterDao);
			request.setAttribute("product", product);
			request.setAttribute("isNew", false);
			view = "newproduct.jsp";
		} catch (ProductException e) {
			request.setAttribute("errMsg", e.getMessage());
			view = "errorPage.jsp";
		}

		return view;
	}

	private String deleteProduct(HttpServletRequest request, HttpServletResponse response) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		String view = "";

		try {
			productService.deleteProduct(pid,productMasterDao);			
			request.setAttribute("msg","Product deleted");
			view = "adminHome.jsp";
		} catch (ProductException e) {
			request.setAttribute("errMsg", e.getMessage());
			view = "errorPage.jsp";
		}

		return view;
	}

	private String insertProduct(HttpServletRequest request, HttpServletResponse response) {
		ProductMaster product = new ProductMaster();
		product.setProductName(request.getParameter("productName"));
		product.setCost(request.getParameter("cost"));
		product.setProductDescription(request.getParameter("productDesc"));

		String view = "";
		
		try {
			if (request.getServletPath().equals("/insertproduct")) {
				product.setId(productMasterDao.getMaxProductId()+1);
				productService.validateAndAdd(product,productMasterDao);
			} else if (request.getServletPath().equals("/updateproduct")) {
				product.setId(Integer.parseInt(request.getParameter("productId")));
				productService.validateAndSave(product,productMasterDao);
			}
			request.setAttribute("msg", "Product Saved Successfully");
			view = "adminHome.jsp";
		} catch (ProductException e) {
			request.setAttribute("errMsg", e.getMessage());
			view = "errorPage.jsp";
		}
		return view;
	}

	private String showNewProductForm(HttpServletRequest request, HttpServletResponse response) {
		ProductMaster product = new ProductMaster();
		request.setAttribute("product", product);
		request.setAttribute("isNew", true);
		String view = "newproduct.jsp";
		return view;
	}

	private String adminLogin(HttpServletRequest request, HttpServletResponse response) {
		String userId=(String)request.getParameter("loginid");
		String password=(String)request.getParameter("password");
		String view = "";
		
		HttpSession session = request.getSession();
		try {
			if(userId==null && password==null)
			{
				request.setAttribute("msg", "User Id and Password not provided");
			}else if(userId==null && password!=null)
			{
				request.setAttribute("msg", "User Id not provided");
			}else if(userId!=null && password==null)
			{
				request.setAttribute("msg", "Password not provided");
			}else if(userId.trim().equals("") || password.trim().equals(""))
			{
				request.setAttribute("msg", "User Id or Password is empty");
				
			}else if( !userId.equals("admin") || !password.equals("admin"))
			{
				request.setAttribute("msg", "User Id or Password is incorrect");
			}else
			{
				session.setAttribute("adminUser", "true");
				request.setAttribute("msg", "Logged in as admin successfully!!!");
				view = "adminHome.jsp";
			}
		} catch (Exception e) {
			request.setAttribute("errMsg", e.getMessage());
			view = "errorPage.jsp";
		}

		return view;
	}

	
}