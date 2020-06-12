/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.result.utils;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo para valor almacenado.
 */
class Value_Builder<T> extends Value_Attributes<T> implements Value.Builder<T> {

    /**
     * Constructor.
     */
    public Value_Builder() {
        this.value = null;
        this.strict = true;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Deprecated
    public Value.Builder<T> nullable(boolean nullable) {
        if (nullable) {
            strict(false);
        } else {
            strict(true);
        }
        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Value.Builder<T> strict(boolean strict) {
        this.strict = strict;
        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Value.Builder<T> value(T value) {
        this.value = value;
        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Value<T> build() {
        return new Value<>(value, strict);
    }

}
