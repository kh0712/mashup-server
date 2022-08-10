package kr.mashup.branding.ui.attendance;

import kr.mashup.branding.ui.attendance.request.AttendanceCheckRequest;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.facade.attendance.AttendanceFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.attendance.response.AttendanceCheckResponse;
import kr.mashup.branding.ui.attendance.response.PersonalAttendanceResponse;
import kr.mashup.branding.ui.attendance.response.PlatformAttendanceResponse;
import kr.mashup.branding.ui.attendance.response.TotalAttendanceResponse;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    private final AttendanceFacadeService attendanceFacadeService;

    @ApiOperation(
        value = "출석 체크",
        notes =
            "<h2>Error Code</h2>" +
                "<p>" +
                "MEMBER_NOT_FOUND</br>" +
                "ATTENDANCE_TIME_OVER</br>" +
                "ATTENDANCE_CODE_NOT_FOUND</br>" +
                "ATTENDANCE_CODE_INVALID(등록된 코드와 다름)</br>" +
                "ATTENDANCE_ALREADY_CHECKED" +
                "</p>"

    )
    @PostMapping("/check")
    public ApiResponse<AttendanceCheckResponse> check(
        @ApiIgnore MemberAuth auth,
        @RequestBody AttendanceCheckRequest req
    ) {
        AttendanceCheckResponse res = attendanceFacadeService.checkAttendance(
            auth.getMemberId(),
            req.getCheckingCode()
        );
        return ApiResponse.success(res);
    }

    @ApiOperation(
        value = "플랫폼 전체 출석조회",
        notes =
            "<h2>Error Code</h2>" +
                "<p>" +
                "SCHEDULE_NOT_FOUND</br>" +
                "EVENT_NOT_FOUND" +
                "</p>"

    )
    @GetMapping("/platforms")
    public ApiResponse<TotalAttendanceResponse> getTotalPlatform(
        @RequestParam Long scheduleId
    ) {
        TotalAttendanceResponse res =
            attendanceFacadeService.getTotalAttendance(scheduleId);
        return ApiResponse.success(res);
    }

    @ApiOperation(
        value = "플랫폼별 출석조회",
        notes =
            "<h2> Error Code</h2>" +
                "<p>" +
                "SCHEDULE_NOT_FOUND" +
                "</p>"
    )
    @GetMapping("/platforms/{platformName}")
    public ApiResponse<PlatformAttendanceResponse> getPlatform(
        @PathVariable Platform platformName,
        @RequestParam Long scheduleId
    ) {
        PlatformAttendanceResponse res =
            attendanceFacadeService.getPlatformAttendance(platformName, scheduleId);
        return ApiResponse.success(res);
    }

    @ApiOperation(
        value = "세미나별 개인 출석조회",
        notes =
            "<h2> Error Code</h2>" +
                "<p>" +
                "MEMBER_NOT_FOUND</br>" +
                "SCHEDULE_NOT_FOUND" +
                "</p>"
    )
    @GetMapping("/schedules/{scheduleId}")
    public ApiResponse<PersonalAttendanceResponse> getPersonal(
        @ApiIgnore MemberAuth auth,
        @PathVariable Long scheduleId
    ) {
        PersonalAttendanceResponse res =
            attendanceFacadeService.getPersonalAttendance(
                auth.getMemberId(),
                scheduleId
            );
        return ApiResponse.success(res);
    }
}
