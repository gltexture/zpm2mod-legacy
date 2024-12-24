package ru.BouH_.world;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.BouH_.world.generator.AGenerator;

import java.util.HashSet;
import java.util.Set;

public class Generators {
    public final Set<AGenerator> generators;

    public Generators() {
        this.generators = new HashSet<>();
    }

    public void init(AGenerator aGenerator) {
        this.generators.add(aGenerator);
        GameRegistry.registerWorldGenerator(aGenerator, aGenerator.getGenId());
    }

    public void loadFiles() {
        for (AGenerator aGenerator : this.generators) {
            aGenerator.loadStructures();
        }
    }
}
