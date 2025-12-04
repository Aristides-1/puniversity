# Sistema Universidad - Desktop

## Requisitos
- JDK 11 o superior
- MySQL 8.0+
- MySQL Connector/J 8.0.33

## Instalación

1. Descargar MySQL Connector:
   https://dev.mysql.com/downloads/connector/j/
   Colocar mysql-connector-java-8.0.33.jar en la carpeta lib/

2. Crear base de datos:
   mysql -u root -p < database/universidad_db.sql

3. Configurar conexión:
   Editar config/database.properties

4. Compilar:
   Windows: compile.bat
   
5. Ejecutar:
   Windows: run.bat

## Estructura

src/
├── model/      # Clases del dominio
├── dao/        # Acceso a datos
├── service/    # Lógica de negocio
└── view/       # Interfaces gráficas (Swing)

## Características

- Gestión de Alumnos (CRUD)
- Gestión de Carreras y Planes de Estudio
- Matriculación con validación de prerequisitos
- Interfaz gráfica intuitiva con Java Swing
- Persistencia en MySQL
