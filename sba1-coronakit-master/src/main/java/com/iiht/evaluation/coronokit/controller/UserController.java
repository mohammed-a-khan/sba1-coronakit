package com.iiht.evaluation.coronokit.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iiht.evaluation.coronokit.dao.KitDao;
import com.iiht.evaluation.coronokit.dao.ProductMasterDao;
import com.iiht.evaluation.coronokit.exception.ProductException;
import com.iiht.evaluation.coronokit.model.CoronaKit;
import com.iiht.evaluation.coronokit.model.KitDetail;
import com.iiht.evaluation.coronokit.model.KitItemDetails;
import com.iiht.evaluation.coronokit.model.OrderSummary;
import com.iiht.evaluation.coronokit.model.ProductMaster;
import com.iiht.evaluation.coronokit.service.KitService;
import com.iiht.evaluation.coronokit.service.KitServiceImpl;
import com.iiht.evaluation.coronokit.service.ProductService;
import com.iiht.evaluation.coronokit.service.ProductServiceImpl;

@WebServlet({ "/newuser", "/insertuser","/userlogout","/addnewitem", "/showproducts", "/showkit", "/deleteitem", "/placeorder", "/saveorder", "/ordersummary"})
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private KitDao kitDAO;
	private ProductMasterDao productMasterDao;
	private KitService kitService;
	private ProductService productService;

	public void setKitDAO(KitDao kitDAO) {
		this.kitDAO = kitDAO;
	}

	public void setProductMasterDao(ProductMasterDao productMasterDao) {
		this.productMasterDao = productMasterDao;
	}

	public void init(ServletConfig config) {
		String jdbcURL = config.getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername = config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = config. getServletContext().getInitParameter("jdbcPassword");
		
		this.kitDAO = new KitDao(jdbcURL, jdbcUsername, jdbcPassword);
		this.productMasterDao = new ProductMasterDao(jdbcURL, jdbcUsername, jdbcPassword);
		kitService=new KitServiceImpl();
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
			case "/newuser":
				viewName = showNewUserForm(request, response);
				break;
			case "/insertuser":
				viewName = insertNewUser(request, response);
				break;
			case "/showproducts":
				viewName = showAllProducts(request, response);
				break;	
			case "/addnewitem":
				viewName = addNewItemToKit(request, response);
				break;
			case "/deleteitem":
				viewName = deleteItemFromKit(request, response);
				break;
			case "/showkit":
				viewName = showKitDetails(request, response);
				break;
			case "/placeorder":
				viewName = showPlaceOrderForm(request, response);
				break;
			case "/saveorder":
				viewName = saveOrderForDelivery(request, response);
				break;	
			case "/ordersummary":
				viewName = showOrderSummary(request, response);
				break;	
			case "/userlogout":
				viewName = userLogout(request, response);
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

	private String userLogout(HttpServletRequest request, HttpServletResponse response) {
		String view="";
		HttpSession session = request.getSession();
		session.removeAttribute("newUser");
		session.removeAttribute("coronaKitId");
		view="index.jsp";
		return view;		
	}
	
	private String showOrderSummary(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();

		String view = "";

		if(session.getAttribute("orderFinalized") != null)
		{
			if(!(boolean)session.getAttribute("orderFinalized"))
			{
				request.setAttribute("msg", "No confirmed order to display Order summary");
				
				view = "ordersummary.jsp";
			}
			else
			{
				try {
					
					int cId=(int) session.getAttribute("coronaKitId");
					CoronaKit coronaKit=kitService.getCoronaKit(cId, kitDAO);
					List<KitItemDetails> kitItemDetails = kitService.getAllKitItems(cId, kitDAO);
					OrderSummary orderSummary=new OrderSummary(coronaKit, kitItemDetails);
					session.setAttribute("orderSummary", orderSummary);
					view = "ordersummary.jsp";
				} catch (ProductException e) {
					request.setAttribute("errMsg", e.getMessage());
					view = "errorPage.jsp";
				}
			}
		}
		else
		{
			request.setAttribute("msg", "Go to Shopping Card and check out to generate Order summary");
			view = "ordersummary.jsp";
		}
		return view;
	}

	private String saveOrderForDelivery(HttpServletRequest request, HttpServletResponse response) {
		
				
		HttpSession session=request.getSession();

		String view = "";
		
		try {
			
			int cId=(int) session.getAttribute("coronaKitId");
			CoronaKit coronaKit=kitService.getCoronaKit(cId, kitDAO);
			coronaKit.setDeliveryAddress(request.getParameter("deliveryAddress"));
			coronaKit.setTotalAmount(Integer.parseInt(request.getParameter("cTotalAmount")));
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
			
			coronaKit.setOrderDate(dateOnly.format(cal.getTime()));
			coronaKit.setOrderFinalized(true);
			session.setAttribute("orderFinalized", true);
			kitService.validateAndSaveCoronaKit(cId, coronaKit, kitDAO);
			request.setAttribute("msg", "Order Successfully placed!! To view Order summary click on 'View Order Summary' link");
			view = "userhome.jsp";
		} catch (ProductException e) {
			request.setAttribute("errMsg", e.getMessage());
			view = "errorPage.jsp";
		}
		return view;
	}

	private String showPlaceOrderForm(HttpServletRequest request, HttpServletResponse response) {
		int total=Integer.parseInt(request.getParameter("total"));
		request.setAttribute("total", total);
		return "placeorder.jsp";
	}

	private String showKitDetails(HttpServletRequest request, HttpServletResponse response) {
		String view = "";
		
		HttpSession session=request.getSession();
		int id=(int)session.getAttribute("coronaKitId");

		try {
			List<KitItemDetails> kitItemDetails = kitService.getAllKitItems(id, kitDAO);
			session.setAttribute("kitItemDetails", kitItemDetails);
			view = "showkit.jsp";
		} catch (ProductException e) {
			request.setAttribute("errMsg", e.getMessage());
			view = "errorPage.jsp";
		}
		return view;
	}

	private String deleteItemFromKit(HttpServletRequest request, HttpServletResponse response) {
		int kid = Integer.parseInt(request.getParameter("kid"));
		HttpSession session=request.getSession();
		String view = "";

		try {
			kitService.deleteKitItem(kid, kitDAO);		
			request.setAttribute("msg","Product deleted");
			view = "showkit.jsp";
			@SuppressWarnings("unchecked")
			List<KitItemDetails> kitItemDetails=(List<KitItemDetails>) session.getAttribute("kitItemDetails");
			for(KitItemDetails kitItemDetail : kitItemDetails)
			{
				if(kitItemDetail.getId()==kid)
				{
					kitItemDetails.remove(kitItemDetail);
					break;
				}
			}
			session.setAttribute("kitItemDetails", kitItemDetails);
		} catch (ProductException e) {
			request.setAttribute("errMsg", e.getMessage());
			view = "errorPage.jsp";
		}

		return view;
	}

	private String addNewItemToKit(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		KitDetail kitDetail = new KitDetail();
		kitDetail.setProductId(Integer.parseInt(request.getParameter("pId")));
		kitDetail.setQuantity(Integer.parseInt(request.getParameter("pQuantity")));
		kitDetail.setCoronaKitId((int) session.getAttribute("coronaKitId"));
		int pCost=Integer.parseInt(request.getParameter("pCost"));
		int kitItemAmount=kitDetail.getQuantity()*pCost;
		kitDetail.setAmount(kitItemAmount);
		
		String view = "";
		
		try {
			
			kitDetail.setId(kitDAO.getMaxKitId()+1);
			kitService.validateAndAdd(kitDetail, kitDAO);
			request.setAttribute("msg", "Product addedd successfully");
			view = "showproductstoadd.jsp";
			List<ProductMaster> products=(List<ProductMaster>) session.getAttribute("products");
			for(ProductMaster product : products)
			{
				if(product.getId()==kitDetail.getProductId())
				{
					products.remove(product);
					break;
				}
			}
			session.setAttribute("products", products);
		} catch (ProductException e) {
			request.setAttribute("errMsg", e.getMessage());
			view = "errorPage.jsp";
		}
		return view;
	}

	private String showAllProducts(HttpServletRequest request, HttpServletResponse response) {
		String view = "";
		
		HttpSession session=request.getSession();
		if(session.getAttribute("orderFinalized") != null)
		{
			if((boolean)session.getAttribute("orderFinalized"))
			{
				int cId=(int) session.getAttribute("coronaKitId");
				try {
					CoronaKit coronaKit=kitService.getCoronaKit(cId, kitDAO);
					CoronaKit coronaKitNew = new CoronaKit();
					coronaKitNew.setPersonName(coronaKit.getPersonName());
					coronaKitNew.setEmail(coronaKit.getEmail());
					coronaKitNew.setContactNumber(coronaKit.getContactNumber());
					try {
						coronaKitNew.setId(kitDAO.getMaxCoronaKitId()+1);
						kitService.validateAndAddCoronaKit(coronaKitNew, kitDAO);
						session.setAttribute("newUser", coronaKitNew.getPersonName());
						session.setAttribute("coronaKitId", coronaKitNew.getId());
					} catch (ProductException e) {
						request.setAttribute("errMsg", e.getMessage());
						view = "errorPage.jsp";
					}
				} catch (ProductException e) {
					request.setAttribute("errMsg", e.getMessage());
					view = "errorPage.jsp";
				}
				session.setAttribute("orderFinalized", false);
				session.removeAttribute("orderSummary");
			}
		}

		try {
			List<ProductMaster> products = productService.getAllProducts(productMasterDao);
			session.setAttribute("products", products);
			view = "showproductstoadd.jsp";
		} catch (ProductException e) {
			request.setAttribute("errMsg", e.getMessage());
			view = "errorPage.jsp";
		}
		return view;
	}

	private String insertNewUser(HttpServletRequest request, HttpServletResponse response) {
		CoronaKit coronaKit = new CoronaKit();
		coronaKit.setPersonName(request.getParameter("pname"));
		coronaKit.setEmail(request.getParameter("pemail"));
		coronaKit.setContactNumber(request.getParameter("pcontact"));
		
		HttpSession session=request.getSession();

		String view = "";
		
		try {
			coronaKit.setId(kitDAO.getMaxCoronaKitId()+1);
			kitService.validateAndAddCoronaKit(coronaKit, kitDAO);
			session.setAttribute("newUser", coronaKit.getPersonName());
			session.setAttribute("coronaKitId", coronaKit.getId());
			view = "userhome.jsp";
		} catch (ProductException e) {
			request.setAttribute("errMsg", e.getMessage());
			view = "errorPage.jsp";
		}
		return view;
	}

	private String showNewUserForm(HttpServletRequest request, HttpServletResponse response) {
		CoronaKit coronaKit = new CoronaKit();
		request.setAttribute("coronaKit", coronaKit);
		String view = "newuser.jsp";
		return view;
	}
}