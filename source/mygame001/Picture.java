package eth;

import java.math.BigDecimal;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;


public class Picture {
	
	private  final SimpleStringProperty level;
	private  final SimpleStringProperty thema;
	private  final SimpleStringProperty name_de;
	private  final SimpleStringProperty name_en;
	private  final SimpleStringProperty sort_view;
	private  final int sort_galery;

	public Picture(String level,  String thema,  String name_de, String name_en,
			    String sort_view, int sort_galery) {
		this.level = new SimpleStringProperty(level);
		this.thema = new SimpleStringProperty(thema);
		this.name_de = new SimpleStringProperty(name_de);
		this.name_en = new SimpleStringProperty(name_en);
		this.sort_view = new SimpleStringProperty(sort_view);
		this.sort_galery = sort_galery;
	}
	
	public String getLevel() {
		return this.level.get();
	}
	public String getThema() {
		return this.thema.get();
	}
	public String getName_de() {
		return this.name_de.get();
	}
	public String getName_en() {
		return this.name_en.get();
	}
	public String getSort_view() {
		return this.sort_view.get();
	}
	public int getSortGalery() {
		return this.sort_galery;
	}
	
	public static Label getPictureName(String level, String thema, String sprache){
		Label pictureName = new Label("noName");
		boolean gefunden = false;
		for (int i=0; i<MyGame.pictureList.size() && !gefunden; i++){
			if (MyGame.pictureList.get(i).getLevel().equals(level)
					&& MyGame.pictureList.get(i).getThema().equals(thema)){
				gefunden = true;
				if (sprache == "en"){
					pictureName = new Label(MyGame.pictureList.get(i).getName_en());
				}else{
					pictureName = new Label(MyGame.pictureList.get(i).getName_de());
				}
			}
		}
		return pictureName;
	}
	
}
