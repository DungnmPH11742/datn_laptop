package com.poly.common;

public class Constants {

    public enum MessageCompare {;
        private String code;
        private String message;

        public void MessageCompare(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
