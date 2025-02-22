package org.upskill.springboot.Config.AuthConfig;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for converting a JWT (JSON Web Token) into an {@link AbstractAuthenticationToken}.
 * It extracts roles from the JWT and creates a {@link JwtAuthenticationToken} with the corresponding authorities.
 *
 * The class implements the {@link Converter} interface, converting a JWT into an authentication token used
 * in Spring Security.
 */
@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    /**
     * Converts the given JWT to an authentication token with authorities.
     *
     * @param jwt the JWT containing the authentication details.
     * @return a JwtAuthenticationToken with authorities extracted from the JWT.
     */
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractRoles(jwt);
        return new JwtAuthenticationToken(jwt, authorities);
    }

    /**
     * Extracts roles from the JWT's claims and returns them as a collection of granted authorities.
     *
     * @param jwt the JWT containing the claim with roles.
     * @return a collection of {@link GrantedAuthority} based on the roles extracted from the JWT.
     */
    private Collection<GrantedAuthority> extractRoles(Jwt jwt) {
        // Extract the role from the JWT claim
        String role = jwt.getClaimAsString("http://schemas.microsoft.com/ws/2008/06/identity/claims/role");
        if (role == null || role.isEmpty()) {
            return Collections.emptyList();
        }
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
    }
}