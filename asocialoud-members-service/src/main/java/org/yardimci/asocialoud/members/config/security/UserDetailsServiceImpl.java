package org.yardimci.asocialoud.members.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.yardimci.asocialoud.members.db.model.Member;
import org.yardimci.asocialoud.members.db.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService  {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberToLogin = memberRepository.findByLoginName(username);

        if (memberToLogin == null) {
            throw new UsernameNotFoundException("Username: " + username + " not found");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(); //AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + memberToLogin.getRole());

        return new User(memberToLogin.getLoginName(), memberToLogin.getPassword(), true, true, true, true,
                grantedAuthorities);

    }
}