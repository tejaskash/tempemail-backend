package tk.tempmlhub.api.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
public class GetMailResponse {
    private List<String> senders;

    private List<String> recipients;

    private String subject;

    private String textContent;

    private String htmlContent;

    private Date date;
}
