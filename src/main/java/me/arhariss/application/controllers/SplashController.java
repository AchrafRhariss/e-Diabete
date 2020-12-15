package me.arhariss.application.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import static com.sun.jna.platform.win32.WinUser.GWL_STYLE;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

@Component
public class SplashController implements Initializable {

	
	@FXML
	private ImageView associationLogo;
	
	@FXML
	private Line progress;
	
	@Autowired
	private ConfigurableApplicationContext springContext;
	
	
	public ConfigurableApplicationContext getSpringContext() {
		return springContext;
	}

	public void setSpringContext(ConfigurableApplicationContext springContext) {
		this.springContext = springContext;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		associationLogo.setImage(new Image("/resources/logoAssociation.png", false));
		Duration cycleDuration = Duration.millis(5000);
	    Timeline timeline = new Timeline(
	            new KeyFrame(cycleDuration,
	                    new KeyValue(progress.endXProperty(),145,Interpolator.EASE_BOTH))
	            );

	    timeline.play();
		new SplashScreen().start();
	}

	class SplashScreen extends Thread {

		@Override
		public void run() {
			try {
				Thread.sleep(5000);
				Platform.runLater(() -> {
					Parent root = null;
					try {
						FXMLLoader fxmlLoader = new FXMLLoader(
								getClass().getResource("../views/Welcome.fxml"));
						fxmlLoader.setControllerFactory(springContext::getBean);
						root = fxmlLoader.load();
						Stage stage = new Stage();
						Scene scene = new Scene(root,1200,670);
						stage.setScene(scene);
						associationLogo.getScene().getWindow().hide();
						stage.centerOnScreen();
						stage.setResizable(false);
						stage.initStyle(StageStyle.UNDECORATED);
						scene.getStylesheets().add("/resources/application.css");
						stage.getIcons().add(new Image("/resources/logo.png"));
						stage.setTitle("e-Diabete");
						stage.show();
						//Native windows minimize/maximize window from icon in the task bar using JNA library (StackOverflow)
						long lhwnd = com.sun.glass.ui.Window.getWindows().get(0).getNativeWindow();
				        Pointer lpVoid = new Pointer(lhwnd);
				        WinDef.HWND hwnd = new WinDef.HWND(lpVoid);
				        final User32 user32 = User32.INSTANCE;
				        int oldStyle = user32.GetWindowLong(hwnd, GWL_STYLE);
				        int newStyle = oldStyle | 0x00020000;//WS_MINIMIZEBOX
				        user32.SetWindowLong(hwnd, GWL_STYLE, newStyle);
					} catch(Exception e) {
						e.printStackTrace();
					}
						
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
