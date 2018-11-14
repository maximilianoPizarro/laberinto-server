package com.rdp.laberinto_server;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;

/**
 * Hello world!
 *
 */
public class App extends Application {

	private boolean firstTime;
	private TrayIcon trayIcon;
	

	@Override
	public void start(Stage stage) throws Exception {

		

		 
		Parent root = FXMLLoader.load(getClass().getResource("/views/index.fxml"));
		
		//Font.loadFont(getClass().getResource("/views/bastrap3/fonts/CHANEWEI.TTF").toExternalForm(), 10);
		
		firstTime = true;
		Platform.setImplicitExit(true);
		Scene scene = new Scene(root, 600, 800);
		stage.setTitle("UNLa");
		stage.setMaxWidth(800);
		stage.setMaxHeight(400);
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}




}
