/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json;

import com.google.gson.JsonElement;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JsonElementSerializableWrapper implements Serializable {

    private JsonElement wrapped;

    public JsonElementSerializableWrapper() {
        this(null);
    }

    public JsonElementSerializableWrapper(JsonElement wrapped) {
        this.wrapped = wrapped;
    }

    public static JsonElementSerializableWrapper instance() {
        return new JsonElementSerializableWrapper();
    }

    public static JsonElementSerializableWrapper instance(String wrapped) {
        JsonElement wrapped0 = (wrapped == null) ? null : Json.fromString(wrapped);
        return instance(wrapped0);
    }

    public static JsonElementSerializableWrapper instance(JsonElement wrapped) {
        return new JsonElementSerializableWrapper(wrapped);
    }

    public static boolean isEmpty(JsonElementSerializableWrapper instance) {
        if (instance == null) {
            return true;
        }
        if (instance.getJsonElement() == null) {
            return true;
        }
        if (instance.getJsonElement().isJsonNull()) {
            return true;
        }
        return false;
    }

    public JsonElement getJsonElement() {
        return wrapped;
    }

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        String value = aInputStream.readUTF();
        this.wrapped = Json.fromString(value);
    }

    private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
        String value = Json.toString(wrapped);
        aOutputStream.writeUTF(value);
    }

}
