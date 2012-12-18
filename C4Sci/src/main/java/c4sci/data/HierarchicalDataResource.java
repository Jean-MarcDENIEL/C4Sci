package c4sci.data;

import java.io.InputStream;
import java.io.OutputStream;

import c4sci.data.exceptions.DataValueParsingException;

/**
 * This interface represents resources that can be used to store and retrieve {@link HierarchicalData}.
 * @author jeanmarc.deniel
 *
 */
public interface HierarchicalDataResource {
	void storeData(OutputStream output_stream, HierarchicalData[] data_tab) throws DataValueParsingException;
	HierarchicalData[] retrieveData(InputStream input_stream, HierarchialDataFactory root_data_factory) throws DataValueParsingException;
}
