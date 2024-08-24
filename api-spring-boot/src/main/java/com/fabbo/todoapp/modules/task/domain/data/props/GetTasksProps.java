package com.fabbo.todoapp.modules.task.domain.data.props;

import com.fabbo.todoapp.common.data.props.ApiPageProps;
import com.fabbo.todoapp.modules.user.domain.data.props.GetUserProps;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTasksProps extends ApiPageProps {
    private GetUserProps getUserProps;

    private FilterTasksProps filter;

    public GetTasksProps(
            final int pageNum,
            final GetUserProps getUserProps
    ) {
        super(pageNum, 10);
        this.getUserProps = getUserProps;
    }
}
