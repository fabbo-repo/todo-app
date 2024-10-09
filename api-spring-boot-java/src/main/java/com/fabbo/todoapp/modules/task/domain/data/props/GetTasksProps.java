package com.fabbo.todoapp.modules.task.domain.data.props;

import com.fabbo.todoapp.common.data.props.ApiPageProps;
import com.fabbo.todoapp.modules.user.domain.data.props.GetUserProps;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GetTasksProps extends ApiPageProps {
    private GetUserProps getUserProps;

    private FilterTasksProps filter;

    public GetTasksProps(
            final int pageNum,
            final GetUserProps getUserProps,
            final FilterTasksProps filter
    ) {
        super(pageNum, 10);
        this.getUserProps = getUserProps;
        this.filter = filter;
    }
}
