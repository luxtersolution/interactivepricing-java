package com.luxter.interprice.instruments.FX;

import com.google.protobuf.Any;
import interactivepricing.Service;
import javafx.beans.property.*;

import static com.luxter.interprice.Utils.makeDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class EuropeanVanillaOption extends FX {
    private final ObjectProperty<LocalDate> settlementDate;
    private final ObjectProperty<LocalDate> expiryDate;
    private final StringProperty settlementType;
    private final DoubleProperty notional;
    private final StringProperty notionalCurrency;
    private final StringProperty optionType;
    private final DoubleProperty strike;

    public static class Builder extends FX.Builder<EuropeanVanillaOption, EuropeanVanillaOption.Builder> {
        private LocalDate settlementDate = LocalDate.of(2016, 06, 27);
        private LocalDate expiryDate = LocalDate.of(2017, 06, 27);
        private String settlementType = "delivery";
        private double notional = 10000;
        private String notionalCurrency = "EUR";
        private String optionType = "call";
        private double strike = 1.15;

        public Builder settlementDate( LocalDate settlementDate ) {
            this.settlementDate = settlementDate;
            return self();
        }
        public Builder expiryDate( LocalDate expiryDate ) {
            this.expiryDate = expiryDate;
            return self();
        }
        public Builder settlementType( String settlementType) {
            this.settlementType = settlementType;
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
        public Builder optionType( String optionType) {
            this.optionType = optionType;
            return self();
        }
        public Builder strike( double strike ) {
            this.strike = strike;
            return self();
        }

        public EuropeanVanillaOption build() {
            return new EuropeanVanillaOption( this );
        }
        public Builder self() { return this; }
    }

    public static Builder builder(){ return new Builder();}

    private EuropeanVanillaOption( Builder builder ) {
        super( builder );
        this.settlementDate = new SimpleObjectProperty<>( builder.settlementDate );
        this.expiryDate = new SimpleObjectProperty<>( builder.expiryDate );
        this.settlementType = new SimpleStringProperty( builder.settlementType );
        this.notional = new SimpleDoubleProperty( builder.notional );
        this.notionalCurrency = new SimpleStringProperty( builder.notionalCurrency );
        this.optionType = new SimpleStringProperty( builder.optionType );
        this.strike = new SimpleDoubleProperty( builder.strike );

        this.baseInstrument = Service.InstrumentFxEuropeanVanillaOption.newBuilder()
                                                        .setUnderlying( this.getUnderlying() )
                                                        .setSettlementDate(makeDate( this.settlementDate.get().format( DateTimeFormatter.ofPattern("yyyy/MM/dd")) ))
                                                        .setExpiryDate(makeDate( this.expiryDate.get().format( DateTimeFormatter.ofPattern("yyyy/MM/dd")) ))
                                                        .setSettlementType(this.settlementType.get())
                                                        .setNotional(this.notional.get())
                                                        .setNotionalCurrency(this.notionalCurrency.get())
                                                        .setOptionType(this.optionType.get())
                                                        .setStrike(this.strike.get())
                                                        .build();

        this.request = Service.ValuationRequest.newBuilder()
                                                        .setContext(context)
                                                        .setInstrument(Any.pack(baseInstrument))
                                                        .addAllRiskMeasures( Arrays.asList( "npv", "delta", "gamma", "vega", "volga", "vanna"))
                                                        .build();
    }

    @Override
    public String toString() {
        return String.format("\n\tEuropeanVanillaOption with:" +
                        "\n\tUnderlying:%s" +
                        "\n\tSettlement Date:%s" +
                        "\n\tExpiry Date:%s" +
                        "\n\tSettlement Type:%s" +
                        "\n\tNotional:%f" +
                        "\n\tNotional Currency:%s" +
                        "\n\tOption Type:%s" +
                        "\n\tStrike:%f" ,
                underlying.get(), settlementDate.get(), expiryDate.get(), settlementType.get(), notional.get(), notionalCurrency.get(), optionType.get(), strike.get() );
    }
}
