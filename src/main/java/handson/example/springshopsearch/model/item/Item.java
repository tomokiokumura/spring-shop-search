package com.mosmos21.shop_search.model.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "description", columnDefinition = "TEXT")
    public String description;

    @Min(value = 0)
    @Column(name = "price")
    public int price;

    @Column(name = "authorId")
    public Long authorId;
}
