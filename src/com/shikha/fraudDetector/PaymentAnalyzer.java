package com.shikha.fraudDetector;

/**
 * This class analyzes the Payments and performs Fraud Detection
 * 
 * @author shikha
 *
 */
public class PaymentAnalyzer {
	
	/**
	 * 
	 * @param payment
	 */
	public static void addBatchPayment(UserPayment payment) {
		UserGraph.getInstance().addConnection(payment.getId1(), payment.getId2());
	}
	
	/**
	 * 
	 * @param payment
	 * @param level
	 * @return
	 */
	public static int addStreamPayment(UserPayment payment, int level) {
		int retVal = UserGraph.getInstance().BFS(payment.getId1(), payment.getId2(), level);
		UserGraph.getInstance().addConnection(payment.getId1(), payment.getId2());
		return retVal;
	}
	
	/**
	 * 
	 */
	public static void printUserInfo() {
		UserGraph.getInstance().print();
	}
	
	/**
	 * 
	 */
	public static void printStats() {
		UserGraph.getInstance().printStats();
	}
}
