
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * Deals with the data access for the web application
 * 
 * It parses XML (DBLP) into a document to be searched
 * 
 * Provides functionality:
 * 
 * **Searches using XPath
 * 
 * **Generates XML files from document
 * 
 * **Appends/Deletes entities from XML files (only the results/shopping cart)
 * 
 * @author Felix
 *
 */

public class NACOAHandler {
	Logger logger = Logger.getLogger(this.getClass().getName());
	final static int MOST_RETRIEVE = 10;
	
	/* our stuff */
	
	private Document mainDoc; //the DBLP database to be searched
	private Document results; //our new results
	private Document shpCart; //the user's shopping cart
	
	public NACOAHandler (){
		mainDoc = null;
		results = null;
		shpCart = null;
	}
	
	/**
	 * Load a given XML file to be the DBLP
	 */
	public void loadMainXML (String location) throws Exception { 		
		//create a doc with the xml file
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		this.mainDoc = builder.parse(location);
		System.out.println("Location is " + location);
	}
	
	public void loadResultsXML (String location) throws Exception { 		
		//create a doc with the xml file
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		this.results = builder.parse(location);
	}
	
	public void loadCartXML (String location) throws Exception { 		
		//create a doc with the xml file
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		this.shpCart = builder.parse(location);
		
		if(this.shpCart == null){
			System.out.println("shpCart is still null despite being loaded!");
		}
	}
	
	public ArrayList<NACOABean> getBeanFromMainDoc(int start){
		return getBeanFromXML(this.mainDoc, start);
	}
	
	public ArrayList<NACOABean> getBeanFromResultDoc(int start){
		return getBeanFromXML(this.results, start);
	}
	
