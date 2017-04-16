package adwcn6xmlparser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button buttonChooseFile;
    
    @FXML
    private void chooseFile(ActionEvent event) {
        // Build fileChooser dialog
        Stage dialog = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XML Files", "*.xml"));
        
        // Show fileChooser dialog, parse file if not null
        File selectedFile = fileChooser.showOpenDialog(dialog);
        
        try {
            if (selectedFile != null) {
                XMLNode resultingDOM = XMLParser.parse(selectedFile);
                System.out.println("DOM is stored in memory as resultingDOM");
            }   
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}