package com.luxter.interprice.instruments.FX;

import com.google.protobuf.Any;
import interactivepricing.Service;
import javafx.beans.property.*;

import static com.luxter.interprice.Utils.makeDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class NoTouch extends FX {
    private final ObjectProperty<LocalDate> settlementDate;
    private final ObjectProperty<LocalDate> expiryDate;
    private final DoubleProperty notional;
    private final StringProperty notionalCurrency;
    private final StringProperty barrierDirection;
    private final DoubleProperty barrier;

    public static class Builder extends FX.Builder<NoTouch, NoTouch.Builder> {
        private LocalDate settlementDate = LocalDate.of(2016, 06, 27);
        private LocalDate expiryDate = LocalDate.of(2017, 06, 27);
        private double notional = 10000;
        private String notionalCurrency = "EUR";
        private String barrierDirection = "up";
        private double barrier = 1.30;

        public Builder settlementDate( LocalDate settlementDate ) {
            this.settlementDate = settlementDate;
            return self();
        }

        public Builder expiryDate( LocalDate expiryDate ) {
            this.expiryDate = expiryDate;
            return self();
        }
        public Builder notional( double notional ) {
            this.notional = notional;
            return self();
        }
        public Builder notionalCurrency( String notionalCurrency ) {
            this.notionalCurrency = notionalCurrency;
            return self();
        }
        public Builder barrierDirection ( String barrierDirection) {
            this.barrierDirection = barrierDirection;
            return self();
        }
        public Builder barrier ( double barrier) {
            this.barrier = barrier;
            return self();
        }

        public NoTouch build() {
            return new NoTouch( this );
        }
        public Builder self() { return this; }
    }

    public static Builder builder(){ return new Builder();}

    private NoTouch( Builder builder ) {
        super( builder );
        this.settlementDate = new SimpleObjectProperty<>( builder.settlementDate );
        this.expiryDate = new SimpleObjectProperty<>( builder.expiryDate );
        this.notional = new SimpleDoubleProperty( builder.notional );
        this.notionalCurrency = new SimpleStringProperty( builder.notionalCurrency );
        this.barrierDirection = new SimpleStringProperty( builder.barrierDirection );
        this.barrier = new SimpleDoubleProperty( builder.barrier );

        this.baseInstrument = Service.InstrumentFxNoTouch.newBuilder()
                                                        .setUnderlying( this.getUnderlying() )
                                                        .setSettlementDate(makeDate( this.settlementDate.get().format( DateTimeFormatter.ofPattern("yyyy/MM/dd")) ))
                                                        .setExpiryDate(makeDate( this.expiryDate.get().format( DateTimeFormatter.ofPattern("yyyy/MM/dd")) ))
                                                        .setNotional(this.notional.get())
                                                        .setNotionalCurrency(this.notionalCurrency.get())
                                                        .setBarrierDirection(this.barrierDirection.get())
                                                        .setBarrier(this.barrier.get())
                                                        .build();

        this.request = Service.ValuationRequest.newBuilder()
                                                        .setContext(context)
                                                        .setInstrument(Any.pack(baseInstrument))
                                                        .addAllRiskMeasures( Arrays.asList( "npv", "delta", "gamma", "vega", "volga", "vanna"))
                                                        .build();
    }

    @Override
    public String toString() {
        return String.format("\n\tNoTouch with:" +
                        "\n\tUnderlying:%s" +
                        "\n\tSettlement Date:%s" +
                        "\n\tExpiry Date:%s" +
                        "\n\tNotional:%f" +
                        "\n\tNotional Currency:%s" +
                        "\n\tBarrier Dir:%s" +
                        "\n\tBarrier:%f" ,
                underlying.get(), settlementDate.get(), expiryDate.get(), notional.get(), notionalCurrency.get(), barrierDirection.get(), barrier.get() );
    }
}
