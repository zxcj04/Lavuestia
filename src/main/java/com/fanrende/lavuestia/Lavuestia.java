package com.fanrende.lavuestia;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Lavuestia.MODID)
public class Lavuestia
{
    public static final String MODID = "lavuestia";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public Lavuestia()
    {

    }
}
