package com.matrix.brainsense.service;

import java.util.List;

import com.matrix.brainsense.entity.KeyCode;

public interface KeyCodeManager {
	
	/**
	 * check the key code is in database or not
	 * @param macAdd the MAC address of device
	 * @return if in the database return true, else return false
	 */
	boolean exist(String macAdd);

	/**
	 * check the key code is used by others or not
	 * @param macAdd the MAC address of device
	 * @return if is used by others return true, else return false
	 */
	boolean isInUse(String macAdd);

	/**
	 * get key code object by key code value 
	 * @param keyCode the key code value of device
	 * @return see <code>KeyCode</code>
	 */
	KeyCode getKeyCodeByKeyCode(String keyCode);

	/**
	 * add key code to database
	 * @param tmpKeyCode <code>KeyCode</code>
	 * @return if add success return ture, else return false
	 */
	KeyCode add(KeyCode tmpKeyCode);

	/**
	 * get all key codes
	 * @return return a list of <code>KeyCode</code>
	 */
	List<KeyCode> getAll();


}
