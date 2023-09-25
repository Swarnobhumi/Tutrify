package Model;

public class ChatMessageModel {
    public static String SEND_BY_ME = "me";
    public static String SEND_BY_BOT = "bot";

    String message;
    String sendBy;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendBy() {
        return sendBy;
    }

    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
    }

    public ChatMessageModel(String message, String sendBy) {
        this.message = message;
        this.sendBy = sendBy;
    }
}
