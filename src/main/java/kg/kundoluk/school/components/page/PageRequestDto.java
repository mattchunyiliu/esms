package kg.kundoluk.school.components.page;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
public class PageRequestDto {
    @Max(10)
    @Min(0)
    @NotNull
    private Integer limit;
    @Min(0)
    @NotNull
    private Integer offset;
}
