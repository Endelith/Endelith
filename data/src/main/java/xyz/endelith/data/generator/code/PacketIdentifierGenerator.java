package xyz.endelith.data.generator.code;

import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeSpec;
import java.lang.reflect.AccessFlag;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.lang.model.element.Modifier;
import net.minecraft.network.ProtocolInfo;
import net.minecraft.network.ProtocolInfo.DetailsProvider;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.network.protocol.common.CommonPacketTypes;
import net.minecraft.network.protocol.cookie.CookiePacketTypes;
import net.minecraft.network.protocol.ping.PingPacketTypes;

public record PacketIdentifierGenerator(
        String className,
        DetailsProvider detailsProvider,
        Class<?> keyDefinitionClass,
        Path outputFolder
) implements CodeGenerator {

    public PacketIdentifierGenerator {
        Objects.requireNonNull(className, "class name");
        Objects.requireNonNull(detailsProvider, "details provider");
        Objects.requireNonNull(keyDefinitionClass, "key definition class");
        Objects.requireNonNull(outputFolder, "output folder");
    }

    @Override
    public void generate() {
        ensureDirectory(this.outputFolder());

        TypeSpec.Builder builder =
                TypeSpec.classBuilder(this.className)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addMethod(
                                MethodSpec.constructorBuilder()
                                        .addModifiers(Modifier.PRIVATE)
                                        .build()
                        )
                        .addJavadoc(this.generateJavadoc());

        Map<String, String> fieldNames = this.createFieldNames();

        this.detailsProvider
                .details()
                .listPackets(
                        (type, identifier) -> {
                            String id = type.id().toString();
                            String fieldName = fieldNames.get(id);

                            if (fieldName == null) {
                                throw new IllegalStateException(
                                        "No field name was specified for packet " + id
                                );
                            }

                            builder.addField(
                                    FieldSpec.builder(int.class, fieldName)
                                            .addModifiers(
                                                    Modifier.PUBLIC,
                                                    Modifier.STATIC,
                                                    Modifier.FINAL
                                            )
                                            .initializer(String.valueOf(identifier))
                                            .addJavadoc(
                                                    CodeBlock.of(
                                                            "An identifier of $S packet.",
                                                            id
                                                    )
                                            )
                                            .build()
                            );
                        }
                );

        writeFiles(JavaFile.builder("xyz.endelith.server.network.packet.identifer", builder.build())
                .indent("    ")
                .build());
    }

    private Map<String, String> createFieldNames() {
        Map<String, String> fieldNames = new HashMap<>();
        this.extractPacketFieldNames(CommonPacketTypes.class, fieldNames);
        this.extractPacketFieldNames(CookiePacketTypes.class, fieldNames);
        this.extractPacketFieldNames(PingPacketTypes.class, fieldNames);
        this.extractPacketFieldNames(this.keyDefinitionClass, fieldNames);
        return fieldNames;
    }

    private void extractPacketFieldNames(
            Class<?> clazz,
            Map<String, String> fieldNameMap
    ) {
        ProtocolInfo.Details details = this.detailsProvider.details();

        for (Field field : clazz.getDeclaredFields()) {
            if (!PacketType.class.isAssignableFrom(field.getType())) {
                continue;
            }
            if (!field.accessFlags().contains(AccessFlag.STATIC)) {
                continue;
            }

            try {
                PacketType<?> packetType = (PacketType<?>) field.get(null);

                if (packetType.flow() != details.flow()) {
                    continue;
                }

                String id = packetType.id().toString();
                fieldNameMap.put(id, field.getName());
            } catch (IllegalAccessException exception) {
                throw new RuntimeException(exception);
            }
        }
    }
}
