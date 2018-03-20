package com.inovision.json;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonGenerator.Feature;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class UnicodeEscapeSerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String str, JsonGenerator jGen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		//This line encodes DBYTE unicode chars to it's escape sequence
		jGen.enable(Feature.ESCAPE_NON_ASCII);
		jGen.writeString(str);
		jGen.flush();
		
		//Unicode generator double encodes existing unicode chars with double backslash (\u2120 converts to \\u2120)
		//Following is to restore single backslash
		StringWriter writer = ((StringWriter) jGen.getOutputTarget());
		StringBuffer buffer = writer.getBuffer();
		String bufstr = new String(buffer.toString());
		
		buffer.delete(0, buffer.length());
		//Replace all instance of two backlash to one, but only for unicode chars
		buffer.append(bufstr.replaceAll("\\\\{2}[uU]([0-9A-Fa-f]{4})", "\\\\u$1"));
		jGen.flush();
	}
	
}