/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.mapping;

import fa.gs.utils.database.jpa.types.pg.PgCodificableEnumType;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Codificable;
import fa.gs.utils.misc.Reflection;
import fa.gs.utils.misc.errors.Errors;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManager;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.usertype.UserType;

/**
 *
 * @author Fabio A. González Sosa
 */
public class HibernateOrmResultSetAdapter implements QueryResultSetAdapter {

    @Override
    public Collection<Map<String, Object>> select(String sql, EntityManager em, Map<String, Field> mappings) throws Throwable {
        // Crear query.
        Session hibernateSession = em.unwrap(Session.class);
        SQLQuery hibernateQuery = hibernateSession.createSQLQuery(sql);

        // Agregar mapeo de columnas anotadas de manera especial.
        for (Map.Entry<String, Field> entry : mappings.entrySet()) {
            // Obtener definicion de tipo para mapeo hibernate-sql.
            org.hibernate.type.Type hibernateTypeDef = null;
            Annotation hibernateTypeAnnotation = entry.getValue().getAnnotation(Type.class);
            if (hibernateTypeAnnotation != null) {
                // Obtener convertidor de tipo via anotacion @Type.
                Class typeClass = adaptUserTypeClass(entry.getValue(), (Type) hibernateTypeAnnotation);
                if (Reflection.isInstanceOf(typeClass, UserType.class)) {
                    Properties typeParameters = adaptUserTypeParameters(entry.getValue(), (Type) hibernateTypeAnnotation);
                    hibernateTypeDef = hibernateSession.getTypeHelper().custom(typeClass, typeParameters);
                } else {
                    hibernateTypeDef = hibernateSession.getTypeHelper().heuristicType(((Type) hibernateTypeAnnotation).type());
                }
            } else {
                // Obtener convertidor de tipo en base a tipo de atributo, exceptuando atributos codificables.
                Class typeClass = entry.getValue().getType();
                String typeName = typeClass.getCanonicalName();
                hibernateTypeDef = hibernateSession.getTypeHelper().heuristicType(typeName);
            }

            // Control.
            if (hibernateTypeDef == null) {
                throw Errors.unsupported("No se pudo identificar el convertidor de tipo para campo '%s'.", entry.getValue().getName());
            }
            hibernateQuery.addScalar(entry.getKey(), hibernateTypeDef);
        }

        // Obtener datos.
        hibernateQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return (Collection<Map<String, Object>>) hibernateQuery.list();
    }

    private Class adaptUserTypeClass(Field field, Type typeAnnotation) throws ClassNotFoundException {
        String typename = typeAnnotation.type();
        return Class.forName(typename);
    }

    private Properties adaptUserTypeParameters(Field field, Type typeAnnotation) {
        Properties properties = new Properties();

        // Parametros base, definidos a nivel de anotacion.
        Parameter[] params = typeAnnotation.parameters();
        if (!Assertions.isNullOrEmpty(params)) {
            for (Parameter param : params) {
                properties.put(param.name(), param.value());
            }
        }

        // Parametros especiales para campos Codificables.
        if (Reflection.isInstanceOf(field.getType(), Codificable.class)) {
            properties.put(PgCodificableEnumType.PARAM_CODIFICABLE_QNAME, field.getType().getCanonicalName());
        }

        return properties;
    }

}
