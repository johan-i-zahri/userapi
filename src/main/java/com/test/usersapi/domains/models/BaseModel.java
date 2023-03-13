package com.test.usersapi.domains.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@SuppressWarnings("serial")
@MappedSuperclass
@Data
public class BaseModel implements Serializable{

	@Id
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length = 36)
    public String id;

    @Column(name = "created_at", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @JsonIgnore
    @JsonProperty("created_at")
    public LocalDateTime createdAt;

    @Version
    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @JsonIgnore
    @JsonProperty("updated_at")
    public LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    @JsonIgnore
    public Boolean isDeleted;

    @Column(name = "created_by", length = 36)
    @JsonIgnore
    public String createdBy;

    @Column(name = "updated_by", length = 36)
    @JsonIgnore
    public String updatedBy;

    @PrePersist
    public void prePersist() {

        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.isDeleted = false;
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

}
