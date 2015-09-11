


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

@WebServlet(name="MainServlet",urlPatterns="/start")
public class DBLPMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static int NUM_ENTRIES_NEW_FILE = 20; 
	final static String RESULTS_FILE_LOCATION = "./workspace/DBLP/WebContent/WEB-INF/results.xml";
	final static String MAIN_FILE_LOCATION = "./workspace/DBLP/WebContent/WEB-INF/DBLPSmallGen.xml";
	//final static String MAIN_FILE_LOCATION = "./workspace/DBLP/WebContent/WEB-INF/smallDBLP.xml";
	final static String CART_FILE_LOCATION = "./workspace/DBLP/WebContent/WEB-INF/cart.xml";
	
	/* Files for the given session */
	private ArrayList<DBLPBean> resultBeans; 
	private ArrayList<DBLPBean> cartBeans;
	
	/* XML Handler: abstract class for dealing with XML */
	private DBLPHandler handler;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DBLPMainServlet() {
        super();

        resultBeans = new ArrayList<DBLPBean>();
        cartBeans = new ArrayList<DBLPBean>();
        handler = new DBLPHandler();
    }
    
    /**
     * Call this function if you want to generate a number if randomly selected entries
     */
    private ArrayList<DBLPBean> generateRandomBeans(int num){
    	//generate num number of entries into an xml
    	
    	Random rand = new Random();
    	int totalEntries = handler.getNumMain();
    	
    	ArrayList<Integer> listOfNum = new ArrayList<Integer>();
    	ArrayList<DBLPBean> randomEntriesList = new ArrayList<DBLPBean>();
    	
    	for(int i = 0;  i < num*5; i++ ){
    		
    		int n = rand.nextInt(totalEntries-1) + 1;
    	
    		listOfNum.add(n);
    	}
    	
    	int counter = 0;
    	for (int n : listOfNum){
    		counter++;
    		
    		if(counter > 10){
    			break;
    		}
    		
    		DBLPBean newBean = handler.getEntryMain(n);
    		if(newBean == null){
    			counter--;
    		} else {
    			randomEntriesList.add(newBean);
    		}
    	}
    	
    	//set to be used by main for display!
    	return randomEntriesList;
    	
    }
    

    private void performAdvancedSearch(HttpServletRequest req, HttpServletResponse res){
    	//extract variables
		String author = (String) req.getParameter("search_author");
		String title = (String) req.getParameter("search_title");
		String venue = (String) req.getParameter("search_venue");
		String year = (String) req.getParameter("search_year");
		String pubType = (String) req.getParameter("search_pubtype");
		
		try {
			//we should only do this if we want to load the main xml
			if(handler.fileExists(MAIN_FILE_LOCATION)){
				handler.loadMainXML(MAIN_FILE_LOCATION);
			}
			
			if(handler.fileExists(RESULTS_FILE_LOCATION)){
				handler.loadResultsXML(RESULTS_FILE_LOCATION);
			}

			handler.searchAdvanced(author, title, venue, year, pubType);
			
			//write results to the given file location
			handler.updateResultStoreXML(RESULTS_FILE_LOCATION);
			
		} catch (Exception e){
			System.out.println("error loading main xml:" + e );
		}
		
			
		resultBeans = handler.getBeanFromResultDoc(0);
		
		//set up our bean to be displayed
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
    	


    }
    
    private void performBasicSearch(HttpServletRequest req, HttpServletResponse res){
    	//extract variables
		String query = (String) req.getParameter("search_query");
		String searchType = (String) req.getParameter("search_category");
		
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
			System.out.println("error loading main xml:" + e );
		}
		
		//Do a search and display the content (from results instead of main!)

		resultBeans = handler.getBeanFromResultDoc(0);	
		
	  	//set up our bean to be displayed
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
    	

    	//set up our bean to be displayed

    }
    
    
  //we can read the URI for the specific request, and then redirect appropriately
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException	{
    	doPost(req,res);
	
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	RequestDispatcher requestDispatcher; 
		//where did the user want to go?
		String uri = req.getRequestURI();
		
		if (uri.contains("cart")){ //CART PAGE
			try {
				//just set up the cart to be read 
				setUpCart(req, res);
			} catch (Exception e){
				System.out.println("setting pu cart fail: " + e );
			}
			
			if (req.getParameter("remove_cart") != null) {
				removeFromCart(req,res);
			}	
			
	    	requestDispatcher = req.getRequestDispatcher("/Cart.jsp");
	    	requestDispatcher.forward(req, res);
			
		} else if (uri.contains("results")){  //RESULT PAGE (changing page No. OR extra detail for an entry)

			String searchType = (String) req.getParameter("search_type");

			//case of search
			//we will redirect to results
			if (searchType != null){

				if (searchType.matches(".*advanced.*")){
					performAdvancedSearch(req,res);

				} else if (searchType.matches(".*basic.*")){
					performBasicSearch(req,res);
					
				}
				
			} else if(req.getParameter("add_to_cart_view") != null){
				//add to cart from extend view
				
				appendToCartPage(req,res);	

			} else if(req.getParameter("add_to_cart") != null){
				//add to cart from results
				
				appendToCartPage(req,res);
				
				processResults(req,res);

			} else {
				//just looking at results 
				
				loadResultsXML();
				
				processResults(req,res);
			}
			
	    	requestDispatcher = req.getRequestDispatcher("/Results.jsp");
	    	requestDispatcher.forward(req, res);
		} else {//MAIN PAGE
			//generate random list

			loadMainXML();

			req.setAttribute("randomBeans",generateRandomBeans(10));
			
	    	requestDispatcher = req.getRequestDispatcher("/Search.jsp");
	    	requestDispatcher.forward(req, res);
		}

	}
	
	public void appendToCartPage(HttpServletRequest req, HttpServletResponse res){
		int pubId = Integer.parseInt(req.getParameter("publication_id"));
		
		try {
			setUpCart(req, res);
			
			//we append to the loaded cart
			handler.appendToCart(pubId);	
			
			handler.updateCartXML(CART_FILE_LOCATION);
			
			cartBeans = handler.getBeanFromCartDoc(0);

    		req.getSession().setAttribute("shoppingCart", cartBeans);
    		handler.setCartToSession("shoppingCartDoc", req.getSession());
			

    		
    		
		} catch (Exception e){
			System.out.println("error adding to cart:" + e );
		}
		
	}
	
	public void removeFromCart(HttpServletRequest req, HttpServletResponse res){
		//we need to load the "current" cart doc, either from session variable, or from xml
		try {
			setUpCart(req, res);
			
			//need to remove from the document from last to first
			///we need to start by looking at the last object 		
			int totalEntries =  Integer.parseInt(req.getParameter("num_items"));

			for(int n = totalEntries-1; n >= 0; n--){
				if(req.getParameter("entry"+n) != null){
					
					//remove from the document
					handler.removeFromCart(n);
					
				}
				
			}

			handler.updateCartXML(CART_FILE_LOCATION);
			
			cartBeans = handler.getBeanFromCartDoc(0);

    		req.getSession().setAttribute("shoppingCart", cartBeans);
    		handler.setCartToSession("shoppingCartDoc", req.getSession());
			
		} catch (Exception e){
			System.out.println("error adding to cart:" + e );
		}
	}
	
	public void setUpCart(HttpServletRequest req, HttpServletResponse res) throws Exception{
		//need to load result xml
		loadResultsXML();
		
		//we should only do this for the first time or when we update the xml
		//we should reuse the session attribute
		
		//check session variable exists
		if (req.getSession().getAttribute("shoppingCartDoc") == null) {
			//we check the file now 
			if(handler.fileExists(CART_FILE_LOCATION)){
				//we load
	    		handler.loadCartXML(CART_FILE_LOCATION);
			} else {
				//we must create the cart file
				
				handler.createCartXML(CART_FILE_LOCATION);
			}
		} else {
			//we can retrieve the file from the attribute
			handler.setSessionCartToDoc("shoppingCartDoc", req.getSession());
			
		}
	}
	
	public void loadResultsXML(){
		try {
			if(handler.fileExists(RESULTS_FILE_LOCATION)){
				handler.loadResultsXML(RESULTS_FILE_LOCATION);
			}
		} catch (Exception e){
			System.out.println("Failed to load results xml: " + e);
		}
			
	}
	
	public void loadMainXML(){
		try {
			if(handler.fileExists(MAIN_FILE_LOCATION)){
				handler.loadMainXML(MAIN_FILE_LOCATION);
			}
		} catch (Exception e){
			System.out.println("Failed to load main xml: " + e);
		}
			
	}
	
	/**
	 * Processes the result page 
	 * @param req
	 * @param res
	 */
	public void processResults(HttpServletRequest req, HttpServletResponse res){
		//just set up the cart to be read 

		String entryToview = req.getParameter("entryMoreView");
		
		if(entryToview != null){ //EXPANDING VIEW TO READ MORE
			int entryToViewNum = Integer.parseInt(entryToview);
			
			resultBeans = handler.getBeanFromResultDoc(entryToViewNum);
			DBLPBean entry = resultBeans.get(0);
			
			ResultPageBean viewBean = new ResultPageBean();
			
			viewBean.setReadMore(true);
			viewBean.setReadEntry(entry);
			
			req.setAttribute("viewBean",viewBean);
			
		} else { //CHANGING PAGE NUMBER
			ResultPageBean viewBean = new ResultPageBean();
			
			//get the current page number (default = 1)
			int totalResults = handler.getNumResults();
			viewBean.setTotalResults(totalResults);
			
			String pageNo = req.getParameter("page");
			int currPageNo = Integer.parseInt(pageNo);
			viewBean.setCurr_page_num(currPageNo);
			
			if(pageNo == null){
				//default is first page
				resultBeans = handler.getBeanFromResultDoc(0);
				
				if(currPageNo > 1){
					viewBean.setLess(true);
				}
				
			} else {
				//generate the appropriate
				int startEntry = 10 * (currPageNo-1);
				
				if(startEntry > totalResults){
					//error
					return;
				} 
				
				//retrieve results
				resultBeans = handler.getBeanFromResultDoc(startEntry);
				//set up our bean to be displayed
				viewBean.setResultBeans(resultBeans); //we keep two copies
				
				//set up entry
				if(currPageNo > 1){
					viewBean.setLess(true);
					viewBean.setPrev_page_num(currPageNo-1);
				}
				if(totalResults > startEntry + 10){
					viewBean.setMore(true);
					viewBean.setNext_page_num(currPageNo+1);
				}

				req.setAttribute("viewBean",viewBean);

			}
		}

		
	}

}

