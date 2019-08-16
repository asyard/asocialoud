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
        Member admin = memberRepository.findByLoginName("admin");
        if (admin == null) {
            admin = new Member();
            admin.setLoginName("admin");
            admin.setMemberType(MemberType.A);
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin");
            admin.setRealName("admin");
            memberRepository.save(admin);
        }
        logger.info("Data initialization completed");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
