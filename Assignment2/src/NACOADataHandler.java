import com.mysql.jdbc.Connection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
/**
 * Class which deals with the data access needs of the NACOAMainServlet
 * 
 * Uses MYSQL
 * 
 * @author Felix
 *
 */
public class NACOADataHandler {
	//REFERENCING ACTION TYPE IN USER_HISTORY
	static final int ACTION_BUY = 1;
	static final int ACTION_ATC = 2;
	static final int ACTION_RFC = 3;
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/nacoadatabase";
	static final String LOCALHOST_URL = "jdbc:mysql://localhost/";
	
	//  Database credentials
	static final String USER = "root";
	static final String PASS = "";
	
	//Dummy variable
	public final String DUMMYDOS = "0000-00-00";
	
	//Tests
	private boolean initialTests = false;
	private boolean testCheckOut = false;
	private boolean testCount = false;
	
	public static void main(String [] args){
		NACOADataHandler handler= new NACOADataHandler();
		
		handler.setUpDatabase();
		//handler.createUser("fakeuser21","pw","fakeemails","k","r","r","2015-09-23","fake","fake");
		
		if(handler.databaseExists("nacoadatabase")){
			System.out.println("Test 1 Passed!");
		}
		
		if(!handler.databaseExists("nacoadatabase1")){
			System.out.println("Test 2 Passed!");
		}
		
		handler.testDatabase(handler);
		System.out.println("User name is " +handler.getUserName(0));
		
		
		//handler.addHistoryBuyEntry(1,2); //
		//handler.addHistoryAddCartEntry(1, 2);//
		//handler.addHistoryRemoveCartEntry(1, 2);//
		if(handler.getCountBooksSold(2) == 3){
			System.out.println("Test 1 Passed! ");
		} else {
			System.out.println("Test 1 Failed! ");
		}
		
		if(handler.getCountBooksNotSold(2) == 1){
			System.out.println("Test 2 Passed! ");
		} else {
			System.out.println("Test 2 Failed! ");
		}
		if(handler.getCountBooksBought(2) == 2){
			System.out.println("Test 3 Passed! ");
		} else {
			System.out.println("Test 3 Failed! ");
		}
		/*
		if(handler.getCountBooksSold(3) == 3){
			System.out.println("Test Passed! ");
		} else {
			System.out.println("Test Failed! ");
		}
		
		if(handler.getCountBooksSold(3) == 3){
			System.out.println("Test Passed! ");
		} else {
			System.out.println("Test Failed! ");
		}
		*/
	}
	
	//Runs tests to make sure the functions are working
	private void testDatabase(NACOADataHandler handler) {
		
		//NACOADataHandler handler = new NACOADataHandler();
		
		if (initialTests) {
			int user_id = testAddUser(handler);
			int book_id = testAddBook(handler, user_id);
			testChangeBook(handler, book_id);
		}
		
		if (testCheckOut) {
			//int newID = createUser("Seller", DUMMYDOS, DUMMYDOS, DUMMYDOS, DUMMYDOS, DUMMYDOS, DUMMYDOS, DUMMYDOS, DUMMYDOS);
			//addBookToCart (int user_id, int book_id, int is_sold, String dou, String dos){
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		  
			//get current date time with Calendar()
			Calendar cal = Calendar.getInstance();
			System.out.println(dateFormat.format(cal.getTime()).replace("/",  "-"));
			   
			//Need to get book_id somehow
			handler.addBookToCart(2, 3, 0, dateFormat.format(cal.getTime()).replace("/",  "-"), DUMMYDOS);
		}
		
		if (testCount) {
			System.out.println("testing count");
			maxBooksID();
			maxUsersID();
		}
		//if (testUp)
		//addBookToCart(newID, 7, 0, , DUMMYDOS);
		//handler.setUpDatabase();
		//Creating a test user
		//String username, String password, String email, String nickname, 
		//String firstname, String lastname, String dob, String address, String creditinfo
		
//		String title, String author, String picture, float price, String publisher, 
//		String dateofpublication, int pages, String isbn, String genre)
		
		
		
	}

	private void testChangeBook(NACOADataHandler handler, int bookID) {
		//Testing Change
		String changedTitle = "Selfless Idiot";
		String changedAuthor = "Bob Jobes";
		String changedPicture = "Datk Knight";
		String changedPrice = "11.11";
		String changedPublisher = "Riot Games";
		String changedDOP = "1000-10-10";
		String changedPages = "1337";
		String changedISBN = "1234-1234-1234";
		String changedGenre = "Action";
		
		System.out.println("Testing change title...");
		handler.changeBookTitle(bookID, changedTitle);
		
		if (!handler.getBookTitle(bookID).contentEquals(changedTitle)) {
			System.out.println("Test failed!");
		} else {
			System.out.println("Test passed!");
		}
		
		System.out.println("Testing change author...");
		handler.changeBookAuthor(bookID, changedAuthor);
		
		if (!handler.getBookAuthor(bookID).contentEquals(changedAuthor)) {
			System.out.println("Test failed!");
		} else {
			System.out.println("Test passed!");
		}
		
		System.out.println("Testing change picture...");
		handler.changeBookPicture(bookID, changedPicture);
		
		if (!handler.getBookPicture(bookID).contentEquals(changedPicture)) {
			System.out.println("Test failed!");
		} else {
			System.out.println("Test passed!");
		}
		
		System.out.println("Testing change price...");
		handler.changeBookPrice(bookID, changedPrice);
		
		if (!handler.getBookPrice(bookID).contentEquals(changedPrice)) {
			System.out.println("Test failed!");
		} else {
			System.out.println("Test passed!");
		}
		
		System.out.println("Testing change publisher...");
		handler.changeBookPublisher(bookID, changedPublisher);
		
		if (!handler.getBookPublisher(bookID).contentEquals(changedPublisher)) {
			System.out.println("Test failed!");
		} else {
			System.out.println("Test passed!");
		}
		
		System.out.println("Testing change dop...");
		handler.changeBookDOP(bookID, changedDOP);
		
		if (!handler.getBookDOP(bookID).contentEquals(changedDOP)) {
			System.out.println("Test failed!");
		} else {
			System.out.println("Test passed!");
		}
		
		System.out.println("Testing change pages...");
		handler.changeBookPages(bookID, changedPages);
		
		if (!handler.getBookPages(bookID).contentEquals(changedPages)) {
			System.out.println("Test failed!");
		} else {
			System.out.println("Test passed!");
		}
		
		System.out.println("Testing change isbn...");
		handler.changeBookISBN(bookID, changedISBN);
		
		if (!handler.getBookISBN(bookID).contentEquals(changedISBN)) {
			System.out.println("Test failed!");
		} else {
			System.out.println("Test passed!");
		}
		
		System.out.println("Testing change genre...");
		handler.changeBookGenre(bookID, changedGenre);
		
		if (!handler.getBookGenre(bookID).contentEquals(changedGenre)) {
			System.out.println("Test failed!");
		} else {
			System.out.println("Test passed!");
		}
	}

