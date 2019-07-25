package org.yardimci.asocialoud.members.db.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.yardimci.asocialoud.members.db.model.FollowData;
import org.yardimci.asocialoud.members.db.model.Member;

import java.util.List;


public interface FollowDataRepository extends CrudRepository<FollowData, Long> {

    @Query("select fd from  FollowData fd where fd.owner = :ownerMember")
    List<FollowData> findAllByOwnerMember(@Param("ownerMember") Member ownerMember);
}
