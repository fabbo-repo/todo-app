package com.fabbo.todoapp.common.data.props;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiPageProps {
    private int pageNum;

    private int pageSize;
}
