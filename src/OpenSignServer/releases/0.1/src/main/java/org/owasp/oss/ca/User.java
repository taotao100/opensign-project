/**
 * 
 */
package org.owasp.oss.ca;


/**
 * Class representing a system user
 */
//@Entity
//@Table(name = "User")
public class User {
	private long _id;
	private String _userName;
	private String _password;
	private String _emailAddress;
	private String _firstName;
	private String _lastName;	
	
	/**
	 * @return the _id
	 */
	public long getId() {
		return _id;
	}
	/**
	 * @param _id the _id to set
	 */
	public void setId(long _id) {
		this._id = _id;
	}
	/**
	 * @return the _userName
	 */
	public String getUserName() {
		return _userName;
	}
	/**
	 * @param name the _userName to set
	 */
	public void setUserName(String name) {
		_userName = name;
	}
	/**
	 * @return the _password
	 */
	public String getPassword() {
		return _password;
	}
	/**
	 * @param _password the _password to set
	 */
	public void setPassword(String _password) {
		this._password = _password;
	}
	/**
	 * @return the _emailAddress
	 */
	public String getEmailAddress() {
		return _emailAddress;
	}
	/**
	 * @param address the _emailAddress to set
	 */
	public void setEmailAddress(String address) {
		_emailAddress = address;
	}
	/**
	 * @return the _firstName
	 */
	public String getFirstName() {
		return _firstName;
	}
	/**
	 * @param name the _firstName to set
	 */
	public void setFirstName(String name) {
		_firstName = name;
	}
	/**
	 * @return the _lastName
	 */
	public String getLastName() {
		return _lastName;
	}
	/**
	 * @param name the _lastName to set
	 */
	public void setLastName(String name) {
		_lastName = name;
	}
}
