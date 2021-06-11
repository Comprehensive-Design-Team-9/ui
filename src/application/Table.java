package application;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Table {

    private StringProperty title;
    private StringProperty source;
    private StringProperty content;
    private StringProperty percent;
    private StringProperty logo;


    public Table(StringProperty title, StringProperty source, StringProperty content, StringProperty percent, StringProperty logo) {
    	super();
        this.title = title;
        this.source = source;
        this.content = content;
        this.percent = percent;
        this.logo = logo;
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
}