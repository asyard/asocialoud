package org.yardimci.asocialoud.members.db.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yardimci.asocialoud.members.db.repository.MemberRepository;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // default repository delete method does not require transactional annotation. Here we need.
    @Transactional
    public void deleteMemberByLoginName(String memberLoginName) {
        memberRepository.deleteByLoginName(memberLoginName);
    }


}
