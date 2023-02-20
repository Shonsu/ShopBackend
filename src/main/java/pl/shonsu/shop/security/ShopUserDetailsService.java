package pl.shonsu.shop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.shonsu.shop.security.model.ShopUserDetails;
import pl.shonsu.shop.security.model.User;
import pl.shonsu.shop.security.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
        User user = userRepository.findByUuid(UUID.fromString(uuid)).orElseThrow();
        //User user = userRepository.findByUsername(uuid).orElseThrow();
        ShopUserDetails shopUserDetails = new ShopUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities().stream()
                        .map(userRole -> (GrantedAuthority) userRole::name)
                        .toList()
        );
        shopUserDetails.setId(user.getId());
        shopUserDetails.setUuid(user.getUuid());
        return shopUserDetails;
    }
}
