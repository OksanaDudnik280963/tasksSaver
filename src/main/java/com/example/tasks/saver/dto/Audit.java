package com.example.tasks.saver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("serial")
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
/*Skeleton of records of the payment system*/
public class Audit implements Serializable {

/*
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",
            locale = JsonFormat.DEFAULT_LOCALE, timezone = DEFAULT_TIMEZONE)
*/
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss" )
    @Column(name = "CREATED", nullable = false)
    @JsonProperty
    private Date created;
/*
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",
            locale = JsonFormat.DEFAULT_LOCALE, timezone = DEFAULT_TIMEZONE)
*/
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss" )
    @Column(name = "UPDATED", nullable = false)
    @JsonProperty
    private Date updated;

    @PrePersist
    protected void setCreatedDate() {
        this.created = new Date();
        this.updated = new Date();
    }

    @PreUpdate
    protected void setUpdatedDate() {
        this.updated = new Date();
    }

    @Column(name = "CREATED_BY", nullable = false)
    @JsonProperty
    private String createdBy="MANAGER";

    @Column(name = "CHANGED_BY", nullable = false)
    @JsonProperty
    private String changedBy="MANAGER";

    public Audit(Date created, Date updated, String createdBy, String changedBy) {
        this.created = created;
        this.updated = updated;
        this.createdBy = createdBy;
        this.changedBy = changedBy;
    }

}