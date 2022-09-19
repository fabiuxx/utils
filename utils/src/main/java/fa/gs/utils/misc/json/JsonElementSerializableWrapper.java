/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

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

    public static JsonElement unwrap(JsonElementSerializableWrapper instance) {
        if (instance == null) {
            return JsonNull.INSTANCE;
        }

        if (instance.getJsonElement() == null) {
            return JsonNull.INSTANCE;
        }

        return instance.getJsonElement();
    }

    public JsonElement getJsonElement() {
        return wrapped;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof JsonElementSerializableWrapper)) {
            return false;
        }

        JsonElementSerializableWrapper self = this;
        JsonElementSerializableWrapper other = (JsonElementSerializableWrapper) o;

        boolean a = isEmpty(self);
        boolean b = isEmpty(other);
        if (a || b && a != b) {
            return false;
        }

        return Json.equals(self.wrapped, other.wrapped);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.wrapped);
        return hash;
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
