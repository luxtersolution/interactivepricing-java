package com.luxter.interprice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class InstrumentListController {
    @FXML
    private TabPane instrumentClassesTabs;
    @FXML
    private ListView<String> instrumentList;

    private Tab instrumentClass;

    @FXML
    private void initialize() {
        ObservableList<String> items = FXCollections.observableArrayList("Spot", "Forward", "Swap", "OneTouch", "NoTouch",
                "SingleBarrierOption", "EuropeanVanillaOption", "DoubleBarrierOption", "DoubleOneTouch", "DoubleNoTouch");
        instrumentList.setItems(items);
        instrumentClassesTabs.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setInstrumentClass(newValue));
        setInstrumentClass( instrumentClassesTabs.getSelectionModel().getSelectedItem() );
    }

    public void showInstrument(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2 && instrumentList.getSelectionModel().getSelectedItem() != null ) //Checking double click
        {
            System.out.println(instrumentList.getSelectionModel().getSelectedItem());
            try {
                Main.currentInstrument = instrumentList.getSelectionModel().getSelectedItem();
                String instrument = instrumentList.getSelectionModel().getSelectedItem();
                System.out.println( "instrumentClass in show instrument:"+ getInstrumentClass().getText() );
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation( getClass().getResource("/Pricer.fxml") );
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Quant library GUI:"+ Main.currentInstrument);
                stage.setScene(new Scene(root));

                stage.show();

                PricerController controller = loader.getController();
                controller.setInstrument( instrument, instrumentClass.getId() );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setInstrumentClass(Tab instrumentClass) {
        this.instrumentClass = instrumentClass;
        System.out.println("instrumentClass:"+instrumentClass.getId());
    }

    public Tab getInstrumentClass() {
        return instrumentClass;
    }

}
