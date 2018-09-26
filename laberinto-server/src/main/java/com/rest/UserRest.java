package com.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.PropertyConfigurator;

import com.google.gson.Gson;
import com.modelo.User;

public class UserRest {
	
	private static String url="http://localhost:8080/app";

	private void iniciaOperacion() {
		Properties log4jProp = new Properties();
		log4jProp.setProperty("log4j.rootLogger", "WARN");
		PropertyConfigurator.configure(log4jProp);
	}

	public User existeUsuario(String user, String pass) throws ParseException, IOException {
		User objeto = null;

		iniciaOperacion();
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url+"/autenticar/");
		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("ssoId", user));
		params.add(new BasicNameValuePair("password", pass));
		httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		HttpResponse response = httpclient.execute(httppost);
		String json = EntityUtils.toString(response.getEntity());
		objeto = new Gson().fromJson(json, User.class);
		
		if (objeto != null)
			objeto.inicializarPerfiles(json);

		return objeto;
	}
	
	public User traerUser(int idUser) throws ParseException, IOException {
		User objeto = null;
		
			iniciaOperacion();
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost(url+"/user/");
			List<NameValuePair> params = new ArrayList<NameValuePair>(1);
			params.add(new BasicNameValuePair("id", String.valueOf(idUser)));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			String json = EntityUtils.toString(response.getEntity());
			objeto = new Gson().fromJson(json, User.class);
			if (objeto != null)
				objeto.inicializarPerfiles(json);
			
		return objeto;
	}
	
	

}
