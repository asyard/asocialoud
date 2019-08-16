package org.yardimci.asocialoud.members.db.model;

public enum MemberType {

    B("Basic"), V("VIP"), A("Admin"), P("Passive");

    private String description;

    MemberType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
