# LITERALURA

Catalago de libros por consola utilzando la API de [Gutendex](https://gutendex.com/),
este es un proyecto parte de los challenges propuestos en [#AluraLatam](https://www.aluracursos.com/)
con [#OracleNexEducation](https://app.aluracursos.com/form-one/registro/latam-general),
en la formación de **Java y Spring Boot G6 - ONE**

# ✨ Features

- 📋 Variables de entorno con [Spring Dotenv](https://github.com/paulschwarz/spring-dotenv).
- 🚀 Manejo de objetos json con [Gson](https://github.com/google/gson)
- 💻 Interfaz de usuario en consola.
- 🔎 Manejo de excepciones.
- 📚 Uso de Spring JPA, derived queries and jpql.
- 📦 Manejo de dependencias con maven.
- 🛠️ Base de datos en MariaDB .
- 📁 Administración de la base de datos como servicio mediante [Docker](https://www.docker.com/).
- 💪 Automaticación para la gestión del contenedor Docker de Base de datos usando [GNU Make](https://www.makigas.es/series/make#:~:text=Make%20es%20una%20utilidad%20del,%2C%20CMake...).).

![Aplicación en ejecución](docs/screen.gif)

# ⚡️ Notas de ejecución

A continuación se daran las instrucciones para ejecutar al aplicación en modo desarrollo
Es necesario tener instalado [Java](https://www.java.com/es/download/ie_manual.jsp) y
opcionalmente [Maven](https://maven.apache.org/download.cgi).
Maven ya viene integrado en algunos IDE'S como Netbeans ó Intellij IDEA,
sin embargo a continuación un tutorial rapido
de [como instalar Maven](https://www.youtube.com/watch?v=biBOXvSNaXg&list=PLvimn1Ins-40atMWQkxD8r8pRyPLAU0iQ&index=2&ab_channel=MitoCode).

### 1. Inicializar el proyecto

- Clonar el repositorio
  ``` bash
  git clone https://github.com/OscarDevCPP/Literalura.git
  ```

- Copiar archivo .env.example con nuevo nombre .env <br>
  Dentro del proyecto existe un archivo con el nombre ".env.example", se debe
  hacer una copia de este archivo dentro del mismo directorio con el nuevo nombre ".env"
  **Importante, no olvidar el punto '.' es parte del nombre del archivo.** <br>
  **Alternativamente**, se puede ejecutar el siguiente comando para completar este paso.
  ``` bash
  cp .env.example .env
  ```

### 2. Configurar la base de datos
Se tiene dos opciones:
* La primera es usar una [base de datos externa](#base-de-datos-externa).
* Y la Segunda es mediante un contenedor [Docker](#usando-docker) de MariaDB (Requisito, tener instalado [Docker](https://www.docker.com/) y GNU Make).

#### Base de datos externa
- Solo necesitas editar los datos necesarios en el .env, para conectar a tu base de datos

  ``` dotenv
  DB_HOST=localhost
  DB_PORT=6602
  DB_USER=admin
  DB_DATABASE=literalura_db
  DB_PASSWORD=12345678
  DEBUG=false
  ```
#### Usando docker
- Editar el archivo .docker/.env (o dejarlo con los valores por defecto)

  ``` dotenv
  APP_NAME=literalura
  
  # Dev MySQL Settings
  MYSQL_DATABASE=literalura_db
  MYSQL_USER=admin
  MYSQL_PASSWORD=12345678
  MYSQL_ROOT_PASSWORD=12345678
  ```
- Editar archivo .env segun los datos de .docker/.env
  ``` dotenv
  DB_HOST=localhost
  DB_PORT=6602
  DB_USER=admin
  DB_DATABASE=literalura_db
  DB_PASSWORD=12345678
  DEBUG=false
  ```
- Ejecutar el contenedor de la base de datos
  ``` bash
  make run params=-d
  ```
Notas: 
- Puedes conectarte a tu base de datos usando cualquier gestor de base de datos MySQL.
- Pero recomiendo usar el IDE Intellij IDEA, y conectarte a la base de datos mediante la creación de un [data source](https://www.jetbrains.com/help/idea/managing-data-sources.html#-wcxhqt_400)
  
### 3. Ejecutar la aplicación.

- Esto se puede hacer con el IDE de turno (Intellij Idea u otro). El proyecto ya viene configurado según Spring Boot.

# 🌈 Funcionalidades Basicas

1. Buscar un libro por titulo en Gutenberg y registrarlo.
2. Listar Libros registrados.
3. Listar Autores registrados.
4. Listar Autores vivos en un año determinado, ya se antes de cristo o despues de cristo.
5. Listar Libros segun un codigo de idioma.

Muchas gracias por revisar este repositorio. (@OscarDevCPP)
