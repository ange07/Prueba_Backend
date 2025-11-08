-- =====================================================
-- SCRIPT SQL ACTUALIZADO PARA TABLA FACTURAS
-- =====================================================
-- Este script incluye los cambios necesarios para el
-- sistema de facturación manual con estados.
-- =====================================================

CREATE TABLE Facturas (
    id_factura BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_pedido BIGINT UNIQUE KEY,
    rfc VARCHAR(15) NOT NULL,
    razon_social VARCHAR(150) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    iva DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,

    -- NUEVO: Estado de la factura con 3 posibles valores
    estado_factura ENUM('PENDIENTE', 'GENERADA', 'ENVIADA') DEFAULT 'PENDIENTE' NOT NULL,

    fecha_emision DATETIME DEFAULT CURRENT_TIMESTAMP,

    -- NUEVO: Fecha de última actualización (para tracking de cambios de estado)
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido)
);

-- =====================================================
-- NOTAS IMPORTANTES:
-- =====================================================
-- 1. estado_factura: Controla el flujo de facturación
--    - PENDIENTE: Cliente solicitó factura, esperando generación por admin
--    - GENERADA: Admin generó la factura en el SAT
--    - ENVIADA: Admin envió el PDF/XML al cliente por email
--
-- 2. fecha_actualizacion: Se actualiza automáticamente con cada cambio
--    Útil para auditoría y tracking de cuándo cambió el estado
--
-- 3. DECIMAL(10,2): Permite montos hasta 99,999,999.99
--    Suficiente para la mayoría de casos de uso
--
-- 4. Relación 1:1 con Pedidos mediante UNIQUE KEY en id_pedido
--    Un pedido solo puede tener UNA factura
-- =====================================================

-- ÍNDICES RECOMENDADOS para mejorar rendimiento de consultas
CREATE INDEX idx_facturas_estado ON Facturas(estado_factura);
CREATE INDEX idx_facturas_rfc ON Facturas(rfc);
CREATE INDEX idx_facturas_fecha_emision ON Facturas(fecha_emision);

-- =====================================================
-- EJEMPLOS DE USO:
-- =====================================================

-- Ver todas las facturas PENDIENTES (para panel de admin)
-- SELECT * FROM Facturas WHERE estado_factura = 'PENDIENTE';

-- Ver facturas de un pedido específico
-- SELECT * FROM Facturas WHERE id_pedido = 123;

-- Actualizar estado a GENERADA (cuando admin genera en SAT)
-- UPDATE Facturas SET estado_factura = 'GENERADA' WHERE id_factura = 1;

-- Actualizar estado a ENVIADA (cuando admin envía PDF/XML)
-- UPDATE Facturas SET estado_factura = 'ENVIADA' WHERE id_factura = 1;
