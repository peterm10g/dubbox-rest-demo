package top.crowley.dubbox.rest.demo.api.util;

public class Constants {
    public static final int    SUCCESS_CODE = 0;

    public static final String SUCCESS_DESC = "success";

    public class ErrorCode {
        // user
        public static final int USER_EXISTS_CODE            = 10000;

        public static final int USER_NOTEXISTS_CODE         = 10001;

        public static final int USER_INVALID_PARAM_CODE     = 10002;

        public static final int USER_INVALID_PIN_CODE       = 10003;

        public static final int USER_VEHICLE_STATUS_INVALID = 10004;
    }

    public class ErrorDesc {
        // user
        public static final String USER_EXISTS_CODE            = "user already exists";

        public static final String USER_NOTEXISTS_CODE         = "user not exists";

        public static final String USER_INVALID_PARAM_CODE     = "Invalid parameter : ";

        public static final String USER_INVALID_PIN_CODE       = "Invalid PIN";

        public static final String USER_VEHICLE_STATUS_INVALID = "Vehicle status invalid.";
    }
}
