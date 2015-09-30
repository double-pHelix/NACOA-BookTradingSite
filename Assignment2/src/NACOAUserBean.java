
public class NACOAUserBean {

	private int userID;
	private String username;
	private String firstname;
	private String lastname;
	private String emailAddress;
	private String password;
	private String nickname;
	private String dob;
	private String address;
	private String creditDetails;
	private int isHalted;
	private boolean isAdmin;


	private boolean isUser;
	private String description;
	private int numBooksSold;
	private int numBooksSale;
	private int numBooksBought;
	private int numBooksInCart;
	
	public NACOAUserBean() {
		isUser = false;
	}
	
	public void setUserID(int user_id) {
		this.userID = user_id;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void setEmailAddress(String email) {
		this.emailAddress = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setDOB(String dob) {
		this.dob = dob;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setCreditDetails(String credit) {
		this.creditDetails = credit;
	}
	
	public void setIsAdmin(boolean admin) {
		this.isAdmin = admin;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getDOB() {
		return dob;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getCreditDetails() {
		return creditDetails;
	}
	
	public boolean getIsAdmin() {
		return isAdmin;
	}
	
	public String getDescription() {
		return description;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public int getIsHalted() {
		return isHalted;
	}

	public void setIsHalted(int isHalted) {
		this.isHalted = isHalted;
	}


	public int getNumBooksSold() {
		return numBooksSold;
	}

	public void setNumBooksSold(int numBooksSold) {
		this.numBooksSold = numBooksSold;
	}

	public int getNumBooksSale() {
		return numBooksSale;
	}

	public void setNumBooksSale(int numBooksSale) {
		this.numBooksSale = numBooksSale;
	}

	public int getNumBooksBought() {
		return numBooksBought;
	}

	public void setNumBooksBought(int numBooksBought) {
		this.numBooksBought = numBooksBought;
	}

	public int getNumBooksInCart() {
		return numBooksInCart;
	}

	public void setNumBooksInCart(int numBooksInCart) {
		this.numBooksInCart = numBooksInCart;
	}

	public boolean isIsUser() {
		return isUser;
	}

	public void setIsUser(boolean isUser) {
		this.isUser = isUser;
	}
	public boolean isUser() {
		return isUser;
	}

	public void setUser(boolean isUser) {
		this.isUser = isUser;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
