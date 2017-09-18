package com.luxter.interprice;

import com.luxter.interprice.instruments.FX.FXController;
import com.luxter.interprice.instruments.InstrumentController;
import com.luxter.interprice.instruments.PostInitable;
import interactivepricing.Service;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.luxter.interprice.Utils.getConverter;

public class PricerController {

    public DatePicker sysdatepicker;
    public TextField npv;
    public TextField delta;
    public TextField vega;
    public TextField volga;
    public TextField vanna;
    public TextField gamma;
    public TextArea output;
    private PrintStream ps;
    private InstrumentController instrument;

    static ObservableList<String> underlyings = FXCollections.observableArrayList( "EURUSD", "AUDUSD", "GBPUSD",  "USDSGD", "USDJPY" );
    static Map<String, TextField> results = new HashMap<>();
    static List<String> postinitables = Arrays.asList("OneTouch", "NoTouch", "SingleBarrierOption", "EuropeanVanillaOption", "DoubleBarrierOption", "DoubleOneTouch", "DoubleNoTouch");

    @FXML
    private AnchorPane instrumentFields;

    @FXML
    private void initialize() {
        results.put("npv", npv);
        results.put("delta", delta);
        results.put("gamma", gamma);
        results.put("vega", vega);
        results.put("volga", volga);
        results.put("vanna", vanna);

        StringConverter converter = getConverter();
        sysdatepicker.setValue( LocalDate.now() );
        sysdatepicker.setConverter(converter);
        ps = new PrintStream(new Console(output));
    }

    public void setInstrument(String instrument, String instrumentClass) {
        System.out.println("Setting instrument:"+instrument);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource( String.format("/instruments/%s/%s.fxml", instrumentClass, instrument )) );
            Parent root = loader.load();
            instrumentFields.getChildren().add( root );
            this.instrument = loader.getController();
            if( instrumentClass.equals("FX")) {
                ((FXController) this.instrument).setUnderlyings( underlyings );
                if( postinitables.contains( instrument )) {
                    ((PostInitable) this.instrument).postinit();
                }
                System.out.println( "Ctrl underlyings:"+ ((FXController) this.instrument).underlying.getItems());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calculate() {
        // Output messages to textarea under Calculate button
        output.clear();
        System.setOut(ps);
        System.setErr(ps);

        for( TextField tf: results.values()) {
            tf.clear();
        }
        String sysdate = sysdatepicker.getValue().format(DateTimeFormatter.ofPattern( "yyyy/MM/dd" ));
        Service.ValuationResponse response = instrument.getInstrument().calculate();
        System.out.println( "Instrument before calc:" + instrument.getInstrument() );

        int i = 0;
        for (String measure: instrument.getInstrument().request.getRiskMeasuresList() ) {
            String result = response.getRiskMeasures(i++)+"";
            System.out.println(measure + ":" + result);
            results.get(measure).setText( result );
        }
    }

    public class Console extends OutputStream {
        private TextArea console;

        public Console(TextArea console) {
            this.console = console;
        }

        public void appendText(String valueOf) {
            Platform.runLater(() -> console.appendText(valueOf));
        }

        public void write(int b) throws IOException {
            appendText(String.valueOf((char)b));
        }
    }

}
