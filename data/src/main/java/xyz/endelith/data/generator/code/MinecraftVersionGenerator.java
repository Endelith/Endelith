package xyz.endelith.data.generator.code;

import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeSpec;
import java.nio.file.Path;
import java.util.Objects;
import javax.lang.model.element.Modifier;
import net.minecraft.SharedConstants;
import net.minecraft.WorldVersion;

public record MinecraftVersionGenerator(Path outputFolder) implements CodeGenerator {
    
    public MinecraftVersionGenerator {
        Objects.requireNonNull(outputFolder, "output folder");
    }

    @Override
    public void generate() {
        ensureDirectory(this.outputFolder);
        
        WorldVersion version = SharedConstants.getCurrentVersion();
        TypeSpec typeSpec = TypeSpec.classBuilder("MinecraftVersion")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addMethod(MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PRIVATE)
                        .addJavadoc(this.generateJavadoc())
                        .build())
                .addField(FieldSpec.builder(String.class, "VERSION_NAME")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer(CodeBlock.of("$S", version.name()))
                        .build())
                .addField(FieldSpec.builder(String.class, "VERSION_ID")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer(CodeBlock.of("$S", version.id()))
                        .build())
                .addField(FieldSpec.builder(int.class, "PROTOCOL_VERSION")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer(CodeBlock.of("$L", version.protocolVersion()))
                        .build())
                .addField(FieldSpec.builder(int.class, "DATA_VERSION")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer(CodeBlock.of("$L", version.dataVersion().version()))
                        .build())
                .build();

        writeFiles(JavaFile.builder("xyz.endelith.server", typeSpec)
                .indent("    ")
                .build()
        );
    }
}
