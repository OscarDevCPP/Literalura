package com.socamaru.literalura;

import jakarta.persistence.*;

@Entity
@Table(name = "example")
public class EntityExample {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
}
