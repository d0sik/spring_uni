package kbtu.kz.sis3.repository;

import kbtu.kz.sis3.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
