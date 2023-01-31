import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.text.DecimalFormat;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


/**
 * Created: 31.01.2023
 *
 * @author Patrick Schneeweis (patrick)
 */
public class WahlTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));

    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
    @Test
    void testisvalid() {
        assertThat(Wahl.isValid("rp")).isTrue();
        assertThat(Wahl.isValid("a")).isFalse();
    }

    @Test
    void testaddifchardash() {
        assertThat(Wahl.addifchardash("ab")).isEqualTo(0);
        assertThat(Wahl.addifchardash("a-")).isEqualTo(1);
        assertThat(Wahl.addifchardash("--")).isEqualTo(2);
    }

    @Test
    void testaddifcharK() {
        assertThat(Wahl.addifcharK("mm")).isEqualTo(0);
        assertThat(Wahl.addifcharK("da")).isEqualTo(1);
        assertThat(Wahl.addifcharK("dn")).isEqualTo(2);
    }

    @Test
    void testtestifeingabeistreturn() throws FileNotFoundException {
        assertThat(Wahl.testifeingabeistreturn(new PrintWriter("log.txt"), 1,  new DecimalFormat("000"), "return")).isTrue();
        assertThat(Wahl.testifeingabeistreturn(new PrintWriter("log.txt"), 1,  new DecimalFormat("000"), "pk")).isFalse();
    }

    @Test
    void testifeingabeKorrekt() throws FileNotFoundException {
        assertThat(Wahl.testifeingabeKorrekt(new PrintWriter("log.txt"), 1,  new DecimalFormat("000"), 2)).isEqualTo(2);
        assertThat(Wahl.testifeingabeKorrekt(new PrintWriter("log.txt"), 1,  new DecimalFormat("000"), 1)).isEqualTo(1);
    }

    @Test
    void testequals() {
        Kandidat wahl = new Kandidat("Dominik Hofmann");
        Kandidat wahl2 = new Kandidat("Kilian Prager");
        Kandidat wahl3 = new Kandidat("Dominik Hofmann");
        assertThat(wahl.equals(wahl3)).isTrue();
        assertThat(wahl.equals(wahl2)).isFalse();
    }

    @Test
    void testhashCode() {
        Kandidat wahl = new Kandidat("Dominik Hofmann");
        Kandidat wahl2 = new Kandidat("Kilian Prager");
        Kandidat wahl3 = new Kandidat("Dominik Hofmann");
        assertThat(wahl.hashCode()).isEqualTo(wahl3.hashCode());
        assertThat(wahl.hashCode()).isNotEqualTo(wahl2.hashCode());
    }
}
