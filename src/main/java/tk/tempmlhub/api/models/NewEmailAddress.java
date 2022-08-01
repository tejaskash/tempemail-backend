package tk.tempmlhub.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class NewEmailAddress {
    private final String fullEmailAddress;
    private final String emailId;
    private final LocalDateTime expires;
}
