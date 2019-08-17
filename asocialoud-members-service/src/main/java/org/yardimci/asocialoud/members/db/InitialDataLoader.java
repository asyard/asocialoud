package org.yardimci.asocialoud.members.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.yardimci.asocialoud.members.db.model.Member;
import org.yardimci.asocialoud.members.db.model.MemberType;
import org.yardimci.asocialoud.members.db.repository.MemberRepository;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(InitialDataLoader.class);

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Initializing data");
        Member admin = memberRepository.findByLoginName("testmem");
        if (admin == null) {
            admin = new Member();
            admin.setLoginName("testmem");
            admin.setMemberType(MemberType.T);
            admin.setPassword(passwordEncoder.encode("111222333"));
            admin.setEmail("testmem@mail.net");
            admin.setRealName("Test Member");
            memberRepository.save(admin);
        }
        logger.info("Data initialization completed");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
