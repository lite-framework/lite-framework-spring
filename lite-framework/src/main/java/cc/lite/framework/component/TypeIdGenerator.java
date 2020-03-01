package cc.lite.framework.component;

import cc.lite.framework.type.ITypeIdGenerator;
import cc.lite.framework.type.IdGenerator;

import java.util.HashMap;
import java.util.Map;

public class TypeIdGenerator implements ITypeIdGenerator {
    private Map<String, IdGenerator> generatorMap = new HashMap<>();
    private int                      workerId;

    public TypeIdGenerator(int workerId) {
        this.workerId = workerId;
    }

    public long nextId(String type) {
        IdGenerator generator = generatorMap.get(type);

        if (generator == null)
            generator = addGenerator(type);

        return generator.nextId();
    }

    private synchronized IdGenerator addGenerator(String type) {
        IdGenerator generator = generatorMap.get(type);

        if (generator == null) {
            generator = Blizzard.create(this.workerId);
            generatorMap.put(type, generator);
        }


        return generator;
    }
}
