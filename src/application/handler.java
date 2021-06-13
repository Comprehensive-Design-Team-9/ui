package application;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class handler implements Initializable {
	
	@FXML private TextArea ta;
	@FXML private TextField file_title;
	@FXML private TextField viral_input;
	@FXML private TableView<Table> tableView;
	@FXML private CheckBox except_logo;
	@FXML private Button set_button;
	@FXML private Button cancel_viral_input;

	@FXML private TableColumn<Table, String> t_title;
	@FXML private TableColumn<Table, String> t_source;
	@FXML private TableColumn<Table, String> t_content;
	@FXML private TableColumn<Table, String> t_percent;
	@FXML private TableColumn<Table, String> t_logo;
	
	private Stage primaryStage;
	
	private ObservableList<Table> original_list = FXCollections.observableArrayList();
	public ObservableList<Table> previous_list = FXCollections.observableArrayList();
	public ObservableList<Table> list = FXCollections.observableArrayList();
	
	public void add(String source, String title, String content, String percent, String logo) {
		Table a = new Table(new SimpleStringProperty(title), new SimpleStringProperty(source),new SimpleStringProperty(content), new SimpleStringProperty(percent), new SimpleStringProperty(logo));
		list.add(a);
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		t_title.setCellValueFactory(cellData -> cellData.getValue().getTitle());
		t_source.setCellValueFactory(cellData -> cellData.getValue().getSource());
		t_content.setCellValueFactory(cellData -> cellData.getValue().getContent());
		t_percent.setCellValueFactory(cellData -> cellData.getValue().getPercent());
		t_logo.setCellValueFactory(cellData -> cellData.getValue().getLogo());
		
		tableView.setItems(null);
	
	}
	
	
	public void except_button() {
		if(except_logo.isSelected() == true) {
			ObservableList<Table> except_list = tableView.getItems();
			ObservableList<Table> except_list1;

			previous_list = tableView.getItems();
			except_list1 = except_list.filtered(t -> t.getLogo().getValue().equals("0"));
	
			tableView.setItems(except_list1);
		}
		else {
			tableView.setItems(previous_list);
		}
	}
	
	public void viral_input() {
		float a;
		a = Float.parseFloat(viral_input.getText());
		
		ObservableList<Table> current_list = tableView.getItems();
		ObservableList<Table> current_list1;

		previous_list = tableView.getItems();
		current_list1 = current_list.filtered(t -> Float.parseFloat(t.getPercent().getValue()) <= a);

		tableView.setItems(current_list1);
	}
	
	public void cancel_viral_input() {
		tableView.setItems(original_list);
		except_logo.setSelected(false);
		viral_input.setText("");
	}

	//열기 버튼을 선택했을때의 처리부분
	public void selOpenFile(ActionEvent e) throws IOException {
		//파일추저를 선언합니다.
		FileChooser fileChooser = new FileChooser();
		
		//선언한 파일 추저에서 표시한 옵션을 넣어 줍니다.
		 fileChooser.setTitle("Open Resource File");
		 fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Text Files", "*.txt", "*.csv"),
		         new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
		         new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
		         new ExtensionFilter("All Files", "*.*"));
		
		//현재 화면에 띄울수 없으므로 위에서 선언한 stage를 사용한다.
		File file = fileChooser.showOpenDialog(primaryStage);
		
		//아무것도 선택하지 않고 취소를 하면  else를 실행한다.
		if (file != null) {
			file_title.setText(file.getPath());

			List<List<String>> ret = new ArrayList<List<String>>();
	        BufferedReader br = null;
	        
	        CSVReader csvReader = new CSVReader(new FileReader(file.getPath()));
	        CSVReader csvReader_sub = new CSVReader(new FileReader("C:\\Users\\HWH\\Desktop\\post_pred2.csv"));

	        List<String[]> readAll = csvReader.readAll();
	        List<String[]> readAll_sub = csvReader_sub.readAll();
	        
	        List<String> table_sub = new ArrayList();
	        
	        for(String[] line : readAll_sub) {
	        	String n0 = line[0];
	        	String n1 = line[1];
	        	String n2 = line[2];
	        	String n3 = line[3];
	        	String n4 = line[4];
	        	String n5 = line[5];
	        	String n6 = line[6];
	        	String n7 = line[7];
	        	String n8 = line[8];
	        	String n9 = line[9];
	        	String n10 = line[10];
	        	
	        	try {
		        	Float f4, f5, f6, f7, f8, f9, f10, result;
		        	f4 = Float.parseFloat(n4);
		        	f5 = Float.parseFloat(n5);
		        	f6 = Float.parseFloat(n6);
		        	f7 = Float.parseFloat(n7);
		        	f8 = Float.parseFloat(n8);
		        	f9 = Float.parseFloat(n9);
		        	f10 = Float.parseFloat(n10);
		        	
		        	result = f8*25 + f9*25 + f10*25 + (f4 + f5/readAll_sub.size() + f6 + f7);
		        	String result_sub = n0+"#"+n1+"#"+result.toString();
		        	table_sub.add(result_sub);
	        	} catch(NumberFormatException error) {
	        		System.out.println("error");
	        	}
	        }
	        
	        for(String[] line : readAll) {
	        	String num0 = line[0];
	        	String num1 = line[1];
	        	String num2 = line[2];

	        	num2 = num2.replaceAll("(\r\n|\r|\n|\n\r)", " ");
	        	
	        	if(num2.length() > 40)
	        	num2 = num2.substring(0, 40);
	        	
	        	String num3 = line[3];
	        	String num4 = line[4];
	        	
	        	for(String line2 : table_sub) {
	        		String[] compare = line2.split("#");
	        		System.out.println(compare[0]);
	        		if(num0.equals(compare[0])) {
	        			add(num0, num1, num2, compare[2], compare[1]);
	        			break;
	        		}
	        	}
	        }
	        
	        try{
	            br = Files.newBufferedReader(Paths.get(file.getPath()));
	            String line = "";
	           
	            original_list = list;
				tableView.setItems(list);
	        }catch(FileNotFoundException error){
	            error.printStackTrace();
	        }catch(IOException error){
	            error.printStackTrace();
	        }finally{	
	            try{
	                if(br != null){
	                    br.close();
	                }
	            }catch(IOException error){
	                error.printStackTrace();
	            }
	        }
		}else {
			ta.setText("아무것도 지정하지 않았습니다.");
		}
	}
	

}