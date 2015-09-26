

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stores info on the result page to be displayed (current page, etc)
 * @author Felix
 *
 */
public class ResultPageUserBean implements Serializable {
 //so yeah this is the pagebean
	//no thats the other bean
	public boolean readMore; //are we viewing a specific title or result page?
	public boolean more; //next page nav?
	public boolean less; //prev page nav?
	public int totalResults; //all results returned
	public int startEntry; //the first entry num of this page
	public int curr_page_num;
	public int prev_page_num; //1 less than curr
	public int next_page_num; //1 more than curr
	public NACOABean readEntry; //is set only if readMore == true
	public ArrayList<NACOAUserBean> resultUserBeans; //should always be set
	

	public ResultPageUserBean (){
		//nothing to do...
	}



	public boolean isReadMore() {
		return readMore;
	}



	public void setReadMore(boolean readMore) {
		this.readMore = readMore;
	}



	public boolean isMore() {
		return more;
	}



	public void setMore(boolean more) {
		this.more = more;
	}



	public boolean isLess() {
		return less;
	}



	public void setLess(boolean less) {
		this.less = less;
	}



	public int getTotalResults() {
		return totalResults;
	}



	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}



	public int getStartEntry() {
		return startEntry;
	}



	public void setStartEntry(int startEntry) {
		this.startEntry = startEntry;
	}



	public int getCurr_page_num() {
		return curr_page_num;
	}



	public void setCurr_page_num(int curr_page_num) {
		this.curr_page_num = curr_page_num;
	}



	public int getPrev_page_num() {
		return prev_page_num;
	}



	public void setPrev_page_num(int prev_page_num) {
		this.prev_page_num = prev_page_num;
	}



	public NACOABean getReadEntry() {
		return readEntry;
	}



	public void setReadEntry(NACOABean readEntry) {
		this.readEntry = readEntry;
	}



	public int getNext_page_num() {
		return next_page_num;
	}



	public void setNext_page_num(int next_page_num) {
		this.next_page_num = next_page_num;
	}





	public ArrayList<NACOAUserBean> getResultBeans() {
		return resultUserBeans;
	}



	public void setResultBeans(ArrayList<NACOAUserBean> resultBeans) {
		this.resultUserBeans = resultBeans;
	}
	
	
	
}
