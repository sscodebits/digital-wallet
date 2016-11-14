package com.shikha.fraudDetector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Creates a social Graph for User by adding connection between two users
 * 
 * Performs Breadth First Search to find connection between two users and finds
 * level of degree between two users
 * 
 * @author shikha
 *
 */
public class UserGraph {
	private Map<Integer, UserNode> usernodes = new HashMap<Integer, UserNode>();

	// Singleton pattern
	private final static UserGraph userGraph = new UserGraph();

	// create a structure to store statistics
	// stores the count of returned values from BFS
	// key - returned value from BFS
	// value - count
	private Map<Integer, Integer> stats = new HashMap<>();

	// Singleton pattern
	public static UserGraph getInstance() {
		return userGraph;
	}

	/**
	 * Add a connection in the graph between user1 and user2
	 * 
	 * @param user1
	 * @param user2
	 */
	public void addConnection(int user1, int user2) {
		// find Nodes, add them if not found
		UserNode userNode1 = checkAndAdd(user1);
		UserNode userNode2 = checkAndAdd(user2);
		// create bi-directional connections
		userNode1.addFriend(userNode2);
		userNode2.addFriend(userNode1);
	}

	/**
	 * Performs Breadth First Search starting at userStartNode and returns level
	 * of degree of connection between userStart and userDestination
	 * 
	 * Returns level of the userDest node from userStart node. It returns -1 if userDestNode not found
	 * 
	 * @param userStart
	 *            Starting node for search
	 * @param userDest
	 *            Destination node for search
	 * @param level
	 *            Maximum degree of level to search for destination node
	 * @return Degree of connection between start user and destination User
	 */
	public int BFS(int userStart, int userDest, int level) {
		int retVal = doBFS(userStart, userDest, level);
		// insert results into stats structure
		if (stats.containsKey(retVal)) {
			stats.put(retVal, stats.get(retVal) + 1);
		} else {
			stats.put(retVal, 1);
		}
		return retVal;
	}

	public void printStats() {
		for (Map.Entry<Integer, Integer> entry : stats.entrySet()) {
			System.out.println(entry.getKey() + " returned " + entry.getValue() + " times");
		}
	}

	/*
	 * BFS Algorithm to search for connection between userStart and userDest and
	 * Returns degree of connection
	 */
	private int doBFS(int userStart, int userDest, int level) {

		if (usernodes.containsKey(userStart)) {
			UserNode nodeStart = usernodes.get(userStart);

			// basic BFS DS - queue to store the list of nodes to visit
			Queue<UserNode> queue = new LinkedList<>();

			// stores the list of nodes already visited to avoid visiting them again
			// value is the level of the nodes from userStart
			// this is used to stop the search if the current node level is beyond the input max level
			Map<Integer, Integer> explored = new HashMap<>();

			// start
			queue.add(nodeStart);
			explored.put(nodeStart.getUserId(), 0);

			while (!queue.isEmpty()) {
				// get next node to process
				UserNode current = queue.remove();
				// check if we have reached the dest
				if (current.getUserId() == userDest) {
					// return level of current node
					return explored.get(current.getUserId());
				} else {
					// add all friends to queue
					// if already visited, don't add them to queue
					for (UserNode node : current.getFriends()) {
						if (!explored.containsKey(node.getUserId())) {
							int nodeLevel = explored.get(current.getUserId());
							// if current node level is equal to max level,
							// then stop the search for children
							if (nodeLevel == level)
								return -1;
							// add node to explore to queue and set its level
							queue.add(node);
							explored.put(node.getUserId(), nodeLevel + 1);
						}
					}
				}
			}
		}

		return -1;
	}

	/*
	 * Print the graphs contents
	 */
	public void print() {
		System.out.println("User Graph");
		for (Map.Entry<Integer, UserNode> node : usernodes.entrySet()) {
			node.getValue().print();
		}
	}

	private UserNode checkAndAdd(int user) {
		if (usernodes.containsKey(user)) {
			return usernodes.get(user);
		} else {
			UserNode userNode = new UserNodeImpl(user);
			usernodes.put(user, userNode);
			return userNode;
		}
	}
}
