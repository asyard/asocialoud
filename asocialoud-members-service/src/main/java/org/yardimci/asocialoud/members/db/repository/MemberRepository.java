package org.yardimci.asocialoud.members.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.yardimci.asocialoud.members.db.model.Member;
import org.yardimci.asocialoud.members.db.model.MemberType;

import java.util.List;

// @Repository : no need to add this annotation
public interface MemberRepository extends CrudRepository<Member, Long> {

    void deleteByLoginName(String loginName);

    Member findByLoginName(String loginName);

    Member findByEmail(String email);

    List<Member> findMembersByLoginNameContains(String loginName); // List<Member> findMembersByLoginNameContaining(String loginName);

    List<Member> findByLoginNameIgnoreCaseContaining(String loginName);

    List<Member> findByLoginNameIgnoreCaseContainingAndMemberType(String loginName, MemberType memberType);

    List<Member> findAllByMemberType(MemberType memberType);


}
