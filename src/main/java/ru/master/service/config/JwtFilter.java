package ru.master.service.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.master.service.auth.model.User;
import ru.master.service.auth.service.JwtService;
import ru.master.service.auth.service.TokenBlacklistService;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.exception.AppException;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;
    private final SecurityProperties securityProperties;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (isPublicEndpoint(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        String userPhoneNumber = null;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            userPhoneNumber = jwtService.extractPhoneNumber(jwt);
        } catch (AppException e) {
            response.sendError(e.getStatus().value(), e.getMessage());
            return;
        }

        if (!jwtService.isAccessToken(jwt)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ErrorMessage.ONLY_ACCESS_TOKENS_ALLOWED);
            return;
        }

        if (tokenBlacklistService.isBlacklisted(jwt)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ErrorMessage.TOKEN_REVOKED);
            return;
        }

        if (userPhoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if(!jwtService.isTokenExpired(jwt)) {
                var userId = jwtService.extractUserId(jwt);
                var authorities = jwtService.extractAuthorities(jwt);
                var authenticatedUser = User.builder()
                        .id(UUID.fromString(userId))
                        .phoneNumber(userPhoneNumber)
                        .role(jwtService.extractRole(jwt))
                        .build();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        authenticatedUser,
                        null,
                        authorities
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(HttpServletRequest request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();

        return securityProperties.getPublicEndpoints().stream()
                .anyMatch(endpoint ->
                        endpoint.getMethod().equalsIgnoreCase(method) &&
                                uri.matches(endpoint.getPath().replaceAll("\\*", ".*"))
                );
    }

}

