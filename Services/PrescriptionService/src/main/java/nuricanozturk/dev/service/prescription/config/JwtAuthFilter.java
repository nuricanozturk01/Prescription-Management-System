package nuricanozturk.dev.service.prescription.config;

import callofproject.dev.service.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nuricanozturk.dev.service.prescription.repository.IPharmacyRepository;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter
{
    private final IPharmacyRepository m_pharmacyRepository;

    public JwtAuthFilter(IPharmacyRepository pharmacyRepository)
    {
        m_pharmacyRepository = pharmacyRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        try
        {
            var authenticationHeader = request.getHeader("Authorization");

            if (authenticationHeader != null && authenticationHeader.startsWith("Bearer "))
            {
                var token = authenticationHeader.substring(7);
                var username = JwtUtil.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
                {
                    var user = m_pharmacyRepository.findPharmacyByUsername(username);

                    if (JwtUtil.isTokenValid(token, username) && username.equals(user.get().getUsername()))
                    {
                        var authToken = new UsernamePasswordAuthenticationToken(user, null, null);
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } else throw new AccessDeniedException("Token not found or user not authenticated!");
            }
            filterChain.doFilter(request, response);
        } catch (BadCredentialsException | AccessDeniedException e)
        {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}