package az.azerenerji.security;

import az.azerenerji.exception.UserNameNotFoundException;
import az.azerenerji.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UserNameNotFoundException {
        return new UserPrincipal(userRepository.findByUserName(email).orElseThrow(UserNameNotFoundException::new));
    }

}