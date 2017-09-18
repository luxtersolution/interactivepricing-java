package com.luxter.interprice.instruments.FX;

import com.luxter.interprice.instruments.InstrumentController;
import com.luxter.interprice.instruments.Instrument;
import com.luxter.interprice.instruments.PostInitable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

import static com.luxter.interprice.Utils.getConverter;
import static com.luxter.interprice.Utils.getCurrenciesFromUnderlying;

public class SingleBarrierOptionController extends FXController implements InstrumentController, PostInitable {
    public DatePicker settlementDate;
    public DatePicker expiryDate;
    public TextField notional;
    public ComboBox notionalCurrency;
    public ComboBox barrierDirection;
    public TextField barrier;
    public ComboBox settlementType;
    public ComboBox optionType;
    public TextField strike;
    public ComboBox barrierType;
    public TextField rebate;
    public ComboBox rebateCurrency;
    public ComboBox rebateTime;

    @FXML
    private void initialize() {
        settlementDate.setValue( LocalDate.of(2016, 06, 27) );
        settlementDate.setConverter(getConverter());
        expiryDate.setValue( LocalDate.of(2017, 06, 27) );
        expiryDate.setConverter(getConverter());
        settlementType.getSelectionModel().selectFirst();
        barrierDirection.getSelectionModel().selectFirst();
        optionType.getSelectionModel().selectFirst();
        barrierType.getSelectionModel().selectFirst();
        rebateTime.getSelectionModel().selectFirst();
    }

    @Override
    public void postinit() {
        notionalCurrency.setItems(getCurrenciesFromUnderlying(underlying.getValue().toString()));
        notionalCurrency.getSelectionModel().selectFirst();
        rebateCurrency.setItems(getCurrenciesFromUnderlying(underlying.getValue().toString()));
        rebateCurrency.getSelectionModel().selectFirst();
        underlying.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            notionalCurrency.setItems(getCurrenciesFromUnderlying(newValue.toString()));
            notionalCurrency.getSelectionModel().selectFirst();
            rebateCurrency.setItems(getCurrenciesFromUnderlying(newValue.toString()));
            rebateCurrency.getSelectionModel().selectFirst();
        });
    }

    @Override
    public Instrument getInstrument() {
        return SingleBarrierOption.builder()
                .underlying(underlying.getSelectionModel().getSelectedItem().toString())
                .settlementDate(settlementDate.getValue())
                .expiryDate(expiryDate.getValue())
                .settlementType( settlementType.getValue().toString())
                .notional(Double.parseDouble(notional.getText()))
                .notionalCurrency(notionalCurrency.getValue().toString())
                .optionType(optionType.getValue().toString())
                .strike(Double.parseDouble(strike.getText()))
                .barrierDirection(barrierDirection.getValue().toString().toLowerCase())
                .barrier(Double.parseDouble(barrier.getText()))
                .barrierType(barrierType.getValue().toString().toLowerCase())
                .rebateCurrency(rebateCurrency.getValue().toString())
                .rebate(Double.parseDouble(rebate.getText()))
                .rebateTime( "Pay"+rebateTime.getValue().toString().replaceAll("\\s", ""))
                .build();
    }
}
