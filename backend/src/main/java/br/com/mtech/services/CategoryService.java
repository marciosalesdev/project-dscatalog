package br.com.mtech.services;


import br.com.mtech.dto.CategoryDTO;
import br.com.mtech.repositories.CategoryRepository;
import br.com.mtech.services.exceptions.DatabaseException;
import br.com.mtech.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import br.com.mtech.entities.Category;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
        Page<Category> list = repository.findAll(pageRequest);
       return list.map(x -> new CategoryDTO(x));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
       Optional<Category> obj = repository.findById(id);
       Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
       return new CategoryDTO(entity);
    }
    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
    Category entity = new Category();
    entity.setName(dto.getName());
    entity = repository.save(entity);
    return new CategoryDTO(entity);
    }
    @Transactional
    public CategoryDTO update(Long id,CategoryDTO dto) {
       try{
        Category entity = repository.getReferenceById(id);
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CategoryDTO(entity);
    }
       catch(EntityNotFoundException e){
        throw new ResourceNotFoundException("Id not found: " + id);
       }

}   @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
    if (!repository.existsById(id)) {
        throw new ResourceNotFoundException("Id not found: " + id);
    }
    try{
        repository.deleteById(id);
    }
    catch(DataIntegrityViolationException e){
        throw new DatabaseException("Violaçao de integridade");
    }
    }
}