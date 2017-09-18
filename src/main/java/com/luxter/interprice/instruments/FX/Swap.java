package com.luxter.interprice.instruments.FX;

import com.google.protobuf.Any;
import interactivepricing.Service;
import javafx.beans.property.*;

import static com.luxter.interprice.Utils.makeDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Swap extends FX {
    private final ObjectProperty<LocalDate> initialSettlementDate;

    private final ObjectProperty<LocalDate> finalSettlementDate;
    private final DoubleProperty baseNotional;
    private final DoubleProperty termNotional;
    private final DoubleProperty spread;

    public static class Builder extends FX.Builder<Swap, Swap.Builder> {
        private LocalDate initialSettlementDate = LocalDate.of(2016, 06, 27);
        private LocalDate finalSettlementDate = LocalDate.of(2017, 06, 27);
        private double baseNotional = 10000;
        private double termNotional = 11775;
        private double spread = 100;

        public Builder initialSettlementDate( LocalDate initialSettlementDate) {
            this.initialSettlementDate = initialSettlementDate;
            return self();
        }
        public Builder finalSettlementDate( LocalDate finalSettlementDate) {
            this.finalSettlementDate = finalSettlementDate;
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
        public Builder spread( double spread ) {
            this.spread = spread;
            return self();
        }
        public Swap build() {
            return new Swap( this );
        }
        public Builder self() { return this; }
    }

    public static Builder builder(){ return new Builder();}

    private Swap( Builder builder ) {
        super( builder );
        this.initialSettlementDate = new SimpleObjectProperty<>( builder.initialSettlementDate);
        this.finalSettlementDate = new SimpleObjectProperty<>( builder.finalSettlementDate);
        this.baseNotional = new SimpleDoubleProperty( builder.baseNotional );
        this.termNotional = new SimpleDoubleProperty( builder.termNotional );
        this.spread = new SimpleDoubleProperty( builder.spread );
        this.baseInstrument = Service.InstrumentFxSwap.newBuilder()
                .setUnderlying( this.getUnderlying() )
                .setInitialSettlementDate(makeDate( initialSettlementDate.get().format( DateTimeFormatter.ofPattern("yyyy/MM/dd")) ))
                .setFinalSettlementDate(makeDate( finalSettlementDate.get().format( DateTimeFormatter.ofPattern("yyyy/MM/dd")) ))
                .setBaseNotional(baseNotional.get())
                .setTermNotional(termNotional.get())
                .setSpread(spread.get())
                .build();
        this.request = Service.ValuationRequest.newBuilder()
                .setContext(context)
                .setInstrument(Any.pack(baseInstrument))
                .addAllRiskMeasures( Arrays.asList( "npv", "delta"))
                .build();
    }

    @Override
    public String toString() {
        return String.format("\n\tSwap with:" +
                "\n\tUnderlying:%s" +
                "\n\tInitial Settlement Date:%s" +
                "\n\tFinal Settlement Date:%s" +
                "\n\tBase Notional:%f" +
                "\n\tTerm Notional:%f" +
                "\n\tSpread:%f",
                underlying.get(), initialSettlementDate.get(), finalSettlementDate.get(), baseNotional.get(), termNotional.get(), spread.get());
    }
}
