package rizki.practicum.learning.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.Role;
import rizki.practicum.learning.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Service
public class MyUserDetails implements UserDetails {

    private String username;
    private String password;
    private String id;
    private String identity;
    private String fullname;
    private String name;
    Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

    public MyUserDetails(){}

    public MyUserDetails(User byEmail) {
        this.username = byEmail.getEmail();
        this.password = byEmail.getPassword();
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for(Role role : byEmail.getRole()){
            grantedAuthorityList.add(new SimpleGrantedAuthority(role.getInitial().toUpperCase()));
        }
        this.authorities = grantedAuthorityList;
        this.name = byEmail.getName();
        this.id = byEmail.getId();
        this.identity = byEmail.getIdentity();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
