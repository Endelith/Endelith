package xyz.endelith.data;

import java.io.File;
import java.nio.file.Path;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import xyz.endelith.data.generator.code.MinecraftVersionGenerator;
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
    
        generator.apiSourceGenerate(optionSet.valueOf(apiSourceFolderOption).toPath()); 
        generator.serverSourceGenerate(optionSet.valueOf(serverSourceFolderOption).toPath()); 
        generator.serverResourceGenerate(optionSet.valueOf(serverResourceFolderOption).toPath());
    }

    public void apiSourceGenerate(Path apiSourceFolder) {
        // Generate sources here
    }

    public void serverSourceGenerate(Path serverSourceFolder) {
        new MinecraftVersionGenerator(serverSourceFolder).generate();
    }

    public void serverResourceGenerate(Path serverResourceFolder) { 
        new GameEventGenerator(serverResourceFolder).generate();

        // Data driven registries
        new GenericResourceGenerator("worldgen/biome", serverResourceFolder).generate(); 
        new GenericResourceGenerator("chat_type", serverResourceFolder).generate();
        new GenericResourceGenerator("trim_pattern", serverResourceFolder).generate();
        new GenericResourceGenerator("trim_material", serverResourceFolder).generate();
        new GenericResourceGenerator("wolf_variant", serverResourceFolder).generate();
        new GenericResourceGenerator("pig_variant", serverResourceFolder).generate();
        new GenericResourceGenerator("frog_variant", serverResourceFolder).generate();
        new GenericResourceGenerator("cat_variant", serverResourceFolder).generate();
        new GenericResourceGenerator("chicken_variant", serverResourceFolder).generate();
        new GenericResourceGenerator("cow_variant", serverResourceFolder).generate();
        new GenericResourceGenerator("damage_type", serverResourceFolder).generate();
        new GenericResourceGenerator("jukebox_song", serverResourceFolder).generate();
        new GenericResourceGenerator("instrument", serverResourceFolder).generate();
        new GenericResourceGenerator("wolf_sound_variant", serverResourceFolder).generate();
        new GenericResourceGenerator("painting_variant", serverResourceFolder).generate();
        new GenericResourceGenerator("dimension_type", serverResourceFolder).generate();
        new GenericResourceGenerator("banner_pattern", serverResourceFolder).generate();
        new GenericResourceGenerator("enchantment", serverResourceFolder).generate();
        new GenericResourceGenerator("dialog", serverResourceFolder).generate();
    }
}
