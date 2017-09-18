package com.luxter.interprice.instruments.FX;

import com.google.protobuf.Any;
import interactivepricing.Service;
import javafx.beans.property.*;

import static com.luxter.interprice.Utils.makeDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class SingleBarrierOption extends FX {
    private final ObjectProperty<LocalDate> settlementDate;
    private final ObjectProperty<LocalDate> expiryDate;
    private final StringProperty settlementType;
    private final StringProperty optionType;
    private final DoubleProperty notional;
    private final StringProperty notionalCurrency;
    private final DoubleProperty strike;
    private final DoubleProperty barrier;
    private final StringProperty barrierType;
    private final StringProperty barrierDirection;
    private final DoubleProperty rebate;
    private final StringProperty rebateCurrency;
    private final StringProperty rebateTime;

    public static class Builder extends FX.Builder<SingleBarrierOption, SingleBarrierOption.Builder> {
        private LocalDate settlementDate = LocalDate.of(2016, 06, 27);
        private LocalDate expiryDate = LocalDate.of(2017, 06, 27);
        private String settlementType = "delivery";
        private double notional = 10000;
        private String notionalCurrency = "EUR";
        private String optionType = "call";
        private double strike = 1.15;
        private double barrier = 1.30;
        private String barrierType = "knockout";
        private String barrierDirection = "up";
        private double rebate = 0.05;
        private String rebateCurrency = "USD";
        private String rebateTime = "PayAtHit";

        public Builder settlementType(String settlementType) {
            this.settlementType = settlementType;
            return self();
        }
        public Builder optionType(String optionType) {
            this.optionType = optionType;
            return self();
        }
        public Builder strike(double strike) {
            this.strike = strike;
            return self();
        }
        public Builder barrierType(String barrierType) {
            this.barrierType = barrierType;
            return self();
        }
        public Builder rebate(double rebate) {
            this.rebate = rebate;
            return self();
        }
        public Builder rebateCurrency(String rebateCurrency) {
            this.rebateCurrency = rebateCurrency;
            return self();
        }
        public Builder rebateTime(String rebateTime) {
            this.rebateTime = rebateTime;
            return self();
        }
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

        public SingleBarrierOption build() {
            return new SingleBarrierOption( this );
        }
        public Builder self() { return this; }
    }

    public static Builder builder(){ return new Builder();}

    private SingleBarrierOption( Builder builder ) {
        super( builder );
        this.settlementDate = new SimpleObjectProperty<>( builder.settlementDate );
        this.expiryDate = new SimpleObjectProperty<>( builder.expiryDate );
        this.settlementType = new SimpleStringProperty( builder.settlementType );
        this.optionType = new SimpleStringProperty( builder.optionType );
        this.notional = new SimpleDoubleProperty( builder.notional );
        this.notionalCurrency = new SimpleStringProperty( builder.notionalCurrency );
        this.strike = new SimpleDoubleProperty( builder.strike );
        this.barrier = new SimpleDoubleProperty( builder.barrier );
        this.barrierType = new SimpleStringProperty( builder.barrierType );
        this.barrierDirection = new SimpleStringProperty( builder.barrierDirection );
        this.rebate = new SimpleDoubleProperty( builder.rebate );
        this.rebateCurrency = new SimpleStringProperty( builder.rebateCurrency );
        this.rebateTime = new SimpleStringProperty( builder.rebateTime );

        this.baseInstrument = Service.InstrumentFxSingleBarrierOption.newBuilder()
                                                        .setUnderlying( this.getUnderlying() )
                                                        .setSettlementDate(makeDate( this.settlementDate.get().format( DateTimeFormatter.ofPattern("yyyy/MM/dd")) ))
                                                        .setExpiryDate(makeDate( this.expiryDate.get().format( DateTimeFormatter.ofPattern("yyyy/MM/dd")) ))
                                                        .setSettlementType(this.settlementType.get())
                                                        .setNotional(this.notional.get())
                                                        .setNotionalCurrency(this.notionalCurrency.get())
                                                        .setOptionType(this.optionType.get())
                                                        .setStrike(this.strike.get())
                                                        .setBarrierDirection(this.barrierDirection.get())
                                                        .setBarrier(this.barrier.get())
                                                        .setBarrierType(this.barrierType.get())
                                                        .setRebateCurrency(this.rebateCurrency.get())
                                                        .setRebate(this.rebate.get())
                                                        .setRebateTime(this.rebateTime.get())
                                                        .build();

        this.request = Service.ValuationRequest.newBuilder()
                                                        .setContext(context)
                                                        .setInstrument(Any.pack(baseInstrument))
                                                        .addAllRiskMeasures( Arrays.asList( "npv", "delta", "gamma", "vega", "volga", "vanna"))
                                                        .build();
    }

    @Override
    public String toString() {
        return String.format("\n\tSingleBarrierOption with:" +
                        "\n\tUnderlying:%s" +
                        "\n\tSettlement Date:%s" +
                        "\n\tExpiry Date:%s" +
                        "\n\tSettlement Type:%s" +
                        "\n\tNotional:%f" +
                        "\n\tNotional Currency:%s" +
                        "\n\tOptionType:%s" +
                        "\n\tStrike:%f" +
                        "\n\tBarrier:%f" +
                        "\n\tBarrier Dir:%s" +
                        "\n\tBarrier Type:%s" +
                        "\n\tRebate:%f" +
                        "\n\tRebate Currency:%s" +
                        "\n\tRebate Time:%s" ,
                underlying.get(), settlementDate.get(), expiryDate.get(), settlementType.get(), notional.get(), notionalCurrency.get(),
                optionType.get(), strike.get(), barrier.get(),barrierDirection.get(), barrierType.get(),
                rebate.get(), rebateCurrency.get(), rebateTime.get() );
    }
}
