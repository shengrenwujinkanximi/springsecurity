package com.zwb.demo.bean;

import com.zwb.demo.config.jpa.CustomerAuditingEntityListener;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "scheduler_task_t")
@DynamicInsert
@DynamicUpdate
/**
 *@EntityListeners(CustomerAuditingEntityListener.class)
 */
public class SchedulerTasks extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_scheduler_task_t_id")
    @SequenceGenerator(name = "seq_scheduler_task_t_id", sequenceName = "seq_scheduler_task_t_id", allocationSize = 1)
    private Integer id;
    @Column(name = "cron", length = 50)
    private String cron;
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "group_name", length = 100)
    private String groupName;
    @Column(name = "status", length = 10)
    private String status;
    @Column(name = "job_class", length = 100)
    private String jobClass;

    // 创建前事件
//    @Override
//    @PrePersist
//    public void PrePersist() {
//
//    }
//
//    // 创建后事件
//    @PostPersist
//    public void PostPersist() {
//
//    }
//
//    // 更新前事件
//    @Override
//    @PreUpdate
//    public void PreUpdate() {
//
//    }
//
//    // 更新后事件
//    @PostUpdate
//    public void PostUpdate() {
//
//    }
}
