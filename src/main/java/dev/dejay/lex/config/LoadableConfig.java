package dev.dejay.lex.config;

import dev.dejay.lex.config.type.JsonConfig;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

public interface LoadableConfig<T extends LoadableConfig> {

    /**
     * Obtains a LoadableConfig instance by it's class.
     *
     * @param configClass The class to load from.
     * @return An instance of either JsonConfig or YamlConfig.
     */
    @SuppressWarnings("unchecked")
    static <T extends LoadableConfig> T getByClass(Class<T> configClass) {
        if (!(JsonConfig.class.isAssignableFrom(configClass))) {
            throw new IllegalArgumentException("This class cannot be instantiated. Is not a ConfigType, type.");
        }

        try {
            return configClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the configuration from disk and returns its instantiated object.
     *
     * @return The config instance.
     */
    T load();

    /**
     * Saves the config to disk.
     */
    void save();

    /**
     * @return The type of config this represents on disk.
     */
    ConfigType getType();

    /**
     * @return The path that this config should be saved and loaded from.
     */
    Path getPath();

    /**
     * @return Default config to write if none can be found.
     */
    T getDefaultConfig();
}