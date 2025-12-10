package org.example.ordersystem.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.ordersystem.User.UserAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
class JwtService {

    private final SecretKey key;
    private final long expirationMs;

    public JwtService(
            @Value("${app.jwt.secret}") String base64Secret,
            @Value("${app.jwt.expiration-ms}") long expirationMs
    ){
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
        this.expirationMs = expirationMs;
    }

    public  String generateToken(UserAccount userAccount) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);
Map<String, Object> claims = new HashMap<>();
claims.put("uid", userAccount.getId());
claims.put("roles", "ROLE_" + userAccount.getRole().name());

Long customerId = (userAccount.getCustomer() != null) ? userAccount.getCustomer().getId() : null;
if(customerId != null){
    claims.put("customerId", customerId);
}
        return Jwts.builder()
                .setSubject(userAccount.getUsername())
                .addClaims(Map.of("uid", userAccount.getId(),
                        "roles",
                        "ROLE_" + userAccount.getRole().name()))
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserNameFromToken(String token) {
        return parseAllClaims(token).getSubject();
    }

    public Long extractUserIdFromToken(String token) {
        Object uid = parseAllClaims(token).get("uid");
        return (uid instanceof Integer i) ? i.longValue() : (uid instanceof Long l ? l : null);
    }

    public String extractRolesFromToken(String token) {
        Object roles = parseAllClaims(token).get("roles");
        return roles == null ? "" : roles.toString();
    }

    public boolean isTokenValid(String token, String expectedUsername) {
return  expectedUsername.equals(extractUserNameFromToken(token)) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        return parseAllClaims(token).getExpiration().before(new Date());
    }


    private Claims parseAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
