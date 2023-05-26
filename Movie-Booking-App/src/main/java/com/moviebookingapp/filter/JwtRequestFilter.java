package com.moviebookingapp.filter;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.moviebookingapp.model.UserImpl;
import com.moviebookingapp.service.CustomerUserDetailsService;
import com.moviebookingapp.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomerUserDetailsService service;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        String token = null;
        String userName = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            //userName = jwtUtil.extractUsername(token);
            try {
                userName = jwtUtil.extractUsername(token);
                
                }catch (IllegalArgumentException e) {
                    logger.error("illegel.arg");
                } catch (ExpiredJwtException e) {
                    logger.error("Expired");
                    throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "session is expired");
                } catch(SignatureException e){
                    logger.error("token is not vsalid");
                    
            }
        }else {
        	logger.error("jwt token is not started with bearer");
        	
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserImpl userDetails = service.loadUserByUsername(userName);

            if (jwtUtil.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}