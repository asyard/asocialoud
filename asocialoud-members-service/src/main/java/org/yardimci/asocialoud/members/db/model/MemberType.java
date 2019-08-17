package org.yardimci.asocialoud.members.db.model;

public enum MemberType {

    B("Basic"), V("VIP"), T("Test"), P("Passive");

    private String description;

    MemberType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
