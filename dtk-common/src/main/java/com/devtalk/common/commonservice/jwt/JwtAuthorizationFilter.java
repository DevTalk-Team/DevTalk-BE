package com.devtalk.common.commonservice.jwt;

import com.devtalk.common.commonservice.error.ErrorCode;
import com.devtalk.common.commonservice.error.exception.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Value("${jwt.token.secret}")
    private String SECRET_KEY;
    private Key KEY;
    private final MemberDetailsService memberDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        String encodedKey = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        KEY = Keys.hmacShaKeyFor(encodedKey.getBytes());

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = resolveToken(request);

        if (token != null && validateToken(token)) {
            String email = getEmail(token);

            if (email != null) {
                MemberDetails memberDetails = memberDetailsService.loadUserByUsername(email);
                return new UsernamePasswordAuthenticationToken(memberDetails, token, memberDetails.getAuthorities());
            }

            return null;
        }

        return null;
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (SignatureException e) {
            throw new TokenException(ErrorCode.INCORRECT_SIGNATURE);
        } catch (ExpiredJwtException e) {
            throw new TokenException(ErrorCode.EXPIRED_TOKEN);
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            throw new TokenException(ErrorCode.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    private String getEmail(String token) {
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("email")
                .toString();
    }

//    private Long getExpiration(String token) {
//        Date expiration = Jwts.parser()
//                .setSigningKey(KEY)
//                .parseClaimsJws(token)
//                .getBody()
//                .getExpiration();
//        Long now = new Date().getTime();
//        return expiration.getTime() - now;
//    }
}