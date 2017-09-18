package com.luxter.interprice;

import com.google.protobuf.Any;
import interactivepricing.Service;
import interactivepricing.ValuerGrpc;
import interactivepricing.ValuerGrpc.ValuerBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.chrono.JulianChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Arrays;

/**
 * Created by james on 4/1/17.
 */
public class OldTest {

    public static void main(String[] args) {

        String host = "interactivepricing.com";
        int port = 8093;
        //int port = 50055;

        ManagedChannelBuilder<?> channelBuilder
                = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true);


        ManagedChannel channel = channelBuilder.build();

        ValuerBlockingStub blockingStub = ValuerGrpc.newBlockingStub(channel);

        Service.ValuationContext context = Service.ValuationContext.newBuilder()
                                                        .setValuationDate(makeDate("2016/06/25"))
                                                        .setVolatility(0.07)
                                                        .setFxSpot(1.13)
                                                        .setDomesticRate(0.05)
                                                        .setForeignRate(0.025)
                                                    .build();

        /*
        Service.InstrumentFxEuropeanVanillaOption instrument = Service.InstrumentFxEuropeanVanillaOption.newBuilder()
                                                        .setUnderlying("EURUSD")
                                                        .setExpiryDate(makeDate("2017/05/01"))
                                                        .setSettlementDate(makeDate("2017/05/03"))
                                                        .setSettlementType("delivery")
                                                        .setNotional(800000)
                                                        .setNotionalCurrency("EUR")
                                                        .setOptionType("call")
                                                        .setStrike(1.15)
                                                    .build();

        Service.ValuationRequest request = Service.ValuationRequest.newBuilder()
                                                        .setContext(context)
                                                        .setInstrument(Any.pack(instrument))
                                                        .addAllRiskMeasures( Arrays.asList( "npv",
                                                                                            "delta",
                                                                                            "gamma",
                                                                                            "vega",
                                                                                            "volga",
                                                                                            "vanna"))
                                                    .build();




        Service.ValuationResponse response = blockingStub.value(request);

        int i = 0;
        for (String measure: request.getRiskMeasuresList() ) {
            System.out.println(measure + ":" + response.getRiskMeasures(i++));
        }
        */

