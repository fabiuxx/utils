/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package fa.gs.utils.tests;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.misc.json.serialization.JsonDeserializer;
import fa.gs.utils.tests.json.Payload0;
import fa.gs.utils.tests.json.Payload1;
import fa.gs.utils.tests.json.Payload2;
import fa.gs.utils.tests.json.Payload3;
import fa.gs.utils.tests.json.Payload4;
import fa.gs.utils.tests.json.Payload5;
import fa.gs.utils.tests.json.Payload6;
import fa.gs.utils.tests.json.Payload7;
import fa.gs.utils.tests.json.Payload8;
import fa.gs.utils.tests.json.Payload9;
import fa.gs.utils.tests.json.Perfil;
import fa.gs.utils.tests.json.TipoUsuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Test_Json_Deserialize {

    @Test
    public void test0() throws Throwable {
        /**
         * Debe lanzar una excepcion ya que Payload0 define dos propiedades
         * obligatorias.
         */
        String json = "{}";
        Assertions.assertThrows(Throwable.class, () -> {
            JsonDeserializer.deserialize(json, Payload0.class);
        });
    }

    @Test
    public void test1() throws Throwable {
        /**
         * Debe lanzar una excepcion ya que Payload0 define dos propiedades
         * obligatorias.
         */
        String json = "{A: \"1\"}";
        Assertions.assertThrows(Throwable.class, () -> {
            JsonDeserializer.deserialize(json, Payload0.class);
        });
    }

    @Test
    public void test2() throws Throwable {
        /**
         * Debe lanzar una excepcion ya que Payload0 define dos propiedades
         * obligatorias, pero de tipo String e Integer.
         */
        String json = "{A: \"1\", X: false}";
        Assertions.assertThrows(Throwable.class, () -> {
            JsonDeserializer.deserialize(json, Payload0.class);
        });
    }

    @Test
    public void test3() throws Throwable {
        /**
         * Ok. Ambas propiedades se definen con valores de tipo esperado.
         */
        String json = "{A: \"1\", X: 2}";
        Payload0 payload = JsonDeserializer.deserialize(json, Payload0.class);
        Assertions.assertEquals("1", payload.A);
        Assertions.assertEquals((Integer) 2, payload.X);
    }

    @Test
    public void test4() throws Throwable {
        /**
         * Debe lanzar una excepcion ya que si no se define un nombre para la
         * propiedad, se toma el nombre del atributo de clase. En este caso x
         * !== X.
         */
        String json = "{A: \"1\", x: 2}";
        Assertions.assertThrows(Throwable.class, () -> {
            JsonDeserializer.deserialize(json, Payload0.class);
        });
    }

    @Test
    public void test5() throws Throwable {
        /**
         * Ok. A diferencia de Payload0, Payload1 define la propiedad X como
         * opcional.
         */
        String json = "{A: \"1\"}";
        Payload1 payload = JsonDeserializer.deserialize(json, Payload1.class);
        Assertions.assertEquals("1", payload.A);
        Assertions.assertEquals(null, payload.X);
    }

    @Test
    public void test6() throws Throwable {
        /**
         * Ok. Por mas que X sea una propiedad opcional, se sigue validando su
         * tipo.
         */
        String json = "{A: \"1\", X: false}";
        Assertions.assertThrows(Throwable.class, () -> {
            JsonDeserializer.deserialize(json, Payload1.class);
        });
    }

    @Test
    public void test7() throws Throwable {
        /**
         * Ok. Se pueden modificar el nombre esperado en JSON y el atributo
         * concreto a inicializar en Java.
         */
        String json = "{x: 100}";
        Payload2 payload = JsonDeserializer.deserialize(json, Payload2.class);
        Assertions.assertEquals((Integer) 100, payload.nombreNadaQueVer);
    }

    @Test
    public void test9() throws Throwable {
        /**
         * Ok. Se pueden anidar objetos complejos.
         */
        String json = "{usuario: {id: 1, username: \"admin\"}}";
        Payload3 payload = JsonDeserializer.deserialize(json, Payload3.class);

        Assertions.assertTrue(payload.usuario != null);
        Assertions.assertEquals((Integer) 1, payload.usuario.id);
        Assertions.assertEquals("admin", payload.usuario.username);

        // Se siguen las mismas reglas de resolucion para propiedades mandatorias y opcionales.
        Assertions.assertTrue(payload.perfil == null);
    }

    @Test
    public void test10() throws Throwable {
        /**
         * Ok. Se pueden anidar objetos complejos.
         */
        String json = "{usuario: {id: 1, username: \"admin\"}, perfil: {id: 1, descripcion:\"p1\"}}";
        Payload3 payload = JsonDeserializer.deserialize(json, Payload3.class);

        Assertions.assertTrue(payload.usuario != null);
        Assertions.assertEquals((Integer) 1, payload.usuario.id);
        Assertions.assertEquals("admin", payload.usuario.username);

        Assertions.assertTrue(payload.perfil != null);
        Assertions.assertEquals((Integer) 1, payload.perfil.id);
        Assertions.assertEquals("p1", payload.perfil.descripcion);
    }

    @Test
    public void test11() throws Throwable {
        /**
         * Ok. Se pueden utilizar arrays.
         */
        String json = "{array: []}";
        Payload4 payload = JsonDeserializer.deserialize(json, Payload4.class);
        Assertions.assertTrue(payload.array != null);
        Assertions.assertTrue(payload.array.isEmpty());
    }

    @Test
    public void test12() throws Throwable {
        /**
         * Ok. Se pueden utilizar arrays.
         */
        String json = "{array: [1,2,3]}";
        Payload4 payload = JsonDeserializer.deserialize(json, Payload4.class);
        Assertions.assertTrue(payload.array != null);
        Assertions.assertEquals((int) 3, payload.array.size());
    }

    @Test
    public void test13() throws Throwable {
        /**
         * Debe lanzar una excepcion ya que solo se permiten arrays con tipos
         * homogeneos.
         */
        String json = "{array: [1,false,3]}";
        Assertions.assertThrows(Throwable.class, () -> {
            JsonDeserializer.deserialize(json, Payload4.class);
        });
    }

    @Test
    public void test14() throws Throwable {
        /**
         * Ok.
         */
        String json = "{usuario: {id: 1, username: \"admin\"}, perfiles: [{id: 1, descripcion:\"p1\"}]}";
        Payload5 payload = JsonDeserializer.deserialize(json, Payload5.class);

        Assertions.assertTrue(payload.usuario != null);
        Assertions.assertEquals((Integer) 1, payload.usuario.id);
        Assertions.assertEquals("admin", payload.usuario.username);

        Assertions.assertTrue(payload.perfiles != null);
        Assertions.assertEquals((int) 1, payload.perfiles.size());

        Perfil perfil = Lists.first(payload.perfiles);
        Assertions.assertTrue(perfil != null);
        Assertions.assertEquals((Integer) 1, perfil.id);
        Assertions.assertEquals("p1", perfil.descripcion);
    }

    @Test
    public void test15() throws Throwable {
        /**
         * Ok. Por defecto, las propiedades opcionales de tipo array tienen
         * valor nulo al igual que las propiedades escalares.
         */
        String json = "{usuario: {id: 1, username: \"admin\"}}";
        Payload6 payload = JsonDeserializer.deserialize(json, Payload6.class);

        Assertions.assertTrue(payload.usuario != null);
        Assertions.assertEquals((Integer) 1, payload.usuario.id);
        Assertions.assertEquals("admin", payload.usuario.username);

        Assertions.assertTrue(payload.perfiles == null);
    }

    @Test
    public void test16() throws Throwable {
        /**
         * Ok. Se puede especificar un valor alternativo para propiedades
         * opcionales mediante un metodo anotado con JsonPostConstruct. Este
         * metodo es llamado posterior a la deserializacion de los objetos.
         */
        String json = "{usuario: {id: 1, username: \"admin\"}}";
        Payload7 payload = JsonDeserializer.deserialize(json, Payload7.class);

        Assertions.assertTrue(payload.usuario != null);
        Assertions.assertEquals((Integer) 1, payload.usuario.id);
        Assertions.assertEquals("admin", payload.usuario.username);

        Assertions.assertTrue(payload.perfiles != null);
        Assertions.assertEquals((int) 0, payload.perfiles.size());
        Assertions.assertEquals(true, payload.postConstruct);

        // La instanciacion normal no llama al metodo de post-construccion.
        payload = new Payload7();
        Assertions.assertEquals(false, payload.postConstruct);
    }

    @Test
    public void test17() throws Throwable {
        /**
         * Ok. Se pueden especificar adaptadores que convierten un valor de
         * entrada a otro valor concreto mediante 'fromJsonAdapter'.
         */
        String json = "{usuario: {id: 1, username: \"admin\"}, tipo: \"U1\"}";
        Payload8 payload = JsonDeserializer.deserialize(json, Payload8.class);

        Assertions.assertTrue(payload.usuario != null);
        Assertions.assertEquals((Integer) 1, payload.usuario.id);
        Assertions.assertEquals("admin", payload.usuario.username);

        Assertions.assertTrue(payload.tipo != null);
        Assertions.assertEquals(TipoUsuario.TIPO1, payload.tipo);
    }

    @Test
    public void test18() throws Throwable {
        /**
         * Debe lanzar una excepcion, ya que el convertidor espera un elemento
         * de tipo cadena para la propiedad tipo.
         */
        String json = "{usuario: {id: 1, username: \"admin\"}, tipo: false}";
        Assertions.assertThrows(Throwable.class, () -> {
            JsonDeserializer.deserialize(json, Payload8.class);
        });
    }

    @Test
    public void test19() throws Throwable {
        /**
         * Debe lanzar una excepcion, ya que se respetan las propiedades
         * heredadas.
         */
        String json = "{password: \"123\"}";
        Assertions.assertThrows(Throwable.class, () -> {
            JsonDeserializer.deserialize(json, Payload9.class);
        });
    }

    @Test
    public void test20() throws Throwable {
        /**
         * Debe lanzar una excepcion, ya que las propiedades heredadas se siguen
         * validando.
         */
        String json = "{id: 1, username: \"admin\", password: \"123\"}";
        Payload9 payload = JsonDeserializer.deserialize(json, Payload9.class);
        Assertions.assertTrue(payload != null);
        Assertions.assertEquals((Integer) 1, payload.id);
        Assertions.assertEquals("admin", payload.username);
        Assertions.assertEquals("123", payload.password);
    }

}
