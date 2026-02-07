package com.Velouraz.velouraz.security;

import com.Velouraz.velouraz.entity.User;
import com.Velouraz.velouraz.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        // 🔓 NO TOKEN → PUBLIC REQUEST (IMPORTANT)
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        String email;

        // ❗ Handle expired / invalid token safely
        try {
            email = jwtUtil.extractEmail(token);
        } catch (Exception e) {
            chain.doFilter(request, response);
            return;
        }

        // Authenticate ONLY if not already authenticated
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            User dbUser = userRepo.findByEmail(email).orElse(null);

            if (dbUser != null && jwtUtil.validateToken(token, email)) {

                UserDetails userDetails = org.springframework.security.core.userdetails.User
                        .withUsername(dbUser.getEmail())
                        .password(dbUser.getPassword())
                        .roles(dbUser.getRole()) // e.g. USER, ADMIN
                        .build();

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);
    }
}
