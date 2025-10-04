package com.djccnt15.spring_board.db.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@MappedSuperclass  // annotation for abstract table class
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class DateTimeEntity extends BaseEntity {
    
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createDateTime;
    
    @Column
    @UpdateTimestamp
    private LocalDateTime updateDateTime;
}
