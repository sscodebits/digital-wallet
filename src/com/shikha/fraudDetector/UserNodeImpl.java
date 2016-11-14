package com.shikha.fraudDetector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implements {@link UserNode} interface
 * @author shikha
 *
 */
public class UserNodeImpl implements UserNode {
	private int username;
	private Set<UserNode> friendsList;
	
	public UserNodeImpl(int username) {
		this.username = username;
		friendsList = new HashSet<UserNode>();
	}
	
	public int getUserId() {
		return this.username;
	}
	
	public Collection<UserNode> getFriends() {
		return this.friendsList;
	}
	
	public void addFriend(UserNode friend) {
		this.friendsList.add(friend);
	}
	
	/**
	 * Prints User and it's Friends
	 */
	public void print() {
		System.out.println("UserNode: " + username);
		System.out.print(" Friends: ");
		for (UserNode un: getFriends()) {
			System.out.print("  " + un.getUserId() + " ");
		}
		System.out.println("");
	}
	
	
	@Override
	/**
	 * Overrides equals and hashcode to use just UsedId for comparison
	 */
	public boolean equals(Object user2) {
		return username == ((UserNodeImpl)user2).getUserId();
	}
	
	@Override
	public int hashCode() {
		/*int hash = 7;
		int strlen = username.length();
		for (int i = 0; i < strlen; i++) {
		    hash = hash*31 + username.charAt(i);
		}
		return hash;*/
		
		return username;
	}
}