/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa;

import fa.gs.utils.collections.Arrays;
import fa.gs.utils.collections.Lists;
import fa.gs.utils.collections.Maps;
import fa.gs.utils.database.jpa.types.pg.PgCodificableEnumType;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Codificable;
import fa.gs.utils.misc.Holder;
import fa.gs.utils.misc.Reflection;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.numeric.Numeric;
import fa.gs.utils.result.simple.Result;
import fa.gs.utils.result.simple.Results;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import lombok.Data;
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
public class Jpa {

    public static Long count(String sql, String countFieldName, EntityManager em) throws Throwable {
        // Ejecutar consulta.
        Collection<Map<String, Object>> resultsSet = select(sql, em);

        // Mapear resultado.
        Map<String, Object> resultSet = Lists.first(resultsSet);
        Object count0 = Maps.get(resultSet, countFieldName);
        return Numeric.adaptAsLong(count0);
    }

    public static Collection<Map<String, Object>> select(String sql, EntityManager em) throws Throwable {
        // Ejecutar la query e indica que necesitamos mapear el resultset a un mapa, valga la redundancia.
        org.hibernate.Query hibernateQuery = em.createNativeQuery(sql).unwrap(org.hibernate.Query.class);
        hibernateQuery.setFetchSize(64);
        hibernateQuery.setReadOnly(true);
        hibernateQuery.setCacheable(false);
        hibernateQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return (Collection<Map<String, Object>>) hibernateQuery.list();
    }

    public static Collection<Map<String, Object>> select(String sql, EntityManager em, Collection<HibernateScalarTypeInfo> scalarsInfo) throws Throwable {
        // Ejecutar la query e indica que necesitamos mapear el resultset a un mapa, valga la redundancia.
        org.hibernate.Session hibernateSession = em.unwrap(Session.class);
        SQLQuery hibernateQuery = hibernateSession.createSQLQuery(sql);
        if (!Assertions.isNullOrEmpty(scalarsInfo)) {
            for (HibernateScalarTypeInfo scalarInfo : scalarsInfo) {
                hibernateQuery.addScalar(scalarInfo.projection, scalarInfo.type);
            }
        }
        hibernateQuery.setFetchSize(64);
        hibernateQuery.setReadOnly(true);
        hibernateQuery.setCacheable(false);
        hibernateQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return (Collection<Map<String, Object>>) hibernateQuery.list();
    }

    public static <T> Collection<T> select(String sql, EntityManager em, Class<T> klass) {
        Collection<T> values = Lists.empty();
        Session session = em.unwrap(Session.class);
        session.doWork((Connection connection) -> {
            try {
                Method m = klass.getDeclaredMethod("instance", new Class[]{ResultSet.class, Session.class});
                if (Modifier.isStatic(m.getModifiers())) {
                    m.setAccessible(true);
                    try (PreparedStatement ps = connection.prepareStatement(sql)) {
                        ps.execute();
                        ResultSet rs = ps.getResultSet();
                        while (rs.next()) {
                            Object value = m.invoke(null, rs, session);
                            if (value != null) {
                                values.add(klass.cast(value));
                            }
                        }
                    }
                }
            } catch (Throwable thr) {
                throw new SQLException(thr);
            }
        });
        return values;
    }

    public static Jpa.HibernateScalarTypeInfo createBasicHibernateScalarType(EntityManager em, String projection, Class<?> klass) {
        org.hibernate.Session session = em.unwrap(Session.class);
        Jpa.HibernateScalarTypeInfo info = new Jpa.HibernateScalarTypeInfo();
        info.projection = projection;
        info.type = session.getTypeHelper().basic(klass);
        return info;
    }

    public static Jpa.HibernateScalarTypeInfo createCustomHibernateScalarType(EntityManager em, String projection, Class<?> klass) {
        return createCustomHibernateScalarType(em, projection, klass, new Properties());
    }

    public static Jpa.HibernateScalarTypeInfo createCustomHibernateScalarType(EntityManager em, String projection, Class<?> klass, Properties props) {
        org.hibernate.Session session = em.unwrap(Session.class);
        Jpa.HibernateScalarTypeInfo info = new Jpa.HibernateScalarTypeInfo();
        info.projection = projection;
        info.type = session.getTypeHelper().custom(klass, props);
        return info;
    }

