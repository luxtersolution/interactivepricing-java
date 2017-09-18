package com.luxter.interprice.instruments.FX;

import com.google.protobuf.Any;
import interactivepricing.Service;
import javafx.beans.property.*;

import static com.luxter.interprice.Utils.makeDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Spot extends FX {
    private final ObjectProperty<LocalDate> settlementDate;
    private final DoubleProperty baseNotional;
    private final DoubleProperty termNotional;

    public static class Builder extends FX.Builder<Spot, Spot.Builder> {
        private LocalDate settlementDate = LocalDate.of(2016, 06, 27);
        private double baseNotional = 10000;
        private double termNotional = 11775;

        public Builder settlementDate( LocalDate settlementDate ) {
            this.settlementDate = settlementDate;
            return self();
        }
        public Builder baseNotional( double notional ) {
            this.baseNotional = notional;
            return self();
        }
        public Builder termNotional( double notional ) {
            this.termNotional = notional;
            return self();
        }

        public Spot build() {
            return new Spot( this );
        }
        public Builder self() { return this; }
    }

    public static Builder builder(){ return new Builder();}

    private Spot( Builder builder ) {
        super( builder );
        this.settlementDate = new SimpleObjectProperty<>( builder.settlementDate );
        this.baseNotional = new SimpleDoubleProperty( builder.baseNotional );
        this.termNotional = new SimpleDoubleProperty( builder.termNotional );

        this.baseInstrument = Service.InstrumentFxSpot.newBuilder()
                                                        .setUnderlying( this.getUnderlying() )
                                                        .setSettlementDate(makeDate( settlementDate.get().format( DateTimeFormatter.ofPattern("yyyy/MM/dd")) ))
                                                        .setBaseNotional(baseNotional.get())
                                                        .setTermNotional(termNotional.get())
                                                        .build();

        this.request = Service.ValuationRequest.newBuilder()
                                                        .setContext(context)
                                                        .setInstrument(Any.pack(baseInstrument))
                                                        .addAllRiskMeasures( Arrays.asList( "npv", "delta"))
                                                        .build();
    }

    @Override
    public String toString() {
        return String.format("\n\tSpot with:\n\tUnderlying:%s\n\tSettlement Date:%s\n\tBase Notional:%f\n\tTerm Notional:%f", underlying.get(), settlementDate.get(), baseNotional.get(), termNotional.get());
    }

}
