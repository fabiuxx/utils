/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests;

import fa.gs.utils.database.dto.DtoMapper;
import fa.gs.utils.database.query.commands.SelectQuery;
import fa.gs.utils.misc.Numeric;
import fa.gs.utils.tests.database.Persistence;
import fa.gs.utils.tests.database.PersonaEmail;
import java.util.Collection;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Test_DatabaseQuery {

    private static Persistence p = null;

    @BeforeClass
    public static void setUpClass() throws Throwable {
        try {
        p = new Persistence();
        } catch(Throwable thr) {
            thr.printStackTrace(System.err);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        if(p != null) {
            p.finish();
        }
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test1() {
        EntityManager em = p.getEntityManager();
        javax.persistence.Query q = em.createNativeQuery("select 1");
        Object obj = q.getSingleResult();
        System.err.printf("RESULT >>> %s\n", Numeric.adaptAsBigDecimal(obj));
        Assertions.assertTrue(obj != null);
    }

    @Test
    public void test2() throws Throwable {
        DtoMapper<PersonaEmail> mapper = DtoMapper.prepare(PersonaEmail.class);
        SelectQuery q = mapper.getSelectQuery();
        q.limit(10L);
        Collection<PersonaEmail> instances = mapper.select(q, p.getEntityManager());
        for (PersonaEmail em : instances) {
            System.out.printf("%s; %s\n", em.idEmail, em.idPersona);
        }
    }

}
