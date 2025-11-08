# GUÍA COMPLETA DE POSTMAN - SISTEMA DE FACTURACIÓN SIMPLE

## CONFIGURACIÓN INICIAL

### URL Base
```
http://localhost:8080
```

### Endpoints de Facturación
```
Base: http://localhost:8080/api/facturas
```

---

## PASOS PARA INICIAR

### 1. Iniciar la Aplicación Spring Boot

```bash
# Desde IntelliJ IDEA:
# Run -> Run 'MuebleriaApplication'

# O desde terminal con Gradle:
./gradlew bootRun
```

### 2. Cargar Datos de Prueba

**IMPORTANTE**: Debido a que `spring.jpa.hibernate.ddl-auto=create-drop`, las tablas se crean vacías al iniciar. Tienes 2 opciones:

**Opción A - Mantener create-drop (recomendado para desarrollo):**
1. Inicia la aplicación (esto crea las tablas vacías)
2. Abre MySQL Workbench o consola MySQL
3. Ejecuta el script: `datos_prueba.sql`

```bash
mysql -u root -p
# Ingresa password: root123
source /ruta/completa/a/Prueba_Backend/datos_prueba.sql
```

**Opción B - Cambiar a update (los datos persisten):**
1. Edita `src/main/resources/application.properties`
2. Cambia: `spring.jpa.hibernate.ddl-auto=update`
3. Reinicia la aplicación
4. Ejecuta el script SQL solo una vez

---

## ENDPOINTS DISPONIBLES (4 TOTALES)

### 📋 1. SOLICITAR FACTURA PARA UN PEDIDO
```
POST http://localhost:8080/api/facturas/solicitar
```

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "idPedido": 3,
  "rfc": "PEDR850615HDF",
  "razonSocial": "Pedro Ramírez Torres"
}
```

**Respuesta Exitosa (201 Created):**
```json
{
  "idFactura": 3,
  "idPedido": 3,
  "rfc": "PEDR850615HDF",
  "razonSocial": "Pedro Ramírez Torres",
  "subtotal": 17412.07,
  "iva": 2786.93,
  "total": 20199.00,
  "fechaEmision": "2024-11-08T10:30:00"
}
```

**Casos de Error:**
```json
// Si el pedido no existe
{
  "error": "Pedido no encontrado"
}

// Si ya existe factura para ese pedido
{
  "error": "Ya existe una factura para este pedido"
}

// Si RFC está vacío
{
  "error": "RFC y Razón Social son obligatorios"
}
```

**Pedidos disponibles para solicitar factura:**
- Pedido 3 (Usuario: María López, id=2) - $20,199
- Pedido 4 (Usuario: Pedro Ramírez, id=5) - $10,700
- Pedido 5 (Usuario: Carlos Martínez, id=3) - $3,200

---

### 📋 2. OBTENER FACTURAS DE UN USUARIO
```
GET http://localhost:8080/api/facturas/usuario/2
```

**Respuesta Exitosa (200 OK):**
```json
[
  {
    "idFactura": 1,
    "rfc": "XAXX010101000",
    "razonSocial": "María López Rodríguez",
    "subtotal": 9912.93,
    "iva": 1586.07,
    "total": 11499.00,
    "fechaEmision": "2024-11-01T09:00:00"
  }
]
```

**Prueba con estos usuarios:**
- Usuario 2 (María López) - Tiene 1 factura
- Usuario 3 (Carlos Martínez) - Tiene 1 factura
- Usuario 5 (Pedro Ramírez) - No tiene facturas aún

---

### 📋 3. OBTENER FACTURA DE UN PEDIDO ESPECÍFICO
```
GET http://localhost:8080/api/facturas/pedido/1
```

**Respuesta Exitosa (200 OK):**
```json
{
  "idFactura": 1,
  "rfc": "XAXX010101000",
  "razonSocial": "María López Rodríguez",
  "subtotal": 9912.93,
  "iva": 1586.07,
  "total": 11499.00,
  "fechaEmision": "2024-11-01T09:00:00"
}
```

**Pedidos con factura:**
- Pedido 1 - Tiene factura
- Pedido 2 - Tiene factura

**Pedidos sin factura:**
- Pedido 3, 4, 5 - Sin factura (devuelve error 404)

---

### 📋 4. VER TODAS LAS FACTURAS (ADMIN)
```
GET http://localhost:8080/api/facturas/todas
```

**Respuesta Exitosa (200 OK):**
```json
[
  {
    "idFactura": 1,
    "rfc": "XAXX010101000",
    "razonSocial": "María López Rodríguez",
    "subtotal": 9912.93,
    "iva": 1586.07,
    "total": 11499.00,
    "fechaEmision": "2024-11-01T09:00:00"
  },
  {
    "idFactura": 2,
    "rfc": "MEGA880101A23",
    "razonSocial": "Muebles El Gran Hogar SA de CV",
    "subtotal": 13534.48,
    "iva": 2165.52,
    "total": 15700.00,
    "fechaEmision": "2024-11-02T11:00:00"
  }
]
```

---

## FLUJO DE PRUEBA COMPLETO

### ESCENARIO: Cliente Solicita Factura

1. **Verificar pedidos sin factura**
```
GET http://localhost:8080/api/facturas/pedido/3
# Debe devolver 404 (sin factura)
```

2. **Solicitar factura**
```
POST http://localhost:8080/api/facturas/solicitar
Body:
{
  "idPedido": 3,
  "rfc": "LOPR920315MDF",
  "razonSocial": "María López Rodríguez"
}
```

3. **Verificar que se creó**
```
GET http://localhost:8080/api/facturas/pedido/3
# Ahora debe devolver la factura
```

4. **Ver todas las facturas del usuario**
```
GET http://localhost:8080/api/facturas/usuario/2
# Debe mostrar 2 facturas (la original + la nueva)
```

5. **Ver todas las facturas del sistema (Admin)**
```
GET http://localhost:8080/api/facturas/todas
# Debe mostrar 3 facturas en total
```

---

## DATOS DE PRUEBA DISPONIBLES

### Usuarios
| ID | Nombre | Correo | Rol |
|----|--------|--------|-----|
| 1 | Juan Pérez | juan.perez@mail.com | ADMIN |
| 2 | María López | maria.lopez@mail.com | CLIENTE |
| 3 | Carlos Martínez | carlos.martinez@mail.com | CLIENTE |
| 5 | Pedro Ramírez | pedro.ramirez@mail.com | CLIENTE |

### Pedidos
| ID | Usuario | Total | Estado | ¿Tiene Factura? |
|----|---------|-------|--------|-----------------|
| 1 | María López | $11,499 | ENTREGADO | ✅ SÍ |
| 2 | Carlos Martínez | $15,700 | ENTREGADO | ✅ SÍ |
| 3 | María López | $20,199 | ENVIADO | ❌ NO |
| 4 | Pedro Ramírez | $10,700 | PROCESANDO | ❌ NO |
| 5 | Carlos Martínez | $3,200 | ENTREGADO | ❌ NO |

### Facturas Existentes
| ID | Pedido | RFC | Total |
|----|--------|-----|-------|
| 1 | 1 | XAXX010101000 | $11,499 |
| 2 | 2 | MEGA880101A23 | $15,700 |

---

## CÁLCULO DEL IVA

El sistema calcula automáticamente:
```
Total del pedido = $20,199.00

