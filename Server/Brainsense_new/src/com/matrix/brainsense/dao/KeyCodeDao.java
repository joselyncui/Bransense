package com.matrix.brainsense.dao;

import com.matrix.brainsense.entity.KeyCode;

public interface KeyCodeDao extends BaseDao<KeyCode> {

	/**
	 * get key code by key code value
	 * @param keyCodeValue the value of key code
	 * @return see <code>KeyCode</code>
	 */
	KeyCode getKeyCodeByKeyCode(String keyCodeValue);

}
