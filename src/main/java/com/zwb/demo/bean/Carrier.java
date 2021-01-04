package com.zwb.demo.bean;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "carrier_t")
public class Carrier extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_carrier_t_id")
    @SequenceGenerator(name="seq_carrier_id",sequenceName = "seq_carrier_t_id",allocationSize = 1)
    private Integer id;
    @Column(name = "name")
    private String name;
}
