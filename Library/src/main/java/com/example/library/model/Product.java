package com.example.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products",
       uniqueConstraints = @UniqueConstraint(columnNames = "name"))

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long id;
    private String name;
    private String description;
    @Column(columnDefinition = "text")
    private double price;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "product")
//    @OneToMany(fetch = FetchType.EAGER,mappedBy = "product",cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH)
    public List<Image> image;

    private boolean is_activated;
    private boolean is_deleted;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;




}
