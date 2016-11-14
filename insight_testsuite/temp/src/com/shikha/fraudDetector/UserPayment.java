package com.shikha.fraudDetector;

/**
 * Contains User Payment Transaction data
 * 
 * @author shikha
 *
 */
public class UserPayment {
	private String dateStamp;
	private int id1;
	private int id2;
	private float amt;
	
	public UserPayment (String dateStamp, int id1, int id2, float amt) {
		this.dateStamp = dateStamp;
		this.id1= id1;
		this.id2=id2;
		this.amt=amt;
	}
	
	/**
	 * Parse a line with the format and return UserPayment class 
	 * date, id1, id2, amt, msg
	 * Message is not stored
	 * @param line
	 * @return
	 */
	public static UserPayment parsePayment(String line) {
		String[] tokens = line.split(",");
		
		try {
		if (tokens.length >= 5) {
			String dateStamp = tokens[0].trim();
			int id1 = Integer.parseInt(tokens[1].trim());
			int id2 = Integer.parseInt(tokens[2].trim());
			float amt = Float.parseFloat(tokens[3].trim());
			return new UserPayment(dateStamp, id1, id2,amt);
		}
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Invalid line input " + line + " " + nfe.getMessage());
		}
		
		throw new IllegalArgumentException("Invalid line input " + line);
	}

	public String getDateStamp() {
		return dateStamp;
	}

	public int getId1() {
		return id1;
	}

	public int getId2() {
		return id2;
	}

	public float getAmt() {
		return amt;
	}
}