        valueFxSpot(context, blockingStub);
        //valueFxForward(context, blockingStub);
        //valueFxSwap(context, blockingStub);
        //valueFxEuropeanVanillaOption(context, blockingStub);
        //valueFxEuropeanDigitalOption(context, blockingStub);
        //valueFxSingleBarrierOption(context, blockingStub);
        //valueFxNoTouch(context, blockingStub);
        //valueFxOneTouch(context, blockingStub);
        //valueFxDoubleBarrierOption(context, blockingStub);
        //valueFxDoubleNoTouch(context, blockingStub);
        //valueFxDoubleOneTouch(context, blockingStub);
    }

    private static int makeDate(String s) {

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd");
        DateTime dt = formatter.parseDateTime(s);

        long jd = DateTimeUtils.toJulianDayNumber(dt.getMillis());

        return (int)jd;
    }

    private static void valueFxSpot(Service.ValuationContext context, ValuerBlockingStub blockingStub)
    {
        System.out.println(makeDate("2017/09/19"));
        Service.InstrumentFxSpot instrument = Service.InstrumentFxSpot.newBuilder()
                                                        .setUnderlying("EURUSD")
                                                        .setSettlementDate(makeDate("2017/09/18"))
                                                        .setBaseNotional(10000)
                                                        .setTermNotional(11775)
                                                        .build();

        Service.ValuationRequest request = Service.ValuationRequest.newBuilder()
                                                        .setContext(context)
                                                        .setInstrument(Any.pack(instrument))
                                                        .addAllRiskMeasures( Arrays.asList( "npv", "delta"))
                                                        .build();

        Service.ValuationResponse response = blockingStub.value(request);

        System.out.println("FxSpot");

        int i = 0;
        for (String measure: request.getRiskMeasuresList() ) {
            System.out.println(measure + ":" + response.getRiskMeasures(i++));
        }
    }

    private static void valueFxForward(Service.ValuationContext context, ValuerBlockingStub blockingStub)
    {
        Service.InstrumentFxForward instrument = Service.InstrumentFxForward.newBuilder()
                                                        .setUnderlying("EURUSD")
                                                        .setSettlementDate(makeDate("2017/06/27"))
                                                        .setBaseNotional(10000)
                                                        .setTermNotional(11500)
                                                        .build();

        Service.ValuationRequest request = Service.ValuationRequest.newBuilder()
                                                        .setContext(context)
                                                        .setInstrument(Any.pack(instrument))
                                                        .addAllRiskMeasures( Arrays.asList( "npv", "delta"))
                                                        .build();

        Service.ValuationResponse response = blockingStub.value(request);

        System.out.println("FxForward");

        int i = 0;
        for (String measure: request.getRiskMeasuresList() ) {
            System.out.println(measure + ":" + response.getRiskMeasures(i++));
        }
    }

    private static void valueFxSwap(Service.ValuationContext context, ValuerBlockingStub blockingStub)
    {
        Service.InstrumentFxSwap instrument = Service.InstrumentFxSwap.newBuilder()
                                                        .setUnderlying("EURUSD")
                                                        .setInitialSettlementDate(makeDate("2016/06/27"))
                                                        .setFinalSettlementDate(makeDate("2017/06/27"))
                                                        .setBaseNotional(10000)
                                                        .setTermNotional(11775)
                                                        .setSpread(100)
                                                        .build();

        Service.ValuationRequest request = Service.ValuationRequest.newBuilder()
                                                        .setContext(context)
                                                        .setInstrument(Any.pack(instrument))
                                                        .addAllRiskMeasures( Arrays.asList( "npv", "delta"))
                                                        .build();

        Service.ValuationResponse response = blockingStub.value(request);

        System.out.println("FxSwap");

        int i = 0;
        for (String measure: request.getRiskMeasuresList() ) {
            System.out.println(measure + ":" + response.getRiskMeasures(i++));
        }
    }

    private static void valueFxEuropeanVanillaOption(Service.ValuationContext context, ValuerBlockingStub blockingStub)
    {
        Service.InstrumentFxEuropeanVanillaOption instrument = Service.InstrumentFxEuropeanVanillaOption.newBuilder()
                                                        .setUnderlying("EURUSD")
                                                        .setExpiryDate(makeDate("2017/06/25"))
                                                        .setSettlementDate(makeDate("2017/06/27"))
                                                        .setSettlementType("delivery")
                                                        .setNotional(10000)
                                                        .setNotionalCurrency("EUR")
                                                        .setOptionType("call")
                                                        .setStrike(1.15)
                                                        .build();

        Service.ValuationRequest request = Service.ValuationRequest.newBuilder()
                                                        .setContext(context)
                                                        .setInstrument(Any.pack(instrument))
                                                        .addAllRiskMeasures( Arrays.asList( "npv", "delta", "gamma", "vega", "volga", "vanna"))
                                                        .build();

        Service.ValuationResponse response = blockingStub.value(request);

        System.out.println("FxEuropeanVanillaOption");

        int i = 0;
        for (String measure: request.getRiskMeasuresList() ) {
            System.out.println(measure + ":" + response.getRiskMeasures(i++));
        }
    }

    private static void valueFxEuropeanDigitalOption(Service.ValuationContext context, ValuerBlockingStub blockingStub)
    {
        Service.InstrumentFxEuropeanDigitalOption instrument = Service.InstrumentFxEuropeanDigitalOption.newBuilder()
                                                        .setUnderlying("EURUSD")
                                                        .setExpiryDate(makeDate("2017/06/25"))
                                                        .setSettlementDate(makeDate("2017/06/27"))
                                                        .setNotional(10000)
                                                        .setNotionalCurrency("EUR")
                                                        .setOptionType("call")
                                                        .setStrike(1.15)
                                                        .build();

        Service.ValuationRequest request = Service.ValuationRequest.newBuilder()
                                                        .setContext(context)
                                                        .setInstrument(Any.pack(instrument))
                                                        .addAllRiskMeasures( Arrays.asList( "npv", "delta", "gamma", "vega", "volga", "vanna"))
                                                        .build();

        Service.ValuationResponse response = blockingStub.value(request);

        System.out.println("FxEuropeanDigitalOption");

        int i = 0;
        for (String measure: request.getRiskMeasuresList() ) {
            System.out.println(measure + ":" + response.getRiskMeasures(i++));
        }
    }

    private static void valueFxSingleBarrierOption(Service.ValuationContext context, ValuerBlockingStub blockingStub)
    {
        Service.InstrumentFxSingleBarrierOption instrument = Service.InstrumentFxSingleBarrierOption.newBuilder()
                                                        .setUnderlying("EURUSD")
                                                        .setExpiryDate(makeDate("2017/06/25"))
                                                        .setSettlementDate(makeDate("2017/06/27"))
                                                        .setSettlementType("delivery")
                                                        .setNotional(10000)
                                                        .setNotionalCurrency("EUR")
                                                        .setOptionType("call")
                                                        .setStrike(1.15)
                                                        .setBarrierType("knockout")
                                                        .setBarrierDirection("up")
                                                        .setBarrier(1.30)
                                                        .setRebate(0.05)
                                                        .setRebateCurrency("USD")
                                                        .setRebateTime("PayAtHit")
                                                        .build();

        Service.ValuationRequest request = Service.ValuationRequest.newBuilder()
                                                        .setContext(context)
                                                        .setInstrument(Any.pack(instrument))
                                                        .addAllRiskMeasures( Arrays.asList( "npv", "delta", "gamma", "vega", "volga", "vanna"))
                                                        .build();

        Service.ValuationResponse response = blockingStub.value(request);

        System.out.println("FxSingleBarrierOption");

        int i = 0;
        for (String measure: request.getRiskMeasuresList() ) {
            System.out.println(measure + ":" + response.getRiskMeasures(i++));
        }
    }

    private static void valueFxNoTouch(Service.ValuationContext context, ValuerBlockingStub blockingStub)
    {
        Service.InstrumentFxNoTouch instrument = Service.InstrumentFxNoTouch.newBuilder()
                                                        .setUnderlying("EURUSD")
                                                        .setExpiryDate(makeDate("2017/06/25"))
                                                        .setSettlementDate(makeDate("2017/06/27"))
                                                        .setNotional(10000)
                                                        .setNotionalCurrency("EUR")
                                                        .setBarrierDirection("up")
                                                        .setBarrier(1.30)
                                                        .build();

        Service.ValuationRequest request = Service.ValuationRequest.newBuilder()
                                                        .setContext(context)
                                                        .setInstrument(Any.pack(instrument))
                                                        .addAllRiskMeasures( Arrays.asList( "npv", "delta", "gamma", "vega", "volga", "vanna"))
                                                        .build();

        Service.ValuationResponse response = blockingStub.value(request);

        System.out.println("FxNoTouch");

        int i = 0;
        for (String measure: request.getRiskMeasuresList() ) {
            System.out.println(measure + ":" + response.getRiskMeasures(i++));
        }
    }

    private static void valueFxOneTouch(Service.ValuationContext context, ValuerBlockingStub blockingStub)
    {
        Service.InstrumentFxOneTouch instrument = Service.InstrumentFxOneTouch.newBuilder()
                                                        .setUnderlying("EURUSD")
                                                        .setExpiryDate(makeDate("2017/06/25"))
                                                        .setSettlementDate(makeDate("2017/06/27"))
                                                        .setPaymentTime("PayAtExpiry")
                                                        .setNotional(10000)
                                                        .setNotionalCurrency("EUR")
                                                        .setBarrierDirection("up")
                                                        .setBarrier(1.30)
                                                        .build();

        Service.ValuationRequest request = Service.ValuationRequest.newBuilder()
                                                        .setContext(context)
                                                        .setInstrument(Any.pack(instrument))
                                                        .addAllRiskMeasures( Arrays.asList( "npv", "delta", "gamma", "vega", "volga", "vanna"))
                                                        .build();

        Service.ValuationResponse response = blockingStub.value(request);

        System.out.println("FxOneTouch");

        int i = 0;
        for (String measure: request.getRiskMeasuresList() ) {
            System.out.println(measure + ":" + response.getRiskMeasures(i++));
        }
    }

    private static void valueFxDoubleBarrierOption(Service.ValuationContext context, ValuerBlockingStub blockingStub)
    {
        Service.InstrumentFxDoubleBarrierOption instrument = Service.InstrumentFxDoubleBarrierOption.newBuilder()
                                                        .setUnderlying("AUDUSD")
                                                        .setExpiryDate(makeDate("2016/06/27"))
                                                        .setSettlementDate(makeDate("2017/06/27"))
                                                        .setSettlementType("delivery")
                                                        .setNotional(10000)
                                                        .setNotionalCurrency("AUD")
                                                        .setOptionType("call")
                                                        .setStrike(1.15)
                                                        .setBarrierType("knockout")
                                                        .setBarrierLow(1.10)
                                                        .setBarrierHigh(1.30)
                                                        .setRebate(0.05)
                                                        .setRebateCurrency("USD")
                                                        .setRebateTime("PayAtHit")
                                                        .build();

        Service.ValuationRequest request = Service.ValuationRequest.newBuilder()
                                                        .setContext(context)
                                                        .setInstrument(Any.pack(instrument))
                                                        .addAllRiskMeasures( Arrays.asList( "npv", "delta", "gamma", "vega", "volga", "vanna"))
                                                        .build();

        Service.ValuationResponse response = blockingStub.value(request);

        System.out.println("FxDoubleBarrierOption");

        int i = 0;
        for (String measure: request.getRiskMeasuresList() ) {
            System.out.println(measure + ":" + response.getRiskMeasures(i++));
        }
    }

    private static void valueFxDoubleNoTouch(Service.ValuationContext context, ValuerBlockingStub blockingStub)
    {
        Service.InstrumentFxDoubleNoTouch instrument = Service.InstrumentFxDoubleNoTouch.newBuilder()
                                                        .setUnderlying("EURUSD")
                                                        .setExpiryDate(makeDate("2017/06/25"))
                                                        .setSettlementDate(makeDate("2017/06/27"))
                                                        .setNotional(10000)
                                                        .setNotionalCurrency("EUR")
                                                        .setBarrierLow(1.10)
                                                        .setBarrierHigh(1.30)
                                                        .build();

        Service.ValuationRequest request = Service.ValuationRequest.newBuilder()
                                                        .setContext(context)
                                                        .setInstrument(Any.pack(instrument))
                                                        .addAllRiskMeasures( Arrays.asList( "npv", "delta", "gamma", "vega", "volga", "vanna"))
                                                        .build();

        Service.ValuationResponse response = blockingStub.value(request);

        System.out.println("FxDoubleNoTouch");

        int i = 0;
        for (String measure: request.getRiskMeasuresList() ) {
            System.out.println(measure + ":" + response.getRiskMeasures(i++));
        }
    }

    private static void valueFxDoubleOneTouch(Service.ValuationContext context, ValuerBlockingStub blockingStub)
    {
        Service.InstrumentFxDoubleOneTouch instrument = Service.InstrumentFxDoubleOneTouch.newBuilder()
                                                        .setUnderlying("EURUSD")
                                                        .setExpiryDate(makeDate("2017/06/25"))
                                                        .setSettlementDate(makeDate("2017/06/27"))
                                                        .setPaymentTime("PayAtExpiry")
                                                        .setNotional(10000)
                                                        .setNotionalCurrency("EUR")
                                                        .setBarrierLow(1.10)
                                                        .setBarrierHigh(1.30)
                                                        .build();

        Service.ValuationRequest request = Service.ValuationRequest.newBuilder()
                                                        .setContext(context)
                                                        .setInstrument(Any.pack(instrument))
                                                        .addAllRiskMeasures( Arrays.asList( "npv", "delta", "gamma", "vega", "volga", "vanna"))
                                                        .build();

        Service.ValuationResponse response = blockingStub.value(request);

        System.out.println("FxDoubleOneTouch");

        int i = 0;
        for (String measure: request.getRiskMeasuresList() ) {
            System.out.println(measure + ":" + response.getRiskMeasures(i++));
        }
    }

}
