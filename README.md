## Tazy-SM
Este software es un sistema web en pruebas, inteligente de gestión de ventas desarrollado para microempresas textiles en Chiquinquirá. Integra el seguimiento de ventas tradicional con análisis predictivo y machine learning, brindando soporte a la toma de decisiones y mejorando el rendimiento del negocio de microempresas. Creado por Will ZP y Miguel Sierra, expuesto en el trabajo de grado titulado "Software de Gestión de Ventas para microempresas textiles en el municipio de Chiquinquirá, con Análisis Predictivo y Machine Learning". 

### Tecnologías expuestas
- **Backend**: Framework Spring Boot junto al gestor de bases de datos - MYSQL 8.4.
    - **Librerias y recursos**: DeepLearning4j, DataVec, Jasperreports*
- **Frontend**: Javascript Vanilla con la librería de CSS Tailwind utlizando los recursos de [Flowbite](https://flowbite.com/docs/getting-started/introduction/)* y con la API de JS de WebComponentes. (A futuro estimamos una migración extendida para Angular)
  - **Librerias y recursos**: Chart.JS*
 
### Modulos
- **Inventario ->** Apartado donde se registran, se eliminan y se modifican las existencias de los productos, ademas permite la visualización de productos a clientes.
- **Gestión de Usuarios ->** Apartado donde se registran los usuarios tanto de vendedores, proveedores y los clientes finales.
- **Ventas ->**  Apartado donde se registran las compras del usuario y se genera su respectiva factura electronica de acuerdo a las normas de la [DIAN](https://www.dian.gov.co/normatividad/Normatividad/Resoluci%C3%B3n%20000167%20de%2030-12-2021.pdf) (Dirección de Impuestos y Aduanas Nacionales) en Colombia. 
- **Modelo de ventas esperadas ->** Modulo donde se genera un modelo de red neuronal que determine la cantidad de ventas de cierto producto de acuerdo a datos historicos.

### Funciones esperadas*
- Conexión mediante BD en el gestor de BD de MYSQL WORKBENCH 8.0 CE para el registro de Inventario, Usuarios, Proveedores y Usuarios.
- En la parte del Backend se creará una API REST con el framework de JAVA, Spring Boot y sus dependencias: Spring Web, Spring Data JPA, MYSQL Driver y Lombok, y sus librerias DeepLearning4j, DataVec, Jasperreports*
- En la parte del Fronted se consumira el API con ayuda de la herramienta POSTMAN, posteriormente se hará la conexión entre frontend y backend con JavaScript Vanilla, finalmente se hará el diseño de la web con HTML, CSS y el Framework tailwindcss. 
- Control y registro de una microempresas con los modulos expuestos. Ademas de generar ventas esperadas a los productos textiles.

## Configuración de la base de datos


> La ciencia de hoy, es la tecnología del mañana. — Edward Teller
