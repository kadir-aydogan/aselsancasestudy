package tr.com.aselsankadir.casestudy.presentation.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseBody<T> {

    private ProcessResult processResult;

    private List<String> messages = new ArrayList<>();

    private T body;

    public static <T> ResponseBody<T> success(T body) {
        return new ResponseBody<T>(body, ProcessResult.SUCCESS, null);
    }

    public static ResponseBody<Void> success() {
        return new ResponseBody<>(null, ProcessResult.SUCCESS, null);
    }

    public static <T> ResponseBody<T> error(T body, String message) {
        return new ResponseBody<>(body, ProcessResult.ERROR, message);
    }

    public static <Void> ResponseBody<Void> error(String message) {
        return new ResponseBody<>(null, ProcessResult.ERROR, message);
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public ResponseBody(T body, ProcessResult processResult, String message) {
        this.processResult = processResult;
        this.body = body;
        if (message != null && !message.isEmpty()) {
            this.messages.add(message);
        }
    }
}
