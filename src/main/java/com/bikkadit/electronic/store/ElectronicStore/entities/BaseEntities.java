package com.bikkadit.electronic.store.ElectronicStore.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
//@Embeddable
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntities {

    @Column(name = "is_active")
    private String isactive;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    @Column(name = "updated_date")
    private String updatedOn;
}
