package com.shikha.fraudDetector;

import java.io.File;

/**
 * This class takes input as batch_payment.txt file and stream_payment.txt file
 * Then processes Batch and Stream Payment using PaymentProcessor It is mainly
 * used for validating the input arguments to the code base
 * 
 * @author shikha
 *
 */
public class PaymentInput {
	// Prints processing output
	// should be moved to logging framework
	static boolean printOut = false;

	/**
	 * Main method that validates the input params
	 * and starts the processing
	 * @param args
	 */
	public static void main(String[] args) {
		PaymentInput input = new PaymentInput();

		if (!input.validateArgs(args)) {
			System.err.println("Invalid Parameters");
			return;
		}
		File batch = input.checkFileReadable(args[0], true);
		File stream = input.checkFileReadable(args[1], true);
		File out1 = input.checkFileReadable(args[2], false);
		File out2 = input.checkFileReadable(args[3], false);
		File out3 = input.checkFileReadable(args[4], false);

		if (batch == null || stream == null || out1 == null || out2 == null || out3 == null) {
			return;
		}

		input.processBatchFile(batch);
		input.processStreamFile(stream, out1, out2, out3);
	}
	
	
	// Processing batch payments
	private boolean processBatchFile(File batch) {
		if (printOut) {
			System.out.println("Starting Batch processing ");
		}

		PaymentProcessor processor = new PaymentProcessor();
		processor.processBatchPayments(printOut, batch);
		if (printOut) {
			PaymentAnalyzer.printUserInfo();
		}

		return true;
	}

	// Processing stream payments as well as inits the output files
	private boolean processStreamFile(File stream, File out1, File out2, File out3) {
		if (printOut) {
			System.out.println("Starting Stream processing ");
		}
		PaymentProcessor processor = new PaymentProcessor();
		processor.processStreamPayments(printOut, stream, out1, out2, out3);
		if (printOut) {
			PaymentAnalyzer.printStats();
		}

		return true;
	}

	private boolean validateArgs(String[] args) {
		if (args.length != 5) {
			return false;
		}
		return true;
	}

	// Checks that the files are valid
	// For input files, checks that they exist and are readable
	// For output files, creates them if they don't exists and checks that they are writable
	private File checkFileReadable(String file, boolean readable) {
		try {
			File fileDir = new File(file);
			if (readable) {
				if (!fileDir.exists() || !fileDir.canRead()) {
					System.err.println("File " + file + " is not readable");
					return null;
				}
			} else {
				if (!fileDir.exists()) {
					fileDir.createNewFile();
				}
				if (!fileDir.canWrite()) {
					System.err.println("File " + file + " is not writable");
					return null;
				}
			}

			return fileDir;
		} catch (Exception e) {
			System.err.println("File " + file + " can't be processed " + e.getMessage());
			return null;
		}
	}

}
