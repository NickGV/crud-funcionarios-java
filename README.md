# Sistema de Gestión de Talento Humano

Sistema CRUD (Create, Read, Update, Delete) desarrollado en Java con interfaz gráfica Swing, implementando el Patrón DAO y Manejo de Excepciones para la gestión de información de funcionarios de la Institución Universitaria Digital de Antioquia.

## 📋 Descripción

Aplicación desktop que permite administrar información de funcionarios, incluyendo sus datos personales. El sistema está diseñado con arquitectura en capas y mejores prácticas de programación orientada a objetos. La base de datos incluye tablas relacionadas para grupo familiar y formación académica de cada funcionario.

## 🎯 Características

- ✅ **CRUD Completo**: Crear, Listar, Actualizar y Eliminar funcionarios
- ✅ **Patrón DAO**: Separación completa entre lógica de negocio y acceso a datos
- ✅ **Manejo de Excepciones Personalizadas**: Control robusto de errores con DAOException
- ✅ **Interfaz Gráfica Moderna**: Java Swing con diseño profesional y botones coloridos
- ✅ **Validaciones**: Verificación de campos obligatorios, formatos de fecha y duplicados
- ✅ **Modelo Relacional**: Base de datos normalizada con relaciones CASCADE
- ✅ **Scripts SQL**: Creación y poblado inicial de base de datos incluidos
- ✅ **Seguridad**: Credenciales de base de datos en archivo de configuración externo

## 🏗️ Arquitectura

```
crud-funcionarios/
├── src/
│   ├── dao/
│   │   ├── ConexionBD.java          # Conexión a BD con Properties
│   │   └── FuncionarioDAO.java      # CRUD con patrón DAO
│   ├── exceptions/
│   │   └── DAOException.java        # Excepción personalizada
│   ├── model/
│   │   ├── Funcionario.java         # Entidad principal
│   │   ├── GrupoFamiliar.java       # Entidad relacionada
│   │   └── Estudio.java             # Entidad formación académica
│   ├── view/
│   │   └── FrmFuncionario.java      # Interfaz gráfica Swing
│   └── Main.java                    # Punto de entrada
├── database/
│   └── talento_humano.sql           # Script BD completo
├── lib/
│   └── mysql-connector-j-9.5.0.jar  # Driver JDBC MySQL
├── db.properties.example            # Plantilla de configuración
├── db.properties                    # Configuración (gitignored)
└── README.md

```

## 🗄️ Modelo Relacional

### Tablas:

1. **funcionario** - Información personal de funcionarios (tabla principal)

   - Campos: id, tipo_identificacion, numero_identificacion, nombres, apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento

2. **grupo_familiar** - Grupo familiar de cada funcionario

   - Campos: id, id_funcionario (FK), nombre, parentesco, edad, telefono
   - Relación: ON DELETE CASCADE

3. **estudio** - Formación académica de funcionarios
   - Campos: id, id_funcionario (FK), universidad, nivel_estudio, titulo
   - Relación: ON DELETE CASCADE

### Relaciones:

- `grupo_familiar` → `funcionario` (N:1 con CASCADE)
- `estudio` → `funcionario` (N:1 con CASCADE)

## 🚀 Requisitos

### Software Necesario:

- **Java JDK**: 8 o superior (recomendado JDK 11+)
- **MySQL**: 5.7 o superior (o MariaDB)
- **IDE**: IntelliJ IDEA, Eclipse o NetBeans (recomendado)
- **MySQL Connector/J**: 9.5.0 (incluido en `lib/`)

## 📦 Instalación y Configuración

### 1. Configurar Base de Datos

**Opción A: MySQL Command Line**

```bash
mysql -u root -p < database/talento_humano.sql
```

**Opción B: MySQL Workbench**

1. Abrir MySQL Workbench
2. Conectarse a tu servidor MySQL
3. File → Open SQL Script → seleccionar `database/talento_humano.sql`
4. Ejecutar el script completo (⚡ icono rayo o Ctrl+Shift+Enter)

Esto creará:

- La base de datos `talento_humano`
- Las tres tablas (funcionario, grupo_familiar, estudio)
- Datos de ejemplo para pruebas

### 2. Configurar Credenciales de Base de Datos

