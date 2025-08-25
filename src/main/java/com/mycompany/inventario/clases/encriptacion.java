/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.clases;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 *
 * @author User
 */
public class encriptacion {
    
    public static String hash(String contra){
        return BCrypt.withDefaults().hashToString(12, contra.toCharArray());
    }
    
    public static boolean verify(String contra, String encrip){
        BCrypt.Result result = BCrypt.verifyer().verify(contra.toCharArray(), encrip);
        return result.verified;
    }
    
}
