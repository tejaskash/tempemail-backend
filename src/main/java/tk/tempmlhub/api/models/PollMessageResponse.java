package tk.tempmlhub.api.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class PollMessageResponse {
    private String emailID;

    private List<String> messageItems;
}
