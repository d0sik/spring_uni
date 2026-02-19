package kbtu.kz.sis3.controller;

import kbtu.kz.sis3.model.Product;
import kbtu.kz.sis3.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository itemRepository) {
        this.productRepository = itemRepository;
    }

    @PostMapping
    public Product createItem(@RequestBody Product item) {
        return productRepository.save(item);
    }

    @GetMapping
    public List<Product> getAllItems() {
        return productRepository.findAll();
    }
}
