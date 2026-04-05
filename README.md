## Tazy-SM
Este software es un sistema web en pruebas, inteligente de gestión de ventas desarrollado para microempresas textiles en Chiquinquirá. Integra el seguimiento de ventas tradicional con análisis predictivo y deep learning, brindando soporte a la toma de decisiones y mejorando el rendimiento del negocio de microempresas. Creado por Will ZP y Miguel Sierra, expuesto en el trabajo de grado titulado "Software de Gestión de Ventas para microempresas textiles en el municipio de Chiquinquirá, con Análisis Predictivo y Machine Learning". 

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

## Angular

This project was generated using [Angular CLI](https://github.com/angular/angular-cli) version 21.1.4.

## Development server

To start a local development server, run:

```bash
ng serve
```

Once the server is running, open your browser and navigate to `http://localhost:4200/`. The application will automatically reload whenever you modify any of the source files.

## Code scaffolding

Angular CLI includes powerful code scaffolding tools. To generate a new component, run:

```bash
ng generate component component-name
```

For a complete list of available schematics (such as `components`, `directives`, or `pipes`), run:

```bash
ng generate --help
```

## Building

To build the project run:

```bash
ng build
```

This will compile your project and store the build artifacts in the `dist/` directory. By default, the production build optimizes your application for performance and speed.

## Running unit tests

To execute unit tests with the [Vitest](https://vitest.dev/) test runner, use the following command:

```bash
ng test
```

## Running end-to-end tests

For end-to-end (e2e) testing, run:

```bash
ng e2e
```

Angular CLI does not come with an end-to-end testing framework by default. You can choose one that suits your needs.

## Additional Resources

For more information on using the Angular CLI, including detailed command references, visit the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.

> La ciencia de hoy, es la tecnología del mañana. — Edward Teller
