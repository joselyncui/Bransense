package com.matrix.brainsense.service;

import java.util.List;

import com.matrix.brainsense.entity.User;


public interface UserManager {

	/**
	 * add a user to database
	 * @param user <code>User</code>
	 * @return see <code>User</code>
	 */
	User addUser(User user);

	/**
	 * update user information
	 * @param user <code>User</code>
	 * @return if update success return true, else reutrn false
	 */
	boolean update(User user);

	
	
	
	
	
	List<User> getAll();

	void delete(User user);

}