**Copiar el archivo de ejemplo:**

```bash
# Windows (PowerShell)
Copy-Item db.properties.example db.properties

# Linux/Mac
cp db.properties.example db.properties
```

**Editar `db.properties` con tus credenciales:**

```properties
db.url=jdbc:mysql://localhost:3306/talento_humano
db.user=root
db.password=tu_password_aqui
```

⚠️ **Importante**: El archivo `db.properties` está en `.gitignore` y NO se subirá al repositorio por seguridad.

### 3. Configurar el Proyecto en tu IDE

**IntelliJ IDEA:**

1. File → Open → Seleccionar carpeta del proyecto
2. El IDE detectará automáticamente la biblioteca en `lib/`
3. Si no, ve a File → Project Structure → Libraries → "+" → Java → seleccionar `lib/mysql-connector-j-9.5.0.jar`

**Eclipse:**

1. File → Open Projects from File System → Seleccionar carpeta
2. Right-click proyecto → Build Path → Configure Build Path
3. Libraries → Add External JARs → seleccionar `lib/mysql-connector-j-9.5.0.jar`

**NetBeans:**

1. File → Open Project → Seleccionar carpeta
2. Right-click en Libraries → Add JAR/Folder → seleccionar `lib/mysql-connector-j-9.5.0.jar`

### 4. Ejecutar la Aplicación

**Desde el IDE:**

- Abrir `src/Main.java`
- Click derecho → Run 'Main' (o presionar Shift+F10 en IntelliJ)

**Desde la línea de comandos:**

```bash
# Compilar (desde la raíz del proyecto)
javac -cp "lib/*" -d out src/**/*.java src/*.java

# Ejecutar
java -cp "out;lib/*" Main
```

Si todo está configurado correctamente, se abrirá la ventana principal de la aplicación.

## 💻 Uso de la Aplicación

### Interfaz Principal

La aplicación tiene tres secciones:

**1. Formulario Superior (Datos del Funcionario)**

- Campos para tipo y número de identificación
- Nombres y apellidos
- Estado civil (combo box)
- Sexo (radio buttons: Masculino/Femenino)
- Dirección, teléfono
- Fecha de nacimiento (formato: yyyy-MM-dd)

**2. Botones de Acción (Centro)**

- 🔵 **Nuevo**: Limpia el formulario para ingresar nuevo funcionario
- 🟢 **Guardar**: Inserta un nuevo funcionario en la BD
- 🟠 **Actualizar**: Modifica el funcionario seleccionado
- 🔴 **Eliminar**: Elimina el funcionario seleccionado (con confirmación)
- ⚪ **Limpiar**: Limpia todos los campos del formulario

**3. Tabla Inferior (Lista de Funcionarios)**

- Muestra todos los funcionarios registrados
- Click en una fila para cargar datos en el formulario
- Se actualiza automáticamente después de cada operación

### Operaciones CRUD

#### Crear Nuevo Funcionario:

1. Click en botón **Nuevo** (o **Limpiar**)
2. Llenar todos los campos obligatorios:
   - Número de identificación (único)
   - Nombres y apellidos
   - Seleccionar sexo
   - Fecha de nacimiento en formato: `yyyy-MM-dd` (ej: `1990-05-12`)
3. Click en **Guardar** (verde)
4. Confirmación de éxito o mensaje de error

#### Actualizar Funcionario:

1. Click en una fila de la tabla para seleccionar
2. Los datos se cargan automáticamente en el formulario
3. Modificar los campos deseados
4. Click en **Actualizar** (naranja)
5. Confirmación de éxito

#### Eliminar Funcionario:

1. Click en una fila de la tabla para seleccionar
2. Click en **Eliminar** (rojo)
3. Confirmar la eliminación en el diálogo
4. ⚠️ **Nota**: Esto eliminará también el grupo familiar y estudios (CASCADE)

## 🎨 Características de la Interfaz

- **Look and Feel**: Aspecto nativo del sistema operativo
- **Layout Profesional**: GridBagLayout para formulario responsive
- **Colores Intuitivos**:
  - Azul (Nuevo), Verde (Guardar), Naranja (Actualizar), Rojo (Eliminar), Gris (Limpiar)
