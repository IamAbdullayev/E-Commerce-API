package com.abdullayev.demoshops.requests.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddCategoryRequest {
    private String name;
}
