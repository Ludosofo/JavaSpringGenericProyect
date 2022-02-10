package com.capgemini.controllers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/* METODOS AUXILIARES PARA EL MAINCONTROLLER */
public class AuxiliarFunctions {

	// Establecemos sesion
    public HttpSession setSession( HttpServletRequest request, String alias, String public_key){
		request.getSession(true);
		request.getSession().setAttribute("MY_USER", alias);
		request.getSession().setAttribute("PUBLIC_KEY", public_key);
		return request.getSession();
	}

	// Obtenemos un String encriptado para usar como key
	public static String getMd5(String input) {
		try {
			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = md.digest(input.getBytes());
			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);
			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}
		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
