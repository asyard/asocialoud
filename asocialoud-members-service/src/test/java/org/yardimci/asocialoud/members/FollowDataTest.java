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
    public void when_member_follows_thenOK() {
        Member member1 = createMember("testm1", "testr1", "testm1", "123");
        Member member2 = createMember("testm2", "testr2", "testm2", "123");
        Member member3 = createMember("testm3", "testr3", "testm3", "123");

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        FollowData followData = createFollowData(member1, member2);
        FollowData followData2 = createFollowData(member1, member3);

        member1.getFollowDataList().add(followData);
        member1.getFollowDataList().add(followData2);
        memberRepository.save(member1);

        List<FollowData> member1FollowsList = followDataRepository.findAllByOwnerMember(member1);
        System.out.println(member1FollowsList.size());
        Assert.assertTrue(member1FollowsList.stream().anyMatch(fd -> fd.getOwner().equals(member1)));
    }




    @Test
    @Transactional
    public void when_member_has_followers_thenOK() {
        Member member1 = createMember("testm1", "testr1", "testm1", "123");
        Member member2 = createMember("testm2", "testr2", "testm2", "123");
        Member member3 = createMember("testm3", "testr3", "testm3", "123");
        Member member4 = createMember("testm4", "testr4", "testm4", "123");

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);

        FollowData followData = createFollowData(member1, member3);
        FollowData followData2 = createFollowData(member2, member3);

        member1.getFollowDataList().add(followData);
        member2.getFollowDataList().add(followData2);
        memberRepository.save(member1);
        memberRepository.save(member2);


        List<FollowData> member3FollowersList = followDataRepository.findAllFollowersOfMember(member3);
        System.out.println(member3FollowersList.size());
        Assert.assertTrue(member3FollowersList.stream().anyMatch(fd -> fd.getMemberToFollow().equals(member3)));
    }

    private Member createMember(String loginName, String realName, String email, String password) {
        Member member = new Member();
        member.setLoginName(loginName);
        member.setRealName(realName);
        member.setEmail(email);
        member.setPassword(password);
        member.setFollowDataList(new ArrayList<>());

        return member;
    }

    private FollowData createFollowData(Member owner, Member toFollow) {
        FollowData followData = new FollowData();
        followData.setFollowDate(new Date());
        followData.setOwner(owner);
        followData.setMemberToFollow(toFollow);

        return followData;
    }


}
