package org.yardimci.asocialoud.members.controller.member;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.yardimci.asocialoud.members.controller.MemberResponse;
import org.yardimci.asocialoud.members.controller.exception.MemberNotFoundException;
import org.yardimci.asocialoud.members.db.model.Member;
import org.yardimci.asocialoud.members.db.model.MemberType;
import org.yardimci.asocialoud.members.db.repository.FollowDataRepository;
import org.yardimci.asocialoud.members.db.repository.MemberRepository;
import org.yardimci.asocialoud.members.db.service.MemberService;
import org.yardimci.asocialoud.members.dto.MemberSearchResultDto;
import org.yardimci.asocialoud.members.dto.RequestMemberDto;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
//@CrossOrigin(origins = "http://localhost:6600")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FollowDataRepository followDataRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberService memberService;

    @GetMapping
    public MemberResponse findAll() {
        logger.info("Retrieving all members");
        MemberResponse memberResponse = new MemberResponse();

        List<Member> memberList = memberRepository.findAllByMemberType(MemberType.B);
        //Iterator<Member> memberIterator = memberRepository.findAll().iterator();
        //memberIterator.forEachRemaining(memberList::add);

        memberResponse.setData(memberList);
        memberResponse.setStatus(HttpStatus.OK.toString());
        return memberResponse;
    }

    @GetMapping("/{userName}")
    public MemberResponse findByUserName(@PathVariable("userName") String userNameToQuery, Principal principal) {
        logger.info("Retrieving member by login name : "+ userNameToQuery);
        Member byLoginName = memberRepository.findByLoginName(userNameToQuery);
        MemberSearchResultDto searchResultDto = new MemberSearchResultDto();
        if (byLoginName != null) {
            ModelMapper modelMapper = new ModelMapper();
            searchResultDto = modelMapper.map(byLoginName, MemberSearchResultDto.class);
            if (!userNameToQuery.equals(principal.getName())) {
                searchResultDto.setMemberEmail("");
                Member ownerMember = memberRepository.findByLoginName(principal.getName());
                searchResultDto.setFollowedByMe(followDataRepository.existsFollowDataByOwnerAndMemberToFollow(ownerMember, byLoginName));
                searchResultDto.setFollowsMe(followDataRepository.existsFollowDataByOwnerAndMemberToFollow(byLoginName, ownerMember));
            }
        }
        MemberResponse memberResponse = new MemberResponse();
        memberResponse.setData(searchResultDto);
        memberResponse.setStatus(byLoginName == null ? HttpStatus.NOT_FOUND.toString() : HttpStatus.OK.toString());
        return memberResponse;
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


    @GetMapping("/search/{userName}")
    public MemberResponse findAllByUserName(@PathVariable("userName") String userNameToQuery, Principal principal) {
        logger.info("Retrieving members by login name : "+ userNameToQuery);
        MemberResponse memberResponse = new MemberResponse();

        List<Member> byLoginName = memberRepository.findByLoginNameIgnoreCaseContainingAndMemberType(userNameToQuery, MemberType.B);
        List<MemberSearchResultDto> searchResultList = new ArrayList<>();

        if (byLoginName != null) {
            Member ownerMember = memberRepository.findByLoginName(principal.getName());
            ModelMapper modelMapper = new ModelMapper();
            for (Member resultMember : byLoginName) {
                MemberSearchResultDto dto = modelMapper.map(resultMember, MemberSearchResultDto.class);
                dto.setFollowedByMe(followDataRepository.existsFollowDataByOwnerAndMemberToFollow(ownerMember, resultMember));
                dto.setFollowsMe(followDataRepository.existsFollowDataByOwnerAndMemberToFollow(resultMember, ownerMember));
                searchResultList.add(dto);
            }
        }

        memberResponse.setData(searchResultList);
        memberResponse.setStatus(byLoginName == null ? HttpStatus.NOT_FOUND.toString() : HttpStatus.OK.toString());
        return memberResponse;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse create(@RequestBody RequestMemberDto member) {
        logger.info("Save member request received");
        MemberResponse memberResponse = new MemberResponse();

        if (member.getRealName() == null || member.getRealName().isEmpty() || member.getLoginName() == null || member.getLoginName().isEmpty() || member.getPassword()==null || member.getPassword().isEmpty()) {
            logger.warn("Missing member info");
            memberResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
            memberResponse.setData("error.missinginformation");
            return memberResponse;
        }


        Member userInDb = memberRepository.findByLoginName(member.getLoginName());
        if (userInDb != null) {
            logger.warn("Member name already exists");
            memberResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
            memberResponse.setData("error.membernameexists");
            return memberResponse;        }

        userInDb = memberRepository.findByEmail(member.getEmail());
        if (userInDb != null) {
            logger.warn("Member email already exists");
            memberResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
            memberResponse.setData("error.emailistaken");
            return memberResponse;
        }

        userInDb = new Member();
        userInDb.setLoginName(member.getLoginName());
        userInDb.setRealName(member.getRealName());
        userInDb.setEmail(member.getEmail());

        try {
            logger.info("Saving member : " + userInDb.getLoginName());
            userInDb.setPassword(passwordEncoder.encode(member.getPassword()));
            //member.setMemberType(MemberType.B);
            memberRepository.save(userInDb);
            memberResponse.setStatus(HttpStatus.CREATED.toString());
            memberResponse.setData(userInDb);
        } catch (Exception e) {
            logger.error("Unable to save member", e);
            memberResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
            memberResponse.setData("error.servererror");
        }

        return memberResponse;
    }

    /*@DeleteMapping("/{id}")
    public MemberResponse delete(@PathVariable Long id) {
        logger.info("Deleting user with id : " + id);
        MemberResponse memberResponse = new MemberResponse();
        try {

            logger.info("Removing user");
            memberRepository.deleteById(id);
            memberResponse.setStatus(HttpStatus.OK.toString());
        } catch (Exception e) {
            logger.error("Unable to delete member : " + id, e);
            if (e instanceof EmptyResultDataAccessException) {
                memberResponse.setStatus(HttpStatus.NOT_ACCEPTABLE.toString());
            } else {
                memberResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            }
        }
        return memberResponse;
    }*/


    // todo require admin privilege if requester is not same as target user
    @DeleteMapping("/{userName}")
    public MemberResponse delete(@PathVariable String userName) {
        logger.info("Deleting user : " + userName);
        MemberResponse memberResponse = new MemberResponse();
        try {
            // no need below since we added OnDelete on follow data - member attributes
            //logger.info("Removing follow data");
            //followDataRepository.deleteAllByOwner(memberToDelete);
            /*List<FollowData> allFollowerDataOfMember = followDataRepository.findAllFollowersOfMember(memberToDelete);
            for (FollowData fd : allFollowerDataOfMember) {
                followDataRepository.delete(fd);
            }*/
            memberService.deleteMemberByLoginName(userName);
            memberResponse.setStatus(HttpStatus.OK.toString());
        } catch (Exception e) {
            logger.error("Unable to delete member : " + userName, e);
            if (e instanceof EmptyResultDataAccessException) {
                memberResponse.setStatus(HttpStatus.NOT_ACCEPTABLE.toString());
            } else {
                memberResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            }
        }
        return memberResponse;
    }

    @PatchMapping("/{userName}")
    public MemberResponse updateMember(@RequestBody RequestMemberDto member, @PathVariable String userName) {
        logger.info("Updating user : " + userName);
        MemberResponse memberResponse = new MemberResponse();

        if (userName == null /*|| !member.getLoginName().equals(userName)*/ ||
                member == null || member.getRealName() == null || member.getRealName().isEmpty() || member.getEmail() == null || member.getEmail().isEmpty()) {
            //throw new MemberIdMismatchException("mismatch : " + userName);
            memberResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
            memberResponse.setData("error.missinginformation");
            return memberResponse;
        }
        try {
            Member userInDb = memberRepository.findByLoginName(userName);
            userInDb.setRealName(member.getRealName());
            userInDb.setEmail(member.getEmail());

            memberRepository.save(userInDb);
            memberResponse.setData(userInDb);
            memberResponse.setStatus(HttpStatus.OK.toString());
        } catch (Exception e) {
            logger.error("Unable to update user : " + userName, e);
            memberResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }


        return memberResponse;
    }

}
