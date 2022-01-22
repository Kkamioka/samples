package to.msn.wings.quickmaster.services;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
//import to.msn.wings.quickmaster.models.AppUser;
//import to.msn.wings.quickmaster.models.AppUserRepository;

@RequiredArgsConstructor
@Service
public class MyUserService implements UserDetailsService {
  
    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
        if (username.equals("admin")) {
             return new User(username,
                 "$2a$10$G5crbmvW44AKypkFIKtVeeaV7tNbPbaFT2ccBozQRzcGaQ5Yc2t.i",
                 Collections.emptySet());
        } else {
            throw new UsernameNotFoundException("User is not found.");
        }
    }

//    private final AppUserRepository rep;
//    @Override
//    public UserDetails loadUserByUsername(String username)
//        throws UsernameNotFoundException {
//        AppUser usr = rep.findByUsername(username);
//        if (usr != null) {
//            return new User(username, usr.getPassword(),
//                Collections.emptySet());
//        } else {
//            throw new UsernameNotFoundException("User does not exist.");
//        }
//    }
}