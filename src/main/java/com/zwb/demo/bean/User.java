package com.zwb.demo.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_t")
public class User extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_t_id")
    @SequenceGenerator(name = "seq_user_t_id", sequenceName = "seq_user_t_id", allocationSize = 1)
    private Integer id;
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String password;
}
