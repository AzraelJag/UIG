package eth;

import java.math.BigDecimal;

import javafx.beans.property.SimpleStringProperty;

public class Item {

	private final SimpleStringProperty id;
	private final SimpleStringProperty name;
	private final BigDecimal costFiat;
	private final BigDecimal costUIG;
	private final Integer wuerfelpos;
	private final Integer level;
	private final Integer position;
	private final Integer wertigkeit;
	private BigDecimal count;

	public Item(String id,    String name,  String costFiat, String costUIG,
			    String level, String position, String wertigkeit) {
		this.id = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);
		this.costFiat = new BigDecimal(costFiat);
		this.costUIG = new BigDecimal(costUIG);
		this.wuerfelpos = Integer.parseInt(costUIG);
		this.count = new BigDecimal(1.0);
		this.level = new Integer(level);
		this.position = new Integer(position);
		this.wertigkeit = new Integer(wertigkeit);
	}

	public String getId() {
		return this.id.get();
	}

	public String getName() {
		return this.name.get();
	}

	public BigDecimal getCostFiat() {
		return this.costFiat;
	}

	public BigDecimal getCostUIG() {
		return this.costUIG;
	}
	
	public void setCount(BigDecimal count) {
		this.count = count;
	}

	
	public Integer getWuerfelpos() {
		return this.wuerfelpos;
	}


	public void addCount(BigDecimal count) {
		//System.out.println("Anzahl-Vorher : " + this.count);
		this.count = this.count.add(count);
		//System.out.println("Anzahl-Nachher: " + this.count);

	}

	public void subCount(BigDecimal count) {
		this.count = this.count.subtract(count);

	}

	public BigDecimal getCount() {
		return this.count;
	}
	
	public Integer getLevel() {
		return this.level;
	}
	
	public Integer getPosition() {
		return this.position;
	}
	
	public Integer getWertigkeit() {
		return this.wertigkeit;
	}
	
	public static String getItemLevelThema (String itemid){
		String LevelThema = new String();
		boolean gefunden = false;
		for (int i=0; i<MyGame.items.size() && !gefunden; i++){
			if (MyGame.items.get(i).getId().
					equals(itemid)){
				LevelThema  = Integer.toString(MyGame.items.get(i).getLevel());
				LevelThema += Integer.toString(MyGame.items.get(i).getWertigkeit());
				gefunden = true;
			}
		}
		return LevelThema;
	}

}