	//Test add book
	private int testAddBook(NACOADataHandler handler, int id) {
		String tTitle1 = "The Jack";
		String tAuthor1 = "Rowl Darn";
		String tPicture1 = "No picture bro";
		String tPrice1 = "100.50";
		String tPublisher1 = "Blizzard";
		String tdop1 = "1920-11-01";
		String tpages1 = "204";
		String tisbn1 = "123441-23322-111";
		String tGenre1 = "Mystery";
		String tDate = "2015-10-12";
		String tDescrip1 = "asdasda";
		System.out.println("Creating Book...");
		int bookID = handler.createBook(id, tDate, tTitle1, tAuthor1, tPicture1, tPrice1, tPublisher1, tdop1, tpages1, tisbn1, tGenre1, tDescrip1);
		
		System.out.println("Testing get book title...");
		if (!handler.getBookTitle(bookID).contentEquals(tTitle1)) {
			System.out.println("Test Failed!");
		} else {
			System.out.println("Test Passed!");
		}
		
		System.out.println("Testing get book author...");
		if (!handler.getBookAuthor(bookID).contentEquals(tAuthor1)) {
			System.out.println("Test Failed!");
		} else {
			System.out.println("Test Passed!");
		}
		
		System.out.println("Testing get book Picture...");
		if (!handler.getBookPicture(bookID).contentEquals(tPicture1)) {
			System.out.println("Test Failed!");
		} else {
			System.out.println("Test Passed!");
		}
		
		System.out.println("Testing get book Price...");
		if (!handler.getBookPrice(bookID).contentEquals(tPrice1)) {
			System.out.println("Test Failed!");
		} else {
			System.out.println("Test Passed!");
		}
		
		System.out.println("Testing get book Publisher...");
		if (!handler.getBookPublisher(bookID).contentEquals(tPublisher1)) {
			System.out.println("Test Failed!");
		} else {
			System.out.println("Test Passed!");
		}
		
		System.out.println("Testing get book dop...");
		if (!handler.getBookDOP(bookID).contentEquals(tdop1)) {
			System.out.println("Test Failed!");
		} else {
			System.out.println("Test Passed!");
		}
		
		System.out.println("Testing get book pages...");
		if (!handler.getBookPages(bookID).contentEquals(tpages1)) {
			System.out.println("Test Failed!");
		} else {
			System.out.println("Test Passed!");
		}
		
		System.out.println("Testing get book isbn...");
		if (!handler.getBookISBN(bookID).contentEquals(tisbn1)) {
			System.out.println("Test Failed!");
		} else {
			System.out.println("Test Passed!");
		}
		
		System.out.println("Testing get book Genre...");
		if (!handler.getBookGenre(bookID).contentEquals(tGenre1)) {
			System.out.println("Test Failed!");
		} else {
			System.out.println("Test Passed!");
		}
		
		return bookID;
	}

	//Test add user
	private int testAddUser(NACOADataHandler handler) {
		// TODO Auto-generated method stub
		String tUserN1 = "Riked";
		String tPass1 = "password123";
		String tEmail1 = "moetfowl@yahoo.com.au";
		String tNickN1 = "DeathToAllThe";
		String tFirstN1 = "Richard";
		String tLastN1 = "Zhang";
		String tdob1 = "1990-01-01";
		String tAddress1 = "Fake Address 1";
		String tCreditInfo1 = "Fake Credit info";
		String tDescrip = "Blah Blah!";
		
		System.out.println("Creating user...");
		int id = handler.createUser(tUserN1, tPass1, tEmail1, tNickN1, tFirstN1, tLastN1, tdob1, tAddress1, tCreditInfo1, tDescrip);
		//handler.addBookToCart(id, 0, 0, "1990-09-09", "1991-01-01");
		
		System.out.println("Testing get email...");
		
		if (!handler.getEmail(id).contentEquals(tEmail1)) {
			System.out.println("Test Failed");
		} else {
			System.out.println("Test Passed");
		}
		
		System.out.println("Testing get username...");
		
		if (!handler.getUserName(id).contentEquals(tUserN1)) {
			System.out.println("Test Failed");
		} else {
			System.out.println("Test Passed");
		}
		
		System.out.println("Testing get creditcarddetails...");
		
		if (!handler.getCreditCardDetails(id).contentEquals(tCreditInfo1)) {
			System.out.println("Test Failed");
		} else {
			System.out.println("Test Passed");
		}
		
		return id;
	}

