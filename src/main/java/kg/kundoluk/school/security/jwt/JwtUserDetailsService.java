package kg.kundoluk.school.security.jwt;


import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final static Logger LOGGER = LoggerFactory.getLogger(JwtUserDetailsService.class);

    private final UserRepository userRepository;

    @Autowired
    public JwtUserDetailsService(UserRepository userService) {
        this.userRepository = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userRepository.findByUsernameCached(s);

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + s + " not found");
        }

        //LOGGER.info("LOADED USER: {}", s);

        return JwtUserFactory.create(user);
    }

    @Transactional
    public UserDetails loadUserByPhone(String phone) {
        User user = userRepository.findFirstByPhone(phone);
        return JwtUserFactory.create(user);
    }
}
