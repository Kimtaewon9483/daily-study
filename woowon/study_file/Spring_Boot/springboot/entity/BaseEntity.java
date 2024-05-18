package com.example.springbootstudy.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
       
    // @CreationTimestamp생성시간
    // @Column(updatable = false) update할때 관여 X
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime boardCreatedTime;
    
    // @UpdateTimestamp 업데이트 시간
    // @Column(insertable = false) insert할때 관여 X
    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime boardUpdatedTime;
}
