package br.com.mtech.dto;

import br.com.mtech.entities.Category;

import java.io.Serializable;

public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

    public CategoryDTO() {
    }
    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDTO that)) return false;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();

    }

}
