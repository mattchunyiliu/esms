package kg.kundoluk.school.controller.web.admin;

import kg.kundoluk.school.model.references.Role;
import kg.kundoluk.school.service.references.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/web/v1/security/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/list")
    public List<Role> list() {
        return this.roleService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(
            @PathVariable(name = "id") Role role
    ) {
        if (role == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
