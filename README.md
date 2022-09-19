# utils
Utilerías (a medida) para soporte de mis proyectos Java.

## Instalación
Existe una rama propia para cada versión puntual existente, que da soporte a proyectos que utilizan
cada versión específica de esta librería.

La versión mas actualizada a la fecha es la versión `0.0.3` y puede encontrarse directamente en Sonatype como una versión snapshot.

```xml
<dependency>
    <groupId>io.github.fabiuxx</groupId>
    <artifactId>utils</artifactId>
    <version>0.0.3-SNAPSHOT</version>
</dependency>
```

Para poder descargar la dependencia desde Sonatype, se debe agregar la siguiente configuración al archivo `pom.xml` (o también de forma general en `settings.xml` de maven).

```xml
<repositories>
    <repository>
        <id>ossrh</id>
        <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
    </repository>
</repositories>
```

## Licencia
Este software está distribuido bajo la licencia MIT. Ver `LICENSE.txt` para más información.