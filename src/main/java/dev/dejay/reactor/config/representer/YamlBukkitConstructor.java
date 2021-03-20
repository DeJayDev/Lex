package dev.dejay.reactor.config.representer;

import com.google.common.collect.Maps;
import dev.dejay.reactor.Reactor;
import java.util.Map;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;

public class YamlBukkitConstructor
    extends Constructor {

    public static final Map<String, Class<?>> CLASS_MAPPINGS = Maps.newHashMap();

    public YamlBukkitConstructor(Class<?> root) {
        super(root);
    }

    @Override
    protected Class<?> getClassForNode(Node node) {
        String name = node.getTag().getClassName();
        Class clazz = CLASS_MAPPINGS.computeIfAbsent(name, claz -> {
            try {
                return Class.forName(name, true, Reactor.class.getClassLoader());
            } catch (ClassNotFoundException e) {
                return null;
            }
        });
        if (clazz == null) {
            return super.getClassForNode(node);
        }
        return clazz;
    }
}
