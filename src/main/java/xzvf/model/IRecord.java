package xzvf.model;

import java.io.Serializable;
import java.util.Date;

public interface IRecord extends Serializable {
	
	Integer getId();
	void setId(Integer id);
	
	String getEntryBy();
	void setEntryBy(String entryBy);
	
	Date getEntryDate();
	void setEntryDate(Date entryDate);

	String getDisplayString();
	
}
