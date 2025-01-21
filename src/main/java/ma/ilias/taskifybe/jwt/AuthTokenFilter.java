package ma.ilias.taskifybe.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("Calling auth token filter for url {}", request.getRequestURI());
        // get the token from header
        String jwtToken = jwtUtils.getJwtDetailsFromHeader(request);
        // validate the token
        if (jwtToken != null && jwtUtils.validateToken(jwtToken)) {
            // get the username
            String username = jwtUtils.extractUsernameFromJwtToken(jwtToken);
            // get the UserDetails object - username, roles etc. from extracted details
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // manually set authentication token in the request context
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            // when the user is authenticated, set the context
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            log.info("JWT token not valid");
        }
        filterChain.doFilter(request, response);
    }
}
