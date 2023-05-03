package com.zoe.services;

import com.zoe.exceptions.DataBaseException;
import com.zoe.exceptions.ResourceNotFoundException;
import com.zoe.models.Product;
import com.zoe.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado para o ID: " + id));
    }
    @Transactional
    public Product insert(Product product) {
        return productRepository.save(product);
    }
    @Transactional
    public Product update(Long id, Product product) {
        try {
            Product entity = productRepository.getReferenceById(id);
            updateData(entity, product);
            return productRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Produto não encontrado para o ID: " + id);
        }
    }
    public void updateData(Product entity, Product product) {
        entity.setSource(product.getSource());
        entity.setDestination(product.getDestination());
        entity.setPrice(product.getPrice());
    }
    @Transactional
    public void delete (Long id){
        try{
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Produto não encontrado para o ID: " + id);
        } catch (DataIntegrityViolationException e){
            throw new DataBaseException("Não é possível excluir o produto com o ID: "
                    + id
                    + ". Ele está sendo referenciado por outros registros no banco de dados.");
        }
    }
}