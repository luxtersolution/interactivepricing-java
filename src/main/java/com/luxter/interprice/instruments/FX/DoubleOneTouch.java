package com.luxter.interprice.instruments.FX;

import com.google.protobuf.Any;
import interactivepricing.Service;
import javafx.beans.property.*;

import static com.luxter.interprice.Utils.makeDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class DoubleOneTouch extends FX {
    private final ObjectProperty<LocalDate> settlementDate;
    private final ObjectProperty<LocalDate> expiryDate;
    private final DoubleProperty notional;
    private final StringProperty notionalCurrency;
    private final StringProperty paymentTime;
    private final DoubleProperty barrierLow;
    private final DoubleProperty barrierHigh;

    public static class Builder extends FX.Builder<DoubleOneTouch, DoubleOneTouch.Builder> {
        private LocalDate settlementDate = LocalDate.of(2016, 06, 27);
        private LocalDate expiryDate = LocalDate.of(2017, 06, 27);
        private double notional = 10000;
        private String notionalCurrency = "EUR";
        private String paymentTime = "PayAtExpiry";
        private double barrierLow = 1.10;
        private double barrierHigh = 1.30;

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
        public Builder paymentTime( String paymentTime ) {
            this.paymentTime = paymentTime;
            return self();
        }
        public Builder barrierLow( double barrierLow ) {
            this.barrierLow = barrierLow;
            return self();
        }
        public Builder barrierHigh( double barrierHigh ) {
            this.barrierHigh = barrierHigh;
            return self();
        }

        public DoubleOneTouch build() {
            return new DoubleOneTouch( this );
        }
        public Builder self() { return this; }
    }

    public static Builder builder(){ return new Builder();}

    private DoubleOneTouch( Builder builder ) {
        super( builder );
        this.settlementDate = new SimpleObjectProperty<>( builder.settlementDate );
        this.expiryDate = new SimpleObjectProperty<>( builder.expiryDate );
        this.notional = new SimpleDoubleProperty( builder.notional );
        this.notionalCurrency = new SimpleStringProperty( builder.notionalCurrency );
        this.paymentTime = new SimpleStringProperty( builder.paymentTime );
        this.barrierLow = new SimpleDoubleProperty( builder.barrierLow );
        this.barrierHigh = new SimpleDoubleProperty( builder.barrierHigh );

        this.baseInstrument = Service.InstrumentFxDoubleOneTouch.newBuilder()
                                                        .setUnderlying( this.getUnderlying() )
                                                        .setSettlementDate(makeDate( this.settlementDate.get().format( DateTimeFormatter.ofPattern("yyyy/MM/dd")) ))
                                                        .setExpiryDate(makeDate( this.expiryDate.get().format( DateTimeFormatter.ofPattern("yyyy/MM/dd")) ))
                                                        .setPaymentTime(this.paymentTime.get())
                                                        .setNotional(this.notional.get())
                                                        .setNotionalCurrency(this.notionalCurrency.get())
                                                        .setBarrierLow(this.barrierLow.get())
                                                        .setBarrierHigh(this.barrierHigh.get())
                                                        .build();

        this.request = Service.ValuationRequest.newBuilder()
                                                        .setContext(context)
                                                        .setInstrument(Any.pack(baseInstrument))
                                                        .addAllRiskMeasures( Arrays.asList( "npv", "delta", "gamma", "vega", "volga", "vanna"))
                                                        .build();
    }

    @Override
    public String toString() {
        return String.format("\n\tDoubleOneTouch with:" +
                        "\n\tUnderlying:%s" +
                        "\n\tSettlement Date:%s" +
                        "\n\tNotional:%f" +
                        "\n\tNotional Currency:%s" +
                        "\n\tPayment Time:%s" +
                        "\n\tBarrierLow:%f" +
                        "\n\tBarrierHigh:%f",
                underlying.get(), settlementDate.get(), notional.get(), notionalCurrency.get(), paymentTime.get(), barrierLow.get(), barrierHigh.get() );
    }
}