subtotal = total / 1.16 = $17,412.07
iva = subtotal * 0.16 = $2,786.93
total = subtotal + iva = $20,199.00 ✓
```

---

## ERRORES COMUNES

### Error: "Connection refused"
- ✅ Verifica que la aplicación esté corriendo en http://localhost:8080
- ✅ Revisa los logs de Spring Boot en IntelliJ

### Error: "Pedido no encontrado"
- ✅ Usa un ID de pedido que exista en la base de datos (1-5)
- ✅ Verifica que los datos de prueba se cargaron correctamente

### Error: "Ya existe una factura para este pedido"
- ✅ Ese pedido ya tiene factura asignada
- ✅ Usa otro pedido (3, 4 o 5)

### La base de datos está vacía
- ✅ Ejecuta el script `datos_prueba.sql` en MySQL
- ✅ Verifica la conexión: `mysql -u root -p` (password: root123)

### Error de compilación: "cannot find symbol: method setPedido"
- ✅ Instala el plugin de Lombok en IntelliJ
- ✅ Habilita "Annotation Processing" en IntelliJ
- ✅ File > Invalidate Caches > Invalidate and Restart
- ✅ Build > Rebuild Project

---

## IMPORTAR COLECCIÓN A POSTMAN

Crea una nueva colección con estos requests ya configurados:

1. Abre Postman
2. Click en "Import"
3. Pega estos endpoints como cURL:

```bash
# Solicitar Factura
curl --location 'http://localhost:8080/api/facturas/solicitar' \
--header 'Content-Type: application/json' \
--data '{
  "idPedido": 3,
  "rfc": "LOPR920315MDF",
  "razonSocial": "María López Rodríguez"
}'

# Ver facturas de usuario
curl --location 'http://localhost:8080/api/facturas/usuario/2'

# Ver factura de pedido
curl --location 'http://localhost:8080/api/facturas/pedido/1'

# Admin: Ver todas
curl --location 'http://localhost:8080/api/facturas/todas'
```

---

## VERIFICACIÓN EN LA BASE DE DATOS

Puedes verificar directamente en MySQL:

```sql
-- Ver todas las facturas
SELECT * FROM facturas;

-- Ver facturas con información del pedido
SELECT
    f.id_factura,
    f.rfc,
    f.razon_social,
    f.total,
    f.fecha_emision,
    p.numero_guia,
    u.nombre,
    u.apellidos
FROM facturas f
JOIN pedidos p ON f.id_pedido = p.id_pedido
JOIN usuarios u ON p.id_usuario = u.id_usuario;

-- Ver pedidos sin factura
SELECT p.id_pedido, p.total, p.estado_pedido, u.nombre
FROM pedidos p
LEFT JOIN facturas f ON p.id_pedido = f.id_pedido
JOIN usuarios u ON p.id_usuario = u.id_usuario
WHERE f.id_factura IS NULL;
```

---

## RESUMEN DE ENDPOINTS

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | /api/facturas/solicitar | Crear factura para un pedido |
| GET | /api/facturas/usuario/{id} | Ver facturas de un usuario |
| GET | /api/facturas/pedido/{id} | Ver factura de un pedido |
| GET | /api/facturas/todas | Ver todas las facturas (Admin) |

---

**¡Listo para probar el sistema de facturación!** 🎉
