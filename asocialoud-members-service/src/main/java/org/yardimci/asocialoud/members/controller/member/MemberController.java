package org.yardimci.asocialoud.members.controller.member;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.yardimci.asocialoud.members.controller.exception.MemberAlreadyExistsException;
import org.yardimci.asocialoud.members.controller.exception.MemberIdMismatchException;
import org.yardimci.asocialoud.members.controller.exception.MemberInfoMissingException;
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

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Member login(@RequestBody Member member) {
        logger.info("Retrieving member");
        Member byLoginName = memberRepository.findByLoginName(member.getLoginName());
        if (byLoginName == null) {
            logger.debug("Member not found by given login name");
            throw new MemberNotFoundException();
        }

        if (!byLoginName.getRealName().equals(member.getRealName())) {
            logger.debug("Member info is invalid");
            throw new MemberIdMismatchException("Member info is invalid");
        }
        return byLoginName;
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Member create(@RequestBody Member member) {
        logger.info("Save member request received");
        if (member.getRealName() == null || member.getRealName().isEmpty() || member.getLoginName() == null || member.getLoginName().isEmpty()) {
            logger.warn("Missing member info");
            throw new MemberInfoMissingException();
        }


        Member byLoginName = memberRepository.findByLoginName(member.getLoginName());
        if (byLoginName != null) {
            logger.warn("Member alreaady exists");
            throw new MemberAlreadyExistsException();
        }

        return memberRepository.save(member);
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
