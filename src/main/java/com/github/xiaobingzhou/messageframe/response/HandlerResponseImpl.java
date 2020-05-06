package com.github.xiaobingzhou.messageframe.response;

import com.github.xiaobingzhou.messageframe.IMessageFrame;
import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class HandlerResponseImpl implements HandlerResponse, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String deviceId;
    private IMessageFrame messageFrame;
    private String message;
    private Date systemDate;

    public static HandlerResponse newResponse(HandlerRequest request) {
        return new HandlerResponseImpl()
                .setDeviceId(request.getDeviceId())
                .setMessageFrame(request.getMessageFrame())
                .setSystemDate(request.getSystemDate())
                ;
    }

}
