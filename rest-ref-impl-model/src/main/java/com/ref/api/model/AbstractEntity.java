package com.ref.api.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

//@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {

//    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
    @NotNull(message = "Id can not be null.")
//    @Column(name = "ID")
    Long id;

//    @CreatedDate
    LocalDateTime createdDate;
//    @LastModifiedDate
    LocalDateTime modifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}