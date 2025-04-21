package oort.cloud.openmarket.products.repository;

import oort.cloud.openmarket.products.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {

}