	public ArrayList<NACOABean> getBeanFromCartDoc(int start){
		return getBeanFromXML(this.shpCart, start);
	}
	
	
	private ArrayList<NACOABean> getBeanFromXML(Document doc, int start){
		ArrayList<NACOABean> beans = new ArrayList<NACOABean>();
		
		//lets default grab it from the main file
		NodeList realRootNodes = doc.getElementsByTagName("dblp");
		Node realRootNode = realRootNodes.item(0);
		
		//get the root's children
		NodeList rootChildren = realRootNode.getChildNodes();
		
		//iterate through and generate beans for every entry in the xml file (up to the limit)
		for (int i=start; (i < (start+MOST_RETRIEVE) && i < rootChildren.getLength()); i++){
			Node currEntry = rootChildren.item(i);
			
			//we append this new Entry to the new tree we are making
			if(currEntry.hasChildNodes()){
				//cre8 da beenz
				//generate 10 Beans
				//harvest the xml 
				NACOABean newBean = new NACOABean();
				
				//TODO: FIX THIS PROBLEM BELOW
				newBean.setXmlID(i); //for retrieval later from the xml file... 
				newBean.setPubType(currEntry.getNodeName());
				
				//get the entry's elements (author, title, etc)
				NodeList elementChildren = currEntry.getChildNodes();
				ArrayList<String> allAuthors = new ArrayList<String>();
				
				for (int j=0; j < elementChildren.getLength(); j++){
					newBean.setXmlID(i);
					Node currElement = elementChildren.item(j);
					
					if(currElement.getNodeName().replaceAll("\\s","").matches("author") ){
						//newBean.setAuthor("Dummy"+i);
						allAuthors.add(currElement.getTextContent());
					} else if (currElement.getNodeName().replaceAll("\\s","").matches("editor")){
						
						allAuthors.add(currElement.getTextContent());
					} else if (currElement.getNodeName().replaceAll("\\s","").matches("title")){
						
						newBean.setTitle(currElement.getTextContent());
					} else if (currElement.getNodeName().replaceAll("\\s","").matches("year")){
						
						newBean.setYear(Integer.parseInt(currElement.getTextContent()));
					} else if (currElement.getNodeName().replaceAll("\\s","").matches("pages")){
						
						newBean.setPages(currElement.getTextContent());
					} else if (currElement.getNodeName().replaceAll("\\s","").matches("booktitle")){
						
						newBean.setBooktitle(currElement.getTextContent());
					} else if (currElement.getNodeName().replaceAll("\\s","").matches("url")){
						
						newBean.setUrl(currElement.getTextContent());
					} else if (currElement.getNodeName().replaceAll("\\s","").matches("ee")){
						
						newBean.setEe(currElement.getTextContent());
					} else if (currElement.getNodeName().replaceAll("\\s","").matches("volume")){
						
						newBean.setVolume(currElement.getTextContent());
					} else if (currElement.getNodeName().replaceAll("\\s","").matches("journal")){
						
						newBean.setJournal(currElement.getTextContent());
					} else if (currElement.getNodeName().replaceAll("\\s","").matches("series")){
						
						newBean.setSeries(currElement.getTextContent());
					} else if (currElement.getNodeName().replaceAll("\\s","").matches("publisher")){
						
						newBean.setPublisher(currElement.getTextContent());
					} else if (currElement.getNodeName().replaceAll("\\s","").matches("number")){
						
						newBean.setNote(currElement.getTextContent());
					} else if (currElement.getNodeName().replaceAll("\\s","").matches("note")){
						
						newBean.setNote(currElement.getTextContent());
					} else if (currElement.getNodeName().replaceAll("\\s","").matches("isbn")){
						
						newBean.setIsbn(currElement.getTextContent());
					} else if (currElement.getNodeName().replaceAll("\\s","").matches("school")){
						
						newBean.setSchool(currElement.getTextContent());
					} else if (currElement.getNodeName().replaceAll("\\s","").matches("crossref")){
						
						newBean.setCrossref(currElement.getTextContent());
					} else {
						/* as you find more attributes worth collecting, add them to the end 
						 * 
						 */
					}

				}
				//add Bean to bunch of Beans
				newBean.setAuthors(allAuthors);
				beans.add(newBean);
			}
		}

		return beans;
	}
	/**
	 * Call this to write the result doc to stored xml
	 */
	public void updateResultStoreXML(String location) throws Exception {
		//create transformers 
		TransformerFactory tranFactory = TransformerFactory.newInstance(); 	
		Transformer aTransformer = tranFactory.newTransformer(); 
	
		//new tree/root element to append children to
		NodeList realRootNodes = this.results.getElementsByTagName("dblp");
		Node realRootNode = realRootNodes.item(0);

		Source src = new DOMSource(realRootNode); 

		//the file
		FileOutputStream fs = new FileOutputStream(location);
		Result dest = new StreamResult(fs);
		
		//transform a source tree into a result tree
		aTransformer.transform(src, dest);
		fs.close();
		
	}
	
	/**
	 * Call this to write the result doc to stored xml
	 */
	public void updateCartXML(String location) throws Exception {
		//create transformers 
		TransformerFactory tranFactory = TransformerFactory.newInstance(); 	
		Transformer aTransformer = tranFactory.newTransformer(); 
	
		//new tree/root element to append children to
		NodeList realRootNodes = this.shpCart.getElementsByTagName("dblp");
		Node realRootNode = realRootNodes.item(0);

		Source src = new DOMSource(realRootNode); 

		//the file
		FileOutputStream fs = new FileOutputStream(location);
		Result dest = new StreamResult(fs);
		
		//transform a source tree into a result tree
		aTransformer.transform(src, dest);
		fs.close();
		
	}
	
	
	/**
	 * 
	 * Publication Type Search
	 * 
	 * Advanced Search
	 * 
	 * 
	 * @throws Exception
	 */
	
	public void searchBasicAuthor(String author) throws Exception {
		//String searchAuthor = "Sanjeev"; 
		String xmlPath = "//author|//editor";
		searchDoc(author, xmlPath, this.mainDoc);
	}
	
