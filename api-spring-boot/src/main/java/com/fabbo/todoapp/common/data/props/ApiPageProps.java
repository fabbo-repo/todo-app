package com.fabbo.todoapp.common.data.props;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ApiPageProps {
    private int pageNum;

    private int pageSize;
}
