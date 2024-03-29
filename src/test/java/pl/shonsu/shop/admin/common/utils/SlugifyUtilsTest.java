package pl.shonsu.shop.admin.common.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SlugifyUtilsTest {

    @ParameterizedTest
    @CsvSource({
            "test test.png, test-test.png",
            "hello word.png, hello-word.png",
            "ąśćęółńćżź.png, asceolnczz.png",
            "Produkt 1.png, produkt-1.png",
            "Produkt - 1.png, produkt-1.png",
            "Produkt  1.png, produkt-1.png",
            "Produkt __1.png, produkt-1.png",

    })
    void shouldSlugifyFileName(String in, String out) {
        String filename = SlugifyUtils.slugifyFileName(in);
        assertEquals(filename, out);
    }
}