package kr.mashup.branding.ui.notification.vo;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.adminmember.entity.Position;
import kr.mashup.branding.domain.notification.Notification;
import kr.mashup.branding.domain.notification.NotificationStatus;
import kr.mashup.branding.domain.notification.sms.SmsNotificationStatus;
import kr.mashup.branding.domain.notification.sms.SmsRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationSimpleResponse {
    @ApiModelProperty(value = "발송 내역 식별자", example = "1")
    private Long notificationId;

    @ApiModelProperty(value = "발송 내역 상태(생성됨, 알수없음, 진행중, 성공, 실패)")
    private NotificationStatus status;

    @ApiModelProperty(value = "발송 메모", example = "스프링팀 서류 합격 문자 안내")
    private String name;

    @ApiModelProperty(value = "발송 번호", example = "01012341234")
    private String senderPhoneNumber;

    @ApiModelProperty(value = "발송 시각")
    private LocalDateTime sentAt;

    @ApiModelProperty(value = "발송자")
    private Position sender;

    @ApiModelProperty(value = "발송 성공한 수신자 수", example = "209")
    private Integer successCount;

    @ApiModelProperty(value = "발송 실패한 수신자 수", example = "11")
    private Integer failureCount;

    @ApiModelProperty(value = "전체 수신자 수", example = "220")
    private Integer totalCount;

    public static NotificationSimpleResponse from(Notification notification){
        Map<SmsNotificationStatus, Integer> statusCountMap = notification.getSmsRequests()
            .stream()
            .collect(Collectors.toMap(
                SmsRequest::getStatus,
                it -> 1,
                Integer::sum
            ));
        return new NotificationSimpleResponse(
            notification.getNotificationId(),
            notification.getStatus(),
            notification.getName(),
            notification.getSenderPhoneNumber(),
            notification.getSentAt(),
            notification.getSender().getPosition(),
            statusCountMap.getOrDefault(SmsNotificationStatus.SUCCESS, 0),
            statusCountMap.getOrDefault(SmsNotificationStatus.FAILURE, 0),
            statusCountMap.values().stream().mapToInt(it -> it).sum()
        );
    }
}
