package c4sci.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class gives access to a file which coherence state is known :
 * <ul>
 * <li>the file has been opened for writing, then closed : it exists in a coherent state</li>
 * <li>the file has been opened for writing but not closed : it is in a incoherent state</li>
 * </ul>
 * A file that is in a coherent state can be :
 * <ul>
 * <li>opened for reading</li>
 * <li>opened for writing (it will be overwritten) by calling the {@link #createNewFile()} method</li>
 * <li>opened for appending by calling the {@link #appendToExistingFile()}</li>
 * </ul>
 * After that it should be closed by calling the 
 * @author jeanmarc.deniel
 *
 */
public class SecuredFile {
	private String 				completeFileName;
	private FileOutputStream	outputStream;
	private FileInputStream 	inputStream;
	
	private static final String COHERENCE_KEY_SUFFIX = ".coh";
	
	/**
	 * 
	 * @param absolute_file_name The absolute file name, including the full path.
	 */
	public SecuredFile(String absolute_file_name){
		completeFileName = absolute_file_name;
		outputStream	= null;
		inputStream		= null;
	}
	/**
	 * Creates an output stream corresponding to a newly created file.
	 * @return
	 * @throws UncoherentStateFileException
	 */
	public OutputStream createNewFile() throws UncoherentStateFileException{
		ensureNotInUse();
		deleteCoherenceKey(completeFileName);
		try {
			outputStream = new FileOutputStream(completeFileName);
			return outputStream;
		} catch (FileNotFoundException _e) {
			throw new UncoherentStateFileException(completeFileName, "error creating a FileOutputStream", _e);
		}
	}
	/**
	 * Creates an output stream corresponding to appending at the end of a file.
	 * @return
	 * @throws UncoherentStateFileException
	 */
	public OutputStream appendToExistingFile() throws UncoherentStateFileException{
		ensureNotInUse();
		deleteCoherenceKey(completeFileName);
		try {
			outputStream = new FileOutputStream(completeFileName, true);
			return outputStream;
		} catch (FileNotFoundException _e) {
			throw new UncoherentStateFileException(completeFileName, "error appending to an existing file", _e);
		}
	}
	/**
	 * Creates an input stream in the case the file is in a coherent state.
	 * @return
	 * @throws UncoherentStateFileException
	 */
	public InputStream readFile() throws UncoherentStateFileException{
		ensureNotInUse();
		if (!existsInCoherentState()){
			throw new UncoherentStateFileException(completeFileName, "File is not in coherent state.");
		}
		try {
			inputStream = new FileInputStream(completeFileName);
			return inputStream;
		} catch (FileNotFoundException _e) {
			throw new UncoherentStateFileException(completeFileName, "File does not exists", _e);
		}
	}
	
	public void closeFile() throws UncoherentStateFileException{
		try{
		if (inputStream != null){
			inputStream.close();
			inputStream = null;
		}
		else{
			if (outputStream != null){
				outputStream.close();
				createCoherenceKey(completeFileName);
				outputStream = null;
			}
			else{
				throw new UncoherentStateFileException(completeFileName, "Cannot close a file that has not been opened");
			}
		}
		}
		catch(IOException _e){
			throw new UncoherentStateFileException(completeFileName, "Cannot close a file that has not been opened previously.");
		}
	}
	
	public boolean existsInCoherentState() throws UncoherentStateFileException{
		return existsCoherenceKey(completeFileName);
	}
	
	private void ensureNotInUse() throws UncoherentStateFileException{
		if (inputStream != null){
			throw new UncoherentStateFileException(completeFileName, "File already in input use");
		}
		if (outputStream != null){
			throw new UncoherentStateFileException(completeFileName, "File already in output use");
		}
	}
	
	private static String getKeyFileName(String absolute_file_name){
		return absolute_file_name + COHERENCE_KEY_SUFFIX;
	}
	
	private static boolean existsCoherenceKey(String absolute_file_name) throws UncoherentStateFileException{
		try{
			File _key_file = new File(getKeyFileName(absolute_file_name));
			return _key_file.exists() && _key_file.isFile();
		}
		catch(SecurityException _e){
			throw new UncoherentStateFileException(absolute_file_name, "file system error", _e);
		}
	}
	
	private static void deleteCoherenceKey(String absolute_file_name) throws UncoherentStateFileException{
		try{
			if (existsCoherenceKey(absolute_file_name)){
				File _key_file = new File(getKeyFileName(absolute_file_name));
				_key_file.delete();
			}
		}
		catch(SecurityException _e){
			throw new UncoherentStateFileException(absolute_file_name, "cannot delete file key", _e);
		}
	}
	
	private static void createCoherenceKey(String absolute_file_name) throws UncoherentStateFileException{
		if (existsCoherenceKey(absolute_file_name)){
			return;
		}
		File _coherence_key = new File(getKeyFileName(absolute_file_name));
		try {
			_coherence_key.createNewFile();
		} catch (IOException _e) {
			throw new UncoherentStateFileException(absolute_file_name, "Cannot create coherence key file", _e);
		}
		
		
	}
}