- **Validaciones en Tiempo Real**: Mensajes claros de error
- **Tabla Interactiva**: Selección simple, scroll automático
- **Diálogos Informativos**: Confirmaciones y alertas

## 🔧 Manejo de Excepciones

El sistema implementa manejo robusto de excepciones en tres niveles:

### 1. Capa DAO (FuncionarioDAO)

```java
try {
    // Operación de BD
} catch (SQLException e) {
    throw new DAOException("Mensaje descriptivo", e);
}
```

### 2. Excepción Personalizada (DAOException)

- Encapsula errores de acceso a datos
- Proporciona mensajes significativos al usuario
- Mantiene la causa original para debugging

### 3. Capa de Vista (FrmFuncionario)

```java
try {
    funcionarioDAO.insertar(funcionario);
    JOptionPane.showMessageDialog(...);
} catch (DAOException e) {
    JOptionPane.showMessageDialog(this,
        "Error: " + e.getMessage(),
        "Error", JOptionPane.ERROR_MESSAGE);
}
```

### Tipos de Errores Manejados:

- ❌ **Duplicados**: Número de identificación ya existe
- ❌ **Formatos**: Fecha inválida (DateTimeParseException)
- ❌ **Campos Vacíos**: Validación antes de guardar
- ❌ **Conexión BD**: Problemas con MySQL o credenciales
- ❌ **Recursos**: Cierre automático con try-with-resources

## 📝 Patrón DAO Implementado

### Separación de Responsabilidades:

```
Model (Funcionario) ←→ DAO (FuncionarioDAO) ←→ Database (MySQL)
                              ↑
                              |
                    View (FrmFuncionario)
```

### Métodos en FuncionarioDAO:

```java
// CREATE
void insertar(Funcionario funcionario) throws DAOException

// READ
List<Funcionario> listarTodos() throws DAOException
Funcionario buscarPorId(int id) throws DAOException
Funcionario buscarPorNumeroIdentificacion(String numero) throws DAOException

// UPDATE
void actualizar(Funcionario funcionario) throws DAOException

// DELETE
void eliminar(int idFuncionario) throws DAOException

// HELPER
Funcionario extraerFuncionarioDeResultSet(ResultSet rs) throws SQLException
```

### Ventajas del Patrón DAO:

✅ Separación de lógica de negocio y acceso a datos  
✅ Facilita el mantenimiento y testing  
✅ Cambios en BD no afectan la vista  
✅ Reutilización del código de acceso a datos  
✅ Uso de PreparedStatements (previene SQL injection)

## 🧪 Pruebas y Verificación

### Base de Datos:

```sql
-- Verificar funcionarios insertados
SELECT * FROM funcionario;

-- Ver grupo familiar de un funcionario
SELECT f.nombres, f.apellidos, gf.nombre, gf.parentesco, gf.edad
FROM funcionario f
LEFT JOIN grupo_familiar gf ON f.id_funcionario = gf.id_funcionario
WHERE f.id_funcionario = 1;

-- Ver estudios de un funcionario
SELECT f.nombres, f.apellidos, e.universidad, e.nivel_estudio, e.titulo
FROM funcionario f
LEFT JOIN estudio e ON f.id_funcionario = e.id_funcionario
WHERE f.id_funcionario = 1;

-- Ver todo junto
SELECT f.*, COUNT(DISTINCT gf.id_familiar) as total_familia,
       COUNT(DISTINCT e.id_estudio) as total_estudios
FROM funcionario f
LEFT JOIN grupo_familiar gf ON f.id_funcionario = gf.id_funcionario
LEFT JOIN estudio e ON f.id_funcionario = e.id_funcionario
GROUP BY f.id_funcionario;
```

### Casos de Prueba en la Aplicación:

1. **✅ Crear Funcionario Válido**

   - Llenar todos los campos correctamente
   - Verificar que aparece en la tabla

2. **❌ Crear con Número Duplicado**

   - Intentar crear con número de identificación existente
   - Debe mostrar error de duplicado

3. **❌ Crear con Campos Vacíos**

   - Intentar guardar sin llenar campos obligatorios
   - Debe mostrar mensaje de validación

