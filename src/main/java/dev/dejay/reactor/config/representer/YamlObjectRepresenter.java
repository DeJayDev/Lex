package dev.dejay.reactor.config.representer;

import java.util.HashMap;
import java.util.Set;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

public class YamlObjectRepresenter extends Representer {

    @Override
    protected MappingNode representJavaBean(Set<Property> properties, Object javaBean) {
        if (!this.classTags.containsKey(javaBean.getClass())) {
            this.addClassTag(javaBean.getClass(), Tag.MAP);
        }
        return super.representJavaBean(properties, javaBean);
    }

    @Override
    public Node represent(Object data) {
        if (data instanceof HashMap) {
            HashMap map = (HashMap) data;
            map.forEach((key, value) -> {
                if (!value.getClass().isEnum()) {
                    return; // I guess?
                }
                map.put(key, ((Enum) value).name());
            });
            return super.represent(map);
        }
        return super.represent(data);
    }

}