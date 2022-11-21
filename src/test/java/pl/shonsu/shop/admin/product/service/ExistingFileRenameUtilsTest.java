package pl.shonsu.shop.admin.product.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ExistingFileRenameUtilsTest {

    @Test
    void shouldNotRenameExistingFile(@TempDir Path tempFir) throws IOException {
        String newName = ExistingFileRenameUtils.renameIfExists(tempFir, "test.png");
        assertEquals("test.png", newName);
    }

    @Test
    void shouldRenameExistingFile(@TempDir Path tempFir) throws IOException {
        Files.createFile(tempFir.resolve("test.png"));
        String newName = ExistingFileRenameUtils.renameIfExists(tempFir, "test.png");
        assertEquals("test-1.png", newName);
    }

    @Test
    void shouldRenameManyExistingFile(@TempDir Path tempFir) throws IOException {
        Files.createFile(tempFir.resolve("test.png"));
        Files.createFile(tempFir.resolve("test-1.png"));
        Files.createFile(tempFir.resolve("test-2.png"));
        Files.createFile(tempFir.resolve("test-3.png"));
        String newName = ExistingFileRenameUtils.renameIfExists(tempFir, "test.png");
        assertEquals("test-4.png", newName);
    }
}