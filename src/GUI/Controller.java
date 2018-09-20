package GUI;

import Simulation.LevitronSimulation;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public AnchorPane anchorPane;

    @FXML
    private ImageView magnetImageView;

    @FXML
    private ImageView spinningTopImageView;

    @FXML
    private ImageView airImageView;

    @FXML
    private ImageView aboutImageView;

    @FXML
    private Pane spinningTopPane;

    @FXML
    public JFXSlider dipoleValueSlider;

    @FXML
    public JFXSlider spinningTopMassSlider;

    @FXML
    public JFXSlider angluarVelocitySlider;

    @FXML
    public JFXTextField positionXTextField;

    @FXML
    public JFXTextField positionYTextField;

    @FXML
    public JFXTextField positionZTextField;

    @FXML
    public JFXSlider spinningTopLengthSlider;

    @FXML
    public JFXSlider spinningTopRadiusSlider;

    @FXML
    private Pane magnetPane;

    @FXML
    public JFXSlider baseRadiusSlider;

    @FXML
    public JFXSlider electricCurrentScalingSlider;

    @FXML
    private Pane airPane;

    @FXML
    public JFXSlider linearDragSlider;

    @FXML
    public JFXSlider rotationalDampingConstant;

    @FXML
    public JFXSlider magnusCoefficientSlider;

    @FXML
    public JFXCheckBox magneticFieldCheckBox;

    private Mouse mouse = new Mouse();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        magnetImageView.setImage(new Image("/GUI/GUIRES/magnet.png"));
        spinningTopImageView.setImage(new Image("/GUI/GUIRES/spinningtop.png"));
        airImageView.setImage(new Image("/GUI/GUIRES/air.png"));
        aboutImageView.setImage(new Image("/GUI/GUIRES/questionmark.png"));
        magnetImageView.setOnMouseClicked(event -> {
            magnetPane.setVisible(true);
            airPane.setVisible(false);
            spinningTopPane.setVisible(false);
        });
        spinningTopImageView.setOnMouseClicked(event -> {
            magnetPane.setVisible(false);
            airPane.setVisible(false);
            spinningTopPane.setVisible(true);
        });
        airImageView.setOnMouseClicked(event -> {
            magnetPane.setVisible(false);
            spinningTopPane.setVisible(false);
            airPane.setVisible(true);
        });
        initFields();
        anchorPane.setOnMousePressed(t -> {
            mouse.setX(t.getX());
            mouse.setY(t.getY());
        });
        anchorPane.setOnMouseDragged(t -> {
            anchorPane.getScene().getWindow().setX(t.getScreenX() - mouse.getX());
            anchorPane.getScene().getWindow().setY(t.getScreenY() - mouse.getY());
        });
    }

    public void exit() {
        System.exit(0);
    }

    public void startSimulationMethod() {
        setupValues();
        MainScreen.mainStage.close();
        LevitronSimulation.startSimulation();
    }

    private void setupValues() {
        SimulationValues.dipoleValue = (float) dipoleValueSlider.getValue();
        SimulationValues.spinningTopMass = (float) spinningTopMassSlider.getValue();
        SimulationValues.angularVelocity = (float) angluarVelocitySlider.getValue();
        try {
            SimulationValues.positionX = Float.valueOf(positionXTextField.getText());
        } catch (NumberFormatException e) {
            if (!positionXTextField.getText().equals(""))
                positionXTextField.setPromptText("Please Enter a valid position");
        }
        positionXTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            positionXTextField.setPromptText("Position X");
        });
        try {
            SimulationValues.positionY = Float.valueOf(positionYTextField.getText());
        } catch (NumberFormatException e) {
            if (!positionXTextField.getText().equals(""))
                positionXTextField.setPromptText("Please Enter a valid position");
        }
        positionYTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            positionYTextField.setPromptText("Position Y");
        });
        try {
            SimulationValues.positionZ = Float.valueOf(positionZTextField.getText());
        } catch (NumberFormatException e) {
            if (!positionZTextField.getText().equals(""))
                positionZTextField.setPromptText("Please Enter a valid position");
        }
        positionZTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            positionZTextField.setPromptText("Position Z");
        });
        SimulationValues.spinningTopLength = (float) spinningTopLengthSlider.getValue();
        SimulationValues.spinningTopRadius = (float) spinningTopRadiusSlider.getValue();
        if (magneticFieldCheckBox.isSelected()) {
            SimulationValues.baseRadius = (float) baseRadiusSlider.getValue();
            SimulationValues.magneticBaseForce = (float) electricCurrentScalingSlider.getValue();
        } else {
            SimulationValues.baseRadius = 0;
            SimulationValues.magneticBaseForce = 0;
        }
        SimulationValues.spinningTopMass = (float) spinningTopMassSlider.getValue() / 100f;
        SimulationValues.rotationalDamping = (float) rotationalDampingConstant.getValue();
    }

    private void initFields() {
        electricCurrentScalingSlider.setValue(SimulationValues.magneticBaseForce);
        baseRadiusSlider.setValue(SimulationValues.baseRadius);
        spinningTopMassSlider.setValue(SimulationValues.spinningTopMass);
        spinningTopRadiusSlider.setValue(SimulationValues.spinningTopRadius);
        spinningTopLengthSlider.setValue(SimulationValues.spinningTopLength);
        dipoleValueSlider.setValue(SimulationValues.dipoleValue);
        angluarVelocitySlider.setValue(SimulationValues.angularVelocity);
        rotationalDampingConstant.setValue(SimulationValues.rotationalDamping);
        linearDragSlider.setValue(SimulationValues.linearDrag);
        magnusCoefficientSlider.setValue(SimulationValues.magnusCoefficient);
    }

    public void disableMagnets() {
        if (magneticFieldCheckBox.isSelected()) {
            electricCurrentScalingSlider.setDisable(false);
            baseRadiusSlider.setDisable(false);
        } else {
            electricCurrentScalingSlider.setDisable(true);
            baseRadiusSlider.setDisable(true);
        }
    }
}
