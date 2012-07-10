package c4sci.data.internationalization;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestInternationalizableTest {

	@Test
	public void testInternationalizableTerm() {
		InternationalizableTerm _term = new InternationalizableTerm("default_term");
		assertTrue(true);
	}

	@Test
	public void testGetDefaultValue() {
		InternationalizableTerm _term = new InternationalizableTerm("default_term");
		assertTrue("default_term".compareTo(_term.getDefaultValue())==0);
	}

	@Test
	public void testGetValue() {
		InternationalizableTerm _term = new InternationalizableTerm("default_term");
		Languages.addNewLanguage(new Language("IT", "Italian", "Italiano"));
		try {
			_term.setValue(Languages.getLanguage("FR"), "terme par defaut");
		} catch (NoCorrespondingLanguageException e) {
			fail("No FR language");
		}
		
		try {
			_term.setValue(Languages.getLanguage("IT"), "Defauti terma");
		} catch (NoCorrespondingLanguageException e) {
			fail("No IT language");
		}
		
		try {
			assertTrue("terme par defaut".compareTo(_term.getValue(Languages.getLanguage("FR")))==0);
		} catch (NoCorrespondingLanguageException e) {
			fail("Unable to retrieve FR value");
		}
		
		try {
			assertTrue("Defauti terma".compareTo(_term.getValue(Languages.getLanguage("IT")))==0);
		} catch (NoCorrespondingLanguageException e) {
			fail("Unable to rerieve IT value");
		}
		
		try {
			Language _other = Languages.getLanguage("other");
			fail("NoCorrespondingLanguageException not launched");
		} catch (NoCorrespondingLanguageException e) {
			assertTrue("NoCorrespondingLanguageException launched", true);
		}
	}


}
