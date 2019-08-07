package org.yardimci.asocialoud.members.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @ManyToOne
    private Member owner;

    @ManyToOne
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
