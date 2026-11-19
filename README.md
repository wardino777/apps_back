# ⚙️ Backend Pañol TI — Arquitectura de Microservicios

Backend distribuido compuesto por cuatro microservicios independientes desarrollados en **Java 21** y **Spring Boot**, desplegados en la nube mediante **Railway** para dar soporte 24/7 a la aplicación móvil de gestión del Pañol TI (Duoc UC — Sede Maipú).

---

## 👥 Integrantes del Equipo
* **Benyamin Arcapio**
* **Ignacio Álvarez**
* *(Agregar tercer integrante si corresponde)*

---

## 🏗️ Arquitectura y Tecnologías
El repositorio está organizado como un **monorepo** donde cada microservicio cuenta con su propio proyecto Maven independiente:

* **Lenguaje:** Java 21
* **Framework:** Spring Boot (Spring Web / Validation)
* **Gestor de Dependencias:** Apache Maven
* **Servidor Embebido:** Apache Tomcat (Puerto interno `8080`)
* **Despliegue Cloud:** Railway (HTTPS con disponibilidad 24/7)

### Estructura del Repositorio
```text
apps_back/
├── ms-prestamos/    # Gestión de solicitudes y préstamos de equipos
├── ms-soporte/      # Recepción y gestión de tickets de fallas técnicas
├── ms-auditoria/    # Registro y trazabilidad de operaciones
└── ms-seguridad/    # Control de acceso y validación de perfiles
```

---

## ☁️ Despliegue en Producción (Railway)

Cada carpeta raíz está vinculada como un servicio independiente en **Railway** escuchando en el puerto `8080`:

| Microservicio | Carpeta (`Root Directory`) | URL Pública en Producción | Descripción |
| :--- | :--- | :--- | :--- |
| **MS Préstamos** | `ms-prestamos` | `https://ms-prestamos.up.railway.app/` | Administra las reservas y estados de equipos solicitados |
| **MS Soporte** | `ms-soporte` | `https://ms-soporte.up.railway.app/` | Procesa los reportes de fallas técnicas enviados desde la app |
| **MS Auditoría** | `ms-auditoria` | `https://ms-auditoria.up.railway.app/` | Registra bitácoras y movimientos relevantes del sistema |
| **MS Seguridad** | `ms-seguridad` | `https://ms-seguridad.up.railway.app/` | Gestiona permisos y validación de credenciales/roles |

* **Repositorio Frontend (App Android):** `https://github.com/wardino777/apps`

---

## 🚀 Ejecución Local (Desarrollo)

1. Clonar el repositorio:
   ```bash
   git clone [https://github.com/wardino777/apps_back.git](https://github.com/wardino777/apps_back.git)
   ```
2. Entrar a la carpeta del microservicio que se desea levantar (por ejemplo, `ms-soporte`):
   ```bash
   cd apps_back/ms-soporte
   ```
3. Compilar y ejecutar con Maven Wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```
   *(En Windows usar `mvnw.cmd spring-boot:run`)*.

El servicio quedará disponible en `http://localhost:8080` (o en el puerto configurado en su respectivo `application.properties`).
