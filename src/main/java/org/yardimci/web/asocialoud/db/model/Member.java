package org.yardimci.web.asocialoud.db.model;


import javax.persistence.*;

@Entity
@Table(name = "tbl_members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String loginName;


    @Column(name = "name", nullable = false)
    private String memberName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Override
    public String toString() {
        return String.format("Member [id=%s, name=%s, loginName=%s]", id, memberName, loginName);
    }

}