	public void searchBasicTitle(String title) throws Exception {
		String xmlPath = "//title";
		searchDoc(title, xmlPath, this.mainDoc);
	}
	
	public void searchBasicYear(String year) throws Exception {
		String xmlPath = "//year";
		searchDoc(year, xmlPath, this.mainDoc);
	}
	
	public void searchBasicVenue(String venue) throws Exception {
		//can only be proceedings
		String xmlPath = "//booktitle";
		searchDoc(venue, xmlPath, this.mainDoc);
	}
	
	public void searchAdvanced(String author, String title, String venue, String year, String pubType) throws Exception {
		String xmlPath;
		
		//we search first based on pubType (if set)
		if (pubType == null){
			//we start by eliminating the most we possibly can in one go...
			//we probably eliminate in this order: venue, author, title, year

			
			xmlPath = "//"; 
			
			
		} else {
			//we start by eliminating the most we possibly can in one go...
			//we probably eliminate in this order: venue, author, title, year
			
			//we only search with nodes of that pubType
			
			if(pubType.contains("Journal")){
				pubType = "article";
			} else if (pubType.contains("Conference")){
				pubType = "inproceedings";
			} else if (pubType.contains("Book/Collections")){
				pubType = "incollection";
			} else if (pubType.contains("Editorship/Proceedings")){
				pubType = "proceedings";
			} else {
				//error!
				return;
			}
			
			xmlPath = "//" + pubType + "/"; 

		}
		
		Document temp = this.mainDoc;
		
		if(author.equals("") && title.equals("") && venue.equals("") && year.equals("")){
			String xmlPathQuery = xmlPath + "title"; //in the case where there is no query, we look up all entries with titles
			searchDoc(venue, xmlPathQuery, temp);
		}
		
		if (!venue.equals("")){
			String xmlPathQuery = xmlPath + "booktitle";
			searchDoc(venue, xmlPathQuery, temp);
			temp = cloneDocument(this.results);
		}
		if (!author.equals("")){
			String xmlPathQuery = xmlPath + "author";
			searchDoc(author, xmlPathQuery, temp);
			temp = cloneDocument(this.results);
		}
		if (!title.equals("")){
			String xmlPathQuery = xmlPath + "title";
			searchDoc(title, xmlPathQuery, temp);
			temp = cloneDocument(this.results);
		}
		if (!year.equals("")){
			String xmlPathQuery = xmlPath + "year";
			searchDoc(year, xmlPathQuery, temp);
			temp = cloneDocument(this.results);
		}
		
	}
	
	public Document cloneDocument(Document originalDocument) throws ParserConfigurationException{
		
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Node originalRoot = originalDocument.getDocumentElement();

        Document copiedDocument = db.newDocument();
        Node copiedRoot = copiedDocument.importNode(originalRoot, true);
        copiedDocument.appendChild(copiedRoot);
        
        return copiedDocument;
	}
	
	public void searchDoc(String query, String xmlPath, Document doc) throws Exception {
		//search criteria...
		//only for authors, venues or 
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document newDoc = builder.newDocument();
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = (XPath) xPathfactory.newXPath();

		Element resultTree = newDoc.createElement(doc.getFirstChild().getNodeName());
		
		XPathExpression expr = xpath.compile(xmlPath);
		NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		
		//go through every node
		
		for (int i=0; (i < nodes.getLength()); i++){
			Node currEntry = nodes.item(i);
			
			//matching with the search query
			if(currEntry.getTextContent().matches(".*"+query+".*")){
				Node parentNode = currEntry.getParentNode();
							
				Node importedNode = newDoc.importNode(parentNode, true);
				
				//we test... are appending the author's parent (the publication)
				resultTree.appendChild(importedNode);

			} 
		}
		
		newDoc.appendChild(resultTree);
		this.results = newDoc;
	
	}
	
	
	/**
	 * Append an entry to the cart document
	 * Index is the index of the results doc
	 */
	public void appendToCart(int index) { 

		//Seems like after placing a doctype, we need to explicitly state the root node name to find the root node (there can be more than 1)
		//else without the doctype declaration in the xml, we are able to find it as the root node
		NodeList realRootNodes = this.results.getElementsByTagName("dblp");
		Node realRootNode = realRootNodes.item(0);
		
		//get the root's children
		NodeList rootChildren = realRootNode.getChildNodes();
		//get the specific entry
		Node entryToAdd = this.shpCart.importNode(rootChildren.item(index), true);
		
		Node shpCartRootNode = this.shpCart.getElementsByTagName("dblp").item(0);
	
		shpCartRootNode.appendChild(entryToAdd);

	}
	
