package oort.cloud.openmarket.category.controller.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CategoryTreeResponse {
    private Long id;
    private String name;
    private List<CategoryTreeResponse> children;

    public CategoryTreeResponse(Long id, String name, List<CategoryTreeResponse> children) {
        this.id = id;
        this.name = name;
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<CategoryTreeResponse> getChildren() {
        return children;
    }
}
