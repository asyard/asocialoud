package org.yardimci.asocialoud.feeds.db.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yardimci.asocialoud.feeds.db.model.Feed;

import java.util.Date;
import java.util.List;

public interface FeedRepository extends PagingAndSortingRepository<Feed, Long> {

    int FETCH_COUNT = 5;

    Long countByMemberId(Long memberId);

    //todo Pageable
    List<Feed> findAllByMemberIdInAndPublishDateAfterOrderByPublishDateDesc(Long[] memberIds, Date publishDateAfter);

    List<Feed> findAllByMemberIdOrderByPublishDateDesc(Long memberId, Pageable pageable);

    List<Feed> findAllByMemberIdInOrderByPublishDateDesc(Long[] memberIds, Pageable pageable);

}
