package com.web.movie.Config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
@Component  
public class JwtTokenProvider {
    @Value("${app.jwt.secret-key}")
    private String  secret_key;
    @Value("${app.jwt.duration}")
    private long expiration;

    public String generateTokenForResourceAccess(){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 120*60*1000); // Token valid for 120 minutes
        return Jwts.builder()
                .setIssuer("Phương đẹp trai")
                .setSubject("resource_access")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512,secret_key)
                .compact();
        }

    public String generateToken(String userId){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setIssuer("Phương đẹp trai")
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512,secret_key)
                .compact();
        }
    public String getUsernameFromJwt(String token){
        Claims claims = Jwts.parser()
                            .setSigningKey(secret_key)
                            .parseClaimsJws(token)
                            .getBody();
        return claims.getSubject();
    }
    public boolean isExpired(String token){
        if(token==null || token.isEmpty()) return true;
        Claims claims = Jwts.parser()
                            .setSigningKey(secret_key)
                            .parseClaimsJws(token)
                            .getBody();
        return claims.getExpiration().before(new Date());
    }
    public boolean validateToken(String authToken){
        try{
            Jwts.parser().setSigningKey(secret_key).parseClaimsJws(authToken);
            return true;
        }
        catch (MalformedJwtException ex) {
           System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
          System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }
}
