package com.ec.rommel.nttdata.challenge.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "persons")
@ToString(of = "personId")
@EqualsAndHashCode(of = "personId")
@Builder
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Person {
    @Id
    @GeneratedValue
    private UUID personId;

    private String name;

    private String gender;

    private Integer age;

    @NotNull
    @Column(unique = true)
    private String identification;

    private  String address;

    private String phoneNumber;



}