	/**
	 * Append list to results to document + xml
	 */
	
	//btw guys im really hungry 
	//Cachey 
	//woah
	//ok i have to go for a sec brb 
	
	public void appendListToResults(NodeList results) throws Exception { 
		//create transformers 
		TransformerFactory tranFactory = TransformerFactory.newInstance(); 
		Transformer aTransformer = tranFactory.newTransformer(); 
				
		//get the root's children
		NodeList rootChildren = mainDoc.getFirstChild().getChildNodes();
		
		//new root element to append children to
		Element newRoot = mainDoc.createElement(mainDoc.getFirstChild().getNodeName());
		
		for (int i=0; (i < 123 && i < rootChildren.getLength()); i++){
			Node newEntry = rootChildren.item(i).cloneNode(true);
			
			//we append this new Entry to the new tree we are making
			if(newEntry.hasChildNodes()){
				newRoot.appendChild(newEntry);
			}
		}
		Source src = new DOMSource(newRoot); 
	
		//the file
		FileOutputStream fs=new FileOutputStream("qw");
		Result dest = new StreamResult(fs);
		
		//transform a source tree into a result tree
		aTransformer.transform(src, dest);
		fs.close();
	}
	
	public boolean fileExists(String location){
		File f = new File(location);
		if(f.exists() && !f.isDirectory()) {
			return true;
		} 
		
		return false;
		
	}
	
	public void setCartToSession (String name, HttpSession session){
		session.setAttribute(name, this.shpCart);
	}
	
	public void setSessionCartToDoc (String name, HttpSession session){
		this.shpCart = (Document) session.getAttribute(name);
	}
	
	public void createCartXML (String location) throws Exception{
		//create transformers 
		TransformerFactory tranFactory = TransformerFactory.newInstance(); 	
		Transformer aTransformer = tranFactory.newTransformer(); 
	
		//new tree/root element to append children to
		Element newRoot = this.mainDoc.createElement(this.mainDoc.getFirstChild().getNodeName());

		Source src = new DOMSource(newRoot); 

		//the file
		FileOutputStream fs = new FileOutputStream(location);
		Result dest = new StreamResult(fs);
		
		//transform a source tree into a result tree
		aTransformer.transform(src, dest);
		fs.close();
	}
	
	
	public void printOutDoc(Document doc) throws IOException{
		OutputFormat format = new OutputFormat(doc);
        format.setIndenting(true);
        XMLSerializer serializer = new XMLSerializer(System.out, format);
        serializer.serialize(doc);
	}
	
	public void removeFromCart(int index){

		//Seems like after placing a doctype, we need to explicitly state the root node name to find the root node (there can be more than 1)
		//else without the doctype declaration in the xml, we are able to find it as the root node
		NodeList realRootNodes = this.shpCart.getElementsByTagName("dblp");
		Node realRootNode = realRootNodes.item(0);	
		
		//get the specific entry	
		Element toRemove = (Element) realRootNode.getChildNodes().item(index);
		
		toRemove.getParentNode().removeChild(toRemove);
		
	}
	
