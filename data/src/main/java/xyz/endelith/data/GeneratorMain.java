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

        OptionSpec<File> apiSourceFolderOption = 
                optionParser.accepts("apiSourceFolder")
                            .withRequiredArg()
                            .ofType(File.class);

        OptionSpec<File> serverSourceFolderOption = 
                optionParser.accepts("serverSourceFolder")
                            .withRequiredArg()
                            .ofType(File.class);
    
        OptionSpec<File> serverResourceFolderOption = 
                optionParser.accepts("serverResourceFolder")
                            .withRequiredArg()
                            .ofType(File.class);
    
        OptionSet optionSet = optionParser.parse(args);
        
        File apiSourceFolder = optionSet.valueOf(apiSourceFolderOption);
        File serverSourceFolder = optionSet.valueOf(serverSourceFolderOption);
        File serverResourceFolder = optionSet.valueOf(serverResourceFolderOption);
    
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        Bootstrap.validate();

        GeneratorMain generator = new GeneratorMain();
        generator.apiSourceGenerate(apiSourceFolder.toPath());
        generator.serverSourceGenerate(serverSourceFolder.toPath());
        generator.serverResourceGenerate(serverResourceFolder.toPath()); 
    }

    public void apiSourceGenerate(Path apiSourceFolder) {
        // Generate sources here
    }

    public void serverSourceGenerate(Path serverSourceFolder) {
        new MinecraftVersionGenerator(serverSourceFolder).generate();
    }

    public void serverResourceGenerate(Path serverResourceFolder) { 
        new GameEventGenerator(serverResourceFolder).generate();

        new GenericResourceGenerator("cat_variant", serverResourceFolder).generate();
    }
}
