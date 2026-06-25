import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Interface
interface Notifiable {
    void send(String recipient, String message);
    
    default void logStatus() {
        System.out.println("Notification status logged at [" + LocalDateTime.now() + "]");
    }
}

// Abstract base class implementing the Interface
abstract class BaseNotification implements Notifiable {
    private final String notificationId;

    public BaseNotification() {
        this.notificationId = UUID.randomUUID().toString();
    }

    public String getNotificationId() {
        return notificationId;
    }

    public int getCharacterLimit() {
        return 500; // Default limit
    }

    public abstract void authenticate();
}

// Email Notification
class EmailNotification extends BaseNotification {
    @Override
    public int getCharacterLimit() {
        return 2000;
    }

    @Override
    public void authenticate() {
        System.out.println("[Auth] Authenticating via OAuth for Email server...");
    }

    @Override
    public void send(String recipient, String message) {
        authenticate();
        if (message.length() > getCharacterLimit()) {
            message = message.substring(0, getCharacterLimit()) + "... (Truncated)";
        }
        System.out.println("Sending Email to [" + recipient + "] with ID [" + getNotificationId() + "]: " + message);
    }
}

// SMS Notification
class SmsNotification extends BaseNotification {
    @Override
    public int getCharacterLimit() {
        return 160;
    }

    @Override
    public void authenticate() {
        System.out.println("[Auth] Verifying SMS Gateway API Key...");
    }

    @Override
    public void send(String recipient, String message) {
        authenticate();
        // Explicit logic requirement: truncate message if it exceeds limit
        if (message.length() > getCharacterLimit()) {
            message = message.substring(0, getCharacterLimit());
        }
        System.out.println("Sending SMS to [" + recipient + "] with ID [" + getNotificationId() + "]: " + message);
    }
}

// Push Notification
class PushNotification extends BaseNotification {
    @Override
    public void authenticate() {
        System.out.println("[Auth] Handshaking with APNS/FCM Push Tokens...");
    }

    @Override
    public void send(String recipient, String message) {
        authenticate();
        if (message.length() > getCharacterLimit()) {
            message = message.substring(0, getCharacterLimit()) + "... (Truncated)";
        }
        System.out.println("Sending Push to device [" + recipient + "] with ID [" + getNotificationId() + "]: " + message);
    }
}

public class MainQuestionThree {
    public static void main(String[] args) {
        List<Notifiable> notificationsQueue = new ArrayList<>();

        notificationsQueue.add(new EmailNotification());
        notificationsQueue.add(new SmsNotification());
        notificationsQueue.add(new PushNotification());

        String rawMessage = "Dear User, this is an important notification regarding your enterprise account workspace setup. Please review your active instances immediately.";

        System.out.println("--- Executing SaaS Notifications Dispatch Workflows ---\n");
        
        for (Notifiable channel : notificationsQueue) {
            channel.send("user@example.com / +123456789 / DeviceToken_XYZ", rawMessage);
            channel.logStatus();
            System.out.println("----------------------------------------------------------------------------------");
        }
    }
}