import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.mail.*;
import javax.mail.internet.*;

/***
 * This program if for the s2/2015 COMP9234 Web Applications Assignment 1
 * 
 * It is a website for search of the DBLP database
 * 
 * This class refers to XML with the DBLPHandler using the DBLPBean class (for each entry)
 * 
 * The class also updates an modifies the model through the DBLPHandler
 * 
 * 
 */

@WebServlet(name="NACOAMainServlet",urlPatterns="/start")
public class NACOAMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/* Files for the given session */
	private ArrayList<NACOABean> resultBeans; 
	private ArrayList<NACOABean> cartBeans;
	private ArrayList<NACOABean> sellingBeans;
	private ArrayList<NACOABean> pausedBeans;
	private ArrayList<NACOAUserBean> resultUserBeans; 
	
	/* Data Handler: class for dealing with databases */
	private NACOADataHandler dHandler;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NACOAMainServlet() {
        super();

        resultBeans = new ArrayList<NACOABean>();
        cartBeans = new ArrayList<NACOABean>();
        sellingBeans = new ArrayList<NACOABean>();
        pausedBeans = new ArrayList<NACOABean>();
        dHandler = new NACOADataHandler();
    }

    private void performBookSearch(HttpServletRequest req, HttpServletResponse res, boolean admin){
    	//extract variables
		String author = (String) req.getParameter("search_author");
		String title = (String) req.getParameter("search_title");
		String genre = (String) req.getParameter("search_genre");
		//String year = (String) req.getParameter("search_year");
		//String pubType = (String) req.getParameter("search_pubtype");
		/*
		try {
			//we should only do this if we want to load the main xml
			if(handler.fileExists(MAIN_FILE_LOCATION)){
				handler.loadMainXML(MAIN_FILE_LOCATION);
			}
			
			if(handler.fileExists(RESULTS_FILE_LOCATION)){
				handler.loadResultsXML(RESULTS_FILE_LOCATION);
			}
			
			//this performs the search and puts the results into a result doc
			handler.searchAdvanced(author, title, venue, year, pubType);
			
			//write results to the given file location
			handler.updateResultStoreXML(RESULTS_FILE_LOCATION);
			
		} catch (Exception e){
			System.out.println("error loading main xml:"  e );
		}
		
		*/	
		//this then just retrieves the results from the result doc (into bean format)
		//resultBeans = handler.getBeanFromResultDoc(0);
		if (admin) {
			resultBeans = dHandler.bookAdminSearch(title, author, genre);
		} else {
			resultBeans = dHandler.bookSearch(title, author, genre);
		}
		
		//System.out.println("Size is " + resultBeans.size());
		//System.out.println("Username = " + resultBeans.get(0).getSellerName());
		//System.out.println("UserID = " + resultBeans.get(0).getUserSellerID());
		//System.out.println("Username = " + resultBeans.get(1).getSellerName());
		//System.out.println("UserID = " + resultBeans.get(1).getUserSellerID());
		
		ArrayList<NACOABean> first10 = new ArrayList<NACOABean>();
		
		int x = 0;
		
		while (x != resultBeans.size() && first10.size() != 10) {
			first10.add(resultBeans.get(x));
			x++;
		}
		
		//set up our bean to be displayed
		ResultPageBean viewBean = new ResultPageBean();
		viewBean.setResultBeans(first10);
		
		int totalResults = resultBeans.size();
		viewBean.setTotalResults(totalResults);
		System.out.println("size is " + totalResults);
		
		
		if(totalResults > 10){
			viewBean.setMore(true);
		}
		viewBean.setCurr_page_num(1);
		viewBean.setNext_page_num(2);
		
		req.setAttribute("viewBean",viewBean);
    	
		//i guess we still need this
		//but we might change it to include a search for people?
		//as Admin can find people and ban them?
		//um that might be good too
		//that sounds like a good idea
		//um same database but different query and table
		

    }
    
    private void performUserSearch(HttpServletRequest req, HttpServletResponse res, boolean isAdmin){
    	//extract variables
    	//same thing except basic search
		//String query = (String) req.getParameter("search_query");
		String query = (String) req.getParameter("search_username");
		
		//Search users
		if (isAdmin) {
			resultUserBeans = dHandler.userAdminSearch(query);
		} else {
			resultUserBeans = dHandler.userSearch(query);
		}
		
		//System.out.println("Size is " + resultUserBeans.size());
		//System.out.println("Is Halted = " + resultUserBeans.get(0).getIsHalted());
		//Set up view for users?
		ResultPageUserBean viewBean = new ResultPageUserBean();
		viewBean.setResultBeans(resultUserBeans);
		
		int totalResults = resultUserBeans.size();
		viewBean.setTotalResults(totalResults);
		
		if(totalResults > 10){
			viewBean.setMore(true);
		}
		viewBean.setCurr_page_num(1);
		viewBean.setNext_page_num(2);
		
		req.setAttribute("viewUserBean",viewBean);
		
		/*
		try {
			//we should only do this if we want to load the main xml
			if(handler.fileExists(MAIN_FILE_LOCATION)){
				handler.loadMainXML(MAIN_FILE_LOCATION);
    		}
			
			//read results from the given file location
    		if(handler.fileExists(RESULTS_FILE_LOCATION)){
				handler.loadResultsXML(RESULTS_FILE_LOCATION);
    		}
    		
    		
    		if(searchType.matches(".*Author.*")){
    			handler.searchBasicAuthor(query);
    		} else if (searchType.matches(".*Publication.Title.*")){
    			handler.searchBasicTitle(query);
    		} else if (searchType.matches(".*Venue.*")){
    			handler.searchBasicVenue(query);
    		} else if (searchType.matches(".*Year.*")){
    			handler.searchBasicYear(query);
    		} else {
    			System.out.println("search type unknown");
    		}
    		//write results to the given file location
    		handler.updateResultStoreXML(RESULTS_FILE_LOCATION);
			
		} catch (Exception e){
			System.out.println("error loading main xml:"  e );
		}*/
		
		//Do a search and display the content (from results instead of main!)

		//resultBeans = handler.getBeanFromResultDoc(0);	
		
	  	//set up our bean to be displayed
		/*
		ResultPageBean viewBean = new ResultPageBean();
		viewBean.setResultBeans(resultBeans);
		
		int totalResults = handler.getNumResults();
		viewBean.setTotalResults(totalResults);
		
		
		if(totalResults > 10){
			viewBean.setMore(true);
		}
		viewBean.setCurr_page_num(1);
		viewBean.setNext_page_num(2);
		
		req.setAttribute("viewBean",viewBean);
    	*/

    	//set up our bean to be displayed
		//yeah um.. ill ask on the forum but i think searching will be easy
		//SQL makes searches pretty easy I think

    }
    
    
  //we can read the URI for the specific request, and then redirect appropriately
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException	{

    	System.out.println(System.getProperty("user.dir"));
    	doPost(req,res);
    	
    	//This is the get method call... it just redirects to post
    	
	
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	RequestDispatcher requestDispatcher; 
		String uri = req.getRequestURI();
		System.out.println("GOT URI: " + uri);
		//create database if not created
		if(!dHandler.databaseExists("nacoadatabase")){
			dHandler.setUpDatabase();
		}
		//if we are logged in retrieve user id
		if(req.getSession().getAttribute("logged_in") != null){
			//write the code you want
		}
		
		if (uri.contains("profile")){ //WE WANT TO VIEW A PROFILE (CURRENT USERS OR SOMEONE ELSE)
			String userToView = (String) req.getParameter("user");
			int user_id = dHandler.getId(userToView);
			String currUser = (String) req.getSession().getAttribute("username");
			int curr_user_id = dHandler.getId(currUser);
			updateSessionUserDetails(req, curr_user_id);
			//load user details
			NACOAUserBean profileBean = dHandler.getUserDetails(user_id);
			if(userToView.equals(currUser)){ //we view our own profile
				profileBean.setIsUser(true);			
			}
			ArrayList<NACOABean> sellingBeans = dHandler.getSellingList(user_id);
			req.getSession().setAttribute("selling_books", sellingBeans);
			req.getSession().setAttribute("profile", profileBean);
			//generate random list
	    	requestDispatcher = req.getRequestDispatcher("/Profile.jsp");
	    	requestDispatcher.forward(req, res);
			
		} else if (uri.contains("transaction_history")){
			String userToView = (String) req.getParameter("user");
			int userId = dHandler.getUserId(userToView);
			String currUser = (String) req.getSession().getAttribute("username");
			//load user details
			NACOAUserBean profileBean = dHandler.getUserDetails(userId);
			if(userToView.equals(currUser)) { //we view our own profile
				profileBean.setIsUser(true);			
			}
			ArrayList<NACOAHistoryBean> historyBeans = dHandler.getUserHistory(userId);
			req.getSession().setAttribute("transaction_history", historyBeans);
			req.getSession().setAttribute("profile", profileBean);
			requestDispatcher = req.getRequestDispatcher("/CustomerHistory.jsp");
	    	requestDispatcher.forward(req, res);
			
		} else if (uri.contains("logout")) {
			//write the code you want
			logoutUser(req, res);
			requestDispatcher = req.getRequestDispatcher("/Logout.jsp");
	    	requestDispatcher.forward(req, res);
			
		} else if (uri.contains("register")) { //USER REGISTRATION PAGE
			req.getSession().setAttribute("register_message", "");
			if(req.getParameter("registering") != null){
				//register the user
				int user_id = registerUser(req, res);
				System.out.println("got user id: " + user_id);
				if (user_id > 0) {
					sendConfirmationEmail(user_id);
			 		requestDispatcher = req.getRequestDispatcher("/Waiting_confirmation.jsp");
			    	requestDispatcher.forward(req, res);
				} else {
					// User already exists
				}
			}
			requestDispatcher = req.getRequestDispatcher("/Register.jsp");
	    	requestDispatcher.forward(req, res);
	    	
		} else if (uri.contains("admin_login")){ //ADMIN LOGIN PAGE
			req.getSession().setAttribute("login_result", "");
		
			if(req.getParameter("admin_login_process") != null){
				String username = req.getParameter("username");
				String password = req.getParameter("password");
				int id = dHandler.getId(username);
				String authResult = null;
				if (id == -1) {
					authResult = "No such username. Please register first";
				} else if (!dHandler.isAdmin(username)){
					authResult = "Not an admin account";
				} else {
					authResult = authUser(id, password); //contains a message of the authentication result
				}
				
				req.getSession().setAttribute("login_result", authResult);
				if (authResult.equals("") && dHandler.isAdmin(username)) {
					loginUser(req, res, id);
					updateSessionUserDetails(req, id);
					requestDispatcher = req.getRequestDispatcher("/Search.jsp");
			    	requestDispatcher.forward(req, res);
				} else {
					requestDispatcher = req.getRequestDispatcher("/Login.jsp");
			    	requestDispatcher.forward(req, res);
				}
			}
			
			
			requestDispatcher = req.getRequestDispatcher("/AdminLogin.jsp");
	    	requestDispatcher.forward(req, res);
	    	
		} else if (uri.contains("login")){ //USER LOGIN PAGE
			req.getSession().setAttribute("login_result", "");
			requestDispatcher = req.getRequestDispatcher("/Login.jsp");
	    	requestDispatcher.forward(req, res);
	    	
		} else if (uri.contains("submitcred")){ //SUBMIT LOGIN DETAILS
			String username = req.getParameter("username");
			System.out.println(username);
			String password = req.getParameter("password");
			int id = dHandler.getId(username);
			String authResult = null; 
			if (id == -1) {
				authResult = "No such username. Please register first";
			} else if(dHandler.isAdmin(username)){
				authResult = "Admin account cannot be used here";
			} else {
				authResult = authUser(id, password); //contains a message of the authentication result
			}
			
			req.getSession().setAttribute("login_result", authResult);
			if (authResult.equals("") && !dHandler.isAdmin(username)) {
				loginUser(req, res, id);
				updateSessionUserDetails(req, id);
				requestDispatcher = req.getRequestDispatcher("/Search.jsp");
		    	requestDispatcher.forward(req, res);
			} else {
				requestDispatcher = req.getRequestDispatcher("/Login.jsp");
		    	requestDispatcher.forward(req, res);
			}
			
		} else if (uri.contains("verify")){ //SUBMIT LOGIN DETAILS
			int id = Integer.parseInt(req.getParameter("id"));
			String code = req.getParameter("code");
			System.out.println("Verifying user "+  id + "\nwith code: " + code);
			verifyUser(id, code);
	    	requestDispatcher = req.getRequestDispatcher("/Login.jsp");
	    	requestDispatcher.forward(req, res);
	    	
		} else if (uri.contains("account")){ //Update account details page
			int user_id = (int) req.getSession().getAttribute("user_id");
			updateSessionUserDetails(req, user_id);
			requestDispatcher = req.getRequestDispatcher("/Account_setting.jsp");
	    	requestDispatcher.forward(req, res);
	    	
		} else if (uri.contains("updacc")){ //Update the account details
			changeUserDetails(req,res);
			requestDispatcher = req.getRequestDispatcher("/Search.jsp");
	    	requestDispatcher.forward(req, res);
	    	
		} else if (uri.contains("cart")){ //CART PAGE
			//for cart it only processes the cart page or remove action
			
			setUpCartDB(req, res);
			if (req.getParameter("remove_cart") != null) {
				System.out.println("removing...");
				removeFromCart(req,res);
			}
	    	requestDispatcher = req.getRequestDispatcher("/Cart.jsp");
	    	requestDispatcher.forward(req, res);
	    	
		} else if (uri.contains("results")){  //RESULT PAGE
			String searchType = (String) req.getParameter("search_type");
			String username = (String) req.getSession().getAttribute("username");
			boolean isAdmin = false;
			System.out.println("username is " + username);
			
			req.getSession().setAttribute("banUser", false);
			req.getSession().setAttribute("makeAdmin", false);
			req.getSession().setAttribute("unbanUser", false);
			//req.getSession().setAttribute("modifiedUser", username);
			
			int user_id = dHandler.getId(username);
			
			if (user_id > 0) {
				if (dHandler.isAdmin(username)) {
					req.getSession().setAttribute("admin", true);
					isAdmin = true;
				} else {
					req.getSession().setAttribute("admin", false);
				}
			}
			//case of search
			//we will redirect to results
			if (searchType != null){
				System.out.println("search");
				if (searchType.matches(".*book.*")){
					performBookSearch(req,res,isAdmin);
				} else if (searchType.matches(".*user.*")){
					performUserSearch(req,res,isAdmin);
				}
			} else if(req.getParameter("add_to_cart_view") != null){
				//add to cart from extend view
				System.out.println("add");
				appendToCartPage(req,res);
			} else if(req.getParameter("add_to_cart") != null){
				//add to cart from results
				System.out.println("add");
				appendToCartPage(req,res);
				processResults(req,res);
			} else if (req.getParameter("ban_user") != null) {
				System.out.println("ban");
				System.out.println("Received " + req.getParameter("banUser"));
				req.getSession().setAttribute("banUser", true);
				banUser(req, res);
			}  else if (req.getParameter("unban_user") != null) {
				System.out.println("unban");
				System.out.println("Received " + req.getParameter("unbanUser"));
				//req.getSession().setAttribute("banUser", true);
				unbanUser(req, res);
			}  else if (req.getParameter("make_admin") != null) {
				System.out.println("admin");
				System.out.println("Received " + req.getParameter("makeAdmin"));
				//req.getSession().setAttribute("makeAdmin", true);
				makeUserAdmin(req, res);
			}  else {
				System.out.println("Proc");
				processResults(req,res);
			}
	    	requestDispatcher = req.getRequestDispatcher("/Results.jsp");
	    	requestDispatcher.forward(req, res);
	    	
		} else if (uri.contains("upload_book")) {
			req.getSession().setAttribute("upload_success", false);
			req.getSession().setAttribute("upload_message", "");
			if (req.getParameter("uploading") != null) {
				int book_id = UploadBook(req, res);
				if (book_id > 0) {
					req.getSession().setAttribute("upload_success", true);
				} else {
					req.getSession().setAttribute("upload_success", false);
				}
			}
	    	requestDispatcher = req.getRequestDispatcher("/Upload_book.jsp");
	    	requestDispatcher.forward(req, res);
	    	
		} else if (uri.contains("check_out")) {
			System.out.println("Checking out");
			
			req.getSession().setAttribute("checkingOut", false);
			//TODO Can sell multiple books
			//System.out.println("Checking out");
			
			if((boolean) req.getSession().getAttribute("checkingOut") == false){
				//register the user
				//int user_id = registerUser(req, res);
				System.out.println("Received parame " + req.getSession().getAttribute("user_id"));
				
				int user_id = (int)req.getSession().getAttribute("user_id");
				//System.out.println("Received id "  user_id);
				String creditDetails = dHandler.getCreditCardDetails(user_id);
				
				if (creditDetails.contentEquals("")) {
					System.out.println("There are no credit details");
				} else {
					req.getSession().setAttribute("checkingOut", true);
				}
			}
					
			sendEmailsToSellers(req, res);  
			requestDispatcher = req.getRequestDispatcher("/checkOut.jsp");
	    	requestDispatcher.forward(req, res);
			System.out.println(uri);
		} else if (uri.contains("selling")) {
			int user_id = (int)req.getSession().getAttribute("user_id");
			int num_books = dHandler.maxBooksID();
			sellingBeans = dHandler.getSellingList(user_id);
			req.getSession().setAttribute("sellingList", sellingBeans);
			pausedBeans = dHandler.getPausedList(user_id);
			req.getSession().setAttribute("pausedList", pausedBeans);
			for(int n = 1; n != num_books+1; n++){
//				System.out.println("Entry "  n);
				if(req.getParameter("entry"+n) != null){
					System.out.println("Deleting book with id " + n);
					dHandler.pauseSelling(user_id, n);
				}
				if(req.getParameter("paused"+n) != null){
					System.out.println("Unpausing book with id " + n);
					dHandler.resumeSelling(user_id, n);
				}
				sellingBeans = dHandler.getSellingList(user_id);
				req.getSession().setAttribute("sellingList", sellingBeans);
				pausedBeans = dHandler.getPausedList(user_id);
				req.getSession().setAttribute("pausedList", pausedBeans);
			}
			sellingBeans = dHandler.getSellingList(user_id);
			req.getSession().setAttribute("sellingList", sellingBeans);
			pausedBeans = dHandler.getPausedList(user_id);
			req.getSession().setAttribute("pausedList", pausedBeans);
			requestDispatcher = req.getRequestDispatcher("/Selling.jsp");
	    	requestDispatcher.forward(req, res);
		} else if (uri.contains("forgot_password")) {
			if(req.getParameter("forget") != null){
				String username = (String) req.getParameter("username");
				String email = (String) req.getParameter("email");
				int user_id = dHandler.getId(username);
				if (email.equals(dHandler.getEmail(user_id))) {		
					sendPasswordEmail(user_id);
					requestDispatcher = req.getRequestDispatcher("/Forgot_confirmation.jsp");
			    	requestDispatcher.forward(req, res);
				} else {
					
				}
			}
			requestDispatcher = req.getRequestDispatcher("/Forgot.jsp");
	    	requestDispatcher.forward(req, res);
		} else {//MAIN PAGE (this is /search or welcome
			req.getSession().setAttribute("randomBeans", dHandler.getRandomList(5));
			
	    	requestDispatcher = req.getRequestDispatcher("/Search.jsp");
	    	requestDispatcher.forward(req, res);
		}

	}

	private void unbanUser(HttpServletRequest req, HttpServletResponse res) {
		int user_id = Integer.parseInt(req.getParameter("user_id"));
		
		System.out.println("Received user id " + user_id);
		
		//String username = dHandler.getUserName(user_id);
		
		if (dHandler.checkHalted(user_id)) {
			dHandler.unbanUser(user_id);
			req.getSession().setAttribute("unbanUser", true);
			req.getSession().setAttribute("modifiedUser", dHandler.getUserName(user_id));
		} else {
			System.out.println("Unban user receiving wrong info!!!");
			//req.getSession().setAttribute("unbanUser", true);
		}
	}

	private void makeUserAdmin(HttpServletRequest req, HttpServletResponse res) {
		int user_id = Integer.parseInt(req.getParameter("user_id"));
		
		System.out.println("Received user id " + user_id);
		
		String username = dHandler.getUserName(user_id);
		
		if (dHandler.isAdmin(username)) {
			dHandler.setAdmin(user_id);
			req.getSession().setAttribute("alreadyAdmin", true);
		} else {
			dHandler.setAdmin(user_id);
			req.getSession().setAttribute("makeAdmin", true);
		}
		req.getSession().setAttribute("modifiedUser", dHandler.getUserName(user_id));
		
	}

	private boolean validEmail(String emailAddress) {
		final String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		final Pattern pattern;
		final Matcher matcher;
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(emailAddress);
		return matcher.matches();
	}
	
	private void banUser(HttpServletRequest req, HttpServletResponse res) {
		
		int user_id = Integer.parseInt(req.getParameter("user_id"));
		
		System.out.println("Received user id " + user_id);
		
		req.getSession().setAttribute("modifiedUser", dHandler.getUserName(user_id));
		dHandler.banUser(user_id);
	}

	private void updateSessionUserDetails(HttpServletRequest req, int id) {
		NACOAUserBean userDetails = dHandler.getUserDetails(id);
		
		System.out.println("got details: " + userDetails);
		req.getSession().setAttribute("userDetails", userDetails);
	}

	private void verifyUser(int id, String code) {
		dHandler.verifyUser(id, code);
	}

	private void setUpCartDB(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("Setting up cart!!!");
		System.out.println(req.getParameter("username"));
		int user_id = dHandler.getId(req.getParameter("username"));

		System.out.println("User id is " + user_id);
		cartBeans = dHandler.getShoppingCart(user_id);
		req.getSession().setAttribute("shoppingCart", cartBeans);
		//handler.setCartToSession("shoppingCartDoc", req.getSession());
	}

	public void appendToCartPage(HttpServletRequest req, HttpServletResponse res){
		
		int user_id = dHandler.getId(req.getParameter("username"));
		System.out.println("Received username "+  req.getParameter("username"));
		System.out.println("Received user ID "+  user_id);
		int book_id = Integer.parseInt(req.getParameter("book_id"));
		System.out.println("Received book ID " + book_id);
		   
		//Need to get book_id somehow
		dHandler.addBookToCart(user_id, book_id, 0);
		cartBeans = dHandler.getShoppingCart(user_id);
		
		System.out.println("Cart is size " + cartBeans.size());
		//Don't forget to make an entry for our transaction history!
		dHandler.addHistoryAddCartEntry(user_id,book_id);
		
		req.getSession().setAttribute("shoppingCart", cartBeans);
		//handler.setCartToSession("shoppingCartDoc", req.getSession());
		
	}
	
	public void removeFromCart(HttpServletRequest req, HttpServletResponse res){
		//we need to load the "current" cart doc, either from session variable, or from xml
		
		int totalEntries =  dHandler.maxBooksID();
		int user_id = Integer.parseInt(req.getParameter("user_id"));
		
		cartBeans = dHandler.getShoppingCart(user_id);
		
		//TODO Need a way to get book id to delete from cart
		for(int n = 0; n != totalEntries+1; n++){
			if(req.getParameter("entry"+n) != null){
				
				//Need to remove from database
				//handler.removeFromCart(n);
				System.out.println("Deleting book with id " + n);
				dHandler.deleteBookCart(n, user_id);
				dHandler.addHistoryRemoveCartEntry(user_id, n);
			}
			
		}
		
		cartBeans = dHandler.getShoppingCart(user_id);

		req.getSession().setAttribute("shoppingCart", cartBeans);
	
	}

	public int registerUser(HttpServletRequest req, HttpServletResponse res){
		String username = (String) req.getParameter("username");
		String password = (String) req.getParameter("password");
		String email = (String) req.getParameter("email");
		String nickname = (String) req.getParameter("nickname");
		String firstname = (String) req.getParameter("firstname");
		String lastname = (String) req.getParameter("lastname");
		String dob = (String) req.getParameter("dob");
		String address = (String) req.getParameter("address");
		String creditinfo = (String) req.getParameter("creditinfo");
		String description = (String) req.getParameter("description");
		
		System.out.println(":" + username );
		System.out.println(":" + password );
		System.out.println(":" + email );
		System.out.println(":" + nickname );
		System.out.println(":" + firstname );
		System.out.println(":" + lastname );
		System.out.println(":" + dob );
		System.out.println(":" + address );
		System.out.println(":" + creditinfo );
		System.out.println(":" + description );
		
		if (username.equals("") || password.equals("") || email.equals("") || nickname.equals("") || firstname.equals("")
			|| lastname.equals("") || dob.equals("") || address.equals("") || creditinfo.equals("")) {
			req.getSession().setAttribute("register_message", "Please check that all fields are filled in");
			return -1;	
		}
		
		if (!isValidDate(dob)) {
			req.getSession().setAttribute("register_message", "Invalid date format, please check that your date is valid (yyyy-mm-dd)");
			return -1;
		}
		
		if (!validEmail(email)) {
			req.getSession().setAttribute("register_message", "Invalid email address, please check that you spelt it correctly");
			return -1;
		}
		
		System.out.println("Creating User in MySQL Database");
		if (!dHandler.userExists(username)) {
			int user_id = dHandler.createUser(username, password, email, nickname, firstname, lastname, dob, address, creditinfo, description);
			req.getSession().setAttribute("register_message", "");
			return user_id;
		}else {
			req.getSession().setAttribute("register_message", "Username already exists, please choose another");
			return -1;
		}
	}
	
	public int UploadBook(HttpServletRequest req, HttpServletResponse res){
		int user_id = Integer.parseInt(req.getParameter("user_id"));
		
		System.out.println("User id is " + user_id);
		//int user_id = dHandler.getId(username);
		
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
	    String today = (String) dateFormat.format(date);
		String title = (String) req.getParameter("title");
		String author = (String) req.getParameter("author");
		String picture = (String) req.getParameter("picture");
		String publisher = (String) req.getParameter("publisher");
		String dateofpublication = (String) req.getParameter("dateofpublication");
		String pages = (String) req.getParameter("pages");
		String isbn = (String) req.getParameter("isbn");
		String genre = (String) req.getParameter("genre");
		String price = (String) req.getParameter("price");
		String description = (String) req.getParameter("description");
		
		System.out.println(":" + user_id );
		System.out.println(":" + today );
		System.out.println(":" + title );
		System.out.println(":" + author );
		System.out.println(":" + publisher );
		System.out.println(":" + dateofpublication );
		System.out.println(":" + pages );
		System.out.println(":" + isbn );
		System.out.println(":" + genre );
		System.out.println(":" + price );
		System.out.println(":" + description );

		if (today.equals("") || title.equals("") || author.equals("") || picture.equals("") || publisher.equals("") || 
			dateofpublication.equals("") || pages.equals("") || isbn.equals("") || genre.equals("") || price.equals("")) {
			req.getSession().setAttribute("upload_message", "Please check that all fields are filled in");
			return -1;
		}
		
		if (!isInteger(pages)) {
			req.getSession().setAttribute("upload_message", "Pages must be an integer value");
			return -1;
		}
		
		if (!isFloat(price)) {
			req.getSession().setAttribute("upload_message", "Price must be a number (no $ sign)");
			return -1;
		}
		
		if (!isValidDate(dateofpublication)) {
			req.getSession().setAttribute("upload_message", "Invalid date format, please check that your date is valid (yyyy-mm-dd)");
			return -1;
		}
		
		System.out.println("Creating User in MySQL Database");
		int book_id = dHandler.createBook(user_id, today, title, author, picture, price, publisher, dateofpublication, pages, isbn, genre, description);
		
		return book_id;
	}
	
	private boolean isFloat(String input) {
		try {
	        Float.parseFloat( input );
	        return true;
	    }
	    catch( Exception e ) {
	        return false;
	    }
	}

	public boolean isInteger( String input ) {
	    try {
	        Integer.parseInt( input );
	        return true;
	    }
	    catch( Exception e ) {
	        return false;
	    }
	}
	
	public boolean isValidDate(String inDate) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);	    
	    try {
			dateFormat.parse(inDate.trim());
		} catch (ParseException e) {
			return false;
		}
	    return true;
	 }
	public String authUser(int id, String pw) {
		String result = "Error authenticating user";
		
		//check if user is halted
		if (!dHandler.checkHalted(id)) {
			if (dHandler.checkPassword(id, pw)) {
				result = "";
			}else {
				result = "Incorrect username or password";
			}
		} else {
			result = "User email not verified or account has been banned, please check your email";
		}
		return result;
	}
	
	public void loginUser(HttpServletRequest req, HttpServletResponse res, int id){
		//set the session to login
		req.getSession().setAttribute("logged_in", true);
		req.getSession().setAttribute("user_id", id);
		req.getSession().setAttribute("username", dHandler.getUserName(id));
		NACOAUserBean userDetails = dHandler.getUserDetails(id);
		req.getSession().setAttribute("userDetails", userDetails);
		
		//Add more stuff that you need for a login
		
	}
	
	public void logoutUser(HttpServletRequest req, HttpServletResponse res){
		//set the session to logged out
		req.getSession().setAttribute("logged_in", false);
		req.getSession().setAttribute("user_id", 0);
		req.getSession().setAttribute("username", "");
		req.getSession().setAttribute("userDetails", null);
		
	}
	
	public void changeUserDetails(HttpServletRequest req, HttpServletResponse res){
		int user_id = Integer.parseInt(req.getParameter("user_id"));
		String newPassword = req.getParameter("password");
		String newEmail = req.getParameter("email");
		String newNickname = req.getParameter("nickname");
		String newFirstname = req.getParameter("firstname");
		String newLastname = req.getParameter("lastname");
		String newDob = req.getParameter("dob");
		String newAddress = req.getParameter("address");
		String newCreditinfo = req.getParameter("creditinfo");
		dHandler.changeUserDetails(user_id, newPassword, newEmail, newNickname, 
				newFirstname, newLastname, newDob, newAddress, newCreditinfo);
	}
	
	public void sendConfirmationEmail(int user_id){
		//send a email to the user...
		
		String subject = new String("Verify your NACOA account");

		String message = new String("Thanks for signing up for NACOA, please follow this link to  " +
 	         		 "verify your account: http://localhost:8080/Assignment2/verify?id=" + user_id);
 		
 		sendEmail(user_id, subject, message);
 		System.out.println("Sent verification email...");
	}
	
	/**
	 * Processes the result page 
	 * @param req
	 * @param res
	 */
	public void processResults(HttpServletRequest req, HttpServletResponse res){
		//just set up the cart to be read 
		//TODO Change this!!!!!!!
		String entryToview = req.getParameter("entryMoreView");//im trying to remember what my code does lol
		String entryToviewuser = req.getParameter("entryMoreViewUser");
		//oh yeah so this viewBean is just a bean to display stuff, is a series of boolean values
		//like do we show more results?
		//do we show less results (like a back button?)
		System.out.println("Processing results");
		System.out.println("Received " + entryToview);
		//We are viewing books
		//Need to separate viewing books and users
		if(entryToview != null){ //EXPANDING VIEW TO READ MORE
			int entryToViewNum = Integer.parseInt(entryToview);
			
			//View num is the book_id
			//resultBeans = handler.getBeanFromResultDoc(entryToViewNum);
			//Search for right entry
			NACOABean entry = resultBeans.get(0);
			
			System.out.println("Size of results is "+ resultBeans.size());
			int i = 0;
			
			while (entry.getBookID() != entryToViewNum) {
				entry = resultBeans.get(i);
				i++;
			}
			
			//that is what a ResultPageBean does
			ResultPageBean viewBean = new ResultPageBean();
			
			viewBean.setReadMore(true);
			viewBean.setReadEntry(entry);
			
			req.setAttribute("viewBean",viewBean);
			
		} else { 
			//TODO Not sure
			//CHANGING PAGE NUMBER
			ResultPageBean viewBean = new ResultPageBean();
			
			//get the current page number (default = 1)
			//int totalResults = handler.getNumResults();
			int totalResults = resultBeans.size();
			viewBean.setTotalResults(totalResults);
			
			String pageNo = req.getParameter("page");
			int currPageNo = Integer.parseInt(pageNo);
			viewBean.setCurr_page_num(currPageNo);
			
			//so the logic for that is here
			//if the current page number is > 1 we can go backwards etc
			if(pageNo == null){
				//default is first page
				//resultBeans = handler.getBeanFromResultDoc(0);
				
				if(currPageNo > 1){
					viewBean.setLess(true);
				}
				
			} else {
				//generate the appropriate
				int startEntry = 10 * (currPageNo-1);
				
				System.out.println("Start entry is " + startEntry);
				if(startEntry > totalResults){
					//error
					return;
				} 
				
				//retrieve results
				//resultBeans = handler.getBeanFromResultDoc(startEntry);
				ArrayList<NACOABean> temp = new ArrayList<NACOABean>();
				
				int x = startEntry;
				
				while (x < resultBeans.size() && x != (startEntry+10)) {
					temp.add(resultBeans.get(x));
					x++;
				}
				
				//set up our bean to be displayed
				viewBean.setResultBeans(temp); //we keep two copies
				
				//set up entry
				if(currPageNo > 1){
					viewBean.setLess(true);
					viewBean.setPrev_page_num(currPageNo-1);
				}
				if(totalResults > startEntry + 10){
					viewBean.setMore(true);
					viewBean.setNext_page_num(currPageNo+1);
				}

				//and we pass these Beans to our JSPs through attributes
				req.setAttribute("viewBean",viewBean);
				//yeah, it reads it and has if statements to display data
				//um yeah it uses JSTL for ifs

			}
		}

		
	}

	//Sends emails to sellers
	private void sendEmailsToSellers(HttpServletRequest req, HttpServletResponse res) {

		System.out.println("sending emails");
		//ArrayList<NACOACheckOutBook> users = (ArrayList<NACOACheckOutBook>) req.getAttribute("all_sellers");
		int user_id = (int)req.getSession().getAttribute("user_id");
		ArrayList<NACOABean> cart = dHandler.getShoppingCart(user_id);
		
		System.out.println("size of cart is "+ cart.size());
		
		int size = 0;
		
		while (size != cart.size()) {
			int user_seller_id = dHandler.getSellersUserID(cart.get(size).getBookID());
			System.out.println("book id is " + cart.get(size).getBookID());
			
	 		int bookID = cart.get(size).getBookID();
	 		
	 		String subject = new String("A book has been sold");
	 		String message = new String("The following book has been sold: \n"
	 					 + "Title: " + dHandler.getBookTitle(bookID) + "\n"
	 					 + "Author: " +  dHandler.getBookAuthor(bookID) + "\n"
	 					 + "Price: $" +  dHandler.getBookPrice(bookID) + "\n"
	 					 + "\n Have a nice day!");

	 		//Sending email
	 		System.out.println("Sending email");
	 		sendEmail(user_seller_id, subject, message);
	 		
 			//Remove the books from the cart
 			System.out.println("Deleting book from cart");
 			dHandler.deleteBookCart(bookID, user_id);
 			
 			//Set the book to sold
 			System.out.println("Setting book to sold");
 			dHandler.setBookSold(bookID);
	 		
 			//add to history
 			dHandler.addHistoryBuyEntry(user_id, bookID);
 			
	 		size++;
		}
		
		
		System.out.println("Sent emails to users with their books sold...");
	}
	
	public void sendPasswordEmail(int user_id) {

		String password = dHandler.getPassword(user_id);
		String subject = new String("NACOA account - Forgot Password");
		String message = new String("The following is your NACOA password:\n");
		message = message + password + "\n Please keep it safe.";
		
		sendEmail(user_id, subject, message);
	}
	
	//Use to send all emails
	public void sendEmail(int user_id, String subject, String sendMessage) {
		String to = dHandler.getEmail(user_id);
		
		System.out.println("sending to email "+ to);
		String from = "info.nacoa@gmail.com";
		
 		Properties props = new Properties();
 		props.put("mail.smtp.auth", "true");
 		props.put("mail.smtp.starttls.enable", "true");
 		props.put("mail.smtp.host", "smtp.gmail.com");
 		props.put("mail.smtp.port", "587");

 		Session session = Session.getInstance(props,
 		  new javax.mail.Authenticator() {
 			protected PasswordAuthentication getPasswordAuthentication() {
 				return new PasswordAuthentication(from, "comp9321");
 			}
 		  });

 		try {
 			System.out.println("starting...");
 			Message message = new MimeMessage(session);
 			message.setFrom(new InternetAddress(from));
 			message.setRecipients(Message.RecipientType.TO,
 					InternetAddress.parse(to));
 			message.setSubject(subject);
 			
 			message.setText(sendMessage);

 			Transport.send(message);
 		} catch (MessagingException e) {
 			throw new RuntimeException(e);
 		}	
	}
}

