package me.DNFneca.leaderboard.utils.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public final class ComponentUtils {

    public static String serializeComponent(final Component component) {
        return GsonComponentSerializer.gson().serialize(component);
    }

    public static Component deserializeComponent(final String serializedComponent) {
        return GsonComponentSerializer.gson().deserialize(serializedComponent);
    }

    public static String serializeComponentAsString(final Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    public static Component deserializeStringAsComponent(final String serializedComponent) {
        return PlainTextComponentSerializer.plainText().deserialize(serializedComponent);
    }

    public static boolean areComponentsEqual(final Component component1, final Component component2) {
        return component1.equals(component2);
    }

}
