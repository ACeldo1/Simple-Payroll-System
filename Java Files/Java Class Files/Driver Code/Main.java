package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
		
	Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try {
			
			setRootStage(primaryStage);
			Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Payroll System");
			primaryStage.setScene(scene);
			primaryStage.setMinWidth(1090);
//			primaryStage.setMaxWidth(1080);
			primaryStage.setMinHeight(700);
			
			primaryStage.setMaxWidth(1200);
			primaryStage.setMaxHeight(700);
			
//			primaryStage.setMaxHeight(700);
			//primaryStage.setResizable(false);
			primaryStage.show();
			
			/*used for running just the application without the login/signup*/
			//BorderPane root = new BorderPane();
//			Parent root = FXMLLoader.load(getClass().getResource("SceneVersion1.fxml"));
//			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
//			primaryStage.setTitle("Testing SceneVersion1");
//			primaryStage.setScene(scene);
//			primaryStage.setMinWidth(800);
//			primaryStage.setMinHeight(600);
//			//primaryStage.setResizable(false);
//			primaryStage.show();
			
		} catch(Exception e) {
		
			e.printStackTrace();
	
		}
	
	}
	
	public void setRootStage(Stage stage) { //don't really use this
	
		this.stage = stage;
	
	}
	
	public Stage getRootStage(){ //don't really use this
	
		return this.stage;
	
	}
	
	public static void main(String[] args) {
	
		launch(args);
	
	}
	
}
