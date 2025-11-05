package org.generation.muebleria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="productos")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Productos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_producto")
    private Long idProducto;
    @Column(name="producto", nullable = false, length = 200)
    private String producto;
    @Column(name="sku", nullable = false, length = 200)
    private String sku;
    @Column(name="descripcion", length = 500)
    private String descripcion;
    @Column(name="precio_actual", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioActual;
    @Column(name="alto", nullable = false, precision = 10, scale = 2)
    private BigDecimal alto;
    @Column(name="ancho", nullable = false, precision = 10, scale = 2)
    private BigDecimal ancho;
    @Column(name="profundidad", nullable = false, precision = 10, scale = 2)
    private BigDecimal profundidad;
    @Column(name="peso", nullable = false, precision = 10, scale = 2)
    private BigDecimal peso;
    @Column(name="activo", columnDefinition = "TINYINT(1)")
    private Boolean activo = true;
    @Column(name="stock_disponible")
    private Integer stockDisponible;
    @Column(name="fecha_pedido", updatable = false)
    private LocalDateTime fechaPedido = LocalDateTime.now();
    @Column(name="fecha_actualizacion")
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

}
