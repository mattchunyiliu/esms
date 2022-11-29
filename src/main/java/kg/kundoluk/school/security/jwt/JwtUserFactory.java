package kg.kundoluk.school.security.jwt;

import kg.kundoluk.school.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of Factory Method for class {@link JwtUser}.
 */
public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {


        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getEmail(),
                user.isEnabled(),
                user.getUpdatedAt(),
                mapToGrantedAuthorities(user)

        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(User user) {

        return user
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getCode()))
                .collect(Collectors.toList());
    }
}
