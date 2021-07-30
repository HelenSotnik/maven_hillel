package org.hillel.lesson12.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Client {
    private int id;
    private String name;
    private String email;
    private Long phone;
    private String about;
    private int age;
}
