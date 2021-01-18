package br.com.myhomefinances.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String generateToken(String email) {
		Date now = new Date();
		Date dateExpiration = new Date(now.getTime() + expiration);

		return Jwts.builder()
				.setIssuer("API-MyHomeFinances")
				.setSubject(email)
				.setIssuedAt(now)
				.setExpiration(dateExpiration)
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}

	public boolean isTokenValid(String token) {
		Claims claims = getClaims(token);

		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());

			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}

		return false;
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);

		if (claims != null) {
			return claims.getSubject();
		}

		return null;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch(Exception e) {
			return null;
		}
	}

}
