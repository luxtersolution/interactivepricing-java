package com.luxter.interprice.instruments.FX;

import com.luxter.interprice.instruments.Instrument;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class FX extends Instrument {

    protected final StringProperty underlying;

    public static abstract class Builder<T extends FX, B extends FX.Builder>
            extends Instrument.Builder<T, B> {
        protected String underlying = "EURUSD";

        public B underlying( String underlying ) {
            this.underlying = underlying;
            return self();
        }
    }

    public String getUnderlying() {
        return underlying.get();
    }

    public StringProperty underlyingProperty() {
        return underlying;
    }

    public void setUnderlying(String underlying) {
        this.underlying.set(underlying);
    }


    protected FX( Builder builder ) {
        this.underlying = new SimpleStringProperty( builder.underlying );
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nUnderlying:" + getUnderlying();
    }
}
