package com.fuzhongwangcs.ssmsimple.web.model;

public class RolePermission {
    private Long id;
    private Long roleId;
    private Long permissionId;
    public RolePermission() {
        
    }
    public RolePermission(Long id,Long roleId,Long permissionId) {
        this.id = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
    }
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return this.permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "RolePermission [id=" + id + ", roleId=" + roleId + ", permissionId=" + permissionId + "]";
    }
}
