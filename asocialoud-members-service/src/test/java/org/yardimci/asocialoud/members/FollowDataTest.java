package org.yardimci.asocialoud.members;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.yardimci.asocialoud.members.db.model.FollowData;
import org.yardimci.asocialoud.members.db.model.Member;
import org.yardimci.asocialoud.members.db.repository.FollowDataRepository;
import org.yardimci.asocialoud.members.db.repository.MemberRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { AsocialoudMemberService.class })
public class FollowDataTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FollowDataRepository followDataRepository;


    @Test
    @Transactional
    public void when_member_has_followers_thenOK() {
        Member member1 = new Member();
        member1.setLoginName("m1");
        member1.setRealName("r1");
        member1.setEmail("m1");
        member1.setPassword("123");


        Member member2 = new Member();
        member2.setLoginName("m2");
        member2.setRealName("r2");
        member2.setEmail("m2");
        member2.setPassword("123");

        Member member3 = new Member();
        member3.setLoginName("m3");
        member3.setRealName("r3");
        member3.setEmail("m3");
        member3.setPassword("123");

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        if (member1.getFollowDataList() == null) {
            member1.setFollowDataList(new ArrayList<>());
        }


        FollowData followData = new FollowData();
        followData.setFollowDate(new Date());
        followData.setOwner(member1);
        followData.setMemberToFollow(member2);

        FollowData followData2 = new FollowData();
        followData2.setFollowDate(new Date());
        followData2.setOwner(member1);
        followData2.setMemberToFollow(member3);

        member1.getFollowDataList().add(followData);
        member1.getFollowDataList().add(followData2);
        memberRepository.save(member1);


        List<FollowData> member1FollowsList = followDataRepository.findAllByOwnerMember(member1);
        System.out.println(member1FollowsList.size());
        Assert.assertTrue(member1FollowsList.get(0).getMemberToFollow().equals(member2));
    }
}
