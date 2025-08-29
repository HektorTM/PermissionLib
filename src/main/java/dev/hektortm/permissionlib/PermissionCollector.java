package dev.hektortm.permissionlib;

import org.bukkit.permissions.PermissionDefault;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class PermissionCollector {
    private PermissionCollector() {}

    public static <E extends Enum<E>> List<PermissionNode> fromEnum(
            Class<E> enumClass,
            Function<E, String> nodeGetter
    ) {
        List<PermissionNode> list = new ArrayList<>();
        for (E e : enumClass.getEnumConstants()) {
            list.add(new PermissionNode(nodeGetter.apply(e)));
        }
        return list;
    }

    public static <E extends Enum<E>> List<PermissionNode> fromEnum(
            Class<E> enumClass,
            Function<E, String> nodeGetter,
            Function<E, String> descGetter,
            Function<E, PermissionDefault> defGetter
    ) {
        List<PermissionNode> list = new ArrayList<>();
        for (E e : enumClass.getEnumConstants()) {
            list.add(new PermissionNode(
                    nodeGetter.apply(e),
                    descGetter.apply(e),
                    defGetter.apply(e)
            ));
        }
        return list;
    }

    public static List<PermissionNode> fromNodes(Collection<String> nodes) {
        List<PermissionNode> list = new ArrayList<>();
        for (String node : nodes) {
            list.add(new PermissionNode(node));
        }
        return list;
    }


}
