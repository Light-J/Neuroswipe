package com.nsa.cubric.application.domain;

public class Relationship {
    Integer relationshipId;
    String relationship;

    public Relationship(Integer relationshipId, String relationship) {
        this.relationshipId = relationshipId;
        this.relationship = relationship;
    }

    public Integer getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Integer relationshipId) {
        this.relationshipId = relationshipId;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}
