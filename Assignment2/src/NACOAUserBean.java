
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
	private int isAdmin;
	private String description;
	private int numBooksSold;
	private int numBooksSale;
	private int numBooksBought;
	
	
	public NACOAUserBean() {
		
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
	
	public void setHalted(int halted) {
		this.isHalted = halted;
	}
	
	public void setAdmin(int admin) {
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
	
	public int getHalted() {
		return isHalted;
	}
	
	public int getAdmin() {
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

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
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
}
