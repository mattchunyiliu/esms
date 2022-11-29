package kg.kundoluk.school.model.enums;

public enum RoleIdType {
    STUDENT(3L), INSTRUCTOR(4L), PARENT(5L), DIRECTOR(6L);
    private Long roleId;
    RoleIdType(Long roleId){
        this.roleId = roleId;
    }
    public Long getRoleId(){ return roleId;}
}
