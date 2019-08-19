package org.yardimci.asocialoud.feeds.db.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_feeds")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", nullable = false, length = 256)
    private String text;

    @Column(name = "media_uri", nullable = true, length = 256)
    private String mediaUri;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "publish_date", nullable = false)
    private Date publishDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMediaUri() {
        return mediaUri;
    }

    public void setMediaUri(String mediaUri) {
        this.mediaUri = mediaUri;
    }

    //@JsonIgnore
    public Long getMemberId() {
        return memberId;
    }

    //@JsonProperty
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
