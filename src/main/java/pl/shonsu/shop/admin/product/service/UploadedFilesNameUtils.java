package pl.shonsu.shop.admin.product.service;

import com.github.slugify.Slugify;
import org.apache.commons.io.FilenameUtils;

class UploadedFilesNameUtils {
    public static String slugifyFileName(String filename) {
        String name = FilenameUtils.getBaseName(filename);
        Slugify slg = Slugify.builder()
                .customReplacement("_","-")
                .build();
        String changedName = slg.slugify(name);
        return changedName + "." + FilenameUtils.getExtension(filename);
    }
}
