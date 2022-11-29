package kg.kundoluk.school.components.hateoas.assembler;

import kg.kundoluk.school.model.base.BaseEntity;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public abstract class DataTableResourceAssembler<T extends BaseEntity, D extends RepresentationModel<?>> extends RepresentationModelAssemblerSupport<T, D> {
    public DataTableResourceAssembler(Class<?> controllerClass, Class<D> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    protected D createModelWithId(Object id, T entity) {
        D d = super.createModelWithId(id, entity);
        return d;
    }
}
