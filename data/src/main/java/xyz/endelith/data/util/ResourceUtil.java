package xyz.endelith.data.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.jar.JarFile;

public final class ResourceUtil {

    private ResourceUtil() {}

    public static String[] getResourceListing(
            Class<?> clazz,
            String path
    ) throws URISyntaxException, IOException {
        var dirUrl = clazz.getClassLoader().getResource(path);

        if (dirUrl != null && dirUrl.getProtocol().equals("file")) {
            return new File(dirUrl.toURI()).list();
        }

        if (dirUrl == null) {
            var me = clazz.getName().replace(".", "/") + ".class";
            dirUrl = clazz.getClassLoader().getResource(me);
        }

        assert dirUrl != null;

        if (dirUrl.getProtocol().equals("jar")) {
            var jarPath = dirUrl.getPath().substring(5, dirUrl.getPath().indexOf("!"));

            try (var jar = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8))) {
                var entries = jar.entries();
                var result = new HashSet<String>();

                while (entries.hasMoreElements()) {
                    var name = entries.nextElement().getName();
                    if (name.startsWith(path)) {
                        var entry = name.substring(path.length());
                        int checkSubdir = entry.indexOf("/");
                        if (checkSubdir >= 0) {
                            entry = entry.substring(0, checkSubdir);
                        }
                        result.add(entry);
                    }
                }

                return result.toArray(new String[0]);
            }
        }

        throw new UnsupportedOperationException("Unable to list files for URL " + dirUrl);
    }
}
