package org.yardimci.asocialoud.feeds.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.yardimci.asocialoud.feeds.db.model.Feed;

import java.util.Date;
import java.util.List;

public interface FeedRepository extends CrudRepository<Feed, Long> {

    //todo Pageable
    List<Feed> findAllByMemberId(Long memberId);

    List<Feed> findAllByMemberIdInAndPublishDateAfter(Long[] memberIds, Date publishDateAfter);

    List<Feed> findAllByMemberIdIn(Long[] memberIds);

}
