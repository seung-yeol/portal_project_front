package kr.ac.jejunu.portal_front.vo;

import lombok.Data;

/**
 * Created by seung-yeol on 2018. 6. 15..
 */

@Data
public class CommentVO {
    private String userId;
    private Integer diaryId;
    private String comment;
    private String writeDate;
}
