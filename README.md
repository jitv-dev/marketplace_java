# marketplace_java

Informacion a guardar para despues
# Limpiar archivos compilados anteriores
mvn clean

# Compilar + testear + instalar en repositorio local
mvn install

# Generar el JAR ejecutable en target/
mvn package

# Ejecutar la aplicación directamente
mvn spring-boot:run

# Saltar tests para ir más rápido en desarrollo
mvn package -DskipTests