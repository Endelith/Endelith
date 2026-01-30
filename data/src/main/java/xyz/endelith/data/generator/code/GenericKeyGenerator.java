package xyz.endelith.data.generator.code;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeSpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import javax.lang.model.element.Modifier;

public record GenericKeyGenerator(
        String resourceName,
        Path serverResourceFolder,
        Path outputFolder
) implements CodeGenerator {

    public GenericKeyGenerator {
        Objects.requireNonNull(resourceName, "resourceName");
        Objects.requireNonNull(serverResourceFolder, "serverResourceFolder");
        Objects.requireNonNull(outputFolder, "outputFolder");
    }

    @Override
    public void generate() {
        this.ensureDirectory(this.serverResourceFolder);
        this.ensureDirectory(this.outputFolder);

        Path jsonPath = this.resolveJsonPath();
        if (!Files.isRegularFile(jsonPath)) {
            throw new IllegalStateException("Resource JSON not found: " + jsonPath);
        }

        Map<String, String> constants = new LinkedHashMap<>();
        try {
            JsonObject root = JsonParser
                    .parseString(Files.readString(jsonPath))
                    .getAsJsonObject();

            for (String rawKey : root.keySet()) {
                if (rawKey == null || rawKey.isBlank()) {
                    continue;
                }

                String fullKey = rawKey.trim();
                String namePart;
                if (fullKey.contains(":")) {
                    namePart = fullKey.split(":", 2)[1];
                } else {
                    namePart = fullKey;
                }

                String base = toConstantName(namePart);
                String constName = ensureUnique(base, fullKey, constants);
                constants.put(constName, fullKey);
            }
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to read/parse JSON: " + jsonPath,
                    e
            );
        }

        String className = toPascalBaseName(this.resourceName) + "Keys";

        TypeSpec.Builder type = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addJavadoc(this.generateJavadoc())
                .addMethod(MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PRIVATE)
                        .build());

        ClassName keyClass = ClassName.get("net.kyori.adventure.key", "Key");
        for (Map.Entry<String, String> entry : constants.entrySet()) {
            type.addField(FieldSpec.builder(keyClass, entry.getKey())
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer(
                            CodeBlock.of("$T.key($S)", keyClass, entry.getValue())
                    )
                    .addJavadoc(
                            "A key of {@code $L} registry entry.\n",
                            entry.getValue()
                    )
                    .build());
        }

        this.writeFiles(
                JavaFile.builder(
                                "xyz.endelith.registry.keys",
                                type.build()
                        )
                        .indent("    ")
                        .build()
        );
    }

    private Path resolveJsonPath() {
        Path p1 = this.serverResourceFolder.resolve(this.resourceName);
        Path p2 = this.serverResourceFolder.resolve(this.resourceName + ".json");

        if (Files.isRegularFile(p1)) {
            return p1;
        }
        if (Files.isRegularFile(p2)) {
            return p2;
        }

        if (this.resourceName.endsWith(".json")
                || this.resourceName.contains("/")
                || this.resourceName.contains("\\")) {

            Path direct = Path.of(this.resourceName);
            if (Files.isRegularFile(direct)) {
                return direct;
            }
        }

        try (Stream<Path> stream = Files.walk(this.serverResourceFolder)) {
            String name;
            if (this.resourceName.endsWith(".json")) {
                name = this.resourceName;
            } else {
                name = this.resourceName + ".json";
            }

            return stream
                    .filter(f -> {
                        String fn = f.getFileName().toString();
                        return fn.equals(this.resourceName) || fn.equals(name);
                    })
                    .findFirst()
                    .orElse(p2);
        } catch (IOException e) {
            return p2;
        }
    }

    private static String toConstantName(String name) {
        String s = name.replaceAll("[^A-Za-z0-9]", "_")
                .replaceAll("_+", "_")
                .replaceAll("^_+", "")
                .toUpperCase();

        if (s.isEmpty()) {
            s = "KEY";
        }
        if (Character.isDigit(s.charAt(0))) {
            s = "_" + s;
        }
        return s;
    }

    private static String ensureUnique(
            String base,
            String fullKey,
            Map<String, String> existing
    ) {
        String candidate = base;
        for (int i = 1; ; i++) {
            String mapped = existing.get(candidate);
            if (mapped == null || mapped.equals(fullKey)) {
                return candidate;
            }
            candidate = base + "_" + i;
        }
    }

    private static String toPascalBaseName(String name) {
        String base = name;
        if (base.contains("/") || base.contains("\\")) {
            String[] parts = base.replace("\\", "/").split("/");
            base = parts[parts.length - 1];
        }

        if (base.endsWith(".json")) {
            base = base.substring(0, base.length() - 5);
        }
        if (base.contains(":")) {
            base = base.split(":", 2)[1];
        }

        String[] parts = base.split("[^A-Za-z0-9]+");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (!p.isEmpty()) {
                sb.append(Character.toUpperCase(p.charAt(0)));
                if (p.length() > 1) {
                    sb.append(p.substring(1));
                }
            }
        }
        return sb.length() == 0 ? "Resource" : sb.toString();
    }
}
