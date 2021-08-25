package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BjackjackApp extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("MyGUI.fxml"));
		stage.setTitle("BlackJack");
		//Image source and dimensions
		Image image = new Image("images/goahed.png");
		
		ImageView mv = new ImageView(image);
		mv.preserveRatioProperty();
		mv.setFitHeight(679);
		mv.setFitWidth(1000);
		
		Parent root = loader.load();
		
        MyController controller = loader.getController();
        controller.setGameText("");
        
        //Background set to image
        controller.setBackground(mv);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
    }  
}
