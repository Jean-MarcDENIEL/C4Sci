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
 * <li>closed by calling the {@link #closeFile()} method
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
	 * Creates the structure to access t a secured file.
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
	 * @throws UncoherentIOStateException in the cases where :
	 * 	<ul>
	 * 		<li>the file cannot be created, or the name points to a directory, or</li>
	 * 		<li>an opening (read or write) method has already been called without {@link #closeFile()} call, or</li>
	 * 		<li>there is a system error.</li>
	 * 	</ul>
	 */
	public OutputStream createNewFile() throws UncoherentIOStateException{
		ensureNotInUse();
		deleteCoherenceKey(completeFileName);
		try {
			outputStream = new FileOutputStream(completeFileName);
			return outputStream;
		} catch (FileNotFoundException _e) {
			throw new UncoherentIOStateException(completeFileName, "error creating a FileOutputStream", _e);
		}
	}
	/**
	 * Creates an output stream corresponding to appending at the end of a file.
	 * @return
	 * @throws UncoherentIOStateException in the cases where :
	 * 	<ul>
	 * 		<li>the file cannot be created, or the name points to a directory, or</li>
	 * 		<li>an opening (read or write) method has already been called without {@link #closeFile()} call, or</li>
	 * 		<li>there is a system error.</li>
	 * 	</ul>
	 */
	public OutputStream appendToExistingFile() throws UncoherentIOStateException{
		ensureNotInUse();
		deleteCoherenceKey(completeFileName);
		try {
			outputStream = new FileOutputStream(completeFileName, true);
			return outputStream;
		} catch (FileNotFoundException _e) {
			throw new UncoherentIOStateException(completeFileName, "error appending to an existing file", _e);
		}
	}
	/**
	 * Creates an input stream in the case the file is in a coherent state.
	 * @return
	 * @throws UncoherentIOStateException in the cases where
	 * 	<ul>
	 * 		<li>the file is already in use, or</li>
	 * 		<li>the file exists ut is not in a coherent state</li>
	 * 		<li>an opening (read or write) method has already been called without {@link #closeFile()} call, or</li>
	 * 		<li>there is a system error.</li>
	 * 	</ul> 
	 */
	public InputStream readFile() throws UncoherentIOStateException{
		ensureNotInUse();
		if (!existsInCoherentState()){
			throw new UncoherentIOStateException(completeFileName, "File is not in coherent state.");
		}
		try {
			inputStream = new FileInputStream(completeFileName);
			return inputStream;
		} catch (FileNotFoundException _e) {
			throw new UncoherentIOStateException(completeFileName, "File does not exists", _e);
		}
	}
	/**
	 * Closes the access to the file, by :
	 * 	<ol>
	 * 		<li> releasing the opened stream if there is one,</li>
	 * 		<li> storing the fact that the file is coherent if {@link #appendToExistingFile()} or {@link #createNewFile()} has been called before
	 * 	</ol>
	 * @throws UncoherentIOStateException in the cases where :
	 * 		<li>this closing method is called several times without any opening between two closing calls, or</li>
	 * 		<li>there is a system error.</li>
	 * 
	 */
	public void closeFile() throws UncoherentIOStateException{
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
				throw new UncoherentIOStateException(completeFileName, "Cannot close a file that has not been opened");
			}
		}
		}
		catch(IOException _e){
			throw new UncoherentIOStateException(completeFileName, "Cannot close a file that has not been opened previously.", _e);
		}
	}
	/**
	 * 
	 * @return true :
	 * <ol>
	 * 	<li> the file exits, and </li>
	 * 	<li> the file is not a directory, and</li>
	 *  <li> the file is in a coherent state.</li>
	 * </ol> 
	 * @throws UncoherentIOStateException in the cases where :
	 * <ul>
	 * 		<li>the file is already in use</li>
	 * </ul>
	 */
	public boolean existsInCoherentState() throws UncoherentIOStateException{
		ensureNotInUse();
		return existsCoherenceKey(completeFileName);
	}
	
	/**
	 * Deletes the files
	 * @throws UncoherentIOStateException in the cases where :
	 * <ul>
	 * 		<li>the file is already in use, or</li>
	 * 		<li>the file does not exists, or</li>
	 * 		<li>the file is a directory,or</li>
	 * 		<li>there is a system error.</li>
	 * </ul>
	 */
	public void deleteFile() throws UncoherentIOStateException {
		ensureNotInUse();
		deleteCoherenceKey(completeFileName);
		try{
			File _file = new File(completeFileName);
			if (_file.isDirectory()){
				throw new UncoherentIOStateException(completeFileName, "cannot delete a directory");
			}
			if (!_file.exists()){
				throw new UncoherentIOStateException(completeFileName, "cannot delete a non existing file");
			}
			if (!_file.delete()){
				throw new UncoherentIOStateException(completeFileName, "deleteing error");
			}
		}
		catch(SecurityException _e){
			throw new UncoherentIOStateException(completeFileName, "Deleting error", _e);
		}
	}
	
	private void ensureNotInUse() throws UncoherentIOStateException{
		if (inputStream != null){
			throw new UncoherentIOStateException(completeFileName, "File already in input use");
		}
		if (outputStream != null){
			throw new UncoherentIOStateException(completeFileName, "File already in output use");
		}
	}
	
	private static String getKeyFileName(String absolute_file_name){
		return absolute_file_name + COHERENCE_KEY_SUFFIX;
	}
	
	private static boolean existsCoherenceKey(String absolute_file_name) throws UncoherentIOStateException{
		try{
			File _key_file = new File(getKeyFileName(absolute_file_name));
			return _key_file.exists() && _key_file.isFile();
		}
		catch(SecurityException _e){
			throw new UncoherentIOStateException(absolute_file_name, "file system error", _e);
		}
	}
	
	private static void deleteCoherenceKey(String absolute_file_name) throws UncoherentIOStateException{
		try{
			if (existsCoherenceKey(absolute_file_name)){
				File _key_file = new File(getKeyFileName(absolute_file_name));
				if (!_key_file.delete()){
					throw new UncoherentIOStateException(absolute_file_name, "Cannot delete coherence key");
				}
			}
		}
		catch(SecurityException _e){
			throw new UncoherentIOStateException(absolute_file_name, "cannot delete file coherence key", _e);
		}
	}
	
	private static void createCoherenceKey(String absolute_file_name) throws UncoherentIOStateException{
		if (existsCoherenceKey(absolute_file_name)){
			return;
		}
		File _coherence_key = new File(getKeyFileName(absolute_file_name));
		try {
			if (!_coherence_key.createNewFile()){
				throw new UncoherentIOStateException(absolute_file_name, "Cannot create coherence key");
			}
		} catch (IOException _e) {
			throw new UncoherentIOStateException(absolute_file_name, "Cannot create coherence key file", _e);
		}
	}
}
