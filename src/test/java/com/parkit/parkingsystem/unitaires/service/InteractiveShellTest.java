package com.parkit.parkingsystem.unitaires.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import java.io.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.service.InteractiveShell;

/**
 * The type Interactive shell test.
 */

@ExtendWith(MockitoExtension.class)
public class InteractiveShellTest {
	@Mock
	private static InteractiveShell interactiveShell;
	/**
	 * The Interactive shell.
	 */

	InputStream inputStream;
	OutputStream outputStream;

	@BeforeEach
	private void setUpPerTest() {

		interactiveShell = new InteractiveShell();
	}

	/**
	 * Load interface ans wer 3 test.
	 *
	 * @throws IOException
	 *             the io exception
	 */
	@Test
	@DisplayName("tests if the right message is displayed when enteing the app and leaving it right away")
	public void loadInterfaceAnsWerTest() throws Exception {
		// ARRANGE
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		String initialString = "3\n";
		inputStream = new ByteArrayInputStream(initialString.getBytes());
		System.setIn(inputStream);

		InteractiveShell.loadInterface();

		String out = outContent.toString();

		assertThat(out, containsString("3 Shutdown System"));
		inputStream.close();
	}

}
