package dev.dejay.reactor.config.type;

import com.google.common.collect.Maps;
import dev.dejay.reactor.Reactor;
import dev.dejay.reactor.config.ConfigType;
import dev.dejay.reactor.config.LoadableConfig;
import dev.dejay.reactor.config.representer.YamlBukkitConstructor;
import dev.dejay.reactor.config.representer.YamlObjectRepresenter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

public abstract class YamlConfig<T extends YamlConfig> implements LoadableConfig<T> {

    /**
     * Fucked if I know.
     */
    private static final transient YamlObjectRepresenter REPRESENTER = new YamlObjectRepresenter();
    private static final transient DumperOptions DUMPER_OPTIONS = new DumperOptions();

    /**
     * Class of the configuration (for serialisation).
     */
    private transient final Class<T> clazz;

    /**
     * Logger to log to.
     */
    private transient final Logger logger;

    /**
     * Backing yaml (the fucks a backing yaml?)
     */
    private final transient Yaml backingYaml;

    /**
     * Represents a configuration file.
     *
     * @param clazz Class of the configuration.
     */
    public YamlConfig(Class<T> clazz) throws NullPointerException {
        this.clazz = clazz;
        this.logger = Reactor.getLogger();
        this.backingYaml = new Yaml(new YamlBukkitConstructor(this.clazz), REPRESENTER, DUMPER_OPTIONS);
        this.backingYaml.setBeanAccess(BeanAccess.FIELD);
        for (Field field : this.clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (Modifier.isStatic(field.getModifiers()) || !field.getType().isEnum()) continue;
            this.backingYaml.addTypeDescription(new TypeDescription(field.getType(), field.getName()));
        }
    }

    /**
     * Loads the configuration from disk and returns its instantiated object.
     *
     * @return The config instance.
     */
    @Override
    public T load() {
        try {
            YamlBukkitConstructor.CLASS_MAPPINGS.put(this.clazz.getName(), this.clazz);
            this.logger.info("Attempting to load config, " + this.getClass().getSimpleName() + "..");
            return (T)((YamlConfig)this.backingYaml.loadAs(new FileInputStream(this.getPath().toFile()), this.clazz));
        }
        catch (Exception e) {
            if (e instanceof FileNotFoundException) {
                this.logger.warn("Could not find, " + this.getPath().toFile().getName() + ", creating one now...");
            } else {
                e.printStackTrace();
            }
            LoadableConfig config = this.getDefaultConfig();
            config.save();
            return (T)config;
        }
    }

    /**
     * @return The type of config this represents on disk.
     */
    @Override
    public ConfigType getType() {
        return ConfigType.YAML;
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
        this.logger.info("Saving config, " + this.getClass().getSimpleName() + "..");
        try {
            Path saveDirectory = this.getPath().getParent();
            if (!Files.exists(saveDirectory)) {
                Files.createDirectory(saveDirectory);
            }

            if (!Files.exists(this.getPath())) {
                Files.createFile(this.getPath());
            }

            HashMap<String, Object> tags = Maps.newHashMap();
            for (Field field : this.clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (Modifier.isStatic(field.getModifiers())) continue;
                try {
                    if (field.getClass().isEnum()) {
                        tags.put(field.getName(), ((Enum)field.get(this)).name());
                        continue;
                    }
                    tags.put(field.getName(), field.get(this));
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            FileWriter writer = new FileWriter(this.getPath().toFile());
            this.backingYaml.dump(tags, writer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        DUMPER_OPTIONS.setDefaultFlowStyle(FlowStyle.BLOCK);
        DUMPER_OPTIONS.setPrettyFlow(true);
    }

}