	/**
	 * Given the location of an SQL database set up script, runs the script
	 * @param location
	 */
	public void setUpDatabase(){
		Connection conn = null;
		Statement stmt = null;
		
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//STEP 3: Open a connection
			//System.out.println("Connecting to database...");
			conn = (Connection) DriverManager.getConnection(LOCALHOST_URL,USER,PASS);
		
			//CREATE DATABASE
			System.out.println("Creating database");
			String createSQL = "CREATE DATABASE IF NOT EXISTS `nacoadatabase` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci";
			stmt = conn.createStatement();
			stmt.executeUpdate(createSQL);
			
			stmt.close();
			conn.close();
			

			//STEP 3: Open a connection with the database
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//DROP THESE TABLES IF THEY EXIST
			
			LinkedList<String> dropQueries = new LinkedList<String>(Arrays.asList(
					"DROP TABLE IF EXISTS `admins`", 
					"DROP TABLE IF EXISTS `books`", 
					"DROP TABLE IF EXISTS `users`", 
					"DROP TABLE IF EXISTS `user_customer_books`", 
					"DROP TABLE IF EXISTS `user_history`", 
					"DROP TABLE IF EXISTS `user_seller_books`"));

			for(int i = 0; i < 6; i++){
				PreparedStatement dStmt = conn.prepareStatement(dropQueries.get(i), Statement.RETURN_GENERATED_KEYS);
				dStmt.executeUpdate();
				dStmt.close();
			}
			
			
			//CREATE OUR TABLES
			System.out.println("Creating tables");
			
			String cBooks = "CREATE TABLE IF NOT EXISTS `books` ("
					+ " `id` int(11) NOT NULL,"
					+ " `title` varchar(52) NOT NULL,"
					+ " `author` varchar(52) NOT NULL,"
					+ "  `picture` varchar(128) NOT NULL,"
					+ " `price` float NOT NULL,"
					+ "  `publisher` varchar(52) NOT NULL,"
				    + " `dateofpublication` date NOT NULL,"
					+ "  `pages` int(11) NOT NULL,"
					+ "  `isbn` varchar(20) NOT NULL,"
					+ "  `genre` varchar(20) NOT NULL,"
					+ "  `description` text NULL"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1";
			
			String cUsers  = "CREATE TABLE IF NOT EXISTS `users` ("
					  + "   `id` int(11) NOT NULL,"
					  + "  `username` varchar(32) NOT NULL,"
					  + "  `password` varchar(128) NOT NULL,"
					  + "  `email` varchar(52) NOT NULL,"
					  + "  `firstname` varchar(20) NOT NULL,"
					  + "  `lastname` varchar(20) NOT NULL,"
					  + "  `nickname` varchar(20) NOT NULL,"
					  + "  `dob` date NOT NULL,"
					  + "  `address` text NOT NULL,"
					  + "  `creditcarddetails` text NOT NULL,"
					  + "  `description` text NULL,"
					  + "  `is_halted` tinyint(1) NOT NULL DEFAULT '0',"
					  + "  `is_admin` tinyint(1) NOT NULL DEFAULT '0'"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1";
			
			String cUBooks  = "CREATE TABLE IF NOT EXISTS `user_customer_books` ("
					 + " `user_id` int(11) NOT NULL,"
					 + " `book_id` int(11) NOT NULL,"
					 + " `is_bought` tinyint(1) NOT NULL DEFAULT '0'"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1";
			
			String cHistor  = "CREATE TABLE IF NOT EXISTS `user_history` ("
					  + "`user_id` int(11) NOT NULL,"
					  + "`book_id` int(11) NOT NULL,"
					  + "`action_type` int(11) NOT NULL,"
					  + "`time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1";
			
			String cSBooks  = "CREATE TABLE IF NOT EXISTS `user_seller_books` ("
					  + "`user_id` int(11) NOT NULL,"
					  + "`book_id` int(11) NOT NULL,"
					  + "`is_sold` tinyint(1) NOT NULL DEFAULT '0',"
					  + "`dateofupload` date NOT NULL,"
					  + "`dateofsale` date NULL,"
					  + " `is_paused` tinyint(1) NOT NULL DEFAULT '0'"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1";
			
			LinkedList<String> creatTables = new LinkedList<String>(Arrays.asList(cBooks, cUsers, cUBooks, cSBooks, cHistor));
			
			for(int i = 0; i < 5; i++){
				PreparedStatement cStmt = conn.prepareStatement(creatTables.get(i));
				cStmt.executeUpdate();
				cStmt.close();
			}
			
			//SET UP OUR CONSTRAINTS
			String query2 = "ALTER TABLE `books` ADD PRIMARY KEY (`id`)";
			String query3 = "ALTER TABLE `users` ADD PRIMARY KEY (`id`)";
			String query4 = "ALTER TABLE `user_customer_books`"
						  + "ADD KEY `user_id` (`user_id`),"
						  + "ADD KEY `book_id` (`book_id`)";
			String query5 = "ALTER TABLE `user_seller_books`"
						  + "ADD KEY `user_id` (`user_id`),"
						  + "ADD KEY `book_id` (`book_id`)";
			String query7 = "ALTER TABLE `books`"
						  + "MODIFY `id` int(11) NOT NULL AUTO_INCREMENT";
			String query8 = "ALTER TABLE `users`"
						  + "MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2";
			String query9 =	"ALTER TABLE `user_customer_books`"
						  + "ADD CONSTRAINT `user_customer_books_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),"
						  + "ADD CONSTRAINT `user_customer_books_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)";
			String query10 = "ALTER TABLE `user_seller_books`"
						  + "ADD CONSTRAINT `user_seller_books_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),"
						  + "ADD CONSTRAINT `user_seller_books_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)";		
											
					
			LinkedList<String> altQueries = new LinkedList<String>(Arrays.asList(query2, query3, query4, query5
					, query7, query8, query9, query10));
			
			for(int i = 0; i < 8; i++){
				PreparedStatement aStmt = conn.prepareStatement(altQueries.get(i));
				aStmt.executeUpdate();
				aStmt.close();
			}
			
			
			System.out.println("...closing connection");
			conn.close();
			
		} catch (SQLException se) {
		  //Handle errors for JDBC
		      se.printStackTrace();
		   } catch (Exception e) {
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   } finally {
		      //finally block used to close resources
		 
			  try {
			     if(conn!=null)
			        conn.close();
			  } catch (SQLException se) {
			     se.printStackTrace();
			  } //end finally try
		   } //end try
	      //
	      
	    

	}
	
	public int createUser(String username, String password, String email, String nickname, 
			String firstname, String lastname, String dob, String address, String creditinfo, String description){
		int auto_id = 0;
		Connection conn = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//STEP 3: Open a connection
			//System.out.println("Connecting to database...");
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);

			//STEP 4: Execute a query
			String sql;
			
			sql = "INSERT INTO `users` "
					 + "(`username`, `password`, `email`, `firstname`, `lastname`, `nickname`, `dob`, `address`, `creditcarddetails`, `description`, `is_halted`, `is_admin`) "
			  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				

			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, email);
			stmt.setString(4, firstname);
			stmt.setString(5, lastname);
			stmt.setString(6, nickname);
			stmt.setString(7, dob);
			stmt.setString(8, address);
			stmt.setString(9, creditinfo);
			stmt.setString(10, description);
			stmt.setInt(11, 1); //user is halted until email is verified
			stmt.setInt(12, 0); //user is assumed not admin
			
			stmt.executeUpdate();
			
			//we need to get the auto generated id for session login
			//we will use this id to reference the user during login
			ResultSet rs = stmt.getGeneratedKeys();
		    rs.next();
		    auto_id = rs.getInt(1);
		    
			System.out.println("...closing connection");

		    stmt.close();
			conn.close();
			
		} catch (SQLException se) {
		  //Handle errors for JDBC
		      se.printStackTrace();
		   } catch (Exception e) {
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   } finally {
		      //finally block used to close resources
		 
			  try {
			     if(conn!=null)
			        conn.close();
			  } catch (SQLException se) {
			     se.printStackTrace();
			  } //end finally try
		   } //end try
	      //
	      
	
		return auto_id;
	}
	
	/*
	 * Creates a book on the database.
	 * 
	 */
	public int createBook (int user_id, String date, String title, String author, String picture, String price, String publisher, 
			String dateofpublication, String pages, String isbn, String genre, String description) {
		int auto_id = 0;
		Connection conn = null;
		
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//STEP 3: Open a connection
			//System.out.println("Connecting to database...");
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			String sql = "INSERT INTO `books` "
					 + "(`title`, `author`, `picture`, `price`, `publisher`, `dateofpublication`, `pages`, `isbn`, `genre`, `description`) "
			  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			//Price conversion
			float f = Float.parseFloat(price);
			
			//Int conversion
			Integer pageNum = Integer.parseInt(pages);
			
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, title);
			stmt.setString(2, author);
			stmt.setString(3, picture);
			stmt.setFloat(4, f);
			stmt.setString(5, publisher);
			stmt.setString(6, dateofpublication);
			stmt.setInt(7, pageNum);
			stmt.setString(8, isbn);
			stmt.setString(9, genre);
			stmt.setString(10, description);
			
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
		    rs.next();
		    auto_id = rs.getInt(1);
		    
			sql = "INSERT INTO `user_seller_books` "
					 + "(`user_id`, `book_id`, `is_sold`, `dateofupload`, `dateofsale`, `is_paused`) "
			  + "VALUES (?, ?, ?, ?, ?, ?)";

			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, user_id);
			stmt.setInt(2, auto_id);
			stmt.setInt(3, 0);
			stmt.setString(4, date);
			stmt.setString(5, DUMMYDOS);
			stmt.setInt(6, 0);

			stmt.executeUpdate();
			
			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		return auto_id;
	}
	
	public boolean databaseExists(String dbName){
		Connection conn = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//STEP 3: Open a connection
			//System.out.println("Connecting to database...");
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);

			//CHECK IF EXISTS
			ResultSet rs = conn.getMetaData().getCatalogs();
			 
			while(rs.next()){
				String catalogs = rs.getString(1);
				
				if(dbName.equals(catalogs)){
					System.out.println("the database "+dbName+" exists");
					return true;
				}
			}
			
		} catch (Exception e){
			 e.printStackTrace();
		}
		
		return false;
	}
	
	//Gets Username
	public String getUserName (int user_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String username = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM users WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, user_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				username = rs.getString("username");
		
			}
			
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
			return username;
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		
		return username; 
	}
	
	//Gets userID using book id
	public int getUserID (int book_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		int username = 0;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM user_seller_books WHERE (user_id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, book_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				username = rs.getInt("user_id");
		
			}
			
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
			return username;
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		
		return username; 
	}
		
	//Gets book title
	public String getBookTitle (int book_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String booktTitle = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM books WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, book_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				booktTitle = rs.getString("title");
		
			}
			
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
			return booktTitle;
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		
		return booktTitle; 
	}
		
	//Gets book author
	public String getBookAuthor (int book_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String bookAuthor = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM books WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, book_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				bookAuthor = rs.getString("author");
		
			}
			
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
			return bookAuthor;
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		
		return bookAuthor; 
	}
	
	//Gets book picture
	public String getBookPicture (int book_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String answer = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM books WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, book_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				answer = rs.getString("picture");
		
			}
			
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
			return answer;
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		
		return answer; 
	}
	
	//Gets book price
	//TODO Do we need to return it as a float??
	public String getBookPrice (int book_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String answer = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM books WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, book_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				answer = String.format("%.2f", rs.getFloat("price"));;
				
			}
			
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
			return answer;
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		
		return answer; 
	}
	
	//Gets book publisher
	public String getBookPublisher (int book_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String answer = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM books WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, book_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				answer = rs.getString("publisher");
		
			}
			
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
			return answer;
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		
		return answer; 
	}
	
	//Gets book date of publication
	public String getBookDOP (int book_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String answer = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM books WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, book_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				answer = rs.getString("dateofpublication");
		
			}
			
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
			return answer;
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		
		return answer; 
	}
	
	//Gets book pages
	public String getBookPages (int book_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String answer = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM books WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, book_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				answer = Integer.toString(rs.getInt("pages"));
		
			}
			
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
			return answer;
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		return answer;
	}
	
	//Gets book isbn
	public String getBookISBN (int book_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String answer = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM books WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, book_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				answer = rs.getString("isbn");
		
			}
			
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
			return answer;
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		
		return answer; 
	}
	
	//Gets book genre
	public String getBookGenre (int book_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String answer = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM books WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, book_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				answer = rs.getString("genre");
		
			}
			
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
			return answer;
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		
		return answer; 
	}
		
	//Gets credit card details
	public String getCreditCardDetails (int user_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String credcarddet = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM users WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, user_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				credcarddet = rs.getString("creditcarddetails");
		
			}
					  //STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
			return credcarddet;
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		
		return credcarddet; 
	}
	
	//Gets email
	public String getEmail (int user_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String email = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM users WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, user_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				email = rs.getString("email");
		
			}
					  //STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
			return email;
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		
		return email; 
	}
	
	//Gets book
	public int getBookAvail (int book_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		int available = 0;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM user_seller_books WHERE (book_id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, book_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				available = rs.getInt("is_sold");
		
			}
					  //STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
			return available;
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		
		return available; 
	}
	
	public int isBookPaused (int book_id){
		Connection conn = null;
		PreparedStatement stmt = null;
		int available = 0;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM user_seller_books WHERE (book_id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, book_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				available = rs.getInt("is_paused");
		
			}
					  //STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
			return available;
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		
		return available; 
	}
	
	//Delete book
	public void deleteBook (int book_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "DELETE FROM books WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, book_id);
			stmt.executeUpdate();

			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
	}
	
	//Delete book from cart
	public void deleteBookCart (int book_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "DELETE FROM user_customer_books WHERE (book_id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, book_id);
			stmt.executeUpdate();

			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
	}

	//Add book to cart
	public void addBookToCart (int user_id, int book_id, int is_sold, String dou, String dos){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "INSERT INTO `user_customer_books` "
					 + "(`user_id`, `book_id`, `is_sold`, `dateofupload`, `dateofsale`) "
			  + "VALUES (?, ?, ?, ?, ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, user_id);
			stmt.setInt(2, book_id);
			stmt.setInt(3, is_sold);
			stmt.setString(4, dou);
			stmt.setString(5, dos);
			stmt.executeUpdate();

			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
	}
	
	//Sold book
	//TODO
	//Do we need to remove the book from the shopping cart or database??
	public void setBookSold (int book_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "UPDATE user_seller_books SET is_sold WHERE (book_id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, 1);
			stmt.setInt(2, book_id);
			stmt.executeUpdate();
			
			sql = "UPDATE user_customer_books SET is_sold WHERE (book_id = ?)";
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, 1);
			stmt.setInt(2, book_id);
			stmt.executeUpdate();

			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
	}
	
	//Gets the shopping cart as beans
	public ArrayList<NACOABean> getShoppingCart (int user_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ArrayList<Integer> listOfBooks = new ArrayList<Integer>();
		//String bookName = null;
		int bookID = 0;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			String sql = "SELECT * FROM user_customer_books WHERE (user_id = ?)";
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, user_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
			
			//STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				bookID = rs.getInt("book_id");
				listOfBooks.add(bookID);
			}

			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
			//return listOfBooks;
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		//Convert books to beans
		ArrayList<NACOABean> realBooks = new ArrayList<NACOABean>();
		
		int size = 0;
		
		while (size != listOfBooks.size()) {
			int bID = listOfBooks.get(size);
			
			NACOABean book = new NACOABean();
			
			//Set Details
			book.setBookID(bID);
			book.setBooktitle(this.getBookTitle(bID));
			book.setAuthor(this.getBookAuthor(bID));
			book.setPicture(this.getBookPicture(bID));
			book.setPrice(this.getBookPrice(bID));
			book.setPublisher(this.getBookPublisher(bID));
			book.setDOP(this.getBookDOP(bID));
			book.setPages(this.getBookPages(bID));
			book.setIsbn(this.getBookISBN(bID));
			book.setGenre(this.getBookGenre(bID));
			
			//Add book
			realBooks.add(book);
			
			size++;
		}
		
		
		return realBooks;
	}
	
	//Gets the shopping cart as beans
	public ArrayList<NACOABean> getSellingList (int user_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ArrayList<Integer> listOfBooks = new ArrayList<Integer>();
		//String bookName = null;
		int bookID = 0;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			String sql = "SELECT * FROM user_seller_books WHERE (user_id = ?)";
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, user_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
			//TODO
			//STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				bookID = rs.getInt("book_id");
				listOfBooks.add(bookID);
			}

			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
			//return listOfBooks;
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		//Convert books to beans
		ArrayList<NACOABean> realBooks = new ArrayList<NACOABean>();
		
		int size = 0;
		
		while (size != listOfBooks.size()) {
			int bID = listOfBooks.get(size);
			if (isBookPaused(bID) == 0) {
				NACOABean book = new NACOABean();
				
				//Set Details
				book.setBookID(bID);
				book.setBooktitle(this.getBookTitle(bID));
				book.setAuthor(this.getBookAuthor(bID));
				book.setPicture(this.getBookPicture(bID));
				book.setPrice(this.getBookPrice(bID));
				book.setPublisher(this.getBookPublisher(bID));
				book.setDOP(this.getBookDOP(bID));
				book.setPages(this.getBookPages(bID));
				book.setIsbn(this.getBookISBN(bID));
				book.setGenre(this.getBookGenre(bID));
				
				//Add book
				realBooks.add(book);
			}
			size++;
		}
		
		
		return realBooks;
	}
	
	//Gets the shopping cart as beans
	public ArrayList<NACOABean> getPausedList (int user_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ArrayList<Integer> listOfBooks = new ArrayList<Integer>();
		//String bookName = null;
		int bookID = 0;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			String sql = "SELECT * FROM user_seller_books WHERE (user_id = ?)";
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, user_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
			//TODO
			//STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				bookID = rs.getInt("book_id");
				listOfBooks.add(bookID);
			}

			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
			//return listOfBooks;
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		//Convert books to beans
		ArrayList<NACOABean> realBooks = new ArrayList<NACOABean>();
		
		int size = 0;
		
		while (size != listOfBooks.size()) {
			int bID = listOfBooks.get(size);
			if (isBookPaused(bID) == 1) {
				NACOABean book = new NACOABean();
				
				//Set Details
				book.setBookID(bID);
				book.setBooktitle(this.getBookTitle(bID));
				book.setAuthor(this.getBookAuthor(bID));
				book.setPicture(this.getBookPicture(bID));
				book.setPrice(this.getBookPrice(bID));
				book.setPublisher(this.getBookPublisher(bID));
				book.setDOP(this.getBookDOP(bID));
				book.setPages(this.getBookPages(bID));
				book.setIsbn(this.getBookISBN(bID));
				book.setGenre(this.getBookGenre(bID));
				
				//Add book
				realBooks.add(book);
			}
			size++;
		}
		
		
		return realBooks;
	}
	
	//Change book title
	public void changeBookTitle(int book_id, String title){
		
		Connection conn = null;
		PreparedStatement stmt = null;

		if (!title.contentEquals("")) {
			try {
				//STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");
				//
				conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
				
				//STEP 4: Execute a query
				//System.out.println("Creating statement...");
				
				//Statement to change details of the database
				String sql = "UPDATE books SET title=? WHERE (id = ?)";
		
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, title);
				stmt.setInt(2, book_id);
				stmt.executeUpdate();
	
				//STEP 6: Clean-up environment
				stmt.close();
				conn.close();	
				
			} catch (SQLException se) {
				//Handle errors for JDBC
				    se.printStackTrace();
			} catch (Exception e) {
			    //Handle errors for Class.forName
			    e.printStackTrace();
			} finally {
			    //finally block used to close resources
			 
				try {
				   if(conn!=null)
				      conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				} //end finally try
			} //end try
		}
	}
	
	//Change book author
	public void changeBookAuthor(int book_id, String author){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		if (!author.contentEquals("")) {
			try {
				//STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");
				//
				conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
				
				//STEP 4: Execute a query
				//System.out.println("Creating statement...");
				
				//Statement to change details of the database
				String sql = "UPDATE books SET author=? WHERE (id = ?)";
		
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, author);
				stmt.setInt(2, book_id);
				stmt.executeUpdate();
	
				//STEP 6: Clean-up environment
				stmt.close();
				conn.close();	
				
			} catch (SQLException se) {
				//Handle errors for JDBC
				    se.printStackTrace();
			} catch (Exception e) {
			    //Handle errors for Class.forName
			    e.printStackTrace();
			} finally {
			    //finally block used to close resources
			 
				try {
				   if(conn!=null)
				      conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				} //end finally try
			} //end try
		}
	}
	
	//Change book picture
	public void changeBookPicture(int book_id, String picture){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		if (!picture.contentEquals("")) {
			try {
				//STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");
				//
				conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
				
				//STEP 4: Execute a query
				//System.out.println("Creating statement...");
				
				//Statement to change details of the database
				String sql = "UPDATE books SET picture=? WHERE (id = ?)";
		
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, picture);
				stmt.setInt(2, book_id);
				stmt.executeUpdate();
	
				//STEP 6: Clean-up environment
				stmt.close();
				conn.close();	
				
			} catch (SQLException se) {
				//Handle errors for JDBC
				    se.printStackTrace();
			} catch (Exception e) {
			    //Handle errors for Class.forName
			    e.printStackTrace();
			} finally {
			    //finally block used to close resources
			 
				try {
				   if(conn!=null)
				      conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				} //end finally try
			} //end try
		}
	}
	
	//Change book price
	public void changeBookPrice(int book_id, String price){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		if (!price.contentEquals("")) {
			try {
				//STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");
				//
				conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
				
				//STEP 4: Execute a query
				//System.out.println("Creating statement...");
				
				//Statement to change details of the database
				String sql = "UPDATE books SET price=? WHERE (id = ?)";
		
				//Price conversion
				float f = Float.parseFloat(price);
				
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setFloat(1, f);
				stmt.setInt(2, book_id);
				stmt.executeUpdate();
	
				//STEP 6: Clean-up environment
				stmt.close();
				conn.close();	
				
			} catch (SQLException se) {
				//Handle errors for JDBC
				    se.printStackTrace();
			} catch (Exception e) {
			    //Handle errors for Class.forName
			    e.printStackTrace();
			} finally {
			    //finally block used to close resources
			 
				try {
				   if(conn!=null)
				      conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				} //end finally try
			} //end try
		}
	}
	
	//Change book publisher
	public void changeBookPublisher(int book_id, String publisher){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		if (!publisher.contentEquals("")) {
			try {
				//STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");
				//
				conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
				
				//STEP 4: Execute a query
				//System.out.println("Creating statement...");
				
				//Statement to change details of the database
				String sql = "UPDATE books SET publisher=? WHERE (id = ?)";
		
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, publisher);
				stmt.setInt(2, book_id);
				stmt.executeUpdate();
	
				//STEP 6: Clean-up environment
				stmt.close();
				conn.close();	
				
			} catch (SQLException se) {
				//Handle errors for JDBC
				    se.printStackTrace();
			} catch (Exception e) {
			    //Handle errors for Class.forName
			    e.printStackTrace();
			} finally {
			    //finally block used to close resources
			 
				try {
				   if(conn!=null)
				      conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				} //end finally try
			} //end try
		}
	}
	
	//Change book date of publication
	public void changeBookDOP(int book_id, String dop){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		if (!dop.contentEquals("")) {
			try {
				//STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");
				//
				conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
				
				//STEP 4: Execute a query
				//System.out.println("Creating statement...");
				
				//Statement to change details of the database
				String sql = "UPDATE books SET dateofpublication=? WHERE (id = ?)";
				
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, dop);
				stmt.setInt(2, book_id);
				stmt.executeUpdate();
	
				//STEP 6: Clean-up environment
				stmt.close();
				conn.close();	
				
			} catch (SQLException se) {
				//Handle errors for JDBC
				    se.printStackTrace();
			} catch (Exception e) {
			    //Handle errors for Class.forName
			    e.printStackTrace();
			} finally {
			    //finally block used to close resources
			 
				try {
				   if(conn!=null)
				      conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				} //end finally try
			} //end try
		}
	}
	
	//Change book pages
	public void changeBookPages(int book_id, String pages){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		if (!pages.contentEquals("")) {
			try {
				//STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");
				//
				conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
				
				//STEP 4: Execute a query
				//System.out.println("Creating statement...");
				
				//Convert to int
				Integer pageNum = Integer.parseInt(pages);
				
				//Statement to change details of the database
				String sql = "UPDATE books SET pages=? WHERE (id = ?)";
				
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setInt(1, pageNum);
				stmt.setInt(2, book_id);
				stmt.executeUpdate();
	
				//STEP 6: Clean-up environment
				stmt.close();
				conn.close();	
				
			} catch (SQLException se) {
				//Handle errors for JDBC
				    se.printStackTrace();
			} catch (Exception e) {
			    //Handle errors for Class.forName
			    e.printStackTrace();
			} finally {
			    //finally block used to close resources
			 
				try {
				   if(conn!=null)
				      conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				} //end finally try
			} //end try
		}
	}
	
	//Change book isbn
	public void changeBookISBN(int book_id, String isbn){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		if (!isbn.contentEquals("")) {
			try {
				//STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");
				//
				conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
				
				//STEP 4: Execute a query
				//System.out.println("Creating statement...");
				
				//Statement to change details of the database
				String sql = "UPDATE books SET isbn=? WHERE (id = ?)";
				
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, isbn);
				stmt.setInt(2, book_id);
				stmt.executeUpdate();
	
				//STEP 6: Clean-up environment
				stmt.close();
				conn.close();	
				
			} catch (SQLException se) {
				//Handle errors for JDBC
				    se.printStackTrace();
			} catch (Exception e) {
			    //Handle errors for Class.forName
			    e.printStackTrace();
			} finally {
			    //finally block used to close resources
			 
				try {
				   if(conn!=null)
				      conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				} //end finally try
			} //end try
		}
	}
	
	//Change book genre
	public void changeBookGenre(int book_id, String genre){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		if (!genre.contentEquals("")) {
			try {
				//STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");
				//
				conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
				
				//STEP 4: Execute a query
				//System.out.println("Creating statement...");
				
				//Statement to change details of the database
				String sql = "UPDATE books SET genre=? WHERE (id = ?)";
				
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, genre);
				stmt.setInt(2, book_id);
				stmt.executeUpdate();
	
				//STEP 6: Clean-up environment
				stmt.close();
				conn.close();	
				
			} catch (SQLException se) {
				//Handle errors for JDBC
				    se.printStackTrace();
			} catch (Exception e) {
			    //Handle errors for Class.forName
			    e.printStackTrace();
			} finally {
			    //finally block used to close resources
			 
				try {
				   if(conn!=null)
				      conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				} //end finally try
			} //end try
		}
	}
	
	//Change book genre
	public void changeBookDescription(int book_id, String description){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		if (!description.contentEquals("")) {
			try {
				//STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");
				//
				conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
				
				//STEP 4: Execute a query
				//System.out.println("Creating statement...");
				
				//Statement to change details of the database
				String sql = "UPDATE books SET description=? WHERE (id = ?)";
				
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, description);
				stmt.setInt(2, book_id);
				stmt.executeUpdate();
	
				//STEP 6: Clean-up environment
				stmt.close();
				conn.close();	
				
			} catch (SQLException se) {
				//Handle errors for JDBC
				    se.printStackTrace();
			} catch (Exception e) {
			    //Handle errors for Class.forName
			    e.printStackTrace();
			} finally {
			    //finally block used to close resources
			 
				try {
				   if(conn!=null)
				      conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				} //end finally try
			} //end try
		}
	}
		
	//gets ID from username
	public int getId (String username){
		Connection conn = null;
		PreparedStatement stmt = null;
		int id = -1;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM users WHERE (username = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, username);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				id = rs.getInt("id");
		
			}
					  //STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
			return id;
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
			
			
		return id; 
	}
	
