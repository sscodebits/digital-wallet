package com.shikha.fraudDetector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Processes Batch Payments and StreamPayments and detects any Fraud in Payment
 * using PaymentAnalyzer. It reads through the input files and writes the
 * results to output files
 * 
 * @author shikha
 *
 */
public class PaymentProcessor {
	static int MAX_LEVEL = 4;
	static String UNVERIFIED = "unverified\n";
	static String VERIFIED = "trusted\n";

	/**
	 * Processes Batch Payments file
	 * 
	 * @param printOut
	 *            boolean Flag for print
	 * @param file
	 *            Input Batch Payment File with all transactions
	 */
	public void processBatchPayments(boolean printOut, File file) {
		processFile(printOut, file, true, null, null, null);
	}

	/**
	 * Processes Stream Payments File
	 * 
	 * @param printOut
	 *            boolean flag for printing
	 * @param file
	 *            Input Stream Payment file with all transactions
	 * @param out1
	 *            Output File for Feature1 - Unverified if user1 never had
	 *            transaction with user2 before
	 * @param out2
	 *            Output File for Feature2 - Unverified if user2 is not find in
	 *            FriendofFriends(2nd degree level) social network
	 * @param out3
	 *            Output File for Feature 3 - Unverified if outside the 4th
	 *            degree friend network
	 */
	public void processStreamPayments(boolean printOut, File file, File out1, File out2, File out3) {
		processFile(printOut, file, false, out1, out2, out3);
	}

	/**
	 * Processes Batch payment single Transaction Line
	 * 
	 * @param printOut
	 *            boolean flag to tell if you want to print this
	 * @param line
	 *            string of one payment transaction
	 */
	void processBatchPayment(boolean printOut, String line) {
		UserPayment payment = getPayment(line);
		if (payment == null) {
			return;
		}

		PaymentAnalyzer.addBatchPayment(payment);
	}

	/**
	 * Processes Stream Payment single Transaction line
	 * Writes the result to output files
	 *  
	 * @param printOut
	 * @param line
	 * @param out1
	 * @param out2
	 * @param out3
	 */
	void processStreamPayment(boolean printOut, String line, BufferedWriter out1, BufferedWriter out2,
			BufferedWriter out3) {
		UserPayment payment = getPayment(line);
		if (payment == null) {
			return;
		}

		int level = PaymentAnalyzer.addStreamPayment(payment, MAX_LEVEL);
		writeOutput(level, out1, out2, out3);
		if (printOut) {
			System.out.println(" Payment " + payment.getId1() + " " + payment.getId2() + " level " + level);
		}
	}

	/**
	 * Writes the output to different output files depending on feature
	 * 
	 * @param level
	 * @param out1
	 * @param out2
	 * @param out3
	 */
	private void writeOutput(int level, BufferedWriter out1, BufferedWriter out2, BufferedWriter out3) {
		try {
			if (level == -1) {
				out1.write(UNVERIFIED);
				out2.write(UNVERIFIED);
				out3.write(UNVERIFIED);
			} else if (level == 1) {
				// user1 is friend of user2
				out1.write(VERIFIED);
				out2.write(VERIFIED);
				out3.write(VERIFIED);
			} else if (level == 2) {
				out1.write(UNVERIFIED);
				out2.write(VERIFIED);
				out3.write(VERIFIED);
			} else if (level == 3 || level == 4) {
				out1.write(UNVERIFIED);
				out2.write(UNVERIFIED);
				out3.write(VERIFIED);
			}
		} catch (IOException ie) {
		}
	}

	/**
	 * Parses the Payment Transaction line and returns a User Payment Object
	 * Doesn't differentiate between /r and /n - needs to be optimized 
	 * 
	 * @param line
	 *            Single Payment Transaction Line
	 * @return UserPayment
	 */
	private UserPayment getPayment(String line) {
		UserPayment payment = null;

		try {
			payment = UserPayment.parsePayment(line);
		} catch (IllegalArgumentException iae) {
			// ignore invalid lines
			// System.err.println("Ignored line " + line);
		}
		return payment;
	}

	/**
	 * 
	 * @param printOut
	 * @param fileDir
	 * @param batch
	 * @param out1
	 * @param out2
	 * @param out3
	 * @return
	 */
	boolean processFile(boolean printOut, File fileDir, boolean batch, File out1, File out2, File out3) {
		BufferedReader in = null;
		BufferedWriter pout1 = null, pout2 = null, pout3 = null;
		try {

			in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));

			if (!batch) {
				pout1 = new BufferedWriter(new FileWriter(out1));
				pout2 = new BufferedWriter(new FileWriter(out2));
				pout3 = new BufferedWriter(new FileWriter(out3));
			}

			String str;

			// ignore first line
			if ((str = in.readLine()) != null) {

				while ((str = in.readLine()) != null) {
					if (batch) {
						processBatchPayment(printOut, str);
					} else {
						processStreamPayment(printOut, str, pout1, pout2, pout3);
					}
				}
			}

			return true;
		} catch (UnsupportedEncodingException e) {
			System.err.println(e.getMessage());
			return false;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return false;
		} finally {
			closeFile(in);
			closeFile(pout1);
			closeFile(pout2);
			closeFile(pout3);
		}
	}

	/**
	 * 
	 */
	private void closeFile(BufferedReader in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
	}

	/**
	 * 
	 * @param out
	 */
	private void closeFile(BufferedWriter out) {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
	}

}
