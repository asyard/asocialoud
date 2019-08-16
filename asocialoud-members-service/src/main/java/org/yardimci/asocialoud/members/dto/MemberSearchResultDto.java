package org.yardimci.asocialoud.members.dto;

public class MemberSearchResultDto {

    private String memberLoginName;
    private String memberRealName;
    private String memberEmail;
    private boolean followedByMe;
    private boolean followsMe;
    private boolean blockedByMe;

    public String getMemberLoginName() {
        return memberLoginName;
    }

    public void setMemberLoginName(String memberLoginName) {
        this.memberLoginName = memberLoginName;
    }

    public String getMemberRealName() {
        return memberRealName;
    }

    public void setMemberRealName(String memberRealName) {
        this.memberRealName = memberRealName;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public boolean isFollowedByMe() {
        return followedByMe;
    }

    public void setFollowedByMe(boolean followedByMe) {
        this.followedByMe = followedByMe;
    }

    public boolean isFollowsMe() {
        return followsMe;
    }

    public void setFollowsMe(boolean followsMe) {
        this.followsMe = followsMe;
    }

    public boolean isBlockedByMe() {
        return blockedByMe;
    }

    public void setBlockedByMe(boolean blockedByMe) {
        this.blockedByMe = blockedByMe;
    }
}
