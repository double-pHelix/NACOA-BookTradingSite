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
import java.util.Random;
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
		/*
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
		if(handler.getCountBooksInCart(2) == 1){
			System.out.println("Test 4 Passed! ");
		} else {
			System.out.println("Test 4 Failed! ");
		}
		
		if(handler.getUserId("qwe") == 2){
			System.out.println("Test 5 Passed! ");
		} else {
			System.out.println("Test 5 Failed! ");
		}
		
		
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
			handler.addBookToCart(2, 3, 0);
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
					+ " `title` varchar(128) NOT NULL,"
					+ " `author` varchar(128) NOT NULL,"
					+ "  `picture` varchar(128) NOT NULL,"
					+ " `price` float NOT NULL,"
					+ "  `publisher` varchar(128) NOT NULL,"
				    + " `dateofpublication` date NOT NULL,"
					+ "  `pages` int(11) NOT NULL,"
					+ "  `isbn` varchar(20) NOT NULL,"
					+ "  `genre` varchar(52) NOT NULL,"
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
						  + "ADD PRIMARY KEY (`user_id`,`book_id`),"
						  + "ADD KEY `user_id` (`user_id`),"
						  + "ADD KEY `book_id` (`book_id`)";
			String query5 = "ALTER TABLE `user_seller_books`"
						  + "ADD PRIMARY KEY (`user_id`,`book_id`),"
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
			
			//INSERT SAMPLE DATA
			insertSampleData();
			
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
	
	//inserts our sample data into the database i.e. users and books
	public void insertSampleData() {
		System.out.println("Entering sample data");
		
		String email = "richard.zhang94@hotmail.com";
		String password = "password123";
		String usernames[] = {
			"annumcanapes", "devoteduntried", "strikersord", "fracturebirds", "nohitterpascal",
			"atomsunderstand", "poppysmicgadolinium", "cheddarscypriot", "whilethorium", "birdslumpy",
			"gilltrue", "wellingtonma", "vagaryunkind", "appealguaranteed", "microwaveconcede",
			"dacitequalling", "fibrosisrye", "endemismperturb", "divingsuper", "pearldequeue", 
			"furlongbiceps", "hoopoedismal", "reclusivebypass", "meterquirky", "itchyaboriginal"
		};
		
		String firstnames[] = {
			"Suzi", "Dollie", "Marshall", "Shannon", "Latoria",
			"Shan", "Dannie", "Georgia", "Candance", "Evelia",
			"Terrell", "Adell", "Enola", "Chassidy", "Yoko", 
			"Tomika", "Desirae", "September", "Tamie", "Joya", 
			"Carolee", "Yen", "Lynetta", "Hollis", "Dominga"
		};
		
		String lastnames[] = {
			"Avila", "Moss", "Spencer", "Doyle", "Sawyer",
			"Leach", "Lam", "Lee", "Lara", "Kramer",
			"Quinn", "Galvan", "Zuniga", "Young", "Woodard",
			"Lindsey", "Navarro", "Carney", "Velazquez", "Nolan",
			"Davis", "Solis", "Pitts", "Rodriguez", "Pace"
		};
		
		String nicknames[] = {
			"Morbid Ivory Titan", "Ox Bandit", "The Rebel", "Major Shadow", "The Lion",
			"Freak Sad", "Colonel Pink", "Screaming Moose", "Alpha Stallion", "Worthy Butterfly",
			"ProphetProphet", "Minimum Kitten", "Dreaded Villain", "TapirTapir", "Black Cockroach",
			"Navy Spider", "Los Turtle", "GiraffeGiraffe", "Crazy Tiger", "Loose Toddler",
			"Insane Wolverine", "Woodchuck Rotten", "Thirsty Panda", "Gold Student", "Los Cat"
		};
		
		String dobs[] = {
			"1915-05-11", "1916-08-10", "1921-08-17", "1923-12-26", "1930-01-01",
			"1934-08-13", "1936-04-11", "1937-01-30", "1937-09-08", "1940-07-15",
			"1942-09-16", "1945-07-16", "1947-05-18", "1958-02-22", "1959-01-26",
			"1963-02-26", "1964-02-06", "1964-10-11", "1967-03-23", "1970-07-01",
			"1971-04-29", "1977-01-05", "1990-08-02", "1999-07-26", "2000-04-28"
		};
		
		String addresses[] = {
			"77 Jackson St, Brotton, Saltburn-by-the-Sea, Redcar and Cleveland TS12 2TE, UK",
			"52 Old St, Kilmarnock, East Ayrshire KA1 4DX, UK",
			"2 Rectory Cl, Wells-next-the-Sea, Norfolk NR23, UK",
			"55 Mark Ln, London EC3R 7NE, UK",
			"12-14 High St, Crowthorne, Bracknell Forest RG45 7AZ, UK",
			"Bellaghy Rd, Ballymena, Ballymena BT44 9PP, UK",
			"16 Coombelands Ln, Addlestone, Surrey KT15 1JJ, UK",
			"24A High St, West Wickham, Greater London BR4 0NJ, UK",
			"228 Dersingham Ave, London E12, UK",
			"88 Broadwater Rd, London N17 6ET, UK",
			"38 Watford Cl, London SW11 4QT, UK",
			"3 Prenton Way, Birkenhead, Prenton, Merseyside CH43 3DU, UK",
			"Halesfield 25, Telford, Telford and Wrekin TF7 4LP, UK",
			"18 Harbour Rd, Eyemouth, Scottish Borders TD14 5HU, UK",
			"7 Shawberry Ave, Birmingham, West Midlands B35 6QU, UK",
			"41 Castle Terrace, Aberdeen, Aberdeen City AB11 5EA, UK",
			"10 Tingewick Rd, Buckingham, Buckinghamshire MK18 1EE, UK",
			"Pool Cl, Rugby, Warwickshire CV22 7RN, UK",
			"Unnamed Road, Morpeth, Northumberland NE65, UK",
			"14 Ronald Toon Rd, Earl Shilton, Leicester, Leicestershire LE9 7BD, UK",
			"Meachants Ln, Eastbourne, East Sussex BN20 9LS, UK",
			"50 Commercial Rd, Lowestoft, Suffolk NR32, UK",
			"Northclose Rd, Chulmleigh, Devon EX18 7SJ, UK",
			"432A Ballyquin Rd, Dungiven, Londonderry, Limavady BT47 4LX, UK",
			"7 Clachan Beag, Strachur, Cairndow, Argyll and Bute PA27 8DG, UK"
		};
		
		String creditNumbers[] = {
			"4485073495865233", "4485701937613767", "4716542505645000", "4539129598883006", "4532201019088841",
			"5369599250479669", "5390110228781559", "5574735483131286", "5296171674319463", "5296761842686254",
			"6011261841780713", "6011819623517163", "6011142590081950", "6011343119429890", "6011545623708342",
			"344225877352755", "371329617134485", "344721400273773", "377987608088558", "373936901315980",
			"36879841998144", "36805878687755", "36065797484277", "6011593327371132", "6011846088330238"
		};
		
		int ids[] = {
			18,	24,	8,	17,	24,
			24,	5,	15,	21,	13,
			22,	18,	12,	15,	7,
			3,	8,	19,	19,	16,
			8,	16,	19,	18,	22
		};
		
		String dates[] = {
			"2015-09-12", "2015-09-10", "2015-09-10", "2015-09-28", "2015-09-16",
			"2015-09-16", "2015-09-28", "2015-09-18", "2015-09-16", "2015-09-13",
			"2015-09-17", "2015-09-24", "2015-09-19", "2015-09-24", "2015-09-15",
			"2015-09-24", "2015-09-11", "2015-09-27", "2015-09-13", "2015-09-21",
			"2015-09-15", "2015-09-15", "2015-09-24", "2015-09-15", "2015-09-21"
		};
		
		String titles[] = {
			"Nopi: The Cookbook",
			"The Red Notebook",
			"Advances in Hydrogen Production, Storage and Distribution",
			"Head First Design Patterns",
			"Java in a Nutshell",
			"Automate the Boring Stuff with Python : Practical Programming for Total Beginners",
			"Voyager",
			"I Let You Go",
			"Gone Girl",
			"Unicorns are Jerks",
			"A Walk in the Woods : Rediscovering America on the Appalachian Trail",
			"Thug Kitchen : Eat Like You Give a F**k",
			"Plenty More",
			"Grain Brain : The Surprising Truth About Wheat, Carbs, and Sugar - Your Brain's Silent Killers",
			"Power Up Your Brain",
			"The Law of Attraction : The Basics of the Teachings of Abraham",
			"All the Light We Cannot See",
			"Animorphia : An Extreme Colouring and Search Challenge",
			"Corporate Finance : Theory and Practice",
			"Becoming a Better Boss",
			"Secret Garden : An Inky Treasure Hunt and Colouring Book",
			"The Very Cranky Bear",
			"The Tiger Who Came to Tea",
			"Queen of Shadows",
			"The Husband's Secret"
			
		};
		
		String authors[] = {
			"Ebury Press, Ramael Scully and Yotam Ottolenghi",
			"Antoine Laurain",
			"Angelo Basile and Adolfo Iulianelli",
			"Elisabeth Freeman, Eric Freeman and Bert Bates",
			"Benjamin J. Evans and David Flanagan",
			"Albert Sweigart",
			"Diana Gabaldon",
			"Clare Mackintosh",
			"Gillian Flynn",
			"Theo Nicole Lorenz",
			"Bill Bryson",
			"Thug Kitchen",
			"Yotam Ottolenghi",
			"David Perlmutter",
			"David Perlmutter",
			"Esther Hicks and Jerry Hicks",
			"Anthony Doerr",
			"Kerby Rosanes",
			"Pierre Vernimmen, Pascal Quiry, Maurizio Dallocchio, Yann Le Fur and Antonio Salvi",
			"Julian Birkinshaw",
			"Johanna Basford",
			"Nick Bland",
			"Judith Kerr",
			"Sarah J. Maas",
			"Liane Moriarty"
		};
		
		String pictures[] = {
			"http://d3by36x8sj6cra.cloudfront.net/assets/images/book/large/9780/0919/9780091957162.jpg",
			"http://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/large/9781/9083/9781908313867.jpg",
			"http://d20eq91zdmkqd.cloudfront.net/assets/images/book/large/9780/8570/9780857097682.jpg",
			"http://d20eq91zdmkqd.cloudfront.net/assets/images/book/large/9780/5960/9780596007126.jpg",
			"http://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/large/9781/4493/9781449370824.jpg",
			"http://d4rri9bdfuube.cloudfront.net/assets/images/book/large/9781/5932/9781593275990.jpg",
			"http://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/large/9780/4402/9780440217565.jpg",
			"http://d4rri9bdfuube.cloudfront.net/assets/images/book/large/9780/7515/9780751554151.jpg",
			"http://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/large/9780/7538/9780753827666.jpg",
			"http://d39ttiideeq0ys.cloudfront.net/assets/images/book/large/9781/4774/9781477468524.jpg",
			"http://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/large/9781/1019/9781101970881.jpg",
			"http://d20eq91zdmkqd.cloudfront.net/assets/images/book/large/9780/7515/9780751555516.jpg",
			"http://d20eq91zdmkqd.cloudfront.net/assets/images/book/large/9780/0919/9780091957155.jpg",
			"http://d39ttiideeq0ys.cloudfront.net/assets/images/book/large/9781/4447/9781444791907.jpg",
			"http://d4rri9bdfuube.cloudfront.net/assets/images/book/large/9781/4019/9781401928186.jpg",
			"http://d3by36x8sj6cra.cloudfront.net/assets/images/book/large/9781/4019/9781401912277.jpg",
			"http://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/large/9780/0081/9780008138301.jpg",
			"http://d39ttiideeq0ys.cloudfront.net/assets/images/book/large/9781/9105/9781910552070.jpg",
			"http://d4rri9bdfuube.cloudfront.net/assets/images/book/large/9781/1188/9781118849330.jpg",
			"http://d4rri9bdfuube.cloudfront.net/assets/images/book/large/9781/1186/9781118645468.jpg",
			"http://d39ttiideeq0ys.cloudfront.net/assets/images/book/large/9781/7806/9781780671062.jpg",
			"http://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/large/9780/3409/9780340989432.jpg",
			"http://d4rri9bdfuube.cloudfront.net/assets/images/book/large/9780/0073/9780007393657.jpg",
			"http://d4rri9bdfuube.cloudfront.net/assets/images/book/large/9781/4088/9781408858615.jpg",
			"http://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/large/9781/4059/9781405911665.jpg"
		};
		
		String prices[] = {
			"39.93", "14.50", "315.42", "60.04", "59.39",
			"35.84", "14.48", "15.83", "14.63", "12.51",
			"12.46", "27.75", "40.94", "23.22", "16.03",
			"19.75", "18.85", "16.03", "99.70", "30.68",
			"14.61", "11.35", "11.28", "10.89", "14.43"
		};
		
		String publishers[] = {
			"Ebury Publishing",
			"Gallic Books",
			"Woodhead Publishing Ltd",
			"O'Reilly Media, Inc, USA",
			"O'Reilly Media, Inc, USA",
			"No Starch Press,US",
			"Bantam Doubleday Dell Publishing Group Inc",
			"Little, Brown Book Group",
			"Orion Publishing Co",
			"Createspace",
			"Anchor Books",
			"Little, Brown Book Group",
			"Ebury Press",
			"Hodder & Stoughton General Division",
			"Hay House Inc",
			"Hay House Inc",
			"HarperCollins Publishers",
			"John Wiley & Sons Inc",
			"John Wiley & Sons Inc",
			"Laurence King Publishing",
			"Hachette Children's Group",
			"HarperCollins Publishers",
			"Bloomsbury Publishing PLC",
			"Penguin Books Ltd"
		};
		
		String dops[] = {
			"2015-09-10", "2015-04-14", "2014-07-25", "2015-08-01", "2014-11-14",
			"2015-09-01", "1994-11-01", "2015-05-07", "2013-01-03", "2012-08-29",
			"2015-08-18", "2014-12-10", "2014-09-11", "2014-01-16", "2012-02-01",
			"2007-01-01", "2015-05-01", "2015-06-25", "2014-11-17", "2013-10-28",
			"2015-06-15", "2010-05-06", "2011-03-03", "2015-09-10", "2013-08-29"
		};
		
		String pages[] = {
			"352", "240", "574", "608", "418",
			"504", "1059", "384", "512", "40",
			"416", "240", "288", "336", "220",
			"194", "544", "96", "1000", "176",
			"96", "32", "32", "656", "432"
		};
		
		String isbns[] = {
			"0091957168", "1908313862", "0857097687", "0596007124", "1449370829",
			"1593275994", "0440217563", "0751554154", "0753827662", "1477468528",
			"110197088X", "0751555517", "009195715X", "1444791907", "1401928188",
			"1401912273", "0008138303", "1910552070", "1118849337", "1118645464",
			"1780671067", "0340989432", "0007393652", "1408858614", "1405911662"
		};
		
		String genres[] = {
			"Food and Drink",
			"Romance",
			"Technology and Engineering",
			"Computer Programming",
			"Computer Programming",
			"Computer Programming",
			"Romance",
			"Crime / Thriller",
			"Crime / Thriller",
			"Humour",
			"Humour / Travel",
			"Food and Drink",
			"Food and Drink",
			"Health / Dieting",
			"Health / Medicine",
			"Personal Development / Psychology",
			"Contemporary Fiction",
			"Colouring / Painting",
			"Finance",
			"Management / Leadership",
			"Colouring / Painting",
			"Storybooks",
			"Storybooks",
			"Fantasy / Fiction",
			"Contemporary Fiction"
		};
		
		String descriptions[] = {
			"Nopi: The Cookbook includes over 120 of the most popular dishes from Yotam's innovative Soho-based restaurant Nopi. It's written with long-time collaborator and Nopi head chef Ramael Scully, who brings his distinctive Asian twist to the Ottolenghi kitchen.",
			"Bookseller Laurent Letellier comes across an abandoned handbag on a Parisian street, and feels impelled to return it to its owner. The bag contains no money, phone or contact information. But a small red notebook with handwritten thoughts and jottings reveals a person that Laurent would very much like to meet. Without even a name to go on, and only a few of her possessions to help him, how is he to find one woman in a city of millions?",
			"Advances in Hydrogen Production, Storage and Distribution reviews recent developments in this key component of the emerging hydrogen economy, an energy infrastructure based on hydrogen. Since hydrogen can be produced without using fossil fuels, a move to such an economy has the potential to reduce greenhouse gas emissions and improve energy security.",
			"You're not alone. At any given moment, somewhere in the world someone struggles with the same software design problems you have. You know you don't want to reinvent the wheel (or worse, a flat tire), so you look to Design Patterns--the lessons learned by those who've faced the same problems. With Design Patterns, you get to take advantage of the best practices and experience of others, so that you can spend your time on...something else.",
			"The latest edition of Java in a Nutshell is designed to help experienced Java programmers get the most out of Java 7 and 8, but it's also a learning path for new developers. Chock full of examples that demonstrate how to take complete advantage of modern Java APIs and development best practices, the first section of this thoroughly updated book provides a fast-paced, no-fluff introduction to the Java programming language and the core runtime aspects of the Java platform.",
			"If you've ever spent hours renaming files or updating hundreds of spreadsheet cells, you know how tedious tasks like these can be. But what if you could have your computer do them for you? In Automate the Boring Stuff with Python, you'll learn how to use Python to write programs that do in minutes what would take you hours to do by hand-no prior programming experience required.",
			"From the author of the breathtaking bestsellers 'Outlander' and 'Dragonfly in Amber,' the extraordinary saga continues.Their passionate encounter happened long ago by whatever measurement ClaireRandall took. Two decades before, she had traveled back in time and into thearms of a gallant eighteenth-century Scot named Jamie Fraser.",
			"THE SENSATIONAL SUNDAY TIMES BESTSELLER A tragic accident. It all happened so quickly. She couldn't have prevented it. Could she? In a split second, Jenna Gray's world descends into a nightmare. Her only hope of moving on is to walk away from everything she knows to start afresh.",
			"THE ADDICTIVE No.1 BESTSELLER THAT EVERYONE IS TALKING ABOUT Who are you? What have we done to each other? These are the questions Nick Dunne finds himself asking on the morning of his fifth wedding anniversary, when his wife Amy suddenly disappears.",
			"Unicorns think they're so great because they're all mysterious and magical, but they can be real jerks sometimes. This coloring book features eighteen examples of unicorns texting in theaters, farting in elevators, eating your leftovers, and generally acting like jerks.",
			"Soon to be a major motion picture starring Robert Redford and Nick Nolte. The Appalachian Trail trail stretches from Georgia to Maine and covers some of the most breathtaking terrain in America majestic mountains, silent forests, sparking lakes. If you re going to take a hike, it s probably the place to go.",
			"Thug Kitchen started their wildly popular website to inspire people to eat some Goddamn vegetables and adopt a healthier lifestyle. Beloved by Gwyneth Paltrow ('This might be my favorite thing ever') and with half a million Facebook fans and counting, Thug Kitchen wants to show everyone how to take charge of their plates and cook up some real f*cking food.",
			"Yotam Ottolenghi's Plenty changed the way people cook and eat. Its focus on vegetable dishes, with the emphasis on flavour, original spicing and freshness of ingredients, caused a revolution not just in this country, but the world over. Plenty More picks up where Plenty left off, with 120 more dazzling vegetable-based dishes, this time organised by cooking method.",
			"Renowned neurologist Dr David Perlmutter, blows the lid off a topic that's been buried in medical literature for far too long: gluten and carbs are destroying your brain.",
			"The quest for enlightenment has occupied mankind for millennia. And from the depictions we've seen-monks sitting on meditation cushions, nuns kneeling in prayer, shamans communing with the universe-it seems that this elusive state is reserved for a chosen few. But now, neuroscientist David Perlmutter and medical anthropologist and shaman Alberto Villoldo have come together to explore the commonalities between their specialties with the aim of making enlightenment possible for anyone.",
			"This book presents the powerful basics of the original Teachings of Abraham.' Within these pages, you'll learn how all things, wanted and unwanted, are brought to you by this most powerful law of the universe, the Law of Attraction. '(that which is like unto itself is drawn).",
			"A beautiful, stunningly ambitious novel about a blind French girl and a German boy whose paths collide in occupied France as both try to survive the devastation of World War II When Marie Laure goes blind, aged six, her father builds her a model of their Paris neighborhood, so she can memorize it with her fingers and then navigate the real streets.",
			"Welcome to this weird and wacky colouring challenge. There are pictures to colour in, drawings to complete, spaces to scribble in and lots of things to find in these super-detailed doodles by artist Kerby Rosanes. Featuring unique and intricate ink drawings of incredible animals, shape-shifting aliens and breathtaking scenes. Readers will have to keep their eyes peeled for hidden treasures and creatures scattered throughout the pages.",
			"Merging theory and practice into a comprehensive, highly-anticipated text Corporate Finance continues its legacy as one of the most popular financial textbooks, with well-established content from a diverse and highly respected author team.",
			"An employee's-eye view of what makes a great boss-and how you can become one Whereas most books on managing people approach the subject from the perspective of a manager of an idealised organisation, Becoming a Better Boss takes a real-world approach, looking at the topic from the perspective of an employee in a real-world organisation-dysfunctions, warts, and all.",
			"Tumble down the rabbit hole and find yourself in an inky black-and-white wonderland. This interactive activity book takes you on a ramble through a secret garden created in beautifully detailed pen-and-ink illustrations - all waiting to be brought to life through colouring, but each also sheltering all kinds of tiny creatures just waiting to be found.",
			"In the Jingle, jangle jungle, four friends encounter a very cranky bear. Moose, Lion and Zebra all think they know how to cheer him up, but it's plain, boring Sheep who has the answer. This is a hilarious picture book, with the over-riding message being: Don't underestimate the quiet ones! Sometime a little thought is all you need to solve a problem instead of rushing to immediate action.",
			"The enchanting story of Sophie and her extraordinary tea-time guest has been loved by millions of children since it was first published 40 years ago. Now a new generation can enjoy this perennial children's classic in a mini edition. The doorbell rings just as Sophie and her mummy are sitting down to tea. Who could it possibly be?",
			"Everyone Celaena Sardothien loves has been taken from her. Now she returns to the empire - to confront the shadows of her past ...The fourth breathtaking instalment in the New York Times bestselling Throne of Glass series. Bloodthirsty for revenge on the two men responsible for destroying her life, and desperate to find out if the prince and his captain are safe, Celaena returns to Rifthold, the seat of so much evil.",
			"The Husband's Secret is a staggeringly brilliant novel. It is literally unputdownable. (Sophie Hannah). At the heart of the top ten bestselling The Husband's Secret by Liane Moriarty is a letter that's not meant to be read...Mother of three and wife of John-Paul, Cecilia discovers an old envelope in the attic. Written in her husband's hand, it says: to be opened only in the event of my death. Curious, she opens it - and time stops."
		};
		int id;
		for (int i=0; i < usernames.length; i++) {
			id = createUser(usernames[i], password, email, nicknames[i], firstnames[i], 
							lastnames[i], dobs[i], addresses[i], creditNumbers[i], "");
			verifyUser(id, null);
		}
		
		for (int i=0; i < titles.length; i++) {
			createBook (ids[i], dates[i], titles[i], authors[i], pictures[i], prices[i], publishers[i], 
						dops[i], pages[i], isbns[i], genres[i], descriptions[i]);
		}
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
	public int getSellersUserID (int book_id){
		
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
	public void deleteBookCart (int book_id, int user_id){
		
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
			
			String sql = "DELETE FROM user_customer_books WHERE ((book_id = ?) AND (user_id = ?))";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, book_id);
			stmt.setInt(2, user_id);
			
			System.out.println("stmt is ");
			System.out.println(stmt);
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
	public void addBookToCart (int user_id, int book_id, int is_bought){
		
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
					 + "(`user_id`, `book_id`, `is_bought`) "
			  + "VALUES (?, ?, ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, user_id);
			stmt.setInt(2, book_id);
			stmt.setInt(3, is_bought);
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
			
			String sql = "UPDATE user_seller_books SET is_sold=? WHERE (book_id = ?)";
	
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, 1);
			stmt.setInt(2, book_id);
			stmt.executeUpdate();
			
			sql = "UPDATE user_customer_books SET is_bought=? WHERE (book_id = ?)";
			
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
			book.setDop(this.getBookDOP(bID));
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
				book.setDop(this.getBookDOP(bID));
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
				book.setDop(this.getBookDOP(bID));
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
				details.setDescription(rs.getString("description"));
				details.setIsAdmin(rs.getBoolean(("is_admin")));
				details.setIsHalted(Integer.parseInt(rs.getString("is_halted")));
				
			}
			//GET COUNTS
			int numBought = (int) getCountBooksBought(id);
			int numSale = (int) getCountBooksNotSold(id);
			int numSold = (int) getCountBooksSold(id);
			int numCart = (int) getCountBooksInCart(id);
			
			//set up the user with this data
			details.setNumBooksBought(numBought);
			details.setNumBooksSale(numSale);
			details.setNumBooksSold(numSold);
			details.setNumBooksInCart(numCart);
			
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
	
			String newTitle = title;
			String newAuthor = author;
			String newGenre = genre;
			boolean exactTitle = false;
			boolean exactAuthor = false;
			boolean exactGenre = false;
			//Exact match
			if (!title.isEmpty() && title.toCharArray()[0] == '"') {
				sql = "SELECT * FROM books "
						 + "WHERE (title = ?)"
						 + " AND ";
				newTitle = title.substring(1, title.length() - 1);
				exactTitle = true;
			} else {
				sql = "SELECT * FROM books "
						 + "WHERE title LIKE ?"
						 + " AND ";
			}
			
			if (!author.isEmpty() && author.toCharArray()[0] == '"') {
				sql = sql + "(author = ?)"
						  + " AND ";
				newAuthor = author.substring(1, author.length() - 1);
				//System.out.println("New author is " + newAuthor);
				exactAuthor = true;
			} else {
				sql = sql + " author LIKE ?"
						 + " AND ";
			}
			
			if (!genre.isEmpty() && genre.toCharArray()[0] == '"') {
				sql = sql + "(genre = ?)";
				newGenre = genre.substring(1, genre.length() - 1);
				exactGenre = true;
			} else {
				sql = sql + "genre LIKE ?";
			}
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			//System.out.println("Title is " + title);
			//System.out.println("Author is " + author);
			//System.out.println("Genre is " + genre);
			if (exactTitle) {
				stmt.setString(1, newTitle);
			} else {
				stmt.setString(1, "%"+newTitle+"%");
			}
			
			if (exactAuthor) {
				stmt.setString(2, newAuthor);	
			} else {
				stmt.setString(2, "%"+newAuthor+"%");
			}
			
			if (exactGenre) {
				stmt.setString(3, newGenre);
			} else {
				stmt.setString(3, "%"+newGenre+"%");
			}
			
			System.out.println("statement is: ");
			System.out.println(stmt);
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
					book.setDop(rs.getString("dateofpublication"));
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
	
	//Searches database for book
	public ArrayList<NACOABean> bookAdminSearch(String title, String author, String genre) {
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
	
			String newTitle = title;
			String newAuthor = author;
			String newGenre = genre;
			boolean exactTitle = false;
			boolean exactAuthor = false;
			boolean exactGenre = false;
			//Exact match
			if (!title.isEmpty() && title.toCharArray()[0] == '"') {
				sql = "SELECT * FROM books "
						 + "WHERE (title = ?)"
						 + " AND ";
				newTitle = title.substring(1, title.length() - 1);
				exactTitle = true;
			} else {
				sql = "SELECT * FROM books "
						 + "WHERE title LIKE ?"
						 + " AND ";
			}
			
			if (!author.isEmpty() && author.toCharArray()[0] == '"') {
				sql = sql + "(author = ?)"
						  + " AND ";
				newAuthor = author.substring(1, author.length() - 1);
				exactAuthor = true;
			} else {
				sql = sql + "author LIKE ?"
						 + " AND ";
			}
			
			if (!genre.isEmpty() && genre.toCharArray()[0] == '"') {
				sql = sql + "(genre = ?)";
				newGenre = genre.substring(1, genre.length() - 1);
				exactGenre = true;
			} else {
				sql = sql + "genre LIKE ?";
			}
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			//System.out.println("Title is " + title);
			//System.out.println("Author is " + author);
			//System.out.println("Genre is " + genre);
			if (exactTitle) {
				stmt.setString(1, newTitle);
			} else {
				stmt.setString(1, "%"+newTitle+"%");
			}
			
			if (exactAuthor) {
				stmt.setString(2, newAuthor);	
			} else {
				stmt.setString(2, "%"+newAuthor+"%");
			}
			
			if (exactGenre) {
				stmt.setString(3, newGenre);
			} else {
				stmt.setString(3, "%"+newGenre+"%");
			}
			
			System.out.println("statement is: ");
			System.out.println(stmt);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
			
			while(rs.next()) {
				
				//Need to make sure that the book has not been sold yet?
				int bookID = rs.getInt("id");

				NACOABean book = new NACOABean();
				
				book.setBookID(rs.getInt("id"));
				book.setBooktitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setPicture(rs.getString("picture"));
				book.setPublisher(rs.getString("publisher"));
				book.setDop(rs.getString("dateofpublication"));
				book.setPages(Integer.toString(rs.getInt("pages")));
				book.setIsbn(rs.getString("isbn"));
				book.setGenre(rs.getString("genre"));
				float price = rs.getFloat("price");
				book.setPrice(Float.toString(price));
				resultBook.add(book);
				
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
			boolean exactUsername = false;
			String newUsername = username;
			//Exact match
			if (!username.isEmpty() && username.toCharArray()[0] == '"') {
				sql = "SELECT * FROM users "
						 + "WHERE (username = ?)";
				exactUsername = true;
				newUsername = username.substring(1, username.length() - 1);
			}
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			if (exactUsername) {
				stmt.setString(1, newUsername);
			} else {
				stmt.setString(1, "%"+newUsername+"%");
			}
			
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
			
			while(rs.next()) {

				int id = rs.getInt("id");
				if (!checkHalted(id)) {
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
					
					System.out.println("Username is " + user.getUsername());
					//System.out.println(user);
					resultUser.add(user);
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
		
		return resultUser;
	}
	
	//Searches database for users
	public ArrayList<NACOAUserBean> userAdminSearch(String username) {
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
	
			boolean exactUsername = false;
			String newUsername = username;
			//Exact match
			if (!username.isEmpty() && username.toCharArray()[0] == '"') {
				sql = "SELECT * FROM users "
						 + "WHERE (username = ?)";
				exactUsername = true;
				newUsername = username.substring(1, username.length() - 1);
			}
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (exactUsername) {
				stmt.setString(1, newUsername);
			} else {
				stmt.setString(1, "%"+newUsername+"%");
			}
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
			
			while(rs.next()) {

				int id = rs.getInt("id");
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
				
				System.out.println("Username is " + user.getUsername());
				//System.out.println(user);
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
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			String sql = "SELECT count(*) FROM user_seller_books WHERE (user_id = ? AND is_sold = FALSE)";
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, user_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
			while(rs.next()){
				count = rs.getLong(1); 
		
			}
			rs.close();
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			    se.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
	
		return count; 
		
	}
	
	public long getCountBooksBought(int user_id){
		Connection conn = null;
		PreparedStatement stmt = null;
		long count = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);

			String sql = "SELECT count(*) FROM user_customer_books WHERE (user_id = ? AND is_bought = TRUE)";
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, user_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
			
			while(rs.next()){
				count = rs.getLong(1); 
			}
			rs.close();
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			    se.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} 
		} 
		return count; 
		
	}
	public long getCountBooksInCart(int user_id){
		Connection conn = null;
		PreparedStatement stmt = null;
		long count = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			String sql = "SELECT count(*) FROM user_customer_books WHERE (user_id = ? AND is_bought = FALSE)";
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, user_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
			while(rs.next()){
				count = rs.getLong(1); 
		
			}
			rs.close();
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			    se.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return count; 	
	}
	

	
	//Bans user
	public void banUser(int id) {
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
			stmt.setInt(1, 1);
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
	//Gets the shopping cart as beans
	public ArrayList<NACOAHistoryBean> getUserHistory (int user_id){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ArrayList<NACOAHistoryBean> history = new ArrayList<NACOAHistoryBean>();

		int bookID = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			
			String sql = "SELECT * FROM user_history LEFT JOIN books ON user_history.book_id = books.id"
								+" WHERE (user_id = ?)";
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, user_id);
			stmt.executeQuery();
			
			ResultSet rs = stmt.getResultSet();
			
			while(rs.next()){
				NACOAHistoryBean newEntry = new NACOAHistoryBean();
				//Retrieve by column name
				newEntry.setBook_id(rs.getInt("book_id")); ;
				newEntry.setUser_id(rs.getInt("user_id"));
				newEntry.setAction(getActionName(rs.getInt("action_type")));
				newEntry.setTimeStamp(Integer.toString(rs.getInt("book_id")));
			}
			stmt.close();
			conn.close();	
			
		} catch (SQLException se) {
			    se.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		 
			try {
			   if(conn!=null)
			      conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} //end finally try
		} //end try
			
		return history;
	}
	public String getActionName (int id){
		if(id == ACTION_BUY){
			return "Bought";
		} else if (id == ACTION_ATC){
			return "Added to Cart";
		} else if (id == ACTION_RFC){
			return "Removed from Cart";
		} else {
			return "Undefined Value";
		}
		
	}
	
	//Gets userID using book id
	//username should be unique
	public int getUserId (String username){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		int userid = 0;
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
	
			  //STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				userid = rs.getInt("id");
		
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
		
		
		return userid; 
	}

	public String getPassword(int user_id) {
					
		Connection conn = null;
		PreparedStatement stmt = null;
		String password = null;
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
				password = rs.getString("password");
		
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
		
		return password;
	}
	
	public ArrayList<NACOABean> getRandomList (int entries) {
		
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
			String sql = "SELECT * FROM user_seller_books WHERE (is_paused = 0 AND is_sold = 0)";
			
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
		
		while (entries != realBooks.size()) {
			int bID = listOfBooks.get(new Random().nextInt(listOfBooks.size()));
			NACOABean book = new NACOABean();
			
			//Set Details
			book.setBookID(bID);
			book.setBooktitle(this.getBookTitle(bID));
			book.setAuthor(this.getBookAuthor(bID));
			book.setPicture(this.getBookPicture(bID));
			book.setPrice(this.getBookPrice(bID));
			book.setPublisher(this.getBookPublisher(bID));
			book.setDop(this.getBookDOP(bID));
			book.setPages(this.getBookPages(bID));
			book.setIsbn(this.getBookISBN(bID));
			book.setGenre(this.getBookGenre(bID));
			
			//Add book
			realBooks.add(book);
		}
		
		System.out.println("realbooks size: "+realBooks.size());
		return realBooks;
	}
}

