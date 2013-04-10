package c4sci.io;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

public class TestSecuredFile {

	@SuppressWarnings("unused")
	@Test
	public void test() {
		SecuredFile _secured = new SecuredFile("toto.txt");
		String _testing_str = "testing";
		String _continue_str = "continue";
		

		
		try{
			OutputStream _out_1 = _secured.createNewFile();
			OutputStream _out_2 = _secured.createNewFile();
			fail("double createNewFile should have thrown");
		}
		catch (UncoherentIOStateException _e){
			assertTrue(true);
		}
		try {
			_secured.closeFile();
		} catch (UncoherentIOStateException e) {
			fail("closeFile should not throw");
		}
		try {
			_secured.closeFile();
			fail("closeFile twice should have thrown");
		} catch (UncoherentIOStateException e) {
			assertTrue(true);
		}
		
		try {
			OutputStream _out_new = _secured.createNewFile();
			_out_new.write(_testing_str.getBytes());
			_secured.closeFile();
		} 
		catch (UncoherentIOStateException e) {
			fail("createNewFile should not throw");
		}
		catch(IOException _e){
			fail("should write");
		}
		
		try {
			OutputStream _out_append = _secured.appendToExistingFile();
			_out_append.write(_continue_str.getBytes());
			_secured.closeFile();
			
		} catch (UncoherentIOStateException _e) {
			fail("append should not throw");
		} catch (IOException e) {
			fail("apend should not IOException");
		}
		
		try{
			InputStream _in_1 = _secured.readFile();
			byte[] _line = new byte[256];
			int _length = _in_1.read(_line);
			String _read = new String(_line, 0, _length);
			assertTrue("bad length", _length == (_testing_str+_continue_str).length());
			assertTrue("strange convertion : " + _read.length() + " for " + _length + " bytes", _length == _read.length());
			assertTrue("reading " + _read + " instead of " + (_testing_str+_continue_str), _read.compareTo(_testing_str+_continue_str) == 0);
		}
		catch(UncoherentIOStateException _e){
			fail("readFile should not throw");
		} catch (IOException e) {
			fail("should not IOException");
		}
		
		try{
			OutputStream _out_3 = _secured.createNewFile();
			fail("should not permit opening an already in use file");
		}
		catch(UncoherentIOStateException _e){
			assertTrue(true);
		}
		
		try{
			_secured.deleteFile();
			fail("should have thrown");
		}
		catch(UncoherentIOStateException _e){
			assertTrue(true);
		}
		
		try{
			_secured.closeFile();
		}
		catch(UncoherentIOStateException _e){
			fail("should not have thrown here");
		}
		
		try{
			_secured.deleteFile();
		}
		catch(UncoherentIOStateException _e){
			fail("delete should not have thrown");
		}
		
		try{
			InputStream _in_2 = _secured.readFile();
			fail("should have thrown");
		}
		catch(UncoherentIOStateException _e){
			assertTrue(true);
		}
		
		
		
	}

}
