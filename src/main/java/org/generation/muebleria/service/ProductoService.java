package org.generation.muebleria.service;

import lombok.AllArgsConstructor;
import org.generation.muebleria.model.Productos;
import org.generation.muebleria.repository.ProductoRepository;
import org.generation.muebleria.service.interfaces.IProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductoService implements IProductoService {

    //inyeccion de dependencia
    public ProductoRepository productoRepository;

    //Implementacion de los metodos de la interface(IProductoService)
    @Override
    public List<Productos> getAllProductsActive() {
        return productoRepository.findByActivoTrue();
    }

    @Override
    public Productos getProductsById(Long id) {
        return null;
    }

    @Override
    public Productos getProductsByCategory(Productos categoria) {
        return null;
    }

    @Override
    public Productos updateProductsById(Long id, Productos updateProduct) {
        return null;
    }

    @Override
    public Productos deleteProductsById(Long id) {
        return null;
    }
}
