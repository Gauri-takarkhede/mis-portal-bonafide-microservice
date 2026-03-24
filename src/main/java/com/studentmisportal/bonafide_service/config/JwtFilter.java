package com.studentmisportal.bonafide_service.config;

import com.studentmisportal.bonafide_service.service.JwtUtil;
import com.studentmisportal.bonafide_service.service.UserInfoDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserInfoDetailsService userDetailsService;

//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        if (request.getServletPath().startsWith("/auth")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//
//            String token = authHeader.substring(7);
//            String mis = null;
//
//            try {
//                mis = jwtUtil.extractSubject(token);
//            }
//            catch (io.jsonwebtoken.ExpiredJwtException e) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
//                return;
//            }
//            catch (Exception e) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
//                return;
//            }
//
//            if (!jwtUtil.isTokenValid(token, mis)){
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token 2");
//                return;
//            }
//
//            if (mis != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//                UserDetails userDetails = userDetailsService.loadUserByUsername(mis);
//
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(
//                                userDetails,
//                                null,
//                                userDetails.getAuthorities()
//                        );
//
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                // Validate using secret key only — pure local operation, no remote call
                Claims claims = jwtUtil.extractAllClaims(token);
                String username = claims.getSubject();

                if (username != null
                        && SecurityContextHolder.getContext().getAuthentication() == null) {

                    // Extract role from token claims (adjust key to match your MIS portal)
                    String role = claims.get("role", String.class);
                    List<SimpleGrantedAuthority> authorities = (role != null)
                            ? List.of(new SimpleGrantedAuthority(role))
                            : List.of(new SimpleGrantedAuthority("ROLE_USER"));

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    token,
                                    authorities
                            );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                // Invalid token — let the request fail at authorization stage
                logger.warn("JWT validation failed: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}