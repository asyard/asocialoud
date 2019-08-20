package org.yardimci.asocialoud.feeds.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.yardimci.asocialoud.feeds.db.model.Feed;

import java.util.Date;
import java.util.List;

public interface FeedRepository extends CrudRepository<Feed, Long> {

    //todo Pageable
    List<Feed> findAllByMemberIdOrderByPublishDateDesc(Long memberId);

    List<Feed> findAllByMemberIdInAndPublishDateAfterOrderByPublishDateDesc(Long[] memberIds, Date publishDateAfter);

    List<Feed> findAllByMemberIdInOrderByPublishDateDesc(Long[] memberIds);

}
