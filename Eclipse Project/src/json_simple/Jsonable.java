package json_simple;

import java.io.IOException;
import java.io.Writer;

/** Jsonables can be serialized in java script object notation (JSON). Deserializing a String produced by a Jsonable
 * should represent the Jsonable in JSON form.
 * @since 2.0.0 */
public interface Jsonable{
	/** Serialize to a JSON formatted string.
	 * @return a string, formatted in JSON, that represents the Jsonable. */
	public String toJson();

	/** Serialize to a JSON formatted stream.
	 * @param writable where the resulting JSON text should be sent.
	 * @throws IOException when the writable encounters an I/O error. */
	public void toJson(Writer writable) throws IOException;
}
