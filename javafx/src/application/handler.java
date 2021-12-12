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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
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

public class handler implements Initializable {
	
	@FXML private TextArea ta;
	@FXML private TextField file_title;
	@FXML private TextField viral_input;
	@FXML private TableView<Table> tableView;
	@FXML private CheckBox except_logo;
	@FXML private CheckBox except_logo1;
	@FXML private Button set_button;
	@FXML private Button cancel_viral_input;
	@FXML private Button view_detail;
	@FXML private Button button;

	@FXML private TableColumn<Table, String> t_num;
	@FXML private TableColumn<Table, String> t_title;
	@FXML private TableColumn<Table, String> t_source;
	@FXML private TableColumn<Table, String> t_content;
	@FXML private TableColumn<Table, String> t_percent;
	@FXML private TableColumn<Table, String> t_logo;
	
	private Stage primaryStage;
	
	private ObservableList<Table> original_list = FXCollections.observableArrayList();
	public ObservableList<Table> previous_list = FXCollections.observableArrayList();
	public ObservableList<Table> list = FXCollections.observableArrayList();
	
	List<String> total_content = new ArrayList<String>();
	List<String> popup_table_data = new ArrayList<String>();
	
	public void add(String num, String source, String title, String content, String logo, String percent) {
		Table a = new Table(new SimpleStringProperty(num), new SimpleStringProperty(title), new SimpleStringProperty(source),new SimpleStringProperty(content), new SimpleStringProperty(logo), new SimpleStringProperty(percent));
		list.add(a);
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		t_num.setCellValueFactory(cellData -> cellData.getValue().getNum());
		t_title.setCellValueFactory(cellData -> cellData.getValue().getTitle());
		t_source.setCellValueFactory(cellData -> cellData.getValue().getSource());
		t_content.setCellValueFactory(cellData -> cellData.getValue().getContent());
		t_logo.setCellValueFactory(cellData -> cellData.getValue().getLogo());
		t_percent.setCellValueFactory(cellData -> cellData.getValue().getPercent());
		
		tableView.setItems(null);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		customiseFactory(t_percent);
		
		
	}
	
	private void customiseFactory(TableColumn<Table, String> calltypel) {
	    calltypel.setCellFactory(column -> {
	        return new TableCell<Table, String>() {
	            @Override
	            protected void updateItem(String item, boolean empty) {
	                super.updateItem(item, empty);

	                setText(empty ? "" : getItem().toString());
	                setGraphic(null);

	                TableRow<Table> currentRow = getTableRow();

	                if (!isEmpty()) {

	                    if(item.equals("2")) {
	                        currentRow.setStyle("-fx-background-color:lightgreen");
	                    } else if(item.equals("0")){
	                        currentRow.setStyle("-fx-background-color:transparent");
	                    } else {
	                    	currentRow.setStyle("-fx-background-color:lightcoral");
	                    }
	                }
	            }
	        };
	    });
	}
	
	public void view_detail_button() throws IOException {
		
		String data = "";
		String title = "";
		int num;
		String detail_data = "";
		
		Stage primaryStage = new Stage();
		primaryStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/popup.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
        popup popup = loader.getController();
        
		Table table = tableView.getSelectionModel().getSelectedItem();
		data += table.getContent().get();
		title += table.getSource().get();
		num = Integer.parseInt(table.getNum().get());
		detail_data = popup_table_data.get(num);
		
        popup.initData(data,title,detail_data);
        
        primaryStage.setScene(scene);
        primaryStage.show();
	   
	}
	
	public void button() {
		String command = "C:\\Users\\HWH\\Anaconda3\\envs\\Desktop\\python.exe";  // 명령어
		String arg1 = "C:\\Users\\HWH\\Desktop\\sum.py"; // 인자
		ProcessBuilder builder = new ProcessBuilder(command, arg1);
		Process process;
		try {
			process = builder.start();
			int exitVal = process.waitFor();  
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "euc-kr")); // 서브 프로세스가 출력하는 내용을 받기 위해
			String line;
			while ((line = br.readLine()) != null) {
			     System.out.println(">>>  " + line);
			}
			if(exitVal != 0) {
			 
			  System.out.println("서브 프로세스가 비정상 종료되었다.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
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
	
	public void except_button1() {
		if(except_logo1.isSelected() == true) {
			ObservableList<Table> except_list = tableView.getItems();
			ObservableList<Table> except_list1;

			previous_list = tableView.getItems();
			except_list1 = except_list.filtered(t -> t.getPercent().getValue().equals("0") || t.getPercent().getValue().equals("2"));
	
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
		current_list1 = current_list.filtered(t -> Float.parseFloat(t.getPercent().getValue()) == a);

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
	        CSVReader csvReader_sub = new CSVReader(new FileReader("C:\\Users\\HWH\\Desktop\\submission.csv"));

	        List<String[]> readAll = csvReader.readAll();
	        List<String[]> readAll_sub = csvReader_sub.readAll();
	        
	        List<String> table_sub = new ArrayList();
	        
	        int i = 0;
	        
	        for(String[] line : readAll_sub) {
	        	if(i == 0) {
	        		i++;
	        		continue;
	        	}
	        	String n0 = line[0]; // num
	        	String n1 = line[1]; // source
	        	String n2 = line[2]; // tmi_words
	        	String n3 = line[3]; // sim_sen
	        	String n4 = line[4]; // commission words
	        	String n5 = line[5]; // commission image
	        	String n6 = line[6]; // image_sim
	        	String n7 = line[7]; // class
	        	String n8;
	        	int a = Integer.parseInt(n4);
	        	int b = Integer.parseInt(n5);
	        	if(a == 1 || b == 1) { n8 = "1"; }
	        	else { n8 = "0"; }
	        
	        	i++;

	        	try {
		        	String result_popup = n2+"#"+n3+"#"+n6;
		        	String result_sub = n0+"#"+n7+"#"+n8;
		        	popup_table_data.add(result_popup);
		        	table_sub.add(result_sub);
	        	} catch(NumberFormatException error) {
	        		System.out.println("error");
	        	}
	        }
	        i = 0;
	        for(String[] line : readAll) {
	        	if(i == 0) {
	        		i++;
	        		continue;
	        	}
	        	String num4 = line[0];
	        	String num0 = line[1];
	        	String num1 = line[2];
	        	String num2 = line[3];
	        	
	        	total_content.add(line[3]);
	        	
	        	num2 = num2.replaceAll("\\s", " ");
	        	num2 = num2.replaceAll("   +", "\n");
	        	
	        	for(String line2 : table_sub) {
	        		String[] compare = line2.split("#");
	        	
	        		if(num4.equals(compare[0])) {
	        			add(compare[0],num0, num1, num2, compare[2], compare[1]);
	        			
	        			
	        			break;
	        		}
	        	}
	        	i++;
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