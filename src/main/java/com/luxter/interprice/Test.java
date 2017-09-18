package com.luxter.interprice;

import com.luxter.interprice.instruments.FX.DoubleBarrierOption;
import com.luxter.interprice.instruments.FX.SingleBarrierOption;
import com.luxter.interprice.instruments.FX.Spot;
import com.luxter.interprice.instruments.FX.Swap;
import interactivepricing.Service;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        Spot foo = Spot.builder().build();
        Service.ValuationResponse response = foo.calculate();
        System.out.println( "Instrument before calc:" + foo );
        int i = 0;
        for (String measure: foo.request.getRiskMeasuresList() ) {
            String result = response.getRiskMeasures(i++)+"";
            System.out.println(measure + ":" + result);
        }

        Swap bar = Swap.builder().build();
        response = bar.calculate();
        System.out.println( "Instrument before calc:" + bar );
        i = 0;
        for (String measure: bar.request.getRiskMeasuresList() ) {
            String result = response.getRiskMeasures(i++)+"";
            System.out.println(measure + ":" + result);
        }

        SingleBarrierOption baz = SingleBarrierOption.builder().build();
        response = baz.calculate();
        System.out.println( "Instrument before calc:" + baz );
        i = 0;
        for (String measure: baz.request.getRiskMeasuresList() ) {
            String result = response.getRiskMeasures(i++)+"";
            System.out.println(measure + ":" + result);
        }

        // Don't know why this set of params causes error
        DoubleBarrierOption qux = DoubleBarrierOption.builder()
                .underlying("AUDUSD")
                .settlementDate(LocalDate.of(2016,06,27))
                .expiryDate(LocalDate.of(2017,06,27))
                .settlementType("delivery")
                .notional(10000)
                .notionalCurrency("AUD")
                .optionType("call")
                .strike(1.0)
                .barrierLow(1.1)
                .barrierHigh(1.3)
                .barrierType("knockin")
                .rebate(0.05)
                .rebateCurrency("USD")
                .rebateTime("PayAtHit")
            .build();
        response = qux.calculate();
        System.out.println( "Instrument before calc:" + qux );
        i = 0;
        for (String measure: qux.request.getRiskMeasuresList() ) {
            String result = response.getRiskMeasures(i++)+"";
            System.out.println(measure + ":" + result);
        }
    }
}
