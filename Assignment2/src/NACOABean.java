
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Details about each entry in the database for display purposes (both minimised and expanded view)
 * 
 * @author Felix
 *
 */
public class NACOABean implements Serializable {
	private int xmlID;
	/* fields */
	private ArrayList<String> authors; //we can have multiple authors or editors
	
	//private String editor; //also treated like authors
	private String title; //we have to ignore the various element types and retrieve the data only (they are all the same)
	//private String booktitle;
	
	private int year;
	private String address;

//	`id` int(11) NOT NULL,
//	  `title` varchar(52) NOT NULL,
//	  `author` varchar(52) NOT NULL,
//	  `picture` varchar(128) NOT NULL,
//	  `price` float NOT NULL,
//	  `publisher` varchar(52) NOT NULL,
//	  `dateofpublication` date NOT NULL,
//	  `pages` int(11) NOT NULL,
//	  `isbn` varchar(20) NOT NULL,
//	  `genre` varchar(20) NOT NULL
	//Book details
	private int bookID;
	private int usersellerID;
	private String sellerUsername;
	private String booktitle;
	private String author;
	private String picture;
	private String price;
	private String publisher;
	private String dop;
	private String pages; //this tends to be ranges
	private String isbn;
	private String genre;
	private String description;
	
	private String journal;
	private String volume;
	private String number;
	private String month;
	private String url;
	private String ee;
	private String cdrom;
	private String cite;

//	private String publisher;
	private String note;
	private String crossref;
//	private String isbn;
	private String series;
	private String school;
	private String chapter;
	
	/* attributes */
	private String key;
	private String mdate;
	private String publtype;
	private String reviewid;
	private String rating;
	
	private String pubType;
	
	public NACOABean(){
		//public constructor no arguments!
	}
	
	public ArrayList<String> getAuthors() {
		return this.authors;
	}
	
	public void setPrice (String price) {
		this.price = price;
	}
	
	public void setPicture (String picture) {
		this.picture = picture;
	}
	
	public void setDOP (String dop) {
		this.dop = dop;
	}
	
	public void setGenre (String genre) {
		this.genre = genre;
	}
	
	public String getPrice () {
		return this.price;
	}
	
	public String getPicture () {
		return this.picture;
	}
	
	public String getDOP () {
		return this.dop;
	}
	
	public String getGenre () {
		return this.genre;
	}

	public void setAuthors(ArrayList<String> authors) {
		this.authors = authors;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBooktitle() {
		return booktitle;
	}
	public void setBooktitle(String booktitle) {
		this.booktitle = booktitle;
	}
	public String getPages() {
		return pages;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getJournal() {
		return journal;
	}
	public void setJournal(String journal) {
		this.journal = journal;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEe() {
		return ee;
	}
	public void setEe(String ee) {
		this.ee = ee;
	}
	public String getCdrom() {
		return cdrom;
	}
	public void setCdrom(String cdrom) {
		this.cdrom = cdrom;
	}
	public String getCite() {
		return cite;
	}
	public void setCite(String cite) {
		this.cite = cite;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCrossref() {
		return crossref;
	}
	public void setCrossref(String crossref) {
		this.crossref = crossref;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getChapter() {
		return chapter;
	}
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMdate() {
		return mdate;
	}
	public void setMdate(String mdate) {
		this.mdate = mdate;
	}
	public String getPubltype() {
		return publtype;
	}
	public void setPubltype(String publtype) {
		this.publtype = publtype;
	}
	public String getReviewid() {
		return reviewid;
	}
	public void setReviewid(String reviewid) {
		this.reviewid = reviewid;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public int getXmlID() {
		return xmlID;
	}
	public void setXmlID(int xmlID) {
		this.xmlID = xmlID;
	}
	public String getPubType() {
		return pubType;
	}
	public void setPubType(String pubType) {
		this.pubType = pubType;
	}

	public int getBookID() {
		return this.bookID;
	}
	
	public void setBookID(int bID) {
		this.bookID = bID;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	public String getDop() {
		return dop;
	}

	public void setDop(String dop) {
		this.dop = dop;
	}
	
	public void setUserSellerID(int user_seller_id) {
		this.usersellerID = user_seller_id;
	}
	
	public int getUserSellerID() {
		return this.usersellerID;
	}
	
	public void setSellerUsername(String username) {
		this.sellerUsername = username;
	}
	
	public String getSellerName() {
		return this.sellerUsername;
	}
}
