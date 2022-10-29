package json_simple;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/** JsonArray is a common non-thread safe data format for a collection of data. The contents of a JsonArray are only
 * validated as JSON values on serialization. Meaning all values added to a JsonArray must be recognized by the Jsoner
 * for it to be a true 'JsonArray', so it is really a JsonableArrayList that will serialize to a JsonArray if all of
 * its contents are valid JSON.
 * @see Jsoner
 * @since 2.0.0 */
public class JsonArray extends ArrayList<Object> implements Jsonable{
	/** The serialization version this class is compatible with. This value doesn't need to be incremented if and only
	 * if the only changes to occur were updating comments, updating javadocs, adding new fields to the class, changing
	 * the fields from static to non-static, or changing the fields from transient to non transient. All other changes
	 * require this number be incremented. */
	private static final long serialVersionUID = 1L;

	/** Instantiates an empty JsonArray. */
	public JsonArray(){
		super();
	}

	/** Instantiate a new JsonArray using ArrayList's constructor of the same type.
	 * @param collection represents the elements to produce the JsonArray with. */
	public JsonArray(final Collection<?> collection){
		super(collection);
	}

	/* (non-Javadoc)
	 * @see org.json.simple.Jsonable#toJson() */
	@Override
	public String toJson(){
		final StringWriter writable = new StringWriter();
		try{
			this.toJson(writable);
		}catch(final IOException caught){
			/* See java.io.StringWriter. */
		}
		return writable.toString();
	}

	/* (non-Javadoc)
	 * @see org.json.simple.Jsonable#toJson(java.io.Writer) */
	@Override
	public void toJson(final Writer writable) throws IOException{
		boolean isFirstElement = true;
		final Iterator<Object> elements = this.iterator();
		writable.write('[');
		while(elements.hasNext()){
			if(isFirstElement){
				isFirstElement = false;
			}else{
				writable.write(',');
			}
			Jsoner.serialize(elements.next(), writable);
		}
		writable.write(']');
	}
}
