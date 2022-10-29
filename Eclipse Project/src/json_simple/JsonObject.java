package json_simple;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

/** JsonObject is a common non-thread safe data format for string to data mappings. The contents of a JsonObject are
 * only validated as JSON values on serialization. Meaning all values added to a JsonObject must be recognized by the
 * Jsoner for it to be a true 'JsonObject', so it is really a JsonableHashMap that will serialize to a JsonObject if all
 * of its contents are valid JSON.
 * @see Jsoner
 * @since 2.0.0 */
public class JsonObject extends HashMap<String, Object> implements Jsonable{
	/** The serialization version this class is compatible with. This value doesn't need to be incremented if and only
	 * if the only changes to occur were updating comments, updating javadocs, adding new fields to the class, changing
	 * the fields from static to non-static, or changing the fields from transient to non transient. All other changes
	 * require this number be incremented. */
	private static final long serialVersionUID = 2L;

	/** Instantiates an empty JsonObject. */
	public JsonObject(){
		super();
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
		/* Writes the map in JSON object format. */
		boolean isFirstEntry = true;
		final Iterator<Map.Entry<String, Object>> entries = this.entrySet().iterator();
		writable.write('{');
		while(entries.hasNext()){
			if(isFirstEntry){
				isFirstEntry = false;
			}else{
				writable.write(',');
			}
			final Map.Entry<String, Object> entry = entries.next();
			Jsoner.serialize(entry.getKey(), writable);
			writable.write(':');
			Jsoner.serialize(entry.getValue(), writable);
		}
		writable.write('}');
	}
}