    // Implementacion tomada de HibernateOrmResultSetAdapter.
    public static Collection<Jpa.HibernateScalarTypeInfo> collectHibernateScalarTypes(EntityManager em, Class<?> entity) {
        Collection<Jpa.HibernateScalarTypeInfo> infos = Lists.empty();
        try {
            Session hibernateSession = em.unwrap(Session.class);
            Field[] fields = entity.getDeclaredFields();
            for (Field field : fields) {
                String hibernateName = null;
                org.hibernate.type.Type hibernateTypeDef = null;
                Annotation hibernateTypeAnnotation = field.getAnnotation(Type.class);
                Annotation jpaColumnAnnotation = field.getAnnotation(Column.class);
                if (jpaColumnAnnotation != null) {
                    hibernateName = ((Column) jpaColumnAnnotation).name();
                    if (hibernateTypeAnnotation != null) {
                        // Obtener convertidor de tipo via anotacion @Type.
                        Class typeClass = adaptUserTypeClass(field, (Type) hibernateTypeAnnotation);
                        if (Reflection.isInstanceOf(typeClass, UserType.class)) {
                            Properties typeParameters = adaptUserTypeParameters(field, (Type) hibernateTypeAnnotation);
                            hibernateTypeDef = hibernateSession.getTypeHelper().custom(typeClass, typeParameters);
                        } else {
                            hibernateTypeDef = hibernateSession.getTypeHelper().heuristicType(((Type) hibernateTypeAnnotation).type());
                        }
                    } else {
                        // Obtener convertidor de tipo en base a tipo de atributo, exceptuando atributos codificables.
                        Class typeClass = field.getType();
                        String typeName = typeClass.getCanonicalName();
                        hibernateTypeDef = hibernateSession.getTypeHelper().heuristicType(typeName);
                    }
                }

                // Control. Error.
                if (hibernateName == null || hibernateTypeDef == null) {
                    continue;
                }

                // Ok.
                Jpa.HibernateScalarTypeInfo info = new Jpa.HibernateScalarTypeInfo();
                info.projection = hibernateName;
                info.type = hibernateTypeDef;
                infos.add(info);
            }
        } catch (Throwable thr) {
            ;
        }
        return infos;
    }

    private static Class adaptUserTypeClass(Field field, Type typeAnnotation) throws ClassNotFoundException {
        String typename = typeAnnotation.type();
        return Class.forName(typename);
    }

    private static Properties adaptUserTypeParameters(Field field, Type typeAnnotation) {
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

    public static Long executeUpdate(String sql, EntityManager em) {
        // Limpiar query.
        sql = sanitizeQuery(sql);

        // Ejecutar consulta.
        Query q = em.createNativeQuery(sql);
        return (long) q.executeUpdate();
    }

    public static Result<Boolean[]> executeBatch(final String[] statements, EntityManager em) throws Throwable {
        final Holder<Result<Boolean[]>> holder = Holder.instance();

        Session session = em.unwrap(Session.class);
        session.doWork((Connection conn) -> {
            Result<Boolean[]> result;

            try {
                Collection<Boolean> retcodes = Lists.empty();
                for (String statement : statements) {
                    try {
                        Statement stmt = conn.createStatement();
                        boolean ok = stmt.execute(statement);
                        retcodes.add(ok);
                    } catch (Throwable thr) {
                        Errors.dump(System.err, thr);
                        retcodes.add(Boolean.FALSE);
                    }
                }

                Boolean[] retcodes1 = Arrays.unwrap(retcodes, Boolean.class);
                result = Results.ok()
                        .value(retcodes1)
                        .build();
            } catch (Throwable thr) {
                result = Results.ko()
                        .cause(thr)
                        .message("Ocurrió un error ejecutando lote de sentencias.")
                        .build();
            }

            holder.set(result);
        });

        return holder.get();
    }

    private static String sanitizeQuery(String sql) {
        sql = sql.trim();

        // Fuente: https://hibernate.atlassian.net/browse/HHH-7962.
        if (sql.startsWith("(") && sql.endsWith(")")) {
            sql = sql.substring(1, Math.max(0, sql.length() - 1));
        }

        return sql;
    }

    @Data
    public static class HibernateScalarTypeInfo {

        private String projection;
        org.hibernate.type.Type type;
    }

}
