package org.example.ordersystem.Product;

import org.example.ordersystem.Errors.DuplicateResourceException;
import org.example.ordersystem.Errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
private final ProductRepository productRepository;
@Autowired
public ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
}


@GetMapping()
public Page<Product> getAllProducts(Pageable pageable) {
    return this.productRepository.findAll(pageable);
}

@GetMapping("/{id}")
public ResponseEntity<ProductDetailDTO> getProductDetails(@PathVariable Long id) {
    Product product = this.productRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Product not Found"));
    return ResponseEntity.ok(ProductMapper.toProductDetailDTO(product));
}


    @PostMapping()
    public ResponseEntity<ProductDetailDTO> addProduct(@RequestBody ProductDetailDTO productDetailDTO) {
    if (this.productRepository.existsProductByName(productDetailDTO.name()) && this.productRepository.existsProductByProvider(productDetailDTO.provider())){
        throw new DuplicateResourceException("Product already exists");
        }
Product newProduct = new Product();
    newProduct.setName(productDetailDTO.name());
    newProduct.setProvider(productDetailDTO.provider());
    newProduct.setPrice(productDetailDTO.price());
    var saved = this.productRepository.save(newProduct);
    return ResponseEntity.ok(ProductMapper.toProductDetailDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDetailDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDetailDTO productDetailDTO) {
    Product productToModify = this.productRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Product not Found"));
    if (this.productRepository.existsProductByName(productDetailDTO.name()) && this.productRepository.existsProductByProvider(productDetailDTO.provider())) {
        throw new DuplicateResourceException("Product already exists");
    }
    productToModify.setName(productDetailDTO.name());
    productToModify.setProvider(productDetailDTO.provider());
    productToModify.setPrice(productDetailDTO.price());
    var saved = this.productRepository.save(productToModify);
    return ResponseEntity.ok(ProductMapper.toProductDetailDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        this.productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
