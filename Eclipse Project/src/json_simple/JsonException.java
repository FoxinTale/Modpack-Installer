package json_simple;

/** JsonException explains how and where the problem occurs in the source JSON text during deserialization.
 * @since 3.0.0 */
public class JsonException extends Exception{
	/** The kinds of exceptions that can trigger a JsonException. */
	public enum Problems{
		DISALLOWED_TOKEN,
		IOEXCEPTION,
		UNEXPECTED_CHARACTER,
		UNEXPECTED_EXCEPTION,
		UNEXPECTED_TOKEN;
	}

	private static final long	serialVersionUID	= 1L;
	private final long			position;
	private final Problems		problemType;
	private final Object		unexpectedObject;

	/** Instantiates a JsonException without assumptions.
	 * @param position where the exception occurred.
	 * @param problemType how the exception occurred.
	 * @param unexpectedObject what caused the exception. */
	public JsonException(final long position, final Problems problemType, final Object unexpectedObject){
		this.position = position;
		this.problemType = problemType;
		this.unexpectedObject = unexpectedObject;
		if(Problems.IOEXCEPTION.equals(problemType) || Problems.UNEXPECTED_EXCEPTION.equals(problemType)){
			if(unexpectedObject instanceof Throwable){
				this.initCause((Throwable)unexpectedObject);
			}
		}
	}

	@Override
	public String getMessage(){
		final StringBuilder sb = new StringBuilder();
		switch(this.problemType){
			case DISALLOWED_TOKEN:
				sb.append("The disallowed token (").append(this.unexpectedObject).append(") was found at position ").append(this.position).append(". If this is in error, try again with a deserialization method in Jsoner that allows the token instead. Otherwise, fix the parsable string and try again.");
				break;
			case IOEXCEPTION:
				sb.append("An IOException was encountered, ensure the reader is properly instantiated, isn't closed, or that it is ready before trying again.\n").append(this.unexpectedObject);
				break;
			case UNEXPECTED_CHARACTER:
				sb.append("The unexpected character (").append(this.unexpectedObject).append(") was found at position ").append(this.position).append(". Fix the parsable string and try again.");
				break;
			case UNEXPECTED_TOKEN:
				sb.append("The unexpected token ").append(this.unexpectedObject).append(" was found at position ").append(this.position).append(". Fix the parsable string and try again.");
				break;
			case UNEXPECTED_EXCEPTION:
				sb.append("Please report this to the library's maintainer. The unexpected exception that should be addressed before trying again occurred at position ").append(this.position).append(":\n").append(this.unexpectedObject);
				break;
			default:
				sb.append("Please report this to the library's maintainer. An error at position ").append(this.position).append(" occurred. There are no recovery recommendations available.");
				break;
		}
		return sb.toString();
	}

}
