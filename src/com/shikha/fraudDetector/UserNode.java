package com.shikha.fraudDetector;

import java.util.Collection;

/**
 * Contains User Information- userId and list of User's friends
 * 
 * @author shikha
 *
 */
public interface UserNode {
	
	/**
	 * Returns UserId
	 * @return
	 */
	int getUserId();
	
	/**
	 * Returns Collection of Friends
	 * @return
	 */
	Collection<UserNode> getFriends();
	
	/**
	 * Add friend
	 * @param usernode
	 */
	void addFriend(UserNode usernode);
	
	/**
	 * Prints User and it's Friends
	 */
	void print();
}