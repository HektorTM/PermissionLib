package dev.hektortm.permissionlib;

import org.bukkit.permissions.PermissionDefault;

public class PermissionNode {
    private final String node;
    private final String description;
    private final PermissionDefault def;

    public PermissionNode(String node) {
        this(node, "", PermissionDefault.FALSE);
    }

    public PermissionNode(String node, String description, PermissionDefault def) {
        this.node = node;
        this.description = description == null ? "" : description;
        this.def = def == null ? PermissionDefault.FALSE : def;
    }
    public String node() { return node; }
    public String description() { return description; }
    public PermissionDefault def() { return def; }

    public static PermissionNode of(String node) {
        return new PermissionNode(node);
    }

    public static PermissionNode of(String node, String description, PermissionDefault def) {
        return new PermissionNode(node, description, def);
    }

}
