package eth;

import java.awt.Scrollbar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MyGalery {
	
	static Rectangle rectangle = new Rectangle(500, 500, Color.LIGHTGRAY);
	
	public static Stage getGaleryStage(ObservableList<Item> itemList) {
		//System.out.println("AnzItemsGaleryStart: " + MyGame.accountItemList.get(0).getItemList().size());
		MyGame.gewonnen = false;
		Stage galeryStage = new Stage();
		ObservableList<String> ppictures = FXCollections.observableArrayList();
		//pictures.clear();
		
		String itemLevelThema = new String();
		for (int i=0; i<itemList.size(); i++){
			//System.out.println("Galery: ItemID :" + itemList.get(i).getId() + " " + itemList.get(i).getName());
			
			itemLevelThema = Item.getItemLevelThema(itemList.get(i).getId());
			
			boolean gefunden = false;
			for (int j=0; j<ppictures.size() && !gefunden; j++){
				if (ppictures.get(j).equals(itemLevelThema))
					gefunden = true;
			}
			
			if (!gefunden && !(itemLevelThema.equals("00"))){
				ppictures.add(itemLevelThema);
			}
		}
		
		//Bilder in Galery sortieren
		ObservableList<String> picturesSort = FXCollections.observableArrayList();
		boolean ende = false;
		int k=0;
		while (!ende){
			boolean gefunden = false;
			//System.out.println("AnzBilderinGalery: " +  ppictures.size());
			for (int l=0 ; l<ppictures.size() && !gefunden ; l++){
				//System.out.println("Bild: " + ppictures.get(l) + "k-BildNr: " + k + "Bildpos: " + getBildPosition(ppictures.get(l)));
				if (k == getBildPosition(ppictures.get(l))){
					picturesSort.add(ppictures.get(l));
					gefunden = true;
				}
			}
			k++;
			if (k == ppictures.size()+2)
				ende = true;	
		}

		final ObservableList<String> pictures = picturesSort;
		
		HBox hBox = new HBox();
		//hBox.setId("galery-box");

		int auswahlThema = MyGame.thema;

		for (int i=0; i<pictures.size(); i++){
			//System.out.println("Picture: " + pictures.get(i));
			final int aktuellesBild = i;
			
			AccountItemList accItemList = new AccountItemList("Galery");
			ObservableList<Item> accPictureItems = FXCollections
					.observableArrayList();
			//accPictureItems.clear();
			
			String pitemLevelThema = new String();
			for (int t=0; t<itemList.size(); t++){
				pitemLevelThema = Item.getItemLevelThema(itemList.get(t).getId());
				//System.out.println("ItemLVTH: " + pitemLevelThema);
				if (pictures.get(i).equals(pitemLevelThema)){
						accPictureItems.add(itemList.get(t));
				}
			}
			//System.out.println("Picture-Items: ");
			//for (int t=0; t<accPictureItems.size(); t++){
				//System.out.println("ItemLVTH: " + accPictureItems.get(t).getName());
			//}
			//System.out.println("-----------------");
			accItemList.setItemList(accPictureItems);
			
			VBox picture = new VBox();
			MyGame.thema = Integer.parseInt(pictures.get(i).substring(1,2));
			picture = MyGame.getbildBox(accItemList, Integer.parseInt(pictures.get(i).substring(0,1)));
			picture.setAlignment(Pos.CENTER);
			//picture.setSpacing(10);
			picture.setPadding(new Insets(10, 150, 10, 10));
			Label bildTitel = Picture.getPictureName(pictures.get(i).substring(0,1), pictures.get(i).substring(1,2), MyGame.sprache);
	
			bildTitel.setStyle("-fx-text-fill: WHITE;");
			bildTitel.setPadding(new Insets(30, 10, 10, 10));
			hBox.setAlignment(Pos.CENTER);
			picture.getChildren().add(bildTitel);
			final VBox picture2 = picture;
			
			//Maus Effekte bei den Bildern
			picture.onMouseClickedProperty()
			.set(new EventHandler<MouseEvent>()
			        {
			          @Override
			          public void handle(MouseEvent arg0)
			          {
			        	  if (arg0.getButton() == MouseButton.SECONDARY) { 
				  			Image img = new Image("file:///" + MyGame.pfad_data_itemjpg 
				  				  + "L" + MyGame.level + MyGame.thema + "/Image.jpg");
				  			rectangle = new Rectangle(img.getWidth(), img.getHeight(), Color.LIGHTGRAY);
				  			rectangle.setFill(new ImagePattern(img));
				  			Pane pane = new Pane(rectangle);
				  			Scene scene = new Scene(pane, img.getWidth(), img.getHeight());
				  			scene.getStylesheets().add(MyGame.pfad_data_css + "mygame.css");
				  			Stage bildstage = new Stage();
				  			bildstage.setScene(scene);
				  			bildstage.showAndWait();
				  			bildstage.close();
			        	  }
			        	  else{
			        	  	  MyGame.level = Integer.parseInt(pictures.get(aktuellesBild).substring(0,1));
			        	  	  MyGame.thema = Integer.parseInt(pictures.get(aktuellesBild).substring(1,2));
			        	  	  //System.out.println("angeklickt Bild Nr.: " + MyGame.level + MyGame.thema);
			        	  	  MyGame.gewonnen = false;
			        	  	  galeryStage.close();
			        	  }
			          }
			        });
			
			picture.onMouseEnteredProperty()
			.set(new EventHandler<MouseEvent>()
			        {
			          @Override
			          public void handle(MouseEvent arg0)
			          {
			        	  MyGame.level = Integer.parseInt(pictures.get(aktuellesBild).substring(0,1));
			        	  MyGame.thema = Integer.parseInt(pictures.get(aktuellesBild).substring(1,2));
			        	  //System.out.println("reingekommen Bild Nr.: " + MyGame.level + MyGame.thema);
			        	  DropShadow dropShadow = new DropShadow();
			              dropShadow.setRadius(8.0);
			              dropShadow.setOffsetX(5.0);
			              dropShadow.setOffsetY(5.0);
			              dropShadow.setColor(Color.WHITE);
			              //dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
			              picture2.setEffect(dropShadow);
			              picture2.setCursor(Cursor.HAND);
			              
			              /*
			              rectangle = new Rectangle((MyGame.level+1)*100, (MyGame.level+1)*100, Color.LIGHTGRAY);
			  			  Image img = new Image("file:///" + MyGame.pfad_data_itemjpg 
			  					  + "L" + MyGame.level + MyGame.thema + "/Image.jpg");
			  			  rectangle.setFill(new ImagePattern(img));
			  			  picture2.getChildren().add(rectangle);
			  			  */

			          }
			        });
			
			
			picture.onMouseExitedProperty()
			.set(new EventHandler<MouseEvent>()
			        {
			          @Override
			          public void handle(MouseEvent arg0)
			          {
			        	  //rectangle = null;
			        	  MyGame.level = Integer.parseInt(pictures.get(aktuellesBild).substring(0,1));
			        	  MyGame.thema = Integer.parseInt(pictures.get(aktuellesBild).substring(1,2));
			        	  //System.out.println("rausgekommen Bild Nr.: " + MyGame.level + MyGame.thema);
			        	  picture2.setEffect(null);
			        	  picture2.setCursor(Cursor.DEFAULT);
			        	  
			        	  /*
			        	  Node bild = picture2.getChildren().get(0);
			        	  Node text = picture2.getChildren().get(1);
			        	  picture2.getChildren().clear();
			        	  picture2.getChildren().addAll(bild, text);
			        	  */
			          }
			        });
			
			
			//bildTitel.setAlignment(Pos.CENTER);
			hBox.getChildren().addAll(picture2);
		}
		MyGame.thema = auswahlThema;
		hBox.setPadding(new Insets(30, 10, 10, 30));
		ScrollPane scrollpane = new ScrollPane(hBox);
		
		Scene scene = new Scene(scrollpane, MyGame.displayX, MyGame.displayY-100);
		scene.getStylesheets().add(MyGame.pfad_data_css + "mygame.css");
		galeryStage.setScene(scene);
		galeryStage.setTitle(MyGame.accounts.get(MyGame.wuerfelAcc).getName() + " Bildergalery");
		galeryStage.alwaysOnTopProperty();
		galeryStage.setMaximized(true);
		MyGame.gewonnen = false;
		return galeryStage;
	}
	
	// Galeryposition eines Bildes ermitteln
	private static int getBildPosition(String picture) {
		int position = 0;
		String	pLevel = picture.substring(0, 1);
		String	pThema = picture.substring(1, 2);
		if (MyGame.pictureList.size() > 0){
			boolean gefunden = false;
			for (int i=0; i<MyGame.pictureList.size() && !gefunden; i++){
				if (pLevel.equals(MyGame.pictureList.get(i).getLevel()) &&
					pThema.equals(MyGame.pictureList.get(i).getThema())){
					gefunden = true;
					position = MyGame.pictureList.get(i).getSortGalery();
				}
			}
			
		}else
		{
			switch (picture){
			case "11":			//Familie
				position = 0;
				break;
			case "21":			//Ostereier
				position = 1;
				break;	
			case "22":			//Shiny Pokemon
				position = 2;
				break;
			case "31":			//Best Jens Pokemon
				position = 3;
				break;	
			case "43":			//Best Magnus Pokemon
				position = 4;
				break;
			case "42":			//Golf
				position = 5;
				break;	
			case "44":			//VW-Kraftwerk
				position = 6;
				break;	
			case "32":			//Hasselbach - Hummel
				position = 7;
				break;	
			case "34":			//Hasselbach - Wurzel
				position = 8;
				break;	
			case "33":			//Hasselbach - Blumenwiese
				position = 9;
				break;	
			case "41":			//Sonnenuntergang
				position = 10;
				break;	

			default:			//Ansonsten immer am Anfang
				position = 0;
				break;
			}
		}
		//System.out.println("Sort: " + position );
		return position;
	}
	

}
