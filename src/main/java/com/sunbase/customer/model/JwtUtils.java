package com.sunbase.customer.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {
    private static final String SECRET_KEY = "sunbasecustomerdemoproject1234fbgdgdfhdfhvbmnmnmcfghghdfhgdjhjkhgfgffgbnbvnbm";
  
    //generate token method
    public static String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static Claims decodeToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    public static String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody().getSubject();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
