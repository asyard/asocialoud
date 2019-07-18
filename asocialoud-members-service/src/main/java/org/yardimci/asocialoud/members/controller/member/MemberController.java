package org.yardimci.asocialoud.members.controller.member;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.yardimci.asocialoud.members.controller.exception.MemberIdMismatchException;
import org.yardimci.asocialoud.members.controller.exception.MemberNotFoundException;
import org.yardimci.asocialoud.members.db.model.Member;
import org.yardimci.asocialoud.members.db.repository.MemberRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/members")
//@CrossOrigin(origins = "http://localhost:6600")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping
    public Iterable findAll() {
        logger.info("Retrieving all members");
        return memberRepository.findAll();
    }

    @GetMapping("/{memberName}")
    public Member findByUserName(@PathVariable String memberName) {
        logger.info("Retrieving member by login name : "+ memberName);
        return memberRepository.findByLoginName(memberName);
    }

    @GetMapping("/id/{id}")
    public Member findMemberById(@PathVariable Long id) {
        logger.info("Retrieving member by id : " + id);
        Optional<Member> member = memberRepository.findById(id);

        if (!member.isPresent())
            throw new MemberNotFoundException("id-" + id);
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        //return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        return member.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Member create(@RequestBody Member book) {
        logger.info("Saving member");
        return memberRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        memberRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Member updateMember(@RequestBody Member member, @PathVariable Long id) {
        if (member.getId().longValue() != id.longValue()) {
            throw new MemberIdMismatchException("mismatch : " + id);
        }
        memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        return memberRepository.save(member);
    }

}
