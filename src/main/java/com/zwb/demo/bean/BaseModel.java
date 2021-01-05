package com.zwb.demo.bean;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseModel {
    /**
     * 表示该字段为创建人，在这个实体被insert的时候，会自动为其赋值
     */
    @CreatedBy
    @Column(name = "create_by")
    private String createBy;

    /**
     * 表示该字段为创建时间字段，在这个实体被insert的时候，会自动为其赋值
     */
    @CreatedDate
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 表示该字段为修改人，在这个实体被update的时候，会自动为其赋值
     */
    @LastModifiedBy
    @Column(name = "last_update_by")
    private String lastUpdateBy;

    /**
     * 表示该字段为修改时间字段，在这个实体被update的时候，会自动为其赋值
     */
    @LastModifiedDate
    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;
    @Column(name = "domain_name")
    private String domainName;

    @Version
    private Integer version;
    @PrePersist
    public void PrePersist(){
        this.setCreateTime(LocalDateTime.now());
    }
    @PreUpdate
    public void PreUpdate() {
        this.setLastUpdateTime(LocalDateTime.now());
    }
}
