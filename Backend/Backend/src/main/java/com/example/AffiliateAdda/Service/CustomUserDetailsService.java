package com.example.AffiliateAdda.Service;

import com.example.AffiliateAdda.Model.User;
import com.example.AffiliateAdda.Model.UserDetails;
import com.example.AffiliateAdda.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user from the database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Assign roles/authorities here based on the user role.
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getRole().equals("ADMIN")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        // Return custom UserDetails object
        return new CustomUserDetails(user.getUsername(), user.getPassword(), authorities);
    }