package dev.dejay.reactor.config.type;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import dev.dejay.reactor.Reactor;
import dev.dejay.reactor.config.ConfigType;
import dev.dejay.reactor.config.LoadableConfig;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.Logger;

public abstract class TomlConfig<T extends TomlConfig> implements LoadableConfig<T> {

    /**
     * Class of the configuration (for serialisation).
     */
    private transient final Class<T> clazz;

    /**
     * The Toml config that will be used to drive the automatic loading.
     */
    protected transient final Toml config;

    /**
     * Logger to log to.
     */
    private transient final Logger logger;

    /**
     * Represents a configuration file.
     *
     * @param clazz Class of the configuration.
     */
    public TomlConfig(Class<T> clazz) throws NullPointerException {
        this.clazz = clazz;
        this.config = new Toml();
        this.logger = Reactor.getLogger();

        if (Files.exists(this.getPath())) {
            this.config.read(this.getPath().toFile());
        }
    }

    /**
     * Loads the configuration from disk and returns its instantiated object.
     *
     * @return The config instance.
     */
    @Override
    @SuppressWarnings("unchecked")
    public T load() {
        try {
            this.logger.info("Attempting to load config, " + this.getClass().getSimpleName() + "..");

            T instance;

            if (!Files.exists(this.getPath())) {
                this.logger.warn("Could not find, " + this.getPath().toFile().getName() + ", creating one now..");

                instance = (T) this.clazz.getDeclaredConstructor().newInstance().getDefaultConfig();
                instance.save();
            } else {
                instance = this.clazz.getDeclaredConstructor().newInstance();
            }

            return instance;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return The type of config this represents on disk.
     */
    @Override
    public ConfigType getType() {
        return ConfigType.TOML;
    }

    /**
     * @return The path that this config should be saved and loaded from.
     */
    @Override
    public abstract Path getPath();

    /**
     * @return Default config to write if none can be found.
     */
    @Override
    public abstract T getDefaultConfig();

    @Override
    public void save() {
        this.writeValues(this.config);

        try {
            TomlWriter writer = new TomlWriter();
            writer.write(this.config, this.getPath().toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void writeValues(Toml config);
}
