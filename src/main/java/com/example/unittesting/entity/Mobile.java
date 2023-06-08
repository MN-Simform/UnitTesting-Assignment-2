package com.example.unittesting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mobile {
    @Id
    private int id;
    private String company;
    private String model;
    private int price;

    @Override
    public String toString() {
        return "Mobile{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                '}';
    }
}
