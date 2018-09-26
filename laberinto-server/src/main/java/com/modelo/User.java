package com.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class User {



	private Integer id;

	private String ssoId;

	private String password;

	private String firstName;

	private String lastName;

	private String email;
	
	private List<UserProfile> userprofiles= new ArrayList<UserProfile>();


	public User(){};
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSsoId() {
		return ssoId;
	}

	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<UserProfile> getUserprofiles() {
		return userprofiles;
	}

	public void setUserprofiles(List<UserProfile> userprofiles) {
		this.userprofiles = userprofiles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", ssoId=" + ssoId + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + "]";
	}
	
	public boolean esAdmin(){
		UserProfile aux= new UserProfile();
		aux.setType("ADMIN");
		
	        for (UserProfile o : userprofiles) {
	            if (aux.getType().compareTo(o.getType())==0)
	            	
	                return true;
	        }
		return false;
	}
	
	public void inicializarPerfiles(String json){
	    JsonElement jelement = new JsonParser().parse(json);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    
	    JsonArray jarray = jobject.getAsJsonArray("userProfiles");
	    
		for(JsonElement elemento:jarray){
			userprofiles.add(new Gson().fromJson(elemento.getAsJsonObject(), UserProfile.class));
		}
	    
	}

}
