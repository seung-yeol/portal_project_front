package kr.ac.jejunu.portal_front.vo;

import lombok.Data;

/**
 * Created by seung-yeol on 2018. 6. 14..
 */

@Data
public class UserVO {
    private String userId;
    private String password;
    private String userName;
    private String birthday;
    private String phoneNo;
    private String gender;
}
