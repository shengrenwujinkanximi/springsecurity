package com.zwb.demo.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "user_roles_t")
@Data
public class UserRoles extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_roles_t_id")
    @SequenceGenerator(name = "seq_user_roles_t_id", sequenceName = "seq_user_roles_t_id", allocationSize = 1)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roles_id")
    private Roles roles;
}
