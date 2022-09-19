/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests;

import fa.gs.utils.database.dto.DtoMapper;
import fa.gs.utils.database.dto.DtoQuery;
import fa.gs.utils.database.query.commands.SelectQuery;
import fa.gs.utils.misc.numeric.Numeric;
import fa.gs.utils.tests.database.Persistence;
import fa.gs.utils.tests.database.PersonaEmail;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Test_DatabaseQuery {

    private static Persistence persistence = null;

    @BeforeAll
    public static void setUpClass() throws Throwable {
        persistence = new Persistence();
    }

    @AfterAll
    public static void tearDownClass() {
        if (persistence != null) {
            persistence.finish();
        }
    }

    @Test
    public void test1() {
        EntityManager em = persistence.getEntityManager();
        javax.persistence.Query q = em.createNativeQuery("select 1");
        Object obj = q.getSingleResult();
        System.err.printf("RESULT >>> %s\n", Numeric.adaptAsBigDecimal(obj));
        Assertions.assertTrue(obj != null);
    }

    @Test
    public void test2() throws Throwable {
        SelectQuery query = DtoQuery.prepareSelectStatement(PersonaEmail.class);
        String sql = query.stringify(null);

        DtoMapper<PersonaEmail> mapper = DtoMapper.prepare(PersonaEmail.class);
        PersonaEmail[] instances = mapper.select(sql, persistence.getEntityManager());
        Assertions.assertFalse(fa.gs.utils.misc.Assertions.isNullOrEmpty(instances));
    }

    @Test
    public void test3() throws Throwable {
        SelectQuery query = DtoQuery.prepareSelectStatement(PersonaEmail.class);
        query.limit(10L);
        String sql = query.stringify(null);

        DtoMapper<PersonaEmail> mapper = DtoMapper.prepare(PersonaEmail.class);
        PersonaEmail[] instances = mapper.select(sql, persistence.getEntityManager());
        Assertions.assertFalse(fa.gs.utils.misc.Assertions.isNullOrEmpty(instances));
        Assertions.assertEquals(10, instances.length);
    }

    @Test
    public void test4() throws Throwable {
        SelectQuery query = DtoQuery.prepareSelectStatement(PersonaEmail.class);
        String sql = query.stringify(null);
        System.out.println(sql);
        Assertions.assertFalse(fa.gs.utils.misc.Assertions.stringNullOrEmpty(sql));
        Assertions.assertTrue(sql.contains("3 = 3"));
    }

    /*
    @Test
    public void test5() throws Throwable {
        SelectQuery query = DtoQuery.prepareSelectStatement(PersonaEmail.class);
        String sql = query.stringify(null);

        EntityManager em = persistence.getEntityManager();
        DtoMapper<PersonaEmail> mapper = DtoMapper.prepare(PersonaEmail.class);
        PersonaEmail[] instances = mapper.select(sql, em);
        for (PersonaEmail instance : instances) {
            Assertions.assertEquals(EnumTest.E1, instance.enumTest);
        }
    }
     */
}
