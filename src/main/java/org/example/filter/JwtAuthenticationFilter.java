package org.example.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.model.User;
import org.example.service.JwtBlacklistService;
import org.example.service.JwtService;
import org.example.service.UserService;
import org.example.util.ProviderConstantUtil;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT authentication filter.
 * This filter applies to all API requests and is executed once per request.
 * It extracts the JWT token from the `Authorization` header, checks its validity and blacklist,
 * and then sets the Spring Security context based on the extracted username from the token.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final JwtBlacklistService jwtBlacklistService;

    private final UserDetailsService userDetailsService;

    private final UserService userService;

    /**
     * Overridden method for filtering requests.
     *
     * @param request     HTTP request
     * @param response    HTTP response
     * @param filterChain Spring Security filter chain
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        if (jwtBlacklistService.isTokenBlacklisted(jwt)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        username = jwtService.extractUsername(jwt);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            User user = userService.findUserByUsername(username).orElseThrow();
            if (user.getStatus().getId() == ProviderConstantUtil.USER_STATUS_BANNED ||
                    user.getStatus().getId() == ProviderConstantUtil.USER_STATUS_INACTIVE) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
