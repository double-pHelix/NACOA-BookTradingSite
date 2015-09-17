import com.mysql.jdbc.Connection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/nacoadatabase";
	static final String LOCALHOST_URL = "jdbc:mysql://localhost/";
	
	//  Database credentials
	static final String USER = "root";
	static final String PASS = "";
	
	
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
		
		System.out.println("User name is " +handler.getUserName(0));
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
			System.out.println("Connecting to database...");
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
			String cAdmins = "CREATE TABLE IF NOT EXISTS `admins` ("
						+ "  `id` int(11) NOT NULL,"
						+ "  `username` varchar(32) NOT NULL,"
						+ "  `password` int(128) NOT NULL,"
						+ "  `email` int(52) NOT NULL"
						+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1";
			
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
					+ "  `genre` varchar(20) NOT NULL"
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
					  + "  `is_halted` tinyint(1) NOT NULL DEFAULT '0'"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1";
			
			String cUBooks  = "CREATE TABLE IF NOT EXISTS `user_customer_books` ("
					 + " `user_id` int(11) NOT NULL,"
					 + " `book_id` int(11) NOT NULL,"
					 + " `is_sold` tinyint(1) NOT NULL DEFAULT '0',"
					 + " `dateofupload` date NOT NULL,"
					 + " `dateofsale` date NOT NULL"
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
					  + "`dateofsale` date NOT NULL,"
					  + " `is_paused` tinyint(1) NOT NULL DEFAULT '0'"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1";
			
			LinkedList<String> creatTables = new LinkedList<String>(Arrays.asList(cAdmins, cBooks, cUsers, cUBooks, cSBooks, cHistor));
			
			for(int i = 0; i < 6; i++){
				PreparedStatement cStmt = conn.prepareStatement(creatTables.get(i));
				cStmt.executeUpdate();
				cStmt.close();
			}
			
			//SET UP OUR CONSTRAINTS
			String query1 = "ALTER TABLE `admins` ADD PRIMARY KEY (`id`)";
			String query2 = "ALTER TABLE `books` ADD PRIMARY KEY (`id`)";
			String query3 = "ALTER TABLE `users` ADD PRIMARY KEY (`id`)";
			String query4 = "ALTER TABLE `user_customer_books`"
						  + "ADD KEY `user_id` (`user_id`),"
						  + "ADD KEY `book_id` (`book_id`)";
			String query5 = "ALTER TABLE `user_seller_books`"
						  + "ADD KEY `user_id` (`user_id`),"
						  + "ADD KEY `book_id` (`book_id`)";
			String query6 = "ALTER TABLE `admins`"
						  + "MODIFY `id` int(11) NOT NULL AUTO_INCREMENT";
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
											
					
			LinkedList<String> altQueries = new LinkedList<String>(Arrays.asList(query1, query2, query3, query4, query5, query6
					, query7, query8, query9, query10));
			
			for(int i = 0; i < 10; i++){
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
			String firstname, String lastname, String dob, String address, String creditinfo){
		int auto_id = 0;
		Connection conn = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);

			//STEP 4: Execute a query
			String sql;
			
			sql = "INSERT INTO `users` "
					 + "(`username`, `password`, `email`, `firstname`, `lastname`, `nickname`, `dob`, `address`, `creditcarddetails`, `is_halted`) "
			  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				

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
			stmt.setInt(10, 0);
			
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
	
	public boolean databaseExists(String dbName){
		Connection conn = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
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
			System.out.println("Creating statement...");
			
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
			System.out.println("Creating statement...");
			
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
			System.out.println("Creating statement...");
			
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
			System.out.println("Creating statement...");
			
			String sql = "SELECT * FROM user_seller_books WHERE (id = ?)";
	
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
			System.out.println("Creating statement...");
			
			String sql = "DELETE * FROM books WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, book_id);
			stmt.executeQuery();

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
			System.out.println("Creating statement...");
			
			String sql = "DELETE * FROM user_customer_books WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, book_id);
			stmt.executeQuery();

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
			System.out.println("Creating statement...");
			
			String sql = "INSERT INTO `user_customer_books` "
					 + "(`user_id`, `book_id`, `is_sold`, `dateofupload`, `dateofsale`) "
			  + "VALUES (?, ?, ?, ?, ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, user_id);
			stmt.setInt(2, book_id);
			stmt.setInt(3, is_sold);
			stmt.setString(4, dou);
			stmt.setString(5, dos);
			stmt.executeQuery();

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
			System.out.println("Creating statement...");
			
			String sql = "UPDATE user_seller_books SET is_sold WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, 1);
			stmt.setInt(2, book_id);
			stmt.executeQuery();
			
			sql = "UPDATE user_customer_books SET is_sold WHERE (id = ?)";
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, 1);
			stmt.setInt(2, book_id);
			stmt.executeQuery();

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
		
	//Change book title
	public void changeBookTitle(int book_id, String title){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			
			//Statement to change details of the database
			String sql = "UPDATE books SET title=? WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, title);
			stmt.setInt(2, book_id);
			stmt.executeQuery();

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
	
	//Change book author
	public void changeBookAuthor(int book_id, String author){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			
			//Statement to change details of the database
			String sql = "UPDATE books SET author=? WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, author);
			stmt.setInt(2, book_id);
			stmt.executeQuery();

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
	
	//Change book picture
	public void changeBookPicture(int book_id, String picture){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			
			//Statement to change details of the database
			String sql = "UPDATE books SET picture=? WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, picture);
			stmt.setInt(2, book_id);
			stmt.executeQuery();

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
	
	//Change book price
	public void changeBookPrice(int book_id, String price){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			
			//Statement to change details of the database
			String sql = "UPDATE books SET price=? WHERE (id = ?)";
	
			//Price conversion
			float f = Float.parseFloat(price);
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setFloat(1, f);
			stmt.setInt(2, book_id);
			stmt.executeQuery();

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
	
	//Change book publisher
	public void changeBookPublisher(int book_id, String publisher){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			
			//Statement to change details of the database
			String sql = "UPDATE books SET publisher=? WHERE (id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, publisher);
			stmt.setInt(2, book_id);
			stmt.executeQuery();

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
	
	//Change book date of publication
	public void changeBookDateOfPublication(int book_id, String dop){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			
			//Statement to change details of the database
			String sql = "UPDATE books SET dateofpublication=? WHERE (id = ?)";
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, dop);
			stmt.setInt(2, book_id);
			stmt.executeQuery();

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
	
	//Change book pages
	public void changeBookPages(int book_id, String pages){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			
			//Convert to int
			Integer pageNum = Integer.parseInt(pages);
			
			//Statement to change details of the database
			String sql = "UPDATE books SET pages=? WHERE (id = ?)";
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, pageNum);
			stmt.setInt(2, book_id);
			stmt.executeQuery();

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
	
	//Change book isbn
	public void changeBookISBN(int book_id, String isbn){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			
			//Statement to change details of the database
			String sql = "UPDATE books SET isbn=? WHERE (id = ?)";
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, isbn);
			stmt.setInt(2, book_id);
			stmt.executeQuery();

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
	
	//Change book genre
	public void changeBookGenre(int book_id, String genre){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		//String bookName = null;
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			
			//Statement to change details of the database
			String sql = "UPDATE books SET genre=? WHERE (id = ?)";
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, genre);
			stmt.setInt(2, book_id);
			stmt.executeQuery();

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
