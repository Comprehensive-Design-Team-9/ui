package application;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Popup;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;

public class popup implements Initializable {
	  @FXML private Hyperlink content;
	  @FXML private TextArea content1;
	  @FXML private TextArea tmi_words;
	  @FXML private TextArea similar_sentence;
	  @FXML private TextArea image_similarity;
	  @FXML private ImageView show;
	  @FXML private Pagination pagination;
	  
	  String source1, source2;
	  
	  private final ChangeListener<Number> paginationChangeListener = (observable, oldValue, newValue) -> paginationShow(source1, source2);
	 
	  @Override
	  public void initialize(URL location, ResourceBundle resources){
	  }
	  
	  public void initData(String cell_content, String source, String detail_data) {

	    content.setText(source);
	   
	    content.setOnAction(e -> {
	        if(Desktop.isDesktopSupported())
	        {
	            try {
	                Desktop.getDesktop().browse(new URI(content.getText()));
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            } catch (URISyntaxException e1) {
	                e1.printStackTrace();
	            }
	        }
	    });
        
	    content1.setText(cell_content);
	    source1 = source.split("/")[3];
	    source2 = source.split("/")[4];
	    
	    String t_words = detail_data.split("#")[0];;
	    String sim_sen = detail_data.split("#")[1];
	    String image_sim = detail_data.split("#")[2];;

	    tmi_words.setText(t_words);
	    similar_sentence.setText(sim_sen);
	    image_similarity.setText(image_sim);
	    
	    File file = new File("C:\\Users\\HWH\\Desktop\\naver_blog_post_img\\naver_blog_post_img\\https_\\blog.naver.com" + "\\" + source1 + "\\" + source2 +"\\"+"1.jpg");
	    Image image = new Image(file.toURI().toString());
	    show.setImage(image);
	    
	    pagination.currentPageIndexProperty().addListener(paginationChangeListener);
	   
	  }
	  
	  public void paginationShow(String source1, String source2) {
		  IntegerProperty index = pagination.currentPageIndexProperty();
		  File file = new File("C:\\Users\\HWH\\Desktop\\naver_blog_post_img\\naver_blog_post_img\\https_\\blog.naver.com" + "\\" + source1 + "\\" + source2 +"\\"+(index.intValue()+1)+".jpg");
		  Image image = new Image(file.toURI().toString());
		  show.setImage(image);
	  }
	  
	  
	}