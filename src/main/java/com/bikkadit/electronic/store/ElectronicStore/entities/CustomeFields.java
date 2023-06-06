package com.bikkadit.electronic.store.ElectronicStore.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
//@Embeddable
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CustomeFields {

    @Column(name = "is_active_switch")
    private String isactive;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "create_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String lastModifiedBy;

}
