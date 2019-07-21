package com.jnshu.model.vo;

import com.jnshu.model.User;
import lombok.Data;
import lombok.ToString;

@ToString(callSuper = true)
@Data
public class UserVO extends User {
    private String jobName;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
