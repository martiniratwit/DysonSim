package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.util.Stack;

public class View extends Application implements EventHandler<ActionEvent> {

    private final Sphere sun = sun();
    private final Sphere mercury = mercury();
    private final Sphere milkyWay = milkyWay();

    private final Button addSatellite = addSatellite();

    Group animationGroup = new Group(milkyWayIV(), new Group(mercury, sun));

    @Override
    public void handle(ActionEvent t) {
        Box satellite = satellite();
        animationGroup.getChildren().add(satellite);

        AnimationTimer Rotation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                satellite.rotateProperty().set(satellite.getRotate() - 0.025);
                satellite.setRotate(satellite.getRotate() - 0.1);

            }
        };
        Rotation.start();
    }

    private Sphere sun() {
        Sphere sun = new Sphere(100);

        PhongMaterial photosphereMaterial = new PhongMaterial();
        Image sunTexture = new Image(getClass().getResourceAsStream("/resources/Photosphere Texture.jpg"));
        photosphereMaterial.setDiffuseMap(sunTexture); //Initialize Texture to Material
        photosphereMaterial.setSelfIlluminationMap(sunTexture); //Initialize Illumination to Material
        sun.setMaterial(photosphereMaterial); //Add material to Sun

        sun.setRotationAxis(Rotate.Y_AXIS);

        return sun;

    }

    private Sphere mercury() {
        Sphere mercury = new Sphere(10);

        PhongMaterial mercuryMaterial = new PhongMaterial();
        Image mercuryTexture = new Image(getClass().getResourceAsStream("/resources/Mercury Texture.jpg"));
        mercuryMaterial.setDiffuseMap(mercuryTexture); //Initialize Texture to Material
        mercuryMaterial.setSelfIlluminationMap(mercuryTexture); //Initialize Illumination to Material
        mercury.setMaterial(mercuryMaterial); //Add material to Mercury


        mercury.getTransforms().add(new Translate(0,-200,100));
        mercury.setRotationAxis(Rotate.Z_AXIS);

        return mercury;

    }

    private Sphere milkyWay() {
        Sphere milkyWay = new Sphere(1000);
        milkyWay.setCullFace(CullFace.NONE); //Remove culling to texture faces inside sphere

        PhongMaterial milkyWayMaterial = new PhongMaterial();
        Image milkyWayTexture = new Image(getClass().getResourceAsStream("/resources/Milky Way Texture.jpg"));
        milkyWayMaterial.setDiffuseMap(milkyWayTexture); //Initialize Texture to Material
        milkyWayMaterial.setSelfIlluminationMap(milkyWayTexture); //Initialize Illumination to Material
        milkyWay.setMaterial(milkyWayMaterial); //Add material to Mercury

        return milkyWay;
    }

    private ImageView milkyWayIV(){
        Image milkyWayTexture = new Image(getClass().getResourceAsStream("/resources/Milky Way Texture.jpg"));
        ImageView milkyWayImageView = new ImageView(milkyWayTexture);
        milkyWayImageView.setPreserveRatio(true);
        milkyWayImageView.getTransforms().add(new Translate(-milkyWayTexture.getWidth()/2,-milkyWayTexture.getHeight()/2, 2000));
        return milkyWayImageView;
    }

    private Button addSatellite() {
        Button button = new Button();
        button.setLayoutX(-150);
        button.setLayoutX(200);
        button.setText("Add Satellite");
        button.setOnAction(this);


        return button;

    }

    private Box satellite() {
        Box satellite = new Box(10,10,1);

        satellite.getTransforms().add(new Translate(0,-130,100));
        satellite.setRotationAxis(Rotate.Z_AXIS);

        return satellite;
    }

    private void Roation() {

        AnimationTimer Rotation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                mercury.rotateProperty().set(mercury.getRotate() - 0.025);
                mercury.setRotate(mercury.getRotate() - 0.1);

                sun.rotateProperty().set(sun.getRotate() + 0.025);

            }
        };
        Rotation.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        HBox pane = new HBox();

        Pane animationPane = new Pane();
        SubScene subScene = new SubScene(animationGroup, 1000,700);
        animationPane.getChildren().add(subScene);

        StackPane inputPane = new StackPane();
        inputPane.getChildren().add(addSatellite);
        inputPane.setPrefWidth(200);

        pane.getChildren().addAll(animationPane, inputPane);
        HBox.setHgrow(animationPane,Priority.ALWAYS);

        Scene scene = new Scene(pane);

        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(10000);
        subScene.setCamera(camera);
        camera.translateZProperty().set(-1000);

        primaryStage.setTitle("Dyson Sim");
        primaryStage.setScene(scene);
        primaryStage.show();

        subScene.heightProperty().bind(animationPane.heightProperty());
        subScene.widthProperty().bind(animationPane.widthProperty());

        Roation();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
