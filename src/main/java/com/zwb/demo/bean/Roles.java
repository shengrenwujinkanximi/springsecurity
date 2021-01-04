package com.zwb.demo.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles_t")
@Data
public class Roles extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_roles_t_id")
    @SequenceGenerator(name = "seq_roles_t_id", sequenceName = "seq_roles_t_id", allocationSize = 1)
    private Integer id;
    @Column(name = "roles_code")
    private String rolesCode;
    @Column(name = "roles_name")
    private String rolesName;
}