	/**
	 * Gets the number of entries in the result doc
	 * @return
	 */
	public int getNumResults(){
		//lets default grab it from the main file
		NodeList realRootNodes = this.results.getElementsByTagName("dblp");
		Node realRootNode = realRootNodes.item(0);
		
		//get the root's children
		NodeList rootChildren = realRootNode.getChildNodes();
		
		return rootChildren.getLength();
	}
	
	
	/**
	 * Gets the number of entries in the result doc
	 * @return
	 */
	public int getNumMain(){
		//lets default grab it from the main file
		NodeList realRootNodes = this.mainDoc.getElementsByTagName("dblp");
		Node realRootNode = realRootNodes.item(0);
		
		//get the root's children
		NodeList rootChildren = realRootNode.getChildNodes();
		
		return rootChildren.getLength();
	}
	
	public NACOABean getEntryMain(int index){
		//lets default grab it from the main file
		NodeList realRootNodes = this.mainDoc.getElementsByTagName("dblp");
		Node realRootNode = realRootNodes.item(0);
		
		//get the root's children
		NodeList rootChildren = realRootNode.getChildNodes();
		
		Node currEntry = rootChildren.item(index);
		NACOABean newBean = new NACOABean();
		
		//we append this new Entry to the new tree we are making
		if(currEntry.hasChildNodes()){
			//cre8 da beenz
			//generate 10 Beans
			//harvest the xml 
			
			//get the entry's elements (author, title, etc)
			NodeList elementChildren = currEntry.getChildNodes();
			ArrayList<String> allAuthors = new ArrayList<String>();
			
			for (int j=0; j < elementChildren.getLength(); j++){
				//newBean.setXmlID(i);
				Node currElement = elementChildren.item(j);
				
				if(currElement.getNodeName().replaceAll("\\s","").matches("author") ){
					//newBean.setAuthor("Dummy"+i);
					allAuthors.add(currElement.getTextContent());
				} else if (currElement.getNodeName().replaceAll("\\s","").matches("title")){
					
					newBean.setTitle(currElement.getTextContent());
				} else if (currElement.getNodeName().replaceAll("\\s","").matches("year")){
					
					newBean.setYear(Integer.parseInt(currElement.getTextContent()));
				} else if (currElement.getNodeName().replaceAll("\\s","").matches("pages")){
					
					newBean.setPages(currElement.getTextContent());
				} else if (currElement.getNodeName().replaceAll("\\s","").matches("booktitle")){
					
					newBean.setBooktitle(currElement.getTextContent());
				} else if (currElement.getNodeName().replaceAll("\\s","").matches("url")){
					
					newBean.setUrl(currElement.getTextContent());
				} else if (currElement.getNodeName().replaceAll("\\s","").matches("ee")){
					
					newBean.setEe(currElement.getTextContent());
				} else if (currElement.getNodeName().replaceAll("\\s","").matches("volume")){
					
					newBean.setVolume(currElement.getTextContent());
				} else if (currElement.getNodeName().replaceAll("\\s","").matches("journal")){
					
					newBean.setJournal(currElement.getTextContent());
				} else if (currElement.getNodeName().replaceAll("\\s","").matches("series")){
					
					newBean.setSeries(currElement.getTextContent());
				} else if (currElement.getNodeName().replaceAll("\\s","").matches("publisher")){
					
					newBean.setPublisher(currElement.getTextContent());
				} else if (currElement.getNodeName().replaceAll("\\s","").matches("number")){
					
					newBean.setNote(currElement.getTextContent());
				} else if (currElement.getNodeName().replaceAll("\\s","").matches("note")){
					
					newBean.setNote(currElement.getTextContent());
				} else if (currElement.getNodeName().replaceAll("\\s","").matches("isbn")){
					
					newBean.setIsbn(currElement.getTextContent());
				} else {
					/* as you find more attributes worth collecting, add them to the end 
					 * 
					 */
				}

			}
			//add Bean to bunch of Beans
			newBean.setAuthors(allAuthors);
			
		} else {
			newBean = null;
		}
		
		return newBean;
	}

}
