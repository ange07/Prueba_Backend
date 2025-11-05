package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.model.Productos;

import java.util.List;

public interface IProductoService {

    public List<Productos> getAllProductsActive();
    Productos getProductsById(Long id);
    Productos getProductsByCategory(Productos categoria);
    Productos updateProductsById(Long id, Productos updateProduct);
    Productos deleteProductsById(Long id);
}
