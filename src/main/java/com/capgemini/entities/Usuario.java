package com.capgemini.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import org.springframework.util.DigestUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name="usuarios")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotEmpty(message = "ERROR su alias no puede estar vacio")
	@Size( min = 4, max = 20, message = "El nombre tiene que estar entre 4 y 20 caracteres")
	@Column( unique = true)
	private String alias;
	
	// @NotNull
	// @NotEmpty(message = "ERROR su nombre no puede estar vacio")
	// @Size( min = 4, max = 20, message = "El nombre tiene que estar entre 4 y 20 caracteres")
	@Null
	private String nombre;
	
	//@NotNull
	//@NotEmpty(message = "ERROR su apellidos no puede estar vacio")
	//@Size( min = 4, max = 20, message = "El nombre tiene que estar entre 4 y 20 caracteres")
	@Null
	private String apellidos;

	@NotNull
	@NotEmpty(message = "ERROR: su password no puede estar vacio")
	@Size( min = 6, message = "ERROR: El password tiene que tener minimo 6 caracteres")
	private String pass; // TODO: Investigar sobre seguridad
	
	// Añadimos un regex para la comprobación del email
	@NotNull
	@Email(message = "ERROR: Correo enviado no es valido", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
	private String mail;

	// Estos atributos pueden ser null
	// private String telefono;
	private String geo;
	private String avatar; // Esto sería un dato obtenido de una imagen


	void setPass(String value){
		this.pass = getMd5(value);
	}

	public static String getMd5(String input)
    {
        try {
  
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
  
            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
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
