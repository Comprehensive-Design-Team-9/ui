package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
	
	public void add(String title, String source, String content, String percent, String logo) {
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
		
	     Runtime runtime = Runtime.getRuntime();
	     try {
			Process p1 = 
			 runtime.exec("C:\\Users\\HWH\\Desktop\\Text_analysis_module-divide_module\\text_analysis_module_divide.py");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		int a;
		a = Integer.parseInt(viral_input.getText());
		
		ObservableList<Table> current_list = tableView.getItems();
		ObservableList<Table> current_list1;

		previous_list = tableView.getItems();
		current_list1 = current_list.filtered(t -> Integer.parseInt(t.getPercent().getValue()) >= a);

		tableView.setItems(current_list1);
	}
	
	public void cancel_viral_input() {
		tableView.setItems(original_list);
		except_logo.setSelected(false);
		viral_input.setText("");
	}

	//열기 버튼을 선택했을때의 처리부분
	public void selOpenFile(ActionEvent e) {
		//파일추저를 선언합니다.
		FileChooser fileChooser = new FileChooser();
		
		//선언한 파일 추저에서 표시한 옵션을 넣어 줍니다.
		//이또한 정형화된 표현 입니다. 약간의 수정후 그대로 사용하시면 됩니다.
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
	        
	        try{
	            br = Files.newBufferedReader(Paths.get(file.getPath()));
	            String line = "";
	            
	            while((line = br.readLine()) != null){
	                //CSV 1행을 저장하는 리스트
	                List<String> tmpList = new ArrayList<String>();
	                String array[] = line.split(",");
	                String num1 = array[0];
	                String num2 = array[1];
	                String num3 = array[2];
	                String num4 = array[3];
	                String num5 = array[4];
	                
	                add(num1, num2, num3, num4, num5);
	                //배열에서 리스트 반환
	                tmpList = Arrays.asList(array);
	                String str = String.join(", ", tmpList); 
	                
	                ret.add(tmpList);
	            }
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