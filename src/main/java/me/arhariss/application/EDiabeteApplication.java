package me.arhariss.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SpringBootApplication
public class EDiabeteApplication extends Application {

	public static ConfigurableApplicationContext springContext;
	private Parent rootNode;

	public static void main(String[] args) {
		// SpringApplication.run(EDiabeteApplication.class, args);
		launch(args);
	}

	@Override
	public void init() throws Exception {

		springContext = SpringApplication.run(EDiabeteApplication.class);
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("views/splashScreen.fxml"));
		springContext.getAutowireCapableBeanFactory().autowireBean(this);
		fxmlLoader.setControllerFactory(springContext::getBean);
		rootNode = fxmlLoader.load();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(rootNode, 600, 400);

		stage.setTitle("e-Diabete");
		stage.setScene(scene);
//		scene.getStylesheets().add("/Resources/login.css");
		stage.getIcons().add(new Image("/resources/favicon.svg"));

		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.centerOnScreen();
		stage.show();
	}

	@Override
	public void stop() throws Exception {
		springContext.close();
		Platform.exit();
	}
}
