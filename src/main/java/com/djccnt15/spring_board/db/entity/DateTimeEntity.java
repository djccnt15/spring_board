package com.djccnt15.spring_board.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@MappedSuperclass  // annotation for super type modeling
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DateTimeEntity extends BaseEntity {
    
    @Column(insertable = false, updatable = false)
    @ColumnDefault(value = "now()")
    @CreationTimestamp
    private LocalDateTime createDateTime;
    
    @Column(insertable = false, updatable = false)
    @ColumnDefault(value = "now()")
    @UpdateTimestamp
    private LocalDateTime updateDateTime;
}
