package org.yardimci.asocialoud.members.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.yardimci.asocialoud.members.db.model.Member;

import java.util.List;

public interface MemberRepository extends CrudRepository<Member, Long> {

    Member findByLoginName(String loginName);

}
