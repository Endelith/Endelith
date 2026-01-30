package xyz.endelith.data;

import java.io.File;
import java.nio.file.Path;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.SharedConstants;
import net.minecraft.network.protocol.configuration.ConfigurationPacketTypes;
import net.minecraft.network.protocol.configuration.ConfigurationProtocols;
import net.minecraft.network.protocol.game.GamePacketTypes;
import net.minecraft.network.protocol.game.GameProtocols;
import net.minecraft.network.protocol.handshake.HandshakePacketTypes;
import net.minecraft.network.protocol.handshake.HandshakeProtocols;
import net.minecraft.network.protocol.login.LoginPacketTypes;
import net.minecraft.network.protocol.login.LoginProtocols;
import net.minecraft.network.protocol.status.StatusPacketTypes;
import net.minecraft.network.protocol.status.StatusProtocols;
import net.minecraft.server.Bootstrap;
import xyz.endelith.data.generator.code.GenericKeyGenerator;
import xyz.endelith.data.generator.code.MinecraftVersionGenerator;
import xyz.endelith.data.generator.code.PacketIdentifierGenerator;
import xyz.endelith.data.generator.resource.GameEventGenerator;
import xyz.endelith.data.generator.resource.GenericResourceGenerator;

public final class GeneratorMain {

    public static void main(String[] args) {
        OptionParser optionParser = new OptionParser();
    
        final OptionSpec<File> apiSourceFolderOption =
                optionParser.accepts("apiSourceFolder")
                            .withRequiredArg()
                            .ofType(File.class);
    
        final OptionSpec<File> serverSourceFolderOption =
                optionParser.accepts("serverSourceFolder")
                            .withRequiredArg()
                            .ofType(File.class);
    
        final OptionSpec<File> serverResourceFolderOption =
                optionParser.accepts("serverResourceFolder")
                            .withRequiredArg()
                            .ofType(File.class);
    
        final OptionSet optionSet = optionParser.parse(args);
    
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        Bootstrap.validate();
    
        GeneratorMain generator = new GeneratorMain();
   
        final Path serverSourcePath =
                optionSet.valueOf(serverSourceFolderOption).toPath();
        
        final Path serverResourcePath =
                optionSet.valueOf(serverResourceFolderOption).toPath();
        
        final Path apiSourcePath =
                optionSet.valueOf(apiSourceFolderOption).toPath();
        
        generator.serverSourceGenerate(serverSourcePath);
        generator.serverResourceGenerate(serverResourcePath);
        generator.apiSourceGenerate(serverResourcePath, apiSourcePath);
    }

    public void apiSourceGenerate(Path serverResourceFolder, Path apiSourceFolder) {
        generateKeys(
                serverResourceFolder,
                apiSourceFolder,
                "biome",
                "chat_type",
                "trim_pattern",
                "trim_material",
                "wolf_variant",
                "pig_variant",
                "frog_variant",
                "cat_variant",
                "chicken_variant",
                "cow_variant",
                "damage_type",
                "jukebox_song",
                "instrument",
                "wolf_sound_variant",
                "painting_variant",
                "dimension_type",
                "banner_pattern",
                "enchantment",
                "dialog"
        );
    }

    public void serverSourceGenerate(Path serverSourceFolder) {
        new MinecraftVersionGenerator(serverSourceFolder).generate();

        // Packet Identifiers
        new PacketIdentifierGenerator(
                "ClientHandshakePackets",
                HandshakeProtocols.SERVERBOUND_TEMPLATE,
                HandshakePacketTypes.class,
                serverSourceFolder
        ).generate();

        new PacketIdentifierGenerator(
                "ServerStatusPackets",
                StatusProtocols.CLIENTBOUND_TEMPLATE,
                StatusPacketTypes.class,
                serverSourceFolder
        ).generate();

        new PacketIdentifierGenerator(
                "ClientStatusPackets",
                StatusProtocols.SERVERBOUND_TEMPLATE,
                StatusPacketTypes.class,
                serverSourceFolder
        ).generate();

        new PacketIdentifierGenerator(
                "ServerLoginPackets",
                LoginProtocols.CLIENTBOUND_TEMPLATE,
                LoginPacketTypes.class,
                serverSourceFolder
        ).generate();

        new PacketIdentifierGenerator(
                "ClientLoginPackets",
                LoginProtocols.SERVERBOUND_TEMPLATE,
                LoginPacketTypes.class,
                serverSourceFolder
        ).generate();

        new PacketIdentifierGenerator(
                "ServerConfigurationPackets",
                ConfigurationProtocols.CLIENTBOUND_TEMPLATE,
                ConfigurationPacketTypes.class,
                serverSourceFolder
        ).generate();

        new PacketIdentifierGenerator(
                "ClientConfigurationPackets",
                ConfigurationProtocols.SERVERBOUND_TEMPLATE,
                ConfigurationPacketTypes.class,
                serverSourceFolder
        ).generate();

        new PacketIdentifierGenerator(
                "ServerPlayPackets",
                GameProtocols.CLIENTBOUND_TEMPLATE,
                GamePacketTypes.class,
                serverSourceFolder
        ).generate();

        new PacketIdentifierGenerator(
                "ClientPlayPackets",
                GameProtocols.SERVERBOUND_TEMPLATE,
                GamePacketTypes.class,
                serverSourceFolder
        ).generate();
    }

    public void serverResourceGenerate(Path serverResourceFolder) { 
        // Built in registries
        new GameEventGenerator(serverResourceFolder).generate();

        // Data driven registries
        generateResources(
                serverResourceFolder,
                "worldgen/biome",
                "chat_type",
                "trim_pattern",
                "trim_material",
                "wolf_variant",
                "pig_variant",
                "frog_variant",
                "cat_variant",
                "chicken_variant",
                "cow_variant",
                "damage_type",
                "jukebox_song",
                "instrument",
                "wolf_sound_variant",
                "painting_variant",
                "dimension_type",
                "banner_pattern",
                "enchantment",
                "dialog"
        );
    }

    private void generateKeys(
            Path serverResourceFolder,
            Path apiSourceFolder,
            String... resources
    ) {
        for (String resource : resources) {
            new GenericKeyGenerator(
                    resource,
                    serverResourceFolder,
                    apiSourceFolder
            ).generate();
        }
    }

    private void generateResources(
            Path serverResourceFolder,
            String... resources
    ) {
        for (String resource : resources) {
            new GenericResourceGenerator(
                    resource,
                    serverResourceFolder
            ).generate();
        }
    }
}
