package dev.hektortm.permissionlib;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

import java.util.*;

public class PermissionRegistry {
    private final Set<String> registered = new HashSet<>();
    private final boolean createWildcards;
    private final PermissionDefault wildcardDefault;


    public PermissionRegistry(boolean createWildcards, PermissionDefault wildcardDefault) {
        this.createWildcards = createWildcards;
        this.wildcardDefault = wildcardDefault == null ? PermissionDefault.FALSE : wildcardDefault;
    }

    private static Builder builder() {
        return new Builder();
    }
    public static final class Builder {
        private boolean createWildcards = true;
        private PermissionDefault wildcardDefault = PermissionDefault.FALSE;
        public Builder wildcards(boolean enable ) {this.createWildcards = enable; return this;}
        public Builder wildcardDefault(PermissionDefault def) {this.wildcardDefault = def; return this;}
        public PermissionRegistry build() {
            return new PermissionRegistry(createWildcards, wildcardDefault);
        }
    }

    public void registerAll(Collection<PermissionNode> nodes) {
        PluginManager pm = Bukkit.getPluginManager();

        for (PermissionNode n : nodes) {
            String node = n.node();
            if (pm.getPermission(node) == null) {
                pm.addPermission(new Permission(node, n.description(), n.def()));
                registered.add(node);
            } else {
                Permission exists = pm.getPermission(node);
                if (exists != null && !n.description().isEmpty()) exists.setDescription(n.description());
            }
        }

        if (createWildcards) {
            for (PermissionNode n : nodes) {
                String node = n.node();
                String[] parts = node.split("\\.");

                String prefix = "";

                for (int i = 0; i < parts.length - 1; i++) {
                    prefix = prefix.isEmpty() ? parts[i] : prefix + "." + parts[i];
                    String parentNode = prefix + ".*";

                    Permission parent = pm.getPermission(parentNode);
                    if (parent == null) {
                        parent = new Permission(parentNode, "Wildcard permission for " + prefix, wildcardDefault);
                        pm.addPermission(parent);
                        registered.add(parentNode);
                    }
                    parent.getChildren().put(node, true);
                }
            }
        }

        for(String name : registered) {
            Permission perm = pm.getPermission(name);
            if (perm != null) perm.recalculatePermissibles();
        }
    }

    public void unregisterAll() {
        PluginManager pm = Bukkit.getPluginManager();
        List<String> sorted = new ArrayList<>(registered);
        sorted.sort(Comparator.comparingInt(String::length).reversed());
        for (String node : sorted) {
            pm.removePermission(node);
        }
        registered.clear();
    }

    public List<String> listRegistered() {
        return new ArrayList<>(registered);
    }


}
