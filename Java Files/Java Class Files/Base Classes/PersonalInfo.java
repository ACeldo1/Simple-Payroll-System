package application;


import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class PersonalInfo {

	
	private Integer ID;
	private String FirstName = new String();
	private String LastName = new String();
	private String Address = new String();
	private String Password = new String();
	private String Email = new String();
	private String Birthday = new String(); 
	private String Contact = new String();
	private String EmergencyContact = new String();
	private String SocialSecurity = new String();
	private String BenefitsCode = new String();
	
	//
	public PersonalInfo()
	{
		
	}
	
	//This one is without emergency contact 
/*
	public PersonalInfo(int id, String firstName, String lastName, String birthday,String address, String email, String contact, String socialSecurity) throws ParseException {
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthday); 
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setBirthday(date);
		setAddress(address);
		setEmail(email);
		setContact(contact);
		setSocialSecurity(socialSecurity);
		
	}
	*/
//This one with emergency contact
	public PersonalInfo(Integer id, String firstName, String lastName,String birthday, String address, String email, String contact, String emergencyContact, String socialSecurity, String password, String benefitsCode) throws ParseException {
//		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthday); 
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setBirthday(birthday);
		setAddress(address);
		setEmail(email);
		setContact(contact);
		setEmergencyContact(emergencyContact);
		setSocialSecurity(socialSecurity);
		Password = password;
		setBenefitsCode(benefitsCode);
		
	}

	
	protected Integer getId() {
		return ID;
	}
	protected void setId(Integer id) {
		this.ID = id;
	}
	protected String getFirstName() {
		return FirstName;
	}
	protected void setFirstName(String firstName) {
		FirstName = firstName;
	}
	protected String getLastName() {
		return LastName;
	}
	protected void setLastName(String lastName) {
		LastName = lastName;
	}
	protected String getAddress() {
		return Address;
	}
	protected void setAddress(String address) {
		Address = address;
	}
	protected String getEmail() {
		return Email;
	}
	protected void setEmail(String email) {
		Email = email;
	}
	protected String getBirthday() {
		return Birthday;
	}

	protected String getPassword() {
		return Password;
	}

	protected void setBirthday(String birthday) {
		Birthday = birthday;
	}
	
	protected String getContact() {
		return Contact;
	}
	protected void setContact(String contact2) {
		Contact = contact2;
	}
	protected String getEmergencyContact() {
		return EmergencyContact;
	}
	protected void setEmergencyContact(String emergencyContact2) {
		EmergencyContact = emergencyContact2;
	}
	protected String getSocialSecurity() {
		return SocialSecurity;
	}
	protected void setSocialSecurity(String socialSecurity2) {
		SocialSecurity = socialSecurity2;
	}
	
	protected void setPassword(String password) {
		Password = encryptThisString(password);
	}
	
	protected void setBenefitsCode(String code) {
		BenefitsCode = code;
	}
	
	protected String getBenefitsCode() {
		return BenefitsCode;
	}

	//Courtesy of GeeksForGeeks
	 public String encryptThisString(String input) 

	    { 
	        try { 
	            // getInstance() method is called with algorithm SHA-1 
	            MessageDigest md = MessageDigest.getInstance("SHA-1"); 
	  
	            // digest() method is called 
	            // to calculate message digest of the input string 
	            // returned as array of byte 
	            byte[] messageDigest = md.digest(input.getBytes()); 
	  
	            // Convert byte array into signum representation 
	            BigInteger no = new BigInteger(1, messageDigest); 
	  
	            // Convert message digest into hex value 
	            String hashtext = no.toString(16); 
	  
	            // Add preceding 0s to make it 32 bit 
	            while (hashtext.length() < 32) { 
	                hashtext = "0" + hashtext; 
	            } 
	  
	            // return the HashText 
	            return hashtext; 
	        } 
	  
	        // For specifying wrong message digest algorithms 
	        catch (NoSuchAlgorithmException e) { 
	            throw new RuntimeException(e); 
	        } 
	    }

}



