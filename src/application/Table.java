package application;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Table {

	private StringProperty num;
    private StringProperty title;
    private StringProperty source;
    private StringProperty content;
    private StringProperty percent;
    private StringProperty logo;
    
    public Table() {
    	this.num = num;
        this.title = title;
        this.source = source;
        this.content = content;
        this.percent = percent;
        this.logo = logo;
    }
    
    public Table(StringProperty num, StringProperty title, StringProperty source, StringProperty content, StringProperty logo, StringProperty percent) {
    	super();
    	this.num = num;
        this.title = title;
        this.source = source;
        this.content = content;
        this.logo = logo;
        this.percent = percent;
    }

    public StringProperty getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source.set(source);
    }

    public StringProperty getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }
    
    public StringProperty getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent.set(percent);
    }
    
    public StringProperty getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo.set(logo);
    }
    
    public StringProperty getNum() {
        return num;
    }

    public void setNum(String num) {
        this.logo.set(num);
    }
    
    @Override
	public String toString() {
 
		StringBuilder str = new StringBuilder();
		str.append("num=").append(num).append(", ");
		str.append("title=").append(title).append(", ");
		str.append("source=").append(source).append(", ");
		str.append("content=").append(content).append(", ");
		str.append("percent=").append(percent).append(", ");
		str.append("logo=").append(logo);

		return str.toString();
	}

}