package com.utils.convert;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DateConvert implements Converter{
	
	private DateFormat formatter = DateFormat.getDateInstance(DateFormat.DATE_FIELD);

	@Override
	public boolean canConvert(Class type) {
		boolean canConvert = Date.class.isAssignableFrom(type);
		return canConvert;
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		writer.setValue(formatter.format(source));
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		try {
			return formatter.parse(reader.getValue());
		} catch (ParseException e) {
			return null;
		}
	}

}
