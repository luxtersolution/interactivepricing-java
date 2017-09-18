package com.luxter.interprice.instruments;

import interactivepricing.Service;
import interactivepricing.ValuerGrpc;
import interactivepricing.ValuerGrpc.ValuerBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.beans.property.StringProperty;

import static com.luxter.interprice.Utils.makeDate;

public abstract class Instrument {
    protected Instrument() {
    }

    protected static abstract class Builder<T extends Instrument, B extends Builder> {
        public Builder() {
        }
        protected abstract T build();
        protected abstract B self();
    }

    @Override
    public String toString() {
        return "Instrument";
    }

    static String host = "interactivepricing.com";
    static int port = 8093;
    static ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true);
    static ManagedChannel channel = channelBuilder.build();

    protected static ValuerBlockingStub blockingStub = ValuerGrpc.newBlockingStub(channel);
    protected static Service.ValuationContext context = Service.ValuationContext.newBuilder()
            .setValuationDate(makeDate("2016/06/25"))
            .setVolatility(0.07)
            .setFxSpot(1.13)
            .setDomesticRate(0.05)
            .setForeignRate(0.025)
            .build();

    protected com.google.protobuf.GeneratedMessageV3 baseInstrument;
    public Service.ValuationRequest request;
    public Service.ValuationResponse calculate(){
        return blockingStub.value(request);
    }
}