public void changeUserDetails(int user_id, String password, String email, String nickname, 
		String firstname, String lastname, String dob, String address, String creditinfo) {
		
		changePassword(user_id, password);
		changeEmail(user_id, email);
		changeNickname(user_id, nickname);
		changeFirstname(user_id, firstname);
		changeLastname(user_id, lastname);
		changeDOB(user_id, dob);
		changeAddress(user_id, address);
		changeCreditInfo(user_id, creditinfo);
	}

public void changeEmail(int user_id, String email) {
	
	Connection conn = null;
	PreparedStatement stmt = null;

	if (!email.contentEquals("")) {
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			//Statement to change details of the database
			String sql = "UPDATE users SET email=? WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, email);
			stmt.setInt(2, user_id);
			stmt.executeUpdate();

			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
	}
}

public void changePassword(int user_id, String password) {
	
	Connection conn = null;
	PreparedStatement stmt = null;

	if (!password.contentEquals("")) {
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			//Statement to change details of the database
			String sql = "UPDATE users SET password=? WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, password);
			stmt.setInt(2, user_id);
			stmt.executeUpdate();

			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
	}
}

public void changeNickname(int user_id, String nickname) {
	
	Connection conn = null;
	PreparedStatement stmt = null;

	if (!nickname.contentEquals("")) {
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			//Statement to change details of the database
			String sql = "UPDATE users SET nickname=? WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, nickname);
			stmt.setInt(2, user_id);
			stmt.executeUpdate();

			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
	}
}

