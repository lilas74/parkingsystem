package com.parkit.parkingsystem.unitaires.util;
import com.parkit.parkingsystem.service.InteractiveShell;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;


/**
 * The type Interactive shell test.
 */
public class InteractiveShellTest {
    /**
     * The Interactive shell.
     */
    InteractiveShell interactiveShell ;

    @BeforeEach
    private void setUpPerTest() {
        interactiveShell = new InteractiveShell();
    }

    /**
     * Load interface ans wer 3 test.
     *
     * @throws IOException the io exception
     */
    @Test
        @DisplayName("tests if the right message is displayed when enteing the app and leaving it right away")
        public void loadInterfaceAnsWer3Test() throws IOException {
            //ARRANGE
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));


            String initialString = "3\n";
            InputStream inputStream = new ByteArrayInputStream(initialString.getBytes());
            System.setIn(inputStream);

            InteractiveShell.loadInterface();

            String out = outContent.toString();


            String load = "Please select an option. Simply enter the number to choose an action";

            Assert.assertThat(out, containsString("3 Shutdown System"));
        }

}