4. **❌ Fecha Inválida**

   - Intentar con formato incorrecto (dd/MM/yyyy)
   - Debe mostrar error de formato

5. **✅ Actualizar Funcionario**

   - Seleccionar de tabla, modificar, actualizar
   - Verificar cambios en tabla

6. **✅ Eliminar con Confirmación**

   - Seleccionar y eliminar
   - Confirmar que desaparece de la tabla

7. **✅ Relaciones CASCADE**
   - Insertar familia/estudios en BD manualmente
   - Eliminar funcionario desde app
   - Verificar que se eliminó todo

## 📚 Tecnologías Utilizadas

| Tecnología        | Propósito                       | Versión  |
| ----------------- | ------------------------------- | -------- |
| Java              | Lenguaje de programación        | JDK 8+   |
| Swing             | Framework para interfaz gráfica | Built-in |
| JDBC              | API de conectividad con BD      | Built-in |
| MySQL             | Sistema gestor de base de datos | 5.7+     |
| MySQL Connector/J | Driver JDBC para MySQL          | 9.5.0    |
| Properties        | Archivo de configuración        | Built-in |

### Conceptos Aplicados:

- 🏗️ **Patrón DAO**: Separación de capas
- 🎯 **POO**: Encapsulación, abstracción
- ⚠️ **Excepciones Personalizadas**: DAOException
- 🔒 **PreparedStatements**: Seguridad SQL
- 🔄 **Try-with-resources**: Gestión de recursos
- 📐 **MVC**: Modelo-Vista-Controlador adaptado

## 🎓 Aspectos Académicos Cubiertos

✅ **Modelo Relacional Normalizado**

- Diseño de base de datos con relaciones 1:N
- Uso de llaves foráneas con ON DELETE CASCADE

✅ **Script SQL Completo**

- Creación de base de datos y tablas
- Inserción de datos de prueba
- Relaciones y restricciones

✅ **Patrón DAO Completo**

- Clase `FuncionarioDAO` con separación de responsabilidades
- Clase `ConexionBD` para gestión de conexiones
- Uso de PreparedStatements para seguridad

✅ **Manejo de Excepciones**

- Clase personalizada `DAOException`
- Try-catch en todos los niveles
- Try-with-resources para cerrar recursos

✅ **CRUD Completo**

- Create: `insertar()`
- Read: `listarTodos()`, `buscarPorId()`
- Update: `actualizar()`
- Delete: `eliminar()`

✅ **Interfaz Desktop con Java Swing**

- JFrame con layouts profesionales
- JTable para visualización de datos
- Componentes: JTextField, JComboBox, JRadioButton
- Eventos y listeners
- Validaciones de entrada

## 🐛 Solución de Problemas

### Error: "No se pudo cargar db.properties"

**Solución**: Asegúrate de que `db.properties` existe en la raíz del proyecto y tiene el formato correcto.

### Error: "Error cargando el driver JDBC"

**Solución**: Verifica que `mysql-connector-j-9.5.0.jar` esté agregado al classpath del proyecto.

### Error: "Access denied for user"

**Solución**: Revisa las credenciales en `db.properties`. Usuario y contraseña deben ser correctos.

### Error: "Unknown database 'talento_humano'"

**Solución**: Ejecuta el script `database/talento_humano.sql` primero.

### Error: "Duplicate entry"

**Solución**: El número de identificación ya existe. Usa uno diferente o elimina el existente.

### La aplicación no muestra datos

**Solución**: Verifica que el servidor MySQL esté ejecutándose y que existan datos en la tabla.

## 📄 Licencia

Este proyecto es de uso académico para la asignatura de **Desarrollo de Software Seguro**.

## 👥 Autor

**Institución Universitaria Digital de Antioquia**  
Semestre 5 - Desarrollo de Software Seguro  
Evidencia de Aprendizaje 1

## 📞 Soporte

Para dudas o problemas:

1. Revisar la sección de Solución de Problemas
2. Verificar que todos los requisitos estén cumplidos
3. Revisar los comentarios en el código fuente

---

**✨ Proyecto completo y funcional ✨**

Este sistema demuestra la implementación del patrón DAO, manejo de excepciones, y una interfaz desktop profesional con Java Swing, cumpliendo todos los requisitos académicos establecidos.
