package io.lite.framework.type;

public interface UserType {
    UserType setUserId(Long userId);


    static boolean isUserType(Object o) {
        return o instanceof UserType;
    }
}