public void changeFirstname(int user_id, String firstname) {
	
	Connection conn = null;
	PreparedStatement stmt = null;

	if (!firstname.contentEquals("")) {
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			//Statement to change details of the database
			String sql = "UPDATE users SET firstname=? WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, firstname);
			stmt.setInt(2, user_id);
			stmt.executeUpdate();

			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
	}
}

public void changeLastname(int user_id, String lastname) {
	
	Connection conn = null;
	PreparedStatement stmt = null;

	if (!lastname.contentEquals("")) {
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			//Statement to change details of the database
			String sql = "UPDATE users SET lastname=? WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, lastname);
			stmt.setInt(2, user_id);
			stmt.executeUpdate();

			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
	}
}

	public void changeDOB(int user_id, String dob) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
	
		if (!dob.contentEquals("")) {
			try {
				//STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");
				//
				conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
				
				//STEP 4: Execute a query
				//System.out.println("Creating statement...");
				
				//Statement to change details of the database
				String sql = "UPDATE users SET dob=? WHERE (id = ?)";
		
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, dob);
				stmt.setInt(2, user_id);
				stmt.executeUpdate();
	
				//STEP 6: Clean-up environment
				stmt.close();
				conn.close();	
				
			} catch (SQLException se) {
				//Handle errors for JDBC
				    se.printStackTrace();
			} catch (Exception e) {
			    //Handle errors for Class.forName
			    e.printStackTrace();
			} finally {
			    //finally block used to close resources
			 
				try {
				   if(conn!=null)
				      conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				} //end finally try
			} //end try
		}
	}

	public void changeAddress(int user_id, String address) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
	
		if (!address.contentEquals("")) {
			try {
				//STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");
				//
				conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
				
				//STEP 4: Execute a query
				//System.out.println("Creating statement...");
				
				//Statement to change details of the database
				String sql = "UPDATE users SET address=? WHERE (id = ?)";
		
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, address);
				stmt.setInt(2, user_id);
				stmt.executeUpdate();
	
				//STEP 6: Clean-up environment
				stmt.close();
				conn.close();	
				
			} catch (SQLException se) {
				//Handle errors for JDBC
				    se.printStackTrace();
			} catch (Exception e) {
			    //Handle errors for Class.forName
			    e.printStackTrace();
			} finally {
			    //finally block used to close resources
			 
				try {
				   if(conn!=null)
				      conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				} //end finally try
			} //end try
		}
	}

	public void changeCreditInfo(int user_id, String creditinfo) {
		
		Connection conn = null;
		PreparedStatement stmt = null;

		if (!creditinfo.contentEquals("")) {
			try {
				//STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");
				//
				conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
				
				//STEP 4: Execute a query
				//System.out.println("Creating statement...");
				
				//Statement to change details of the database
				String sql = "UPDATE users SET creditcarddetails=? WHERE (id = ?)";
		
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, creditinfo);
				stmt.setInt(2, user_id);
				stmt.executeUpdate();
	
				//STEP 6: Clean-up environment
				stmt.close();
				conn.close();	
				
			} catch (SQLException se) {
				//Handle errors for JDBC
				    se.printStackTrace();
			} catch (Exception e) {
			    //Handle errors for Class.forName
			    e.printStackTrace();
			} finally {
			    //finally block used to close resources
			 
				try {
				   if(conn!=null)
				      conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				} //end finally try
			} //end try
		}
	}

	public void changeDescription(int user_id, String description) {
		
		Connection conn = null;
		PreparedStatement stmt = null;

		if (!description.contentEquals("")) {
			try {
				//STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");
				//
				conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
				
				//STEP 4: Execute a query
				//System.out.println("Creating statement...");
				
				//Statement to change details of the database
				String sql = "UPDATE users SET description=? WHERE (id = ?)";
		
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, description);
				stmt.setInt(2, user_id);
				stmt.executeUpdate();
	
				//STEP 6: Clean-up environment
				stmt.close();
				conn.close();	
				
			} catch (SQLException se) {
				//Handle errors for JDBC
				    se.printStackTrace();
			} catch (Exception e) {
			    //Handle errors for Class.forName
			    e.printStackTrace();
			} finally {
			    //finally block used to close resources
			 
				try {
				   if(conn!=null)
				      conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				} //end finally try
			} //end try
		}
	}

	public Boolean checkPassword(int id, String pw) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		String realPassword = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM users WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				realPassword = rs.getString("password");
		
			}
			
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			result = (pw.equals(realPassword));
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		return result;
	}

	public boolean checkHalted(int id) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		int isHalted = 1;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM users WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				isHalted = rs.getInt("is_halted");
		
			}
			
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
			if (isHalted == 1) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		return result;
	}

	public void verifyUser(int id, String code) {
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "UPDATE users SET is_halted=? WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, 0);
			stmt.setInt(2, id);
			stmt.executeUpdate();
			
			

			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
	}

	public boolean userExists(String username) {
				Connection conn = null;
				PreparedStatement stmt = null;
				String usernameReturned = "";
				//String bookName = null;
				try {
					//STEP 2: Register JDBC driver
					Class.forName("com.mysql.jdbc.Driver");
					//
					conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
					
					//STEP 4: Execute a query
					//System.out.println("Creating statement...");
					
					String sql = "SELECT * FROM users WHERE (username = ?)";
			
					stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					stmt.setString(1, username);
					stmt.executeQuery();
					ResultSet rs = stmt.getResultSet();
					while (rs.next()) {
						usernameReturned = rs.getString("username");
					}
					System.out.println("query returned: " + usernameReturned);
					//STEP 6: Clean-up environment
					stmt.close();
					conn.close();	
					
				} catch (SQLException se) {
					//Handle errors for JDBC
					    se.printStackTrace();
				} catch (Exception e) {
				    //Handle errors for Class.forName
				    e.printStackTrace();
				} finally {
				    //finally block used to close resources
				 
					try {
					   if(conn!=null)
					      conn.close();
					} catch (SQLException se) {
						se.printStackTrace();
					} //end finally try
				} //end try
		if(usernameReturned.equals(username)) {
			return true;
		}else {
			return false;
		}
	}
	
	//Get the max id of book in database
	public int maxBooksID() {
		Connection conn = null;
		PreparedStatement stmt = null;
		int books = 0;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT MAX(id) AS Size FROM books";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
			//stmt.getFetchSize()
			while (rs.next()) {
				books = rs.getInt("Size");
				System.out.println(rs.getInt("Size"));
			}
			
			
			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		return books;
	}
	
	//Gets the max id of user in database
	public int maxUsersID() {
		Connection conn = null;
		PreparedStatement stmt = null;
		int users = 0;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT MAX(id) AS Size FROM users";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
			
			while (rs.next()) {
				users = rs.getInt("Size");
				System.out.println(rs.getInt("Size"));
			}
			
			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		return users;
	}

	public ArrayList<String> getDetails(int id) {
		ArrayList<String> details = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM users WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, id);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			System.out.println("id: " + id + "\nDetails: " + rs);
			rs.next();
			details.add(rs.getString("password"));
			details.add(rs.getString("email"));
			details.add(rs.getString("nickname"));
			details.add(rs.getString("firstname"));
			details.add(rs.getString("lastname"));
			details.add(rs.getString("dob"));
			details.add(rs.getString("address"));
			details.add(rs.getString("creditcarddetails"));	
			details.add(rs.getString("description"));		

			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		return details;
	}
	
	
	
	public NACOAUserBean getUserDetails(int id) {
		NACOAUserBean details = new NACOAUserBean();
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM users WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, id);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			System.out.println("id: " + id + "\nDetails: " + rs);
			
			while(rs.next()){
				details.setUsername(rs.getString("username"));
				details.setPassword(rs.getString("password"));
				details.setEmailAddress(rs.getString("email"));
				details.setNickname(rs.getString("nickname"));
				details.setFirstname(rs.getString("firstname"));
				details.setLastname(rs.getString("lastname"));
				details.setDOB(rs.getString("dob"));
				details.setAddress(rs.getString("address"));
				details.setCreditDetails(rs.getString("creditcarddetails"));		
			}
			
			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		return details;
	}
	
	public void addHistoryBuyEntry(int user_id, int book_id){
		createHistoryEntry (user_id, book_id, ACTION_BUY); //history bought book
	}
	public void addHistoryAddCartEntry(int user_id, int book_id){
		createHistoryEntry (user_id, book_id, ACTION_ATC);	//history entry add to cart
	}
	public void addHistoryRemoveCartEntry(int user_id, int book_id){
		createHistoryEntry (user_id, book_id, ACTION_RFC); //history entry remove from cart
	}
	
	public void createHistoryEntry(int user_id, int book_id, int action){
		int auto_id = 0;
		Connection conn = null;
		
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//STEP 3: Open a connection
			//System.out.println("Connecting to database...");
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			String sql = "INSERT INTO `user_history` "
					 + "(`user_id`, `book_id`, `action_type`) "
			  + "VALUES (?, ?, ?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, user_id);
			stmt.setInt(2, book_id);
			stmt.setInt(3, action);
			
			stmt.executeUpdate();
		
			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
	}
	
	public void pauseSelling (int user_id, int book_id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			String sql = "UPDATE user_seller_books SET is_paused=? WHERE (user_id = ? AND book_id = ?)";
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, 1);
			stmt.setInt(2, user_id);
			stmt.setInt(3, book_id);
			stmt.executeUpdate();
			
			  //STEP 6: Clean-up environment
			stmt.close();
			conn.close();
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
	}
	
	public void resumeSelling (int user_id, int book_id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			String sql = "UPDATE user_seller_books SET is_paused=? WHERE (user_id = ? AND book_id = ?)";
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, 0);
			stmt.setInt(2, user_id);
			stmt.setInt(3, book_id);
			stmt.executeUpdate();
			
			  //STEP 6: Clean-up environment
			stmt.close();
			conn.close();
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
	}
	
	//Searches database for book
	public ArrayList<NACOABean> bookSearch(String title, String author, String genre) {
		ArrayList<NACOABean> resultBook = new ArrayList<NACOABean>();

		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM books "
						 + "WHERE title LIKE ?"
						 + "AND author LIKE ?"
						 + "AND genre LIKE ?";
	
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			//System.out.println("Title is " + title);
			//System.out.println("Author is " + author);
			//System.out.println("Genre is " + genre);
			stmt.setString(1, "%"+title+"%");
			stmt.setString(2, "%"+author+"%");
			stmt.setString(3, "%"+genre+"%");
			
			//System.out.println("statement is: ");
			//System.out.println(stmt);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
			
			while(rs.next()) {
				
				//Need to make sure that the book has not been sold yet?
				int bookID = rs.getInt("id");
				
				//Search the user_seller_book for the id to see if sold
				if (getBookAvail(bookID) == 0 && isBookPaused(bookID) == 0) {
					NACOABean book = new NACOABean();
					
					book.setBookID(rs.getInt("id"));
					book.setBooktitle(rs.getString("title"));
					book.setAuthor(rs.getString("author"));
					book.setPicture(rs.getString("picture"));
					book.setPublisher(rs.getString("publisher"));
					book.setDOP(rs.getString("dateofpublication"));
					book.setPages(Integer.toString(rs.getInt("pages")));
					book.setIsbn(rs.getString("isbn"));
					book.setGenre(rs.getString("genre"));
					float price = rs.getFloat("price");
					book.setPrice(Float.toString(price));
					resultBook.add(book);
				}
			}	

			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		return resultBook;
	}
	
	//Searches database for users
	public ArrayList<NACOAUserBean> userSearch(String username) {
		ArrayList<NACOAUserBean> resultUser = new ArrayList<NACOAUserBean>();

		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			//System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM users "
						 + "WHERE (username LIKE ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, "%"+username+"%");
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
			
			while(rs.next()) {

				NACOAUserBean user = new NACOAUserBean();
		
				user.setUserID(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setFirstname(rs.getString("firstname"));
				user.setLastname(rs.getString("lastname"));
				user.setNickname(rs.getString("nickname"));
				user.setEmailAddress(rs.getString("email"));
				user.setDOB(rs.getString("dob"));
				user.setPassword(rs.getString("password"));
				user.setAddress(rs.getString("address"));
				user.setCreditDetails(rs.getString("creditcarddetails"));
				user.setHalted(rs.getInt("is_halted"));
				
				resultUser.add(user);
			}	

			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
		
		return resultUser;
	}
	
	public long getCountBooksSold(int user_id){
		Connection conn = null;
		PreparedStatement stmt = null;
		long count = 0;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			
			String sql = "SELECT count(*) FROM user_seller_books WHERE (user_id = ? AND is_sold = TRUE)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, user_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				count = rs.getLong(1); 
		
			}
					  //STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
			
			
		return count; 
	}
	
	public long getCountBooksNotSold(int user_id){
		Connection conn = null;
		PreparedStatement stmt = null;
		long count = 0;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			
			String sql = "SELECT count(*) FROM user_seller_books WHERE (user_id = ? AND is_sold = FALSE)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, user_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				count = rs.getLong(1); 
		
			}
					  //STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
			
			
		return count; 
		
	}
	
	public long getCountBooksBought(int user_id){
		Connection conn = null;
		PreparedStatement stmt = null;
		long count = 0;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			
			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			
			String sql = "SELECT count(*) FROM user_customer_books WHERE (user_id = ? AND is_bought = TRUE)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, user_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				count = rs.getLong(1); 
		
			}
					  //STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			//Handle errors for JDBC
			    se.printStackTrace();
		} catch (Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		} finally {
		    //finally block used to close resources
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
			
			
		return count; 
		
	}
	
	
	
}

