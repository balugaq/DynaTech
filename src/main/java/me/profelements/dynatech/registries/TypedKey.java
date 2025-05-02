package me.profelements.dynatech.registries;

import java.util.Locale;

import javax.annotation.Nonnull;

import me.profelements.dynatech.DynaTech;
import org.bukkit.NamespacedKey;

public record TypedKey<T>(@Nonnull NamespacedKey key) {
    public static <T> TypedKey<T> create(NamespacedKey key) {
        return new TypedKey<>(key);
    }

    public static <T> TypedKey<T> create(String namespace, String key) {
        return new TypedKey<>(new NamespacedKey(namespace, key));
    }

    // THIS IS TEMPORARY TILL SLIMEFUN MOVES TO NamespacedKey
    public String asSlimefunId() {
        String raw = this.key().toString().replace(':', '_').toUpperCase(Locale.ROOT);
        if (!DynaTech.getInstance().getConfig().getBoolean("options.prefix-dynatech") && raw.startsWith("DYNATECH_")) {
            return raw.substring(9);
        }

        return raw;
    }
}
