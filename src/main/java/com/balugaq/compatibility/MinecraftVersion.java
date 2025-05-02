package com.balugaq.compatibility;

import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;

public class MinecraftVersion {
    public static final boolean AT_LEAST_1_20;
    public static final boolean AT_LEAST_1_20_4;
    public static final boolean AT_LEAST_1_20_5;
    static {
        int major = PaperLib.getMinecraftVersion();
        int minor = PaperLib.getMinecraftPatchVersion();
        AT_LEAST_1_20 = major >= 20;
        AT_LEAST_1_20_4 = (major == 20 && minor >= 4) || major > 20;
        AT_LEAST_1_20_5 = (major == 20 && minor >= 5) || major > 20;
    }
}
