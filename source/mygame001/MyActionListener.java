package eth;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MyActionListener implements EventHandler<ActionEvent> {
	private Integer id = 0;

	public MyActionListener(Integer id)
	{
	    this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

