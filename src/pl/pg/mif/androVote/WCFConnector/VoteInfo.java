package pl.pg.mif.androVote.WCFConnector;

import java.util.HashMap;

/**
 * Class VoteInfo storing all the information needed by user about voting.
 * @author Erdk
 *
 */
public class VoteInfo {
	
	/**
	 * Returns voting id.
	 * @return Voting id.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets voting id.
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public HashMap<String, String> getAnswers() {
		return answers;
	}
	public void setAnswers(HashMap<String, String> answers) {
		this.answers = answers;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	private int id;
	private int userId;
	private String name;
	private String beginDate;
	private String endDate;
	private char type;
	private String question;
	//private String[] answers;
	private HashMap<String, String> answers;
}
