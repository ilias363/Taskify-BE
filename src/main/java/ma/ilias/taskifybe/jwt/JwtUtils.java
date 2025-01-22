//package ma.ilias.taskifybe.jwt;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.io.Serializable;
//import java.security.Key;
//import java.util.Date;
//
//@Slf4j
//@Component
//public class JwtUtils implements Serializable {
//    private static final long serialVersionUID = 718364989462347816L;
//
//    @Value("${spring.app.jwtSecret}")
//    private String jwtSecret;
//
//    @Value("${spring.app.jwtExpirationInMs}")
//    private long jwtExpirationInMs;
//
//    public String getJwtDetailsFromHeader(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        } else {
//            return null;
//        }
//    }
//
//    private Key getSigningKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public boolean validateToken(String jwtToken) {
//        try {
//            log.info("Validating token {}", jwtToken);
//            Jwts.parser()
//                    .verifyWith((SecretKey) getSigningKey())
//                    .build().parseSignedClaims(jwtToken);
//
//            return true;
//        } catch (MalformedJwtException e) {
//            log.error("JWT token {} is invalid", jwtToken);
//        } catch (ExpiredJwtException e) {
//            log.error("JWT token {} is expired", jwtToken);
//        } catch (UnsupportedJwtException e) {
//            log.error("JWT token {} is not supported", jwtToken);
//        } catch (IllegalArgumentException e) {
//            log.error("JWT claims is empty");
//        }
//        return false;
//    }
//
//    public String extractUsernameFromJwtToken(String jwtToken) {
//        return Jwts.parser()
//                .verifyWith((SecretKey) getSigningKey())
//                .build()
//                .parseSignedClaims(jwtToken)
//                .getPayload()
//                .getSubject();
//    }
//
//    public String generateToken(UserDetails userDetails) {
//        String username = userDetails.getUsername();
//        return Jwts.builder()
//                .subject(username)
//                .signWith(getSigningKey())
//                .expiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
//                .issuedAt(new Date(System.currentTimeMillis()))
//                //.claims()
//                .compact();
//    }
//}
