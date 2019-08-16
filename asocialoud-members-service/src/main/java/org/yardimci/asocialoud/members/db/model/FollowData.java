package org.yardimci.asocialoud.members.db.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_follow_data")
public class FollowData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "follow_date", nullable = false)
    private Date followDate;

    //@JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member owner;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "target_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member memberToFollow;

    @Column(name = "allow_relaud", nullable = false)
    private boolean allowRelauding;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFollowDate() {
        return followDate;
    }

    public void setFollowDate(Date followDate) {
        this.followDate = followDate;
    }

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    public Member getMemberToFollow() {
        return memberToFollow;
    }

    public void setMemberToFollow(Member memberToFollow) {
        this.memberToFollow = memberToFollow;
    }

    public boolean isAllowRelauding() {
        return allowRelauding;
    }

    public void setAllowRelauding(boolean allowRelauding) {
        this.allowRelauding = allowRelauding;
    }
}
